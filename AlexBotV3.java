import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import Strategies.*;
/* TODO:
    - Dokumentation/Kommentare
    - Strategy Handler und Logik, nach wie vielen Spielen Strategie gewechselt wird
    - Auslagern von Strategien in eigenes Package und eigene AbstractStrategy Klasse erstellen
    - verschiedene Strategien erstellen (Random, chance-aggressiv, chance-defensiv, dynamik-mapped, static-mapped)
    - Hilfsmethoden in CardManager auslagern und hier verknüpfen
    - Grafiken erstellen --> Mappings
 */
/**
 * Der AlexBotV3 versucht, seine Erfolgshistorie nachzuverfolgen und basierend darauf, eine Strategie auszuwählen, welche gewinnbringender ist, als die Aktuelle.
 * <p>
 * Ich habe mich für 5 Strategien entschieden, welche im separatem Package strategies liegen und alle von der abstrakten Klasse Astrategy erben.
 * <br>
 * Mein Bot nutzt einerseits simple Strategien (wie Zufalls- oder statisch-gemappte Strategien), aber hat auch dynamischere Strategien.
 * <p><p>
 * <b>Methodenübersicht für Hauptklasse AlexBotV3:</b><br>
 * <ul>
    * <li> reset(): Initialisiert die Listen der eigenen, gegnerischen Karten und Punktekarten.
    * <li> gibKarte(): Hauptmethode, gibt letztlich die zu spielende Karte aus<br>
    * <li> updateLists(int nextPointCard): Aktualisiert die Arraylisten basierend auf der aktuellen Punktekarte.
    * <li> playCard(int nextPointCard): Spielt eine Karte basierend auf der Strategie des Bots.
    * <li> playSpecialCase1(int nextPointCard): Behandelt den speziellen Fall für Punktekarten 4 und 5.
    * <li> playSpecialCase2(int nextPointCard): Behandelt den speziellen Fall für Punktekarten 9 und 10.
    * <li> playMinCard(): Spielt die niedrigste verfügbare Karte aus der Liste der eigenen Karten.
    * <li> playMaxCard(): Spielt die höchste verfügbare Karte aus der Liste der eigenen Karten.
    * <li> cardIsAvailable(int number): Überprüft, ob die Karte in der Liste der eigenen Karten verfügbar ist.
    * <li> enemyCardNotPlayed(int number): Überprüft, ob die gegnerische Karte nicht gespielt wurde.
    * <li> removeCard(int cardToRemove): Entfernt die gespielte Karte aus der Liste der eigenen Karten.
 * </ul>
 */

public class AlexBotV3 extends HolsDerGeierSpieler {

    /**
     * Hier werden die Listen für die eigenen Karten, gegnerischen Karten und verbleibende Punktekarten erstellt.
     */
    private final ArrayList<Integer> myAvailableCards = new ArrayList<>();
    private final ArrayList<Integer> enemyAvailableCards = new ArrayList<>();
    private final ArrayList<Integer> pointsLeftInGame = new ArrayList<>();

    /**
    *   Diese 3 Variablen sollen den Spielverlauf bzw. die bisherige Leistung von AlexBotV3 tracken und
    *   anhand der relativen Anzahl an Siegen (wonGames/playedGames) Strategieänderungen vornehmen,
    *   um nicht bei einer verlustbringenden Strategie festzustecken.
    */
    private double playedGames;
    private double wonGames;
    private double winPercentage;
    //Festgelegter Wert - unterschreitet winPercentage diesen Wert, ändert der StrategyHandler die Strategie von AlexBotV3
    private final double strategyThreshold = 0.55;

    /**
     *  Konstruktor für den AlexBotV3 - beim Instanziieren eines AlexBotV3-Objekts in der Startklasse, wird die Methode reset() aufgerufen.
     */
    public AlexBotV3() {
        reset();
    }


    /**
    *  Die abstrakte Methode reset() aus der Klasse "HolsDerGeierSpieler" wird hier überschrieben.<br>
    *  Zuerst werden alle Arraylisten zurückgesetzt, danach wieder mit den Punkte-/Spielerkarten befüllt.
    */
    @Override
    public void reset() {
        enemyAvailableCards.clear();
        myAvailableCards.clear();
        pointsLeftInGame.clear();

        //Spielerkarten
        for (int i = 1; i <= 15; i++) {
            enemyAvailableCards.add(i);
            myAvailableCards.add(i);
        }
        //Punktekarten
        for (int i = -5; i <= 10; i++) {
            if (i != 0) {
                pointsLeftInGame.add(i);
            }
        }
    }

    /**
     *  Die abstrakte Klasse gibKarte() aus der Klasse "HolsDerGeierSpieler" wird hier überschrieben.<br>
     *  Zunächst werden mit dem Methodenaufruf updateLists() und dem Eingabeparameter nextPointCard (also die aufgedeckte Karte)
     *  die Listen aktualisiert, welche den Spielverlauf tracken.<br>
     *  Danach wird die eigentliche Karte zurückgegeben, in dem man playCard() mit dem Parameter nextPointCard aufruft.
     */

    private void strategyHandler(){
        //TODO:: winPercantage berechnen und basieren darauf Strategie wehcseln und ggf. die gewinnbringendste Strategie speicher in einer "gerankten Liste/tabelle"
    }
    @Override
    public int gibKarte(int nextPointCard) {
        updateLists(nextPointCard);
        return playCard(nextPointCard);
    }

    /**
     *  Die Hilfsmethode updateLists() nimmt die aktuell aufgedeckte Karte entgegen
     *  und entfernt sie aus der Arrayliste, welche die verbleibenden Punktekarten trackt.<br>
     *  Außerdem wird die letzte vom Gegner gespielte Karte aus der Arrayliste entfernt,
     *  welche die verbleibenden gegnerischen Karten trackt
     */
    private void updateLists(int nextPointCard) {
         /*
            Mit der Funktion removeIf werden alle Elemente, die eine bestimmte Bedingung erfüllen aus der Arrayliste pointsLeftInGame entfernt.
            Der Parameter ist ein Lambda-Ausdruck (funktionale Programmierung):
            Jedes Element card in der Liste wird überprüft, ob es gleich nextPointCard ist, wenn ja, wird dieses Element gelöscht.
            Lambda-Ausdrücke verkürzen den Code und machen ihn lesbarer
         */
        pointsLeftInGame.removeIf(card -> card == nextPointCard);

        /*
            Hier wird nach dem gleichen Prinzip die Karte aus der Liste enemyAvailableCards entfernt,
            welche letzte Runde vom Gegner gespielt wurde.
         */
        enemyAvailableCards.removeIf(card -> card == letzterZug());
    }

    /**
     * Die Methode playCard() beinhaltet die Hauptlogik des Bots.
     * @param nextPointCard aufgedeckte Karte
     * @return Integer - Karte die diesen Zug gespielt werden soll
     */
    private int playCard(int nextPointCard) {
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
            return playMinCard();
        }
        /*
        * Wenn die aufgedeckte Karte eine 4 oder 5 ist, wird die Methode playSpecialCase1() aufgerufen
        */
        else if (Arrays.asList(4, 5).contains(nextPointCard)) {
            return playSpecialCase1(nextPointCard);
        }
       /*
       * Wenn die aufgedeckte Karte eine 6,7 oder 8 ist, wird die Methode playMaxCard() aufgerufen
       */
        else if (Arrays.asList(6, 7, 8).contains(nextPointCard)) {
            return playMaxCard();
        }
        /*
        * Wenn die Bedingungen davor nicht erfüllt wurden, heißt es das die aufgedeckte Karte 9 oder 10 ist.
        * Es wird die zweite spezielle Strategie benutzt und playSpecialCase2() aufgerufen
        */
        else{
            return playSpecialCase2(nextPointCard);
        }
    }

    //TODO: Strategien in extra Klasse "AlexBotV3 Strategien" auslagern
    /**
     * Die Methode playSpecialCase1 behandelt den Fall "4 oder 5 aufgedeckt"
     */
    private int playSpecialCase1(int nextPointCard) {

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
                if (cardIsAvailable(card)) {
                    return removeCard(card);
                }
            }
        }

        /* Wenn die gewünschten Karten nicht mehr auf der hand liegen, wird mit Hilfe playMinCard() die niedrigste verfügbare Karte ausgespielt. */
        return playMinCard();
    }

    /**
     * Die Methode playSpecialCase2 behandelt den Fall "9 oder 10 aufgedeckt"
     */
    private int playSpecialCase2(int nextPointCard) {
        // Im optimalen Fall, soll bei den höchsten Punktekarten, die höchsten Karten ausgespielt werden.
        int card = nextPointCard + 5;

        /*
         *  Nun wird aber überprüft, ob wir die 15 bzw. 14 überhaupt haben
         *  und mit der Hilfsmethode enemyCardExists wird überprüft ob der Gegner die 14 und 15 nicht mehr im Deck hat.
         *  Wenn ja, wird die Hilfsmethode removeCard() aufgerufen.
        */
        if (cardIsAvailable(card) && !enemyCardExists(14) && !enemyCardExists(15)) {
            return removeCard(card);
        }
        /*
         * Wenn wir die 14 bzw. 15 nicht haben oder der Gegner die 14 bzw 15 noch nicht ausgespielt hat,
         * wird die niedrigste Karte ausgespielt.
         */
        return playMinCard();
    }

    /**
     * In der Methode playMinCard() wird unsere niedrigste noch verfügbare Karte an die Hilfsmethode
     * removeCard() übergeben.
     */
    private int playMinCard() {
        return removeCard(Collections.min(myAvailableCards));
    }

    /**
     * In der Methode playMinCard() wird unsere höchste noch verfügbare Karte an die Hilfsmethode
     * removeCard() übergeben.
     */
    private int playMaxCard() {
        return removeCard(Collections.max(myAvailableCards));
    }

    /**
     * Die Hilfsmethode cardIsAvailable() gibt an, ob die übergebene Karte noch auf der Hand liegt.
     */
    private boolean cardIsAvailable(int number) {
        return myAvailableCards.contains(number);
    }

    /**
     * Die Hilfsmethode enemyCardExists() gibt an, ob die übergebene Karte noch auf der Hand des Gegners liegt.
     */
    private boolean enemyCardExists(int number) {
        return enemyAvailableCards.contains(number);
    }

    /**
     * Die Hilfsmethode removeCard() löscht die vom Bot zu spielende Karte aus der Hand
     * und übergibt die Karte auf die Methode, die die Hilfsmethode aufgerufen hat.
     */
    private int removeCard(int cardToRemove) {
        myAvailableCards.removeIf(card -> card == cardToRemove);
        return cardToRemove;
    }
}
