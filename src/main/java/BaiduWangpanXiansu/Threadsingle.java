package BaiduWangpanXiansu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

//定义线程 实现runnable接口
public class Threadsingle implements Runnable{

    private String urlLocation;//url路径

    private String filePath; //保存文件路径

    private long start; //开始保存位置

    private long end;  //结束保存位置

    //构造函数
    Threadsingle(String urlLocation, String filePath, long start, long end)
    {
        this.urlLocation = urlLocation;
        this.filePath = filePath;
        this.start = start;
        this.end = end;
    }

    //重写run方法
    @Override
    public void run()
    {
        try
        {
            //获取连接
            HttpURLConnection conn = getHttp();
            //设置开始和结束节点
            conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
            //设置请求头标识 防止被屏蔽ip
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
            //根据存储路径 创建文件
            File file = new File(filePath);
            //创建随机文件输出流
            RandomAccessFile out = null;
            if (file != null)
            {
            // “r”：以只读的方式打开，调用该对象的任何write（写）方法都会导致IOException异常
            // “rw”：以读、写方式打开，支持文件的读取或写入。若文件不存在，则创建之。
            // “rws”：以读、写方式打开，与“rw”不同的是，还要对文件内容的每次更新都同步更新到潜在的存储设备中去。这里的“s”表示synchronous（同步）的意思
            // “rwd”：以读、写方式打开，与“rw”不同的是，还要对文件内容的每次更新都同步更新到潜在的存储设备中去。使用“rwd”模式仅要求将文件的内容更新到存储设备中，而使用“rws”模式除了更新文件的内容，还要更新文件的元数据（metadata），因此至少要求1次低级别的I/O操作
                out = new RandomAccessFile(file, "rwd");
            }
            //表示文件指针的偏移量。从文件的开头开始测量，在此位置进行下一步的读或写。
            out.seek(start);
            //获取输入流
            InputStream in = conn.getInputStream();
            //定义字节数组
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1)
            {
                out.write(b, 0, len);
            }
            in.close();
            out.close();
        }
        catch (Exception e)
        {
            e.getMessage();
        }

    }


    /***
     * 获取HTTPURLConnection 连接
     * @return HTTPURLConnection
     * @throws IOException
     */

    public HttpURLConnection getHttp() throws IOException
    {
        URL url = null;
        if (urlLocation != null)
        {
            url = new URL(urlLocation);
        }
        //
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置连接时间 防止断掉
        conn.setReadTimeout(5000);
        //设置请求头 防止被屏蔽
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
       // conn.setRequestMethod("GET");
        //返回HttpUrlConnection
        return conn;

    }
}
