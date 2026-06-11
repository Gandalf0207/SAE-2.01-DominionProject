package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.IGame;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;


/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * Elle est initialisée avec une référence sur la partie en cours (Game).
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * ainsi que les listeners à exécuter lorsque ces éléments changent.
 */
public class GameView extends HBox {

    private IGame game;

    @FXML
    private Button skip;

    @FXML
    private Label instruction;

    @FXML
    private CurrentPlayerView currentPlayerPane;

    @FXML
    private SupplyCardView supplyCardPane;

    @FXML
    private VBox leftPane;

    @FXML
    private ScoresView scoresView;


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


        // si la fenetre bouge, on upate les composantes pour la taille
        sceneProperty().addListener((obs, oldScene, newScene) -> {
        if (newScene != null) {

            // bloc au desu cu current vu player
            leftPane.prefWidthProperty().bind(newScene.widthProperty().multiply(2.0 / 5.0));
            leftPane.maxWidthProperty().bind(newScene.widthProperty().multiply(2.0 / 5.0));
            }
        });

        // cache du menu de résultats
        scoresView.setVisible(false);
        scoresView.setManaged(false);
    }

    public void createBindings() {

        // text de ce qu'il y a a faire
        instruction.textProperty().bind(
                game.instructionProperty());

        currentPlayerPane.bindCurrentPlayer();

        skip.setOnMouseClicked(defaultSkipHandler);
        skip.setPadding(Insets.EMPTY);

        // fin du jeu
        game.gameOverProperty().addListener((obs, oldVal, newVal) -> {
           if(newVal) {
            scoresView.setVisible(true);
            scoresView.setManaged(true);
            scoresView.refresh();
           }
        });

    }


    private EventHandler<? super MouseEvent> defaultSkipHandler = (mouseEvent -> {
        game.skipWasChosen();
    });
}
