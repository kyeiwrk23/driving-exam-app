package com.kay.driving_exam_app.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwt_secretKey}")
    private  String jwt_secretKey;
    @Value("${spring.app.jwt_expiration}")
    private  int jwt_expiration;

    @Value("${spring.app.jwtCookieName}")
    private String jwtCookieName;

//    public String JwtFromTokenHeader(HttpServletRequest request) {
//        String header = request.getHeader("Authorization");
//        if(header != null &&  header.startsWith("Bearer ")) {
//            return header.substring(7);
//        }
//        return null;
//    }

    public String generateJwtTokenFromCookie(HttpServletRequest Request) {
        Cookie cookie = WebUtils.getCookie(Request,jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        }else
            return null;
    }

    public ResponseCookie generateJwtCookie(UserDetails userDetails) {
        String jwt = generateTokenFromUsername(userDetails.getUsername());

        ResponseCookie cookie = ResponseCookie.from(jwtCookieName,jwt)
                .path("/api")
                .maxAge(24*60*60)
                .httpOnly(false)
                .build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName,null)
                .path("/api")
                .build();
        return cookie;
    }

    public String generateTokenFromUsername(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .signWith(getKey())
                .expiration(new Date(new Date().getTime() + jwt_expiration))
                .compact();
    }

    public String generateUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isValidatedToken(String token){
        try {
            logger.debug("Validating Token ....");
            Jwts.parser()
                    .verifyWith((SecretKey) getKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (MalformedJwtException | IllegalArgumentException e){
            logger.error("Invalid Token {}",e.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("Expired Token {}",e.getMessage());
        }catch(UnsupportedJwtException e){
            logger.error("Unsupported Token {}",e.getMessage());
        }
        return false;
    }

    public Key getKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwt_secretKey));
    }


}
