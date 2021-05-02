package com.pegasus.messengerserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
public class OpaqueSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}")
  private String introspectionUri;

  @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}")
  private String clientSecret;

  @Value("${websocket.registry-endpoint}")
  private String websocketRegistryEndpoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests(auth -> auth
        .antMatchers("/csrf").permitAll()
        .antMatchers(websocketRegistryEndpoint + "/**").permitAll()
        .anyRequest().authenticated())
      .oauth2ResourceServer(oauth2 -> oauth2
        .authenticationManagerResolver(httpServletRequest -> opaqueTokenAuthenticationProvider()::authenticate))
//      .headers(headers -> headers
//        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
      .cors(Customizer.withDefaults())
      .csrf(csrf -> csrf
        .ignoringAntMatchers(websocketRegistryEndpoint + "/**"))
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

  }

  @Bean
  public OpaqueTokenAuthenticationProvider opaqueTokenAuthenticationProvider() {
    OpaqueTokenIntrospector opaqueTokenIntrospector = new NimbusOpaqueTokenIntrospector(this.introspectionUri, clientId, clientSecret);
    return new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
    corsConfiguration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

}
