package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MagicCard;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.dto.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
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

    static List<Card> getUsersDeck(User player){
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
                for(int i = 0; i < 4; i++){
                    if(rs.getString(3).contains("Spell")){
                        addedCard = new MagicCard(rs.getString(4), rs.getFloat(5), rs.getString(1));
                    }
                    else{
                        addedCard = new MonsterCard(rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getString(1));
                    }
                    deck.add(addedCard);
                }
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return deck;
    }
}
