package ca.sheridancollege.ozcelikh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 
 * @author Hizir Ozcelik, November 2021
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
// needs to be removed later. only for development purposes
		http.csrf().disable();
		http.headers().frameOptions().disable();

		http.authorizeRequests()
				.antMatchers("/secure/**").hasRole("USER")						
				.antMatchers(HttpMethod.POST, "/register").permitAll()
				.antMatchers(HttpMethod.GET, "/register").permitAll()
				.antMatchers("/", "/js/**", "/css/**", "/images/**", "/permission-denied", "/about").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/**").denyAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll()
				.and().logout().invalidateHttpSession(true)
				.clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout").permitAll().and().exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());

	}

}
