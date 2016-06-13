package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class deleta_tempo_verbal extends HttpServlet
{
	
	private String codigo_tempo_verbal;	

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		java.io.PrintWriter out = res.getWriter();

		codigo_tempo_verbal = req.getParameter("codigo_tempo_verbal");
		int codigo_tempo_verbal_int = Integer.parseInt(codigo_tempo_verbal);   


		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			String sql = "DELETE FROM tempoverbal WHERE codigo_tempo_verbal="+codigo_tempo_verbal_int;
			Statement st = con.createStatement();
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

}

