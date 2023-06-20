package pl.ersms.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import pl.ersms.core.security.AzureClaimsMappingFilter;
import pl.ersms.core.security.AzureJwtTokenFilter;
import pl.ersms.core.security.LocalAuthenticationFilter;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    @ConditionalOnProperty(name = "application.security.disabled", havingValue = "true")
    SecurityFilterChain localFilterChain(HttpSecurity http) throws Exception {
        log.info("[MOCK] Using local security configuration");
        http.headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'")
                .and()
                .and()
                .csrf().disable()   //self set headers
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(new LocalAuthenticationFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @ConditionalOnProperty(name = "application.security.disabled", havingValue = "false", matchIfMissing = true)
    SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http.headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'")
                .and()
                .and()
                .csrf().disable()   //self set headers
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(new AzureJwtTokenFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new AzureClaimsMappingFilter(), AzureJwtTokenFilter.class);
        return http.build();
    }
}
