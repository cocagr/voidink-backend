package org.voidink.api.models;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "tatuadores")
public class Tatuador {

    @Id
    private UUID id; // Enlazado directamente al auth.users.id de Supabase

    @Column(name = "nombre_artistico", nullable = false)
    private String nombreArtistico;

    @Column(name = "nombre_estudio", nullable = false)
    private String nombreEstudio;

    @Column(columnDefinition = "TEXT")
    private String biografia;

    @Column(name = "instagram_username")
    private String instagramUsername;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "direccion_texto", nullable = false)
    private String direccionTexto;

    @Column(name = "onboarding_completo")
    private Boolean onboardingCompleto = false;

    // ElementCollection para guardar los IDs de la tabla intermedia tatuadores_estilos de forma rápida
    @ElementCollection
    @CollectionTable(name = "tatuadores_estilos", joinColumns = @JoinColumn(name = "tatuador_id"))
    @Column(name = "estilo_id")
    private List<Long> estilos;

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNombreArtistico() { return nombreArtistico; }
    public void setNombreArtistico(String nombreArtistico) { this.nombreArtistico = nombreArtistico; }
    public String getNombreEstudio() { return nombreEstudio; }
    public void setNombreEstudio(String nombreEstudio) { this.nombreEstudio = nombreEstudio; }
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
    public String getInstagramUsername() { return instagramUsername; }
    public void setInstagramUsername(String instagramUsername) { this.instagramUsername = instagramUsername; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public String getDireccionTexto() { return direccionTexto; }
    public void setDireccionTexto(String direccionTexto) { this.direccionTexto = direccionTexto; }
    public Boolean getOnboardingCompleto() { return onboardingCompleto; }
    public void setOnboardingCompleto(Boolean onboardingCompleto) { this.onboardingCompleto = onboardingCompleto; }
    public List<Long> getEstilos() { return estilos; }
    public void setEstilos(List<Long> estilos) { this.estilos = estilos; }
}