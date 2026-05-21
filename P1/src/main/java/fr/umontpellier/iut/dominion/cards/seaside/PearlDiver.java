package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.dominion.Button;
import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Plongeur de perles (Pearl Diver)
 * <p>
 * +1 Carte
 * +1 Action
 * Consultez la carte du bas de votre pioche. Vous pouvez la placer sur le haut.
 */
public class PearlDiver extends Card {
    Card c = null;

    public PearlDiver() {
        super("Pearl Diver", 2, CardType.ACTION);
    }

    @Override
    public void play(Player p) {
        p.drawToHand();
        p.incrementAction(1);


        //carte du dessous de la pioche si la défausse et la pioche ne sont pas vide

        List<Card> deck = p.UpdateDeck();
        if(!deck.isEmpty()) {
            c = deck.getFirst();
        }


        // carte n'est pas null
        if(c != null) {
            // choix avec btn
            ArrayList<Button> l = new ArrayList<>();
            l.add(new Button("Yes", "y"));
            l.add(new Button("Non", "n"));
            if ("y".equals(p.chooseStringFromButtons("Choissiser si vous souhaitez mettre la carte : " + c.getName() + " au dessus de votre pioche", l, false))) {
                p.moveToDraw(c);
            }
        }
    }
}
