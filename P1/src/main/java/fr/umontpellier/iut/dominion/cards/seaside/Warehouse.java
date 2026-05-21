package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Entrepôt (Warehouse)
 * <p>
 * +3 Cartes
 * +1 Action
 * Défaussez 3 cartes.
 */
public class Warehouse extends Card {
    public Warehouse() {
        super("Warehouse", 3, CardType.ACTION);
    }
    @Override
    public void play(Player p){
        p.drawToHand();
        p.drawToHand();
        p.drawToHand();
        p.incrementAction(1);
        int n = 3;
        while (!p.getCardsInHand().isEmpty() && n>0){
            Card card = p.chooseCardFromHand(
                    "Défaussez %d carte(s)." .formatted(n),
                    false); //ça nous renvoit directement une carte, elle nous renvoit nul si il y a rien.
            p.moveToDiscard(card);
            n--;
        }
    }

}
