package br.com.zup.LeadCollector.config.security.JWT;


import br.com.zup.LeadCollector.config.security.JWT.excepetion.TokenInvalidoException;
import io.jsonwebtoken.Claims;
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
    //claims é a informação dentro do token em geral o corpo.
    public Claims pegarClaims(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(segredo.getBytes()).parseClaimsJws(token).getBody();
            //essa linha descriptografa o token
            //parser = ele descritografa o token e transforma em uma clase descriptografada para gerar os claims
            return claims;
        }catch (Exception exception){
            throw new TokenInvalidoException();
        }
    }

    public boolean tokenValido(String token){
        try{
            Claims claims = pegarClaims(token);
            Date dataAtual = new Date(System.currentTimeMillis());

            String username = claims.getSubject();
            Date vencimentoToken = claims.getExpiration();
            if(username != null && vencimentoToken != null && dataAtual.before(vencimentoToken)){
                return true;
            }else {
                return false;
            }
        }catch (TokenInvalidoException e){
            return false;
        }
    }
}
