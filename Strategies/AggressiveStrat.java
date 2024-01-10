package Strategies;

import java.util.ArrayList;
import java.util.Random;

//TODO: vllt mit klassischem gemapptem (10 -> 1, 9->15,...) ausprobieren
/**
 *<p>
 *     Die Aggressive Strategie ist ausgelegt, hauptsächlich die höchste Karte im Deck zu legen. Anders als beim direkten Mapping (10->15, 9->14, etc.), ist diese Strategie durch eine Zufallskomponente unvorhersehbarer für den Gegner. *
 *</p><br>
 * <b>Methodenübersicht der Aggressive Strategie</b>
 *
 <ul>
 *      <li> playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard): Spielt eine Karte basierend auf der aggressiven Natur der Strategie
 </ul>
 */
public class AggressiveStrat extends Astrategy{
    private final Random random = new Random();
    public AggressiveStrat(){
        this.cm = new CardManager();
    }

    @Override
    public int playCard(ArrayList<Integer> myAvailableCards, int nextPointCard) {

        // Wenn es nur eine übrige Karte gibt, wird diese ausgespielt.
        if (myAvailableCards.size() == 1) {
            return myAvailableCards.getFirst();
        }
        // Es wird eine Zufallszahl von 0 bis 9 generiert
        int decision = random.nextInt(10);

        // Falls die Zahl kleiner 7 ist (also eine Wahrscheinlichkeit von 70 %), wird die höchste verfügbare Karte gelegt.
        if (decision < 7) {
            return  cm.playMaxCard(myAvailableCards);
        }
        // Falls die Zahl zwischen 7 und 8 liegt (also eine Wahrscheinlichkeit von 20 %), wird eine zufällige Karte gelegt.
        else if (decision < 9){
            return cm.playRandomCard(myAvailableCards);
        }
        // Andernfalls (restliche 10 %) wird die niedrigste Karte gelegt.
        else{
            return cm.playMinCard(myAvailableCards);
        }
    }

    //Unbenutzte Methode
    @Override
    int playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard) {
        return 0;
    }
}
