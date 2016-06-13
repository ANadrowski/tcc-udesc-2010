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


public class escreve_regra extends HttpServlet
{
	
	private int codigo_regra;
	private String nome_regra;
	private String descritivo_regra;
	private String exemplo_regra;
	private int codigo_usuario;

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		nome_regra = req.getParameter("nome_regra");
		descritivo_regra = req.getParameter("descritivo_regra");
		exemplo_regra = req.getParameter("exemplo_regra");

		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	


			Statement st = con.createStatement();

			String sql = "INSERT INTO regragramatical (nome_regra, descritivo_regra, exemplo_regra, codigo_idioma) VALUES ('"+nome_regra+"', '"+descritivo_regra+"', '"+exemplo_regra+"', '"+User.codigo_idioma+"')";

			st.executeUpdate( sql );
			con.close();
			st.close();
			
				res.sendRedirect( "/outroidioma/regra" );
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

