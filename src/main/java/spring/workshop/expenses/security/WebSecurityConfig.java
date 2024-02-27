package spring.workshop.expenses.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				// .requestMatchers("/categories/**").permitAll()
				// .requestMatchers("/shops/**").permitAll()
				.requestMatchers("/users/**").hasRole("ADMINISTRATOR")
				.requestMatchers(HttpMethod.GET, "/expenses/**").hasAnyRole("EMPLOYEE", "SUPERIOR")
				.requestMatchers("/expenses/**").hasRole("EMPLOYEE")
				// .requestMatchers("/reports/**").hasAnyRole("SUPERIOR", "ACCOUNTANT")
				.requestMatchers("/employees/**").hasRole("ADMINISTRATOR")
				// .requestMatchers("/superiors/**").hasAnyRole("SUPERIOR", "ACCOUNTANT")
				.anyRequest().permitAll())
				.csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// @Bean
	// This is an example of how to create users in memory
	public UserDetailsService userDetailsServiceInMemory(PasswordEncoder passwordEncoder) {

		UserDetails bartosz = User
				.withUsername("bartosz")
				.password(passwordEncoder.encode("superior"))
				.authorities("ROLE_SUPERIOR", "VIEW_EXPENSES")
				.build();
		UserDetails victoria = User
				.withUsername("victoria")
				.password(passwordEncoder.encode("employee"))
				.authorities("ROLE_EMPLOYEE", "VIEW_EXPENSES")
				.build();

		UserDetails admin = User
				.withUsername("admin")
				.password(passwordEncoder.encode("admin"))
				// .roles(Role.EMPLOYEE.name())
				.authorities("CREATE_USER")
				.build();

		return new InMemoryUserDetailsManager(victoria, bartosz, admin);
	}

	@Bean
	public UserDetailsService userDetailsServiceFromDB(PasswordEncoder passwordEncoder, DataSource dataSource) {
		String passString = passwordEncoder.encode("password");
		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
		userDetailsManager.setUsersByUsernameQuery("SELECT username, password, true FROM user_tab WHERE username = ?");
		userDetailsManager.setAuthoritiesByUsernameQuery(
				"SELECT u.username, upper(r.name) FROM user_tab u join permission_tab p on u.role_id = p.role_id join right_tab r on r.right_id = p.right_id WHERE username = ?");
		return userDetailsManager;
	}
}
