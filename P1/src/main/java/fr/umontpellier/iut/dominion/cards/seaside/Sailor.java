package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;

import fr.umontpellier.iut.dominion.Button;
import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Navigatrice (Sailor)
 * <p>
 * +1 Action
 * Une fois durant ce tour, quand vous recevez une carte Durée (Duration),
 * vous pouvez la jouer.
 * Au début de votre prochain tour, +2 Pièces et vous pouvez écarter une carte
 * de votre main.
 */
public class Sailor extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;
    private boolean isEffectUse = false;

    public Sailor() {
        super("Sailor", 4, CardType.ACTION, CardType.DURATION);
    }

    @Override
    public void play(Player p){
        p.incrementAction(1);
        durationEffect = true;
        isEffectLaunch = false;
        isEffectUse = false;

    }

    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        durationEffect = false;
        p.incrementMoney(2);
        Card c = p.chooseCardFromHand("Choisissez une carte à écarter de votre main", true);
        if(c!= null) p.moveToTrash(c);
    }

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }

    public boolean getEffectUse() {
        return isEffectUse;
    }

    public void useEffectWhenGainDurationCard(Player p, Card c) {
        // choix d'utiliser la carte duration pioché
        ArrayList<Button> l = new ArrayList<>();
        l.add(new Button("Jouer la carte", "y"));
        l.add(new Button("Passer", "n"));
        if ("y".equals(p.chooseStringFromButtons("Voulez vous jouer la carte gagné ?", l, false))) {
            isEffectUse = true;
            p.playCard(c); // joue la carte
        }
    }
}
