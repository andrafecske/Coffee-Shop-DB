package org.example.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.model.HasID;

import java.util.List;

/**
 * The {@code DBRepo} abstract class provides a base implementation of the {@code IRepository} interface
 * for repositories interacting with a database. It defines common database operations.
 *
 * @param <T> The type of entity managed by the repository.
 */
public abstract class DBRepo<T extends HasID> implements IRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityType;

    /**
     * Constructs a new {@code DBRepo} for the specified entity type.
     *
     * @param entityType The class type of the entity.
     */
    protected DBRepo(Class<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T read(Integer id) {
        return entityManager.find(entityType, id);
    }

    @Override
    public void update(Integer id, T entity) {
        if (read(id) != null) {
            entityManager.merge(entity);
        }
    }

    @Override
    public void delete(Integer id) {
        T entity = read(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public List<T> getAll() {
        return entityManager.createQuery("FROM " + entityType.getName(), entityType).getResultList();
    }
}
