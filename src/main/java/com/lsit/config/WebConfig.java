package com.lsit.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Environment env;

    public WebConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        
        // Configure HTTP connector (if you want to support HTTP and redirect to HTTPS)
        factory.addAdditionalTomcatConnectors(createHttpConnector());
        
        // Configure SSL for HTTPS
        factory.addConnectorCustomizers(connector -> {
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(8443);  // HTTPS Port
        });
        
        return factory;
    }

    private Connector createHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8081);  // HTTP Port
        connector.setSecure(false);
        connector.setRedirectPort(8443);  // Redirect HTTP to HTTPS
        return connector;
    }
}
