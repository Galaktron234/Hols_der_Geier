import java.util.ArrayList;

public class AlexBotV2 extends HolsDerGeierSpieler {
    private ArrayList<Integer> myAvailableCards = new ArrayList<>();
    private ArrayList<Integer> cardsToPlayThisGame = new ArrayList<>();
    private ArrayList<Integer> enemyAvailableCards = new ArrayList<>();
    private ArrayList<Integer> cardsPlayedByEnemy = new ArrayList<>();
    private int round;
    private int game;
    private final int gegnernr;

    public AlexBotV2() {
        game = 0;
        cardsPlayedByEnemy.clear();
        gegnernr = getNummer();
    }

    @Override
    public void reset() {
        myAvailableCards.clear();
        enemyAvailableCards.clear();
        cardsToPlayThisGame.clear();
        cardsToPlayThisGame.addAll(cardsPlayedByEnemy);
        cardsPlayedByEnemy.clear();
        round = 1;
        game++;

        for (int i = -5; i <= 10; i++) {
            if (i == 0) continue;
            enemyAvailableCards.add(i);
        }

        for (int i = 1; i <= 15; i++) {
            myAvailableCards.add(i);
        }
    }

    @Override
    public int gibKarte(int naechsteKarte) {
        int retKarte;

        if (game == 1) {
            int randomIndex = (int) (Math.random() * myAvailableCards.size());
            retKarte = myAvailableCards.remove(randomIndex);
        } else {
            retKarte = cardsToPlayThisGame.remove(0);
        }

        // Überprüfung auf Gültigkeit der gespielten Karte
        if (retKarte < 1 || retKarte > 15) {
            System.out.println("Ungueltige Karte gespielt: " + retKarte);
            int randomIndex = (int) (Math.random() * myAvailableCards.size());
            retKarte = myAvailableCards.remove(randomIndex);
        }

        kopiereGegnerKarte();

        round++;
        return retKarte;
    }


    private void kopiereGegnerKarte() {
        if (letzterZug() >= 0 && letzterZug() <= 15 && round != 15) {
            cardsPlayedByEnemy.add(letzterZug());
        } else if (round == 15) {
            cardsPlayedByEnemy.add(enemyAvailableCards.get(0));
        }
    }
}
