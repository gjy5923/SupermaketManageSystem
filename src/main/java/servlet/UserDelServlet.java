package servlet;

import DAO.UserInfoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UserDelServlet")
public class UserDelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String []userNum = request.getParameterValues("eno") ;

        String user = "";
        //拼接学号字符串，形成sql语句"delete from stuInfo where sno in (" + sno + ")",其值形成后传给sno变量
        for (String s : userNum) {//遍历ajax传递过来的学号数组
            user = user + s + ",";
        }

        user = user.substring(0, user.length() - 1);//去除最后一个“,”
        String result = "false";//设置默认返回值

        try {
            if (UserInfoDAO.delete(user) > 0) {//删除数据库记录

                result = "ok";//设置返回值到ajax调用userManage.jsp中的删除按钮事件
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        response.getWriter().print(result);
    }
}
