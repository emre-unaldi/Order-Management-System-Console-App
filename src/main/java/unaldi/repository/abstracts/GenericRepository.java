package unaldi.repository.abstracts;

import java.util.List;

public interface GenericRepository<T> {
    void save(T entity);
    void delete(T entity);
    T findById(T entity);
    List<T> findAll();
}
