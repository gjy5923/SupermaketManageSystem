package servlet;

import DAO.GoodsInfoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Auther: GU
 * @Date: 2021/5/17 15:51
 * @Description:
 */
@WebServlet( "/GoodsDelServlet")
public class GoodsDelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String []goodsNum = request.getParameterValues("goodsNum") ;
        String []pic = request.getParameterValues("pic");
        String goods = "";
        //拼接学号字符串，形成sql语句"delete from stuInfo where sno in (" + sno + ")",其值形成后传给sno变量
        for (String s : goodsNum) {//遍历ajax传递过来的学号数组
            goods = goods + s + ",";
        }
        goods = goods.substring(0, goods.length() - 1);//去除最后一个“,”
        String result = "false";//设置默认返回值
        try {
            if (GoodsInfoDAO.delete(goods) > 0) {//删除数据库记录
                //遍历删除上传的图像文件
                for (String sfile : pic) {//遍历ajax传过来的图片文件名数组
                    String filepath = this.getServletContext().getRealPath("/uploadfile/") + sfile;//获取图片文件的物理路径;
                    if (!sfile.equals("nopic.jpg")) {
                        new File(filepath).delete();//如果不是头像未上传，则删除文件
                    }
                }
                result = "ok";//设置返回值到ajax调用showStu.jsp中的删除按钮事件
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        response.getWriter().print(result);
    }
}
