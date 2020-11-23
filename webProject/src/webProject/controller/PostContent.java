package webProject.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webProject.model.UserPost;
import webProject.model.dao.Dao;
/**
 * Servlet implementation class PostContent
 */
@WebServlet("/PostContent")
public class PostContent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostContent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("loggedInUser") != null)
		{
			String action = request.getParameter("action");
			if (action != null)
			{
				Dao dao = new Dao();
				if (action.equals("post"))
				{
					ArrayList<UserPost> posts = new ArrayList<UserPost>();
					
					posts = dao.listAllPosts();
					
					// Use id of the latest post in case some posts get deleted and gives a duplicate id
					int postCount = posts.size();
					int id = posts.get(postCount - 1).getId();
					
					String title = request.getParameter("title");
					String content = request.getParameter("content");
					String user = (String) session.getAttribute("loggedInUser");
					
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
					LocalDateTime now = LocalDateTime.now();
					String date = dtf.format(now);
					
					dao.addPost(id + 1, user, title, content, date);
					
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				else if (action.equals("delete"))
				{
					// https://jaxenter.com/convert-java-string-int-134101.html
					int id = Integer.parseInt(request.getParameter("id"));
					String user = dao.getPostUser(id);
					// Allow post deletion if username is admin or current user posted it
					if (session.getAttribute("loggedInUser").equals("admin") || session.getAttribute("loggedInUser").equals(user))
					{
						dao.deletePost(id);
						response.sendRedirect(request.getContextPath() + "/index.jsp");	
					}
					else {
						System.out.println("You are not admin");
						response.sendRedirect(request.getContextPath() + "/index.jsp");
					}

				}
			}
			
		}
		else
		{
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}