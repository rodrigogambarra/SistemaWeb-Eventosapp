package com.eventosapp.eventosapp.repository;

import com.eventosapp.eventosapp.models.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    Usuario findByLogin(String login);
}
