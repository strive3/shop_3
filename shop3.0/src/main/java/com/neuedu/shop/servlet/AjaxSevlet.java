package com.neuedu.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.shop.entity.Admin;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.User;
import com.neuedu.shop.mapper.IAdminMapper;
import com.neuedu.shop.util.MybatisUtil;
import com.neuedu.shop.util.ServletUtil;
import net.sf.json.JSONArray;
import org.apache.ibatis.session.SqlSession;

/**
 * Servlet implementation class AjaxSevlet
 */
@WebServlet("*.ajax")
public class AjaxSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletUtil.getPath(request, response);
		if("/register".equals(path)) {
			
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");

			SqlSession sqlSession = MybatisUtil.getSqlSession();
			IAdminMapper mapper = sqlSession.getMapper(IAdminMapper.class);

			PrintWriter out = response.getWriter();
			Admin admin = mapper.findByName(name);
System.out.println("admin" + admin);
			if (admin == null) {
				out.print("可以注册");
			}else {
				out.print("用户名已存在！");
			}
		}else if ("/list".equals(path)) {
			//通过ajax请求获取Userlist
			/*UserMapperImpl udao = new UserMapperImpl();
			Paging<User> users = udao.findByPage(1,10);
			JSONArray jsonarray = JSONArray.fromObject(users.getObjects());
			String jsonusers = jsonarray.toString();
			PrintWriter out = response.getWriter();
			out.print(jsonusers);*/
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
