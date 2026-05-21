package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

import java.util.List;

/**
 * Carte Singe (Monkey)
 * <p>
 * Jusqu'à votre prochain tour, quand le joueur à votre droite reçoit une
 * carte, +1 Carte.
 * Au début de votre prochain tour, +1 Carte.
 */
public class Monkey extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    public Monkey() {
        super("Monkey", 3, CardType.ACTION, CardType.DURATION);
    }

    @Override
    public void play(Player p){
        durationEffect = true;
        isEffectLaunch = false;

    }

    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.drawToHand();
        durationEffect = false;
    }

    @Override
    public void monkeyEffect(Player p) {
        p.gainToHand(p.getCardFromDeck());
    }

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}
