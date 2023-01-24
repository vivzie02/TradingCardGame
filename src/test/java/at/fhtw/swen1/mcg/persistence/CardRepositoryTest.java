package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.dto.User;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardRepositoryTest {
    CardRepository cardRepository = new CardRepository();

    @Test
    void saveCard() {
        Card newCard = new MonsterCard("Test", "Water", 10, "CardID");
        User player = new User("Vivian", 20, -1, 100);

        assertTrue(cardRepository.saveCard(newCard, player));

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM cards
                WHERE cardid=?
            """ )
        ){
            statement.setString(1, newCard.getCardID());
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Test
    void getNumberOfCards() {
        CardRepository cardRepository = new CardRepository();
        User player = new User("Vivian", 20, -1, 100);

        assertEquals(0, cardRepository.getNumberOfCards(player));
    }

    @Test
    void createPackage() {
        CardRepository cardRepository = new CardRepository();
        List<Card> pack = new ArrayList<>();

        Card card1 = new MonsterCard("Dragon", "Fire", 101, "ID1");
        Card card2 = new MonsterCard("Dragon", "Fire", 101, "ID2");
        Card card3 = new MonsterCard("Dragon", "Fire", 101, "ID3");
        Card card4 = new MonsterCard("Dragon", "Fire", 101, "ID4");
        Card card5 = new MonsterCard("Dragon", "Fire", 101, "ID5");

        pack.add(card1);
        pack.add(card2);
        pack.add(card3);
        pack.add(card4);
        pack.add(card5);

        cardRepository.createPackage(pack);

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM packages
                WHERE card1id=?
            """ )
        ){
            statement.setString(1, pack.get(0).getCardID());
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }


        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM cards
                WHERE damage=?
            """ )
        ){
            statement.setFloat(1, pack.get(0).getDamage());
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
}