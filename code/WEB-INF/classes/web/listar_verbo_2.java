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



public class listar_verbo_2 extends HttpServlet
{
	
	private String codigo_verbo;
	private int codigo_usuario;

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";
/*
	private String p_pes_singular;
	private String s_pes_singular;
	private String t_pes_singular;
	private String p_pes_plural;
	private String s_pes_plural;
	private String t_pes_plural;

	private String trad_p_pes_singular;
	private String trad_s_pes_singular;
	private String trad_t_pes_singular;
	private String trad_p_pes_plural;
	private String trad_s_pes_plural;
	private String trad_t_pes_plural;
*/

	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		codigo_verbo = req.getParameter("codigo_verbo");
		int codigo_verbo_int = Integer.parseInt(codigo_verbo);  



		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT p_pes_singular, s_pes_singular, t_pes_singular, p_pes_plural, s_pes_plural, t_pes_plural, trad_p_pes_singular, trad_s_pes_singular, trad_t_pes_singular, trad_p_pes_plural, trad_s_pes_plural, trad_t_pes_plural  FROM conjugacao WHERE codigo_verbo='"+codigo_verbo_int+"'");
			
	
			out.println("<html><head><title>Lista de verbos</title></head><body>");
			out.println("<br><br>");

 			while(rs.next()) 
			{
				out.println(rs.getString("p_pes_singular")+" - "+rs.getString("trad_p_pes_singular"));
				out.println("<br>");
				out.println(rs.getString("s_pes_singular")+" - "+rs.getString("trad_s_pes_singular"));
				out.println("<br>");
				out.println(rs.getString("t_pes_singular")+" - "+rs.getString("trad_t_pes_singular"));
				out.println("<br>");
				out.println(rs.getString("p_pes_plural")+" - "+rs.getString("trad_p_pes_plural"));
				out.println("<br>");
				out.println(rs.getString("s_pes_plural")+" - "+rs.getString("trad_s_pes_plural"));
				out.println("<br>");
				out.println(rs.getString("t_pes_plural")+" - "+rs.getString("trad_t_pes_plural"));
				out.println("<br>");
				out.println("<br>");

			} 
			out.println("<br><br>");
			out.println("");
			out.println("</body>");
			out.println("</html>");

		

			rs.close();
			con.close();
			st.close();
			
			res.sendRedirect( "/outroidioma/listar_verbo_2" );
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

