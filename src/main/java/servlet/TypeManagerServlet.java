package servlet;

import bean.Type;
import DAO.TypesInfoDAO;
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

/**
 * @Auther: GU
 * @Date: 2021/6/3 13:44
 * @Description:
 */
@WebServlet("/TypeManagerServlet")
public class TypeManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String data = "";
        String key = null;
        if (request.getParameter("typeNum") ==null && request.getParameter("type") ==null ) {
            key = "1=1";
        }else {
            key = "type like '%" + request.getParameter("type") + "%' and typeNum like '%" + request.getParameter("typeNum") + "%'";
        }

        try {
            List<Type> types = TypesInfoDAO.checkType(key);
            data = JSON.toJSONString(types);
            int count = types.size();
            data = "{\"code\": 0,\"msg\": \"\",\"count\": "+count+",\"data\":"+data+"}";//字符串拼接成Layui的table组件能识别的json字符串
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print(data);
    }
}
