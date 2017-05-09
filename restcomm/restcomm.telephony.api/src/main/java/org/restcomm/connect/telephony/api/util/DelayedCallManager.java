package org.restcomm.connect.telephony.api.util;

import org.apache.log4j.Logger;
import org.restcomm.connect.dao.DaoManager;
import org.restcomm.connect.dao.entities.Client;

import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServletRequest;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by laslo.horvat@gmail.com on 8.5.2017..
 */
public class DelayedCallManager {
    private static final Logger logger = Logger.getLogger(DelayedCallManager.class);
    private static int nThreads = 3; //TODO move to configuration


    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(nThreads);

    public static void delayCallInvite(final SipServletRequest request, final Client client, final Client toClient,
                                       final DaoManager storage, final SipFactory sipFactory, final boolean patchForNat,
                                       final int numberOfRetry){
        // Create delayed task
        Runnable delayedTask = new Runnable(){
            @Override
            public void run() {
                try{
                    logger.info("Executing delayed call");
                    B2BUAHelper.redirectToB2BUA(request, client, toClient, storage, sipFactory, patchForNat, numberOfRetry);
                }catch(Exception e){
                    if (logger.isInfoEnabled()) {
                        logger.info("Cannot execute delayed call attempt");
                    }
                }
            }
        };

        executor.schedule(delayedTask, 3, TimeUnit.SECONDS);

    }

}
