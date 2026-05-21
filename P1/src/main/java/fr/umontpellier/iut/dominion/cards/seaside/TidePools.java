package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Marée (Tide Pools)
 * <p>
 * +3 Cartes
 * +1 Action
 * Au début de votre prochain tour, défaussez 2 cartes.
 */
public class TidePools extends Card {
    private boolean durationEffect;
    private boolean isEffectLaunch = false;

    public TidePools() {
        super("Tide Pools", 4, CardType.ACTION, CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.drawToHand();
        p.drawToHand();
        p.drawToHand();
        p.incrementAction(1);
        durationEffect = true;
        isEffectLaunch = false;

    }

    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        int n = 2;
        while (!p.getCardsInHand().isEmpty() && n > 0) {
            Card card = p.chooseCardFromHand(
                    "Défaussez %d carte(s).".formatted(n),
                    false); //ça nous renvoit directement une carte, elle nous renvoit nul si il y a rien.
            p.moveToDiscard(card);
            n--;
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
