package com.mahmood.atm_service.restclient;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class Config {

    @Bean
    public RestTemplate template(RestTemplateBuilder builder) {
        RestTemplate build = builder.build();
        List<ClientHttpRequestInterceptor> interceptors = build.getInterceptors();
        interceptors.add(new SecurityInterceptor());
        build.setInterceptors(interceptors);
        return build;
    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
}