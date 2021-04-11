package com.pegasus.messengerserver.websocket;

import org.keycloak.representations.AccessToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JWSAuthenticationToken extends AbstractAuthenticationToken implements Authentication {

  private static final long serialVersionUID = 1L;

  private final String token;
  private final AccessToken principal;

  public JWSAuthenticationToken(String token, AccessToken principal, Collection<GrantedAuthority> authorities) {
    super(authorities);
    this.token = token;
    this.principal = principal;
  }

  public JWSAuthenticationToken(String token) {
    this(token, null, null);
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

}
