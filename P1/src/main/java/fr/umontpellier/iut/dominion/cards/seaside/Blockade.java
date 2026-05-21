package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Game;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Blocus (Blockade)
 * <p>
 * Recevez une carte coûtant jusqu'à 4 Pièces, en la mettant de côté.
 * Au début de votre prochain tour, prenez-la en main
 * Tant qu'elle est mise de côté, quand un autre joueur en reçoit un
 * exemplaire durant leur tour, il reçoit une Malédiction (Curse).
 */
public class Blockade extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    Card cardAside;


    public Blockade() {
        super("Blockade", 4, CardType.ACTION, CardType.DURATION, CardType.ATTACK);
    }


    public void play(Player p) {

        Game game = p.GetGame();
        durationEffect = true;
        // choix carte et déplacement dans set Aside
        cardAside  = p.chooseCardFromSupply("Choisissez une carte à gagner d'une valeur <= 4", c -> c.getCost() <= 4, false);
        p.moveToSetAside(cardAside);
        game.addFlagCardNameGainCurseWhenDrawFromSupply(cardAside.getName());

        isEffectLaunch = false;

    }

    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        Game game = p.GetGame();

        p.moveToHand(cardAside);
        game.removeFlagCardNameGainCurseWhenDrawFromSupply(cardAside.getName());
    }

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}
