package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Carte Vigie (Lookout)
 * <p>
 * +1 Action
 * Consultez les 3 premières cartes des votre pioche. Écartez-en une.
 * Défaussez-en une. Placez la carte restante sur le haut de votre pioche.
 */
public class Lookout extends Card {
    public Lookout() {
        super("Lookout", 3, CardType.ACTION);
    }

    @Override
    public void play(Player p){
        p.incrementAction(1);
        List<Card> topCards = new ArrayList<>();
        for(int i = 0; i < 3 ; i++){
            Card c = p.getCardFromDeck();
            if(c != null) {
                c.moveTo(topCards);
            }
            else {
                break;
            }
        }
        Card c = p.chooseCardFromButtons("Trash une carte.",
                topCards, false);
        if(c != null) {
            p.moveToTrash(c);
        }

        c = p.chooseCardFromButtons("Discard une carte.",
                topCards, false);
        if(c != null) {
            p.moveToDiscard(c);
        }

        if(!topCards.isEmpty()){
            p.moveToDraw(topCards.getFirst());
        }
    }
}
