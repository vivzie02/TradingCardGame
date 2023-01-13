package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface DeckRepository {
    static void createDeck(User player1, List<Card> deck){
        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO decks
                (user_id, card1, card2, card3, card4)
                VALUES (?,?,?,?,?);
            """ )
        ){

            statement.setInt(1, player1.getId());
            statement.setString(2, deck.get(0).getCardID());
            statement.setString(3, deck.get(1).getCardID());
            statement.setString(4, deck.get(2).getCardID());
            statement.setString(5, deck.get(3).getCardID());
            statement.execute();

        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
}
