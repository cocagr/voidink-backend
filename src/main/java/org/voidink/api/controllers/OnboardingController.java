package org.voidink.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voidink.api.models.Tatuador;
import org.voidink.api.repositories.TatuadorRepository;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/onboarding")
@CrossOrigin(
    origins = "*", 
    allowedHeaders = "*", 
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
public class OnboardingController {

    @Autowired
    private TatuadorRepository tatuadorRepository;

    // DTO para mapear el payload exacto del JSON que manda React Native
    public static class OnboardingRequest {
        public Tatuador tatuador;
        public Double latitud;
        public Double longitud;
    }

    @PostMapping("/tatuador/{id}")
    public ResponseEntity<?> completarOnboarding(
            @PathVariable UUID id, 
            @RequestBody OnboardingRequest request) {
        
        return tatuadorRepository.findById(id)
            .map(tatuador -> {
                Tatuador datos = request.tatuador;
                
                // 1. Guardar la información convencional
                tatuador.setNombreArtistico(datos.getNombreArtistico());
                tatuador.setNombreEstudio(datos.getNombreEstudio());
                tatuador.setDireccionTexto(datos.getDireccionTexto());
                tatuador.setInstagramUsername(datos.getInstagramUsername());
                tatuador.setBiografia(datos.getBiografia());
                tatuador.setEstilos(datos.getEstilos());
                tatuador.setOnboardingCompleto(true);
                
                Tatuador guardado = tatuadorRepository.save(tatuador);

                // 2. Si vienen coordenadas válidas de la Edge Function, impactamos PostGIS
                if (request.latitud != null && request.longitud != null) {
                    tatuadorRepository.actualizarCoordenadasPostGIS(id, request.latitud, request.longitud);
                }

                return ResponseEntity.ok(guardado);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}