package com.scolaire.cartescolaire.ui.components;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ResizableField extends Rectangle {

    private final double[] dragDelta = new double[2];
    private final boolean[] isResizing = new boolean[3]; // 0=Sud-Est, 1=Est, 2=Sud
    private static final int RESIZE_MARGIN = 8;
    private static final int MIN_SIZE = 20;

    public ResizableField(double x, double y) {
        super(x, y, 0, 0);
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

    private void handleMousePressed(javafx.scene.input.MouseEvent e) {
        if (this.getCursor() == Cursor.SE_RESIZE) isResizing[0] = true;
        else if (this.getCursor() == Cursor.E_RESIZE) isResizing[1] = true;
        else if (this.getCursor() == Cursor.S_RESIZE) isResizing[2] = true;
        else {
            dragDelta[0] = this.getX() - e.getX();
            dragDelta[1] = this.getY() - e.getY();
        }
        e.consume(); // Empêche la propagation de l'événement au parent (le Workspace)
    }

    private void handleMouseDragged(javafx.scene.input.MouseEvent e) {
        if (isResizing[0]) {
            this.setWidth(Math.max(MIN_SIZE, e.getX() - this.getX()));
            this.setHeight(Math.max(MIN_SIZE, e.getY() - this.getY()));
        } else if (isResizing[1]) {
            this.setWidth(Math.max(MIN_SIZE, e.getX() - this.getX()));
        } else if (isResizing[2]) {
            this.setHeight(Math.max(MIN_SIZE, e.getY() - this.getY()));
        } else {
            this.setX(e.getX() + dragDelta[0]);
            this.setY(e.getY() + dragDelta[1]);
        }
        e.consume();
    }

    private void handleMouseReleased(javafx.scene.input.MouseEvent e) {
        isResizing[0] = false;
        isResizing[1] = false;
        isResizing[2] = false;
        e.consume();
    }
}