package kakao.valuetogether.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    private String secretKey = "askldfjwfkjasldfjqjdasljfklhqlkjasdfkjqnalskjdfwosdjfqalsjkhfqisahf";

    //==토큰 생성 메소드==//
    public String createToken(Long id) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + (1 * 60 * 1000L)); // 만료기간 30분

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                //.setIssuer("test") // 토큰발급자(iss)
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(expiration) // 만료시간(exp)
                //.setSubject(subject) //  토큰 제목(subject)
                .claim("id",id)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes())) // 알고리즘, 시크릿 키
                .compact();
    }

    public Long parseJwtToken(String token) {
        String findToken = BearerRemove(token);
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(findToken)
                .getBody().get("id", Long.class);
    }

    private String BearerRemove(String token) {
        return token.substring("Bearer ".length());
    }
}
