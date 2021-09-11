package servlet;

import DAO.TypesInfoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Auther: GU
 * @Date: 2021/6/3 15:03
 * @Description:
 */
@WebServlet("/TypeDelServlet")
public class TypeDelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String []typesNum = request.getParameterValues("typeNum") ;
        String types = "";
        //拼接学号字符串，形成sql语句"delete from stuInfo where sno in (" + sno + ")",其值形成后传给sno变量
        for (String s : typesNum) {//遍历ajax传递过来的学号数组
            types = types + s + ",";
        }
        types = types.substring(0, types.length() - 1);//去除最后一个“,”
        String result = "false";//设置默认返回值
        try {
            if (TypesInfoDAO.delete(types) > 0) {
                result = "ok";//设置返回值到ajax调用showStu.jsp中的删除按钮事件
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.getWriter().print(result);
    }
}
