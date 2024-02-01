package spring.actuator.endpoints;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

//implemented as POC
@Component
@Endpoint(id="poc")
public class CustomAgaEndpoint{
   
}