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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.modus.edeliveryclient.EDeliveryClient;
import com.modus.edeliveryclient.EDeliveryClientImplementation;
import com.modus.edeliveryclient.consumer.SbdConsumer;
import com.modus.edeliveryclient.consumer.SmpParticipantConsumer;

/**
 *
 * @author pantelispanka
 */
@Path("/SBD")
@Consumes("application/json")
@Produces("application/json")
@Api(value = "/StandardBusinessDocument", tags = "Standard business document resources")
public class SBDResources {

    @GET
    @Path("/allincoming")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Returns All Incoming SBD id's")
    public Response getAllIncoming() {
        return Response.ok().build();
    }

    @GET
    @Path("/alloutgoing")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Returns All Outgoing SBD id's")
    public Response getAllOutgoing() {
        return Response.ok().build();
    }

    
    @GET
    @Path("/incoming/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Returns an SBD by its ID")
    public Response getDocumentById(){
        return Response.ok().build();
    }
    
    
    @POST
    @Path("/newoutgoing")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Creates and a message at DB ")
    public Response newoutgoing(){ //input   DOCUMENT DATA.
        
        return Response.ok().build();
        
    }
    
    @POST
    @Path("/sendMessage")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Send document")
    public Response postSBD(Integer id){ //input   DOCUMENT DATA.
        
    	
    	EDeliveryClient deliveryClient ;
    	
    	//deliveryClient.sendMessage(params, auth);
        return Response.ok().build();
        
    }
    
    
    @DELETE
    @Path("/delete/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Deletes an SBD by its ID")
    public Response deleteSBD(){
        return Response.ok().build();
    }
    
}
