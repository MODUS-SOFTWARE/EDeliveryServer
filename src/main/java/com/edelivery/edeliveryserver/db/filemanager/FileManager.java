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
package com.edelivery.edeliveryserver.db.filemanager;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.handlers.EDeliveryHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author Pantelispanka
 */
@Stateless
public class FileManager {

    @Inject
    EDeliveryServerConfiguration edelConf;
    
    
    private String workingPath;
    
//    @PostConstruct
    public FileManager(){
        this.workingPath = edelConf.getWorkingPath();
    }
    
    
    public void saveFile(String fileId) {
    
        FileOutputStream fop = null;
        
        File file = new File(workingPath + fileId);
        
        
        try{
            file.createNewFile();
            fop = new FileOutputStream(file);
        }catch( IOException e ){
            throw new InternalServerErrorException("File path could not be created", e);
        }
        
        
        
    }

}
