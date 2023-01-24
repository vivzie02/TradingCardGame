package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.service.users.UserData;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDataRepositoryTest {

    @Test
    void editUserData() {
        UserDataRepository userDataRepository = new UserDataRepository();

        JSONObject jsonObject = UserData.getUserData("{\"Name\": \"Kienboeck\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}");
        User player = new User("TestUser", 20, -1, 100);

        assertEquals("User data updated successfully", userDataRepository.editUserData(player, jsonObject));

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM userdata
            """ )
        ){
            statement.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Test
    void getUserData() {
        UserDataRepository userDataRepository = new UserDataRepository();

        User player = new User("TestUser", 20, -1, 100);

        JSONObject jsonObject = UserData.getUserData("{\"Name\": \"Kienboeck\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}");
        userDataRepository.editUserData(player, jsonObject);

        assertEquals("Kienboeck", userDataRepository.getUserData(player).get(0));

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM userdata
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
}