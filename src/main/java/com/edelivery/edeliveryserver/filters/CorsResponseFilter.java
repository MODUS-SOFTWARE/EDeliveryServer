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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Pantelispanka
 */
@Provider
@PreMatching
public class CorsResponseFilter implements ContainerResponseFilter{
    
    
    
    private static final Logger LOG = Logger.getLogger(CorsResponseFilter.class.getName());

    /**
     * Filters all Responses from the Service in order to add CORS Headers.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        LOG.log(Level.FINE, "Executing CORS Response Filter for request {0}", request.getUriInfo().getPath());
        for (String header : request.getHeaders().keySet()) {
            LOG.log(Level.FINE, "{0}:{1}", new Object[]{header, request.getHeaders().getFirst(header)});
        }
        response.getHeaders().add("Access-Control-Allow-Origin", request.getHeaderString("origin") != null ? request.getHeaderString("origin") : "localhost");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Headers", "origin, credentials, authorization, accept, content-type");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    }
    
    
    
}
