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
		out.println("<form method='post' action='cadastrar_palavra' name='cadastrar_palavra'>");
		out.println("<table style='text-align: left; width: 468px; height: 232px;' border='0' cellpadding='0' cellspacing='0'>");
		out.println("<tbody>");
		out.println("<tr>");
		out.println("<td>Palavra: </td>");
		out.println("<td><input name='palavra'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Tradução: </td>");
		out.println("<td><input name='traducao'></td>");	
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Pronúncia escrita:</td>");
		out.println("<td style='text-align: left;'><input name='pronuncia_escrita'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Classe gramatical:</td>");
		//exibe classes gramaticais cadastradas:
		out.println("<td>");
	
		out.println("<select>");  
		out.println("<option>Não classificar</option>"); 
 		while(rs.next()) 
		{
		out.println("<option>"+rs.getString("nome_classe_gramatical")+"</option>");
		} 
		out.println("</select>");
		out.println("<br>");


		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Tempo verbal:</td>");
		//exibe tempos verbais cadastrados:

      		ResultSet rs1 = st.executeQuery("SELECT codigo_tempo_verbal, nome_tempo_verbal FROM tempoverbal  WHERE codigo_idioma="+User.codigo_idioma+"");

		out.println("<td>");
	
		out.println("<select>");  
		out.println("<option>Não classificar</option>");
 		while(rs1.next()) 
		{
		out.println("<option>"+rs1.getString("nome_tempo_verbal")+"</option>");
		} 
		out.println("</select>");
		out.println("<br>");


		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");



		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'>Tópico especial:</td>");

		ResultSet rs2 = st.executeQuery("SELECT codigo_topico_especial, nome_topico_especial FROM topicoespecial  WHERE codigo_idioma="+User.codigo_idioma+"");

		out.println("<td>");
	
		out.println("<select>");  
		out.println("<option>Não classificar</option>");
 		while(rs2.next()) 
		{
		out.println("<option>"+rs2.getString("nome_topico_especial")+"</option>");
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
		out.println("<td style='vertical-align: top;'><button name='Cadastrar palavra'>Cadastrar palavra</button><br>");
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
		rs1.close();	
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

