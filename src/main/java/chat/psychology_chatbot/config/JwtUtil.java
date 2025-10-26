package chat.psychology_chatbot.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String secret = "yourVeryLongAndSecureSecretKeyForHS512AlgorithmThatIsAtLeast64BytesLongAndRandomlyGenerated";

    //유효시간
    private final long expiration = 30 * 60 * 1000L;

    //이름 기반 토큰 생성
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username) //토큰 주인
                .setIssuedAt(new Date(System.currentTimeMillis())) //토큰 발급
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) //만료 시간
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //유효성 검사
    public String getUsernameToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    //토큰 만료 확인
    public boolean isTokenExpired(String token){
        Date expiration = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

    // 종합적 검사
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !isTokenExpired(token);
        }catch (Exception e){
            return false;
        }
    }
}
