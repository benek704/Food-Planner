package pl.coderslab.utils;

import pl.coderslab.dao.Dao;
import pl.coderslab.exception.IdNotFoundException;
import pl.coderslab.model.Administrable;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

public class ServletUtil {

    /**
     * Retrieves an object by id with provided dao class, and sets it as attributeName in request.
     * Throws IdNotFoundException if no object is found.
     */
    public static <T> void searchIdRequest(Dao<T> dao, String attributeName, HttpServletRequest request) throws IdNotFoundException {
        try {
            String idParam = request.getParameter("id");
            int id = Integer.parseInt(idParam);
            T object = dao.read(id).orElseThrow();
            request.setAttribute(attributeName, object);
        } catch (NumberFormatException | NullPointerException | NoSuchElementException e) {
            throw new IdNotFoundException(e.getMessage());
        }
    }

    /**
     * Deletes object with id provided in request if current user is owner of that object
     */
    public static <T extends Administrable> void deleteObjectWithRequestedId(Dao<T> dao, HttpServletRequest request) {
        try {
            String idParam = request.getParameter("id");
            int id = Integer.parseInt(idParam);
            int adminId = SessionUtil.getCurrentUserId(request.getSession());
            if (adminId == dao.read(id).orElseThrow().getAdminId()) {
                dao.delete(id);
            }
        } catch (NumberFormatException | NullPointerException | NoSuchElementException e) {
            // nothing to do here
        }
    }
}
