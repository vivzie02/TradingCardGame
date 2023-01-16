package at.fhtw.swen1.mcg.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicCardTest {

    @Test
    void CardValues() {
        Card newCard = new MagicCard("Water", 110.0f, "TestID");
        newCard.getDamage();
        assertEquals(110.0f, newCard.getDamage());
        assertEquals("Water", newCard.getElementType());
        assertEquals("TestID", newCard.getCardID());
    }
}