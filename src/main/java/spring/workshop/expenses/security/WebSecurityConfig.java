package spring.workshop.expenses.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import spring.workshop.expenses.enums.Role;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/categories/**").permitAll()
				.requestMatchers("/shops/**").permitAll()
				.requestMatchers("/users/**").hasRole("ADMINISTRATOR")
				.requestMatchers("/expenses/**").hasAnyRole("EMPLOYEE", "SUPERIOR")
				.requestMatchers("/reports/**").hasAnyRole("SUPERIOR", "ACCOUNTANT")
				.requestMatchers("/employees/**").hasAnyRole("EMPLOYEE", "SUPERIOR", "ACCOUNTANT")
				.requestMatchers("/superiors/**").hasAnyRole("SUPERIOR", "ACCOUNTANT")
				.anyRequest().hasAnyRole("EMPLOYEE", "SUPERIOR", "ACCOUNTANT", "ADMINISTRATOR"))
				.csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

		UserDetails bartosz = User
				.withUsername("bartosz.kowalski")
				.password(passwordEncoder.encode("superior"))
				.roles(Role.SUPERIOR.name())
				.build();
		UserDetails victoria = User
				.withUsername("victoria.marano")
				.password(passwordEncoder.encode("employee"))
				.roles(Role.EMPLOYEE.name())
				.build();
		return new InMemoryUserDetailsManager(victoria, bartosz);

	}

	// @Bean
	// public UserDetailsService userDetailsServiceFromDB(PasswordEncoder
	// passwordEncoder, DataSource dataSource) {

	// JdbcUserDetailsManager userDetailsManager = new
	// JdbcUserDetailsManager(dataSource);
	// userDetailsManager.setUsersByUsernameQuery("SELECT username, password FROM
	// user WHERE username = ?");
	// userDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority
	// FROM role WHERE username = ?");
	// userDetailsManager.setRolePrefix("ROLE_");

	// return userDetailsManager;

	// }
}
