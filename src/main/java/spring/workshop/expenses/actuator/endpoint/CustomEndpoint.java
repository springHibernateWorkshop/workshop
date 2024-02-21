package spring.workshop.expenses.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="custom")
public class CustomEndpoint {

    @ReadOperation
    public String helloActuator(){
        return "Hello from actuator";
    }
}
