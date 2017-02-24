# Reservation
An example of spring cloud see [youtube - An example of spring cloud](https://www.youtube.com/watch?v=cCEvFDhe3os)
The configuration files are from [github](https://github.com/joshlong/bootiful-microservices-config)

## Knownledge

### @RefreshScope
In the code there is a MessageRestController with as attribute @RefreshScope
```java
@RefreshScope
@RestController
class MessageRestController {

	@Value( "${message}")
	private String message;

	@RequestMapping("/message")
	String message () {
		return this.message;
	}
}
```
This indicates the value will be reevaluated when triggered at the actuator refresh endpoint like:
``` 
$ curl -d {} http://localhost:8000/refresh | jq
[
  "config.client.version",
  "message"
]

```
