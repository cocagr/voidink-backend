package org.voidink.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.voidink.api.models.Tatuador;
import java.util.UUID;

public interface TatuadorRepository extends JpaRepository<Tatuador, UUID> {

    // Consulta nativa para actualizar las coordenadas tipo geography (SRID 4326 estándar global)
    @Modifying
    @Transactional
    @Query(value = "UPDATE tatuadores SET coordenadas = ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography WHERE id = :id", nativeQuery = true)
    void actualizarCoordenadasPostGIS(@Param("id") UUID id, @Param("lat") Double lat, @Param("lng") Double lng);
}