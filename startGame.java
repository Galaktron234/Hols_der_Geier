public class startGame {
    public static int punkte1 = 0;
    public static int punkte2 = 0;
    public static void main(String[] args) throws Exception {



        HolsDerGeier hdg = new HolsDerGeier();
        AlexBotV3 a1 = new AlexBotV3();
        GeierDenik a2 = new GeierDenik();
        EndbossBotPlus5 p1 = new EndbossBotPlus5();
        IntelligentererGeier g1 = new IntelligentererGeier();
        Random r1 = new Random();

        hdg.neueSpieler(a2, a1);

        final int games = 10000;


        for(int i = 0; i < games; i++) {
            hdg.ganzesSpiel();
            System.out.println("-------------------------------------");
        }
        hdg.getSpieler();
        System.out.println("Punktestand: \n Spieler 1: " + punkte1 + "\n Spieler 2: " + punkte2);
    }
}
