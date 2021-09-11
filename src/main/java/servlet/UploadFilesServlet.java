package servlet;

import Utils.FileTools;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * @Auther: GU
 * @Date: 2021/5/17 13:58
 * @Description:
 */
@MultipartConfig
@WebServlet( "/UploadFilesServlet")
public class UploadFilesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part part = request.getPart("photo");//获取上传文件域
        String fileimg = "";
        if(part.getSubmittedFileName().length()>0) {//如果选择了文件
            String filepath = this.getServletContext().getRealPath("/uploadfile");//获取文件上传位置
            fileimg= FileTools.fileUpload(part, filepath);//上传文件，并返回上传后的文件名
        }
        JSONObject res = new JSONObject();
        JSONObject resurl = new JSONObject();
        resurl.put("src",fileimg);//设置json键值对
        res.put("code", 0);
        res.put("msg","");
        res.put("data", resurl);
        response.getWriter().print(res.toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
