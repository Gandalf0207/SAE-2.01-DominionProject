package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Village de pêcheurs (Fishing Village)
 * <p>
 * +2 Actions
 * +1 Pièce
 * Au début de votre prochain tour, +1 Action et +1 Pièce.
 */
public class FishingVillage extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    public FishingVillage() {
        super("Fishing Village", 3, CardType.ACTION, CardType.DURATION);
    }

    @Override
    public void play(Player p){
        p.incrementAction(2);
        p.incrementMoney(1);
        durationEffect = true;
        isEffectLaunch = false;

    }
    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.incrementMoney(1);
        p.incrementAction(1);
        durationEffect = false;
    }

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}
