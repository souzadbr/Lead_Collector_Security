package br.com.zup.LeadCollector.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration //Componetes especifico para essa configuração, mas que não deixa de ser um @Componente
@EnableWebSecurity //serve para marcar que as configurações dessa classe serão as validas em cima das originais
//As configurações já existem o que queremos é subscrever as seguranças extendo a classe que tras essas config.

public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {

}
