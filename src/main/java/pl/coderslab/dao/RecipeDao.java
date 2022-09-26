package pl.coderslab.dao;

import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecipeDao implements Dao<Recipe> {

    private static final String CREATE_RECIPES_QUERY = "INSERT INTO recipe(name,ingredients,description,created,updated,preparation_time,preparation,admin_id) VALUES (?,?,?,?,?,?,?,?);";
    private static final String DELETE_RECIPES_QUERY = "DELETE FROM recipe where id = ?;";
    private static final String FIND_ALL_RECIPES_QUERY = "SELECT * FROM recipe;";
    private static final String READ_RECIPES_QUERY = "SELECT * from recipe where id = ?;";
    private static final String UPDATE_RECIPES_QUERY = "UPDATE	recipe SET name = ? , ingredients = ?, " +
            "description = ?, created = ?, updated = ?,preparation_time = ?,preparation = ? WHERE	id = ?;";

    private static final String READ_RECIPES_COUNT = "select count(admin_id) as count from recipe where admin_id = ?;";



    @Override
    public Optional<Recipe> read(int id) {
        Recipe recipes = null;


        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_RECIPES_QUERY)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recipes = new Recipe();
                    recipes.setId(resultSet.getInt("id"));
                    recipes.setName(resultSet.getString("name"));
                    recipes.setIngredients(resultSet.getString("ingredients"));
                    recipes.setCreated(resultSet.getString("created"));
                    recipes.setUpdated(resultSet.getString("updated"));
                    recipes.setPreparation_time(resultSet.getInt("preparation_time"));
                    recipes.setPreparation(resultSet.getString("preparation"));
                    recipes.setAdminId(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(recipes);
    }

    public List<Recipe> findAll(int adminId){
        return findAll()
                .stream()
                .filter(p->p.getAdminId()==adminId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_RECIPES_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Recipe recipeToAdd = new Recipe();
                recipeToAdd.setId(resultSet.getInt("id"));
                recipeToAdd.setName(resultSet.getString("name"));
                recipeToAdd.setIngredients(resultSet.getString("ingredients"));
                recipeToAdd.setDescriptions(resultSet.getString("description"));
                recipeToAdd.setCreated(resultSet.getString("created"));
                recipeToAdd.setUpdated(resultSet.getString("updated"));
                recipeToAdd.setPreparation_time(resultSet.getInt("preparation_time"));
                recipeToAdd.setPreparation(resultSet.getString("preparation"));
                recipeToAdd.setAdminId(resultSet.getInt("admin_id"));
                recipeList.add(recipeToAdd);


            }
            return recipeList;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }


    }

    @Override
    public Optional<Recipe> create(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_RECIPES_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, recipe.getName());
            insertStm.setString(2, recipe.getIngredients());
            insertStm.setString(3, recipe.getDescriptions());
            insertStm.setString(4,recipe.getCreated());
            insertStm.setString(5,recipe.getUpdated());
            insertStm.setInt(6,recipe.getPreparation_time());
            insertStm.setString(7,recipe.getPreparation());
            insertStm.setInt(8,recipe.getAdminId());
            insertStm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void delete(int id){

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPES_QUERY)) {
            statement.setInt(1, id);
            int rowDeleted = statement.executeUpdate();

//            System.out.println("rowDeleted: " + rowDeleted);
//            if (rowDeleted == 0) {
//                System.out.println("Recipe " + id +  " not found");
//            }

        }
        catch (Exception e) {
//            System.out.println("Catch " + e.getMessage());
//            if (e instanceof MySQLIntegrityConstraintViolationException) {
//                System.out.println("Inside instanceof");
//                throw new MySQLIntegrityConstraintViolationException(e.getMessage());
//            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPES_QUERY)) {
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescriptions());
            statement.setString(4, recipe.getCreated());
            statement.setString(5, recipe.getUpdated());
            statement.setInt(6, recipe.getPreparation_time());
            statement.setString(7, recipe.getPreparation());
            statement.setInt(8, recipe.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRecipesCountForUser(int id){
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_RECIPES_COUNT)) {
             statement.setInt(1, id);
             ResultSet count = statement.executeQuery();
             count.next();
            return count.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
