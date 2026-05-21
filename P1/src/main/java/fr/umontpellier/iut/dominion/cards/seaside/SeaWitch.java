package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Game;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

import java.util.ArrayList;

/**
 * Carte Sorcière marine (Sea Witch)
 * <p>
 * +2 Cartes
 * Tous vos adversaires reçoivent une Malédiction (Curse).
 * Au début de votre prochain tour, +2 Cartes, puis défaussez 2 cartes.
 */
public class SeaWitch extends Card {
    private boolean durationEffect = false;
    private boolean isEffectLaunch = false;

    public SeaWitch() {
        super("Sea Witch", 5, CardType.ACTION, CardType.DURATION, CardType.ATTACK);
    }

    @Override
    public void play(Player p) {
        p.drawToHand();
        p.drawToHand();
        Game game = p.GetGame();

        for (Player opponent : game.getAllPlayers()) {
            if (!game.PlayerHasLightHouse(opponent) && !opponent.equals(p)) {
                Card c = opponent.getCardFromSupply("Curse");
                opponent.gainToDiscard(c);
            }
        }
        durationEffect = true;
        isEffectLaunch = false;

    }
    @Override
    public boolean hasDurationEffect() {
        return durationEffect;
    }

    @Override
    public void atStartOfTurn(Player p) {
        ArrayList<Card> cardsGainFromSeaWitch = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            Card card1 = p.drawToHand();
            p.moveToHand(card1);
            cardsGainFromSeaWitch.add(card1);
        }

        int n = 0;
        while(n < 2) {
            if(p.getCardsInHand().isEmpty()){
                break;
            }
            if(p.getCardsInHand().size() == 1){
                n++;
            }
            Card card = p.chooseCardFromHand(
                    "Mettez une carte a defaussé.",
                    false); //ça nous renvoit directement une carte, elle nous renvoit nul si il y a rien.
            p.moveToDiscard(card);
            n++;
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
