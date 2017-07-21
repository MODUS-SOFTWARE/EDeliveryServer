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

import com.edelivery.edeliveryserver.db.models.DocumentsReceived;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

/**
 *
 * @author modussa
 *
 */
@Stateless
@Transactional
public class DocumentsReceivedHandler extends AbstractDbHandler<DocumentsReceived> {

    @PersistenceContext
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentsReceivedHandler() {
        super(DocumentsReceived.class);
    }

    public void saveMessage(DocumentsReceived docRec) {

        try {
            em.persist(docRec);
        } catch (Exception e) {
            throw new BadRequestException("Message could not be stored", e);
        }

    }
    
    
    public List<DocumentsReceived> gatMessageByUniqueId(String id){
        List<DocumentsReceived> docRec = (List<DocumentsReceived>) em.createNamedQuery("DocumentsReceived.findByMessageUniqueId")
                .setParameter("messageUniqueId", id)
                .getResultList();
        return docRec;
    }

}
