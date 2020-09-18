package cn.org.zmabel.admin.util;

import cn.org.zmabel.admin.entity.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtTokenUtil implements Serializable {

    @Value("${jwt.expire}")
    private Long EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.prefix}")
    private String TOKEN_PREFIX;

    public final String HEADER_STRING = "Authorization";

    public String getTokenPrefix() {
        return this.TOKEN_PREFIX;
    }

    public String generateToken(Map<String, Object> claims) {
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims);
        long currentTimeMillis = System.currentTimeMillis();
        jwtBuilder.setExpiration(new Date(currentTimeMillis + EXPIRATION_TIME * 1000L));
        SecretKey secretKey = getSecretKey();
        jwtBuilder.signWith(secretKey);
        return jwtBuilder.compact();
    }

    public Claims getClaimsFromToken(HttpServletRequest request) {
        Claims claims;
        String token = request.getHeader(HEADER_STRING);
        SecretKey secretKey = getSecretKey();
        try {
            claims = Jwts.parserBuilder().setAllowedClockSkewSeconds(180L).setSigningKey(secretKey).build().parseClaimsJws(
                    token.replace(TOKEN_PREFIX, "")).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims;
        SecretKey secretKey = getSecretKey();
        try {
            claims = Jwts.parserBuilder().setAllowedClockSkewSeconds(180L).setSigningKey(secretKey).build().parseClaimsJws(
                    token.replace(TOKEN_PREFIX, "")).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private SecretKey getSecretKey() {
        byte[] encodeKey = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(encodeKey);
    }

    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("username", userDetails.getUsername());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            Date now = new Date();
            return expiration.before(now);
        } catch (Exception e) {
            return false;
        }
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, User user) {
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
}
