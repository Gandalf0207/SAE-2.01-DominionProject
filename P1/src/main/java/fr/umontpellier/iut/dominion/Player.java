package fr.umontpellier.iut.dominion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fr.umontpellier.iut.dominion.cards.Card;
import fr.umontpellier.iut.dominion.cards.seaside.Embargo;
import fr.umontpellier.iut.dominion.cards.seaside.Sailor;
import fr.umontpellier.iut.dominion.cards.seaside.Pirate;
import fr.umontpellier.iut.dominion.gui.Utils;

/**
 * Un joueur de Dominion
 */
public class Player {
    /**
     * Nom du joueur
     */
    private final String name;

    /**
     * Nombre d'actions disponibles
     */
    private int numberOfActions;

    /**
     * Nombre d'achats disponibles
     */
    private int numberOfBuys;

    /**
     * Nombre de pièces disponibles pour acheter des cartes
     */
    private int money;

    /**
     * La partie en cours
     */
    private final Game game;

    /**
     * Liste des cartes dans la main du joueur
     */
    private final List<Card> hand;

    /**
     * Liste des cartes dans la défausse du joueur
     */
    private final List<Card> discard;

    /**
     * Liste des cartes dans la pioche du joueur (on considère que le dessus de
     * la pioche est à la fin de la liste)
     */
    private final List<Card> draw;

    /**
     * Listes des cartes qui ont été jouées pendant le tour courant
     */
    private final List<Card> inPlay;

    /**
     * Liste des cartes actuellement mises de côté par une action (par exemple Haven
     * of Blockade) qui ne sont pas considérées comme "in play" ni sur aucun des
     * tapis spéciaux du joueur (island mat, native village mat, etc.)
     */
    private final List<Card> cardsSetAside;

    /**
     * Liste des cartes mises de côté sur le plateau île (island mat) du joueur
     */
    private final List<Card> islandMat;

    /**
     * Liste des cartes mises de côté sur le plateau correspondant à la carte Native
     * Village du joueur
     */
    private final List<Card> nativeVillageMat;

    private List<String> GainCardNameDuringPlayTurn;
    private List<Card> GainCardDuringPlayTurn;

    private int nbConsecutifTurn;

    private int pirateShipMat;
    /**
     * Constructeur
     *
     * Initialise les différentes piles de cartes du joueur, place 3 cartes
     * Estate et 7 cartes Copper dans la défausse du joueur puis fait piocher 5
     * cartes en main au joueur.
     *
     * @param name: le nom du joueur
     * @param game: le jeu en cours
     *
     */
    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
        // Prépare les listes de cartes
        hand = new ArrayList<>();
        discard = new ArrayList<>();
        draw = new ArrayList<>();
        inPlay = new ArrayList<>();
        cardsSetAside = new ArrayList<>();
        islandMat = new ArrayList<>();
        nativeVillageMat = new ArrayList<>();

        // Ajoute 3 Estate et 7 Copper (pris dans la réserve du jeu) dans la
        // défausse du joueur
        for (int i = 0; i < 3; i++)
            getCardFromSupply("Estate").moveTo(discard);
        for (int i = 0; i < 7; i++)
            getCardFromSupply("Copper").moveTo(discard);

        // Mélange la défausse, construit la pioche et pioche 5 cartes en main
        Collections.shuffle(discard);
        while (!discard.isEmpty()) {
            discard.getLast().moveTo(draw);
        }
        for (int i = 0; i < 5; i++) {
            draw.getLast().moveTo(hand);
        }
    }

    /**
     * Getters et setters
     */
    public String getName() {
        return name;
    }

    public int getIndex() {
        return game.getPlayerIndex(this);
    }

    public int getMoney() {
        return money;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public int getNumberOfBuys() {
        return numberOfBuys;
    }

    public Game getGame() {
        return game;
    }

    /**
     * Renvoie une liste des cartes que le joueur a en main.
     * <p>
     * La liste renvoyée est une copie de la liste {@code hand} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     */
    public List<Card> getCardsInHand() {
        return new ArrayList<>(hand);
    }

    /**
     * Renvoie une liste des cartes que le joueur a dans sa défausse.
     * <p>
     * La liste renvoyée est une copie de la liste {@code discard} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     */
    public List<Card> getCardsInDiscard() {
        return new ArrayList<>(discard);
    }

    /**
     * Renvoie une liste des cartes que le joueur a dans sa pioche.
     * <p>
     * La liste renvoyée est une copie de la liste {@code draw} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     *
     */
    public List<Card> getCardsInDraw() {
        return new ArrayList<>(draw);
    }

    /**
     * Renvoie une liste des cartes que le joueur a en jeu.
     * <p>
     * La liste renvoyée est une copie de la liste {@code inPlay} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     */
    public List<Card> getCardsInPlay() {
        return new ArrayList<>(inPlay);
    }

    /**
     * Renvoie une liste des cartes que le joueur a mises de côté.
     * <p>
     * La liste renvoyée est une copie de la liste {@code cardsSetAside} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     */
    public List<Card> getCardsSetAside() {
        return new ArrayList<>(cardsSetAside);
    }

    /**
     * Renvoie une liste des cartes que le joueur a sur son plateau île (island
     * mat).
     * <p>
     * La liste renvoyée est une copie de la liste {@code islandMat} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     */
    public List<Card> getCardsOnIslandMat() {
        return new ArrayList<>(islandMat);
    }

    /**
     * Renvoie une liste des cartes que le joueur a sur son plateau Native Village.
     * <p>
     * La liste renvoyée est une copie de la liste {@code nativeVillageMat} du
     * joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     */
    public List<Card> getCardsOnNativeVillageMat() {
        return new ArrayList<>(nativeVillageMat);
    }

    /**
     * Renvoie une liste de toutes les cartes possédées par le joueur
     */
    public List<Card> getAllOwnedCards() {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(discard);
        allCards.addAll(draw);
        allCards.addAll(inPlay);
        allCards.addAll(cardsSetAside);
        allCards.addAll(islandMat);
        allCards.addAll(nativeVillageMat);
        return allCards;
    }

    /**
     * Renvoie le nombre total de points de victoire du joueur
     * <p>
     * Ce total est calculé en ajoutant les valeurs individuelles de toutes les
     * cartes possédées par le joueur (en utilisant la méthode
     * {@code getVictoryValue()}) des cartes
     */
    public int getVictoryPoints() {
        int total = 0;
        for (Card c : getAllOwnedCards()) {
            total += c.getVictoryValue();
        }
        return total;
    }

    /**
     * Incrémente le nombre de pièces du joueur ({@code money})
     *
     * @param n nombre de pièces à ajouter (ce nombre peut être négatif si l'on
     *          souhaite diminuer le nombre de pièces)
     */
    public void incrementMoney(int n) {
        money += n;
    }
    /**
     * Incrémente le nombre d'actions du joueur ({@code numberOfActions})
     *
     * @param n nombre d'actions à ajouter
     */
    public void incrementAction(int n){
        numberOfActions += n;
    }

    /**
     * Déplace une carte dans la main du joueur.
     *
     * @param c la carte à déplacer
     */
    public void moveToHand(Card c) {
        c.moveTo(hand);
    }
    /**
     * Déplace une carte dans la discard du joueur.
     *
     * @param c la carte à déplacer
     */
    public void moveToDiscard(Card c) {
        c.moveTo(discard);
    }
    /**
     * Déplace une carte dans le draw du joueur.
     *
     * @param c la carte à déplacer
     */
    public void moveToDraw(Card c) {
        c.moveTo(draw);
    }
    /**
     * Déplace une carte dans le Trash du List de Trash dans la classe Game.
     *
     * @param c la carte à déplacer
     */
    public void moveToTrash(Card c) {
        game.moveCardToTrash(c);
    }

    public void moveToSupply(Card c) {
        game.moveCardToSupply(c);
    }

    public void moveToSetAside(Card c) {
        c.moveTo(cardsSetAside);
    }

    public void MoveToIslandMat(Card c) {
        c.moveTo(islandMat);
    }

    public Game GetGame() {
        return game;
    }

    public void moveToNativeVillageMat(Card c) {
        c.moveTo(nativeVillageMat);
    }

    public int getPirateShipMat() {
        return pirateShipMat;
    }

    public void incrementPirateShipMat(int n) {
        pirateShipMat+= n;
    }


    /**
     * Renvoie la carte qui se trouve au sommet de la pioche du joueur.
     * <p>
     * Si la pioche du joueur est vide, on commence par mélanger la défausse
     * et transférer toutes les cartes de la défausse dans la pioche.
     * On renvoie ensuite la première carte de la pioche si elle n'est
     * pas vide (sinon la méthode renvoie {@code null}).
     * <p>
     * Remarque : la carte n'est pas retirée de la pioche.
     *
     * @return la carte piochée, ou {@code null} si aucune carte disponible
     */
    public Card getCardFromDeck() {
        UpdateDeck();

        if(!draw.isEmpty()) {
            return draw.getLast();
        }
        return null;
    }

    public List<Card> UpdateDeck() {
        if(draw.isEmpty()) {
            Collections.shuffle(discard);
            while (!discard.isEmpty()) {
                discard.getLast().moveTo(draw);
            }
        }
        return draw;
    }

    /**
     * @param cardName nom de la carte à obtenir dans la réserve
     * @return la carte du sommet de la pile de réserve correspondant au nom
     *         passé en argument, ou {@code null} si la pile de réserve est vide ou
     *         si le nom ne correspond à aucune pile de la réserve.
     */
    public Card getCardFromSupply(String cardName) {
        return game.getCardFromSupply(cardName);
    }

    /**
     * Pioche une carte et la place directement dans la main du joueur.
     * <p>
     * Cette méthode fait appel à la méthode {@code getCardFromDeck()} pour piocher
     * une carte et la place dans la main du joueur.
     */
    public Card drawToHand() {
        Card c = getCardFromDeck();
        if(c != null) {
            c.moveTo(hand);
        }
        return c;
    }

    /**
     * Renvoie une représentation de l'état du joueur sous forme d'une chaîne
     * de caractères.
     * <p>
     * Cette représentation comporte
     * - le nom du joueur
     * - le nombre d'actions, de pièces et d'achats du joueur
     * - le nombre de cartes dans la pioche et dans la défausse du joueur
     * - la liste des cartes en jeu du joueur
     * - la liste des cartes dans la main du joueur
     * <p>
     * On pourrait par exemple avoir l'affichage suivant:
     * <p>
     * -- Toto --
     * Actions: 2 Money: 4 Buys: 1 Draw: 7 Discard: 3
     * In play: Caravan, Copper, Silver, Copper
     * Hand: Estate, Province
     */
    @Override
    public String toString() {
        String r = String.format("     -- %s --\n", name);
        r += String.format("Actions: %d     Money: %d     Buys: %d     Draw: %d     Discard: %d\n",
                numberOfActions,
                money, numberOfBuys, draw.size(), discard.size());
        r += String.format("In play: %s\n", inPlay.toString());
        r += String.format("Hand: %s\n", hand.toString());
        return r;
    }

    public String toLog() {
        return "<span class=\"player-name\">" + name + "</span>";
    }

    /**
     * Méthode utilitaire pour l'interface graphique.
     * À NE PAS MODIFIER.
     */
    public String toJSON() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(String.format("\"name\": \"%s\"", name));
        joiner.add(String.format("\"actions\": %d", numberOfActions));
        joiner.add(String.format("\"money\": %d", money));
        joiner.add(String.format("\"buys\": %d", numberOfBuys));
        joiner.add(String.format("\"draw\": %s", Utils.toJSON(draw)));
        joiner.add(String.format("\"discard\": %s", Utils.toJSON(discard)));
        joiner.add(String.format("\"in_play\": %s", Utils.toJSON(inPlay)));
        joiner.add(String.format("\"hand\": %s", Utils.toJSON(hand)));
        return "{" + joiner + "}";
    }

    /**
     * Joue une carte de la main du joueur.
     * <p>
     * Cette méthode ne vérifie pas que le joueur a le droit de jouer la
     * carte, ni même que la carte se trouve effectivement dans sa main.
     * La carte est déplacée de la main du joueur dans la liste
     * {@code inPlay} et la méthode {@code play(Player p)} de la
     * carte est exécutée.
     *
     * @param c carte à jouer
     */
    public void playCard(Card c) {
        c.moveTo(inPlay);
        c.play(this);

        for (Player p : game.getAllPlayers()) {
            for (Card card : p.getCardsInPlay()) {
                card.playerPlayTheCard(this, c);
            }
        }

    }

    /**
     * Joue une carte de la main du joueur.
     * <p>
     * S'il existe une carte dans la main du joueur dont le nom est
     * égal au paramètre, la carte est jouée à l'aide de la méthode
     * {@code playCard(Card c)}. Si aucune carte ne correspond, la
     * méthode ne fait rien.
     *
     * @param cardName nom de la carte à jouer
     */
    public void playCard(String cardName) {
        Card c = getCardFromSupply(cardName);
        if(c != null) {
            playCard(c);
        }
    }

    /**
     * Le joueur gagne une carte et la place dans un emplacement donné (main,
     * défausse, etc.)
     * <p>
     * Si la carte n'est pas {@code null}, elle est déplacée dans l'emplacement
     * indiqué
     *
     * @param gainedCard carte à gagner (éventuellement {@code null})
     */
    public void gainTo(Card gainedCard, List<Card> location) {

        // pirate event
        if(gainedCard!=null) {
            if(gainedCard.hasType(CardType.TREASURE)) {
                // check de tout les playeurs s'ils ont un pirate et si l'effet a été lancé
                // dans l'ordre de déroulé actuel en partant du player actuel
                List<Player> allP = game.getAllPlayers();
                int indexOfActuelPlayer = game.getPlayerIndex(this);
                Player p = this;
                int cpt = 0;
                do {
                    for (Card c : p.getCardsInHand()) {
                        if(c.hasName("Pirate")) {
                            Pirate cardPirate = (Pirate) c;
                            // choix de jouer la carte ou non
                            boolean isPlayed = cardPirate.askPlayerPlayPirate(this, p);
                            if(!isPlayed) {break;};
                        }
                    }

                    cpt++;
                    p = allP.get((indexOfActuelPlayer + cpt) % (allP.size()));
                } while (!p.equals(this));
            }
        }

        if(gainedCard != null) {
            gainedCard.moveTo(location);

            // sauvegarde des cartes gagné
            if(game.getCurrentTurnPlayer().equals(this)) {
               addGainCardNameDuringPlayTurn(gainedCard.getName());
               addGainCardDuringPlayTurn(gainedCard);
            }
        }

        if(gainedCard!=null) {
            if(!game.PlayerHasLightHouse(this) && game.getCurrentTurnPlayer().equals(this)) { // si on gagne un carte, il faut que ce soit son tours pour eetre affecté par le malus
                // check de si la carte pioché est un flag pour gagner un curse
                for (String flagCardName : game.getFlagCardNameGainCurseWhenDrawFromSupply()) {
                    if(gainedCard.getName().equals(flagCardName)) {
                        Card curse = game.getCardFromSupply("Curse");
                        if(curse != null) {

                            // sauvegarde des cartes gagné
                            if(game.getCurrentTurnPlayer().equals(this)) {
                                addGainCardNameDuringPlayTurn(curse.getName());
                                addGainCardDuringPlayTurn(curse);
                            }

                            this.gainToDiscard(curse);
                        }
                    }
                }
            }
        }

        if(gainedCard!=null) {
            // sailor play gain duration card
            if(gainedCard.hasType(CardType.DURATION)) {
                for(int i = 0; i < inPlay.size(); i++) {
                    Card c = inPlay.get(i);
                    if(c.hasName("Sailor")) {
                        Sailor cardSailor = (Sailor) inPlay.get(i);
                        if(!cardSailor.getEffectUse()) {
                            cardSailor.useEffectWhenGainDurationCard(this, gainedCard);
                        }
                    }
                }
            }
        }

    }


    public void gainToHand(Card c) {
        gainTo(c, hand);
    }

    public void gainToDiscard(Card c) {gainTo(c, discard); }

    public void gainToDraw(Card c) { gainTo(c, draw);
    }
    /**
     * Attend une entrée de la part du joueur (au clavier) et renvoie le choix
     * du joueur.
     * <p>
     * La méthode lit l'entrée clavier jusqu'à ce qu'un choix valide
     * soit entré par l'utilisateur sous la forme d'une chaîne de caractères
     * {@code <TYPE>:<VALEUR>} (par exemple {@code "HAND:Caravan"})
     * correspondant à un élément de {@code choices} ou éventuellement la chaîne
     * vide si l'utilisateur est autorisé à passer. Lorsqu'un choix valide est
     * obtenu, il est renvoyé.
     * <p>
     * Exemple d'utilisation pour demander à un joueur de choisir le nom d'une
     * carte de sa main (ici il n'a pas le droit de passer s'il a au moins une carte
     * en main). Dans l'exemple la méthode renvoie une chaîne de caractères de la
     * forme {@code "HAND:<cardName>"} où {@code <cardName>} est le nom de la carte
     * choisie par le joueur parmi les cartes de sa main.
     *
     * <pre>
     * {@code
     * List<String> choices = new ArrayList<>();
     * for (Card c : hand) {
     *     choices.add("HAND:" + c.getName());
     * }
     * String choice = p.choose("Choose a card", choices, new ArrayList<>(), false);
     * }
     * </pre>
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     *                    la nature du choix qui est attendu
     * @param choices     une liste de {@code String} correspondant aux
     *                    choix valides attendus du joueur.
     * @param buttons     une liste de boutons à afficher à l'écran. Chaque bouton
     *                    correspond à une option de choix qui sera ajoutée à la
     *                    liste des choix valides ({@code "BUTTON:<value>"}).
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix. S'il est autorisé à passer, c'est la
     *                    chaîne de caractères vide {@code ""} qui signifie qu'il
     *                    désire passer. Remarque : si aucun choix valide n'est
     *                    fourni (la liste {@code choices} est vide), le joueur est
     *                    automatiquement autorisé à passer même si {@code canPass}
     *                    est faux.
     * @return l'objet {@code String} correspondant au choix effectué par
     *         l'utilisateur (un élément de {@code choices} ou une chaîne de la
     *         forme {@code "BUTTON:<value>"} correspondant à un bouton de
     *         {@code buttons} ou éventuellement {@code ""}, si l'utilisateur a
     *         choisi de passer.
     */
    public String choose(String instruction, List<String> choices, List<Button> buttons, boolean canPass) {
        // Ajout des options correspondant aux boutons
        for (Button b : buttons) {
            choices.add("BUTTON:" + b.value());
        }
        // Si aucun choix disponible, le joueur est autorisé à passer
        if (choices.isEmpty()) {
            canPass = true;
        }
        // Si le joueur peut passer, on ajoute l'option ""
        if (canPass) {
            choices.add("");
        }
        // Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
        while (true) {
            game.prompt(instruction, choices, buttons, getIndex());
            String input = game.readLine();
            if (choices.contains(input)) {
                return input;
            }
        }
    }

    /**
     * Attend une entrée de la part du joueur et renvoie le choix du joueur.
     * <p>
     * Dans cette méthode, la liste des choix est donnée sous la forme d'un prédicat
     * permettant de filtrer les cartes de la main du joueur. Le résultat renvoyé
     * est la carte choisie ou {@code null} si le joueur a choisi de passer.
     * <p>
     * La méthode commence par construire une liste de tous les noms des cartes
     * dans {@code hand} qui vérifient le prédicat, puis appelle la méthode
     * {@code choose} pour faire choisir un nom parmi cette liste à l'utilisateur.
     * <p>
     * Exemple d'utilisation pour faire choisir le nom d'une carte Action de sa
     * main à un joueur (dans cet exemple le joueur n'a pas le droit de passer
     * s'il a au moins une carte Action en main, mais la méthode peut quand
     * même renvoyer {@code null} s'il n'a aucune carte Action en main) :
     *
     * <pre>
     * Card choice = p.chooseCardFromHand(
     *         "Choose an Action card",
     *         c -> c.hasType(CardType.ACTION),
     *         false);
     * </pre>
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     *                    la nature du choix qui est attendu
     * @param filter      prédicat permettant de filtrer les cartes de la main
     *                    du joueur. Seules les cartes pour lesquelles le prédicat
     *                    renvoie {@code true} seront considérées comme choix
     *                    valides.
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix.
     * @return la carte choisie par le joueur ou {@code null} si le joueur a choisi
     *         de passer ou s'il n'avait aucune carte valide dans sa main.
     */
    public Card chooseCardFromHand(String instruction, Predicate<Card> filter, boolean canPass) {
        // ajout des options correspondant aux cartes de la liste
        List<String> choices = hand.stream().filter(filter).map(c -> "HAND:" + c.getName())
                .collect(Collectors.toList());
        String choice = choose(instruction, choices, new ArrayList<>(), canPass);
        if (choice.startsWith("HAND:")) {
            return hand.stream()
                    .filter(c -> c.hasName(choice.split(":")[1]))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Version de la méthode {@code chooseCardFromHand} sans prédicat. Toutes les
     * cartes de la main du joueur sont considérées comme choix valides.
     *
     * @param instruction
     * @param canPass
     * @return
     */
    public Card chooseCardFromHand(String instruction, boolean canPass) {
        return chooseCardFromHand(instruction, c -> true, canPass);
    }

    /**
     * Attend une entrée de la part du joueur et renvoie le choix du joueur.
     * <p>
     * Cette méthode est similaire à {@code chooseCardFromHand} mais elle fait
     * choisir une carte parmi les cartes disponibles dans la réserve du jeu
     * (uniquement les piles de réserve contenant au moins une carte).
     * <p>
     * Exemple d'utilisation pour faire choisir une carte sur le dessus d'une pile
     * de réserve qui coûte 4 pièces ou moins (dans cet exemple le joueur est
     * autorisé à passer s'il ne veut pas choisir de carte) :
     *
     * <pre>
     * Card choice = p.chooseCardFromSupply(
     *         "Choose a card costing up to 4",
     *         c -> c.getCost() <= 4,
     *         true);
     * </pre>
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     *                    la nature du choix qui est attendu
     * @param filter      prédicat permettant de filtrer les cartes disponibles dans
     *                    la réserve du jeu. Seules les cartes pour lesquelles le
     *                    prédicat renvoie {@code true} seront considérées comme
     *                    choix valides.
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix.
     * @return la carte du dessus de la pile de réserve choisie par le joueur ou
     *         {@code null} si le joueur a choisi de passer ou s'il n'avait aucune
     *         carte valide dans sa main.
     */
    public Card chooseCardFromSupply(String instruction, Predicate<Card> filter, boolean canPass) {
        List<String> choices = game.getAvailableSupplyCards().stream()
                .filter(filter)
                .map(c -> "SUPPLY:" + c.getName())
                .collect(Collectors.toList());

        String choice = choose(instruction, choices, new ArrayList<>(), canPass);
        if (choice.startsWith("SUPPLY:")) {
            return getCardFromSupply(choice.split(":")[1]);
        }
        return null;
    }

    /**
     * Demande au joueur de choisir une carte parmi une liste passée en argument.
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur la
     *                    nature du choix qui est attendu
     * @param cards       liste des cartes parmi lesquelles le joueur doit choisir.
     *                    Pour chaque carte de la liste, un bouton portant le nom de
     *                    la carte est affiché à l'écran.
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix.
     * @return la carte choisie par le joueur ou {@code null} si le joueur a choisi
     *         de passer ou si la liste de cartes était vide.
     */
    public Card chooseCardFromButtons(String instruction, List<Card> cards, boolean canPass) {
        // liste de noms de cartes
        List<Button> buttons = new ArrayList<>();
        // ajout des options correspondant aux cartes de la liste
        for (Card c : cards)
            buttons.add(new Button(c.getName(), c.getName()));

        String choice = choose(instruction, new ArrayList<>(), buttons, canPass);
        if (choice.startsWith("BUTTON:")) {
            for (Card c : cards) {
                if (c.hasName(choice.split(":")[1])) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Demande au joueur de choisir une option parmi une liste de boutons affichés à
     * l'écran.
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur la
     *                    nature du choix qui est attendu
     * @param buttons     liste des boutons à afficher à l'écran. Chaque bouton
     *                    correspond à une option de choix qui sera ajoutée à la
     *                    liste des choix valides ({@code "BUTTON:<value>"}).
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix.
     * @return la valeur du bouton choisi par le joueur ou {@code null} si le joueur
     *         a choisi de passer ou si la liste de boutons était vide.
     */
    public String chooseStringFromButtons(String instruction, List<Button> buttons, boolean canPass) {
        String choice = choose(instruction, new ArrayList<>(), buttons, canPass);
        if (choice.startsWith("BUTTON:")) {
            return choice.split(":")[1];
        }
        return null;
    }

    /**
     * Ajoute un message dans le log du jeu qui est affiché dans l'interface
     * graphique.
     *
     * @param message message à ajouter au log du jeu (peut contenir du HTML pour le
     *                formatage)
     */
    public void log(String message) {
        game.log(message);
    }

    /**
     * Exécute le tour d'un joueur
     * <p>
     * Cette méthode exécute successivement les phases du tour d'un joueur:
     * <p>
     * 1. (Préparation) initialise les compteurs d'actions, d'achats et d'argent du
     * joueur
     * <p>
     * 2. (Action, Trésor et Achat) Le joueur peut jouer des cartes Action et Trésor
     * de sa main, et acheter des cartes de la réserve. Cependant, dès qu'il joue
     * une carte Trésor, il ne peut plus jouer de carte Action pendant le reste de
     * son tour. De même, dès qu'il achète une carte, il ne peut plus jouer de carte
     * Action ni de carte Trésor pendant le reste de son tour.
     * <p>
     * Le joueur peut passer pour terminer son tour. Pour fluidifier le jeu, le tour
     * se termine également automatiquement lorsque le joueur n'a plus d'achat
     * disponible.
     */
    public void playTurn() { // squellette à reprendre !!!!
        numberOfActions = 1;
        numberOfBuys = 1;
        money = 0;

        GainCardDuringPlayTurn = new ArrayList<>();
        GainCardNameDuringPlayTurn = new ArrayList<>();
        nbConsecutifTurn++;

        for (Card c : getCardsInPlay()){
            c.atStartOfTurn(this);
        }

        boolean canPlayAction = true;
        boolean canPlayTreasures = true;

        while(numberOfBuys > 0) {

            //préparation de la liste de tous les choix possibles
            List<String> choiceOptions = new ArrayList<>();
            for(Card c : hand) {
                //cartes actions qui peuvent être jouées depuis la main
                if(canPlayAction && numberOfActions > 0 && c.hasType(CardType.ACTION)) {
                    choiceOptions.add("HAND:" + c.getName());
                }

                // cartes trésors qui peuvent être jouées depuis la main
                if(canPlayTreasures && c.hasType(CardType.TREASURE)) {
                    choiceOptions.add("HAND:" + c.getName());
                }
            }

            for(Card c : game.getAvailableSupplyCards()) {
                // cartes qui peuvent être achetées de la réserve
                if (c.getCost() <= money) {
                    choiceOptions.add("SUPPLY:" + c.getName());
                }
            }

            // le joueur doit choisir une option (ou passer)
            String choice = choose("Play your turn", choiceOptions, new ArrayList<>(), true);

            // exécution du choix du joueur
            if(choice.isEmpty()) {
                //le joueur passe, son tour se termine
                break;
            } else if(choice.startsWith("HAND:")) {
                //il joue une carte de sa main
                String cardName = choice.split(":", 2)[1];
                Card card = null;
                for(Card c:hand) {
                    if(c.hasName(cardName)){
                        card = c;
                        break;
                    }
                }

                if(card.hasType(CardType.ACTION)) {
                    // c'est une carte action
                    numberOfActions -= 1;
                    playCard(card);
                } else if(card.hasType(CardType.TREASURE)) {
                    canPlayAction = false;
                    playCard(card);
                }
            } else if (choice.startsWith("SUPPLY:")) {
                canPlayAction = false;
                canPlayTreasures = false;
                String cardName = choice.split(":", 2)[1];
                Card card = getCardFromSupply(cardName);
                numberOfBuys -=1;
                money -= card.getCost();
                gainTo(card, discard);
                checkEmbargo(card.getName()); // effet embargo lors d'achat d'une carte
            }
        }

        // reset des infos pour smugglers
        game.getlastPlayerGainCard().reset(this);
        game.getlastPlayerGainCard().addCardName(GainCardNameDuringPlayTurn);
        game.getlastPlayerGainCard().addCard(GainCardDuringPlayTurn);
    }

    /**
     * Fin du tour du joueur
     * <p>
     * Cette méthode exécute la phase de "Clean-up" à la fin du tour d'un joueur:
     * - Les compteurs d'actions, argent et achats du joueur sont remis à 0
     * - Les cartes en main et en jeu sont défaussées (sauf les cartes Duration qui
     * ont encore un effet)
     * - Le joueur pioche les cartes de sa prochaine main (normalement 5 cartes,
     * mais parfois moins selon les effets de certaines cartes)
     */
    public void cleanup() { // A modif pour les cartes durations

        // mise à 0 des compteurs
        this.numberOfActions = 0;
        this.money = 0;
        this.numberOfBuys = 0;
        boolean hasOutpost = false;
        // code pour la carte treasury (check)
        boolean hasVictory = false;
        for(Card c : game.getlastPlayerGainCard().getCardGainLastTurn()){
            if(c.hasType(CardType.VICTORY)) {
                hasVictory = true;
            }
        }

        // defause des cartes (sauf cartes duration)
        while(!hand.isEmpty())  {
            hand.getLast().moveTo(discard);
        }
        for(Card c : getCardsInPlay()){
            if(!c.hasDurationEffect()){

                c.moveTo(discard);
            }
            else {
                c.launcheEffect();
                if(c.hasName("Outpost")){
                    hasOutpost = true;
                }
            }

            if(c.hasName("Treasury") && !hasVictory) {
                c.treasuryEffect(this);
            }

        }

        // prioche les cartes
        int nbCard = hasOutpost ? 3 : 5;
        for (int i = 0 ; i < nbCard ; i++){
            Card c = getCardFromDeck();
            if (c != null) {
                c.moveTo(hand);
            }
        }

        if(hasOutpost && nbConsecutifTurn < 2) {game.playAgain();} else {nbConsecutifTurn = 0;}

    }
    public void addGainCardNameDuringPlayTurn(String cardName) {
        GainCardNameDuringPlayTurn.add(cardName);

        // Code pour monkey
        List<Player> allP = game.getAllPlayers();
        int indexOfActuelPlayer = game.getPlayerIndex(this);
        Player nextPlayer = allP.get((indexOfActuelPlayer + 1) % (allP.size()));
        for(Card card : nextPlayer.getCardsInPlay()) {
            if(card.hasName("Monkey")) {
                card.monkeyEffect(nextPlayer);
            }
        }
    }

    public void addGainCardDuringPlayTurn(Card c) {
        GainCardDuringPlayTurn.add(c);
    }


    public void incrementBuy(int i) {
        this.numberOfBuys += i;
    }

    private void checkEmbargo(String supply) {
        for (Embargo c : game.getAffectedEmbargo()) {
            if(c.getAffectedSupply().equals(supply)) {
                gainTo(getCardFromSupply("Curse"), discard);
            }
        }
    }

    public String chooseStringFromSupply(String instruction,  boolean canPass) {
        List<String> choices = game.getAvailableSupplyCards().stream()
                .map(c -> "SUPPLY:" + c.getName())
                .collect(Collectors.toList());

        String choice = choose(instruction, choices, new ArrayList<>(), canPass);
        if (choice.startsWith("SUPPLY:")) {
            return choice.split(":")[1];
        }
        return null;
    }
}
