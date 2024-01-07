package Strategies;

import java.util.ArrayList;

public abstract class Astrategy {

    protected CardManager cm;
    protected abstract int playCard(ArrayList<Integer> myAvailableCards, int nextPointCard);
    protected abstract int playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard);

}
