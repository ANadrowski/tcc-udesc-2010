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
    
    	public infousuario(String login_usuario, int codigo_idioma)
	{
        	this.login_usuario = login_usuario;
        	this.codigo_idioma = codigo_idioma;
         }
}



public class cadastra_idioma extends HttpServlet
{
	

	private String nome_idioma;
	private int codigo_usuario;

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		nome_idioma = req.getParameter("nome_idioma");

		if (nome_idioma != "")
		{

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
				sql = "INSERT INTO idioma (nome_idioma, data, codigo_usuario) VALUES ('"+nome_idioma+"', '"+date+"', '"+codigo_usuario+"')";

				st.executeUpdate( sql );
				con.close();
				st.close();
			
				res.sendRedirect( "/outroidioma/configuracoes" );
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
		else res.sendRedirect( "/outroidioma/configuracoes" );
	}
}

