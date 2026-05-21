package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Ambassadeur (Ambassador)
 * <p>
 * Dévoilez une carte de votre main.
 * Replacez, de votre main, à la réserve, jusqu'à 2 exemplaires de cette carte.
 * Ensuite, tous vos adversaires reçoivent un exemplaire de cette carte.
 */
public class Ambassador extends Card {
    public Ambassador() {
        super("Ambassador", 3, CardType.ACTION, CardType.ATTACK);
    }

    @Override
    public void play(Player p) {

        // choix de la carte
        if(p.getCardsInHand().isEmpty()) return;

        // choix de la carte et on la dévoile
        Card c = p.chooseCardFromHand("Choisissez une carte de votre main", false);
        p.log("Carte révélé : " + c.getName());

        int cpt  = 0;
        Card chooseCard = null;
        do {
            // choix des cartes
            chooseCard = p.chooseCardFromHand(
                "Choisissez jusqu'à 2 cartes de votre main de meme type que la carte sélectionné",
                card -> card.getName().equals(c.getName()),
                true);

            // déplacement de la carte dans le supply
            if(chooseCard != null) p.moveToSupply(chooseCard);
            cpt++;

        } while (cpt < 2 && chooseCard != null);

        p.GetGame().distribuateAllOpponentsThisCard(c, p, true);
    }
}
