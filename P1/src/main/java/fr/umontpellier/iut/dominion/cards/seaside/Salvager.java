package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Sauveteur (Salvager)
 * <p>
 * +1 Achat
 * Écartez une carte de votre main. +1 Pièce par Pièce de son coût.
 */
public class Salvager extends Card {
    public Salvager() {
        super("Salvager", 4, CardType.ACTION);
    }

    @Override
    public void play(Player p){
        p.incrementBuy(1);
        int n = 0;
        if (!p.getCardsInHand().isEmpty()) {
            Card card = p.chooseCardFromHand("Choisissez une carte de votre main à Trash", false);
            n = card.getCost();
            p.incrementMoney(n);
            p.moveToTrash(card);
        }
    }
}
