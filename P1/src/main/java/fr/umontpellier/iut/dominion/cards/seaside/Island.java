package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Île (Island)
 * <p>
 * 2 VP
 * Placez cette carte et une carte de votre main sur votre plateau Île (Island
 * Mat).
 */
public class Island extends Card {
    public Island() {
        super("Island", 4, CardType.ACTION, CardType.VICTORY);
    }

    @Override
    public void play(Player p) {
        p.MoveToIslandMat(this);
        if(!p.getCardsInHand().isEmpty()) {
            Card c = p.chooseCardFromHand("Choissiez une carte à mettre dans island mat avec la carte island (déjà déplacé)", false);
            p.MoveToIslandMat(c);
        }
    }
}
