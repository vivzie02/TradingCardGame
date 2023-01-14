package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MagicCard;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface CardRepository {
    static User getPlayersCards(String username, int playerID, User player){
        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM cards
                WHERE playerID=?
            """ )
        ){

            statement.setInt(1, playerID);
            ResultSet rs = statement.executeQuery();

            String name, elementType, cardID;
            int damage;
            Card anotherCard;

            while (rs.next()){
                cardID = rs.getString(1);
                name = rs.getString(3);
                elementType = rs.getString(4);
                damage = rs.getInt(5);
                if(rs.getString(6).equals("MONSTER")){
                    anotherCard = new MonsterCard(name, elementType, damage, cardID);
                    player.addToStack(anotherCard);
                } else if (rs.getString(6).equals("MAGIC")) {
                    anotherCard = new MagicCard(elementType, damage, cardID);
                    player.addToStack(anotherCard);
                }
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
            return null;
        }

        return player;
    }

    static void saveCard(Card newCard, User player1){
        System.out.println(newCard.getName() + " | " + newCard.getElementType() + " | " + newCard.getDamage());

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO cards
                (cardid, playerid, name, elementtype, damage, cardtype)
                VALUES (?,?,?,?,?,?);
            """ )
        ){
            statement.setString(1, newCard.getCardID());
            statement.setInt(2, player1.getId());
            statement.setString(3, newCard.getName());
            statement.setString(4, newCard.getElementType());
            statement.setFloat(5, newCard.getDamage());
            if(newCard instanceof MonsterCard){
                statement.setString(6, "MONSTER");
            }else {
                statement.setString(6, "MAGIC");
            }
            statement.execute();

        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    static int getNumberOfCards(User player1){
        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM cards
            """ )
        ){
            ResultSet rs = statement.executeQuery();

            int counter = 0;

            while (rs.next()){
                counter++;
            }
            return counter;

        }
        catch (SQLException ex){
            System.out.println(ex);
            return 0;
        }
    }

    static int createPackage(List<Card> cardsInPackage){
        User admin = new User("admin", 1000, 0, 1000);
        List<String> cardIds = new ArrayList<>();

        for (Card card:cardsInPackage) {
            saveCard(card, admin);
            cardIds.add(card.getCardID());
        }

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO packages
                (card1id, card2id, card3id, card4id, card5id, owner)
                VALUES (?,?,?,?,?,?);
            """ )
        ){
            statement.setString(1, cardIds.get(0));
            statement.setString(2, cardIds.get(1));
            statement.setString(3, cardIds.get(2));
            statement.setString(4, cardIds.get(3));
            statement.setString(5, cardIds.get(4));
            statement.setString(6, "admin");
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
            return -1;
        }
        return 0;
    }

    static int assignPackages(User player){
        int id = 0;

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT packageid FROM packages
                WHERE owner='admin'
            """ )
        ){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                id = rs.getInt(1);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
            return 0;
        }

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE packages
                SET owner=?
                WHERE packageid=?
            """ )
        ){

            statement.setString(1, player.getUsername());
            statement.setInt(2, id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        return 0;
    }
}
