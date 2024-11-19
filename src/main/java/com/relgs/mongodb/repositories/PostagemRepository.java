package com.relgs.mongodb.repositories;

import com.relgs.mongodb.models.Perfil;
import com.relgs.mongodb.models.Postagem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostagemRepository extends MongoRepository<Postagem, String> {
}
