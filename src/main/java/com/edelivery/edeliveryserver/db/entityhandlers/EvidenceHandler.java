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

import com.edelivery.edeliveryserver.db.models.EvidenceReceived;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;

/**
 *
 * @author Pantelispanka
 */


@Stateless
public class EvidenceHandler extends AbstractDbHandler<EvidenceReceived>{
    
    
    @PersistenceContext
    EntityManager em;
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    public EvidenceHandler(){
        super(EvidenceReceived.class);
    }
    
    
    public void saveEvidence(EvidenceReceived evidenceReceinved){
        
        try{
            em.persist(evidenceReceinved);
        }catch(Exception e){
            throw new BadRequestException("Evidence could not be stored", e);
        }
       
    }
    
    
    
}
