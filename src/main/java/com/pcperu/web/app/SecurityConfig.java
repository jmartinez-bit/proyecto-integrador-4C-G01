package com.pcperu.web.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pcperu.web.app.config.CustomLoginSuccessHandler;
import com.pcperu.web.app.oauth.OAuth2LoginSuccessHandler;
import com.pcperu.web.app.oauth.UsuarioOauth2UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private CustomLoginSuccessHandler successHandler;
	
	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
		.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        .and().authorizeRequests()
				.antMatchers("/oauth2/**", "/login", "/", "/register", "/register2", "/uploads/**","/productos/**", "/carrito/add/**").permitAll()
				.antMatchers("/carrito").authenticated()
				.antMatchers("/home/**").hasAnyAuthority("USER", "ADMIN")
				.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error=true")
					.successHandler(successHandler)
					.usernameParameter("email")
					.passwordParameter("password")
				.and()
					.oauth2Login()
					.loginPage("/login")
					.successHandler(successHandler)
					.userInfoEndpoint().userService(oauth2UsuarioService)
					.and()
					.successHandler(oAuth2LoginSuccessHandler)
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").permitAll()
				.deleteCookies("JSESSIONID").and()
				.exceptionHandling()
				.accessDeniedPage("/acces-denied");
	}
	
	@Autowired
	private UsuarioOauth2UsuarioService oauth2UsuarioService;
	
	@Autowired
	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
}
