package com.comm.http.framework;

import android.util.Log;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by iqiao on 2020-03-04 16:17
 * Desc:
 *
 * @author iqiao
 */
public class RequestManager {


    private final String POOL_NAME = "http-pool-";
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 任务队列
     */
    private LinkedBlockingDeque<Runnable> tasks = new LinkedBlockingDeque<>(10);
    /**
     * 线程池任务
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 添加任务
     *
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 构造器
     */
    public RequestManager() {
        threadPoolExecutor = new ThreadPoolExecutor(1, 3, 5, TimeUnit.SECONDS, tasks, (Runnable runnable) -> {
            return new Thread(runnable, POOL_NAME + atomicInteger.getAndAdd(1));
        }, (Runnable runnable, ThreadPoolExecutor executor) -> {
            tasks.add(runnable);
        });
    }

    /**
     * 具体执行任务
     */
    private Runnable runnable = () -> {
        while (true) {
            try {
                Runnable runnable1 = tasks.take();
//                threadPoolExecutor.submit(runnable1);
                threadPoolExecutor.execute(runnable1);
                Log.e("RequestManager", "excute: " + runnable1 + "   " + tasks.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 单例
     *
     * @return
     */

    public static RequestManager getInstance() {
        return InnerClass.INSTANCES;
    }

    private static class InnerClass {
        private static final RequestManager INSTANCES = new RequestManager();
    }
}
