package servlet;

import bean.Type;
import DAO.TypesInfoDAO;
import Utils.JDBCUtils;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Auther: GU
 * @Date: 2021/6/3 14:20
 * @Description:
 */
@WebServlet("/AddTypeServlet")
public class AddTypeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");//设置请求的编码
        response.setContentType("text/html;charset=utf-8");//设置响应的编码
        Connection conn = JDBCUtils.getConnection();
        PrintWriter out = response.getWriter();//获取响应字节打印流
        Type type = new Type();
        type.setTypeNum(request.getParameter("typeNum"));
        type.setType(request.getParameter("type"));
        try {
            JSONObject res = new JSONObject();//使用阿里的FastJson实例化json对象
            res.put("code", 0);//创建键值对，设置键code的值为0
            if (TypesInfoDAO.addTypesInfo(type,conn) > 0) {//使用方法添加学生信息，添加成功返回提交页去处理
                res.put("code", 1);//修改键值对，设置键code的值为0
            }
            out.print(res.toJSONString());//输出json字符串，返回给addStu2.jsp
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
