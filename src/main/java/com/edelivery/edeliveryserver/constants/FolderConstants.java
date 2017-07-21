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

/**
 *
 * @author modussa
 */
public class FolderConstants {
    
    /**
     * Enumeration that holds types of permissions that a User or Group can have
     * over a Folder, and their according Id.
     *
     * @see PFolder
     * @see PFoldersPermissions
     */
    public enum Permission {

        /**
         * Represents the permission value that gives a User the right to view a
         * folder.
         */
        VIEW_PERMISSION(1),
        /**
         * Represents the permission value that gives a User the right to rename
         * a folder.
         *
         */
        RENAME_PERMISSION(2),
        /**
         * Represents the permission value that gives a User the right to delete
         * a folder.
         *
         */
        DELETE_PERMISSION(4),
        /**
         *
         */
        CREATE_SUBFOLDERS_PERMISSION(8),
        /**
         *
         */
        INSERT_DOCUMENTS_PERMISSION(16),
        /**
         *
         */
        DELETE_DOCUMENTS_PERMISSION(32),
        /**
         *
         */
        SHOW_DOCUMENTS_PERMISSION(64),
        /**
         * Represents the permission value that gives a User the right to view
         * the documents contained in a Folder.
         *
         */
        SHOW_SUBFOLDERS_PERMISSION(128),
        /**
         *
         */
        CHOOSE_VIEW_STYLE_PERMISSION(256),
        /**
         *
         */
        CHANGE_PERMISSIONS_PERMISSION(512),
        /**
         *
         */
        MAKE_VIRTUAL_PERMISSION(1024),
        /**
         *
         */
        MAKE_NON_VIRTUAL_PERMISSION(2048);

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

    public static final int P_OBJECT_TYPE_FOLDER = 7;
    public static final int FOLDER_FAVORITES = 4; //Αγαπημένα
    public static final int FOLDER_MY_DOCUMENTS = 5; //Τα έγγραφά μου
    public static final int FOLDER_QUERY_WEB = 8;//ερώτημα στο web - στο FAT είναι το 2 κανονικά, όταν γίνει ενοποίηση να αντικατασταθεί το 8 με το 2
    
    
}
