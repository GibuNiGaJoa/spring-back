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
        Date expiration = new Date(now.getTime() + (30 * 60 * 1000L)); // 만료기간 30분

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


    //==Jwt 토큰의 유효성 체크 메소드==//
    public Long parseJwtToken(String token) {
        String findToken = BearerRemove(token);// Bearer 제거
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(findToken) //token을 가지고 풀어주기=해석하기
                .getBody().get("id", Long.class);
    }

    //==토큰 앞 부분('Bearer') 제거 메소드==//
    private String BearerRemove(String token) {
        return token.substring("Bearer ".length());
    }
}
