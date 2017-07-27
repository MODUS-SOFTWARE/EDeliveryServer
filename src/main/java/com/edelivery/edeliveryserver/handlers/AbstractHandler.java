/*
 * Copyright (C) 2017 modussa
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.edelivery.edeliveryserver.handlers;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author modussa
 */
public abstract class AbstractHandler<T> {
    
    private final Class<T> entityClass;

    /**
     * Constructor that provides the Class of the Entity that is being handled,
     * to be used by the EntityManager.
     *
     * @param entityClass the Class of the Entity that is being handled.
     */
    public AbstractHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * This method must be implemented by any class extending
     * {@link AbstractHandler} and should provide an active
     * {@link EntityManager} instance.
     *
     * @return the EntityManager instance used by this handler.
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Persists the provided Entity into the database. The provided Entity is
     * now a managed instance and the underlying {@link EntityManager} will keep
     * track of changes made to it as long as the transaction stays open.
     *
     * @param entity The Entity to be persisted in the database.
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Persists the provided Entity into the database by creating a new Entity
     * or updating an existing one. This method makes a copy of the provided
     * Entity and closes the transaction as soon as the operation is finished.
     * Changes made to the Entity will not be tracked and will only be applied
     * if this method is called again.
     *
     * @param entity The Entity to be persisted or updated in the database.
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Removes the provided Entity from the database.
     *
     * @param entity The Entity to be removed from the database.
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Finds an Entity in the database with the specified id.
     *
     * @param id the Id of the Entity to be found.
     * @return the Entity with the specified Id.
     */
    public T find(Object id) {
        if(id == null){
            return null;
        }
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Finds all Entities of type {@link T} from the database.
     *
     * @return a {@link List} of all the Entities of type {@link T} from the
     * database.
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Finds Entities of type {@link T} in a specific range.
     *
     * @param range An integer array containing the desired range. Position of
     * the first result should be provided at array[0] and position of the last
     * result should be provided at array[1]
     *
     * @return a {@link List} of all the Entities of type {@link T} in the
     * specified range from the database.
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Counts all persisted entities of type {@link T} in the database.
     *
     * @return the sum of all persisted entities of type {@link T}
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    
    
}
