package chat.psychology_chatbot.repository;

import chat.psychology_chatbot.domain.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    //규칙에 맞게 이름만 지으면 스프링이 알아서 쿼리 생성
    List<ChatMessage> findByChatRoomIdOrderByTimestampAsc(String chatRoomId);
    List<ChatMessage> findByChatRoomIdOrderByTimestampDesc(String chatRoomId, Pageable pageable);
}
