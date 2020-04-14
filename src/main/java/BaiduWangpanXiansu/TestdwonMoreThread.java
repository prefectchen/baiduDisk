package BaiduWangpanXiansu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class TestdwonMoreThread {
    public static void main(String[] args) {
        //下载路径
        String str = "https://bjbgp01.baidupcs.com/file/b71e1f7fbr2a466ddc1462d836cb81c1?fid=1592894899-250528-714870127344118&rt=pr&sign=FDtAERVCY-DCb740ccc5511e5e8fedcff06b081203-hlIYc8jIgCZSfIwqmcRWRyONfhc%3D&expires=8h&chkv=1&chkbd=1&chkpc=et&dp-logid=2401839344468263762&dp-callid=0&dstime=1586755549&r=912793907";
        str = "https://bjbgp01.baidupcs.com/file/b71e1f7fbr2a466ddc1462d836cb81c1?fid=1592894899-250528-714870127344118&rt=pr&sign=FDtAERVCY-DCb740ccc5511e5e8fedcff06b081203-M%2FfC5nV8%2FG7OlQk9OhdoEWZmWu0%3D&expires=8h&chkv=1&chkbd=1&chkpc=et&dp-logid=2424740694664904345&dp-callid=0&dstime=1586840863&r=972532119";
        //创建线程池
        DownloadFileWithThreadPool dfw = new DownloadFileWithThreadPool();

        //调用下载方法
        downFile(str, dfw);

    }

    /****
     *
     * @param IntenetFilePath 需要下载的网络资源地址
     * @param dfw 下载文件线程池类对象
     * @return
     */
    public static String downFile(String IntenetFilePath, DownloadFileWithThreadPool dfw) {
        try {
            //程序开始时间
            long startTime = System.currentTimeMillis();
            //创建url对象
            URL url = new URL(IntenetFilePath);
            //链接url
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            //设置超时时间
            http.setConnectTimeout(3000);
            // 设置 User-Agent 避免被拦截
            http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
            //获得文件名称
            String fileName = getFileName(http, IntenetFilePath);
            //线程池
            dfw.getFileWithThreadPool(IntenetFilePath, "D:\\chen\\new\\" + fileName, 10);
            //程序结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("耗费时间： " + (endTime - startTime) + " ms");
            System.out.println("下载成功！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "下载成功";
    }




    /**
     * description: 获取文件名
     * @param http
     * @param urlPath
     * @throws UnsupportedEncodingException
     * @return String
     * @version v1.0
     * @author w
     * @date 2019年9月3日 下午8:25:55
     */
    private static String getFileName(HttpURLConnection http , String urlPath) throws UnsupportedEncodingException {
        String headerField = http.getHeaderField("Content-Disposition");
        String fileName = null ;
        if(null != headerField) {
            String decode = URLDecoder.decode(headerField, "UTF-8");
            fileName = decode.split(";")[1].split("=")[1].replaceAll("\"", "");
            System.out.println("文件名是： "+ fileName);
        }
        if(null == fileName) {
            // 尝试从url中获取文件名
            String[] arr  = urlPath.split("/");
            fileName = arr[arr.length - 1];
            System.out.println("url中获取文件名："+ fileName);
        }
        return fileName;
    }
}
