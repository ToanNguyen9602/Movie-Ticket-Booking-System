package com.demo.configurations;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.cors(cor -> cor.disable())
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(request -> {
						request.requestMatchers(
							"/**" ,
							"/home"
							).permitAll();
		}).formLogin(form -> {
			form.loginPage("/admin/account/login").loginProcessingUrl("/admin/account/login-process")
					.usernameParameter("username").passwordParameter("password")
					.successHandler(new AuthenticationSuccessHandler() {
						@Override
						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
								Authentication authentication) throws IOException, ServletException {

							@SuppressWarnings("unchecked")
							Collection<GrantedAuthority> role = (Collection<GrantedAuthority>) authentication
									.getAuthorities();
							
							// custom to redirect
							Map<String, String> defaultRedirectUrlByRole = new HashMap<>();
							defaultRedirectUrlByRole.put("ROLE_ADMIN", "/admin/homepage/index");
							defaultRedirectUrlByRole.put("ROLE_STAFF", "/admin/homepage/index");
							defaultRedirectUrlByRole.put("ROLE_PROCESS_STAFF", "/admin/homepage/index");

							String url = "/admin/homepage/index";
							for (GrantedAuthority gr : role) {
								if (defaultRedirectUrlByRole.containsKey(gr.getAuthority())) {
									url = defaultRedirectUrlByRole.get(gr.getAuthority());
									break;
								}
							}
							response.sendRedirect(url);
						}
					}).failureUrl("/admin/account/login?mistake");
		}).logout(lg -> {
			lg.logoutUrl("/admin/account/logout")
				.logoutSuccessUrl("/admin/account/login?logout");
		}).exceptionHandling(hd -> {
			hd.accessDeniedPage("/admin/account/denied");
		}).build();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
