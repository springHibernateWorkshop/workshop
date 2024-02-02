package spring.actuator.endpoints;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;


//implemented as POC

@RequestMapping("aga")
@Component
public class CustomAgaEndpoint{
    
      @Bean
      public String HiFromActuator(){
        return "Hi from endpoint created in Actuator";
      } 

}