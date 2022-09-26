package pl.coderslab.utils;

import pl.coderslab.exception.SessionNotFound;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static int getCurrentUserId(HttpSession session){
            Integer check = (Integer) session.getAttribute("logged");
            if(check == null){
                throw new SessionNotFound("Nie znaleziono ID uzytkownika");
            }
            return check;
    }
}
