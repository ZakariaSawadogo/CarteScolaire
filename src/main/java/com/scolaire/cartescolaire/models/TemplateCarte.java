package com.scolaire.cartescolaire.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un projet global de confection contenant les designs visuels et leurs champs associés.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateCarte {
    private int id;
    private String nomProjet;
    private String cheminImageRecto;
    private String cheminImageVerso;
    private double largeurImage;
    private double hauteurImage;

    @Builder.Default
    private List<ChampCarte> champs = new ArrayList<>();
}