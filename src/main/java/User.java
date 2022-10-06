import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private int coins = 20;
    private List<Card> deck = new ArrayList<Card>();
    private int elo = 100;
    private Stack cardsInStack;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck() {
        int numberOfMonsters = ((int) (Math.random() * 5));
        for(int i = 0; i < numberOfMonsters; i++){
            this.deck.add(new MonsterCard());
        }
        for(int j = numberOfMonsters; j < 4; j++){
            this.deck.add(new MagicCard());
        }

        System.out.println(this.username + "´s deck:");
        this.getDeck().forEach((card -> {
            System.out.println(card.getElementType() + " " + card.getName());
        }));
        System.out.println("");
    }
}
