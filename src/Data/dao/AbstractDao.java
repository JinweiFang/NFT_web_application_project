package Data.dao;

import java.util.Collection;

public interface AbstractDao<T> {
    Collection<T> findAll();
    T find(T item);
    T save(T item);
    T update(T item);
    T delete(T item);
}
