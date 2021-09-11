package servlet;

import DAO.UserInfoDAO;
import bean.User;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/UserManagerServlet")
public class UserManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String data = "";
        String key = null;
        if (request.getParameter("eno") ==null && request.getParameter("username") ==null ) {
            key = "1=1";
        }else {
            key = "username like '%" + request.getParameter("username") + "%' and eno like '%" + request.getParameter("eno") + "%'";
        }

        try {
            List<User> users = UserInfoDAO.findEno(key);
            data = JSON.toJSONString(users);
            int count = users.size();
            data = "{\"code\": 0,\"msg\": \"\",\"count\": "+count+",\"data\":"+data+"}";//字符串拼接成Layui的table组件能识别的json字符串
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print(data);
    }
}
