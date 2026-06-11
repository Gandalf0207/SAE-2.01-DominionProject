package fr.umontpellier.iut.dominionfx.views;

import java.io.IOException;

import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ShowInfosCardView extends VBox {

    private Font pirateFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("fonts/PiecesOfEight.ttf"), 55);
    private Font pirateFont2 = Font.loadFont(getClass().getClassLoader().getResourceAsStream("fonts/PiecesOfEight.ttf"), 25);


    @FXML
    private Label cardNameLabel;

    @FXML
    private Label cardCostLabel;

    @FXML
    private Label cardTypeLabel;

    @FXML
    private Label cardStockLabel;

    @FXML
    private VBox cardPreviewContainer;

    @FXML
    private Label titreInfos;


    public ShowInfosCardView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/showInfosCard.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        titreInfos.setFont(pirateFont);
        cardNameLabel.setFont(pirateFont2);
        cardCostLabel.setFont(pirateFont2);
        cardTypeLabel.setFont(pirateFont2);
        cardStockLabel.setFont(pirateFont2);

    }


    private String getType(String name) {
        return switch (name) {
            case "Copper", "Silver", "Gold" -> "Treasure";
            case "Estate", "Duchy", "Province", "Curse" -> "Victory";
            default -> "Kingdom";
        };
    }

    public void showPile(SupplyPile pile) {
        cardNameLabel.setText("Nom de la carte : " + pile.getName());
        cardCostLabel.setText("Cout de la carte : " + pile.getCost() + " piece(s)");
        cardTypeLabel.setText("Type de la carte : " + getType(pile.getName()));
        cardStockLabel.setText("Il reste " + pile.getSize() + " carte(s)");

        cardPreviewContainer.getChildren().clear();
        cardPreviewContainer.getChildren().add(new CardView(pile.getName(), 200, 300));
        cardPreviewContainer.setVisible(true);
    }

    public void hide() {
        cardPreviewContainer.setVisible(false);
        cardNameLabel.setText("Nom de la carte : ...");
        cardCostLabel.setText("Cout de la carte : ... piece(s)");
        cardTypeLabel.setText("Type de la carte : ...");
        cardStockLabel.setText("Il reste ... carte(s)");
    }
}
