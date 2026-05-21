package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.dominion.Button;
import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Navigateur (Navigator)
 * <p>
 * +2 Pièces
 * Consultez les 5 premières cartes de votre pioche.
 * Défaussez-les toutes ou replacez-les sur votre pioche dans l'ordre de
 * votre choix.
 */
public class Navigator extends Card {
    public Navigator() {
        super("Navigator", 4, CardType.ACTION);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(2);
        List<Card> list = new ArrayList<>();

        Card c = null;
        int cpt = 0;
        do {
            c = p.getCardFromDeck();
            if (c!= null) c.moveTo(list);
            cpt++;
        } while (c!=null && cpt < 5);


        ArrayList<Button> l = new ArrayList<>();
        l.add(new Button("yes", "y"));
        l.add(new Button("no", "n"));

        if ("n".equals(p.chooseStringFromButtons("choisir de défauser les 5 premières cartes de la pioche (y) ou bien de les remettre sur le dessus dans un ordre  choisis (n)", l, false))) {

            while(!list.isEmpty()) {
                Card card = p.chooseCardFromButtons("Choisissez une carte à mettre ur la pioche", list, false);
                p.moveToDraw(card);
            }
        }
        else {
            while (!list.isEmpty()) {
                p.moveToDiscard(list.getFirst());
            }
        }
    }
}
