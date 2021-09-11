package test2;

import DAO.ImageDAO;
import bean.Safetymonitoring;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/FindImageServlet")
public class FindImageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //得到jsp中所键入的值
        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        String key="";
        List<Safetymonitoring> list= null;//查询所有信息
        if((request.getParameter("startdate")==null&&request.getParameter("enddate")==null)||startdate.length()==0&&enddate.length() == 0){
            key = "1=1";//如果没有键入任何值，则执行查找所有信息操作
        }else if(startdate.length()==0){
            key = "time<'" + enddate + "'";//当起始时间为空时，执行小于结束时间操作
        } else if (enddate.length() == 0) {
            key = "time>'" + startdate + "'";//当结束时间为空时，执行大于开始时间操作
        }else {
            key = "time>'" + startdate + "' and time<'" + enddate + "'";//都不为空时，执行在区间中查询的操作
        }
        try {
            if (key.equals("1=1")) {
                list = ImageDAO.imageList();
            } else {
                list = ImageDAO.findQuerry(key);//执行有命令的数据库查找操作
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (list!=null){
            new Safetymonitoring().setTemp(list);
            response.getWriter().print("ok");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
