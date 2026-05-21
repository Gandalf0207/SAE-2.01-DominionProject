package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Tacticien (Tactician)
 * <p>
 * Si vous avez au moins une carte en main, défaussez votre main, et au debut
 * de votre prochain tour, +5 Cartes, +1 Action, et +1 Achat.
 */
public class Tactician extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    public Tactician() {
        super("Tactician", 5, CardType.ACTION, CardType.DURATION);
    }

    @Override
    public void play(Player p){
        if(p.getCardsInHand().isEmpty()) return;
        durationEffect = true;
        isEffectLaunch = false;

        for(Card c : p.getCardsInHand()){
            p.moveToDiscard(c);
        }
    }
    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.drawToHand();
        p.drawToHand();
        p.drawToHand();
        p.drawToHand();
        p.drawToHand();
        p.incrementAction(1);
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
