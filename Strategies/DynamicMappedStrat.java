package Strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DynamicMappedStrat extends Astrategy{

    public DynamicMappedStrat(){
        this.cm = new CardManager();
    }

    /**
     * Diese Methode wird nicht benutzt, da wir die playCard() Methode mit 4 Parametern brauchen.
     */
    @Override
    protected int playCard(ArrayList<Integer> myAvailableCards, int nextPointCard) {
        return 0;
    }

    /**
     * Die Methode playCard() beinhaltet die Hauptlogik der dynamic-mapped Strategie.
     */
    @Override
    public int playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard) {
        // Wenn es nur eine übrige Karte gibt, wird diese ausgespielt.
        if (myAvailableCards.size() == 1) {
            return myAvailableCards.get(0);
        }
        /*
         * Wenn die aufgedeckte Karte, eine Karte zwischen -5 und 3 ist,
         * wird die Methode playMinCard() aufgerufen und die resultierende Karte ausgespielt.
         * Mithilfe der Methode Arrays.asList wird eine Liste von Integerwerten erzeugt,
         * mit contains() wird überprüft, ob die aufgedeckte Karte in dieser Liste ist.
         */
        if (Arrays.asList(-5, -4, -3, -2, -1, 1, 2, 3).contains(nextPointCard)) {
            return playMinCard(myAvailableCards);
        }
        /*
         * Wenn die aufgedeckte Karte eine 4 oder 5 ist, wird die Methode playSpecialCase1() aufgerufen
         */
        else if (Arrays.asList(4, 5).contains(nextPointCard)) {
            return playSpecialCase1(pointsLeftInGame, myAvailableCards, nextPointCard);
        }
        /*
         * Wenn die aufgedeckte Karte eine 6,7 oder 8 ist, wird die Methode playMaxCard() aufgerufen
         */
        else if (Arrays.asList(6, 7, 8).contains(nextPointCard)) {
            return playMaxCard(myAvailableCards);
        }
        /*
         * Wenn die Bedingungen davor nicht erfüllt wurden, heißt es das die aufgedeckte Karte 9 oder 10 ist.
         * Es wird die zweite spezielle Strategie benutzt und playSpecialCase2() aufgerufen
         */
        else{
            return playSpecialCase2(myAvailableCards,enemyAvailableCards,nextPointCard);
        }
    }

    /**
     * Die Methode playSpecialCase1 behandelt den Fall "4 oder 5 aufgedeckt"
     */
    private int playSpecialCase1(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, int nextPointCard) {

        // Mit der Methode Collections.max() wird überprüft,
        // welche die höchste verbleibende Punktekarte im Spiel (bzw. im Punktekartenstapel) ist
        int maxCard = Collections.max(pointsLeftInGame);

        /*
         *  Wenn die aufgedeckte Karte, die Höchste im verbleibenden Stapel ist,
         *  wird nach folgender Formel die zu spielende Karte bestimmt:
         *  aufgedeckteKarte * 3 - i, was dazu führt, dass wir von der höchsten Karte abwärts eine Karte auswählen,
         *  die überhaupt noch in unserer Hand liegt.
         *  Die Hilfsmethode cardIsAvailable() überprüft, ob eine Karte in unserer Hand liegt.
         *  Wenn eine Karte berechnet wurde, die in der Hand liegt,
         *  wird diese mit der Hilfsmethode removeCard() zurückgegeben (ausgespielt).
         */
        if (maxCard == nextPointCard) {
            for (int i = 0; i <= 3; i++) {
                int card = nextPointCard * 3 - i;
                if (cm.cardIsAvailable(myAvailableCards,card)) {
                    return cm.removeCard(myAvailableCards,card);
                }
            }
        }

        /* Wenn die gewünschten Karten nicht mehr auf der hand liegen, wird mit Hilfe playMinCard() die niedrigste verfügbare Karte ausgespielt. */
        return playMinCard(myAvailableCards);
    }

    /**
     * Die Methode playSpecialCase2 behandelt den Fall "9 oder 10 aufgedeckt"y
     */
    private int playSpecialCase2(ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard) {
        // Im optimalen Fall, soll bei den höchsten Punktekarten, die höchsten Karten ausgespielt werden.
        int card = nextPointCard + 5;

        /*
         *  Nun wird aber überprüft, ob wir die 15 bzw. 14 überhaupt haben
         *  und mit der Hilfsmethode enemyCardExists wird überprüft ob der Gegner die 14 und 15 nicht mehr im Deck hat.
         *  Wenn ja, wird die Hilfsmethode removeCard() aufgerufen.
         */
        if (cm.cardIsAvailable(myAvailableCards, card) && !cm.enemyCardExists(enemyAvailableCards, 14) && !cm.enemyCardExists(enemyAvailableCards,15)) {
            return cm.removeCard(myAvailableCards, card);
        }
        /*
         * Wenn wir die 14 bzw. 15 nicht haben oder der Gegner die 14 bzw 15 noch nicht ausgespielt hat,
         * wird die niedrigste Karte ausgespielt.
         */
        return playMinCard(myAvailableCards);
    }

    /**
     * In der Methode playMinCard() wird unsere niedrigste noch verfügbare Karte an die Hilfsmethode
     * removeCard() übergeben.
     */
    private int playMinCard(ArrayList<Integer> myAvailableCards) {
        return cm.removeCard(myAvailableCards, Collections.min(myAvailableCards));
    }

    /**
     * In der Methode playMinCard() wird unsere höchste noch verfügbare Karte an die Hilfsmethode
     * removeCard() übergeben.
     */
    private int playMaxCard(ArrayList<Integer> myAvailableCards) {
        return cm.removeCard(myAvailableCards, Collections.max(myAvailableCards));
    }


}
