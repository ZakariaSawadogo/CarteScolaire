package com.scolaire.cartescolaire.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampCarte {
    private int id;
    private int templateId; // Clé étrangère vers le Template

    private String nomColonne; // Ex: "Nom", "Matricule" (Fera le lien avec le fichier Excel)
    private TypeChamp type;

    // Coordonnées spatiales (Position et dimensions sur l'image)
    private double x;
    private double y;
    private double largeur;
    private double hauteur;

    // Propriétés de style (spécifiques au texte)
    private String nomPolice;
    private int taillePolice;
    private String couleurHex; // Ex: "#000000"
}