package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.dto.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardRepositoryTest {

    @Test
    void saveCard() {
        Card newCard = new MonsterCard("Test", "Water", 10, "CardID");
        User player = new User("Vivian", 20, -1, 100);

        assertTrue(CardRepository.saveCard(newCard, player));
    }

    @Test
    void getNumberOfCards() {
        User player = new User("Vivian", 20, -1, 100);

        assertEquals(1, CardRepository.getNumberOfCards(player));

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM cards
                WHERE playerid=?
            """ )
        ){
            statement.setInt(1, player.getId());
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Test
    void createPackage() {
        List<Card> pack = new ArrayList<>();

        Card card1 = new MonsterCard("Dragon", "Fire", 100, "ID1");
        Card card2 = new MonsterCard("Dragon", "Fire", 100, "ID2");
        Card card3 = new MonsterCard("Dragon", "Fire", 100, "ID3");
        Card card4 = new MonsterCard("Dragon", "Fire", 100, "ID4");
        Card card5 = new MonsterCard("Dragon", "Fire", 100, "ID5");

        pack.add(card1);
        pack.add(card2);
        pack.add(card3);
        pack.add(card4);
        pack.add(card5);

        CardRepository.createPackage(pack);

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM packages
                WHERE card1id=?
            """ )
        ){
            statement.setString(1, pack.get(2).getCardID());
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
}