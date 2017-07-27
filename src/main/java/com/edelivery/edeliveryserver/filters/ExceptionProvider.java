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
package com.edelivery.edeliveryserver.filters;

import com.edelivery.edeliveryserver.dto.ErrorReport;
import java.util.Arrays;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Pantelispanka
 */
@Provider
public class ExceptionProvider implements ExceptionMapper<Exception>{
    
    @Override
    public Response toResponse(Exception exception){
                ErrorReport er = new ErrorReport();

        Exception exc = exception;
        String excClassName = exc.getClass().getName();
        String[] excClass = excClassName.split("\\.");
        String excName = excClass[(excClass.length - 1)].toUpperCase();
        
        switch(excName){
            case "BADREQUESTEXCEPTION": 
                er.setStatus(400);
                er.setDevMessage(Arrays.toString(exception.getStackTrace()));
                er.setErrorMessage(exception.getMessage().toString());
                return Response.status(Status.BAD_REQUEST).entity(er).build();
            case "NOTAUTHORIZEDEXCEPTION":
                er.setStatus(404);
                er.setDevMessage(Arrays.toString(exception.getStackTrace()));
                er.setErrorMessage(exception.getMessage().toString());
                return Response.status(Status.UNAUTHORIZED).entity(er).build();
            case "FORBIDDENEXCEPTION":
                er.setStatus(403);
                er.setDevMessage(Arrays.toString(exception.getStackTrace()));
                er.setErrorMessage(exception.getMessage().toString());
                return Response.status(Status.FORBIDDEN).entity(er).build();

        }
        er.setStatus(500);
        er.setDevMessage(Arrays.toString(exception.getStackTrace()));
        er.setErrorMessage("Unknown Error");
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(er).build();
        
    }
    
}
