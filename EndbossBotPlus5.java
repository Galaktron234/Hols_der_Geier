import java.util.ArrayList;

public class EndbossBotPlus5 extends HolsDerGeierSpieler {

        private ArrayList<Integer> nochNichtGespielt=new ArrayList<Integer>();


        public void reset() {
            nochNichtGespielt.clear();
            for (int i=1;i<=15;i++)
                nochNichtGespielt.add(i);
        }

        private int spieleKarte(int x) {
            return switch (x) {
                case -5 -> 5;
                case -4 -> 4;
                case -3 -> 3;
                case -2 -> 2;
                case -1 -> 1;
                case 1 -> 6;
                case 2 -> 7;
                case 3 -> 8;
                case 4 -> 9;
                case 5 -> 10;
                case 6 -> 11;
                case 7 -> 12;
                case 8 -> 13;
                case 9 -> 14;
                default -> 15;
            };
        }

        public int gibKarte(int naechsteKarte) {

            return spieleKarte(naechsteKarte);

        }
    }



