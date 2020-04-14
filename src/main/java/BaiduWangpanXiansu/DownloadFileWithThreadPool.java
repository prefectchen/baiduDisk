package BaiduWangpanXiansu;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
//调用线程池下载类
public class DownloadFileWithThreadPool {
    final long awaitTime = 5 * 1000;

    public void getFileWithThreadPool(String urlLocation,String filePath, int poolLength) throws IOException
    {
        //根据固定长度创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(poolLength);
        //获取文件长度
        long len = getContentLength(urlLocation);
        System.out.println("文件大小："+len);
        //遍历文件
        for(int i=0;i<poolLength;i++)
        {
            //获得文件指针起点偏移量
            long start=i*len/poolLength;
            //获得文件指针终点偏移量
            long end = (i+1)*len/poolLength-1;
            if(i==poolLength-1)
            {
                end =len;
            }
            Threadsingle download=new Threadsingle(urlLocation, filePath, start, end);
            threadPool.execute(download);
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            // 向学生传达“问题解答完毕后请举手示意！”
            threadPool.shutdown();

            // 向学生传达“XX分之内解答不完的问题全部带回去作为课后作业！”后老师等待学生答题
            // (所有的任务都结束的时候，返回TRUE)
            if(!threadPool.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted: " + e);
            threadPool.shutdownNow();
        }
//        threadPool.shutdown();


    }

    /***
     * 获取文件长度
     * @param urlLocation
     * @return 返回文件长度 按照字节计算
     * @throws IOException
     */
    public static long getContentLength(String urlLocation) throws IOException
    {
        URL url = null;
        if (urlLocation != null)
        {
            url = new URL(urlLocation);
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
      //  conn.setRequestMethod("GET");
        // 设置 User-Agent 避免被拦截
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        long len = conn.getContentLength();

        return len;
    }



}
