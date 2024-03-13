package com.demo.configurations;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.demo.services.AccountService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
	
	@Autowired
	private AccountService accountService;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		
		return http.cors(cor -> cor.disable())
					.csrf(cs -> cs.disable())
					.authorizeHttpRequests(auth -> {
						auth
						.requestMatchers(new AntPathRequestMatcher("/")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/home/**")).permitAll()
	                    .requestMatchers(new AntPathRequestMatcher("/blog/**")).permitAll()
	                    .requestMatchers(new AntPathRequestMatcher("/cinema/**")).permitAll()
	                    .requestMatchers(new AntPathRequestMatcher("/account/**")).permitAll()
	                    .requestMatchers(new AntPathRequestMatcher("/film/**")).permitAll()
	                    .requestMatchers(new AntPathRequestMatcher("/bookseat/**")).permitAll()
	                    .requestMatchers(new AntPathRequestMatcher("/contact/**")).permitAll()
	                    .requestMatchers("/images/**").permitAll()
	                    .requestMatchers("/user/**").permitAll()
						.requestMatchers(new AntPathRequestMatcher("/admin/register")).hasAnyRole("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyRole("STAFF","ADMIN");

					})
					.formLogin(formLogin -> {
						formLogin.loginPage("/user/login")
						.loginProcessingUrl("/account/process-login")
						.usernameParameter("username")
						.passwordParameter("password")
						//.defaultSuccessUrl("/account/welcome")
						.successHandler(new AuthenticationSuccessHandler() {
							
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
									Authentication authentication) throws IOException, ServletException {
								System.out.println(authentication.getName());
								Collection<GrantedAuthority> roles= (Collection<GrantedAuthority>) authentication.getAuthorities();
								System.out.println("roles");
								Map<String, String> urls= new HashMap<>();
								urls.put("ROLE_ADMIN", "/admin/dashboard");
								urls.put("ROLE_STAFF", "/admin/dashboard");
								urls.put("ROLE_USER", "/home");
								String url= "";
								for(GrantedAuthority role:roles)
								{
									System.out.println(role.getAuthority());
									if(urls.containsKey(role.getAuthority()))
									{
										url= urls.get(role.getAuthority());
										break;
									}
								}
								System.out.println(url);
								response.sendRedirect(url);
							}
						})
						.failureUrl("/user/login?error");
					})
					.logout(logout->{logout.logoutUrl("/account/logout").logoutSuccessUrl("/account/login?logout");
						})
					.exceptionHandling(ex->{
						ex.accessDeniedPage("/account/accessdenied");
					})
					.build();
					
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}