package Utils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class FileTools {
    /**
     * 文件上传
     * @param part Servlet3.0 将enctype="multipart/form-data"的POST，请求封装成 Part，通过 Part 对文件进行上传。
     * @param filepath 文件上传的路径
     * @throws IOException
     * @return 返回上传的文件名
     */
    public static String fileUpload(Part part,String filepath) throws IOException {
        String filename=part.getSubmittedFileName();//获取上传文件名
        String newFilename = System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));//上传文件按时间戳新命名
        File file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        part.write(filepath+"/"+newFilename);//上传文件
        return newFilename;
    }
}
