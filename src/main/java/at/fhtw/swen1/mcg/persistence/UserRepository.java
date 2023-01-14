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
                (username, password, salt, coins, elo, token)
                VALUES (?,?,?,?,?,?);
            """ )
        ){

            statement.setString(1, username);
            statement.setString(2, hashPassword);
            statement.setString(3, salt);
            statement.setInt(4, 20);
            statement.setInt(5, 100);
            statement.setString(6, "Basic " + username + "-mtcgToken");
            statement.execute();
            System.out.println("at.fhtw.swen1.mcg.dto.User created successfully");

            return 0;

        }
        catch (SQLException ex){
            System.out.println(ex);
            return -1;
        }
    }

    static User loginUser(String username, String password){
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

    static User loginWithToken(String token){
        User player;

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM users
                WHERE token=?
            """ )
        ){

            statement.setString(1, token);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                String username = rs.getString(2);
                int coins = rs.getInt(5);
                int playerID = rs.getInt(1);
                int elo = rs.getInt(6);
                player = new User(username, coins, playerID, elo);
                player = CardRepository.getPlayersCards(username, playerID, player);
                return player;
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
            return null;
        }
        return null;
    }
    static int shop(User player1){

        if(player1.getCoins() < 5){
            System.out.println("Not enough coins");
            return -1;
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
            return 1;
        }

        CardRepository.assignPackages(player1);

        return 0;
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
