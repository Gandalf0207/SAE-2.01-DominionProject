package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.dominion.Button;
import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Bateau pirate (Pirate Ship)
 * <p>
 * Choisissez : +1 Pièce par jeton Pièce sur votre plateau Bateau pirate ;
 * ou tous vos adversaires dévoilent les 2 premières cartes de leur pioche,
 * écartent un Trésor (Treasure) dévoilé de votre choix et défaussent le reste,
 * et si au moins un Trésor a été écarté, placez un jeton Pièce sur votre
 * plateau Bateau pirate.
 */
public class PirateShip extends Card {
    public PirateShip() {
        super("Pirate Ship", 4, CardType.ACTION, CardType.ATTACK);
    }

    @Override
    public void play(Player p) {

        ArrayList<Button> l = new ArrayList<>();
        l.add(new Button("Argent", "coins"));
        l.add(new Button("Attaquer", "attack"));

        if ("coins".equals(p.chooseStringFromButtons("choisir de défauser les 5 premières cartes de la pioche (y) ou bien de les remettre sur le dessus dans un ordre  choisis (n)", l, false))) {
            p.incrementMoney(p.getPirateShipMat());
        }
        else {
            List<Player> allP = p.getGame().getAllPlayers();
            int indexOfactuelPlayer = p.getGame().getPlayerIndex(p);
            int cpt = 1;
            Player passageP = allP.get((indexOfactuelPlayer + cpt) % (allP.size()));
            boolean isCardSendToTrash = false;
            while(!passageP.equals(p)) {

                if(!p.getGame().PlayerHasLightHouse(passageP)) { // protection lighthouse
                    List<Card> tresorCardList = new ArrayList<>();
                    int compteur = 0;
                    Card c = null;
                    do {
                        passageP.UpdateDeck(); // pour remettre la défausse si besoin
                        c = passageP.getCardFromDeck();
                        if(c!=null) {
                            if(c.hasType(CardType.TREASURE)) {
                                c.moveTo(tresorCardList);
                            }
                            else {
                                passageP.moveToDiscard(c);
                            }
                            compteur++;
                        }
                    } while(compteur < 2 && c!=null);

                    if(!tresorCardList.isEmpty()) {
                        Card choosenCard = p.chooseCardFromButtons("Choisissez une carte à écarter", tresorCardList, false);
                        p.moveToTrash(choosenCard);
                        isCardSendToTrash = true;

                        // on replace la carte qui reste
                        if(!tresorCardList.isEmpty()) {
                            passageP.moveToDiscard(tresorCardList.get(0));
                        }
                    }
                }
                cpt++;
                passageP = allP.get((indexOfactuelPlayer + cpt) % (allP.size())); // changer de player

            }

            if(isCardSendToTrash) p.incrementPirateShipMat(1);
        }
    }
}

