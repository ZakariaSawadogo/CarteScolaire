package com.scolaire.cartescolaire.ui.handlers;

import com.scolaire.cartescolaire.ui.components.ResizableField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.function.Consumer;

public class WorkspaceDrawHandler {

    private final Pane workspace;
    private final Consumer<ResizableField> onFieldCreated;

    private double startX;
    private double startY;
    private ResizableField currentField;

    /**
     * @param workspace Le panneau sur lequel on écoute la souris.
     * @param onFieldCreated Callback déclenché une fois la zone dessinée avec succès.
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
        startX = event.getX();
        startY = event.getY();
        currentField = new ResizableField(startX, startY);
        workspace.getChildren().add(currentField);
    }

    private void onMouseDragged(MouseEvent event) {
        if (currentField == null) return;

        double currentX = event.getX();
        double currentY = event.getY();

        currentField.setX(Math.min(startX, currentX));
        currentField.setY(Math.min(startY, currentY));
        currentField.setWidth(Math.abs(currentX - startX));
        currentField.setHeight(Math.abs(currentY - startY));
    }

    private void onMouseReleased(MouseEvent event) {
        if (currentField == null) return;

        // Validation (On supprime si le tracé est trop petit, bruit de souris)
        if (currentField.getWidth() < 15 || currentField.getHeight() < 15) {
            workspace.getChildren().remove(currentField);
        } else {
            // Le tracé est valide, on notifie le Controller
            if (onFieldCreated != null) {
                onFieldCreated.accept(currentField);
            }
        }
        currentField = null; // Reset pour le prochain tracé
    }
}