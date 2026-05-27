package org.voidink.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.voidink.api.models.Tatuador;

public interface TatuadorRepository extends JpaRepository<Tatuador, UUID> {
    // Spring Boot generará automáticamente los métodos save(), findById(), etc.
}