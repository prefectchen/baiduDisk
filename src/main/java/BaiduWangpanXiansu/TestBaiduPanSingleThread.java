package BaiduWangpanXiansu;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

//下载百度网盘单独文件 < 40M  单线程操作  比较慢
public class TestBaiduPanSingleThread {
    public static void main(String[] args) throws Exception {
        String sty ="https://bjbgp01.baidupcs.com/file/b71e1f7fbr2a466ddc1462d836cb81c1?fid=1592894899-250528-714870127344118&rt=pr&sign=FDtAERVCY-DCb740ccc5511e5e8fedcff06b081203-hlIYc8jIgCZSfIwqmcRWRyONfhc%3D&expires=8h&chkv=1&chkbd=1&chkpc=et&dp-logid=2401839344468263762&dp-callid=0&dstime=1586755549&r=912793907";
        sty="http://v5-dy-a.ixigua.com/9ac4eca1cdf7ea985033a785f019f093/5e942fcb/video/tos/cn/tos-cn-ve-15/c8644db0a87a410aad4490e7f9216416/?a=1128&br=0&bt=1261&cr=0&cs=0&dr=0&ds=3&er=&l=2020041316230601001404003132119636&lr=aweme&qs=0&rc=amQ8eG5xeWw1czMzOWkzM0ApNDM0NmY3NmVoN2ZoMzk1Z2dzNm1tZW9jbl9fLS0vLS9zczIyL18xLV4vYzQtLWMtLmA6Yw%3D%3D&vl=&vr=";
        long startTime=System.currentTimeMillis();
        download(sty, "D:/chen");
        long endTime=System.currentTimeMillis();
        System.out.println("耗费时间： "+(endTime-startTime)+" ms");
        System.out.println("下载成功！");
    }

/*
     * description: 使用url 下载远程文件
	 * @param urlPath  --- url资源
	 * @param targetDirectory --- 目标文件夹
	 * @throws Exception
	 * @return void
	 * @version v1.0
            * @author w
	 * @date 2019年9月3日 下午8:29:01
            */
    public static void download(String urlPath , String targetDirectory) throws Exception {
        // 解决url中可能有中文情况
        System.out.println("url:"+ urlPath);
        URL url = new URL(urlPath);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setConnectTimeout(3000);
        // 设置 User-Agent 避免被拦截
        http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");

        String contentType = http.getContentType();
        System.out.println("contentType: "+ contentType);
        // 获取文件大小
        long length = http.getContentLengthLong();
        System.out.println("文件大小："+(length / 1024)+"KB");
        // 获取文件名
        String fileName = getFileName(http , urlPath);
        InputStream inputStream = http.getInputStream();
        byte[] buff = new byte[1024*10];
        OutputStream out = new FileOutputStream(new File(targetDirectory,fileName));
        int len ;
        int count = 0; // 计数
        while((len = inputStream.read(buff)) != -1) {
            out.write(buff, 0, len);
            out.flush();
            ++count ;
        }
        System.out.println("count:"+ count);
        // 关闭资源
        out.close();
        inputStream.close();
        http.disconnect();
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


    public static String downloadFromUrl(String url,String dir) {

        try {
            URL httpurl = new URL(url);
            String fileName = getFileNameFromUrl(url);
            System.out.println(fileName);
            File f = new File(dir + fileName);
            FileUtils.copyURLToFile(httpurl, f);
        } catch (Exception e) {
            e.printStackTrace();
            return "Fault!";
        }
        return "Successful!";
    }

    public static String getFileNameFromUrl(String url){
        String name = new Long(System.currentTimeMillis()).toString() + ".X";
        int index = url.lastIndexOf("/");
        if(index > 0){
            name = url.substring(index + 1);
            if(name.trim().length()>0){
                return name;
            }
        }
        return name;
    }


}
