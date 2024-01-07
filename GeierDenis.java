/**
 * @author Denis Pavlov (SWT, VIL)
 *
 * Beschreibung der com.company.Geier Klasse:
 * Das Prinzip dieses com.company.Geier-Bots ist, dass er nicht zufaellig spielt, der Bot merkt sich sowohl die Punktkarten
 * als auch seine Karten als auch gegnerische Karten und versucht von dieser Taktik die Karten konkret auszuspielen,
 * das heisst fuer jede Punktkarte gibt es eine bestimmte Karte.
 * Die Klasse enthaellt ungefaehr 120 Codezeilen (ohne Kommentare) und wurde recht einfach geschrieben.
 */

/* Hier sind die Imports (Biblioteken), die man fuer ein oder anderes Object braucht */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* Hier faengt unsere Klasse com.company.Geier an, die von der abstrakten Klasse "HolsDerGeierSpieler" erbt */
class GeierDenik extends HolsDerGeierSpieler {

    /* Hier sind die Listen, die man fuers Merken von Karten benoetigt */
    private final List<Integer> myNotPlayedCards = new ArrayList<>();
    private final List<Integer> enemyNotPlayedCards = new ArrayList<>();
    private final List<Integer> notPlayedPointCards = new ArrayList<>();

    /* Hier ist der Konstruktor der Klasse, der eine Methode namens "reset" aufruft */
    public GeierDenik() {
        reset();
    }

    /* Hier ist die Methode "reset()", also neu setzen, die nicht anders macht, als die Listen von Karten aufzufuellen */
    public void reset() {
        enemyNotPlayedCards.clear();
        myNotPlayedCards.clear();

        for (int i = 1; i <= 15; i++) {
            enemyNotPlayedCards.add(i);
            myNotPlayedCards.add(i);
        }

        notPlayedPointCards.clear();
        for (int i = -5; i <= 10; i++) {
            if (i == 0) {
                continue;
            }

            notPlayedPointCards.add(i);
        }
    }

    /* Hier ueberschreiben wir die abstrakte Methode namens "gibKarte" von der abstrakten Klasse "HolsDerGeierSpieler" */
    public int gibKarte(int nextPointCard) {

        /* Hier wird der Parameter (unsere Punktkarte) "nextPointCard", die von der Klasse "HolsDerGeier" zufaellig
        ausgespielt wurde, von der Liste "notPlayedPointCards" entfernt */
        notPlayedPointCards.removeIf(card -> card == nextPointCard);

        /* Hier wird der Variable "lastTurn" die letzte ausgespielte gegnerische Karte zugewiesen */
        int lastTurn = letzterZug();
        /* Hier wird die letzte Karte "lastTurn" von der Liste "enemyNotPlayedCards" entfernt */
        enemyNotPlayedCards.removeIf(card -> card == lastTurn);

        /* Hier wird die Methode "myTurn" zurueckgegeben */
        return myTurn(nextPointCard);
    }

    /* Hier ist die Methode "myTurn", die einen Parameter aufnimmt (die Punktkarte). In dieser Methode wurde
    die Hauptlogik des Bots geschrieben */
    private int myTurn(int nextPointCard) {
        int cardToRemove = 0;

        /* Hier spielt der Bot seine letzte Karte, wenn er in der Liste "myNotPlayedCards" nur ein einziges Element hat,
        * ansonsten spielt er einen der folgenden Faelle aus:
        * In erstem "else if" ist die Logik so aufgebaut, dass, wenn diese Punktkarten ausgespielt wurden,
        * wird die niedrigste Karte (siehe die Methode "removeMinNotPlayedCard()") von der Liste "myNotPlayedCards"
        * ausgespielt, wobei die Gleichheit der Punktkarten mit "contains" ueberprueft wird.
        * In zweitem "else if" ist die Logik die gleiche, hier wird aber eine spezielle Karte (siehe die Methode
        * "removeCardFirstSpecialCase()") ausgespielt.
        * In drittem "else if" wird die hoechste Karte (siehe die Methode "removeMaxNotPlayedCard()") ausgespielt.
        * In viertem "else if" wird noch eine spezielle Karte (siehe die Methode "removeCardSecondSpecialCase()")
        * ausgespielt. */
        int myCardsCount = myNotPlayedCards.size();
        if (myCardsCount == 1) {
            cardToRemove = myNotPlayedCards.get(0);
        } else if (Arrays.asList(-5, -4, -3, -2, -1, 1, 2, 3).contains(nextPointCard)) {
            cardToRemove = removeMinNotPlayedCard();
        } else if (Arrays.asList(4, 5).contains(nextPointCard)) {
            cardToRemove = removeCardFirstSpecialCase(nextPointCard);
        } else if (Arrays.asList(6, 7, 8).contains(nextPointCard)) {
            cardToRemove = removeMaxNotPlayedCard();
        } else if (Arrays.asList(9, 10).contains(nextPointCard)) {
            cardToRemove = removeCardSecondSpecialCase(nextPointCard);
        }

        return cardToRemove;
    }

    /* In dieser Methode wird ein Spezialfall behandelt. Als Parameter "nextPointCard" wird
    * eine Punktkarte (4 oder 5) aufgenommen */
    private int removeCardFirstSpecialCase(int nextPointCard) {
        int cardToRemove;

        /* Hier wird die maximale Punktkarte ueberprueft, ob sie gleich unser Parameter "nextPointCard" ist.
        * Wenn das der Fall ist, spielen wir "nextPointCard" * 3 (also, bspw. 4*3 oder 5*3), wobei diese Karte, die
        * wir aus der obengenannten Formel berechnet haben, nach der Existenz (siehe die Methode "cardIsExist()")
        * ueberprueft wird.
        * Es wird genau das gleiche fuer die anderen ifs gemacht. Wenn es der Fall ist, wird diese Karte ausgespielt und
        * von der Liste "myNotPlayedCards" entfernt (siehe die Methode "removeCard(int cardToRemove)"). Wenn es nicht
        * der Fall ist, wird die niedrigste Karte ausgespielt */
        int maxNotPlayedCard = Collections.max(notPlayedPointCards);

        if (maxNotPlayedCard == nextPointCard) {
            cardToRemove = nextPointCard * 3;
            if (cardIsExist(cardToRemove)) {
                return removeCard(cardToRemove);
            }

            cardToRemove = nextPointCard * 3 - 1;
            if (cardIsExist(cardToRemove)) {
                return removeCard(cardToRemove);
            }
        }

        cardToRemove = nextPointCard * 3 - 2;
        if (cardIsExist(cardToRemove)) {
            return removeCard(cardToRemove);
        }

        cardToRemove = nextPointCard * 3 - 3;
        if (cardIsExist(cardToRemove)) {
            return removeCard(cardToRemove);
        }

        return removeMinNotPlayedCard();
    }

    /* In dieser Methode wird ein weiterer Spezialfall behandelt. Als Parameter "nextPointCard" wird
     * eine Punktkarte (9 oder 10) aufgenommen */
    private int removeCardSecondSpecialCase(int nextPointCard) {

        /* Hier wird eine weitere Formel verwendet: "nextPointCard" + 5 (also, bspw. 10+5 oder 9+5), wobei die Existenz
        * dieser Karte ueberprueft wird. Dabei wird die Existenz der ausgespielten gegnerischen Karten ueberprueft
        * (siehe die Methode "enemyCardNotExist()"). Wenn es der Fall ist, wird diese Karte ausgespielt und
        * von der Liste "myNotPlayedCards" entfernt (siehe die Methode "removeCard(int cardToRemove)").
        * Wenn es nicht der Fall ist, wird die niedrigste Karte ausgespielt */
        int cardToRemove = nextPointCard + 5;
        if (cardIsExist(cardToRemove)
                && enemyCardNotExist(14)
                && enemyCardNotExist(15)) {
            return removeCard(cardToRemove);
        }

        return removeMinNotPlayedCard();
    }

    /* In dieser Methode wird die niedrigste Karte von "myNotPlayedCards" gefunden */
    private int removeMinNotPlayedCard() {
        int minNotPlayedCard = Collections.min(myNotPlayedCards);
        return removeCard(minNotPlayedCard);
    }

    /* In dieser Methode wird die hoechste Karte von "myNotPlayedCards" gefunden */
    private int removeMaxNotPlayedCard() {
        int maxNotPlayedCard = Collections.max(myNotPlayedCards);
        return removeCard(maxNotPlayedCard);
    }

    /* In dieser Methode wird die Existenz der Karte von "myNotPlayedCards" ueberprueft */
    private boolean cardIsExist(int number) {
        return myNotPlayedCards.contains(number);
    }

    /* In dieser Methode wird die Existenz der gegnerischen Karte von "enemyNotPlayedCards" ueberprueft */
    private boolean enemyCardNotExist(int number) {
        return !enemyNotPlayedCards.contains(number);
    }

    /* Hier wird die Karte, die in den Parameter geschrieben wurde, von der Liste "myNotPlayedCards" entfernt */
    private int removeCard(int cardToRemove) {
        myNotPlayedCards.removeIf(card -> card == cardToRemove);
        return cardToRemove;
    }
}

