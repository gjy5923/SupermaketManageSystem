package servlet;


import DAO.GoodsInfoDAO;
import DAO.TypesInfoDAO;
import bean.Goodsmessage;
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


@WebServlet("/AddGoodsServlet")
public class AddGoodsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");//��������ı���
        response.setContentType("text/html;charset=utf-8");//������Ӧ�ı���
        Connection conn = JDBCUtils.getConnection();
        PrintWriter out = response.getWriter();//��ȡ��Ӧ�ֽڴ�ӡ��
        Goodsmessage goods = new Goodsmessage();
        goods.setGoodsNum(request.getParameter("goodsNum"));
        goods.setName(request.getParameter("name"));
        try {
            String type=TypesInfoDAO.checkTypeNum(request.getParameter("type"));
            goods.setType(type);//�޸Ķ�����ӵ�ֵ
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        goods.setPurchasePrice(request.getParameter("purchasePrice"));
        goods.setSalePrice(request.getParameter("salePrice"));
        goods.setDate(request.getParameter("date"));
        goods.setPeriod(request.getParameter("period"));
        goods.setPhoto(request.getParameter("filesrc"));//��ȡ�ϴ���ͼƬ�ļ���
        try {
            JSONObject res = new JSONObject();//ʹ�ð����FastJsonʵ����json����
            res.put("code", 0);//������ֵ�ԣ����ü�code��ֵΪ0
            if (GoodsInfoDAO.addGoodsInfo(goods,conn) > 0) {//ʹ�÷������ѧ����Ϣ����ӳɹ������ύҳȥ����
                res.put("code", 1);//�޸ļ�ֵ�ԣ����ü�code��ֵΪ0
            }
            out.print(res.toJSONString());//���json�ַ��������ظ�addStu2.jsp
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
