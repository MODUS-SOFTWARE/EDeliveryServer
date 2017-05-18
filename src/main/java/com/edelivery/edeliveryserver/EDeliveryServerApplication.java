package com.edelivery.edeliveryserver;

import com.edelivery.edeliveryserver.api.ParticipantsResources;
import com.edelivery.edeliveryserver.api.SBDResources;
import io.swagger.jaxrs.config.BeanConfig;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author pantelispanka
 */
@ApplicationPath("/api")
public class EDeliveryServerApplication extends Application {

    public EDeliveryServerApplication() {

        try {
            BeanConfig beanConfig = new BeanConfig();
            beanConfig.setVersion("1.0");
            beanConfig.setSchemes(new String[]{"http"});
            beanConfig.setHost("localhost:8080");
            beanConfig.setBasePath("/EDeliveryServer/api");
            beanConfig.setResourcePackage("com.edelivery.edeliveryserver.api");
            beanConfig.setScan(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet();

        resources.add(ParticipantsResources.class);
        resources.add(SBDResources.class);

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        
        
        return resources;

    }

}
