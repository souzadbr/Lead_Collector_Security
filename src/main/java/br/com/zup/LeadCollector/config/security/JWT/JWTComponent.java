package br.com.zup.LeadCollector.config.security.JWT;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTComponent {
    @Value("${jwt.segrego}")
    private String segredo; //tem que ficar escondido
    @Value("${jwt.milisegundo}")
    private Long milissegundo;

    public String gerarToken(String username, UUID id){
        Date vencimento = new Date(System.currentTimeMillis()+milissegundo); //gerando data de vencimento token

        String token = Jwts.builder().setSubject(username)
                .claim("idUsuario", id).setExpiration(vencimento)
                .signWith(SignatureAlgorithm.HS512,segredo.getBytes()).compact();

        return token;
    }
}
