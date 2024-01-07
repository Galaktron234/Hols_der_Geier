import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class AlexBot extends HolsDerGeierSpieler{



        HolsDerGeier hdg;
        private ArrayList<Integer> nochNichtGespielt;
        private ArrayList<Integer> vomGegnerNochNichtGelegt;
        private ArrayList<Integer> meineStrategie;
        private ArrayList<Integer> mittelKarten;
        private int runde;

        public AlexBot(HolsDerGeier hdg){
            this.hdg = hdg;
            nochNichtGespielt = new ArrayList<>();
            vomGegnerNochNichtGelegt = new ArrayList<>();
            meineStrategie = new ArrayList<>();
            mittelKarten = new ArrayList<>();
            runde = 0;
        }
        @Override
        public void reset() {
            nochNichtGespielt.clear();
            vomGegnerNochNichtGelegt.clear();
            meineStrategie.clear();
            mittelKarten.clear();
            runde = 0;
            for (int i = 1; i <= 15; i++) {
                nochNichtGespielt.add(i);
                vomGegnerNochNichtGelegt.add(i);
            }
        }

        @Override
        public int gibKarte(int naechsteKarte) {
            int letzterZugGegner = letzterZug();

            if (letzterZugGegner != -99) {
                vomGegnerNochNichtGelegt.remove(Integer.valueOf(letzterZugGegner));
                nochNichtGespielt.remove(Integer.valueOf(letzterZugGegner));
            }

            int ausgabe = findBestMove(naechsteKarte);

            nochNichtGespielt.remove(Integer.valueOf(ausgabe));
            System.out.printf("Runde: " + runde + "\n");
            runde++;
            return ausgabe;
        }

        private int findBestMove(int naechsteKarte) {
            ArrayList<Integer> verfuegbareKarten = new ArrayList<>(nochNichtGespielt);

            // Sortiere die Karten in aufsteigender Reihenfolge
            Collections.sort(verfuegbareKarten);

            // Beispiel-Strategie: Spiele eine Karte, die höher oder gleich der nächsten Karte ist
            for (int karte : verfuegbareKarten) {
                if (karte >= naechsteKarte) {
                    return karte;
                }
            }

            // Wenn keine passende Karte gefunden wurde, spiele eine zufällige verfügbare Karte

                int randomIndex = (int) (Math.random() * verfuegbareKarten.size());
                return verfuegbareKarten.get(randomIndex);

        }

        private void zugDesGegners() {
            for (int i = 1; i <= 15; i++) {
                if (!vomGegnerNochNichtGelegt.contains(i)) {
                    vomGegnerNochNichtGelegt.add(i);
                }
            }
        }

    private void strategieBerechnung() {
        if (mittelKarten.isEmpty()) {
            // Handle den Fall, dass mittelKarten leer ist
            return ;
        }
        int totalRunden = mittelKarten.size() / 15; // Gesamtanzahl der bisherigen Runden
        if (totalRunden <= 7) {
            // Strategie für die ersten 5 Runden
            if (totalRunden % 2 == 0) {
                // In geraden Runden aggressiver spielen
                meineStrategie.add(15);
                meineStrategie.add(14);
                meineStrategie.add(13);
                meineStrategie.add(12);
                meineStrategie.add(11);
                meineStrategie.add(10);
            } else {
                // In ungeraden Runden defensiver spielen
                meineStrategie.add(1);
                meineStrategie.add(2);
                meineStrategie.add(3);
                meineStrategie.add(4);
                meineStrategie.add(5);
                meineStrategie.add(6);
            }
        } else {
            // Strategie nach den ersten 5 Runden ändern
            if (totalRunden % 3 == 0) {
                // In jeder dritten Runde die Taktik ändern
                Collections.shuffle(meineStrategie);
            } else {
                // Standardstrategie
                Collections.reverse(meineStrategie);
            }
        }
    }

}


