package com.scolaire.cartescolaire.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    // Chemin vers le fichier local SQLite. Il sera créé à la racine du projet.
    private static final String URL = "jdbc:sqlite:cartescolaire.db";

    /**
     * Établit la connexion avec la base de données SQLite.
     * @return L'objet Connection
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Initialise la base de données en créant les tables si elles n'existent pas.
     */
    public static void initialiserBaseDeDonnees() {
        // Le bloc try-with-resources ferme automatiquement la connexion et le statement à la fin
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Activer les contraintes de clés étrangères (désactivées par défaut sur SQLite)
            stmt.execute("PRAGMA foreign_keys = ON;");

            // 1. Création de la table Templates
            String sqlTemplates = "CREATE TABLE IF NOT EXISTS templates (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nom_projet TEXT NOT NULL," +
                    "chemin_recto TEXT," +
                    "chemin_verso TEXT," +
                    "largeur_image REAL," +
                    "hauteur_image REAL" +
                    ");";
            stmt.execute(sqlTemplates);

            // 2. Création de la table Champs (Liée à un template)
            String sqlChamps = "CREATE TABLE IF NOT EXISTS champs (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "template_id INTEGER," +
                    "nom_colonne TEXT," +
                    "type TEXT," +
                    "x REAL," +
                    "y REAL," +
                    "largeur REAL," +
                    "hauteur REAL," +
                    "nom_police TEXT," +
                    "taille_police INTEGER," +
                    "couleur_hex TEXT," +
                    "FOREIGN KEY(template_id) REFERENCES templates(id) ON DELETE CASCADE" +
                    ");";
            stmt.execute(sqlChamps);

            System.out.println("Base de données SQLite initialisée avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la base de données : " + e.getMessage());
        }
    }
}