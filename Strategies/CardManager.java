package Strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CardManager {

    private final Random random = new Random();

    /**
     *  Die Hilfsmethode updateLists() nimmt die aktuell aufgedeckte Karte entgegen
     *  und entfernt sie aus der Arrayliste, welche die verbleibenden Punktekarten trackt.<br>
     *  Außerdem wird die letzte vom Gegner gespielte Karte aus der Arrayliste entfernt,
     *  welche die verbleibenden gegnerischen Karten trackt
     */
    public void updateLists(ArrayList<Integer> pointsLeftInGame,ArrayList<Integer> enemyAvailableCards, int letzterZug, int nextPointCard) {
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
        enemyAvailableCards.removeIf(card -> card == letzterZug);
    }


    /**
    * Die Hilfsmethode cardIsAvailable() gibt an, ob die übergebene Karte noch auf der Hand liegt.
    */
    public boolean cardIsAvailable(ArrayList<Integer> myAvailableCards, int number) {
       return myAvailableCards.contains(number);
   }

   /**
    * Die Hilfsmethode enemyCardExists() gibt an, ob die übergebene Karte noch auf der Hand des Gegners liegt.
    */
    public boolean enemyCardExists(ArrayList<Integer> enemyAvailableCards, int number) {
        return enemyAvailableCards.contains(number);
    }

    /**
     * Die Hilfsmethode removeCard() löscht die vom Bot zu spielende Karte aus der Hand
     * und übergibt die Karte auf die Methode, die die Hilfsmethode aufgerufen hat.
     */
    public int removeCard(ArrayList<Integer> myAvailableCards, int cardToRemove) {
        myAvailableCards.removeIf(card -> card == cardToRemove);
        return cardToRemove;
    }

    /**
     * In der Methode playMinCard() wird unsere niedrigste noch verfügbare Karte an die Hilfsmethode
     * removeCard() übergeben.
     */
     int playMinCard(ArrayList<Integer> myAvailableCards) {
        return removeCard(myAvailableCards, Collections.min(myAvailableCards));
    }

    /**
     * In der Methode playMinCard() wird unsere höchste noch verfügbare Karte an die Hilfsmethode
     * removeCard() übergeben.
     */
    int playMaxCard(ArrayList<Integer> myAvailableCards) {
        return removeCard(myAvailableCards, Collections.max(myAvailableCards));
    }

    int playRandomCard(ArrayList<Integer> myAvailableCards){
        return removeCard(myAvailableCards, myAvailableCards.get(random.nextInt(myAvailableCards.size())));
    }

}
