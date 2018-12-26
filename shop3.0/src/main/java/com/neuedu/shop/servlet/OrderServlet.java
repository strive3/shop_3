package com.neuedu.shop.servlet;

import com.neuedu.shop.mapper.IOrderDAO;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.Salesorder;
import com.neuedu.shop.util.MybatisUtil;
import com.neuedu.shop.util.ServletUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 杜晓鹏
 * @create 2018-11-29 19:56
 */
@WebServlet(urlPatterns = "*.order")
public class OrderServlet extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtil.getPath(request, response);
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IOrderDAO omapper = sqlSession.getMapper(IOrderDAO.class);
        //一页多少条数据
        int pagesize = 10;
        //默认为第一也页
        int pageNumber = 1;
        int count = omapper.count();
        if (path.equals("/list")){
            if (request.getParameter("pageNumber") != null && !request.getParameter("pageNumber").equals("")){
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            }
            Paging<Salesorder> paging = new Paging<>(pageNumber,pagesize,count);
            List<Salesorder> salesorders = omapper.findByPage(paging.getStartIndex(), paging.getPageSize());
            paging.setObjects(salesorders);

            request.setAttribute("paging",paging);

            request.getRequestDispatcher("order/orders.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
