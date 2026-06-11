package fr.umontpellier.iut.dominionfx.views;

import java.io.IOException;
import java.util.ArrayList;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import fr.umontpellier.iut.dominionfx.IPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Cette classe définit les éléments à afficher lorsque la partie est terminée.
 * Elle peut proposer de recommencer une nouvelle partie.
 */
public class ScoresView extends VBox {

    @FXML
    private HBox contenerNodeResult;

    @FXML
    private Label phraseVictoire;

    private int resultatMax = Integer.MIN_VALUE;
    private ArrayList<String> playersWin = new ArrayList<>();

    public ScoresView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/scoresView.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setMaxWidth(USE_PREF_SIZE);
        setMaxHeight(USE_PREF_SIZE);
    }

    public void refresh() {

        for(IPlayer player : DominionIHM.getGame().getPlayers()) {
            contenerNodeResult.getChildren().add(builderNodeResult(player));
            updateInfosInterne(player);
        }
        updateText();
    }

        private Node builderNodeResult(IPlayer player) {
        VBox node = new VBox();
        Label name = new Label(player.getName());

        Label pts = new Label("Points Victoire : " + player.getVictoryPoints());

        name.getStyleClass().addAll("textPirate", "sizeText18");
        pts.getStyleClass().addAll("textPirate", "sizeText18");

        node.getChildren().addAll(name, pts);

        return node;
    }

    private void updateInfosInterne(IPlayer player) {
        int resP = player.getVictoryPoints();
        String nameP = player.getName();

        if(resP >= resultatMax) {
            if(resP > resultatMax) {
                playersWin.clear();
            }
            resultatMax = resP;
            playersWin.add(nameP);
        }
    }

    private void updateText() {
        if(playersWin.size() == 1) {
            phraseVictoire.setText("Victoire de " + playersWin.get(0));
        }
        else {
            String names ="";
            for(int i = 0; i < playersWin.size(); i++) {
                names += playersWin.get(i);

                if(i == (playersWin.size() - 2)) {
                    names += " et ";
                }
                else if(i != playersWin.size() -1) {
                    names += ", ";
                }
            }
            phraseVictoire.setText("Egalité entre " + names);
        }
    }
}
