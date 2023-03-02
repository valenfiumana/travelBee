package com.example.travelbee.security.jwt;

import com.example.travelbee.security.entities.UserEntity;
import io.jsonwebtoken.*;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Clase que genera el token y valida que este bien formado y no expirado
//Tiene 3 responsabilidades:
//1. Genera un JWT
//2. Extrae la identidad y los claims de autorización de los Access token y los usa para crear el UserContext
//3. Valida el JWT
@Component
public class JwtUtil {
    private String SECRET_KEY = "secret";

    //CLAIMS
    //Los claims son info del usuario que fue empaquetada
    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    public Date getExpiration(String token) {
        return getClaimDate(token);
    }
    public Date getClaimDate(String token){
        Claims claims =getAllClaims(token);
        return claims.getExpiration();
    }

    public String getClaimUsername(String token){
        Claims claims = getAllClaims(token);
        return claims.getSubject();
    }
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    // GENERACION TOKEN
    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getFirstName());
        claims.put("lastname", user.getLastName());
        claims.put("role", user.getRole().getId());
        return createToken(claims, user.getUsername());
    }

    //el metodo builder nos permite crear un token al que le pasamos el usuario, le asidnamos una fecha de expiracion y el algoritmo de encriptacion
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 18000000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    //AUTENTICACIÓN ó VALIDACIÓN TOKEN
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getClaimUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}