package com.scolaire.cartescolaire.ui.handlers;

import com.scolaire.cartescolaire.models.TypeChamp;
import com.scolaire.cartescolaire.ui.components.ResizableField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Setter;

import java.util.function.Consumer;

/**
 * Intercepte les événements de la souris sur le plan de travail pour créer de nouveaux champs.
 */
public class WorkspaceDrawHandler {

    private final Pane workspace;
    private final Consumer<ResizableField> onFieldCreated;

    @Setter
    private OutilUI outilActif = OutilUI.SELECTION;

    private double startX;
    private double startY;
    private ResizableField currentField;

    /**
     * Initialise le gestionnaire de tracé.
     *
     * @param workspace      Le conteneur sur lequel la souris est écoutée.
     * @param onFieldCreated Callback exécuté lorsqu'une zone valide est terminée.
     */
    public WorkspaceDrawHandler(Pane workspace, Consumer<ResizableField> onFieldCreated) {
        this.workspace = workspace;
        this.onFieldCreated = onFieldCreated;
    }

    public void activerModeDessin() {
        workspace.setOnMousePressed(this::onMousePressed);
        workspace.setOnMouseDragged(this::onMouseDragged);
        workspace.setOnMouseReleased(this::onMouseReleased);
    }

    public void desactiverModeDessin() {
        workspace.setOnMousePressed(null);
        workspace.setOnMouseDragged(null);
        workspace.setOnMouseReleased(null);
    }

    private void onMousePressed(MouseEvent event) {
        if (outilActif == OutilUI.SELECTION) return;

        startX = event.getX();
        startY = event.getY();

        TypeChamp typeDuChamp = (outilActif == OutilUI.TRACER_TEXTE) ? TypeChamp.TEXTE : TypeChamp.IMAGE_PROFIL;
        currentField = new ResizableField(startX, startY, typeDuChamp);

        if (outilActif == OutilUI.TRACER_TEXTE) {
            currentField.setStroke(Color.web("#3b82f6"));
            currentField.setFill(Color.rgb(59, 130, 246, 0.2));
        } else if (outilActif == OutilUI.TRACER_IMAGE) {
            currentField.setStroke(Color.web("#f97316"));
            currentField.setFill(Color.rgb(249, 115, 22, 0.2));
        }

        workspace.getChildren().add(currentField);
    }

    private void onMouseDragged(MouseEvent event) {
        if (currentField == null || outilActif == OutilUI.SELECTION) return;

        double currentX = event.getX();
        double currentY = event.getY();

        currentField.setX(Math.min(startX, currentX));
        currentField.setY(Math.min(startY, currentY));
        currentField.setWidth(Math.abs(currentX - startX));
        currentField.setHeight(Math.abs(currentY - startY));
    }

    private void onMouseReleased(MouseEvent event) {
        if (currentField == null || outilActif == OutilUI.SELECTION) return;

        if (currentField.getWidth() < 15 || currentField.getHeight() < 15) {
            workspace.getChildren().remove(currentField);
        } else if (onFieldCreated != null) {
            onFieldCreated.accept(currentField);
        }
        currentField = null;
    }
}