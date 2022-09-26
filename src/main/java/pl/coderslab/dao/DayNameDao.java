package pl.coderslab.dao;
import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DayNameDao implements Dao<DayName> {
    private static final String FIND_ALL_DAYS_QUERY = "SELECT * FROM day_name;";

    @Override
    public Optional<DayName> read(int id) {
        return Optional.empty();
    }

    public List<DayName> findAll(){
    List<DayName> dayNames = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAYS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DayName dayNameToAdd = new DayName();
                dayNameToAdd.setId(resultSet.getInt("id"));
                dayNameToAdd.setDisplayOrder(resultSet.getInt("display_order"));
                dayNameToAdd.setName(resultSet.getString("name"));
                dayNames.add(dayNameToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayNames;

    }

    @Override
    public Optional<DayName> create(DayName dayName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(DayName dayName) {
        throw new UnsupportedOperationException();
    }

}