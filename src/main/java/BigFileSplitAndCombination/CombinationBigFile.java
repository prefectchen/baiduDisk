package BigFileSplitAndCombination;

import java.io.*;

public class CombinationBigFile
{
    public static void main(String[] args) {
        System.out.println("开始合并");
        //每个小文件
        merge("D:\\test\\new",
                new File("D:\\test\\4.mp4-0"),
                new File("D:\\test\\4.mp4-1"),
                new File("D:\\test\\4.mp4-2"),
                new File("D:\\test\\4.mp4-186")
               );
        System.out.println("合并成功");
    }
    private static void merge(String dest,File... files) {
        // TODO 自动生成的方法存根
        String filename=files[0].getName();
        filename=files[0].getName().substring(0,filename.lastIndexOf("-"));
        try {
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(dest+File.separator+filename));
            BufferedInputStream bis=null;
            byte bytes[]=new byte[1024*1024];
            int len=-1;
            for(int i=0;i<files.length;i++)
            {
                bis=new BufferedInputStream(new FileInputStream(files[i]));
                while ((len=bis.read(bytes))!=-1) {
                    bos.write(bytes, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

    }

}
