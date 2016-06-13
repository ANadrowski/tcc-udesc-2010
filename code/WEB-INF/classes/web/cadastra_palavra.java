package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*; 

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



public class cadastra_palavra extends HttpServlet
{
	
	private int codigo_palavra;
	private String palavra;
	private String traducao;
	private String pronuncia_escrita;
	private String codigo_classe_gramatical;
	private String codigo_topico_especial;
	


	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		palavra = req.getParameter("palavra");
		traducao = req.getParameter("traducao");
		pronuncia_escrita = req.getParameter("pronuncia_escrita");
		codigo_classe_gramatical = req.getParameter("codigo_classe_gramatical");
		codigo_topico_especial = req.getParameter("codigo_topico_especial");

		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	

			Statement st = con.createStatement();

			java.util.Date date = new java.util.Date();
			String sql = "INSERT INTO palavra (palavra, traducao, pronuncia_escrita, codigo_topico_especial, codigo_classe_gramatical, codigo_idioma) VALUES ('"+palavra+"', '"+traducao+"', '"+pronuncia_escrita+"', '"+codigo_topico_especial+"', '"+codigo_classe_gramatical+"', '"+User.codigo_idioma+"')";

			st.executeUpdate( sql );
			con.close();
			st.close();
			
				res.sendRedirect( "/outroidioma/palavra" );
			}	

    			catch(ClassNotFoundException e) 
			{ 
      				out.println("Couldn't load database driver: " + e.getMessage());
    			}
    			catch(SQLException e) 
			{ 
				out.println("SQLException caught: " + e.getMessage());
    			}
	}
}

