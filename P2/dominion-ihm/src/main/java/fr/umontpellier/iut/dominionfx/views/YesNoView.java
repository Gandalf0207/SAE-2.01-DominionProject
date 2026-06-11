package fr.umontpellier.iut.dominionfx.views;

import java.io.IOException;

import fr.umontpellier.iut.dominionfx.IPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;



public class YesNoView extends HBox {

    @FXML
    private Button yesBtn;

    @FXML
    private Button noBtn;

    private Font pirateFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("fonts/PiecesOfEight.ttf"), 18);

    public YesNoView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/yesNoView.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        yesBtn.setFont(pirateFont);
        noBtn.setFont(pirateFont);
    }

    /**
     * Lie les bouton aux event du player et s'activent ou non
     * La box de queston est affiché s'il y a une question en attente sinon elle disparait
     * et les btn sont désactivé par sécurité
     * @param player
     */
    public void bindToPlayer(IPlayer player) {

        // si question alors visible
        visibleProperty().bind(player.waitForYesOrNoProperty());

        // securité verouillage btn
        yesBtn.disableProperty().bind(player.waitForYesOrNoProperty().not());
        noBtn.disableProperty().bind(player.waitForYesOrNoProperty().not());

        // get event surles btn
        yesBtn.setOnAction(e -> player.answer("Yes"));
        noBtn.setOnAction(e -> player.answer("No"));
    }

    public void unBind() {
        yesBtn.disableProperty().unbind();
        noBtn.disableProperty().unbind();
        visibleProperty().unbind();
    }
}
