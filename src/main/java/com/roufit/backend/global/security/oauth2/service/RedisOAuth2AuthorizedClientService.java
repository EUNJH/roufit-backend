package com.roufit.backend.global.security.oauth2.service;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Slf4j
@RequiredArgsConstructor
public class RedisOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    @Resource(name = "OAuth2AuthorizedClientIdTemplate")
    private HashOperations<String, String, OAuth2AuthorizedClient> hashOperations;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        ClientRegistration registration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (registration == null) {
            return null;
        }
        log.info("get auth from redis");
        return (T) this.hashOperations.get(principalName, clientRegistrationId);
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        String clientRegistrationId = authorizedClient.getClientRegistration().getRegistrationId();
        String principalName = principal.getName();
        log.info("put auth ot redis");
        this.hashOperations.put(principalName, clientRegistrationId, authorizedClient);
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        ClientRegistration registration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (registration != null) {
            log.info("delete auth from redis");
            this.hashOperations.delete(clientRegistrationId, principalName);
        }
    }
}
