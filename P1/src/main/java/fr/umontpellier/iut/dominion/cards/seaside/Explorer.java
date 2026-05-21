package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Explorateur (Explorer)
 * <p>
 * Vous pouvez dévoiler une Province de votre main. Si vous le faites, recevez
 * un Or (Gold) en main. Sinon, recevez un Argent (Silver) en main.
 */
public class Explorer extends Card {
    public Explorer() {
        super("Explorer", 5, CardType.ACTION);
    }

    @Override
    public void play(Player p) {

        Card c = p.chooseCardFromHand(
            "Choisir une carte province de votre main pour gagner une carte gold ou passer pour gagner une carte silver",
            card -> card.getName().equals("Province"),
            true);

        String str = c!= null ? "Gold" : "Silver";
        Card gCard = p.getCardFromSupply(str);
        p.gainToHand(gCard);

    }
}
