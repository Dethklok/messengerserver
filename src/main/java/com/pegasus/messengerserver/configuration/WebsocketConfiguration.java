package com.pegasus.messengerserver.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Order(HIGHEST_PRECEDENCE + 50)
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

  private final OpaqueTokenAuthenticationProvider opaqueTokenAuthenticationProvider;

  @Value("${websocket.message-broker-prefix}")
  private String messageBrokerPrefix;

  @Value("${websocket.destination-prefix}")
  private String destinationPrefix;

  @Value("${websocket.registry-endpoint}")
  private String registryEndpoint;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker(messageBrokerPrefix);
    registry.setApplicationDestinationPrefixes(destinationPrefix);
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint(registryEndpoint)
      .setAllowedOrigins("http://localhost:4200")
      .withSockJS()
      .setSessionCookieNeeded(false);
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
          String token = accessor.getFirstNativeHeader("Authorization");

          if (token == null) throw new AuthenticationCredentialsNotFoundException("Authorization header is not found");

          BearerTokenAuthenticationToken bearerToken = new BearerTokenAuthenticationToken(token.replaceAll("Bearer ", ""));
          Authentication authentication = opaqueTokenAuthenticationProvider.authenticate(bearerToken);
          accessor.setUser(authentication);
        }

        return message;
      }
    });
  }

}
