package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;

import fr.umontpellier.iut.dominion.Button;
import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.SupplyPile;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Embargo
 * <p>
 * +2 Pièces
 * Écartez ceci pour placer un jeton Embargo sur une pile de la réserve.
 * (Pendant le reste de la partie, quand un joueur achète une carte de cette
 * pile, il reçoit une Malédiction (Curse).)
 */
public class Embargo extends Card {
    private String affectedSupply;


    public Embargo() {
        super("Embargo", 2, CardType.ACTION);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(2);

        // choix de poser l'embargo
        ArrayList<Button> list = new ArrayList<>();
        for (SupplyPile supplyPile : p.getGame().getSupplyPiles()) {
            list.add(new Button("Nom " + supplyPile.getName(), supplyPile.getName().toUpperCase()));
        }

        String choosenSupplyPile = p.chooseStringFromSupply(
            "Choisissez un type de carte pour l'embargo",
            false);

        affectedSupply = choosenSupplyPile;


        p.getGame().addAffectedEmbargo(this);
        p.moveToTrash(this);
    }

    public String getAffectedSupply() {
        return affectedSupply;
    }
}
