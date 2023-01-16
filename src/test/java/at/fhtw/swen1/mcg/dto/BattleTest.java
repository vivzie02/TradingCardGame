package at.fhtw.swen1.mcg.dto;

import static org.junit.jupiter.api.Assertions.*;
class BattleTest {
    @org.junit.jupiter.api.Test
    void specialRules() {
        Card card1 = new MonsterCard("Goblin", "Water", 20.0f, "testID1");
        Card card2 = new MonsterCard("Dragon", "Water", 30.0f, "testID2");
        Battle battle = new Battle();

        Card card3 = new MonsterCard("Goblin", "Water", 50.0f, "testID3");
        Card card4 = new MonsterCard("Dragon", "Water", 30.0f, "testID4");

        Card card5 = new MonsterCard("Ork", "Water", 50.0f, "testID3");
        Card card6= new MonsterCard("Wizard", "Water", 30.0f, "testID4");

        assertFalse(battle.specialRules(card1, card2));
        assertTrue(battle.specialRules(card3, card4));
        assertTrue(battle.specialRules(card5, card6));
    }

    @org.junit.jupiter.api.Test
    void effectiveness() {
        Battle battle = new Battle();

        assertEquals(1, battle.effectiveness("Water", "Water"));
        assertEquals(2, battle.effectiveness("Water", "Fire"));
        assertEquals(0.5, battle.effectiveness("Fire", "Water"));
    }

    @org.junit.jupiter.api.Test
    void startBattle() {
        User player1 = new User("A", 20, 1, 100);
        User player2 = new User("B", 20, 2, 100);
        Battle battle = new Battle();

        assertEquals("Too few cards in deck", battle.startBattle(player1, player2));
        assertEquals("Too few cards in deck", battle.startBattle(player2, player1));
    }

}