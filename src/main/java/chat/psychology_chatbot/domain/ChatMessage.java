package chat.psychology_chatbot.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "chat_messages") //몽고DB에 저장
public class ChatMessage {
    @Id
    private String id; //고유 Id

    private String chatRoomId; //어떤 대화방인지 구분

    private String senderName;

    private String message;

    private LocalDateTime timestamp;
}
