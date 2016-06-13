package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


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

public class configuracoes extends HttpServlet
{

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";

	public void service( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

    		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			String sql = "SELECT nome_idioma FROM idioma WHERE codigo_idioma='"+User.codigo_idioma+"'";
			Statement st = con.createStatement();
      			ResultSet rs = st.executeQuery(sql);

			out.println("<html><head>");
			out.println("<title>Configurações</title></head>");
			out.println("<body>");
			out.println("<form method='post' action='cadastra_idioma' name='cadastrar_idioma'>");
			out.println("<font size=2><b>Informe o nome do novo idioma e clique em cadastrar:</b></font><br> <br><input name='nome_idioma'><br><br><button name='Cadastrar'>Cadastrar</button></form>");
			out.println("<br>");
			out.println("<hr noshade='noshade' size='1px' align='left' width='503'><br>");

     			while(rs.next()) 
			{

				out.println("<font size=2>Idioma atual de trabalho: <b></font><font size=2 color='red'>"+rs.getString("nome_idioma")+"</b></font> ");
				out.println("<br>");

			} 
			out.println("<br><hr noshade='noshade' size='1px' align='left' width='503'><br>");
			
			sql = "SELECT nome_idioma, codigo_idioma FROM idioma WHERE codigo_usuario='"+User.codigo_usuario+"'";
			rs = st.executeQuery(sql);

			out.println("<br><font size=2><b>Seus idiomas de estudo, clique em ativar para selecionar:</b></font><br><br>");

			out.println("<table style='text-align: left; width: 546px; height: 64px;' border='0' cellpadding='2' cellspacing='2'>");
			out.println("<tbody>");
     			
			while(rs.next()) 
			{
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><form method='post' action='ativa_idioma' name='ativa_idioma'><input name='codigo_idioma' value='"+rs.getInt("codigo_idioma")+"' type='hidden'><font size=2><i>"+rs.getString("nome_idioma")+"</i></font><br><button value='Ativar' name='Ativer'>Ativar</button></form>");
			out.println("<hr noshade='noshade' size='1px' align='left' width='250'>");
			out.println("</td></tr>");


			} 

			out.println("</tbody>");
			out.println("</table>");
 			out.println("</body></html>");
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
    		}
	}
}

