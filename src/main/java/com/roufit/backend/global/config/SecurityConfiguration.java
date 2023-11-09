package com.roufit.backend.global.config;

import com.roufit.backend.global.security.jwt.JwtAuthenticationFilter;
import com.roufit.backend.global.security.oauth2.handler.OAuth2AuthorizationFailureHandler;
import com.roufit.backend.global.security.oauth2.handler.OAuth2AuthorizationSuccessHandler;
import com.roufit.backend.global.security.oauth2.service.CustomOAuth2UserService;
import com.roufit.backend.global.security.oauth2.service.RedisOAuth2AuthorizedClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final OAuth2AuthorizationSuccessHandler oAuth2AuthorizationSuccessHandler;
    private final OAuth2AuthorizationFailureHandler oAuth2AuthorizationFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(
                                        "/", "/swagger-ui/**",
                                        "**.html", "**.css", "**.js",
                                        "/swagger-resources/**", "/webjars/**", "/v3/api-docs/**"
                                )
                                .permitAll()
                                .anyRequest().authenticated()
                )

                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // OAuth2 로그인 설정
                .oauth2Login(oAuth2LoginConfigurer ->
                        oAuth2LoginConfigurer
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig
                                                .userService(customOAuth2UserService))
                                .failureHandler(oAuth2AuthorizationFailureHandler)
                                .successHandler(oAuth2AuthorizationSuccessHandler)
                                .clientRegistrationRepository(clientRegistrationRepository)
                                .authorizedClientService(authorizedClientService())
                )
                // JWT 필터 설정
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> httpSessionOAuth2AuthorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new RedisOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

}
