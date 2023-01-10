package at.fhtw.swen1.mcg.persistence;
import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MagicCard;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.dto.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.sql.*;
import java.util.Scanner;

public interface UserRepository {
    static int createUser(String username, String password){
        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM users
                WHERE username=?
            """ )
        ){

            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                return 1;
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
            return -1;
        }

        String salt = BCrypt.gensalt(12);
        String hashPassword = BCrypt.hashpw(password, salt);

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO users
                (username, password, salt, coins, elo)
                VALUES (?,?,?,?,?);
            """ )
        ){

            statement.setString(1, username);
            statement.setString(2, hashPassword);
            statement.setString(3, salt);
            statement.setInt(4, 20);
            statement.setInt(5, 100);
            statement.execute();
            System.out.println("at.fhtw.swen1.mcg.dto.User created successfully");

            return 0;

        }
        catch (SQLException ex){
            System.out.println(ex);
            return -1;
        }
    }

    static User loginUser(){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter username:");
        String username = myObj.nextLine();  // Read user input
        System.out.println("Enter password:");
        String password = myObj.nextLine();  // Read user input

        User player;

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM users
                WHERE username=?
            """ )
        ){

            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                String salt = rs.getString(4);
                String hashPassword = BCrypt.hashpw(password, salt);
                if(rs.getString(2).equals(username) && hashPassword.equals(rs.getString(3))){
                    System.out.println("Login successful");
                    int coins = rs.getInt(5);
                    int playerID = rs.getInt(1);
                    int elo = rs.getInt(6);
                    player = new User(username, coins, playerID, elo);
                    player = CardRepository.getPlayersCards(username, playerID, player);
                    return player;
                }
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
            return null;
        }
        System.out.println("Username or password was incorrect");
        return null;
    }

    static User shop(User player1){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Purchase a pack? y/n (5 coins)");
        System.out.println("Available coins: " + player1.getCoins());
        String option = myObj.nextLine();  // Read user input
        Card newCard;
        if(option.equals("n")){
            return player1;
        }

        if(player1.getCoins() < 5){
            System.out.println("Not enough coins");
            return player1;
        }
        player1.spendCoins();

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE users
                SET coins=?
                WHERE username=?
            """ )
        ){

            statement.setInt(1, player1.getCoins());
            statement.setString(2, player1.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println(ex);
            return null;
        }

        int numberOfCards = CardRepository.getNumberOfCards(player1);

        System.out.println("Cards bought: ");
        int numberOfMonsters = ((int) (Math.random() * 5));
        for(int i = 0; i < numberOfMonsters; i++){
            newCard = (new MonsterCard("", "", 0, numberOfCards).randomizeCard());
            player1.addToStack(newCard);
            CardRepository.saveCard(newCard, player1);
            numberOfCards++;
        }
        for(int j = numberOfMonsters; j < 4; j++){
            newCard = (new MagicCard("", 0, numberOfCards).randomizeCard());
            player1.addToStack(newCard);
            CardRepository.saveCard(newCard, player1);
            numberOfCards++;
        }
        return player1;
    }

    static void updateElo(User winner, User loser){
        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE users
                SET elo=?
                WHERE user_id=?
            """ )
        ){

            statement.setInt(1, winner.getElo());
            statement.setInt(2, winner.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE users
                SET elo=?
                WHERE user_id=?
            """ )
        ){

            statement.setInt(1, loser.getElo());
            statement.setInt(2, loser.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
}
