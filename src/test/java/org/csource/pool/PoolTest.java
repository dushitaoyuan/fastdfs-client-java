package org.csource.pool;

import org.csource.client.FdfsClient;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dushitaoyuan
 * @date 2020/6/17
 */
public class PoolTest {
    FdfsClient fdfsFileUtil = new FdfsClient("classpath:fdfs.properties");


    @Test
    public void poolTest() throws InterruptedException {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        int limit = 10;
        for (int i = 0; i < limit; i++) {
            final int finalI = i;
            poolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        String fileId = fdfsFileUtil.upload(null, new ByteArrayInputStream("123456".getBytes()), UUID.randomUUID().toString() + ".txt");
                        System.out.println(String.format("%s %s", finalI, fileId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        poolExecutor.shutdown();
        while (!poolExecutor.awaitTermination(3, TimeUnit.SECONDS)) {
            Thread.sleep(3000);
        }
        System.out.println("finsh");
        Thread.sleep(5000000);
    }
}
