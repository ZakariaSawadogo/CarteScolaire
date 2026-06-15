package com.scolaire.cartescolaire.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Représente un étudiant dont les données seront fusionnées sur les cartes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etudiant {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private String cheminPhotoProfil;

    @Builder.Default
    private Map<String, String> donneesSupplementaires = new HashMap<>();
}