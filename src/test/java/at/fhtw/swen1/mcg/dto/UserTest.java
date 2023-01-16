package at.fhtw.swen1.mcg.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getId() {
        User player = new User("TestUser", 100, 1, 100);
        assertEquals(1, player.getId());
    }

    @Test
    void getElo() {
        User player = new User("TestUser", 100, 1, 100);
        assertEquals(100, player.getElo());
    }

    @Test
    void setElo() {
        User player = new User("TestUser", 100, 1, 100);
        player.setElo(100);
        assertEquals(0, player.getElo());
    }

    @Test
    void getUsername() {
        User player = new User("TestUser", 100, 1, 100);
        assertEquals("TestUser", player.getUsername());
    }

    @Test
    void setUsername() {
        User player = new User("TestUser", 100, 1, 100);
        player.setUsername("Bernd");
        assertEquals("Bernd", player.getUsername());
    }

    @Test
    void getCoins() {
        User player = new User("TestUser", 100, 1, 100);
        assertEquals(100, player.getCoins());
    }

    @Test
    void spendCoins() {
        User player = new User("TestUser", 100, 1, 100);
        player.spendCoins();
        assertEquals(95, player.getCoins());
    }
}