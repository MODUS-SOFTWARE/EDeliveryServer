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

import com.edelivery.edeliveryserver.db.constants.MessageStatus;
import com.edelivery.edeliveryserver.db.models.MessagesSent;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author modussa
 */
@Stateless
public class MessagesSentHandler extends AbstractDbHandler<MessagesSent> {

    @PersistenceContext
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessagesSentHandler() {
        super(MessagesSent.class);
    }

    public List<MessagesSent> getPendingMessages() {
        List<MessagesSent> messagesPending = em.createNamedQuery("MessagesSent.findByDocStatus")
                .setParameter("status", MessageStatus.PENDING.toString()).getResultList();
        return messagesPending;
    }

    public MessagesSent getMessageByUniqueId(String uniqueId) {

        MessagesSent ms = new MessagesSent();
        try {
            ms = (MessagesSent) em.createNamedQuery("MessagesSent.findByMessageUnId")
                    .setParameter("messageUnId", uniqueId).getSingleResult();
        } catch (NoResultException e) {
            throw new InternalServerErrorException("Could not find messages sent with unique id provided", e);
        }
        return ms;

    }

    public void updateMessage(MessagesSent message) {
        try {
//            em.getTransaction();
            em.merge(message);
//            em.close();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not update Entity", e);
        }

    }

}
