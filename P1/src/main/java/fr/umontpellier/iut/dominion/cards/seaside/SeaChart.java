package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.List;
import java.util.function.Predicate;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte marine (Sea Chart)
 * <p>
 * +1 Carte
 * +1 Action
 * Dévoilez la carte du haut de votre pioche. Si vous en avez un exemplaire
 * en jeu, prenez-la en main.
 */
public class SeaChart extends Card {
    Card c = null;

    public SeaChart() {
        super("Sea Chart", 3, CardType.ACTION);
    }

    @Override
    public void play(Player p){
        p.drawToHand();
        p.incrementAction(1);


        List<Card> deck = p.UpdateDeck();
        if(!deck.isEmpty()) {
            c = deck.getLast();
        }


        if(c!=null) {
            p.log("Revèle la carte" + c.getName());
            if(p.getCardsInPlay().stream().anyMatch(
               new Predicate<Card>() {

                @Override
                public boolean test(Card card) {
                    return card.hasSameNameAs(c);
                }

               })) {
                p.moveToHand(c);
            }
        }


//        for(Card card : p.getCardsInPlay()){
//            if(c.hasSameNameAs(card)) {
//                p.moveToHand(c);
//                  break;
//            }
//        }

    }
}
