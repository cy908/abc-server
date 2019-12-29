package com.abc.app.engine.config;

import java.util.ArrayList;
import java.util.List;

import com.abc.app.engine.security.config.LoginUrl;
import com.abc.app.engine.security.util.AccessLogFilter;
import com.abc.app.engine.security.util.FixedPassword;
import com.abc.app.engine.security.util.PasswordUtil;
import com.abc.app.engine.security.util.SecurityUserService;
import com.abc.app.engine.security.util.TokenConfig;
import com.abc.app.engine.security.util.TokenFilter;
import com.abc.app.engine.security.util.TokenUtil;
import com.abc.app.engine.security.util.UnauthorizedHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(securityUserService()).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) {
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 放行URL-START
		List<String> permitUrlList = new ArrayList<>();
		permitUrlList.add(LoginUrl.URL_LOGIN);
		String[] permitUrls = permitUrlList.toArray(new String[permitUrlList.size()]);
		// 放行URL-END
		http.cors().and().csrf().disable();
		http.headers().cacheControl();
		http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler());
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(permitUrls).permitAll().anyRequest().authenticated();
		http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterAfter(accessLogFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public UserDetailsService securityUserService() {
		return new SecurityUserService();
	}

	@Bean
	public AuthenticationEntryPoint unauthorizedHandler() {
		return new UnauthorizedHandler();
	}

	@Bean
	public TokenConfig tokenConfig() {
		return new TokenConfig();
	}

	@Bean
	public TokenUtil tokenUtil() {
		return new TokenUtil();
	}

	@Bean
	public TokenFilter tokenFilter() {
		return new TokenFilter();
	}

	@Bean
	public PasswordUtil passwordUtil() {
		return new FixedPassword();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AccessLogFilter accessLogFilter() {
		return new AccessLogFilter();
	}

}
