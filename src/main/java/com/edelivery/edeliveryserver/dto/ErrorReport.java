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
package com.edelivery.edeliveryserver.dto;

/**
 *
 * @author Pantelispanka
 */
public class ErrorReport {
    
//    private Integer errorId;
    private Integer status;
    private String errorMessage;
    private String DevMessage;

//    public Integer getErrorId() {
//        return errorId;
//    }
//
//    public void setErrorId(Integer errorId) {
//        this.errorId = errorId;
//    }
//
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDevMessage() {
        return DevMessage;
    }

    public void setDevMessage(String DevMessage) {
        this.DevMessage = DevMessage;
    }
    
       
}
