package org.zerock.clubex.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.clubex.security.handler.ClubLoginSuccessHandler;
import org.zerock.clubex.security.handler.ClubLogoutSuccessHandler;
import org.zerock.clubex.security.handler.ClubAccessDeniedHandler;
import org.zerock.clubex.security.service.ClubUserDetailsService;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) //555 page 중요 자주씀
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ClubUserDetailsService clubUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    ClubAccessDeniedHandler cDeniedHandler() {
        return new ClubAccessDeniedHandler();
    }

    @Bean
    ClubLoginSuccessHandler loginSuccessHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    ClubLogoutSuccessHandler logoutSuccessHandler() {
        return new ClubLogoutSuccessHandler();
    }

    /* 
        security를 적용하기 위한 url에 대한 설정과 로그인과 
        access denied되는 경우에
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 어느 페이지에 어떤 권한을 줄 것인가에 대한 것
         http.authorizeHttpRequests()
         .antMatchers("/sample/all").permitAll()
         .antMatchers("/sample/member").hasRole("MEMBER")
         .antMatchers("/sample/admin").hasRole("ADMIN");

        // 여기서 ADMIN으로 바꾸면 로그인 할 수 있는 대상이 다르게 제한됨
        http.exceptionHandling().accessDeniedHandler(cDeniedHandler());
        
        /* 1. Security login form 사용 */
        // http.formLogin();

        /* 2. 사용자 정의에 의한 loginprocessingurl 사용
         http.formLogin().loginPage("파일경로").loginProcessingUrl("매핑경로");
         member에 있는 login */
        // http.formLogin().loginPage("/member/login"); 
         http.csrf().disable();
        /* csrf 없이 동작 x */

        /* 3. UserDetailsService사용 : security가 들고있는 '/login' */
        http.formLogin().loginPage("/member/login")
            .loginProcessingUrl("/login")
            .successHandler(new ClubLoginSuccessHandler(passwordEncoder()));
            
        /* 4. OAuth2UserDetailsService 로그인  handler :: social login */
        http.oauth2Login().successHandler(loginSuccessHandler());
        
        /*로그아웃 시 이루어질 동작 분기*/
        http.logout() 
        .logoutSuccessHandler(logoutSuccessHandler());
        
        // 60 (sec)* 60 *24 = 1일 * 7 = 7일
        http.rememberMe().tokenValiditySeconds(60*60*24*7)
            .userDetailsService(clubUserDetailsService);
    }


    /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         // inMemory 방식, 사용자의컴퓨터에 저장??? <-> DB에서 데이터들고오기
        auth.inMemoryAuthentication()
         .withUser("user1")
         .password("$2a$10$JVJC1geWkmjTSHOy5Ch8ZeBovWB3kJHnW.v7.1jDU/v52NXXzN4uu")
         .roles("USER");
     }*/
    
}
