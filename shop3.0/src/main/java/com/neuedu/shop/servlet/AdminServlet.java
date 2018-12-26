package com.neuedu.shop.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.neuedu.shop.mapper.IAdminMapper;
import com.neuedu.shop.entity.Admin;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.util.MybatisUtil;
import com.neuedu.shop.util.ServletUtil;
import com.neuedu.shop.util.VerifyCodeUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * Admin实体类中的所有crud 使用注解完成配置@WebServlet 使用通配符完成多Servlet处理
 *
 * @author 杜晓鹏
 */
@WebServlet("*.admin")
public class AdminServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = -2550332412219581465L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = ServletUtil.getPath(request, response);

        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IAdminMapper mapper = sqlSession.getMapper(IAdminMapper.class);
        if (path.equals("/adminlist")) {
            int pageNumber = 1;
            int pagesize = 10;
            if (request.getParameter("pageNumber") != null && !request.getParameter("pageNumber").equals("")){
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            }
            Paging<Admin> paging = new Paging<>(pageNumber, pagesize, mapper.count());
            List<Admin> admins = mapper.findByPage(paging.getStartIndex(),pagesize);
            // 将遍历的结果交给对应的jsp页面来处理
            // 1.讲对象绑定到request对象当中
            paging.setObjects(admins);
            request.setAttribute("paging",paging);
            // 2.获取一个jsp的转发器（将要处理的页面交给谁来显示）
            RequestDispatcher rd = request.getRequestDispatcher("admin/admins.jsp");
            // 3.转发（将处理结果交给页面显示）
            rd.forward(request, response);
        } else if (path.equals("/add")) {
            // 从前台获取表单域的内容
            String aname = request.getParameter("aname");
            String apwd = request.getParameter("apwd");
            mapper.add(new Admin(aname, apwd));
            sqlSession.commit();
            sqlSession.close();
            // 页面跳转、重定向
            response.sendRedirect("adminlist.admin");
        } else if (path.equals("/load")) {
            // 从前台获取超链接中的数据 超链接中的？相当于 name ？id 相当于 name="id"
            int id = Integer.parseInt(request.getParameter("id"));
            Admin admin = mapper.findById(id);

            request.setAttribute("admin", admin);
            request.getRequestDispatcher("admin/update_admin.jsp").forward(request, response);
        } else if (path.equals("/update")) {
            // 从前台获取到新密码
            String newpwd = request.getParameter("newpwd");

            int id = Integer.parseInt(request.getParameter("id"));
            Admin admin = mapper.findById(id);
            admin.setApwd(newpwd);

            mapper.update(admin);
            sqlSession.commit();
            sqlSession.close();
            // 重定向到list頁面
            response.sendRedirect("adminlist.admin");
        } else if (path.equals("/delete")) {
            int id = Integer.parseInt(request.getParameter("id"));

            mapper.delete(id);
            sqlSession.commit();
            sqlSession.close();
            response.sendRedirect("adminlist.admin");
        } else if (path.equals("/login")) {
            //管理员登陆
            String name = request.getParameter("name");
            String pwd = request.getParameter("pwd");
            String code = request.getParameter("code");
            HttpSession session = request.getSession();
            String vercode = (String) session.getAttribute("verCode");
            Admin admin = mapper.findByName(name);
            //判断 用户名 和密码 能不能登陆
            if (code.toLowerCase().equals(vercode)) {
                if (admin != null && admin.getApwd().equals(ServletUtil.getMD5(pwd))) {
                    //如果登陆成功 将admin绑定到session中
                    //目的是让非登陆的管理员不能通过url地址直接访问到其他页面
                    session.setAttribute("admin", admin);
                    //登陆成功
                    response.sendRedirect("index.jsp");
                } else {
                    ServletUtil.forwardPage("error_msg", "用户名或密码错误", "login.jsp", request, response);
                }

            } else {
                ServletUtil.forwardPage("error_msg_code", "验证码错误", "login.jsp", request, response);
            }

        } else if (path.equals("/code")) {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

System.out.println("生成的验证码" + verifyCode);
            //存入会话session
            HttpSession session = request.getSession(true);
            //删除以前的
            session.removeAttribute("verCode");
            session.setAttribute("verCode", verifyCode.toLowerCase());
System.out.println("sessionID: " + session.getId());
            //生成图片
            int w = 100, h = 30;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } else if (path.equals("/logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("login.jsp");
        }
    }
}
