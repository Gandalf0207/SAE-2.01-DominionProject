package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Havre (Haven)
 * <p>
 * +1 Carte
 * +1 Action
 * Mettez de côté une carte de votre main face cachée (sous cette carte).
 * Au début de votre prochain tour, prenez-la en main.
 */
public class Haven extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    Card c;

    public Haven() {
        super("Haven", 2, CardType.ACTION, CardType.DURATION);
        c=null;
    }

    @Override
    public void play(Player p){
        p.drawToHand();
        p.incrementAction(1);
        durationEffect = true;
        isEffectLaunch = false;


        // choix de la carte à mettre de coté
        if(!p.getCardsInHand().isEmpty()){
            c = p.chooseCardFromHand("Choississez une carte à mettre de côté pendant le tour pour la récupérer ensuite", false);
            p.moveToSetAside(c);
        }
    }

    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        if(c!=null) {
            p.moveToHand(c);
            c = null;
        }
        durationEffect = false;
    }

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}
