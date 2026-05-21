package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Quai (Wharf)
 * <p>
 * Maintenant et au début de votre prochain tour : +2 Cartes et +1 Achat.
 */
public class Wharf extends Card {

    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    public Wharf() {
        super("Wharf", 5, CardType.ACTION, CardType.DURATION);
    }

    @Override
    public void play(Player p){
        p.drawToHand();
        p.drawToHand();
        p.incrementBuy(1);
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
        p.drawToHand();
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
