package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.dominion.Button;
import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Pirate
 * <p>
 * Au début de votre prochain tour, recevez en main un Trésor coûtant jusqu'à
 * 6 Pièces.
 * Quand un joueur reçoit un Trésor, vous pouvez jouer cette carte depuis votre
 * main.
 */
public class Pirate extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;
    private boolean isPlayDuringHisOwnTurn = false;

    public Pirate() {
        super("Pirate", 5, CardType.ACTION, CardType.DURATION, CardType.REACTION);
    }

    @Override
    public void play(Player p){
        durationEffect = true;
        isEffectLaunch = false;
        isPlayDuringHisOwnTurn = false;
    }

    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        if(isPlayDuringHisOwnTurn) {
            isPlayDuringHisOwnTurn = false; // duration efect qui prend affet au tours suivant
            // car la carte a été joué lors du  tours du player actuel
            return;
        }

        // base
        Card card = p.chooseCardFromSupply("Choisissez une carte trésor de max 6 pièces", c -> c.getCost() <= 6 && c.hasType(CardType.TREASURE), false);
        if(card!=null) {
            p.gainToHand(card);
        }
        durationEffect = false;
    }

    public boolean askPlayerPlayPirate(Player currentPlayer, Player p) {
        List<String> choices = new ArrayList<>();
        choices.add("HAND:Pirate"); // "HAND:Pirate"

        String choice = p.choose("Voulez-vous jouer la carte Pirate ?", choices, new ArrayList<>(), true);

        boolean play = "HAND:Pirate".equals(choice);
        if (play) {
            if(currentPlayer.equals(p)) {
                isPlayDuringHisOwnTurn = true;
            }
            p.playCard(this); // joue la carte
        }
        return play;
    }

    public void launcheEffect() {
        isEffectLaunch = true;
    }

    public boolean getEffectLaunch() {
        return isEffectLaunch;
    }
}

