package br.com.zup.LeadCollector.config.security.JWT.excepetion;

import br.com.zup.LeadCollector.config.security.JWT.JWTComponent;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class FiltroDeAutorizacaoJWT extends BasicAuthenticationFilter {

    private JWTComponent jwtComponent;
    private UserDetailsService userDetailsService;//inversão de controle -
    // dependo de uma abstração que implemta o metodo


    public FiltroDeAutorizacaoJWT(AuthenticationManager authenticationManager,
                                  JWTComponent jwtComponent, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtComponent = jwtComponent;
        this.userDetailsService = userDetailsService;
    }

    public UsernamePasswordAuthenticationToken pegarAutenticacao(String token ){
        if(!jwtComponent.tokenValido(token)){
            throw new TokenInvalidoException();
        }
        Claims claims = jwtComponent.pegarClaims(token);
        UserDetails usuarioLogado = userDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(usuarioLogado, null, usuarioLogado.getAuthorities());
    }

}
