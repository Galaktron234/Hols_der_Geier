package Strategies;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Die Static Mapped Strategie ordnet jeder Punktekarte eine Karte zu, um auf min. 21 Punkte zu kommen und somit zu gewinnen.<br>
 * Informationen über den Umgang mit Hashmaps habe ich mir <a href="https://www.w3schools.com/java/java_hashmap.asp"> hier </a> besorgt<br><br>
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

        // Es werden zwei Arrays mit den Punktekarten und den dazugehörigen Spielerkarten erstellt.
        final int[] punktekarten = {-5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final int[] spielerkarten = {4, 12, 10, 9, 7, 6, 8, 5, 14, 1, 11, 15, 13, 2, 3};

        /*
         * Die Methode put() fügt der Hashmap ein key:value-Paar hinzu;y
         * Links die Punktkarten (key), rechts die dazugehörigen Spielerkarten (value)
         */
        for (int i = 0; i < punktekarten.length; i++) {
            cardMapping.put(punktekarten[i], spielerkarten[i]);
        }

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
        return CardManager.removeCard(myAvailableCards, cardMapping.get(nextPointCard));
    }



    // Diese Methode wird nicht verwendet
    @Override
    int playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard) {
        return 0;
    }
}
