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

import com.edelivery.edeliveryserver.db.models.MessagesStatus;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;

/**
 *
 * @author Pantelispanka
 */

@Stateless
public class MessageStatusHandler extends AbstractDbHandler<MessagesStatus>{
    
    @PersistenceContext
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public MessageStatusHandler(){
        super(MessagesStatus.class);
    }
    
    public MessagesStatus findByStatus(String status){
        MessagesStatus ds = new MessagesStatus();
        try{
            ds = (MessagesStatus) em.createNamedQuery("MessagesStatus.findByStatus")
                    .setParameter("status", status).getSingleResult();
        }catch(Exception e){
            throw new BadRequestException("Status not found", e);
        }
        return ds;
    }
    
}
