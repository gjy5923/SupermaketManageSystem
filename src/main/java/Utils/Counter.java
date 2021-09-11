package Utils;

import java.io.*;
/**
 * 创建文本文件记录浏览次数
 */
public class Counter {
    public Counter() {
        super();
    }

    //写入文件的方法
    public static void write2File(String filename, long count){
        try{
            PrintWriter out = new PrintWriter(new FileWriter(filename));
            out.println(count);
            out.close();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    //读文件的方法
    public static long readFromFile(String filename){
        File file = new File(filename);
        long count = 0;
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            write2File(filename, 0);
        }
        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            try{
                count = Long.parseLong(in.readLine());
            }
            catch (NumberFormatException e) {
                // TODO: handle exception
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return count;
    }
}
