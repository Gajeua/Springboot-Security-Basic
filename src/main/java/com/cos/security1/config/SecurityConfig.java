package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 된다.
public class SecurityConfig { // 스프링 시큐리티 필터
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests()	  // 어떤 요청에 대한 인가가 들어오면
				.antMatchers("/user/**").authenticated()  // /user로 들어오면 인증이 필요하다는 설정
				.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // Role이 어드민 또는 매니저를 가지고 있어야 접근 가능
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // Role이 어드민을 가지고 있어야 접근 가능
				.anyRequest() // 다른 요청들은
				.permitAll() // 모두 허락
				.and()
				.formLogin()
				.loginPage("/login");
		
		return http.build();
	}
}
