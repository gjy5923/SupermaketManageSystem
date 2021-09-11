package servlet;

import DAO.UserInfoDAO;
import bean.User;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("111");
        request.setCharacterEncoding("utf-8");//设置请求的编码
        response.setContentType("text/html;charset=utf-8");//设置响应的编码
        PrintWriter out = response.getWriter();//获取响应字节打印流
        User user = new User();//实例化用户信息实体类
        user.setEno(request.getParameter("eno"));
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setPosition(request.getParameter("position"));
        try {
            JSONObject res = new JSONObject();//使用阿里的FastJson实例化json对象
            res.put("code", 0);//创建键值对，设置键code的值为0
            if (UserInfoDAO.updateUsersInfo(user) > 0) {//使用方法添加用户信息，添加成功返回提交页去处理
                res.put("code", 1);//修改键值对，设置键code的值为0
            }
            out.print(res.toJSONString());//输出json字符串，返回给editUser.jsp
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
