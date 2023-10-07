package by.javaguru.hibernate.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, K> {
    void save(T ticket);
    void remove(T ticket);
    void update(T ticket);
    List<T> getAll();
    Optional<T> findById(K key);
}
