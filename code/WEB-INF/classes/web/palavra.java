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

public class palavra extends HttpServlet
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
		Statement st = con.createStatement();

      		ResultSet rs = st.executeQuery("SELECT codigo_classe_gramatical, nome_classe_gramatical FROM classegramatical  WHERE codigo_idioma="+User.codigo_idioma+"");


		out.println("<html><head><title></title></head><body>");	
		out.println("<form method='post' action='cadastra_palavra' name='cadastra_palavra'>");
		out.println("<font size=2><b><br>Preencha os campos abaixo para cadastrar uma palavra:</font></b><br>");
		out.println("<table style='text-align: left; width: 350px; height: 232px;' border='0' cellpadding='0' cellspacing='0'>");
		out.println("<tbody>");
		out.println("<tr>");
		out.println("<td><font size=2>Palavra: </font></td>");
		out.println("<td><input name='palavra'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td><font size=2>Tradução: </font></td>");
		out.println("<td><input name='traducao'></td>");	
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td><font size=2>Pronúncia escrita:</font></td>");
		out.println("<td style='text-align: left;'><input name='pronuncia_escrita'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td><font size=2>Classe gramatical:</font></td>");

		out.println("<td>");
	
		out.println("<select name='codigo_classe_gramatical'>");  
 		while(rs.next()) 
		{
		out.println("<option value='"+rs.getString("codigo_classe_gramatical")+"'>"+rs.getString("nome_classe_gramatical")+"</option>");
		} 
		out.println("</select>");
		out.println("<br>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: center;'><font size=2>Tópico especial:</font></td>");

		ResultSet rs2 = st.executeQuery("SELECT codigo_topico_especial, nome_topico_especial FROM topicoespecial  WHERE codigo_idioma="+User.codigo_idioma+"");

		out.println("<td>");
	
		out.println("<select name='codigo_topico_especial'>");  
 		while(rs2.next()) 
		{
		out.println("<option value='"+rs2.getString("codigo_topico_especial")+"'>"+rs2.getString("nome_topico_especial")+"</option>");
		} 
		out.println("</select>");
		out.println("<br>");
		out.println("</td>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><button name='Cadastrar Palavra'>Cadastrar Palavra</button><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</tbody>");
		out.println("<br>");
		out.println("<br>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

		rs.close();
		rs2.close();		
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

