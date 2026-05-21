package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.List;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Game;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Sorcière de mer (Sea Hag)
 * <p>
 * Tous vos adversaires défaussent la carte du haut de leur pioche, puis
 * reçoivent une Malédiction (Curse) sur leur pioche.
 */
public class SeaHag extends Card {
    Card c = null;

    public SeaHag() {
        super("Sea Hag", 4, CardType.ACTION, CardType.  ATTACK);
    }

    @Override
    public void play(Player p){
        Game game = p.GetGame();

        for (Player opponent : game.getAllPlayers()) {
            if (!game.PlayerHasLightHouse(opponent) && !opponent.equals(p)){

                // recup de la carte (remélange de la défausse si necessaire)
                List<Card> deck = opponent.UpdateDeck();
                if(!deck.isEmpty()) {
                    c = deck.getLast();
                }

                // défausse de la carte
                if(c!= null) {
                    opponent.moveToDiscard(c);
                }

                // gain du curse
                c = opponent.getCardFromSupply("Curse");
                opponent.gainToDraw(c);
            }
        }
    }
}
