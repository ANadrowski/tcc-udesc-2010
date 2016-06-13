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


public class ativa_idioma extends HttpServlet
{
	
	private String codigo_idioma;
	private String login_antigo;
	private int codigo_idioma_antigo;
	private int codigo_usuario_antigo;

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		codigo_idioma = req.getParameter("codigo_idioma");
		int codigo_idioma_int = Integer.parseInt(codigo_idioma);   

		login_antigo = User.login_usuario;
		codigo_usuario_antigo = User.codigo_usuario;

		sessao.invalidate();


		sessao = req.getSession( true );
		infousuario user = new infousuario(login_antigo, codigo_idioma_int, codigo_usuario_antigo);
		sessao.setAttribute("infousuario", user);
			
		res.sendRedirect( "/outroidioma/configuracoes" );
		

	}

}

