package chat.psychology_chatbot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//CORS 정책
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 API 경로(/**)에 대해 CORS 정책 적용
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500") // VS Code Live Server의 주소요청을 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드를 지정
                .allowedHeaders("*") // 모든 종류의 HTTP 헤더를 허용
                .allowCredentials(true); // 자격 증명(쿠키 등)을 포함한 요청을 허용
    }
}