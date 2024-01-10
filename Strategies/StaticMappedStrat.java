package Strategies;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *<p>
 *     Die Static Mapped Strategie ordnet jeder Punktekarte eine Karte zu, um auf min. 21 Punkte zu kommen und somit zu gewinnen.
 *</p><br>
 * Informationen über den Umgang mit Hashmaps habe ich mir <a href="https://www.w3schools.com/java/java_hashmap.asp"> hier </a> besorgt
 *
 * <b>Methodenübersicht der Aggressive Strategie</b>
 *
 <ul>
 *      <li> playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard): Spielt eine Karte basierend auf dem gehardcodetem Mapping.<br>
 *      (Anmerkung: Es gibt unzählige Varianten, so eine Zuordnung vorzunehmen, hier habe ich mich für eine von diesen entschieden)
 </ul>
 */
public class StaticMappedStrat extends Astrategy{

    /**
     * Aus Interessengründen habe ich mich für eine Hashmap für die Zuordnung entschieden. Hashmaps haben u.A. den Vorteil, dass die Suche nach Werten immer eine konstant ist.<br>
     *  Ebenfalls sind Hashmaps dynamisch/einfacher erweiterbar (z.B. im Vergleich zu einem "if-else-Baum").
     */
    private final HashMap<Integer, Integer> cardMapping = new HashMap<>();

    public StaticMappedStrat() {
        this.cm = new CardManager();

        /*
         * Die Methode put() fügt der Hashmap ein key:value-Paar hinzu;
         * Links die Punktkarten (key), rechts die dazugehörigen Spielerkarten (value)
         */
        cardMapping.put(-5, 4);
        cardMapping.put(-4, 12);
        cardMapping.put(-3, 10);
        cardMapping.put(-2, 9);
        cardMapping.put(-1, 7);
        cardMapping.put(1, 6);
        cardMapping.put(2, 8);
        cardMapping.put(3, 5);
        cardMapping.put(4, 14);
        cardMapping.put(5, 1);
        cardMapping.put(6, 11);
        cardMapping.put(7, 15);
        cardMapping.put(8, 13);
        cardMapping.put(9, 2);
        cardMapping.put(10, 3);
    }
    @Override
    public int playCard(ArrayList<Integer> myAvailableCards, int nextPointCard) {

        // Wenn es nur eine übrige Karte gibt, wird diese ausgespielt.
        if (myAvailableCards.size() == 1) {
            return myAvailableCards.getFirst();
        }

        /*
         * Mithilfe der Hilfsmethode removeCards() wird die entsprechende Karte zurückgegeben.
         * Die Hashmap-Methode get() gibt den Wert des dazugehörigen Schlüssels zurück (z.b. -5 → 4)
         */
        return cm.removeCard(myAvailableCards, cardMapping.get(nextPointCard));
    }





    // Diese Methode wird nicht verwendet
    @Override
    int playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard) {
        return 0;
    }
}
