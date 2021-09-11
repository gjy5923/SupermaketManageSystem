package servlet;

import DAO.UserInfoDAO;
import bean.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");//设置响应的编码
        ServletContext application = request.getServletContext();
        User user = (User) application.getAttribute("user");//得到登录的账号
        PrintWriter out = response.getWriter();//获取响应字节打印流
        String old=request.getParameter("old_password");
        String new_password = request.getParameter("new_password");
        try {
            if (user.getPassword().equals(old)) {//原始密码与数据库中的密码比较
                user.setPassword(new_password);
                UserInfoDAO.updateUsersInfo(user);

                out.print("ok");
            }else {
                out.print("error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
