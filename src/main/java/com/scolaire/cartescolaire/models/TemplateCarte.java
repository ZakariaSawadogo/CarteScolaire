package com.scolaire.cartescolaire.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateCarte {
    private int id;
    private String nomProjet; // Ex: "Cartes 2024-2025"

    // Chemins absolus vers les images locales (C:/.../template_recto.png)
    private String cheminImageRecto;
    private String cheminImageVerso;

    // Dimensions originales de l'image pour garder les proportions
    private double largeurImage;
    private double hauteurImage;

    @Builder.Default
    private List<ChampCarte> champs = new ArrayList<>();
}