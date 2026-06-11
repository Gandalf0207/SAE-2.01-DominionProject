package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import fr.umontpellier.iut.dominionfx.ICard;
import fr.umontpellier.iut.dominionfx.IPlayer;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class OtherPlayersView extends VBox {
    @FXML
    private Label titreInfosOtherPlayer;

    @FXML
    private HBox boxForNodeInfoPlayer;

    private Font pirateFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("fonts/PiecesOfEight.ttf"), 30);

    public OtherPlayersView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/otherPlayersView.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        titreInfosOtherPlayer.setFont(pirateFont);
    }

    /**
     * Setup d'un Node d'information concernant un joueur
     * @param player
     * @return
     */
    private VBox createPlayerInfoNode(IPlayer player) {
        Label name = new Label(player.getName());
        name.getStyleClass().addAll("textPirate","sizeText18");

        VBox box = new VBox();
        box.setUserData(player);
        box.getStyleClass().add("center");

        StackPane playerDraw = new StackPane();
        StackPane playerDiscard = new StackPane();
        StackPane playerHand = new StackPane();

        playerDraw.getStyleClass().add("imageSetup");
        playerDiscard.getStyleClass().add("imageSetup");
        playerHand.getStyleClass().add("imageSetup");

        playerDraw.setId("infoDrawOtherPlayer");
        playerDiscard.setId("infoDiscardOtherPlayer");
        playerHand.setId("infosHandOtherPlayer");


        StackPane nbHand = createBulleNombre(player.getHand());
        StackPane nbDiscard = createBulleNombre(player.getDiscard());
        StackPane nbDraw = createBulleNombre(player.getDraw());

        StackPane.setAlignment(nbHand, Pos.BOTTOM_CENTER);
        playerHand.getChildren().add(nbHand);

        StackPane.setAlignment(nbDraw, Pos.BOTTOM_CENTER);
        playerDraw.getChildren().add(nbDraw);

        StackPane.setAlignment(nbDiscard, Pos.BOTTOM_CENTER);
        playerDiscard.getChildren().add(nbDiscard);

        HBox boxForNodeInfo = new HBox(playerHand, playerDraw, playerDiscard);
        boxForNodeInfo.setId("contenerInfosOtherPlayer");

        box.getChildren().addAll(name,boxForNodeInfo);
        return box;
    }


    private StackPane createBulleNombre(ObservableList<? extends ICard> list) {
        StackPane bloc = new StackPane(); // bloc

        Label nb = new Label(); // text
        Circle cercleBcg = new Circle(15.0, Color.BLACK); // bcg

        //style
        bloc.setMaxWidth(30);
        bloc.setMaxHeight(30);
        nb.setTextFill(Color.WHITE);

        // listener pour update stat et état
        nb.textProperty().bind(
            Bindings.concat(Bindings.size(list))
        );

        // formation du bloc
        bloc.getChildren().addAll(cercleBcg, nb);
        return bloc;
    }


    /**
     * Cache le node d'information du player actuel
     * Affiche les nodes d'informations des autres joueurs
     */
    private void refreshOtherPlayers(IPlayer player) {
        for(Node playerInfoNode : boxForNodeInfoPlayer.getChildren()) {
            Boolean value = !playerInfoNode.getUserData().equals(player);
            playerInfoNode.setManaged(value);  // le layout l'ignore complètement
            playerInfoNode.setVisible(value);
        }
    }
    /**
     * Crée les nodes d'informations de tout les joueurs
     *
     */
    private void createPlayerInfoNode(){
        for(IPlayer joueur : DominionIHM.getGame().getPlayers()) {
            boxForNodeInfoPlayer.getChildren().add(createPlayerInfoNode(joueur));
        }
    }
    @FXML
    public void initialize() {
        createPlayerInfoNode();
        DominionIHM.getGame().currentPlayerProperty().addListener((obs, ancienJoueur, nouveauJoueur) ->
        {
            if(nouveauJoueur != null) {
                refreshOtherPlayers(nouveauJoueur);
            }
        });

    }
}
