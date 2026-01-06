package com.github.hondams.common.repository;


import java.util.List;
import org.springframework.dao.OptimisticLockingFailureException;

public interface EntityRepository<T extends Entity<ID>, ID> {

    T findEntityById(ID id);

    T findEntityByIdWithLock(ID id);

    List<T> insertEntities(List<T> entities);

    boolean updateEntityForcibly(T entity);

    boolean updateEntityWithCheckExclusive(T entity);

    boolean deleteEntityByIdForcibly(ID id);

    boolean deleteEntityByIdWithCheckExclusive(ID id);

    boolean existsEntityById(ID id);

    default T insertEntity(T entity) {
        List<T> entities = insertEntities(List.of(entity));
        return entities.getFirst();
    }

    default boolean updateEntityByIdExclusively(T entity) {
        boolean updated = updateEntityWithCheckExclusive(entity);
        if (!updated) {
            if (existsEntityById(entity.getId())) {
                throw new OptimisticLockingFailureException("Entity with id " + entity.getId()
                        + " has been modified by another transaction.");
            }
        }
        return updated;
    }

    default boolean deleteEntityByIdExclusively(ID id) {
        boolean deleted = deleteEntityByIdWithCheckExclusive(id);
        if (!deleted) {
            if (existsEntityById(id)) {
                throw new OptimisticLockingFailureException(
                        "Entity with id " + id + " has been modified by another transaction.");
            }
        }
        return deleted;
    }
}
