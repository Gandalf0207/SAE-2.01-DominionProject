package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Astrolabe
 * <p>
 * Maintenant et au début de votre prochain tour :
 * +1 Pièce
 * +1 Achat
 */
public class Astrolabe extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    public Astrolabe() {
        super("Astrolabe", 3, CardType.TREASURE, CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(1);
        p.incrementBuy(1);
        isEffectLaunch = false;
        durationEffect = true;
    }

     @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.incrementMoney(1);
        p.incrementBuy(1);
        durationEffect = false;
    }

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}
