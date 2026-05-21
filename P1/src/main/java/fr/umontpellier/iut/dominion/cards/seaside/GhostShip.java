package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;
import fr.umontpellier.iut.dominion.Game;

/**
 * Carte Vaisseau fantôme (Ghost Ship)
 * <p>
 * +2 Cartes
 * Tous vos adversaires ayant au moins 4 cartes en main placent des cartes
 * de leur main sur leur pioche jusqu'à avoir 3 cartes en main.
 */
public class GhostShip extends Card {
    public GhostShip() {
        super("Ghost Ship", 5, CardType.ACTION, CardType.ATTACK);
    }

    @Override
    public void play(Player p){
        p.drawToHand();
        p.drawToHand();

        Game game = p.GetGame();

        for (Player opponent : game.getAllPlayers()) {
            if (!game.PlayerHasLightHouse(opponent) && !opponent.equals(p)){
                int n = opponent.getCardsInHand().size();
                while(n > 3){
                    Card card = opponent.chooseCardFromHand(
                            "Mettez une ou des carte(s) dans votre deck." .formatted(n),
                            false); //ça nous renvoit directement une carte, elle nous renvoit nul si il y a rien.
                    opponent.moveToDraw(card);
                    n--;
                }
            }
        }
    }
}
