package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Caravane (Caravan)
 * <p>
 * +1 Carte
 * +1 Action
 * Au début de votre prochain tour, +1 Carte.
 */
public class Caravan extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    public Caravan() {
        super("Caravan", 4, CardType.ACTION, CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.incrementAction(1);
        p.drawToHand();
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

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}
