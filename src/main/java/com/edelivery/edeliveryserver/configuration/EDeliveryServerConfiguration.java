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
package com.edelivery.edeliveryserver.configuration;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author Pantelispanka
 */
@Singleton
//@ApplicationScoped
@Startup
public class EDeliveryServerConfiguration {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private String windowsDocPath;
    private String unixDocPath;

    private String workingPath;

    private ResourceBundle EDeliveryServerProperties;

    private File file;
    
    public EDeliveryServerConfiguration(){}

    @PostConstruct
    public void Properties() {

        EDeliveryServerProperties = ResourceBundle.getBundle("EDeliveryServer", Locale.ENGLISH);

        if (OS.contains("win")) {
            this.workingPath = EDeliveryServerProperties.getString("windowsfilepath");
            this.file = new File(workingPath);
            if (file.canWrite() && file.canRead()) {
                System.out.println("Rights are rigth");
            } else {
                throw new InternalServerErrorException("Server Doesn't have right to save files received, Please give permitions to " + workingPath);
            }
        } else {
            this.workingPath = EDeliveryServerProperties.getString("unixfilepath");
            this.file = new File(workingPath);
            if (file.canWrite() && file.canRead()) {
                System.out.println("Rights are rigth");
            } else {
                throw new InternalServerErrorException("Server Doesn't have right to save files received, Please give permitions to " + workingPath);
            }
        }

    }

    public String getWorkingPath() {
        return workingPath;
    }

    public void setWorkingPath(String workingPath) {
        this.workingPath = workingPath;
    }

    public static String getOS() {
        return OS;
    }

    public static void setOS(String OS) {
        EDeliveryServerConfiguration.OS = OS;
    }

}