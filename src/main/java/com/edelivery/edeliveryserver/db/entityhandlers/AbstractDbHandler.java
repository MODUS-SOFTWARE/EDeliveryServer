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
package com.edelivery.edeliveryserver.db.entityhandlers;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Pantelispanka
 */
public abstract class AbstractDbHandler<T> implements EDeliveryServerEntityHandler{

    private final Class<T> entityClass;

    public AbstractDbHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

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
    
    
    
    public T find(Object id) {
        if(id == null){
            return null;
        }
        return getEntityManager().find(entityClass, id);
    }
    
    
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

}
