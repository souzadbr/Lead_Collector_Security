package br.com.zup.LeadCollector.config.security.JWT.excepetion;

import br.com.zup.LeadCollector.config.security.JWT.JWTComponent;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @Override //ele vai receber a requisicão e a resposta junto com o chaim ...
    // filter por onde as requisições e respostas pasdsam o tempo todo
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
       String token = request.getHeader("Authorization");//autorizando a passagem do usuario dentro do cabeçalho da requisição

        //se token diferente de nulo e token começa com token no nome padrão feito no filtro de autorização
        if (token != null && token.startsWith("Token")){
            try{
                //token.substring(6) retira 6 caracteres do inicio do topke que é o nome dele "token
                // + espaço concatenado antes do valor a ser descriptogrfado"
                UsernamePasswordAuthenticationToken auth = pegarAutenticacao(token.substring(6));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch(TokenInvalidoException exception){
               response.sendError(HttpStatus.FORBIDDEN.value());
            }
        }
        chain.doFilter(request, response);
    }


}
