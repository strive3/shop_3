package com.neuedu.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.User;
import com.neuedu.shop.mapper.IUserMapper;
import com.neuedu.shop.util.MybatisUtil;
import com.neuedu.shop.util.ServletUtil;
import org.apache.ibatis.session.SqlSession;

@WebServlet("*.user")
public class UserServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = -6911428611473388368L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = ServletUtil.getPath(request, response);
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        if ("/userlist".equals(path)) {
            //每页显示的条数
            int pagesize = 10;
            int pageNumber = 1;
            if (request.getParameter("pageNumber") != null && !request.getParameter("pageNumber").equals("")) {
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            }
            Paging<User> paging = new Paging<>(pageNumber,pagesize,mapper.count());
            List<User> users = mapper.findByPage(paging.getStartIndex(), pagesize);
System.out.println(users);
            paging.setObjects(users);

            request.setAttribute("paging", paging);
            request.getRequestDispatcher("user/users.jsp").forward(request, response);
        } else if ("/add_user".equals(path)) {
            request.getRequestDispatcher("user/add_user.jsp").forward(request, response);
        } else if ("/add".equals(path)) {
                String username = request.getParameter("username");
                PrintWriter out = response.getWriter();
                if (username == null || username.equals("")){
                    out.print("请输入用户名");
                return;
            }
            User user = mapper.findByName(username);
            String pwd = request.getParameter("pwd");
            String phone = request.getParameter("phone");
            String addr = request.getParameter("addr");
            if (user != null){
                out.print("用户名已存在");
            }else {
                out.print("用户名可以使用");
                if (pwd != null && !pwd.equals("")){
                    mapper.add(new User(username, pwd, phone, addr));
                    sqlSession.commit();
                    sqlSession.close();
                    response.sendRedirect("userlist.user");
                }
            }
        } else if ("/load".equals(path)) {
            int id = Integer.parseInt(request.getParameter("id"));

            User user = mapper.findById(id);

            request.setAttribute("user", user);
            request.getRequestDispatcher("user/update_user.jsp").forward(request, response);
        } else if ("/update".equals(path)) {
            int id = Integer.parseInt(request.getParameter("id"));

            User user = mapper.findById(id);
            user.setPassword(request.getParameter("newpwd"));
            user.setPhone(request.getParameter("phone"));
            user.setAddr(request.getParameter("addr"));
            mapper.update(user);
            sqlSession.commit();
            sqlSession.close();
            response.sendRedirect("userlist.user");
        } else if ("/delete".equals(path)) {
            int id = Integer.parseInt(request.getParameter("id"));

            mapper.delete(id);
            sqlSession.commit();
            sqlSession.close();
            response.sendRedirect("userlist.user");
        }
    }
}
