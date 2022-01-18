package com.test.prueba.dao;

import com.test.prueba.entidades.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaDao extends CrudRepository<Persona, Long>{
    
}
