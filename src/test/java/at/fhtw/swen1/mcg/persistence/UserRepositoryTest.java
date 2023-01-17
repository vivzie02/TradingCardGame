package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void createUser() {
        assertEquals(0, UserRepository.createUser("Vivian", "Test123"));
        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM users
                WHERE username=?
            """ )
        ){
            statement.setString(1, "Vivian");
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Test
    void loginUser() {
        UserRepository.createUser("Vivian", "Test123");
        assertEquals("Vivian", UserRepository.loginUser("Vivian", "Test123").getUsername());

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM users
                WHERE username=?
            """ )
        ){
            statement.setString(1, "Vivian");
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Test
    void loginWithToken() {
        UserRepository.createUser("Vivian", "Test123");
        assertEquals("Vivian", UserRepository.loginWithToken("Basic Vivian-mtcgToken").getUsername());

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM users
                WHERE username=?
            """ )
        ){
            statement.setString(1, "Vivian");
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Test
    void updateElo() {
        UserRepository.createUser("Winner", "Test123");
        User player2 = new User("Winner", 20, -2, 110);
        User player1 = new User("Vivian", 20, -1, 90);

        assertEquals(0, UserRepository.updateElo(player2, player1));

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM users
                WHERE username=?
            """ )
        ){
            statement.setString(1, player2.getUsername());
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Test
    void getStats() {
        UserRepository.createUser("Vivian", "Test123");
        User player = UserRepository.loginUser("Vivian", "Test123");
        assertEquals("20 | 100", UserRepository.getStats(player));

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM users
                WHERE username=?
            """ )
        ){
            statement.setString(1, "Vivian");
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Test
    void getScoreBoard() {
        UserRepository.createUser("Vivian", "Test123");
        assertEquals("Vivian | 100\n", UserRepository.getScoreBoard());

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM users
                WHERE username=?
            """ )
        ){
            statement.setString(1, "Vivian");
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
}