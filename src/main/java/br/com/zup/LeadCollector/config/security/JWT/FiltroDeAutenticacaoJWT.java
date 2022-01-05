package br.com.zup.LeadCollector.config.security.JWT;

import br.com.zup.LeadCollector.config.security.JWT.excepetion.AcessoNegadoExcepetion;
import br.com.zup.LeadCollector.usuario.dtos.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.imageio.IIOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class FiltroDeAutenticacaoJWT extends UsernamePasswordAuthenticationFilter {
    private JWTComponent jwtComponent;
    private AuthenticationManager authenticationManager; //serve para fazer autenticação

    public FiltroDeAutenticacaoJWT(JWTComponent jwtComponent, AuthenticationManager authenticationManager) {
        this.jwtComponent = jwtComponent;
        this.authenticationManager = authenticationManager;
    }

    @Override //aqui que vai ocorrer as comparações com as informações em banco de dados
    //caso contrario não permite acesso, não vai autenticar.
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        ObjectMapper objectMapper =  new ObjectMapper();

        try{
            LoginDTO login = objectMapper.readValue(request.getInputStream(), LoginDTO.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    login.getEmail(), login.getSenha(), new ArrayList<>()
            );

            Authentication autenticacao  = authenticationManager.authenticate(authToken);

            return autenticacao;

        }catch (IOException e){
            throw new AcessoNegadoExcepetion();
        }
    }
}
