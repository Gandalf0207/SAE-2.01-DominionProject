package fr.umontpellier.iut.dominionfx.views;

import java.io.IOException;
import java.util.List;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
/**
 * Cette classe est utilisé pour représenter la zone lattéral droite, là ou se trouve toutes les cartes des supplys
 */
public class SupplyCardView extends VBox {

    private List<SupplyPile> piles;

    private Font pirateFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("fonts/PiecesOfEight.ttf"), 55);
    private Font pirateFont2 = Font.loadFont(getClass().getClassLoader().getResourceAsStream("fonts/PiecesOfEight.ttf"), 25);


    @FXML
    private TilePane treasureCard;
    @FXML
    private TilePane victoryCard;
    @FXML
    private TilePane kingdomCard;

    @FXML
    private Label shopLabel;
    @FXML
    private Label treasuresCardLabel;
    @FXML
    private Label victoryCardLabel;
    @FXML
    private Label kingdomCardLabel;

    @FXML
    private ShowInfosCardView showInfosCardView;



    public SupplyCardView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/supplyCardView.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

        piles = DominionIHM.getGame().getSupplyPiles();

        for (SupplyPile pile : piles) {
            // une sorte btn pour get une carte, avec les infos du nombre restant

            Node pileVisuel = createSupplyPileNode(pile);
            switch (pile.getName()) {
                case "Copper", "Silver", "Gold" -> treasureCard.getChildren().add(pileVisuel);
                case "Estate", "Duchy", "Province", "Curse" -> victoryCard.getChildren().add(pileVisuel);
                default -> kingdomCard.getChildren().add(pileVisuel);
            }
        }


        // force load

        shopLabel.setFont(pirateFont);
        treasuresCardLabel.setFont(pirateFont2);
        victoryCardLabel.setFont(pirateFont2);
        kingdomCardLabel.setFont(pirateFont2);


    }

    private Node createSupplyPileNode(SupplyPile pile) {
        // permet de superposer des elts
        StackPane pileContainer = new StackPane();
        String name = pile.getName();

        // btn cliquable avec la carte
        Button btn = new Button();
        CardView img = new CardView(name, 100, 150);
        btn.setPadding(Insets.EMPTY);
        btn.setGraphic(img);

        btn.setOnAction(e -> {
            DominionIHM.getGame().supplyCardWasChosen(name);
        });

        // au survol → affiche les infos
        pileContainer.setOnMouseEntered(e -> {
            showInfosCardView.showPile(pile);
        });

        // quand on quitte → cache
        pileContainer.setOnMouseExited(e -> {
            showInfosCardView.hide();
        });

        // bulle nbcard
        StackPane blocNbCard = createBulleNombreCardInPile(pile, btn, pileContainer);
        StackPane.setAlignment(blocNbCard, Pos.BOTTOM_RIGHT);

        // bulle embargo
        StackPane blocEmbargo = createBulleEmbargo(pile);
        StackPane.setAlignment(blocEmbargo, Pos.TOP_RIGHT);
        // caché par défaut
        blocEmbargo.setVisible(pile.getNbEmbargoTokens() > 0);
        blocEmbargo.setManaged(pile.getNbEmbargoTokens() > 0);

        pileContainer.setUserData(pile); // infos utilisable
        pileContainer.getChildren().addAll(btn, blocNbCard, blocEmbargo);
        return pileContainer;
    }

    private StackPane createBulleNombreCardInPile(SupplyPile pile, Button btn, StackPane pileContainer) {
        StackPane bloc = new StackPane(); // bloc

        Label quantiteCard = new Label(""+pile.getSize()); // text
        Circle cercleBcg = new Circle(15.0, Color.WHITESMOKE); // bcg

        //style
        bloc.setMaxWidth(30);
        bloc.setMaxHeight(30);
        quantiteCard.setTextFill(Color.BLACK);
        quantiteCard.setFont(pirateFont2);

        // listener pour update stat et état
        pile.sizeProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) {
                if (pile.isEmpty()) {
                    btn.setDisable(true);
                    pileContainer.setOpacity(0.4);
                }
                quantiteCard.setText(newVal.toString());
            }
        });

        // formation du bloc
        bloc.getChildren().addAll(cercleBcg, quantiteCard);
        return bloc;
    }

    private StackPane createBulleEmbargo(SupplyPile pile) {
        StackPane bloc = new StackPane();
        Label nb = new Label("" + pile.getNbEmbargoTokens());
        Circle cercleBcg = new Circle(15.0, Color.RED);

        bloc.setMaxWidth(30);
        bloc.setMaxHeight(30);
        nb.setTextFill(Color.WHITE);
        nb.setFont(pirateFont2);

        // s'afiche ou non automatiquement
        pile.nbEmbargoTokensProperty().addListener((obs, oldVal, newVal) -> {
            boolean hasNb = newVal.intValue() > 0;
            bloc.setVisible(hasNb);
            bloc.setManaged(hasNb);
            nb.setText("" + newVal.intValue());
        });

        bloc.getChildren().addAll(cercleBcg, nb);
        return bloc;
}
}
