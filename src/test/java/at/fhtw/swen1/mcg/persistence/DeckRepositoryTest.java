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

class DeckRepositoryTest {

    @Test
    void createDeck() {
        DeckRepository deckRepository = new DeckRepository();

        List<String> deck = new ArrayList<>();

        Card card1 = new MonsterCard("Dragon", "Fire", 100, "ID1");
        Card card2 = new MonsterCard("Dragon", "Fire", 100, "ID2");
        Card card3 = new MonsterCard("Dragon", "Fire", 100, "ID3");
        Card card4 = new MonsterCard("Dragon", "Fire", 100, "ID4");

        deck.add(card1.getCardID());
        deck.add(card2.getCardID());
        deck.add(card3.getCardID());
        deck.add(card4.getCardID());

        User player = new User("testUser", 20, 1, 100);

        assertEquals(0, deckRepository.createDeck(deck, player));

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
    }

    @Test
    void getUsersDeck() {
        DeckRepository deckRepository = new DeckRepository();
        CardRepository cardRepository = new CardRepository();

        List<String> deck = new ArrayList<>();

        Card card1 = new MonsterCard("Dragon", "Fire", 100, "ID1");
        Card card2 = new MonsterCard("Dragon", "Fire", 100, "ID2");
        Card card3 = new MonsterCard("Dragon", "Fire", 100, "ID3");
        Card card4 = new MonsterCard("Dragon", "Fire", 100, "ID4");

        deck.add(card1.getCardID());
        deck.add(card2.getCardID());
        deck.add(card3.getCardID());
        deck.add(card4.getCardID());

        User player = new User("testUser", 20, 1, 100);

        cardRepository.saveCard(card1, player);
        cardRepository.saveCard(card2, player);
        cardRepository.saveCard(card3, player);
        cardRepository.saveCard(card4, player);

        deckRepository.createDeck(deck, player);

        assertEquals("Dragon", deckRepository.getUsersDeck(player).get(0).getName());

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
}