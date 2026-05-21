package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;

import fr.umontpellier.iut.dominion.Button;
import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Village indigène (Native Village)
 * <p>
 * +2 Actions
 * Choisissez : placez la carte du haut de votre pioche, face cachée, sur votre
 * plateau Village indigène (vous pouvez consulter ces cartes à tout moment);
 * ou prenez en main toutes les cartes du plateau.
 */
public class NativeVillage extends Card {
    public NativeVillage() {
        super("Native Village", 2, CardType.ACTION);
    }

    @Override
    public void play(Player p) {
        p.incrementAction(2);

        // choix avec btn :
        ArrayList<Button> l = new ArrayList<>();
        l.add(new Button("Ajouter", "add"));
        l.add(new Button("Tout Prendre", "take"));

        if ("add".equals(p.chooseStringFromButtons("Choisissez si vous voulez mettre la carte en haut du deck (attention si plus de carte) dans native village ou si vous voulez mettre toutes les cartes de native village dans votre main", l, false))) {
            // prendre la carte du haut du deck et mettre dans native village
            Card c = p.getCardFromDeck();
            if (c != null ) p.moveToNativeVillageMat(c);
        }
        else {
            // prenre toutes les cartes de native village
            for (Card c : p.getCardsOnNativeVillageMat()) {
                p.moveToHand(c);
            }
        }
    }
}
