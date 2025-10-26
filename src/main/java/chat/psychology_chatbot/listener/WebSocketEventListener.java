package chat.psychology_chatbot.listener;

import chat.psychology_chatbot.domain.ChatMessage;
import chat.psychology_chatbot.service.ChatService;
// import org.slf4j.ILoggerFactory; // 이 import는 필요 없습니다.
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders; // MessageHeaders import 추가
import org.springframework.messaging.simp.SimpMessageHeaderAccessor; // SimpMessageHeaderAccessor import 추가
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.List; // List import 추가
import java.util.Map; // Map import 추가

@Component
public class WebSocketEventListener {
    private static final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);
//    private final SimpMessagingTemplate messagingTemplate;
//    private final ChatService chatService;


    @EventListener
    public void handleListener(SessionConnectedEvent event) { // 메서드 이름 유지
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info(">>>> 1. 새로운 WebSocket 연결 감지됨: {}", headerAccessor.getSessionId());

        // --- 헤더 읽는 방식 변경 ---
    }

    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent event) { // 메서드 이름 유지
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("WebSocket 연결 해제됨: {}", headerAccessor.getSessionId());
    }
}