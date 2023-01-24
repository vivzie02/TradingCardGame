package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MagicCard;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.dto.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeckRepository {
    public int createDeck(List<String> cards, User player){
        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM decks
                WHERE user_id=?
            """ )
        ){

            statement.setInt(1, player.getId());
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }


        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO decks
                (user_id, card1, card2, card3, card4)
                VALUES (?,?,?,?,?);
            """ )
        ){

            statement.setInt(1, player.getId());
            statement.setString(2, cards.get(0));
            statement.setString(3, cards.get(1));
            statement.setString(4, cards.get(2));
            statement.setString(5, cards.get(3));
            statement.execute();

            return 0;

        }
        catch (SQLException ex){
            System.out.println(ex);
            return 1;
        }
    }

    public List<Card> getUsersDeck(User player){
        List<String> cardids = new ArrayList<>();
        List<Card> deck = new ArrayList<>();
        Card addedCard;

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM decks
                WHERE user_id=?
            """ )
        ){

            statement.setInt(1, player.getId());

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                cardids.add(rs.getString(3));
                cardids.add(rs.getString(4));
                cardids.add(rs.getString(5));
                cardids.add(rs.getString(6));
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        if(cardids.isEmpty()){
            return null;
        }

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM cards
                WHERE (cardid=? OR cardid=? OR cardid=? OR cardid=?)
            """ )
        ){

            statement.setString(1, cardids.get(0));
            statement.setString(2, cardids.get(1));
            statement.setString(3, cardids.get(2));
            statement.setString(4, cardids.get(3));
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                if(rs.getString(3).contains("Spell")){
                    addedCard = new MagicCard(rs.getString(4), rs.getFloat(5), rs.getString(1));
                }
                else{
                    addedCard = new MonsterCard(rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getString(1));
                }
                deck.add(addedCard);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return deck;
    }
}
