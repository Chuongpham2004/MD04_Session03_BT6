package org.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        // Sử dụng Factory cơ bản của Spring để cấu hình thời gian chờ
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        // Đơn vị ở đây là mili-giây (3000ms = 3 giây)
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);

        return new RestTemplate(factory);
    }
}