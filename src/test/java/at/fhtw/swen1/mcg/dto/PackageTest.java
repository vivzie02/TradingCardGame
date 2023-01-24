package at.fhtw.swen1.mcg.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackageTest {

    @Test
    void getCardsInPackage() {
        Card card1 = new MonsterCard("Goblin", "Water", 20.0f, "testID1");
        Card card2 = new MonsterCard("Dragon", "Water", 30.0f, "testID2");
        Card card3 = new MonsterCard("Goblin", "Water", 50.0f, "testID3");
        Card card4 = new MonsterCard("Dragon", "Water", 30.0f, "testID4");

        Package pack = new Package();
        List<Card> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);

        pack.setPackage(cards);

        assertEquals("testID3", pack.getCardsInPackage().get(2).getCardID());



    }
}