package springBoot.study.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MalformedKeyException;
import io.jsonwebtoken.security.UnsupportedKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import springBoot.study.blog.exception.BlogAPIException;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String secret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long expiration;

    public String createToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getKey())
                .compact();
        return token;
    }

    private Key getKey() {
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)) ;
    }

    public String getUsernameFromToken(String token) {
      return   Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
       try {
           Jwts.parser()
                   .verifyWith((SecretKey) getKey())
                   .build().parse(token);
           return true;
       }
       catch (MalformedKeyException malformedKeyException) {
           throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Invalid JWT token");
       }catch (ExpiredJwtException expiredJwtException){
           throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Expired JWT token");
       }catch (IllegalArgumentException illegalArgumentException){
           throw new BlogAPIException(HttpStatus.BAD_REQUEST , " JWT claims is null or empty");
       }catch (UnsupportedJwtException unsupportedJwtException){
           throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Unsupported JWT token");
       }


    }

}
