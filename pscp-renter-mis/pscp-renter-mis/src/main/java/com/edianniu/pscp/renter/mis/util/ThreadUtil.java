package com.edianniu.pscp.renter.mis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程工具类
 * ClassName: ThreadUtil
 * Author: tandingbo
 * CreateTime: 2017-06-19 16:03
 * <p>
 * TODO 这个多线程工具类存在的问题
 * TODO 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。 说明：Executors各个方法的弊端：
 * TODO 1）newFixedThreadPool和newSingleThreadExecutor:
 * TODO 主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
 * TODO 2）newCachedThreadPool和newScheduledThreadPool:
 * TODO 主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
 */
public class ThreadUtil {
    private static final Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

    /**
     * 长时间线程池的最大线程数量
     */
    private static final int MAX_THREAD = 10;

    /**
     * 长时间线程的超时时间,如果线程执行时间超过这个时间会自动释放
     */
    private static final int LONG_TIMEOUT_TIME = 60000;

    /**
     * 短时间线程的超时时间,如果线程执行时间超过这个时间会自动释放
     */
    private static final int SORT_TIMEOUT_TIME = 12000;

    /**
     * 长时间线程池,用于执行短时间较长的线程,需要设置最大线程数量
     */
    private static ExecutorService longTimeThreadPool = Executors.newFixedThreadPool(MAX_THREAD);

    /**
     * 短时间线程池，用于存放执行时间较短的线程,无需设置线程的最大数量
     */
    private static ExecutorService sortTimeThreadPool = Executors.newCachedThreadPool();


    /**
     * 创建一个线程,将线程放到短时间线程池中,并且启动一个守护线程监控线程超时
     *
     * @param runnable:需要运行的线程
     * @throws Exception
     */
    public static synchronized void getSortTimeOutThread(Runnable runnable) {
        //将线程提交到短时间线程池中,然后会得到一个Future
        Future<String> future = (Future<String>) sortTimeThreadPool.submit(runnable);
        //启动一个守护线程，这个线程将计算超时
        new ThreadUtil().monitorThreadTime(SORT_TIMEOUT_TIME, future);
    }

    /**
     * 创建一个线程,将线程放到长时间线程池中,并且启动一个守护线程监控线程超时
     *
     * @param runnable:需要运行的线程
     * @throws Exception
     */
    public static synchronized void getLongTimeOutThread(Runnable runnable) throws Exception {
        //将线程提交到短时间线程池中,然后会得到一个Future
        Future<String> future = (Future<String>) longTimeThreadPool.submit(runnable);
        //启动一个守护线程，这个线程将计算超时
        new ThreadUtil().monitorThreadTime(LONG_TIMEOUT_TIME, future);
    }

    /**
     * 创建一个线程,将线程放到长时间线程池中,并且启动一个守护线程监控线程超时
     *
     * @param runnable:需要运行的线程
     * @param timeOutTime:自定义的超时时间
     * @throws Exception
     */
    public static synchronized void getLongTimeOutThread(Runnable runnable, int timeOutTime) throws Exception {
        //将线程提交到短时间线程池中,然后会得到一个Future
        Future<String> future = (Future<String>) longTimeThreadPool.submit(runnable);
        //启动一个守护线程，这个线程将计算超时
        new ThreadUtil().monitorThreadTime(timeOutTime, future);
    }


    /**
     * 监控线程超时守护线程,到超时时间去查看一下线程状态，如果正在执行中则停止线程（这段有问题）
     *
     * @param timeOutTime:超时时间
     * @param future:执行结果表单，将用来计算超时
     */
    private void monitorThreadTime(final int timeOutTime, final Future<String> future) {
        //执行守护线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //等待一段时间，然后调用执行结果，如果没有执行完，则试着去结束
                try {
                    Thread.sleep(timeOutTime);
                } catch (InterruptedException e) {
                    logger.error("线程池执行过程中发生异常", e);

                }
                //判断future是否执行完成,如果超时未完成，则试着结束线程
                if (!future.isDone()) {
                    //false表示不允许线程执行时中断,true表示允许
                    future.cancel(true);
                }
            }
        }).start();
    }
}
