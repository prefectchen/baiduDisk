package BigFileSplitAndCombination;

import java.io.*;

public class SplitBigFile {
    public static void main(String[] args) {
        String srcName="D:\\test\\3.flv";
        String destName="D:\\test\\";
        int size=40; //每个文件的大小 以M为单位
        System.out.println("开始分割文件");
        split(srcName, size, destName);
        System.out.println("文件分割完成");
    }
    private static void split(String src,int mb,String dest) {
        // TODO 自动生成的方法存根
        File srcFile=new File(src);
        if(!srcFile.exists())
        {
            return;
        }
        long countSize=srcFile.length();
        long fileSize=1024*1024*mb;
        int num=0;
        if(countSize%fileSize==0)
        {
            num=(int) (countSize/fileSize);
        }
        else
        {
            num=(int) (countSize/fileSize)+1;
        }
        InputStream in=null;
        try {
            in = new FileInputStream(srcFile);
            BufferedInputStream bis=new BufferedInputStream(in);
            BufferedOutputStream bos=null;
            byte bytes[]=new byte[1024*1024];
            int len=-1;
            for (int i = 0; i < num; i++) {
                String newFile=dest+File.separator+srcFile.getName()+"-"+i;
                bos=new BufferedOutputStream(new FileOutputStream(newFile));
                int count=0;
                while ((len=bis.read(bytes))!=-1) {
                    bos.write(bytes,0,len);
                    bos.flush();
                    count+=len;
                    if(count>=fileSize)
                    {
                        break;
                    }
                }
                bos.close();
            }
            bis.close();
            in.close();
        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

    }

}
