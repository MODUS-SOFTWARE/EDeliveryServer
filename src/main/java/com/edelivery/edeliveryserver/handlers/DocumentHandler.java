///*
// * Copyright (C) 2017 modussa
// *
// * This program is free software; you can redistribute it and/or
// * modify it under the terms of the GNU General Public License
// * as published by the Free Software Foundation; either version 2
// * of the License, or (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program; if not, write to the Free Software
// * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// */
//package com.edelivery.edeliveryserver.handlers;
//
//import JavaPapyrusR6ServerApi.DataTypes.DocumentApi;
//import com.edelivery.edeliveryserver.db.models.Document;
//import com.edelivery.edeliveryserver.db.models.DocumentVersion;
//import java.util.Optional;
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
///**
// *
// * 
// */
//@Stateless
//public class DocumentHandler extends AbstractHandler<Document>{
//    
//    @PersistenceContext()
//    private EntityManager em;
//    
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }
//
//    /**
//     * Constructor that provides the class of Entity {@link PDocument} for use
//     * in generic methods of {@link AbstractHandler}
//     */
//    public DocumentHandler() {
//        super(Document.class);
//    }
//    
//    public DocumentApi findComplete(Object id) {
//        DocumentApi document = super.findAll(id);
////        document = updateWithVersions(document);
//
//        Integer mainDocId = document.getId();
//        Optional<DocumentVersion> o = document.getVersions().stream().findFirst();
//        if (o.isPresent()) {
//            mainDocId = o.get().getMainDocumentId();
//        }
////        document.setDispatches(routingHandler.findRoutingsOfDocument(mainDocId));
//        //return updateMostRecentChangesDate(document);
//        return document;
//    }
//    
//    
//}
