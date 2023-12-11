package spring.workshop.expenses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ExpensesApplication extends SpringBootServletInitializer {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ExpensesApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(ExpensesApplication.class, args);
	}

	@GetMapping("/dobry_wieczor_kochanie")
    public String hello(@RequestParam(value = "imie", defaultValue = "World") String name) {
		List<String> names =jdbcTemplate.queryForList( "SELECT NAME FROM USER_TAB", String.class);
      	return String.format("Hello %s!", names);
    }

}
