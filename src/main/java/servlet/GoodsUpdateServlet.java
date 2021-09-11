package servlet;

import DAO.GoodsInfoDAO;
import DAO.TypesInfoDAO;
import bean.Goodsmessage;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/GoodsUpdateServlet")
public class GoodsUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");//设置请求的编码
        response.setContentType("text/html;charset=utf-8");//设置响应的编码
        PrintWriter out = response.getWriter();//获取响应字节打印流
        Goodsmessage goods = new Goodsmessage();//实例化学生信息实体类
        goods.setGoodsNum(request.getParameter("goodsNum"));//设置学号为学号输入框的值
        goods.setName(request.getParameter("name"));
        String type = null;
        try {
            type = TypesInfoDAO.checkTypeNum(request.getParameter("type"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        goods.setType(type);
        goods.setPurchasePrice(request.getParameter("purchasePrice"));
        goods.setSalePrice(request.getParameter("salePrice"));
        goods.setDate(request.getParameter("date"));
        goods.setPeriod(request.getParameter("period"));
        goods.setPhoto(request.getParameter("filesrc"));//获取上传的图片文件名
        try {
            JSONObject res = new JSONObject();//使用阿里的FastJson实例化json对象
            res.put("code", 0);//创建键值对，设置键code的值为0
            if (GoodsInfoDAO.updateGoodsInfo(goods) > 0) {//使用方法添加学生信息，添加成功返回提交页去处理
                res.put("code", 1);//修改键值对，设置键code的值为0
            }
            out.print(res.toJSONString());//输出json字符串，返回给addStu2.jsp
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
