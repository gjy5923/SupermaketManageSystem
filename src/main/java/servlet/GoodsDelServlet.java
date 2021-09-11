package servlet;

import DAO.GoodsInfoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Auther: GU
 * @Date: 2021/5/17 15:51
 * @Description:
 */
@WebServlet( "/GoodsDelServlet")
public class GoodsDelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String []goodsNum = request.getParameterValues("goodsNum") ;
        String []pic = request.getParameterValues("pic");
        String goods = "";
        //ƴ��ѧ���ַ������γ�sql���"delete from stuInfo where sno in (" + sno + ")",��ֵ�γɺ󴫸�sno����
        for (String s : goodsNum) {//����ajax���ݹ�����ѧ������
            goods = goods + s + ",";
        }
        goods = goods.substring(0, goods.length() - 1);//ȥ�����һ����,��
        String result = "false";//����Ĭ�Ϸ���ֵ
        try {
            if (GoodsInfoDAO.delete(goods) > 0) {//ɾ�����ݿ��¼
                //����ɾ���ϴ���ͼ���ļ�
                for (String sfile : pic) {//����ajax��������ͼƬ�ļ�������
                    String filepath = this.getServletContext().getRealPath("/uploadfile/") + sfile;//��ȡͼƬ�ļ�������·��;
                    if (!sfile.equals("nopic.jpg")) {
                        new File(filepath).delete();//�������ͷ��δ�ϴ�����ɾ���ļ�
                    }
                }
                result = "ok";//���÷���ֵ��ajax����showStu.jsp�е�ɾ����ť�¼�
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        response.getWriter().print(result);
    }
}
