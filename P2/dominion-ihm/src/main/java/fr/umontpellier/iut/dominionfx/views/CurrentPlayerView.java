package fr.umontpellier.iut.dominionfx.views;

import java.io.IOException;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import fr.umontpellier.iut.dominionfx.ICard;
import fr.umontpellier.iut.dominionfx.IPlayer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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

    // NOM
    @FXML
    private Label playerName;

    // INFOS
    @FXML
    private Label moneyLabel;
    @FXML
    private Label drawLabel;
    @FXML
    private Label discardLabel;
    @FXML
    private Label actionsLabel;
    @FXML
    private Label buysLabel;
    @FXML
    private Label pirateShipLabel;
    @FXML
    private Label nativeVillageInfosLabel;
    @FXML
    private Label islandMatInfosLabel;

    // ZONE CARDS
    @FXML
    private HBox inPlayPane;
    @FXML
    private HBox handPane;
    @FXML
    private HBox islandMatPane;
    @FXML
    private HBox nativeVillageMatPane;


    // BTN UNILES
    @FXML
    private YesNoView yesNoView;
    @FXML
    private Button playTreasuresBtn;

    @FXML
    private HBox temporaryCardsPane;

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

    private ListChangeListener<ICard> temporaryCardsListener = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                for (ICard c : change.getAddedSubList()) {
                    temporaryCardsPane.getChildren().add(createNodeTemporaryCard(c));
                }
            }

            if (change.wasRemoved()) {
                for (ICard c : change.getRemoved()) {
                    temporaryCardsPane.getChildren().removeIf(btn -> btn.getUserData() == c);
                }
            }
        }
    };

    private ListChangeListener<ICard> islandMatListener = change -> {
        refreshIslandMat();
    };

    private ListChangeListener<ICard> nativeVillageMatListener = change -> {
        refreshNativeVillageMat();
    };

    private ChangeListener<IPlayer> currentPlayerChangeListener = (currentPlayer, oldvalue, newValue) -> {
        refreshIslandMat();
        refreshNativeVillageMat();
        refreshHand();
        refreshInPlay();

        // détacher listener update hand
        if(oldvalue != null) {
            oldvalue.getHand().removeListener(handListener);
            oldvalue.getInPlay().removeListener(inPlayListener);
            yesNoView.unBind();

            oldvalue.getIslandMat().removeListener(islandMatListener);
            oldvalue.getNativeVillageMat().removeListener(nativeVillageMatListener);
        }

        // attacher listener update hand
        if(newValue != null) {
            // player gestion
            playerName.setText(newValue.getName());
            newValue.getHand().addListener(handListener);
            newValue.getInPlay().addListener(inPlayListener);
            newValue.getIslandMat().addListener(islandMatListener);
            newValue.getNativeVillageMat().addListener(nativeVillageMatListener);
            // label gestion
            moneyLabel.textProperty().bind(Bindings.concat("Money : " ,newValue.moneyProperty().asString()));
            actionsLabel.textProperty().bind(Bindings.concat("Actions : " ,newValue.numberOfActionsProperty().asString()));
            buysLabel.textProperty().bind(Bindings.concat("Buy : " ,newValue.numberOfBuysProperty().asString()));
            pirateShipLabel.textProperty().bind(Bindings.concat("PirateShip : " ,newValue.pirateShipCounterProperty().asString()));

            drawLabel.textProperty().bind(Bindings.concat("Draw : " ,Bindings.size(newValue.getDraw())));
            discardLabel.textProperty().bind(Bindings.concat("Discard : " ,Bindings.size(newValue.getDiscard())));

            nativeVillageInfosLabel.textProperty().bind(Bindings.concat("Native Village : ", Bindings.size(newValue.getNativeVillageMat())));
            islandMatInfosLabel.textProperty().bind(Bindings.concat("Island Map : ",Bindings.size(newValue.getIslandMat())));

            // btn gestion
            playTreasuresBtn.setOnAction(event -> {newValue.playTreasuresWasChosen();});
            // actif ou non
            playTreasuresBtn.disableProperty().bind(newValue.numberOfBuysProperty().lessThanOrEqualTo(0));

            // bind du bloc de question
            yesNoView.bindToPlayer(newValue);
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

    public void refreshInPlay() {
        // on vide tout les enfants de la liste
        inPlayPane.getChildren().clear();

        // ajout autant de btn que de cartes
        for(ICard c : currentPlayer.getValue().getInPlay()) {
            Node button = createNodeInPlay(c);
            inPlayPane.getChildren().add(button);
        }
    }


    private void refreshIslandMat() {
        islandMatPane.getChildren().clear();
        for (ICard card : currentPlayer.getValue().getIslandMat()) {
            islandMatPane.getChildren().add(new CardView(card.getName(), 50, 75));
        }
    }

    private void refreshNativeVillageMat() {
        nativeVillageMatPane.getChildren().clear();
        for (ICard card : currentPlayer.getValue().getNativeVillageMat()) {
            nativeVillageMatPane.getChildren().add(new CardView(card.getName(), 50, 75));
        }
    }

    /**
     * Permet de créer un button pour une carte et lui
     * affecte sont gestionnaire d'évènement.
     * @param card
     * @return
     */
    public Node createCardNodeInHand(ICard card) {
        Button button = new Button();
        button.setGraphic(new CardView(card.getName(), 100, 150));
        button.setPadding(Insets.EMPTY);  // supprime le padding interne du bouton
        button.setUserData(card);
        button.setOnAction(event -> {
            getCurrentPlayer().getValue().cardInHandWasChosen(card.getName());
        });
        return button;
    }



    public Node createNodeInPlay(ICard card) {
        Button button = new Button();
        button.setGraphic(new CardView(card.getName(), 100, 150));
        button.setPadding(Insets.EMPTY);
        button.setUserData(card);
        return button;
    }

    public Node createNodeTemporaryCard(ICard card) {
        Button button = new Button();
        button.setGraphic(new CardView(card.getName(), 100, 150));
        button.setPadding(Insets.EMPTY);
        button.setUserData(card);
        button.setOnAction(event -> {
            DominionIHM.getGame().temporaryCardWasChosen(card.getName());
        });
        return button;
    }
    public void bindCurrentPlayer() {
        // référence player actuel
        currentPlayer = DominionIHM.getGame().currentPlayerProperty();
        // synchro nom player actuel
        currentPlayer.addListener(currentPlayerChangeListener);
        //Listener pour des cartes temporaires
        DominionIHM.getGame().temporaryCardsProperty().addListener(temporaryCardsListener);
    }


    public ObjectProperty<? extends IPlayer> getCurrentPlayer() {
        return currentPlayer;
    }



}

