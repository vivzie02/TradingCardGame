package at.fhtw.swen1.mcg.dto;

import at.fhtw.swen1.mcg.persistence.UserRepository;

import java.util.Scanner;

public class Battle {
    public boolean fight(User player1, User player2){
        UserRepository userRepository = new UserRepository();

        int rand1 = ((int) (Math.random() * player1.getDeck().size()));
        int rand2 = ((int) (Math.random() * player2.getDeck().size()));
        Card card1 = player1.getDeck().get(rand1);
        Card card2 = player2.getDeck().get(rand2);

        if(card1 instanceof MonsterCard && card2 instanceof MonsterCard){
            pureMonsterBattle(player1, player2, card1, card2, rand1, rand2);
        } else if (card1 instanceof MagicCard && card2 instanceof MagicCard) {
            pureMagicBattle(player1, player2, card1, card2, rand1, rand2);
        } else {
            mixedBattle(player1, player2, card1, card2, rand1, rand2);
        }

        if(player1.getDeck().isEmpty()){
            System.out.printf("\n" + player2.getUsername() + " wins\n");
            player1.setElo(10);
            player2.setElo(-5);
            userRepository.updateElo(player2, player1);
            return false;
        }
        if(player2.getDeck().isEmpty()){
            System.out.printf("\n" + player1.getUsername() + " wins\n");
            player1.setElo(-5);
            player2.setElo(10);
            userRepository.updateElo(player1, player2);
            return false;
        }
        return true;
    }

    public boolean pureMonsterBattle(User player1, User player2, Card card1, Card card2, int rand1, int rand2){
        System.out.printf(player1.getUsername() + " " + card1.getElementType() + " " + card1.getName() +
                " (" + card1.getDamage() + ")");
        System.out.printf(player2.getUsername() + " " + card2.getElementType() + " " + card2.getName() +
                " (" + card2.getDamage() + ")");

        if(specialRules(card1, card2)){
            return true;
        }

        double damage1 = card1.getDamage();
        double damage2 = card2.getDamage();

        printFight(damage1, damage2, card1, card2, player1, player2, rand1, rand2);

        return false;
    }

    public void pureMagicBattle(User player1, User player2, Card card1, Card card2, int rand1, int rand2){
        System.out.printf(player1.getUsername() + " "+ card1.getElementType() + " " + card1.getName() +
                " (" + card1.getDamage() + ")");
        System.out.printf(player2.getUsername() + " " + card2.getElementType() + " " + card2.getName() +
                " (" + card2.getDamage() + ")");

        double ratio = effectiveness(card1.getElementType(), card2.getElementType());

        double damage1 = (card1.getDamage() * ratio);
        double damage2 = (card2.getDamage() / ratio);

        System.out.printf(" => " + damage1 + "VS" + damage2);
        printFight(damage1, damage2, card1, card2, player1, player2, rand1, rand2);
    }

    public void mixedBattle(User player1, User player2, Card card1, Card card2, int rand1, int rand2){
        System.out.printf(player1.getUsername() + " " + card1.getElementType() + " " + card1.getName() +
                " (" + card1.getDamage() + ")");
        System.out.printf(" vs. " + player2.getUsername() + " " + card2.getElementType() + " " + card2.getName() +
                " (" + card2.getDamage() + ")");

        double ratio = effectiveness(card1.getElementType(), card2.getElementType());
        double damage1, damage2;

        if(card1 instanceof MagicCard){
            damage1 = (card1.getDamage() * ratio);
            damage2 = card2.getDamage();
        }else {
            damage1 = card1.getDamage();
            damage2 = (card2.getDamage() / ratio);
        }

        System.out.printf(" => " + damage1 + "VS" + damage2);
        printFight(damage1, damage2, card1, card2, player1, player2, rand1, rand2);
    }

    public boolean specialRules(Card card1, Card card2){
        if(((card1.getName() == "Goblin" && card1.getDamage() > card2.getDamage())
                || (card2.getName() == "Goblin" && card2.getDamage() > card1.getDamage()))
                && (card1.getName() == "Dragon" || card2.getName() == "Dragon")){
            System.out.printf(" => Goblin too afraid of Dragon for fight");
            return true;
        } else if (((card1.getName() == "Ork" && card1.getDamage() > card2.getDamage())
                || (card2.getName() == "Ork") && card2.getDamage() > card1.getDamage())
                && (card1.getName() == "Wizard" || card2.getName() == "Wizard")) {
            System.out.printf(" => Ork controlled by Wizard and can´t attack");
            return true;
        } else if (((card1.getName() == "Dragon" && card1.getDamage() > card2.getDamage())
                || (card2.getName() == "Dragon") && card2.getDamage() > card1.getDamage())
                && ((card1.getName() == "Elf" && card1.getElementType() == "Fire")
                || (card2.getName() == "Elf" && card2.getElementType() == "Fire"))) {
            System.out.printf(" => Elf can evade the dragon");
            return true;
        } else if (((card1.getName() == "Robot" && card1.getDamage() < card2.getDamage())
                || (card2.getName() == "Robot") && card2.getDamage() < card1.getDamage())
                && (card1.getName() == "Knight" || card2.getName() == "Knight")) {
            System.out.printf(" => Robot can not be killed by a sword");
            return true;
        } else if (((card1.getName() == "Robot" && card1.getDamage() > card2.getDamage())
                || (card2.getName() == "Robot") && card2.getDamage() > card1.getDamage())
                && (card1.getElementType() == "Water" || card2.getElementType() == "Water")) {
            System.out.printf(" => Robot gets rusted by the water");
            return true;
        }
        return false;
    }

    public double effectiveness(String type1, String type2){
        double ratio = (Card.types.valueOf(type2).ordinal() - Card.types.valueOf(type1).ordinal());

        if(ratio < 0){
            ratio = (3 + ratio);
        }

        if(ratio == 1){
            return 2;
        } else if (ratio == 2) {
            return 0.5;
        }
        return 1;
    }

    public void printFight(double damage1, double damage2, Card card1, Card card2, User player1, User player2, int rand1, int rand2){
        if(damage1 > damage2){
            System.out.printf(" => " + card1.getName() + " defeats " + card2.getName());
            Card test = player2.getDeck().get(rand2);
            player2.getDeck().remove(rand2);
            player1.getDeck().add(test);
        } else if (damage1 < damage2) {
            System.out.printf(" => " + card2.getName() + " defeats " + card1.getName());
            Card test = player1.getDeck().get(rand1);
            player1.getDeck().remove(rand1);
            player2.getDeck().add(test);
        } else {
            System.out.printf(" => Draw");
        }
    }

    public String startBattle(User player1, User player2){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        if(player1.getDeck().size() < 4){
            System.out.println("Your deck contains too few cards");
            return "Too few cards in deck";
        }

        if(player2.getDeck().size() < 4){
            System.out.println("second players deck is too small");
            return "Too few cards in deck";
        }

        int counter = 0;
        while(fight(player1, player2)){
            System.out.printf("\n");
            counter++;
            if(counter > 99){
                System.out.printf("Draw\n");
                return "DRAW";
            }
        }
        if(player1.getDeck().isEmpty()){
            return player2.getUsername() + " wins!";
        }else {
            return player1.getUsername() + " wins!";
        }
    }
}
