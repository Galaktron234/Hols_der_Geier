package Strategies;

import java.util.ArrayList;
import java.util.Random;

/**
 *<p>
 *     Die Random Strategie spielt Karten auf Basis der aufgedeckten Karte. Dabei wird ein Zahlenbereich um die "verbesserte" aufgedeckte Karte geschaffen und zufällig aus diesem ausgewählt.
 *</p><br>
 *

 * <b>Methodenübersicht der Random Strategie</b>
 *
 <ul>
 *      <li> playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard): Spielt eine Karte basierend auf der aufgedeckten Karte und der Varianz um sie herum.
 </ul>
 */

public class RandomStrat extends Astrategy{

    private final Random random = new Random();

    public RandomStrat(){
        this.cm = new CardManager();
    }

    @Override
    public int playCard(ArrayList<Integer> myAvailableCards, int nextPointCard) {

        // Wenn es nur eine übrige Karte gibt, wird diese ausgespielt.
        if (myAvailableCards.size() == 1) {
            return myAvailableCards.getFirst();
        }

        // Es werden die Variablen retCard (Karte die gespielt werden soll), varianz, lowerBound, upperBound deklariert/initialisiert.
        int retCard;
        int varianz = 0;
        int lowerBound;
        int upperBound;


        // Eine do-while-Schleife sorgt für den größer werdenden Zahlenbereich, falls alle Karten aus dem vorherigen Bereich schon gelegt wurden.
        do {
            // Zuerst wird der Wert der Punktekarte künstlich angehoben. (5 -> 10, -5 -> 5)
            int adjustedPointCard = (nextPointCard > 0) ? nextPointCard + 5 : nextPointCard * (-1);

            //Hier werden die untere und obere Grenze des Zahlenbereiches um die Karte berechnet.

            /*
             * Die Methode Math.max nimmt immer den größeren Wert zweier Ganzzahlen (int).
             * Somit wird gewährleistet, dass die untere Grenze des Zahlenbereiches nicht kleiner als 1 ist (falls die Varianz zu hoch sein sollte).
             */
            lowerBound = Math.max(1, adjustedPointCard - varianz);

            /*
             * Die Methode Math.min nimmt immer den kleineren Wert zweier Ganzzahlen (int).
             * Somit wird gewährleistet, dass die obere Grenze des Zahlenbereiches nicht größer als 15 ist.
             */
            upperBound = Math.min(15, adjustedPointCard + varianz);

            // Anschließend wird eine zufällige Zahl (Karte) aus dem berechneten Zahlenbereich gewählt.
            retCard = random.nextInt(lowerBound, upperBound + 1);
            // Die Varianz wird erhöht, falls die gewählte Karte nicht mehr auf der Hand liegt.
            varianz++;

        } while (!cm.cardIsAvailable(myAvailableCards, retCard)); //Der Zahlenbereich wird so lang vergrößert, bis eine Karte gewählt wird, die noch auf der Hand liegt.

        //Am Ende wird die Karte mithilfe der Hilfsmethode removeCard() zurückgegeben.
        return cm.removeCard(myAvailableCards, retCard);
    }




// Die Methode wird nicht gebraucht, da wir die playCard() Methode mit 2 Parametern brauchen
@Override
    protected int playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard) {
        return 0;
    }
}
