import java.util.ArrayList;
import java.util.Random;

import Strategies.*;
/* TODO:
    - Dokumentation/Kommentare
    - Strategy Handler und Logik, nach wie vielen Spielen Strategie gewechselt wird
    - verschiedene Strategien erstellen (Random, chance-aggressiv, chance-defensiv, dynamik-mapped, static-mapped)
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
    *   anhand der relativen Anzahl an Verlusten (lostGames/playedGames) sollen Strategieänderungen vorgenommen werden,
    *   um nicht bei einer verlustbringenden Strategie festzustecken.
    */
    private double playedGames;
    private double lostGames;
    private double losePercentage;
    /** Nach jeder Runde wird ein Gewinner ausgegeben, dieser wird in dieser Variable gespeichert*/
    private String winnerOfLastRound;
    /**Festgelegter Wert - überschreitet losePercentage diesen Wert, ändert der StrategyHandler die Strategie von AlexBotV3*/
    private final double strategyThreshold = 0.55;
    private int currentStrategy;

    /**
     * Hier werden die 5 Strategien instanziiert, damit man auf die jeweiligen Strategien (und ihre playCard() Methoden) zugreifen kann.
     */
    private final AggressiveStrat aggr = new AggressiveStrat();
    private final DefensiveStrat def = new DefensiveStrat();
    private final DynamicMappedStrat dyn = new DynamicMappedStrat();
    private final StaticMappedStrat stat = new StaticMappedStrat();
    private final RandomStrat rand = new RandomStrat();

    /**
     * Ein Objekt der Klasse CardManager wird instanziiert, um auf die Hilfsmethoden für die Karten zuzugreifen.
     */
    CardManager cm = new CardManager();

    /**
     *  Konstruktor für den AlexBotV3 - beim Instanziieren eines AlexBotV3-Objekts in der Startklasse, wird die Methode reset() aufgerufen.
     */
    public AlexBotV3() {
        currentStrategy = 0;
    }

    public void setWinnerOfLastRound(String winnerOfLastRound){
        this.winnerOfLastRound = winnerOfLastRound;
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
    @Override
    public int gibKarte(int nextPointCard) {
        cm.updateLists(pointsLeftInGame,enemyAvailableCards,letzterZug(),nextPointCard);
        return strategyHandler(nextPointCard);
    }



    private int strategyHandler(int nextPointCard){

        //TODO: winPercentage berechnen und basieren darauf Strategie wechseln und ggf. die gewinnbringendste Strategie speicher in einer "gerankten Liste/tabelle"
        // Variable currentStrategy ändern (z.B. einfach "0,1,2,3,4,0..." Zyklus)

        // Wenn es einen Gewinner aus der letzten Runde gibt und es der Gegner war, wird die Variable lostGames
        if(winnerOfLastRound != null && !winnerOfLastRound.equals(this.getClass().getSimpleName())){
            //TODO: Formel für losePercentage und tracking von Games insgesammt
        }

        if(losePercentage > strategyThreshold){
            //TODO: Change currentStrategy
        }

        /*V1
        int retCard = 0;
        switch (currentStrategy){
            case 0: //Dynamic Mapped
                //Strategieaufruf von Klasse DynamicMapped
                retCard = dyn.playCard(pointsLeftInGame,myAvailableCards,enemyAvailableCards,nextPointCard);
                break;
            case 1: //Static Mapped
                //Strategieaufruf von Klasse StaticMapped
                retCard = stat.playCard();
                break;
            case 2: //Aggressive
                //Strategieaufruf von Klasse Aggressive
                retCard = aggr.playCard();
                break;
            case 3: //Defensive
                //Strategieaufruf von Klasse Defensive
                retCard = def.playCard();
                break;
            case 4: //Random
                //Strategieaufruf von Klasse Random
                retCard = rand.playCard();
                break;
        }
        return retCard;

        */
        /* V2
        int retCard = switch (currentStrategy) {
            case 0 -> //Dynamic Mapped
                    dyn.playCard(pointsLeftInGame, myAvailableCards, enemyAvailableCards, nextPointCard);
            case 1 -> //Static Mapped
                    stat.playCard();
            case 2 -> //Aggressive
                    aggr.playCard();
            case 3 -> //Defensive
                    def.playCard();
            case 4 -> //Random
                    rand.playCard();
            default -> 0;
        };
        return retCard;
         */

        // V3
//TODO Strategieaufrufe mit Parametern füllen
        return switch (currentStrategy) {
            case 0 -> //Dynamic Mapped
                    dyn.playCard(pointsLeftInGame, myAvailableCards, enemyAvailableCards, nextPointCard);
           /* case 1 -> //Static Mapped
                   stat.playCard();
            case 2 -> //Aggressive
                    aggr.playCard();
            case 3 -> //Defensive
                    def.playCard();
            case 4 -> //Random
                    rand.playCard();*/
            default -> 0;
        };

    }







}
