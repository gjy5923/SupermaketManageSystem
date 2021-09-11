package servlet;

import DAO.LoginDAO;
import Utils.RandomValue;
import bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String power = request.getParameter("user");//权限
        String temp= RandomValue.getuserNum(power);//随机获取一个职工号
            try {
                if (LoginDAO.findUser(username)) {//判断用户名是否存在
                    response.getWriter().print("error");
                } else {
                   do{
                        if (LoginDAO.findEno(temp)) {//判断分配的职工号是否存在
                            temp = RandomValue.getuserNum(power);
                        } else {
                            User user = new User(temp,username,password,power);
                            if (LoginDAO.addUser(user)!=0) {
                                response.getWriter().print(temp);//注册成功是把分配的职工号当作结果返回
                                return;
                            }
                        }
                    } while(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
