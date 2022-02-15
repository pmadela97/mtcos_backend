package com.security.jwt;

import com.user.application.UserApplicationService;
import com.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenComponent {

    private final String secretKey = "secretKey";
    private final long  jwtExpirationTime = 60 * 20 * 1000;

    private UserApplicationService userApplicationService;


    @Autowired
    public JwtTokenComponent(UserApplicationService userApplicationService) {

        Assert.notNull(userApplicationService, "userService must be not null");

        this.userApplicationService = userApplicationService;
    }


    private Claims getClaimFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();

    }

    public String generateToken(UserDetails userDetails) {

        User user = (User) userApplicationService.loadUserByUsername(userDetails.getUsername());
        Map<String,Object> claims = new HashMap<>();

        claims.put("type",user.getUserRole());
        claims.put("userId",user.getId());

        return doGenerateToken(claims,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(jwtExpirationTime + System.currentTimeMillis()+30*60*1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token){

        return getClaimFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {

        final Date expDate = getExpirationDateFromToken(token);

        return expDate.before(new Date());
    }

}
