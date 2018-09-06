package com.edianniu.pscp.das.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ThreadPoolUtils
 *
 * @author: tandingbo
 * CreateTime: 2017-10-24 22:06
 */
public class ThreadPoolUtils {
    /**
     * 创建线程池
     */
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS,
            // 工作队列
            new SynchronousQueue<>(),
            // 线程池饱和处理策略
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 提交一个线程
     * 具体调用示例
     * ThreadPoolUtils.submit(() -> {
     * TODO 业务逻辑...
     * });
     *
     * @param runnable 处理业务线程
     */
    public static void submit(Runnable runnable) {
        EXECUTOR_SERVICE.submit(runnable);
    }
}
