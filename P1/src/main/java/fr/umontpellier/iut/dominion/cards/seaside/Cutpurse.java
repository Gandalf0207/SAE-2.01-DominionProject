package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Game;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Coupeur de bourse (Cutpurse)
 * <p>
 * +2 Pièces
 * Tous vos adversaires défaussent un Cuivre (Copper) (ou dévoilent une main
 * sans Cuivre).
 */
public class Cutpurse extends Card {
    public Cutpurse() {
        super("Cutpurse", 4, CardType.ACTION, CardType.ATTACK);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(2);

        Game game = p.GetGame();

        for (Player opponent : game.getAllPlayers()) {
            if (!game.PlayerHasLightHouse(opponent) && !opponent.equals(p)) {
                // regarde s'il contient un copper dans sa main et le défausse
                Boolean find = false;
                int cpt = 0;
                while (!find && cpt < opponent.getCardsInHand().size()) {
                    Card c = opponent.getCardsInHand().get(cpt);
                    if (c.getName().equals("Copper")) {
                        opponent.moveToDiscard(c);
                        find = true;
                    }
                    cpt++;
                }

                // révèle toutes les cartes de la main sinon
                if(!find) {
                    String m = "La main du jouer contient : ";
                    for (Card c : opponent.getCardsInHand()) {
                        m+= c.getName();
                    }
                    game.sendToUI(m);
                }
            }
        }
    }
}
