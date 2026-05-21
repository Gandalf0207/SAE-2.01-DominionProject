package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Avant-poste (Outpost)
 * <p>
 * Piochez seulement 3 cartes pour votre prochaine main.
 * Jouez un tour supplémentaire après celui-ci (mais pas un troisième
 * consécutif).
 */
public class Outpost extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    public Outpost() {
        super("Outpost", 5, CardType.ACTION, CardType.DURATION);
    }
    public void play(Player player) {
        durationEffect = true;
        isEffectLaunch = false;
    }

    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {

        durationEffect = false;
    }
    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}
