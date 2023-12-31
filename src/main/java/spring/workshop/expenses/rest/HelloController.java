package spring.workshop.expenses.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/dobry_wieczor_kochanie")
    public String hello(@RequestParam(value = "imie", defaultValue = "World X") String name) {
        return String.format("Hello14 %s!", name);
    }

    @GetMapping("/wie_ist_das_wetter")
    public String wetter(@RequestParam(value = "name_der_stadt", required = false) String stadtname) {
        return "Das Wetter in " + stadtname + " ist hervorragend. ";
    }

    @GetMapping("/users")
    public List<String> getAllUsers() {
        List<String> names = jdbcTemplate.queryForList("SELECT NAME FROM user_tab", String.class);
        return names;
    }
}
