package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminDao implements Dao<Admin>{
    private static final String CREATE_ADMIN_QUERY =
            "INSERT INTO admins (first_name, last_name, email, password, superadmin, enable)\n" +
                    "values (?, ?, ?, ?, ?, ?)";
    private static final String READ_ADMIN_QUERY =
            "SELECT * FROM admins WHERE id = ?";
    private static final String READ_ALL_ADMIN_QUERY =
            "SELECT * FROM admins";
    private static final String UPDATE_ADMIN_QUERY =
            "UPDATE admins SET first_name = ?, last_name = ?, email = ?, password = ?, superadmin = ?, enable = ?" +
                    " WHERE id = ?";
    private static final String DELETE_ADMIN_QUERY =
            "DELETE FROM admins WHERE id = ?";
    private static final String READ_LOGIN =
            "SELECT email, password FROM admins WHERE email = ?";
    private static final String READ_ID_LOGIN =
            "SELECT id FROM admins WHERE email = ?";


    public String hashPassword(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    /** This method will create object admin and put it into DataBase */
    public Optional<Admin> create(Admin admin){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement statement = conn.prepareStatement(CREATE_ADMIN_QUERY);
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, hashPassword(admin.getPassword()));
            statement.setInt(5, admin.getSuperadmin());
            statement.setInt(6, admin.getEnable());

            statement.executeUpdate();
            return Optional.of(admin);
        }catch(SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /** This method will read from DataBase object admin under specified admin name */
    public Optional<Admin> read(int id) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_ADMIN_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setFirstName(resultSet.getString("first_name"));
                admin.setLastName(resultSet.getString("last_name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPassword(resultSet.getString("password"));
                admin.setSuperadmin(resultSet.getInt("superadmin"));
                admin.setEnable(resultSet.getInt("enable"));
                return Optional.of(admin);
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /** This method will read ->ALL<- admin objects from DataBase*/
    public List<Admin> findAll(){
        try(Connection connection = DbUtil.getConnection()){
            Admin[] admins = new Admin[0];
            PreparedStatement statement = connection.prepareStatement(READ_ALL_ADMIN_QUERY);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setFirstName(resultSet.getString("first_name"));
                admin.setLastName(resultSet.getString("last_name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPassword(resultSet.getString("password"));
                admin.setSuperadmin(resultSet.getInt("superadmin"));
                admin.setEnable(resultSet.getInt("enable"));
                admins = addToArray(admin, admins);
            }
            return Arrays.stream(admins).collect(Collectors.toList());
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /** This method will pack all users from DataBase into Array */
    private Admin[] addToArray(Admin a, Admin[] admins){
        Admin[] tmpAdmins = Arrays.copyOf(admins, admins.length + 1);
        tmpAdmins[admins.length] = a;
        return tmpAdmins;
    }

    /** This method will allow us to edit existing admin in DataBase */
    public void update(Admin admin) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADMIN_QUERY);
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, hashPassword(admin.getPassword()));
            statement.setInt(5, admin.getSuperadmin());
            statement.setInt(6, admin.getEnable());
            statement.setInt(7,admin.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method will delete exiting admin in DataBase by their name*/
    public void delete(int id){
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /** This Method will check credentials for login data to see if they match with database*/
    public int readLogin(String email, String password){
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_LOGIN);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            Admin admin = new Admin();
            if(resultSet.next()){
                admin.setEmail(resultSet.getString("email"));
                admin.setPassword(resultSet.getString("password"));
            }
            if(admin.getEmail().equals(email) && BCrypt.checkpw(password, admin.getPassword())){
                return 1;
            }else{
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int userId(String email){
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_ID_LOGIN);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            Admin admin = new Admin();
            if(resultSet.next()){
               admin.setId(resultSet.getInt("id"));
               return admin.getId();
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
