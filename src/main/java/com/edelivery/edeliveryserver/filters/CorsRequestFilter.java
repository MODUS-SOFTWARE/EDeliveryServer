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
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Pantelispanka
 */
@Provider
@PreMatching
public class CorsRequestFilter implements ContainerRequestFilter{
    
    private static final Logger LOG = Logger.getLogger(CorsRequestFilter.class.getName());

    @Context
    SecurityContext securityContext;

    /**
     * Replies with Status 200 to all OPTION Requests.
     *
     * @param request
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        String userName = securityContext.getUserPrincipal() != null ? securityContext.getUserPrincipal().getName() : "unkown";
        LOG.log(Level.INFO, "Request: {0} {1} by User {2}", new Object[]{request.getMethod(), request.getUriInfo().getRequestUri(), userName});

        LOG.log(Level.FINE, "Executing  CORS Request Filter");
        if (request.getRequest().getMethod().equals("OPTIONS")) {
            LOG.log(Level.FINE, "HTTP Method (OPTIONS) - Detected!");
            request.abortWith(Response.status(Response.Status.OK).build());
        }
    }
    
    
}
