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



public class escreve_ditado extends HttpServlet
{
	
	private int codigo_ditado;
	private String ditado_popular;
	private String traducao_ditado_popular;
	private int codigo_usuario;

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		ditado_popular = req.getParameter("ditado_popular");
		traducao_ditado_popular = req.getParameter("traducao_ditado_popular");

		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	

			String sql = "SELECT codigo_usuario FROM usuario WHERE login_usuario='"+User.login_usuario+"'";
			Statement st = con.createStatement();
			ResultSet rs1 = st.executeQuery(sql);

     			while(rs1.next()) 
			{
				codigo_usuario = rs1.getInt("codigo_usuario");
			} 
			java.util.Date date = new java.util.Date();
			sql = "INSERT INTO ditadopopular (ditado_popular, traducao_ditado_popular, codigo_idioma, codigo_usuario) VALUES ('"+ditado_popular+"', '"+traducao_ditado_popular+"', '"+User.codigo_idioma+"', '"+codigo_usuario+"')";

			st.executeUpdate( sql );
			con.close();
			st.close();
			
				res.sendRedirect( "/outroidioma/ditado" );
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

