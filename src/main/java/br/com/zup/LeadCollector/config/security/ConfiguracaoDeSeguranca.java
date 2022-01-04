package br.com.zup.LeadCollector.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration //Componetes especifico para essa configuração, mas que não deixa de ser um @Componente
@EnableWebSecurity //serve para marcar que as configurações dessa classe serão as validas em cima das originais
//As configurações já existem o que queremos é subscrever as seguranças extendo a classe que tras essas config.

public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {

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
    }

    //confiração de rota de dominio - de qual dominio ele pode receber requisições do front.
    //Ele deve ser um Bean
    //"/** = qualquer dominio
    @Bean
    CorsConfigurationSource confirgurarCORS(){

        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return cors;

        //a string pattern não pode ser um array como metodo acima, não recebe no lugar do dominio.
    }
}
