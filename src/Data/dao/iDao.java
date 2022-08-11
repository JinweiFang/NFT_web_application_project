package Data.dao;

import java.util.Collection;

public interface iDao<T, E> {
    Collection<T> findAll();
    T find(E id);
    T save(T item);
    T update(T item);
    T delete(E id);
}
