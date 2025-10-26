package chat.psychology_chatbot.service;

import chat.psychology_chatbot.domain.ChatMessage;
import chat.psychology_chatbot.domain.PsychologyData;
import chat.psychology_chatbot.repository.ChatMessageRepository;
import chat.psychology_chatbot.repository.PsychologyDataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final AIService aiService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatService(ChatMessageRepository chatMessageRepository, AIService aiService, SimpMessagingTemplate messagingTemplate) {
        this.chatMessageRepository = chatMessageRepository;
        this.aiService = aiService;
        this.messagingTemplate = messagingTemplate;
    }

    @Async
    public CompletableFuture<ChatMessage> processAndGetAIResponse(ChatMessage userMessage) {
        // 사용자 메시지를 MongoDB에 저장
        userMessage.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(userMessage);

        //DB에서 채팅방의 최근 대화 기록 조회
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "timestamp"));
        List<ChatMessage> recentMessages = chatMessageRepository.findByChatRoomIdOrderByTimestampDesc(userMessage.getChatRoomId(), pageable);
        Collections.reverse(recentMessages);

        // Gemini API를 호출해서 AI의 답변을 받아옴
        String aiResponseText = aiService.getAIResponse(recentMessages, userMessage.getMessage());

        // AI의 답변으로 챗봇 메시지를 생성하여 MongoDB에 저장
        ChatMessage botMessage = new ChatMessage();
        botMessage.setChatRoomId(userMessage.getChatRoomId());
        botMessage.setSenderName("챗봇");
        botMessage.setMessage(aiResponseText);
        botMessage.setTimestamp(LocalDateTime.now().plusNanos(1));
        chatMessageRepository.save(botMessage);

        return CompletableFuture.completedFuture(botMessage);
    }

    public List<ChatMessage> getMessages(String chatRoomId){
        return chatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
    }

    public void saveBotMessage(ChatMessage botMessage) {
        chatMessageRepository.save(botMessage);
    }
}
