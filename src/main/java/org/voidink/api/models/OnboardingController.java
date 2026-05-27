package org.voidink.api.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voidink.api.models.Tatuador;
import org.voidink.api.repositories.TatuadorRepository;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/onboarding")
@CrossOrigin(origins = "*")
public class OnboardingController {

    @Autowired
    private TatuadorRepository tatuadorRepository;

    // DTO con mapeo explícito de propiedades JSON para que coincida con el Frontend
    public static class OnboardingDTO {
        @JsonProperty("nombre_artistico")
        public String nombreArtistico;

        @JsonProperty("nombre_estudio")
        public String nombreEstudio;

        @JsonProperty("direccion_texto")
        public String direccionTexto;

        @JsonProperty("instagram_username")
        public String instagramUsername;

        @JsonProperty("biografia")
        public String biografia;

        @JsonProperty("estilos")
        public List<Long> estilos;

        @JsonProperty("latitud")
        public Double latitud;

        @JsonProperty("longitud")
        public Double longitud;
    }

    @PostMapping("/tatuador/{id}")
    public ResponseEntity<?> completarOnboarding(
            @PathVariable UUID id, 
            @RequestBody OnboardingDTO dto) {
        
        return tatuadorRepository.findById(id)
            .map(tatuador -> {
                // 1. Asignar los valores recibidos del DTO mapeado
                tatuador.setNombreArtistico(dto.nombreArtistico);
                tatuador.setNombreEstudio(dto.nombreEstudio);
                tatuador.setDireccionTexto(dto.direccionTexto);
                tatuador.setInstagramUsername(dto.instagramUsername);
                tatuador.setBiografia(dto.biografia);
                tatuador.setEstilos(dto.estilos);
                tatuador.setOnboardingCompleto(true);
                
                Tatuador guardado = tatuadorRepository.save(tatuador);

                // 2. Ejecutar la actualización PostGIS si hay coordenadas reales
                if (dto.latitud != null && dto.longitud != null) {
                    tatuadorRepository.actualizarCoordenadasPostGIS(id, dto.latitud, dto.longitud);
                }

                return ResponseEntity.ok(guardado);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}