package com.pegasus.messengerserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

  @Value("${websocket.message-broker-prefix}")
  private String messageBrokerPrefix;

  @Value("${websocket.destination-prefix}")
  private String destinationPrefix;

  @Value("${websocket.registry-endpoint}")
  private String registryEndpoint;

  @Override
  protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
    messages
      .simpDestMatchers(messageBrokerPrefix + "/**", destinationPrefix + "/**").authenticated()
      .simpSubscribeDestMatchers(messageBrokerPrefix + "/**").authenticated()
      .anyMessage().authenticated();
  }

  @Override
  protected boolean sameOriginDisabled() {
    return true;
  }

}
