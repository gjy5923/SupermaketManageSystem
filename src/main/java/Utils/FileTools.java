package Utils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class FileTools {
    /**
     * �ļ��ϴ�
     * @param part Servlet3.0 ��enctype="multipart/form-data"��POST�������װ�� Part��ͨ�� Part ���ļ������ϴ���
     * @param filepath �ļ��ϴ���·��
     * @throws IOException
     * @return �����ϴ����ļ���
     */
    public static String fileUpload(Part part,String filepath) throws IOException {
        String filename=part.getSubmittedFileName();//��ȡ�ϴ��ļ���
        String newFilename = System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));//�ϴ��ļ���ʱ���������
        File file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        part.write(filepath+"/"+newFilename);//�ϴ��ļ�
        return newFilename;
    }
}
