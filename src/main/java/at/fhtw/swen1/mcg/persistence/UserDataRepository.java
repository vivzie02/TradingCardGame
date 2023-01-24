package at.fhtw.swen1.mcg.persistence;

import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataRepository {
    public String editUserData(User player, JSONObject body){
        List<String> userData = new ArrayList<>(); // Name + Bio + Image

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
            return "Error deleting old data";
        }


        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO userdata
                (user_id, username, bio, image)
                VALUES (?,?,?,?);
            """ )
        ){

            statement.setInt(1, player.getId());
            statement.setString(2, body.getString("Name"));
            statement.setString(3, body.getString("Bio"));
            statement.setString(4, body.getString("Image"));
            statement.execute();

        }
        catch (SQLException ex){
            System.out.println(ex);
            return "Error creating new Data Set";
        }

        return "User data updated successfully";
    }

    public List<String> getUserData(User player){
        List<String> userData = new ArrayList<>();

        try(Connection connection = DatabaseFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM userdata
                WHERE user_id=?;
            """ )
        ){

            statement.setInt(1, player.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                userData.add(rs.getString(2));
                userData.add(rs.getString(3));
                userData.add(rs.getString(4));
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
            userData.add("Error creating new Data Set");
        }
        return userData;
    }
}
