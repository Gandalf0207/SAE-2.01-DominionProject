package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Contrebandiers (Smugglers)
 * <p>
 * Recevez un exemplaire d'une carte coûtant jusqu'à 6 Pièces que le joueur
 * à votre droite a reçues à son dernier tour.
 */




/**
 * Dans player, localement on va stocker les cartes gagnés lors du tours et à la fin
 * elles seront update dans l'item qui est stocké dans game
 *
 * Dans game on stock un elt global qui contient les infos des cartes
 * gagné par le player précédent
 *
 */
public class Smugglers extends Card {
    public Smugglers() {
        super("Smugglers", 3, CardType.ACTION);
    }

    @Override
    public void play(Player p) {
        if(!p.GetGame().getlastPlayerGainCard().getNamePlayerLastTurn().equals(p)) {
            List<String> list = p.GetGame().getlastPlayerGainCard().getCardNameGainLastTurn();

            // on traite avec le nom des cartes simplement
            Card choice = p.chooseCardFromSupply(
                    "Choisissez une carte à gagner",
                    c -> list.contains(c.getName()) && c.getCost() <= 6,
                    true
            );

            if(choice!= null) p.gainToDiscard(p.getCardFromSupply(choice.getName()));

        }
    }
}
