package org.voidink.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voidink.api.models.Tatuador;
import org.voidink.api.repositories.TatuadorRepository;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/onboarding")
@CrossOrigin(origins = "*") // Permite peticiones de Expo Go en local y web externa
public class OnboardingController {

    @Autowired
    private TatuadorRepository tatuadorRepository;

    @PostMapping("/tatuador/{id}")
    public ResponseEntity<?> completarOnboarding(
            @PathVariable UUID id, 
            @RequestBody Tatuador datosOnboarding) {
        
        return tatuadorRepository.findById(id)
            .map(tatuador -> {
                // Actualizamos los datos del registro existente creado en el registro
                tatuador.setNombreArtistico(datosOnboarding.getNombreArtistico());
                tatuador.setNombreEstudio(datosOnboarding.getNombreEstudio());
                tatuador.setDireccionTexto(datosOnboarding.getDireccionTexto());
                tatuador.setInstagramUsername(datosOnboarding.getInstagramUsername());
                tatuador.setBiografia(datosOnboarding.getBiografia());
                tatuador.setEstilos(datosOnboarding.getEstilos());
                tatuador.setOnboardingCompleto(true); // Cambiamos el flag de control de la app

                Tatuador guardado = tatuadorRepository.save(tatuador);
                return ResponseEntity.ok(guardado);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}