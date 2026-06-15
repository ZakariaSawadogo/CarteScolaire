package com.scolaire.cartescolaire.ui.controllers;

import com.scolaire.cartescolaire.models.TypeChamp;
import com.scolaire.cartescolaire.ui.components.ResizableField;
import com.scolaire.cartescolaire.ui.handlers.OutilUI;
import com.scolaire.cartescolaire.ui.handlers.WorkspaceDrawHandler;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

/**
 * Contrôleur principal orchestrant l'interface utilisateur, la barre d'outils et le plan de travail.
 */
public class MainController {

    @FXML private Pane drawingPane;
    @FXML private ImageView templateImageView;
    @FXML private ToggleGroup groupeOutils;
    @FXML private ToggleButton btnSelection, btnTexte, btnImage;
    @FXML private ListView<ResizableField> listeChampsUI;

    private WorkspaceDrawHandler drawHandler;
    private final ObservableList<ResizableField> champsCrees = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        drawingPane.setPrefSize(800, 500);

        drawHandler = new WorkspaceDrawHandler(drawingPane, this::onNouveauChampTrace);
        drawHandler.activerModeDessin();

        configurerEcouteursOutils();
        configurerListeChamps();
    }

    private void configurerEcouteursOutils() {
        groupeOutils.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == null) {
                btnSelection.setSelected(true);
                return;
            }
            if (newToggle == btnSelection) drawHandler.setOutilActif(OutilUI.SELECTION);
            else if (newToggle == btnTexte) drawHandler.setOutilActif(OutilUI.TRACER_TEXTE);
            else if (newToggle == btnImage) drawHandler.setOutilActif(OutilUI.TRACER_IMAGE);
        });
    }

    private void configurerListeChamps() {
        listeChampsUI.setItems(champsCrees);

        listeChampsUI.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ResizableField item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
                setGraphic(null);
            }
        });

        listeChampsUI.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ResizableField champSelectionne = listeChampsUI.getSelectionModel().getSelectedItem();
                if (champSelectionne != null) {
                    afficherEditeurProprietes(champSelectionne);
                }
            }
        });
    }

    private void onNouveauChampTrace(ResizableField nouveauChamp) {
        champsCrees.add(nouveauChamp);
        afficherEditeurProprietes(nouveauChamp);
        btnSelection.setSelected(true);
    }

    /**
     * Construit et affiche la boîte de dialogue de configuration des propriétés d'un champ.
     */
    private void afficherEditeurProprietes(ResizableField champ) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Propriétés du Champ");
        dialog.setHeaderText("Configuration du point de fusion");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nomField = new TextField(champ.getNomChamp());
        grid.add(new Label("Nom de la colonne :"), 0, 0);
        grid.add(nomField, 1, 0);

        ComboBox<String> policeCombo = new ComboBox<>();
        Spinner<Integer> tailleSpinner = new Spinner<>(8, 72, champ.getTaillePolice());
        ColorPicker colorPicker = new ColorPicker(champ.getCouleurTexte());

        if (champ.getType() == TypeChamp.TEXTE) {
            policeCombo.getItems().addAll(Font.getFamilies());
            policeCombo.setValue(champ.getNomPolice());

            grid.add(new Label("Police :"), 0, 1);
            grid.add(policeCombo, 1, 1);
            grid.add(new Label("Taille :"), 0, 2);
            grid.add(tailleSpinner, 1, 2);
            grid.add(new Label("Couleur :"), 0, 3);
            grid.add(colorPicker, 1, 3);
        }

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(nomField::requestFocus);

        Optional<ButtonType> resultat = dialog.showAndWait();

        if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
            if (!nomField.getText().trim().isEmpty()) {
                champ.setNomChamp(nomField.getText().trim());
            }
            if (champ.getType() == TypeChamp.TEXTE) {
                champ.setNomPolice(policeCombo.getValue());
                champ.setTaillePolice(tailleSpinner.getValue());
                champ.setCouleurTexte(colorPicker.getValue());
            }
            listeChampsUI.refresh();
        }
    }

    @FXML
    public void onImporterTemplate() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner le template de la carte");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg")
        );

        File fichierChoisi = fileChooser.showOpenDialog(drawingPane.getScene().getWindow());

        if (fichierChoisi != null) {
            chargerImageTemplate(fichierChoisi);
        }
    }

    private void chargerImageTemplate(File fichierImage) {
        Image image = new Image(fichierImage.toURI().toString());
        templateImageView.setImage(image);

        drawingPane.setPrefSize(image.getWidth(), image.getHeight());
        drawingPane.setMaxSize(image.getWidth(), image.getHeight());
    }
}