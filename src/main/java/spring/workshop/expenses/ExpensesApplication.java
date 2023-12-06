package spring.workshop.expenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ExpensesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensesApplication.class, args);
	}

	@GetMapping("/dobry_wieczor_kochanie")
    public String hello(@RequestParam(value = "imie", defaultValue = "World") String name) {
      	return String.format("Hello12 %s!", name);
    }

}
