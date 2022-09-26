package pl.coderslab.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import pl.coderslab.model.Plan;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    public Optional<T> read(int id);
    public List<T> findAll();
    public Optional<T> create(T t);
    public void delete(int id);
    public void update(T t);
}
