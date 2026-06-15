package com.scolaire.cartescolaire.ui.components;

import com.scolaire.cartescolaire.models.TypeChamp;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

/**
 * Composant graphique interactif représentant une zone de dessin redimensionnable et déplaçable.
 */
public class ResizableField extends Rectangle {

    private enum ResizeMode { NONE, EAST, SOUTH, SOUTH_EAST }

    private static final int RESIZE_MARGIN = 8;
    private static final int MIN_SIZE = 20;

    private final double[] dragDelta = new double[2];
    private ResizeMode resizeMode = ResizeMode.NONE;

    @Setter @Getter private String nomChamp;
    @Setter @Getter private TypeChamp type;
    @Setter @Getter private String nomPolice = "Arial";
    @Setter @Getter private int taillePolice = 14;
    @Setter @Getter private Color couleurTexte = Color.BLACK;

    /**
     * Crée un nouveau champ interactif sur le plan de travail.
     *
     * @param x    Coordonnée X initiale.
     * @param y    Coordonnée Y initiale.
     * @param type Le type de donnée (Texte, Image) que contiendra ce champ.
     */
    public ResizableField(double x, double y, TypeChamp type) {
        super(x, y, 0, 0);
        this.type = type;
        this.nomChamp = (type == TypeChamp.TEXTE) ? "Nouveau Texte" : "Nouvelle Image";

        appliquerStyleParDefaut();
        initialiserInteractivite();
    }

    private void appliquerStyleParDefaut() {
        this.setStroke(Color.web("#bef264"));
        this.setStrokeWidth(2);
        this.getStrokeDashArray().addAll(5d, 5d);
        this.setFill(Color.rgb(190, 242, 100, 0.2));
    }

    private void initialiserInteractivite() {
        this.setOnMouseMoved(e -> updateCursor(e.getX(), e.getY()));
        this.setOnMousePressed(this::handleMousePressed);
        this.setOnMouseDragged(this::handleMouseDragged);
        this.setOnMouseReleased(this::handleMouseReleased);
    }

    private void updateCursor(double mouseX, double mouseY) {
        boolean rightEdge = mouseX >= this.getX() + this.getWidth() - RESIZE_MARGIN;
        boolean bottomEdge = mouseY >= this.getY() + this.getHeight() - RESIZE_MARGIN;

        if (rightEdge && bottomEdge) this.setCursor(Cursor.SE_RESIZE);
        else if (rightEdge) this.setCursor(Cursor.E_RESIZE);
        else if (bottomEdge) this.setCursor(Cursor.S_RESIZE);
        else this.setCursor(Cursor.HAND);
    }

    private void handleMousePressed(MouseEvent e) {
        if (this.getCursor() == Cursor.SE_RESIZE) resizeMode = ResizeMode.SOUTH_EAST;
        else if (this.getCursor() == Cursor.E_RESIZE) resizeMode = ResizeMode.EAST;
        else if (this.getCursor() == Cursor.S_RESIZE) resizeMode = ResizeMode.SOUTH;
        else {
            resizeMode = ResizeMode.NONE;
            dragDelta[0] = this.getX() - e.getX();
            dragDelta[1] = this.getY() - e.getY();
        }
        e.consume();
    }

    private void handleMouseDragged(MouseEvent e) {
        switch (resizeMode) {
            case SOUTH_EAST -> {
                this.setWidth(Math.max(MIN_SIZE, e.getX() - this.getX()));
                this.setHeight(Math.max(MIN_SIZE, e.getY() - this.getY()));
            }
            case EAST -> this.setWidth(Math.max(MIN_SIZE, e.getX() - this.getX()));
            case SOUTH -> this.setHeight(Math.max(MIN_SIZE, e.getY() - this.getY()));
            case NONE -> {
                this.setX(e.getX() + dragDelta[0]);
                this.setY(e.getY() + dragDelta[1]);
            }
        }
        e.consume();
    }

    private void handleMouseReleased(MouseEvent e) {
        resizeMode = ResizeMode.NONE;
        e.consume();
    }

    @Override
    public String toString() {
        String typeStr = (this.type == TypeChamp.TEXTE) ? "[Texte] " : "[Image] ";
        return typeStr + this.nomChamp;
    }
}