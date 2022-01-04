package br.com.zup.LeadCollector.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

//classeUserDetalis obriga a implmentar alguns metodos e os nomes dos metodos ja dizem o que eles fazem.

public class UsuarioLogado implements UserDetails {

    private UUID id;
    private String email;
    private String senha;

    public UsuarioLogado(UUID id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public UsuarioLogado() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override //significa quais são as autorizações do usuario / tipo usuario ..administrador ou comum etc.
    //manter como nulo pois não estamos implementando.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override //subscrever esse metodo para ser usado pelo security para comparar a senha com o banco de dados.
    public String getPassword() {
        return senha;
    }

    @Override // tbm comparar para saber se o usuario já está no banco de dados
    public String getUsername() {
        return email;
    }

    @Override //se a conta não espira ..trocar para true para a conta não estar expirada
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // se a conta não está travada
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override //credencial não está expirada
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override //não está inativa
    public boolean isEnabled() {
        return true;
    }
}
