package com.neuedu.shop.servlet;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.shop.mapper.ICategoryMapper;
import com.neuedu.shop.entity.Category;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.util.MybatisUtil;
import com.neuedu.shop.util.ServletUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("*.category")
public class CategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    List<Category> list = new ArrayList<>();

    //递归 取出 categoryList中的category
    public void recursionAddCategory(Category category){
        //如果传过来的category中没有子类别就跳出循环
        if (category.getCategoryList().size() == 0){
            return;
        }
        for (int i = 0; i < category.getCategoryList().size();i++){
            //添加的是传来category的下一层子类别
            list.add(category.getCategoryList().get(i));
            //再将下一层子类别递归
            recursionAddCategory(category.getCategoryList().get(i));
        }
    }
    public void addCategory(int pid , String name ,String descr ){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        ICategoryMapper cmapper = sqlSession.getMapper(ICategoryMapper.class);
        try {
            //先找到父类别，如果没有父类别   那么添加的当前类别为根类别
            Category category = cmapper.findFather(pid);
            if (category == null){
                cmapper.add(name,descr,pid, 1);
            }else {
                //如果有父类别，那么grade 就在父类别的基础上加1
                cmapper.add(name,descr,pid,category.getGrade() + 1);
                System.out.println(category);
            }
            //将父类别的leaf 设为false
            cmapper.updateLeafTo0(pid);
            sqlSession.commit();

        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }
    Set<Integer> set = new HashSet<>();

    //这个递归和查询的时候递归一样
    public void deleteId(Category category ){
        for (int i = 0; i < category.getCategoryList().size();i++){
            set.add(category.getCategoryList().get(i).getId());
            //如果集合中没有category时跳出
            if (category.getCategoryList().get(i) == null){
                return;
            }
            deleteId(category.getCategoryList().get(i));
        }
        set.add(category.getId());
    }
    public void  deleteCategory(int id){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        ICategoryMapper mapper = sqlSession.getMapper(ICategoryMapper.class);
        try {
            //通过传来的ID找到当前的category
            Category category = mapper.findForDelete(id);
            //找到当前category下的所有子类别的id
            deleteId(category );
            //删除
            mapper.delete(set);
            //如果当前category的父亲还有字节点 那么不执行
            if (mapper.findCountByPid(category.getPid()) <= 0) {
                mapper.updateLeafTo1(category.getPid());
            }
            sqlSession.commit();
        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }

    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = ServletUtil.getPath(request, response);

        SqlSession sqlSession = MybatisUtil.getSqlSession();
        ICategoryMapper cmapper = sqlSession.getMapper(ICategoryMapper.class);
        if (path.equals("/list")) {
            int pageNumber = 1;
            int pageSize = 10;
            int count = cmapper.count();
            String pageNumberS = request.getParameter("pageNumber");
            if ( pageNumberS!= null && !pageNumberS.equals("")){
                pageNumber = Integer.parseInt(pageNumberS);
            }
            Paging<Category> paging = new Paging<>(pageNumber,pageSize,count);
            //分页
            List<Category> categories = cmapper.findToTree(0);
            //递归拿出存在category属性categorylist中的子类别
            for (int j = 0; j < categories.size(); j++){
                list.add(categories.get(j));
                recursionAddCategory(categories.get(j));
            }
            if (count % pageSize == 0){
                list = list.subList(paging.getStartIndex(), pageSize * pageNumber);
            } else{
                if (paging.getPageNumber() >= paging.getEndPage()){
                    list = list.subList(paging.getStartIndex(), paging.getCount());
                } else {
                    list = list.subList(paging.getStartIndex(), paging.getPageSize() * paging.getPageNumber());
                }
            }
            paging.setObjects(list);
            String pname = "";
            // 创建一个map 存储 pid 对应的 pname
            Map<Integer, String> maps = new HashMap<>();
            for (int i = 0; i < paging.getObjects().size(); i++) {
                if (paging.getObjects().get(i).getPid() != 0) {
                    pname = cmapper.findById(paging.getObjects().get(i).getPid()).getName();
                    maps.put(paging.getObjects().get(i).getPid(), pname);
                }
            }
            // ServletUtil.forwardPage("categories", categories, "category/categories.jsp",
            // request, response);
            request.setAttribute("paging",paging);
            request.setAttribute("maps", maps);
            request.getRequestDispatcher("category/categories.jsp").forward(request, response);
            //因为此处list为成员变量，每次调用完方法后都要给它进行重新初始化
            list = new ArrayList<>();
        } else if (path.equals("/add_root")) {
            // 页面跳转
            request.getRequestDispatcher("category/add_category_root.jsp").forward(request, response);
        } else if (path.equals("/addroot")) {
            String name = request.getParameter("name");
            String descr = request.getParameter("descr");

            addCategory(0,name,descr);

            response.sendRedirect("list.category");
        } else if (path.equals("/add_child")) {
            // 携带pid 跳转到添加子类别的页面
            int id = Integer.parseInt(request.getParameter("pid"));
            Category category = cmapper.findById(id);
            ServletUtil.forwardPage("category", category, "category/add_category_child.jsp", request, response);
        } else if (path.equals("/addchild")) {
            String name = request.getParameter("name");
            String descr = request.getParameter("descr");
            int id = Integer.parseInt(request.getParameter("id"));

            addCategory(id,name,descr);
            response.sendRedirect("list.category");
        } else if (path.equals("/delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            int pid = Integer.parseInt(request.getParameter("pid"));
            deleteCategory(id);

            response.sendRedirect("list.category");
            set = new HashSet<>();
        } else if (path.equals("/load")) {
            int id = Integer.parseInt(request.getParameter("id"));

            Category category = cmapper.findById(id);
            ServletUtil.forwardPage("category", category, "category/update_category.jsp", request, response);
        } else if (path.equals("/update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String descr = request.getParameter("descr");
            Category category = cmapper.findById(id);
            category.setName(name);
            category.setDescr(descr);

            cmapper.update(category);
            sqlSession.commit();
            sqlSession.close();

            response.sendRedirect("list.category");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }


}
