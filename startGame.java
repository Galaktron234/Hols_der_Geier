public class startGame {
    public static int punkte1 = 0;
    public static int punkte2 = 0;
    public static void main(String[] args) throws Exception {



        HolsDerGeier hdg = new HolsDerGeier();
        AlexBotV3 a1 = new AlexBotV3();
        EndbossBotPlus5 p1 = new EndbossBotPlus5();
        IntelligentererGeier g1 = new IntelligentererGeier();
        Random r1 = new Random();

        hdg.neueSpieler(a1, p1);

        final int games = 10;


        for(int i = 0; i < games; i++) {
            String gewinner = hdg.ganzesSpiel();
            System.out.println("gewinnner: " + gewinner);
            a1.setWinnerOfLastRound(gewinner);
        }
        hdg.getSpieler();
        System.out.println("Punktestand: \n Spieler 1: " + punkte1 + "\n Spieler 2: " + punkte2);
    }
}
