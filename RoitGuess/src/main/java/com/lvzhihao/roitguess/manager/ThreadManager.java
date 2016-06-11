package com.lvzhihao.roitguess.manager;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by vzhihao on 2016/1/1.
 */
public class ThreadManager {

    private ThreadPoolProxy longPool;
    private ThreadPoolProxy shortPool;

    public ThreadManager(){

    }
    static ThreadManager instance=new ThreadManager();

    public static ThreadManager getInstance(){
        return  instance;
    }

    public ThreadPoolProxy createLongPool(){
        if (longPool==null) {
            longPool = new ThreadPoolProxy(3, 3, 5000);
        }
        return longPool;
    }
    public ThreadPoolProxy createShortPool(){
        if (shortPool==null) {
            shortPool = new ThreadPoolProxy(3, 3, 5000);
        }
        return shortPool;
    }
    public class ThreadPoolProxy{
       ThreadPoolExecutor pool;
       int corePoolsize;
       int maxsize;
       int time;
       public  ThreadPoolProxy(int corePoolsize,int maxsize,int time){
           this.corePoolsize=corePoolsize;
           this.time=time;
           this.maxsize=maxsize;
       }

       public  void excute(Runnable runnable){
           if (pool==null){
               pool=new ThreadPoolExecutor(corePoolsize,maxsize,time, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
           }
           pool.execute(runnable);
       }
       public void cancel(Runnable runnable){
           if (pool!=null&&!pool.isShutdown()&&!pool.isTerminated()){
               pool.remove(runnable);
           }
       }
    }
}
