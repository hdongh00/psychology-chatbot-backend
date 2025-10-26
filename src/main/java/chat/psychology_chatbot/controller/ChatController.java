package chat.psychology_chatbot.controller;

import chat.psychology_chatbot.domain.ChatMessage;
import chat.psychology_chatbot.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping("/messages")
    public ResponseEntity<ChatMessage> sendChatMessage(@RequestBody ChatMessage message, Authentication authentication){
        //토큰에서 사용자 이름 추출
        String senderName = authentication.getName();
        message.setSenderName(senderName);

        chatService.processAndGetAIResponse(message);

        return ResponseEntity.ok(message);
    }
    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable String chatRoomId){
        return ResponseEntity.ok(chatService.getMessages(chatRoomId));
    }
}
