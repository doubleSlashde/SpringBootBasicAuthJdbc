package de.doubleslash.example.springboot;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MySecurityConfigurer extends WebSecurityConfigurerAdapter {

   private static final Logger LOG = LoggerFactory.getLogger(MySecurityConfigurer.class);

   @Autowired
   DataSource dataSource;

   @Autowired
   public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {

      auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder())
            .usersByUsernameQuery("select username, password, enabled from user where username=?")
            .authoritiesByUsernameQuery("select username, role_role from user where username=?");

   }

   @Configuration
   @Order(1)
   public static class BasicAuthAdminForDevFunctionality extends WebSecurityConfigurerAdapter {

      @Override
      protected void configure(final HttpSecurity http) throws Exception {
         http.antMatcher("/h2-console/**").authorizeRequests().anyRequest().permitAll();

         // CSRF Prüfung für H2 Konsole deaktivieren, ok, da wir diese nur während der Entwicklung brauchen...
         // Ansonsten: There was an unexpected error (type=Forbidden, status=403).
         // Expected CSRF token not found. Has your session expired?
         http.csrf().disable();
         // ebenso Frame Options...
         http.headers().frameOptions().disable();

      }

   }

   @Configuration
   @Order(2)
   public static class BasicAuthProtectionForApp extends WebSecurityConfigurerAdapter {

      @Override
      protected void configure(final HttpSecurity http) throws Exception {
         http.authorizeRequests() //
               .anyRequest().authenticated() //
               .and() //
               .httpBasic();
      }

   }

}