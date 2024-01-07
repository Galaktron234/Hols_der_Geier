package Strategies;

import java.util.ArrayList;

public class RandomStrat extends Astrategy{

    @Override
    protected int playCard(ArrayList<Integer> myAvailableCards, int nextPointCard) {

        //TODO: Entweder komplett random oder Varianz um die Punktekarte
        return 0;
}




// Die Methode wird nicht gebraucht, da wir die playCard() Methode mit 2 Parametern brauchen
@Override
    protected int playCard(ArrayList<Integer> pointsLeftInGame, ArrayList<Integer> myAvailableCards, ArrayList<Integer> enemyAvailableCards, int nextPointCard) {
        return 0;
    }
}
