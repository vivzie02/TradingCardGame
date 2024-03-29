package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MagicCard;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardRepository {
    public User getPlayersCards(String username, int playerID, User player){
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

    public boolean saveCard(Card newCard, User player1){
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

            return true;

        }
        catch (SQLException ex){
            System.out.println(ex);
            return false;
        }
    }

    public int getNumberOfCards(User player1){
        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM cards 
                WHERE playerid=?
            """ )
        ){
            statement.setInt(1, player1.getId());
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

    public int createPackage(List<Card> cardsInPackage){
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

    public int assignPackages(User player){
        int id = 0;
        //get an available package

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

        System.out.println(id);

        //assign package to buyer

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


        //get the ids of the purchased cards

        List<String> cardIds = new ArrayList<>();

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM packages
                WHERE packageid=?
            """ )
        ){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                cardIds.add(rs.getString(2));
                cardIds.add(rs.getString(3));
                cardIds.add(rs.getString(4));
                cardIds.add(rs.getString(5));
                cardIds.add(rs.getString(6));
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
            return 0;
        }

        System.out.println(cardIds.toString());

        //assign the purchased cards to the buyer
        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE cards
                SET playerid=?
                WHERE (cardid=? OR cardid=? OR cardid=? OR cardid=? OR cardid=?)
            """ )
        ){
            statement.setInt(1, player.getId());
            statement.setString(2, cardIds.get(0));
            statement.setString(3, cardIds.get(1));
            statement.setString(4, cardIds.get(2));
            statement.setString(5, cardIds.get(3));
            statement.setString(6, cardIds.get(4));
            statement.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return 0;
    }
}
