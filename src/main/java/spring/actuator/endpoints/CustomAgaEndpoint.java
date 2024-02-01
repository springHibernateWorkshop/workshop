package spring.actuator.endpoints;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


//implemented as POC

@Endpoint(id="aga")
@Component
public class CustomAgaEndpoint{
    
      @ReadOperation
      @Bean
      public String HiFromActuator(){
        return "Hi from endpoint created in Actuator";
      } 

}