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
import com.edelivery.edeliveryserver.db.entityhandlers.MessagesReceivedFromApHandler;
import com.edelivery.edeliveryserver.db.models.DocumentsReceived;
import com.edelivery.edeliveryserver.db.models.MessageReceivedFromAp;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.StandardBusinessDocument;
import com.modus.edeliveryclient.models.Messages;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author modussa
 */
@Singleton
//@DependsOn("Initializer")
public class Poller {

//    @Inject
//    MessagesReceivedFromApHandler mesRecieved;

    @Inject
    EDeliveryHandler deliveryHandler;

    @Inject
    EDeliveryServerConfiguration serverConfig;

    private static final Logger LOG = Logger.getLogger(Poller.class.getName());

    private Boolean poll;
    private Future future;

    @Resource
    ManagedExecutorService managedExecutor;


    public void start() {
        LOG.info("Start Poller");
        future = managedExecutor.submit(() -> {
            try {
                _start();
            } catch (Exception e) {
                throw new InternalServerErrorException("Poller does not start");
            }
        });

    }

    public void stop() {
        this.poll = false;

    }

    private void _start() throws Exception {

        LOG.log(Level.INFO, "Indexing poller started.");
//        managedExecutor.execute();

    }

//    private void pollNewMessages() throws InterruptedException, ExecutionException {
//
//        Messages msgs = deliveryHandler.getMessagesList();
//        StandardBusinessDocument sbd = new StandardBusinessDocument();
//        for (String messageId : msgs.getMessages().getMessageId()) {
//            try {
//                mesRecieved.getMessageUniqueId(messageId);
//            } catch (BadRequestException e) {
//                MessageReceivedFromAp newMessage = new MessageReceivedFromAp();
//                newMessage.setMessageUniqueId(messageId);
//                try {
//                    mesRecieved.create(newMessage);
//                    DocumentsReceived dr = new DocumentsReceived();
////                    dr.set
////                    sbd = deliveryHandler.getMessage(messageId);
//                } catch (Exception ex) {
//                    throw new InternalServerErrorException(ex.getMessage());
//                }
//
//            }
//        }
//
//    }

}

//@Singleton
//@DependsOn("Initializer")
//public class Poller {
//
//    private static final Logger LOG = Logger.getLogger(Poller.class.getName());
//    @Resource
//    private TimerService timerService;
//    private DataSource datasource;
//    private Indexer indexer;
//    private NotificationsDBCommands notifCommands;
//    private DBHandler db;
//    private Serializer serializer;
//
//    private ExecutorService executor;
//    private Timer timer;
//
//    //TODO: move these settings to settings config
//    private int poolSize = 1;
//    private int batchSize = poolSize * 1;
//    private int INTERVAL = 5000;
//    
//    
//    
//    @Inject
//    public Poller( DataSource datasource, Indexer indexer, NotificationsDBCommands notifCommands, DBHandler db,Serializer serializer) {
//        //this.timerService = timerService;
//        this.datasource = datasource;
//        this.indexer = indexer;
//        this.notifCommands = notifCommands;
//        this.db = db;
//        this.serializer = serializer;
//    }
//
//    public Poller() {
//    }
//    
//    public void start()  {
//        
//    	try (Connection connection = datasource.getConnection()) {
//            notifCommands.updateAllNotificationStatus(NotificationItem.IndexStatus.RUNNING.getId(), NotificationItem.IndexStatus.QUEUED.getId(), connection);
//        
//	        if (timer != null) {
//	        	try{
//	        		timer.cancel();
//	        	}
//	        	catch(javax.ejb.NoSuchObjectLocalException ex){ //όταν κληθεί στην περίπτωση που είναι canceled idi.
//	        		LOG.log(Level.WARNING,"NoSuchObjectLocalException on start Poller timer");
//	        	}
//	            timer = null;
//	        }
//	        if (executor != null) {
//	            executor.shutdown();
//	            executor.awaitTermination(15, TimeUnit.SECONDS);
//	            executor = null;
//	        }
//	        executor = Executors.newFixedThreadPool(poolSize);
//	        TimerConfig timerConf = new TimerConfig();timerConf.setPersistent(false);
//	        timer = timerService.createIntervalTimer(500, INTERVAL, timerConf);
//    	}
//    	catch(Exception ex){
//    		LOG.log(Level.SEVERE, "start poller", ex);
//    	}
//    }
//
//    @PreDestroy
//    public void stop() throws InterruptedException {
//        if (timer != null) {
//        	try{
//        		timer.cancel();
//        	}
//        	catch(javax.ejb.NoSuchObjectLocalException ex){ //όταν κληθεί στην περίπτωση που είναι canceled idi.
//        		LOG.log(Level.WARNING,"NoSuchObjectLocalException on stop Poller timer");
//        	}
//        }
//        if (executor != null) {
//            executor.shutdown();
//            executor.awaitTermination(15, TimeUnit.SECONDS);
//            executor = null;
//        }
//    }
//
//    @Timeout
//    public void timeout(Timer timer) {
//        for (int i = 0; i < batchSize; i++) {
//            try (Connection connection = datasource.getConnection();) {
//                NotificationItem item = db.getNextNotification(connection);
//                if (item == null) {
//                    break;
//                }
//                db.updateNotificationToRunning(item);
//                try{
//                	executor.submit(() -> poll(item));
//                }
//                catch(NullPointerException nullex){
//                	LOG.log(Level.SEVERE,"timeout poller null pointer exception cancel timer",nullex);
//                	timer.cancel();
//                }
//            } catch (SQLException ex) {
//                LOG.log(Level.SEVERE, "Error when trying to poll Notification items.", ex);
//                break;
//            }
//        }
//    }
//    
//    
//    public void poll(NotificationItem item) {
//        
//        try {
//        	
//            try {
//                IndexStatus status;
//                try {
//                    indexer.index(item);
//                    status = NotificationItem.IndexStatus.COMPLETED;
//                } catch (DocumentNotIndexable ex) {
//                    //LOG.log(Level.INFO, ex.getMessage());
//                    status = IndexStatus.SKIPPED;
//                }
//                db.updateIndexedDB(item, status.getId(), "");
//            } catch (NotAvailableNodeException ex) {
//                db.updateIndexedDB(item, NotificationItem.IndexStatus.FAILED.getId(), ex.getMessage());
//            } catch (Exception ex) {
//                db.updateIndexedDB(item, NotificationItem.IndexStatus.FAILED.getId(), ex.getMessage());
//            } finally {
//                db.removeNotification(item);
//            }
//        } catch (Exception ex) {
//            //LOG.log(Level.SEVERE, ex.getMessage(), ex);
//        }
//    }
//}

