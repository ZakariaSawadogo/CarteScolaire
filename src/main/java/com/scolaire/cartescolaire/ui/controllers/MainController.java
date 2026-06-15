package com.scolaire.cartescolaire.ui.controllers;

import com.scolaire.cartescolaire.ui.components.ResizableField;
import com.scolaire.cartescolaire.ui.handlers.WorkspaceDrawHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainController {

    @FXML private Pane drawingPane;
    @FXML private ImageView templateImageView;

    private WorkspaceDrawHandler drawHandler;

    @FXML
    public void initialize() {
        drawingPane.setPrefSize(800, 500);

        // Injection de dépendance basique et passage de la méthode de callback
        drawHandler = new WorkspaceDrawHandler(drawingPane, this::onNouveauChampTrace);

        // On active le mode dessin par défaut pour le moment
        drawHandler.activerModeDessin();
    }

    /**
     * Méthode appelée automatiquement par le Handler quand l'utilisateur finit de tracer.
     */
    private void onNouveauChampTrace(ResizableField nouveauChamp) {
        System.out.println("Nouveau champ enregistré par le Controller !");
        System.out.println("Dimensions : " + nouveauChamp.getWidth() + "x" + nouveauChamp.getHeight());

        // Plus tard, c'est ici que nous instancierons notre modèle de données (ChampCarte)
        // pour lier cet objet visuel à la base de données.
    }
}