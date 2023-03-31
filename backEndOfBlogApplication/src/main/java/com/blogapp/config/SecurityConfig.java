package com.blogapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blogapp.securityblog.CustomUserDetailService;
import com.blogapp.securityblog.JwtAuthenticationEntryPoint;
import com.blogapp.securityblog.JwtAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	//below comented code for new version how to configure spring security without Extendinf WebSecurityConfigurerAdapter class
	// and uncommented code is using websecurityconfigurerAdapter class
	// for without webSecurityConfigurerAdapter class information follow google and search like "websecurityConfigurerAdapter depricated"
	// then follow spring boot docs
	
	/*
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 * throws Exception {
	 * 
	 * http .csrf() .disable() .authorizeHttpRequests()
	 * .antMatchers(HttpMethod.GET).permitAll()
	 * .antMatchers(PUBLIC_URLS).permitAll()
	 * 
	 * 
	 * .anyRequest() .authenticated() .and()
	 * .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)
	 * .and() .sessionManagement()
	 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	 * 
	 * http.addFilterBefore(this.authenticationFilter,
	 * UsernamePasswordAuthenticationFilter.class);
	 * http.authenticationProvider(authenticationProvider());
	 * DefaultSecurityFilterChain defaultSecurityFilterChain = http.build(); return
	 * defaultSecurityFilterChain; }
	 * 
	 * @Bean public AuthenticationManager
	 * authenticationManagerBeanForNew(AuthenticationConfiguration configuration)
	 * throws Exception { // TODO Auto-generated method stub return
	 * configuration.getAuthenticationManager(); }
	 * 
	 * @Bean public DaoAuthenticationProvider authenticationProvider() {
	 * 
	 * DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
	 * provider.setUserDetailsService(this.customUserDetailService);
	 * provider.setPasswordEncoder(passwordEncoder());
	 * 
	 * return provider; }
	 */
	
	public static final String[] PUBLIC_URLS= {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-ui/**",
			"/swagger-resources/**",			
			"/webjars/**"
			
	};
	
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint  authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf()
		.disable()
		.authorizeHttpRequests()
		.antMatchers(HttpMethod.GET).permitAll()
		.antMatchers(PUBLIC_URLS).permitAll()
		
		
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	            .ignoring()
	            .antMatchers("/assets/**");
	}
	
	
}
