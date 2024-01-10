
import Strategies.*;
import java.util.Random;
import java.util.ArrayList;

public class testKlasse {
    public static void main(String[] args) {


    CardManager cm = new CardManager();


        ArrayList<Integer> myAvailableCards = new ArrayList<Integer>();
        myAvailableCards.add(2);
        myAvailableCards.add(4);
        myAvailableCards.add(5);
        myAvailableCards.add(1);

        Random random = new Random();
        int nextPointCard = 4;
        int retCard;
        int varianz = 0;
        int lowerBound;
        int upperBound;
        do {
            int adjustedPointCard = (nextPointCard > 0) ? nextPointCard + 5 : nextPointCard + 6;

            lowerBound = Math.max(1, (adjustedPointCard - varianz) % 15);
            upperBound = Math.min(15, adjustedPointCard + varianz);

            retCard = random.nextInt(lowerBound, upperBound + 1);
            varianz++;

        } while (!cm.cardIsAvailable(myAvailableCards, retCard));


        System.out.println(retCard);


    }
}
