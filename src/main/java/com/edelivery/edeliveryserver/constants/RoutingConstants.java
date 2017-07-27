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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author modussa
 */
public class RoutingConstants {
    
    
    public enum Status {

        /**
         * Represents the pending state of a Dispatch. A newly created Dispatch
         * is usually a pending one.
         */
        PENDING(0),
        /**
         * Represents the completed state of a Dispatch.
         */
        COMPLETED(1),
        /**
         * Represents the denied state of a Dispatch.
         */
        DENIED(2),
        /**
         * Represents the approved state of a Dispatch.
         */
        APPROVED(3),
        
        READ(100002);

        private final Integer statusId;

        Status(Integer statusId) {
            this.statusId = statusId;
        }

        /**
         *
         * @return the Id of this state.
         */
        public Integer getId() {
            return this.statusId;
        }

    }

    /**
     * ActionType enumeration holds all possible actions that can happen on a
     * Dispatch Entity and their according Id values.
     *
     * @see PDispatch
     */
    public enum ActionType {

        /**
         * Represents the action of locking a Dispatch so that it can't be
         * processed by other users.
         */
        LOCK(2),
        /**
         * Represents the action of creating a new Dispatch assigned to a
         * specific User or Group.
         */
        ASSIGN(1),
        /**
         * Represents the action of unlocking a Dispatch so that it can be
         * processed by other users.
         */
        UNLOCK(3),
        /**
         * Represents the action of completing a Dispatch.
         */
        COMPLETE(4),
        /**
         * Represents the action of deleting a Dispatch.
         */
        DELETE(5);

        private final Integer typeId;

        ActionType(Integer typeId) {
            this.typeId = typeId;
        }

        /**
         *
         * @return The Id of this Action.
         */
        public Integer getId() {
            return this.typeId;
        }

        private static final Map<Integer, ActionType> lookup = new HashMap<>();

        static {
            for (ActionType actionType : ActionType.values()) {
                lookup.put(actionType.getId(), actionType);
            }
        }

        /**
         * Searches for a Dispatch ActionType with the specified id.
         *
         * @param id the id of the ActionType that needs to be found.
         * @return an ActionType with the specified id, or null if there is no
         * such ActionType.
         */
        public static ActionType getById(Integer id) {
            return lookup.get(id);
        }
    }

    /**
     * Priority enumeration holds default possible Priority types a Dispatch can
     * have.
     */
    @XmlRootElement
    public enum Priority {

        /**
         * Represents a Dispatch of low priority.
         */
        LOW(-1),
        /**
         * Represents a Dispatch of normal priority.
         */
        NORMAL(0),
        /**
         * Represents a Dispatch of high priority.
         */
        HIGH(1);

        private final Integer id;

        Priority(Integer id) {
            this.id = id;
        }

        /**
         *
         * @return the Id of this Priority.
         */
        public Integer getId() {
            return this.id;
        }

        private static final Map<Integer, Priority> lookup = new HashMap<>();

        static {
            for (Priority priority : Priority.values()) {
                lookup.put(priority.getId(), priority);
            }
        }

        /**
         * Searches for a Dispatch Priority with the specified id.
         *
         * @param id the id of the Priority that needs to be found.
         * @return a Priority with the specified id, or null if there is no such
         * Priority.
         */
        public static Priority getById(Integer id) {
            return lookup.get(id);
        }
    }
    
    /**
     * The Id of a Dispatch Entity as referenced in Papyrus database
     * transactions.
     */
    public static final int ROUTING_OBECT_TYPE = 13;

    /**
     * The default size of a database query with paging capabilities.
     */
    public static final int DEFAULT_DISPATCH_PAGE_SIZE = 500;
}
