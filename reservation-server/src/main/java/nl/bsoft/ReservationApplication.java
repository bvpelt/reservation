package nl.bsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@EnableBinding(Sink.class)
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationApplication {

	@Bean
	CommandLineRunner runner (ReservationRepository rr) {
		return args -> {
			Arrays.asList("Dr. Rod,Dr. Syer,Juergen,ALL THE COMMUNITY,Josh".split(","))
					.forEach( x -> rr.save(new Reservation(x)));

			rr.findAll().forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}
}

@Entity
class Reservation {

	@Id @GeneratedValue
	private Long id;
	private String reservationName;

	public Reservation() {

	}
	public Reservation(String reservationName) {
		this.reservationName = reservationName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Reservation{");
		sb.append("id=").append(id);
		sb.append(", reservationname='").append(reservationName).append("'");
		sb.append("}");
		return sb.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}
}

@MessageEndpoint
class MessageReservationReceiver {


    @ServiceActivator (inputChannel = Sink.INPUT)
    public void acceptReservation(String rn) {
    this.reservationRepository.save(new Reservation(rn));
    }

    @Autowired
    private ReservationRepository reservationRepository;
}

@RefreshScope
@RestController
class MessageRestController {

	// @Value( "${message}")
	private final String message;

	@RequestMapping(method = RequestMethod.GET, value = "/message")
	String message () {
		return this.message;
	}

	@Autowired
	public MessageRestController(@Value("${message}") String value) {
		this.message = value;
	}
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@RestResource (path="by-name")
	Collection<Reservation> findByReservationName (@Param("rn") String rn);
}