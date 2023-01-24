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
        UserRepository userRepository = new UserRepository();

        assertEquals(0, userRepository.createUser("Vivian", "Test123"));
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
        UserRepository userRepository = new UserRepository();

        userRepository.createUser("Vivian", "Test123");
        assertEquals("Vivian", userRepository.loginUser("Vivian", "Test123").getUsername());

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
        UserRepository userRepository = new UserRepository();

        userRepository.createUser("Vivian", "Test123");
        assertEquals("Vivian", userRepository.loginWithToken("Basic Vivian-mtcgToken").getUsername());

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
        UserRepository userRepository = new UserRepository();

        userRepository.createUser("Winner", "Test123");
        User player2 = new User("Winner", 20, -2, 110);
        User player1 = new User("Vivian", 20, -1, 90);

        assertEquals(0, userRepository.updateElo(player2, player1));

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM users
            """ )
        ){
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Test
    void getStats() {
        UserRepository userRepository = new UserRepository();

        userRepository.createUser("Vivian", "Test123");
        User player = userRepository.loginUser("Vivian", "Test123");
        assertEquals("20 | 100", userRepository.getStats(player));

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
        UserRepository userRepository = new UserRepository();

        userRepository.createUser("Vivian", "Test123");
        assertEquals("Vivian | 100\n", userRepository.getScoreBoard());

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