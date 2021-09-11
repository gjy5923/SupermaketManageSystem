//package filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter(filterName = "Filter", urlPatterns = "/mainPage/*")
//public class Filter implements javax.servlet.Filter {
//    public void destroy() {
//    }
//
//    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        resp.setContentType("text/html;charset=utf-8");
//        HttpServletRequest request = (HttpServletRequest)req;
//        HttpServletResponse response = (HttpServletResponse) resp;
//        String path = request.getServletPath();
//        boolean flag = false;
//        ServletContext application = request.getServletContext();
//        String []name = {"/mainPage/login.jsp","/mainPage/register.jsp"};
//        for(String i:name){
//            if(path.endsWith(i)){
//                flag = true;
//                break;
//            }
//        }
//
//        if(flag){
//            chain.doFilter(req,resp);//放行，不拦截
//      } else{
//            if (application.getAttribute("username") != null) {
//                chain.doFilter(req, resp);//放行，不拦截
//            }else {
//                resp.getWriter().print("<script>");
//                resp.getWriter().print("alert('还未登陆，无权访问！');");
//                resp.getWriter().print("window.location='"+request.getContextPath()+"/mainPage/login.jsp';");
//                resp.getWriter().print("</script>");
//            }
//
//       }
//        return;
//    }
//
//}
