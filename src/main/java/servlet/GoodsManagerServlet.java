package servlet;

import DAO.GoodsInfoDAO;
import bean.Goodsmessage;
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


@WebServlet("/GoodsManagerServlet")
public class GoodsManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String data = "";
        String key = null;
        if (request.getParameter("goodsNum") ==null && request.getParameter("name") ==null ) {
            key = "1=1";
        }else {
            key = "name like '%" + request.getParameter("name") + "%' and goodsNum like '%" + request.getParameter("goodsNum") + "%'";
        }

        try {
            List<Goodsmessage> goods = GoodsInfoDAO.findGoodsNum(key);
            data = JSON.toJSONString(goods);
            int count = goods.size();
            data = "{\"code\": 0,\"msg\": \"\",\"count\": "+count+",\"data\":"+data+"}";//字符串拼接成Layui的table组件能识别的json字符串
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print(data);
    }
}
