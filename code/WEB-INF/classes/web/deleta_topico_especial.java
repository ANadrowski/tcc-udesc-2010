package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class deleta_topico_especial extends HttpServlet
{
	
	private String codigo_topico_especial;	

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		java.io.PrintWriter out = res.getWriter();

		codigo_topico_especial = req.getParameter("codigo_topico_especial");
		int codigo_topico_especial_int = Integer.parseInt(codigo_topico_especial);   


		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			String sql = "DELETE FROM topicoespecial WHERE codigo_topico_especial="+codigo_topico_especial_int;
			Statement st = con.createStatement();
			st.executeUpdate( sql );
			con.close();
			st.close();
			
				res.sendRedirect( "/outroidioma/topicoespecial" );
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

