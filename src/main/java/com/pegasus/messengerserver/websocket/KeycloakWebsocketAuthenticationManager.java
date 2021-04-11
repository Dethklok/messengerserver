package com.pegasus.messengerserver.websocket;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.rotation.AdapterTokenVerifier;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Qualifier("websocket")
@RequiredArgsConstructor
public class KeycloakWebsocketAuthenticationManager implements AuthenticationManager {

  private final KeycloakTokenVerifier keycloakTokenVerifier;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JWSAuthenticationToken token = (JWSAuthenticationToken) authentication;
    String tokenString = (String) token.getCredentials();
    log.debug("Authentication token credentials {}:", token.getCredentials());

    try {
      final AccessToken accessToken = keycloakTokenVerifier.verifyToken(tokenString);
      List<GrantedAuthority> authorities = accessToken.getRealmAccess().getRoles().stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
      token = new JWSAuthenticationToken(tokenString, accessToken, authorities);
    } catch (VerificationException e) {
      log.debug("Exception authenticating the token {}: ", tokenString, e);
      throw new BadCredentialsException("Invalid token");
    }

    return token;
  }

}
