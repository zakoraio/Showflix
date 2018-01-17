package com.showflix.app.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
 
/**
 *
 * @author Saurabh Rai
 */
public class AuthenticationUtil {
    
    public static final String tokenHeader = "authentication-token";
    
    public static boolean hasAuthenticationToken(HttpServletRequest context) {
        System.out.println(context.getHeader(tokenHeader));
        if( context.getHeader(tokenHeader) == null || !context.getHeader(tokenHeader).startsWith("Bearer ")) {
            return false;
        }
        return true;
    }
    
    public static Claims parseToken(HttpServletRequest context, String applicationSecret) {
    	Claims claims  = null;
    	 final String token = context.getHeader(tokenHeader).substring(7); // The part after "Bearer "
    	 try{
         claims = Jwts.parser()
                    .setSigningKey(applicationSecret)
                    .parseClaimsJws(token)
                    .getBody();
    	 }
    	 catch(UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
    		e.printStackTrace();
    	 }
    	 catch (Exception e){
    		 e.printStackTrace();
    	 }
    	 return claims;
    }
    
    public static boolean isExpired(Claims claims) {
        Date expiresOn = claims.getExpiration();
        return new Date().getTime() > expiresOn.getTime();
    }
    
    public static String generateToken(String userName, String role, String applicationSecret) {
        long ONE_MINUTE_IN_MILLIS=60000;
        long now = new Date().getTime();
        Date expires = new Date(now + (10 * ONE_MINUTE_IN_MILLIS));
        String token = Jwts.builder()
        		.setSubject(userName)
                .setExpiration(expires).claim("role", role)
                .signWith(SignatureAlgorithm.HS256, applicationSecret)
                .compact();
        return token;
    }
}
