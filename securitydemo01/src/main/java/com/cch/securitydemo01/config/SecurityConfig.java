package com.cch.securitydemo01.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        return jdbcTokenRepositoryImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        /**
         * 配置使用者
         */
        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // String password = passwordEncoder.encode("test");
        // auth.inMemoryAuthentication().withUser("test").password(password).roles("admin");
        /**
         * 使用 UserDetailsService
         */
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        // logout
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/api/v1/index").permitAll();
        http.exceptionHandling().accessDeniedPage("/403.html");
        http.formLogin() // 自定義編寫登入頁面
            .loginPage("/login.html")
            .loginProcessingUrl("/user/login") // 登入後訪問路徑
            .defaultSuccessUrl("/success.html") // 登入後跳轉路徑
            .permitAll()
            .and().authorizeRequests()
            .antMatchers("/", "/api/v1/hello", "/user/login").permitAll() // 不需要認證即可訪問
            .antMatchers("/api/v1/index").hasAuthority("admins")
            .antMatchers("/api/v1/index").hasAnyAuthority("admins,manager")
            .antMatchers("/api/v1/index").hasRole("sale") 
            .anyRequest().authenticated()
            .and().rememberMe().tokenRepository(persistentTokenRepository())
            .tokenValiditySeconds(60) // 設置有效時間
            .userDetailsService(userDetailsService)
            .and().csrf().disable(); // 關閉 csrf 保護
    }
}
