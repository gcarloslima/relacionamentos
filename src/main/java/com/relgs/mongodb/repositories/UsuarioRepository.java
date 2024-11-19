package com.relgs.mongodb.repositories;

import com.relgs.mongodb.models.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
