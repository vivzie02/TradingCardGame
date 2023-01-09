package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MagicCard;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.dto.User;

import java.sql.*;
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

            String name, elementType;
            int damage, cardID;
            Card anotherCard;

            while (rs.next()){
                cardID = rs.getInt(1);
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
                (playerid, name, elementtype, damage, cardtype)
                VALUES (?,?,?,?,?);
            """ )
        ){

            statement.setInt(1, player1.getId());
            statement.setString(2, newCard.getName());
            statement.setString(3, newCard.getElementType());
            statement.setInt(4, newCard.getDamage());
            if(newCard instanceof MonsterCard){
                statement.setString(5, "MONSTER");
            }else {
                statement.setString(5, "MAGIC");
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
}
