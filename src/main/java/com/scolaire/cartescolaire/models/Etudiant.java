package com.scolaire.cartescolaire.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etudiant {
    private int id;
    private String matricule; // Souvent utilisé comme identifiant unique
    private String nom;
    private String prenom;
    private String cheminPhotoProfil; // Chemin absolu vers la photo sur le disque

    // Pour rendre le logiciel évolutif : on stocke les colonnes Excel supplémentaires
    // (ex: "Classe", "Date Naissance", "Groupe Sanguin") dans une Map dynamique.
    @Builder.Default
    private Map<String, String> donneesSupplementaires = new HashMap<>();
}