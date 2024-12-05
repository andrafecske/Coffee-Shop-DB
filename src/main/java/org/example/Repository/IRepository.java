package org.example.Repository;

import java.util.List;


/**
 * The {@code IRepository} interface defines the basic contract for a repository
 * that provides CRUD (Create, Read, Update, Delete) operations on entities of type {@code T}.
 * Any repository class implementing this interface should manage objects with unique identifiers.
 *
 * @param <T> The type of entity that the repository manages. This could be any class or interface,
 *            but typically, the entity will have an ID property for identifying it.
 */
public interface IRepository<T> {
    /**
     * Creates a new entity in the repository.
     *
     * @param entity The entity to be created in the repository.
     */
    void create(T entity);
    /**
     * Retrieves an entity from the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to retrieve.
     * @return The entity with the specified ID, or {@code null} if no entity with the given ID exists.
     */
    T read(Integer id);
    /**
     * Updates an existing entity in the repository.
     * If an entity with the specified ID exists, it will be replaced with the provided entity.
     *
     * @param id The unique identifier of the entity to update.
     * @param entity The updated entity that will replace the existing one.
     */
    void update(Integer id, T entity);
    /**
     * Deletes an entity from the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to delete.
     */
    void delete(Integer id);
    /**
     * Retrieves all entities stored in the repository.
     *
     * @return A list containing all entities in the repository.
     */
    List<T> getAll();
}
