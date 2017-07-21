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
package com.edelivery.edeliveryserver.api;

import com.modus.edeliveryclient.EDeliveryClientFactory;
import com.modus.edeliveryclient.EDeliveryClientImplementation;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.StandardBusinessDocumentHeader;
import com.modus.edeliveryclient.models.Authorization;
import com.modus.edeliveryclient.models.Messages;
import com.modus.edeliveryclient.models.ResponseMessage;
import io.swagger.annotations.Api;
import java.util.concurrent.CompletableFuture;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Pantelispanka
 */
@Path("/login")
@Consumes("application/json")
@Produces("application/json")
@Api(value = "/login", tags = "Authenticates administrative user and returns an api key")

public class AuthenticationResources {

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response getApiKey() {
        
        
        
        return Response.ok().build();
    }

}
