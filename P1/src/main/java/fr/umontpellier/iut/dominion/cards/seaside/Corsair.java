package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Corsaire (Corsair)
 * <p>
 * +2 Pièces
 * Au début de votre prochain tour, +1 Carte. D'ici là, chacun de vos
 * adversaires écarte le premier Argent ou Or qu'il joue à chaque tour.
 */
public class Corsair extends Card {
    private boolean durationEffect = false;
    private ArrayList<Player> playerAffected;
    private boolean isEffectLaunch = false;

    public Corsair() {
        super("Corsair", 5, CardType.ACTION, CardType.DURATION, CardType.ATTACK);
        playerAffected = new ArrayList<>();
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
    public void playerPlayTheCard(Player p, Card c) {
        if(!p.GetGame().PlayerHasLightHouse(p)) {
            if(!playerAffected.contains(p) && (c.hasName("Gold") || c.hasName("Silver"))) {
                playerAffected.add(p);
                p.moveToTrash(c);
            }
        }
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.drawToHand();
        durationEffect = false;
        playerAffected = new ArrayList<>();
    }

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}
