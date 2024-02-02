package spring.workshop.expenses.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("indicator")
public class ExampleHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        Health.Builder healthBuilder = new Health.Builder();
        return 
            healthBuilder.up().withDetail("detail 1", "Just checking how to add detail ✅").withDetail("detail 2", "No relevant detail").build();
    }
}
