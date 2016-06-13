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


public class topicoespecial extends HttpServlet
{
	
	private int codigo_topico_especial;
	private String nome_topico_especial;
	

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";

	public void service( HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException  
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

    		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			Statement st = con.createStatement();

      			ResultSet rs = st.executeQuery("SELECT nome_topico_especial, codigo_idioma FROM topicoespecial WHERE codigo_idioma="+User.codigo_idioma+"");

			out.println("<html><head>");
			out.println("<meta content='text/html; charset=ISO-8859-1' http-equiv='content-type'>");
			out.println("<title>Tópico especial</title></head>");
			out.println("<font size=2><b>Informe o nome do novo tópico especial e clique em cadastrar: </b><font><br>");
			out.println("<form method='post' action='cadastra_topico_especial' name='cadastra_topico_especial'>");
			out.println("<br><input type='text' size='40' name='nome_topico_especial' value=''><br>");
			out.println("<div style='text-align: left;'>");
			out.println("<br><button value='Cadastrar' name='Cadastrar'>Cadastrar</button><br>");
			out.println("</div></form>");
			out.println("<hr noshade='noshade' size='1px' align='left' width='503'>");
			out.println("<body>");
			out.println("<br><br><font size=2><b>Tópicos especiais atualmente cadastrados: </b></font><br>");


     			while(rs.next()) 
			{
				out.println("<font size=2><br><i> &nbsp; &nbsp; &nbsp; &nbsp; &raquo; &nbsp;"+rs.getString("nome_topico_especial")+"</i></font>");
			} 
			out.println("<br><br><a href='/outroidioma/index_deletar_topico_especial' TARGET='conteudo'><font size=2>Clique aqui para deletar um tópico especial.</font></a>");
			
			rs.close();		
			st.close();
			con.close();	

		}
		catch(ClassNotFoundException e) 
		{ 
        		out.println("Couldn't load database driver: " + e.getMessage());
    		}
    		catch(SQLException e) 
		{ 
      			out.println("SQLException caught: " + e.getMessage());
			out.println("Login: "+User.login_usuario);
			out.println("Codigo user: "+User.codigo_usuario);
    		}
	}
}

