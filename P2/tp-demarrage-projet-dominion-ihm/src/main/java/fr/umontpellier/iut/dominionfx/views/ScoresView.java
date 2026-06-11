package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import javafx.scene.layout.Pane;

/**
 * Cette classe définit les éléments à afficher lorsque la partie est terminée.
 * Elle peut proposer de recommencer une nouvelle partie.
 */
public class ScoresView extends Pane {

    private DominionIHM ihm;

    public ScoresView(DominionIHM ihm) {
        this.ihm = ihm;
    }
}
