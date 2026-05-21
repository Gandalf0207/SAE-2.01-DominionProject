package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Navire marchand (Merchant Ship)
 * <p>
 * Maintenant et au début de votre prochain tour, +Pièces.
 */
public class MerchantShip extends Card  { //il faudra peut être créer une classe durationEffect
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    public MerchantShip() {
        super("Merchant Ship", 5, CardType.ACTION, CardType.DURATION);
    }
    @Override
    public void play(Player p){
        p.incrementMoney(2);
        durationEffect = true;
        isEffectLaunch = false;

    }

    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.incrementMoney(2);
        durationEffect = false;
    }

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}
