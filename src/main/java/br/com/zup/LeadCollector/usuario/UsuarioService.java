package br.com.zup.LeadCollector.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Usuario salvarUsuario(Usuario usuario){
      // metodo usado para criptografia de senha
        String senhaEscondida = encoder.encode(usuario.getSenha());

        usuario.setSenha(senhaEscondida);
        return usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(Usuario usuario, UUID id){
        Optional<Usuario>usuarioOptional = usuarioRepository.findById(id);

        if(usuarioOptional.isEmpty()){
            throw new RuntimeException("Usuário não Existe");
        }

        Usuario usuarioBanco = usuarioOptional.get();
        if(!usuarioBanco.getEmail().equals(usuario.getEmail())){
            usuarioBanco.setEmail(usuario.getEmail());
        }
        String senhaEscondida = encoder.encode(usuario.getSenha());
        usuarioBanco.setSenha(senhaEscondida);
        usuarioRepository.save(usuarioBanco);
    }

}
