package at.fhtw.swen1.mcg.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private String username;
    private int coins;
    private List<Card> deck = new ArrayList<Card>();
    private int elo;
    private List<Card> cardsInStack = new ArrayList<>();
    private int id;

    public User(String username, int coins, int id, int elo) {
        this.username = username;
        this.coins = coins;
        this.id = id;
        this.elo = elo;
    }

    public int getId(){ return id; }

    public int getElo() {
        return elo;
    }

    public void setElo(int score) {
        this.elo = (this.elo - (score));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCoins() {
        return coins;
    }

    public void spendCoins() {
        this.coins = coins - 5;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void deckSelect(){
        if(cardsInStack == null){
            System.out.println("You don't have any cards, buy some from the shop");
            return;
        }
        System.out.println("Here are all your cards:");
        int id = 1;
        for (Card card : cardsInStack) {
            System.out.print(id + " | ");
            System.out.println(card.getName() + " | " + card.getElementType() + " | " + card.getDamage());
            id++;
        }
        if(cardsInStack.size() < 4){
            System.out.println("can't build a deck yet");
            return;
        }
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("select your cards:");
        List<Integer> selected = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            int option = myObj.nextInt();  // Read user input
            if(selected.contains(option)){
                System.out.println("already selected");
                i--;
            }else {
                selected.add(option);
                this.deck.add(this.cardsInStack.get(option-1));
            }
        }

        System.out.println("your deck is now: ");
        for (Card card : deck) {
            System.out.print(id + " | ");
            System.out.println(card.getName() + " | " + card.getElementType() + " | " + card.getDamage());
        }
    }

    public void addToStack(Card addedCard){
        this.cardsInStack.add(addedCard);
    }
}
