package br.com.zup.LeadCollector.config.security;

import br.com.zup.LeadCollector.config.security.JWT.FiltroDeAutenticacaoJWT;
import br.com.zup.LeadCollector.config.security.JWT.JWTComponent;
import br.com.zup.LeadCollector.config.security.JWT.excepetion.FiltroDeAutorizacaoJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration //Componetes especifico para essa configuração, mas que não deixa de ser um @Componente
@EnableWebSecurity //serve para marcar que as configurações dessa classe serão as validas em cima das originais
//As configurações já existem o que queremos é subscrever as seguranças extendo a classe que tras essas config.

public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTComponent jwtComponent;
    @Autowired
    private UserDetailsService userDetailsService;


    //todas as requisições passam pelo mesmo metodos abaixo
    //retirasse o super pois ele significa a configuração original e não queremos ela.

    //esse http que recebemos como paramentro verificamos se é eprmitido ou não o acesso.

    // http.authorizeRequests() autorizar requisição antiMatchs (quando for igual a). o que será permitido.


    //metodo usado para substituir lista de endpoints e deixar metodo mais limpo.
    private static final String[] ENDPOINT_POST_PUBLICO = {
            "/leads",
            "/usuario"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); //desabilita o token que protege desse tipo de ataque, pois não precisamos.
        //front se preocupa com isso
        http.cors().configurationSource(confirgurarCORS());
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, ENDPOINT_POST_PUBLICO).permitAll()
                .anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//não salva a sessão do usuario.
        //carrega via token e é mais seguro em alguns momentos.

        http.addFilter(new FiltroDeAutenticacaoJWT(jwtComponent, authenticationManager()));
        http.addFilter(new FiltroDeAutorizacaoJWT(authenticationManager(), jwtComponent, userDetailsService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    //confiração de rota de dominio - de qual dominio ele pode receber requisições do front.
    //Ele deve ser um Bean
    //"/** = qualquer dominio
    @Bean
    CorsConfigurationSource confirgurarCORS() {

        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return cors;

        //a string pattern não pode ser um array como metodo acima, não recebe no lugar do dominio.
        //cors = cross origin = sincronizar a origem das requisções
    }

    @Bean
    //faz parte do metodo de criptografia de senha
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
