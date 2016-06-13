package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


class infousuario implements java.io.Serializable
{
   	public String login_usuario;
   	public int codigo_idioma;
	public int codigo_usuario;
    
    	public infousuario(String login_usuario, int codigo_idioma, int codigo_usuario)
	{
        	this.login_usuario = login_usuario;
        	this.codigo_idioma = codigo_idioma;
		this.codigo_usuario = codigo_usuario;
         }
}


public class home extends HttpServlet
{
	public void service( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{


		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		if( User.login_usuario != null )
		{

			out.println("<HTML><HEAD>");
			out.println("<FRAMESET ROWS='100, *'  BORDER='0' FRAMESPACING='0' NORESIZE SCROLLING='NO'>");
			out.println("<FRAME SRC='lin1.jsp'>");
			out.println("<FRAMESET COLS='230, *'>");
			out.println("<FRAME SRC='col1.jsp' NORESIZE SCROLLING='NO'> ");
			out.println("<FRAME SRC='configuracoes' NAME='conteudo'>");
			out.println("</FRAMESET> ");
			out.println("</FRAMESET> ");
			out.println("<TITLE>OutroIdioma - Seu organizador de conteúdo online!</TITLE>");
			out.println("</HEAD></HTML>");

		} 
		else
		{
			//usuario nao tem o nome na sessão. Não está logado
			out.println("Desculpe, você não esta logado no sistema!");
		}

	}
}

