package com.autobots.automanager.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.security.TokenFilter;
import com.autobots.automanager.security.TokenService;
import com.autobots.automanager.servicos.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private Environment env;

  @Autowired
  CustomUserDetailsService userDetailsService;

  @Autowired
  TokenService tokenService;

  @Autowired
  RepositorioUsuario repositorioUsuario;

  private static final String[] rotasPublicas = {
      "/",
      "/auth/login",
      "/usuarios", "/usuario/**",
      "/empresas", "/empresas/**",
      "/mercadorias", "/mercadoria/**",
      "/veiculos", "/veiculo/**"
  };

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests()
        .antMatchers(rotasPublicas).permitAll()
        .anyRequest().authenticated();

    if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
      http.headers().frameOptions().disable();
    }

    http.cors().and().csrf().disable();

    http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/login").permitAll().anyRequest()
        .authenticated().and()
        .addFilterBefore(
            new TokenFilter(
                tokenService,
                repositorioUsuario),
            UsernamePasswordAuthenticationFilter.class);

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
    configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
