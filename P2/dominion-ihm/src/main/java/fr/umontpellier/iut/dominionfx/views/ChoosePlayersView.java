package fr.umontpellier.iut.dominionfx.views;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir les noms des joueurs de la partie.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class ChoosePlayersView extends Stage implements Initializable {

    private final ObservableList<String> playersNames;

    @FXML private Spinner<Integer> nbPlayersSpinner;
    @FXML private VBox playersNamesContainer;
    @FXML private Button startBtn;

    private final List<TextField> nameFields = new ArrayList<>();

    public ChoosePlayersView() {
        playersNames = FXCollections.observableArrayList();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/choosePlayersView.fxml"));
            loader.setController(this);
            VBox root = loader.load();
            Scene scene = new Scene(root, 400, 500);
            this.setTitle("Dominion-Seaside - Choix des joueurs");
            this.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // génère les champs au démarrage
        refreshNameFields(2);

        // regénère les champs quand le spinner change
        nbPlayersSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            refreshNameFields(newVal);
        });
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setPlayersNamesDefinedListener(ListChangeListener<String> whenPlayersNamesAreDefined) {
        playersNames.addListener(whenPlayersNamesAreDefined);
    }


    private void refreshNameFields(int nb) {
        playersNamesContainer.getChildren().clear();
        nameFields.clear();
        for (int i = 1; i <= nb; i++) {
            HBox ligne = new HBox(10);
            ligne.setAlignment(Pos.CENTER);
            Label label = new Label("Joueur " + i + " :");
            label.getStyleClass().addAll("textPirate", "sizeText18");
            TextField field = new TextField();
            field.setPromptText("Nom du joueur " + i);
            nameFields.add(field);
            ligne.getChildren().addAll(label, field);
            playersNamesContainer.getChildren().add(ligne);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNumberOfPlayers() {
        return nbPlayersSpinner.getValue();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     *
     * @param playerNumber : le numéro du participant
     */
    protected String getPlayerByIndex(int playerNumber) {
        return nameFields.get(playerNumber - 1).getText();
    }

        public String[] getPlayersNames() {
        return playersNames.toArray(new String[0]);
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    @FXML
    protected void setPlayersNamesList() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNumberOfPlayers(); i++) {
            String name = getPlayerByIndex(i);
            if (name == null || name.isEmpty()) {
                tempNamesList.clear();
                break;
            } else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            playersNames.clear();
            playersNames.addAll(tempNamesList);
        }
    }
}
