package com.relgs.mongodb.repositories;

import com.relgs.mongodb.models.Perfil;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PerfilRepository extends MongoRepository<Perfil, String> {
}
