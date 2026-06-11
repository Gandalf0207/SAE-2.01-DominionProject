package fr.umontpellier.iut.dominionfx.views;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir les noms des joueurs de la partie.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class ChoosePlayersView extends Stage implements Initializable {

    private final ObservableList<String> playersNames;

    public ChoosePlayersView() {
        playersNames = FXCollections.observableArrayList();
    }

    public ObservableList<String> playersNamesProperty() {
        return playersNames;
    }

    public String[] getPlayersNames() {
        return playersNames.toArray(new String[0]);
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setPlayersNamesDefinedListener(ListChangeListener<String> whenPlayersNamesAreDefined) {}

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

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNumberOfPlayers() {
        throw new RuntimeException("Methode à implémenter");
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     *
     * @param playerNumber : le numéro du participant
     */
    protected String getPlayerByIndex(int playerNumber) {
        throw new RuntimeException("Methode à implémenter");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}