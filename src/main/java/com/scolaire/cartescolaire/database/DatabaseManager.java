package com.scolaire.cartescolaire.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gère la connexion et l'initialisation de la base de données SQLite embarquée.
 */
public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:cartescolaire.db";

    /**
     * Établit une connexion avec la base de données.
     *
     * @return L'objet Connection actif.
     * @throws SQLException Si la connexion à la base de données échoue.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Initialise le schéma de la base de données.
     * Crée les tables nécessaires (templates, champs) si elles n'existent pas encore.
     */
    public static void initialiserBaseDeDonnees() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON;");

            String sqlTemplates = "CREATE TABLE IF NOT EXISTS templates (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nom_projet TEXT NOT NULL," +
                    "chemin_recto TEXT," +
                    "chemin_verso TEXT," +
                    "largeur_image REAL," +
                    "hauteur_image REAL" +
                    ");";
            stmt.execute(sqlTemplates);

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

        } catch (SQLException e) {
            System.err.println("Erreur d'initialisation DB : " + e.getMessage());
        }
    }
}