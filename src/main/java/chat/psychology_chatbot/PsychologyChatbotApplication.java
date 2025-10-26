package chat.psychology_chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PsychologyChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsychologyChatbotApplication.class, args);
	}

}
