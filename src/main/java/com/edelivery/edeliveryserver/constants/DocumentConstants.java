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
package com.edelivery.edeliveryserver.constants;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author modussa
 */
public class DocumentConstants {
    
    /**
     * Enumeration that holds types of permissions that a User or Group can have
     * over a Document, and their according Id.
     *
     * @see PDocumentsPermissions
     * @see PDocument
     */
    public enum Permission {

        /**
         * Represents the permission value that gives a User the right to View a
         * Document.
         */
        VIEW_PERMISSION(1),
        /**
         * Represents the permission value that gives a User the right to View a
         * Document in a list. If one does not have this permission for a
         * Document, they cannot view that document included in it's parent's
         * folder contents.
         */
        VIEW_IN_LIST_PERMISSION(4096);

        private final Integer permissionId;

        Permission(Integer permissionId) {
            this.permissionId = permissionId;
        }

        /**
         *
         * @return the Id of this Permission value.
         */
        public Integer getId() {
            return this.permissionId;
        }

    }

    /**
     * Enumeration that holds all Document Types as registered in Papyrus
     * Database and their according Id.
     *
     * @see PDocument
     */
    public enum Type {

        /**
         * Represents a normal Document in the Papyrus System, meaning a
         * Document that has a physical substance and is backed by a real file.
         */
        /**
         * Represents a normal Document in the Papyrus System, meaning a
         * Document that has a physical substance and is backed by a real file.
         */
        NORMAL_DOCUMENT(1),
        /**
         * Represents a Papyrus Case.
         */
        CASE_DOCUMENT(2),
        /**
         * Represents a blank Document in the Papyrus System, meaning a Document
         * that has no physical substance and is not backed by a real file.
         */
        BLANK_DOCUMENT(3),
        EVENT(4),
        TASK(5),
        ANNOUNCEMENT(6);

        private final Integer typeId;

        Type(Integer typeId) {
            this.typeId = typeId;
        }

        /**
         *
         * @return the Id of this Document type.
         */
        public Integer getId() {
            return this.typeId;
        }

        private static final Map<Integer, DocumentConstants.Type> lookup = new HashMap<>();

        static {
            for (DocumentConstants.Type type : DocumentConstants.Type.values()) {
                lookup.put(type.getId(), type);
            }
        }

        /**
         * Searches for a Document Type with the specific id.
         *
         * @param Id the Id of the Document Type needed.
         * @return the Document Type represented by the provided Id, or null if
         * there is no such Type.
         */
        public static DocumentConstants.Type getById(Integer Id) {
            return lookup.get(Id);
        }
    }

    /**
     * Enumeration that holds all Actions that can be performed on Documents as
     * registered in the Papyrus Database and their according id.
     *
     * @see PDocument
     */
    public enum Action {

        /**
         * Represents the action of viewing a Document's actual data. Viewing a
         * Document's meta data is not a VIEW_DOCUMENT action.
         */
        VIEW_DOCUMENT(1),
        /**
         * Represents the action of editing Document's actual data.
         */
        EDIT_DOCUMENT(2);

        private final Integer actionId;

        Action(Integer actionId) {
            this.actionId = actionId;
        }

        /**
         *
         * @return the Id of this Document Action.
         */
        public Integer getId() {
            return this.actionId;
        }

    }

    public static final int DEFAULT_DOCUMENT_PAGE_SIZE = 500;

    
    
}
