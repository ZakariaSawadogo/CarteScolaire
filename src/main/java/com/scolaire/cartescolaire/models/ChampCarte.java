package com.scolaire.cartescolaire.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une zone de fusion de données (Texte ou Image) positionnée sur un template de carte.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampCarte {
    private int id;
    private int templateId;
    private String nomColonne;
    private TypeChamp type;

    private double x;
    private double y;
    private double largeur;
    private double hauteur;

    private String nomPolice;
    private int taillePolice;
    private String couleurHex;
}