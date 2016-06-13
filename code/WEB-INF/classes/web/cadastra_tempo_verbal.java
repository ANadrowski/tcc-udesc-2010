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



public class cadastra_tempo_verbal extends HttpServlet
{
	

	private String nome_tempo_verbal;

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		
		nome_tempo_verbal = req.getParameter("nome_tempo_verbal");


		if (nome_tempo_verbal != "")
		{

			try 
			{
				Connection con = null;
				Class.forName("org.postgresql.Driver"); 
				con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	

				Statement st = con.createStatement();

				String sql = "INSERT INTO tempoverbal (nome_tempo_verbal, codigo_idioma) VALUES ('"+nome_tempo_verbal+"', '"+User.codigo_idioma+"')";

				st.executeUpdate( sql );
				con.close();
				st.close();
			
				res.sendRedirect( "/outroidioma/tempoverbal" );
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
		else res.sendRedirect( "/outroidioma/tempoverbal" );
	}
}

