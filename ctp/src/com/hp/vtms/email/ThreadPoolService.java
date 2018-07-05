package com.hp.vtms.email;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ThreadPoolService
 * 
 * @author yue
 */
public final class ThreadPoolService {

    /**
     * the ThreadPoolService logger
     */
    private Logger logger = LoggerFactory.getLogger(ThreadPoolService.class);

    /**
     * the minimum number of threads
     */
    private static final int COREPOOLSIZE = 3;

    /**
     * the maximum number of threads
     */
    private static final int MAXPOOLSIZE = 10;

    /**
     * the idle time of the thread if the thread idle time is more than this, it will be shutdown
     */
    private static final int KEEPALIVETIME = 5;

    /**
     * the size of the queue
     */
    private static final int WORKQUEUESIZE = 10;

    /**
     * ThreadPoolExecutor
     */
    private static ThreadPoolExecutor executor;

    /**
     * ThreadPoolService
     */
    private static ThreadPoolService threadPoolService;

    /**
     * private constructor
     */
    private ThreadPoolService() {
    }

    /**
     * getInstance
     * 
     * @return ThreadPoolService
     */
    public static ThreadPoolService getInstance() {
        if (threadPoolService == null) {
            threadPoolService = new ThreadPoolService();

            // create thread pool instance
            executor = new ThreadPoolExecutor(COREPOOLSIZE, MAXPOOLSIZE, KEEPALIVETIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(WORKQUEUESIZE), new ThreadPoolExecutor.CallerRunsPolicy());
        }
        return threadPoolService;
    }

    /**
     * add the task to the thread pool
     * 
     * @param task
     */
    public void execute(Runnable task) {
        logger.debug(task.getClass().getName() + " is added to the thread pool");
        executor.execute(task);
    }

    /**
     * getThreadExecutor
     * 
     * @return ThreadPoolExecutor
     */
    public ThreadPoolExecutor getThreadExecutor() {
        return executor;
    }

}
