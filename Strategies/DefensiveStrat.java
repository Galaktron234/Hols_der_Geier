package Strategies;

import java.util.ArrayList;

public class DefensiveStrat extends Astrategy{
    @Override
    int playCard(ArrayList<Integer> myAvailableCards, int nextPointCard) {
        return 0;
    }

    @Override
    int playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard) {
        return 0;
    }
}
