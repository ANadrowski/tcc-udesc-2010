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



public class cadastra_conjugacao extends HttpServlet
{
	
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

	private String codigo_verbo;
	private String codigo_tempo_verbal;


	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();


		p_pes_singular = req.getParameter("p_pes_singular");
		s_pes_singular = req.getParameter("s_pes_singular");
		t_pes_singular = req.getParameter("t_pes_singular");
		p_pes_plural = req.getParameter("p_pes_plural");
		s_pes_plural = req.getParameter("s_pes_plural");
		t_pes_plural = req.getParameter("t_pes_plural");
		trad_p_pes_singular = req.getParameter("trad_p_pes_singular");
		trad_s_pes_singular = req.getParameter("trad_s_pes_singular");
		trad_t_pes_singular = req.getParameter("trad_t_pes_singular");
		trad_p_pes_plural = req.getParameter("trad_p_pes_plural");
		trad_s_pes_plural = req.getParameter("trad_s_pes_plural");
		trad_t_pes_plural = req.getParameter("trad_t_pes_plural");
		codigo_verbo = req.getParameter("codigo_verbo");
		codigo_tempo_verbal = req.getParameter("codigo_tempo_verbal");


		int codigo_verbo_int = Integer.parseInt(codigo_verbo);  
		int codigo_tempo_verbal_int = Integer.parseInt(codigo_tempo_verbal); 

		if ((p_pes_singular != "") && (s_pes_singular != "") && (t_pes_singular != "") && (p_pes_plural != "") && (s_pes_plural != "") && (t_pes_plural != ""))
		{

		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	

			Statement st = con.createStatement();
			String sql = "INSERT INTO conjugacao (p_pes_singular, s_pes_singular, t_pes_singular, p_pes_plural, s_pes_plural, t_pes_plural, trad_p_pes_singular, trad_s_pes_singular, trad_t_pes_singular, trad_p_pes_plural, trad_s_pes_plural, trad_t_pes_plural, codigo_verbo, codigo_tempo_verbal) VALUES ('"+p_pes_singular+"', '"+s_pes_singular+"', '"+t_pes_singular+"', '"+p_pes_plural+"', '"+s_pes_plural+"', '"+t_pes_plural+"', '"+trad_p_pes_singular+"', '"+trad_s_pes_singular+"', '"+trad_t_pes_singular+"', '"+trad_p_pes_plural+"', '"+trad_s_pes_plural+"', '"+trad_t_pes_plural+"', '"+codigo_verbo_int+"', '"+codigo_tempo_verbal_int+"')";

			st.executeUpdate( sql );
			con.close();
			st.close();
			
				res.sendRedirect( "/outroidioma/verbo" );
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
		else res.sendRedirect( "/outroidioma/verbo" );
	}
}

