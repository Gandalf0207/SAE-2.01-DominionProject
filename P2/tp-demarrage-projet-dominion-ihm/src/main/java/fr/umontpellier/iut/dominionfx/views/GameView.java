package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.IGame;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;


/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * Elle est initialisée avec une référence sur la partie en cours (Game).
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * ainsi que les listeners à exécuter lorsque ces éléments changent.
 */
public class GameView extends VBox {

    private IGame game;

    @FXML
    private Button skip;

    @FXML
    private Label instruction;

    @FXML
    private CurrentPlayerView currentPlayerPane;

    public GameView(IGame game) {
        this.game = game;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        createBindings();
    }

    public void createBindings() {

        // meme text
        instruction.textProperty().bind(
                game.instructionProperty());

        currentPlayerPane.bindCurrentPlayer();

        skip.setOnMouseClicked(defaultSkipHandler);
    }


    private EventHandler<? super MouseEvent> defaultSkipHandler = (mouseEvent -> {
        System.out.println("You chose to skip");
        game.skipWasChosen();
    });
}
