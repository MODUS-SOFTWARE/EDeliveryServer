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
//package com.edelivery.edeliveryserver.filters;
//
//import java.io.Serializable;
//import javax.annotation.Priority;
//import javax.ws.rs.Priorities;
//import javax.ws.rs.Produces;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.ext.Provider;
//
//
//
///**
// *
// * @author Pantelispanka
// */
//@TokenSecured
//@Provider
//@Priority(Priorities.AUTHORIZATION)
//@Produces("application/json")
//public class TokenRequestFilter implements ContainerRequestFilter, Serializable {

//    @Inject
//    AuthenticationFacade authenticationFacade;

//    @Context
//    private ResourceInfo resourceInfo;
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) {
//
//        Class<?> resourceClass = resourceInfo.getResourceClass();
//        List<RoleEnum> classRoles = extractRoles(resourceClass);
//
//        MultivaluedMap headers = requestContext.getHeaders();
//        String api_key;
//        try {
//            api_key = headers.get("api_key").toString();
//            api_key = api_key.substring(1, api_key.length() - 1);
//        } catch (Exception e) {
//            throw new BadRequestException("No Api Key Provided. Please Login First");
//        }
//
////        authenticationFacade.apiKeyCheck(api_key);
//        checkPermissions(classRoles, api_key);
//
//    }
//
//    public List<RoleEnum> extractRoles(AnnotatedElement annotatedElement) {
//        if (annotatedElement == null) {
//            return new ArrayList<RoleEnum>();
//        } else {
//            TokenSecured secured = annotatedElement.getAnnotation(TokenSecured.class);
//            if (secured == null) {
//                return new ArrayList<RoleEnum>();
//            } else {
//                RoleEnum[] allowedRoles = secured.value();
//                return Arrays.asList(allowedRoles);
//            }
//        }
//    }
//
//    public void checkPermissions(List<RoleEnum> allowedRoles, String api_key) {
////        Object Role = authenticationFacade.getUserRole(api_key);
//        try {
//            if(allowedRoles != null){
//                allowedRoles.contains(Role);
//            }
//        } catch (Exception e) {
//            throw new ForbiddenException("Your User doesn't have permission for the request", e);
//        }
//
//    }
//
//}
