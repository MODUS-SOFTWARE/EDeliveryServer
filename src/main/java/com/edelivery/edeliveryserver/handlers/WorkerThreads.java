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
package com.edelivery.edeliveryserver.handlers;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author Pantelispanka
 */
@Singleton
//@DependsOn("EDeliveryServerConfiguration")
public class WorkerThreads {

    private static final Logger LOG = Logger.getLogger(WorkerThreads.class.getName());

    
    @Inject
    EDeliveryServerConfiguration config;
    
    @EJB
    RemDispIncRun incTask;
    
    @EJB
    RemDispOutRun outTask;
    
//    private ManagedExecutorService executor;
    private int poolSize = 1;
    private int batchSize = poolSize * 1;
    private int INTERVAL = 5000;

    private Future future;

    private ScheduledExecutorService executor1;

    private ScheduledExecutorService executor2;
    
    public WorkerThreads() {
    }

    public void Start() {

        System.out.println("Starting pollers");
                
        executor1 = Executors.newScheduledThreadPool(poolSize);

        executor2 = Executors.newScheduledThreadPool(poolSize);
        
        
        RemDispIncRun.findIncoming findIncTask = incTask.new findIncoming();

        RemDispOutRun.sendOutgoing sendOutgoingTask = outTask.new sendOutgoing();
        
        Runnable task1 = findIncTask;
        Runnable task2 = sendOutgoingTask;
        
        
        int initialDelay = 0;
        int period = 10;
        executor1.scheduleAtFixedRate(task1, initialDelay, period, TimeUnit.SECONDS);
        executor2.scheduleAtFixedRate(task2, initialDelay, period, TimeUnit.SECONDS);
        
    }

    @PreDestroy
    public void shutdown() {

        try {
            executor1.shutdown();
            executor1.awaitTermination(15, TimeUnit.SECONDS);
            executor1 = null;
            executor2.shutdown();
            executor2.awaitTermination(15, TimeUnit.SECONDS);
            executor2 = null;
        } catch (InterruptedException e) {
            throw new InternalServerErrorException("Poller Problem", e);
        }

    }

}
