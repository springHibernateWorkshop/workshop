package spring.workshop.expenses.security;

import javax.sql.DataSource;

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
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/categories/**").permitAll()
				.requestMatchers("/shops/**").permitAll()
				.requestMatchers("/users/**").hasRole("ADMINISTRATOR")
				.requestMatchers("/expenses/**").hasRole("EMPLOYEE")
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

		UserDetails superior = User
				.withUsername("bartosz.kowalski")
				.password(passwordEncoder.encode("superior"))
				.roles("SUPERIOR")
				.build();
		UserDetails employee = User
				.withUsername("victoria.marano")
				.password(passwordEncoder.encode("employee"))
				.roles("EMPLOYEE")
				.build();
		return new InMemoryUserDetailsManager(employee, superior);

	}

	@Bean
	public UserDetailsService userDetailsServiceFromDB(PasswordEncoder passwordEncoder, DataSource dataSource) {

		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
		userDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?");
		userDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM role WHERE username = ?");
		userDetailsManager.setRolePrefix("ROLE_");

		return userDetailsManager;

	}
}
