# Reservation Configuration	
The configuration of several distinct components will be provided by this configuration server.
The configuration server gets its content from a github repository.
This is defined in application.properties
``` 
spring.cloud.config.server.git.uri=file://${user.home}/Develop/reservation-repo
```
For development it points to a local respository

Working endpoints (default for spring cloud)

| endpoint                       | usage                                             |
|--------------------------------|---------------------------------------------------|
| http://localhost:8888/env      | show environment variables, usefull for debugging |
| http://localhost:8888/dump     | show a list of threads                            |
| http://localhost:8888/heapdump | get list of memory usage                          |
| http://localhost:8888/info     | ?                                                 |
| http://localhost:8888/metrics  | metrics about this running instance after metrics a regular expression can be used. Example: `http://localhost:8888/metrics/gauge.*` |
| http://localhost:8888/configprops | a number of configuration parameters with their value |
| http://localhost:8888/beans | a list of known beans |
| http://localhost:8888/trace | show a list of executed requests |
| http://localhost:8888/health | shows the current state of the component |
| http://localhost:8888/mappings | a list of all known mappings (rest services) |


