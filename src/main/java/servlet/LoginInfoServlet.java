package servlet;

import DAO.LoginDAO;
import Utils.CookieEncryptTool;
import bean.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/LoginInfoServlet")
public class LoginInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        User user = new User(username,username,password);
        User userResult = null;
        //ʹ��application��ס��¼�ɹ����û���Ϣ
        ServletContext application = request.getServletContext();
        application.setAttribute("username",username);

        try {
            userResult = LoginDAO.findByKey(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (userResult != null) {
            HttpSession session = request.getSession();
            session.setAttribute("eno",userResult.getEno());
            //�õ���¼�ɹ�֮���ʱ��
            SimpleDateFormat dateformat = new SimpleDateFormat("M/dd H:mm:ss");
            String date = dateformat.format(new Date());
            application.setAttribute("date",date);
            rememberMe(rememberMe, username, password, request, response);
            response.getWriter().print("ok");
        } else {
            response.getWriter().print("error");
        }

    }
/*
 * ��ס����
 */
    private void rememberMe(String rememberMe, String username, String password,HttpServletRequest request, HttpServletResponse response) {
        // �ж��Ƿ���Ҫͨ��Cookie��ס���������
        if ("true".equals(rememberMe)) {
            // ��ס���估����
            Cookie cookie = new Cookie("COOKIE_USERNAME",
                    CookieEncryptTool.encodeBase64(username));
            cookie.setPath("/");
            cookie.setMaxAge(365 * 24 * 3600);
            response.addCookie(cookie);

            cookie = new Cookie("COOKIE_PASSWORD",
                    CookieEncryptTool.encodeBase64(password));
            cookie.setPath("/");
            cookie.setMaxAge(365 * 24 *
                    3600);
            response.addCookie(cookie);
        } else {
            // �����估����Cookie���
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("COOKIE_USERNAME".equals(cookie.getName())
                            || "COOKIE_PASSWORD".equals(cookie.getName())) {
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
            }
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
