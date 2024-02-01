package spring.workshop.expenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import spring.actuator.HealthIndicator.DBHealthIndicator;

@SpringBootApplication
public class ExpensesApplication extends SpringBootServletInitializer {
	DBHealthIndicator healthIndicator=new DBHealthIndicator();

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ExpensesApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ExpensesApplication.class, args);
	}

}
