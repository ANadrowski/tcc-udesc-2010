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



public class cadastra_classe_gramatical extends HttpServlet
{
	

	private String nome_classe_gramatical;

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		
		nome_classe_gramatical = req.getParameter("nome_classe_gramatical");

		if (nome_classe_gramatical != "")
		{

			try 
			{
				Connection con = null;
				Class.forName("org.postgresql.Driver"); 
				con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	

				Statement st = con.createStatement();

				String sql = "INSERT INTO classegramatical (nome_classe_gramatical, codigo_idioma) VALUES ('"+nome_classe_gramatical+"', '"+User.codigo_idioma+"')";

				st.executeUpdate( sql );
				con.close();
				st.close();
			
				res.sendRedirect( "/outroidioma/classegramatical" );
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
		else res.sendRedirect( "/outroidioma/classegramatical" );

	}
}

