package com.example.odprojekt.configuration;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class WebConfiguration implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedOrigins("http://localhost:9090", "https://od-damian-smugorzewski-client.herokuapp.com")
//                .allowedHeaders("*").allowCredentials(true);
//    }
//
//    @Bean
//    public TomcatContextCustomizer sameSiteCookiesConfig() {
//        return context -> {
//            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
//            cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
//            context.setCookieProcessor(cookieProcessor);
//        };
//    }
//}
