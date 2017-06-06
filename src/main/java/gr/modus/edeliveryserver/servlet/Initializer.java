package gr.modus.edeliveryserver.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import gr.modus.edelivery.pollers.SendPoller;

/**
 * Κλάση η οποία καλείται όταν γίνεται deployed η εφαρμογή στον application server
 * και χρησιμοποιείται για να αποθηκεύσει τις παραμέτρους του web.xml σε global μεταβλητές
 * Αν δεν υπάρχει η μεταβλητή στο web.xml τότε η μεταβλητή θα πάρει τιμή null
 */
public class Initializer extends HttpServlet 
{
	SendPoller sendPoller;
   
    public void init(ServletConfig config) throws ServletException {
     
     
        super.init(config);
           
        sendPoller  = new SendPoller();
        sendPoller.init();
    }
   
}
