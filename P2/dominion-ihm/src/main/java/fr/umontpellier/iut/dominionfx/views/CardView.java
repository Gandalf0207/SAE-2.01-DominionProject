package fr.umontpellier.iut.dominionfx.views;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte.
 */
public class CardView extends Pane {

    // cache statique partagé par toutes les instances des images
    private static final Map<String, Image> imageCache = new HashMap<>();


    // méthode fate avec l'IA pour la manipulatio du hashmap et des fichier
    private static Image getImage(String cardName) {
        if (!imageCache.containsKey(cardName)) {

            String path = "images/cards/" + cardName.replace(" ", "") + ".jpg"; // remove espace stripTrailing

            // acces robuste peu import l'environnement
            Image image = new Image(CardView.class.getClassLoader().getResourceAsStream(path));
            imageCache.put(cardName, image);
        }
        return imageCache.get(cardName);
    }

    public CardView(String name, int witdh, int height) { // , HBox contener
        ImageView imageView = new ImageView(getImage(name));
        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if(newScene != null){
                imageView.fitWidthProperty().bind(newScene.widthProperty().multiply(0.1*(witdh/100.)));
                imageView.fitHeightProperty().bind(newScene.heightProperty().multiply(0.1*(height/100.)));
            }
        });
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
    }
}
