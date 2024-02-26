package spring.workshop.expenses.controllers;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/superiors")
@EnableMethodSecurity(prePostEnabled = true)
public class SuperiorController {

}
