package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class deleta_classe_gramatical extends HttpServlet
{
	
	private String codigo_classe_gramatical;	

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		java.io.PrintWriter out = res.getWriter();

		codigo_classe_gramatical = req.getParameter("codigo_classe_gramatical");
		int codigo_classe_gramatical_int = Integer.parseInt(codigo_classe_gramatical);   

		out.println(codigo_classe_gramatical);
		out.println(codigo_classe_gramatical_int);
	

		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			String sql = "DELETE FROM classegramatical WHERE codigo_classe_gramatical="+codigo_classe_gramatical_int;
			Statement st = con.createStatement();
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

}

