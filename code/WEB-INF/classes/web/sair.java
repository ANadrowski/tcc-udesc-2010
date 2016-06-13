package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class sair extends HttpServlet
{
	public void service( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		String login_usuario = (String) sessao.getAttribute("login_usuario");
		java.io.PrintWriter out = res.getWriter();
		sessao.invalidate();
		res.sendRedirect( "/outroidioma/index.jsp" );	
	}
}

