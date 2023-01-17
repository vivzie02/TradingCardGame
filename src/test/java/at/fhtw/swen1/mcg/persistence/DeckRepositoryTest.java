package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.dto.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckRepositoryTest {

    @Test
    void createDeck() {
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

        assertEquals(0, DeckRepository.createDeck(deck, player));
    }

    @Test
    void getUsersDeck() {
        User player = new User("testUser", 20, 1, 100);
        assertEquals("Dragon", DeckRepository.getUsersDeck(player).get(0).getName());
    }
}