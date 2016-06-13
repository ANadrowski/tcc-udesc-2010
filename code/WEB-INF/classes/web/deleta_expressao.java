package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class deleta_expressao extends HttpServlet
{
	
	private String codigo_expressao_idiomatica;
	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		String login_usuario = (String) sessao.getAttribute("login_usuario");
		java.io.PrintWriter out = res.getWriter();

		codigo_expressao_idiomatica = req.getParameter("codigo_expressao_idiomatica");
		int codigo_expressao_idiomatica_int = Integer.parseInt(codigo_expressao_idiomatica);   


		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			String sql = "DELETE FROM expressaoidiomatica WHERE codigo_expressao_idiomatica="+codigo_expressao_idiomatica_int;
			Statement st = con.createStatement();
			st.executeUpdate( sql );
			con.close();
			st.close();
			
				res.sendRedirect( "/outroidioma/expressao" );
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

