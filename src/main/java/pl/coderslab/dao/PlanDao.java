package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.*;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlanDao implements Dao<Plan> {


    private static final String REMOVE_RECIPE_QUERY = "DELETE FROM recipe_plan where id = ?;";

    private static final String ADD_MEAL_QUERY = "INSERT INTO recipe_plan(recipe_id,meal_name,display_order,day_name_id,plan_id) VALUES (?,?,?,?,?);";

    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name,description,created,admin_id) VALUES (?,?,?,?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan;";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?;";
    private static final String READ_PLAN_BY_ADMIN_QUERY = "SELECT * from plan where admin_id = ? ORDER BY created DESC;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ? , description = ?, created = ?, admin_id = ? WHERE id = ?;";
    private static final String COUNT_PLANS_QUERY = "SELECT Count(*) AS count FROM plan WHERE admin_id = ?";
    private static final String FIND_NEWEST_PLAN_FOR_USER_QUERY = "SELECT day_name.id as day_id, day_name.name, day_name.display_order as ord, recipe_plan.display_order as meal_ord, recipe_plan.meal_name, recipe_plan.id as meal_id, recipe.id as recipe_id, newest_plan.id as plan_id\n" +
            "FROM recipe_plan\n" +
            "         JOIN day_name on day_name.id = day_name_id\n" +
            "         JOIN recipe on recipe.id = recipe_id\n" +
            "INNER JOIN  (SELECT id FROM plan\n" +
            "                    WHERE admin_id = ?\n" +
            "                              ORDER BY created DESC\n" +
            "                              LIMIT 1) as newest_plan\n" +
            "ON recipe_plan.plan_id = newest_plan.id\n" +
            "ORDER BY meal_id";
    private static final String FIND_PLAN_DETAILS_QUERY = "SELECT day_name.id as day_id, day_name.name, day_name.display_order as ord, rp.display_order as meal_ord, rp.meal_name, rp.id as meal_id, recipe.id as recipe_id, rp.plan_id as plan_id\n" +
            "FROM recipe_plan rp\n" +
            "         JOIN day_name on day_name.id = day_name_id\n" +
            "         JOIN recipe on recipe.id = recipe_id\n" +
            "WHERE rp.plan_id = ? ORDER BY meal_id";

    private final int REMOVE_ID = 1;

    private final int PLAN_ID = 1;
    private final int NAME = 1;
    private final int DESCRIPTION = 2;
    private final int CREATION_DATE = 3;
    private final int ADMIN_ID = 4;
    private final int UPDATE_PLAN_ID = 5;
    private final int ONE_AFFECTED_ROW = 1;

    @Override
    public Optional<Plan> read(int id) {
        try (Connection connection = DbUtil.getConnection()) {
            return Optional.ofNullable(read(connection, id));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    protected Plan read(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY);
        statement.setInt(PLAN_ID, id);
        ResultSet resultSet = statement.executeQuery();
        return getPlanFromResultSet(resultSet);
    }

    public List<Plan> findAll(int adminId) {
        return findAll()
                .stream()
                .filter(p -> p.getAdminId() == adminId)
                .sorted(Comparator.comparing(Plan::getCreationDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Plan> findAll() {
        try (Connection connection = DbUtil.getConnection()) {
            return findAll(connection);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    protected List<Plan> findAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY);
        ResultSet resultSet = statement.executeQuery();
        return getPlanListFromResultSet(resultSet);
    }

    @Override
    public Optional<Plan> create(Plan plan) {
        try (Connection connection = DbUtil.getConnection()) {
            return Optional.ofNullable(create(connection, plan));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    protected Plan create(Connection connection, Plan plan) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CREATE_PLAN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(NAME, plan.getName());
        statement.setString(DESCRIPTION, plan.getDescription());
        statement.setTimestamp(CREATION_DATE, plan.getCreationDate());
        statement.setInt(ADMIN_ID, plan.getAdminId());
        if (statement.executeUpdate() != ONE_AFFECTED_ROW) {
            throw new SQLException();
        }
        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.first();
        plan.setId(generatedKeys.getInt(PLAN_ID));
        return plan;
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DbUtil.getConnection()) {
            delete(connection, id);
        } catch (SQLException e) {
            // nothing to do
        }
    }

    protected void delete(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY);
        statement.setInt(PLAN_ID, id);
        statement.executeUpdate();
    }

    @Override
    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection()) {
            update(connection, plan);
        } catch (SQLException | NotFoundException e) {
            // nothing to do
        }
    }

    protected void update(Connection connection, Plan plan) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY);
        statement.setString(NAME, plan.getName());
        statement.setString(DESCRIPTION, plan.getDescription());
        statement.setTimestamp(CREATION_DATE, plan.getCreationDate());
        statement.setInt(ADMIN_ID, plan.getAdminId());
        statement.setInt(UPDATE_PLAN_ID, plan.getId());
        statement.executeUpdate();
    }

    public int getNumberOfPlansCreatedByUser(int adminId) {
        try (Connection connection = DbUtil.getConnection()) {
            return getNumberOfPlansCreatedByUser(connection, adminId);
        } catch (SQLException | NotFoundException e) {
            return 0;
        }
    }

    protected int getNumberOfPlansCreatedByUser(Connection connection, int adminId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(COUNT_PLANS_QUERY);
        statement.setInt(1, adminId);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            return 0;
        }
        return resultSet.getInt("count");
    }

    public Optional<PlanDetails> getPlanDetails(int planId) {
        try (Connection connection = DbUtil.getConnection()) {
            return Optional.ofNullable(getPlanDetails(connection, planId));
        } catch (SQLException | NotFoundException e) {
            return Optional.empty();
        }
    }

    protected PlanDetails getPlanDetails(Connection connection, int planId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_PLAN_DETAILS_QUERY);
        statement.setInt(1, planId);
        ResultSet resultSet = statement.executeQuery();
        return readPlanDetailsFromResultSet(resultSet);
    }

    public Optional<PlanDetails> getLatestPlanForUser(int adminId) {
        try (Connection connection = DbUtil.getConnection()) {
            return Optional.ofNullable(getLatestPlanForUser(connection, adminId));
        } catch (SQLException | NotFoundException e) {
            return Optional.empty();
        }
    }

    protected PlanDetails getLatestPlanForUser(Connection connection, int adminId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_NEWEST_PLAN_FOR_USER_QUERY);
        statement.setInt(1, adminId);
        ResultSet resultSet = statement.executeQuery();
        return readPlanDetailsFromResultSet(resultSet);
    }

    private PlanDetails readPlanDetailsFromResultSet(ResultSet resultSet) throws SQLException {
        final int NOT_SET = -1;
        PlanDetails planDetails = new PlanDetails();
        RecipeDao recipeDao = new RecipeDao();
        int planId = NOT_SET;
        while (resultSet.next()) {
            planId = resultSet.getInt("plan_id");
            int recipeId = resultSet.getInt("recipe_id");
            DayName dayName = new DayName(resultSet.getInt("day_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("ord"));
            Recipe recipe = recipeDao.read(recipeId).orElseThrow(SQLException::new);
            int mealOrder = resultSet.getInt("meal_ord");
            int mealId = resultSet.getInt("meal_id");
            String mealName = resultSet.getString("meal_name");
            planDetails.addRecipeForTheDay(dayName, new MealData(recipe, mealId, mealName, mealOrder));
        }
        if (planId == NOT_SET) { // if user doesn't have any plans
            return null;
        }
        Plan plan = read(planId).orElseThrow(SQLException::new);
        planDetails.setPlan(plan);
        return planDetails;
    }

    private List<Plan> getPlanListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Plan> plans = new ArrayList<>();
        Plan plan = getPlanFromResultSet(resultSet);
        while (plan != null) {
            plans.add(plan);
            plan = getPlanFromResultSet(resultSet);
        }
        return plans;
    }

    private Plan getPlanFromResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        return new Plan(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getTimestamp("created"),
                resultSet.getInt("admin_id")
        );
    }

    public Optional<Plan> readForAdmin(int adminId) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_PLAN_BY_ADMIN_QUERY);
            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Plan plan = new Plan();
                plan.setId(resultSet.getInt("id"));
                plan.setName(resultSet.getString("name"));
                plan.setDescription(resultSet.getString("description"));
                plan.setAdminId(resultSet.getInt("admin_id"));
                return Optional.of(plan);
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void removeMeal(int id) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(REMOVE_RECIPE_QUERY);
            statement.setInt(REMOVE_ID, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMeal(int planId, int recipeId, int dayId, String mealName, int mealNumber) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_MEAL_QUERY);
            statement.setInt(1, recipeId);
            statement.setString(2, mealName);
            statement.setInt(3, mealNumber);
            statement.setInt(4, dayId);
            statement.setInt(5, planId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}