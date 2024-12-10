package org.example.Repository;

import org.example.Exceptions.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.example.Exceptions.DataBaseException;
import org.example.model.HasID;

import java.util.List;

/**
 * The {@code DBRepo} abstract class provides a base implementation of the {@code IRepository} interface
 * for repositories interacting with a database. It defines common database operations.
 *
 * @param <T> The type of entity managed by the repository.
 */
public class DBRepo<T extends HasID> implements IRepository<T> {
    private final SessionFactory sessionFactory;
    private final Class<T> entityType;

    public DBRepo(SessionFactory sessionFactory, Class<T> entityType) {
        this.sessionFactory = sessionFactory;
        this.entityType = entityType;
    }

    @Override
    public void create(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(entity); // Save new entity
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DataBaseException("Error creating entity in the database.", e);
        } finally {
            session.close();
        }
    }

 //   @Override
//    public T read(Integer id) {
//        Session session = sessionFactory.openSession();
//        try {
//            T entity = session.get(entityType, id); // Fetch the entity by ID
//            if (entity == null) {
//                throw new EntityNotFoundException(entityType.getSimpleName() + " with ID " + id + " not found.", null);
//            }
//            return entity;
//        } catch (EntityNotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new DataBaseException("Error reading entity in the database.", e);
//        } finally {
//            session.close();
//        }
//    }
    @Override
    public T read(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(entityType, id); // Fetch the entity by ID
        } catch(Exception e){
            throw new DataBaseException("Error reading entity in the database.", e);
        }

        finally {
            session.close();
        }
    }


    @Override
    public void update(Integer id, T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            T existingEntity = session.get(entityType, id); // Get existing entity
            if (existingEntity == null) {
                throw new EntityNotFoundException(entityType.getSimpleName() + " with ID " + id + " not found.", null);
            }
            session.merge(entity); // Update the existing entity with new data
            transaction.commit();
        } catch (EntityNotFoundException e) {
            throw e; // Re-throw EntityNotFoundException for clarity
        } catch (Exception e) {
            transaction.rollback();
            throw new DataBaseException("Error updating entity in the database.", e);
        } finally {
            session.close();
        }
    }


    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            T entity = session.get(entityType, id); // Get the entity by ID
            if (entity != null) {
                session.delete(entity); // Delete the entity
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DataBaseException("Error deleting entity in the database.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<T> getAll() {
        Session session = sessionFactory.openSession();
        try {
            String query = String.format("FROM %s", entityType.getSimpleName()); // HQL query
            return session.createQuery(query, entityType).getResultList(); // Retrieve all entities
        } catch(Exception e){
            throw new DataBaseException("Error reading all the entities in the database.", e);
        }
        finally {
            session.close();
        }
    }
}