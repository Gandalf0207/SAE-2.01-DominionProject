package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte aux trésors (Treasure Map)
 * <p>
 * Écartez ceci et une Carte aux trésors de votre main. Si vous avez écarté
 * deux Cartes aux trésors, recevez 4 Ors (Gold) sur votre pioche.
 */
public class TreasureMap extends Card {
    public TreasureMap() {
        super("Treasure Map", 4, CardType.ACTION);
    }

    @Override
    public void play(Player p) {

        Card toTrash = this;
        if (!p.getCardsInHand().isEmpty())
            for (Card card : p.getCardsInHand()) {
                if (card.getName().equals(this.getName())) {
                    toTrash = card;
                }
            }

        if (!toTrash.equals(this)) {
            p.moveToTrash(toTrash);
            for (int i = 0; i < 4; i++) {
                p.moveToDraw(p.getCardFromSupply("Gold"));
            }
        }
        p.moveToTrash(this);
    }
}
