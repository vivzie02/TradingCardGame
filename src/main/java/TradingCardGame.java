public class TradingCardGame {
    public static void main(String[] args) {

        User A = new User("Arnold", "Test");
        User B = new User("Bernd", "Test");
        Battle rounds = new Battle();

        A.setDeck();
        B.setDeck();

        int counter = 0;
        while(rounds.fight(A, B)){
            System.out.printf("\n");
            counter++;
            if(counter > 99){
                System.out.printf("\nDraw");
                break;
            }
        }
    }
}
