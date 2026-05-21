package fr.umontpellier.iut.dominion;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.dominion.cards.Card;


public class LastGainCardByLastPlayerWhoPlay {
    private Player p;
    private List<String> listCardName;
    private List<Card> listCard;

    public LastGainCardByLastPlayerWhoPlay(Player p) {
        this.p = p;
        listCardName = new ArrayList<>();
        listCard = new ArrayList<>();
    }

    public Player getNamePlayerLastTurn() {
        return p;
    }

    public List<String> getCardNameGainLastTurn() {
        return listCardName;
    }

    public List<Card> getCardGainLastTurn() {
        return listCard;
    }

    public void reset(Player p) {
        this.p = p;
        listCardName = new ArrayList<>();
        listCard = new ArrayList<>();
    }

    public void addCardName(List<String> c) {
        listCardName.addAll(c);
    }

    public void addCard(List<Card> c) {
        listCard.addAll(c);
    }
}
