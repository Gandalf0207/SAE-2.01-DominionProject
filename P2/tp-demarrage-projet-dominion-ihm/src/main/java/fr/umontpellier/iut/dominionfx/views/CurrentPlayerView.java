package fr.umontpellier.iut.dominionfx.views;

import java.io.IOException;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import fr.umontpellier.iut.dominionfx.ICard;
import fr.umontpellier.iut.dominionfx.IPlayer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class CurrentPlayerView extends VBox {

    @FXML
    private HBox handPane;

    @FXML
    private Label playerName;

    @FXML
    private HBox inPlayPane;

    ObjectProperty<? extends IPlayer> currentPlayer;


    public CurrentPlayerView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/currentPlayerView.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ListChangeListener<ICard> handListener = change -> {
        while(change.next()) {
            if(change.wasAdded()) {
                for(ICard c : change.getAddedSubList()) {
                    handPane.getChildren().add(createCardNodeInHand(c));
                }
            }

            if(change.wasRemoved()) {
                for (ICard c : change.getRemoved()) {
                    handPane.getChildren().removeIf(btn -> btn.getUserData() == c);
                }
            }
        }

    };

    private ListChangeListener<ICard> inPlayListener = change -> {
        while(change.next()) {
            if(change.wasAdded()) {
                for(ICard c : change.getAddedSubList()) {
                    inPlayPane.getChildren().add(createNodeInPlay(c));
                }
            }

            if(change.wasRemoved()) {
                for (ICard c : change.getRemoved()) {
                    inPlayPane.getChildren().removeIf(btn -> btn.getUserData() == c);
                }
            }
        }

    };

    private ChangeListener<IPlayer> currentPlayerChangeListener = (currentPlayer, oldvalue, newValue) -> {

        refreshHand();
        refreshInPlay();

        // détacher listener update hand
        if(oldvalue != null) {
            oldvalue.getHand().removeListener(handListener);
            oldvalue.getInPlay().removeListener(inPlayListener);
        }

        // attacher listener update hand
        if(newValue != null) {
            playerName.setText(newValue.getName());
            newValue.getHand().addListener(handListener);
            newValue.getInPlay().addListener(inPlayListener);
        }


    };



    public void refreshHand() {

        // on vide tout les enfants de la liste
        handPane.getChildren().clear();

        // ajout autant de btn que de cartes
        for(ICard c : currentPlayer.getValue().getHand()) {
            Node button = createCardNodeInHand(c);
            handPane.getChildren().add(button);
        }
    }

    /**
     * Permet de créer un button pour une carte et lui
     * affecte sont gestionnaire d'évènement.
     * @param card
     * @return
     */
    public Node createCardNodeInHand(ICard card) {
        Button button = new Button(card.getName());
        button.setUserData(card);
        button.setOnAction(event -> {
            getCurrentPlayer().getValue().cardInHandWasChosen(card.getName());
        });
        return button;
    }


    public void refreshInPlay() {
        // on vide tout les enfants de la liste
        inPlayPane.getChildren().clear();

        // ajout autant de btn que de cartes
        for(ICard c : currentPlayer.getValue().getInPlay()) {
            Node button = createNodeInPlay(c);
            inPlayPane.getChildren().add(button);
        }
    }

    public Node createNodeInPlay(ICard card) {
        Button button = new Button(card.getName());
        button.setUserData(card);
        return button;
    }





    public void bindCurrentPlayer() {

        // référence player actuel
        currentPlayer = DominionIHM.getGame().currentPlayerProperty();
        // synchro nom player actuel
        currentPlayer.addListener(currentPlayerChangeListener);

    }


    public ObjectProperty<? extends IPlayer> getCurrentPlayer() {
        return currentPlayer;
    }


}

