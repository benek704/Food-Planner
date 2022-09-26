package pl.coderslab.dao;

import org.junit.jupiter.api.*;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import pl.coderslab.model.Plan;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlanDaoTest {

    private static Connection connection;
    private Statement statement;
    private PlanDao planDao;
    private Plan plan;

    @BeforeAll
    public static void setup() throws ParserConfigurationException, IOException, SAXException, SQLException {
        connection = LocalDb.getConnection();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void setDatabase() throws SQLException {
        statement = connection.createStatement();
        // clear database for testing
        statement.executeUpdate("DELETE FROM plan");
        planDao = new PlanDao();
        plan = new Plan("TestName", "TestDesc", new Timestamp(System.currentTimeMillis()), 1);
    }

    @AfterEach
    public void rollback() throws SQLException {
        try {
        } finally {
            statement.close();
            connection.rollback();
        }
    }

    @Test
    public void createPlan() throws SQLException {
        Timestamp sqlDate = plan.getCreationDate();
        String name = plan.getName();
        String desc = plan.getDescription();
        plan = planDao.create(connection, plan);
        assertTrue(plan.getId() > 0);
        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM plan")) {
            // one object in db
            assertTrue(resultSet.next());
            // it is plan object we created
            assertEquals(name, resultSet.getString("name"));
            assertEquals(desc, resultSet.getString("description"));
            assertEquals(sqlDate.toString(), resultSet.getDate("created").toString());
            // no more data in database
            assertFalse(resultSet.next());
        }
    }

    @Test
    public void exceptionWhenAdminIdIsIncorrect() {
        plan.setAdminId(0);
        SQLException thrown = assertThrows(SQLException.class, () -> planDao.create(connection, plan), "Exception wasn't thrown");
    }

    @Test
    public void readPlan() throws SQLException {
        plan = planDao.create(connection, plan);
        Plan foundPlan = planDao.read(connection, plan.getId());
        assertEquals(foundPlan.getId(), plan.getId());
        assertEquals(foundPlan.getName(), plan.getName());
    }

    @Test
    public void returnNullPlanWhenDoesntExist() throws SQLException {
        plan = planDao.read(connection, 22);
        assertNull(plan);
    }

    @Test
    public void findsAllPlans() throws SQLException {
        Plan otherPlan = new Plan("FindAllTest", "Find", plan.getCreationDate(), 1);
        plan = planDao.create(connection, plan);
        otherPlan = planDao.create(connection, otherPlan);
        List<Plan> allPlans = planDao.findAll(connection);
        assertEquals(2, allPlans.size());
        for (Plan planElement : allPlans) {
            assertTrue(planElement.getId() == plan.getId() || planElement.getId() == otherPlan.getId());
        }
    }

    @Test
    public void returnsEmptyArrayWhenDataBaseIsEmpty() throws SQLException {
        List<Plan> allPlans = planDao.findAll(connection);
        assertEquals(allPlans, new ArrayList<>());
    }

    @Test
    public void deletePlan() throws SQLException {
        plan = planDao.create(connection, plan);
        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM plan")){
            // at least one element in db
            assertTrue(resultSet.next());
            assertEquals(plan.getId(), resultSet.getInt("id"));
        }
        planDao.delete(connection, plan.getId());
        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM plan")){
            // table should be empty now
            assertFalse(resultSet.next());
        }
    }

    @Test
    public void deleteNonExistingPlan_shouldNotThrowException() throws SQLException {
        planDao.delete(connection, 31);
        // maybe we will want something like NotFoundException here
    }

    @Test
    public void updatePlan() throws SQLException {
        plan = planDao.create(connection, plan);
        String newName = "newTest";
        String newDesc = "newDesc";
        plan.setName(newName);
        plan.setDescription(newDesc);
        planDao.update(connection, plan);
        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM plan")){
            assertTrue(resultSet.next());
            assertEquals(newName, resultSet.getString("name"));
            assertEquals(newDesc, resultSet.getString("description"));
        }
    }

    @Test
    public void updateNonExistingPlan_shouldNotThrowException() throws SQLException {
        plan.setId(13);
        planDao.update(connection, plan);
    }

    @Test
    public void getNumberOfPlansCreatedByUser() throws SQLException {
        planDao.create(connection, plan);
        assertEquals(1, planDao.getNumberOfPlansCreatedByUser(connection, plan.getAdminId()));
    }

    @Test
    public void returnZero_WhenUserHasNoPlans() throws SQLException {
        assertEquals(0, planDao.getNumberOfPlansCreatedByUser(connection, 0));
    }
}