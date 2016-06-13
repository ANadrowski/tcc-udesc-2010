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

public class verbo extends HttpServlet
{
	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void service( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		out.println("<html><head><title>Verbo</title></head>");
		out.println("<body>");	
		out.println("<font size=2><br> <b>Cadastro de novo verbo no gerundio:</b></font><br><br>");
		out.println("<form method='post' action='cadastra_verbo' name='cadastra_verbo'>");	
		out.println("<table style='text-align: left; width: 279px; height: 100px;' border='0' cellpadding='2' cellspacing='2'>");	
		out.println("<tbody>");
		out.println("<tr>");	
		out.println("<td style='vertical-align: top;'><font size=2>Verbo:</font><br>");	
		out.println("</td>");	
		out.println("<td style='vertical-align: top;'><input name='verbo'><br>");
		out.println("</td>");	
		out.println("</tr>");	
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><font size=2>Tradução:</font><br>");
		out.println("</td>");	
		out.println("<td style='vertical-align: top;'><input name='traducao_verbo'></td>");	
		out.println("</tr>");	
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><br>");	
		out.println("</td>");	
		out.println("<td style='vertical-align: top; text-align: right;'><input name='Cadastrar Verbo' value='Cadastrar Verbo' type='submit'><br>");
		out.println("</td>");	
		out.println("</tr>");	
		out.println("</tbody>");
		out.println("</table>");	
		out.println("</form>");
		out.println("<hr noshade='noshade' size='1px' align='left' width='503'>");
		out.println("</body>");	
		out.println("</html>");

	

    	try 
	{
		Connection con = null;
		Class.forName("org.postgresql.Driver"); 
		con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
		Statement st = con.createStatement();

      		ResultSet rs = st.executeQuery("SELECT codigo_verbo, verbo FROM verbo  WHERE codigo_idioma="+User.codigo_idioma+"");


		out.println("<html><head><title>Verbo</title></head>");
		out.println("<body>");
		out.println("<font size=2><br> <b>Cadastro de conjugação para verbo já cadastrado:</b></font><br><br>");
		out.println("<form method='post' action='cadastra_conjugacao' name='cadastra_conjugacao'>");
		out.println("<table style='text-align: left; width: 520px; height: 297px;' border='0' cellpadding='2' cellspacing='2'>");
		out.println("<tbody>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><font size=2>Selecione o verbo:</font><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'>");

		out.println("<select name='codigo_verbo'>");  
 		while(rs.next()) 
		{
		out.println("<option value='"+rs.getString("codigo_verbo")+"'>"+rs.getString("verbo")+"</option>");
		} 
		out.println("</select>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><font size=2>1 ª pes. (singular):</font><br>");
		out.println("</td>");	
		out.println("<td style='vertical-align: top;'><input name='p_pes_singular'></td>");
		out.println("<td style='vertical-align: top;'><input name='trad_p_pes_singular'></td>");
		out.println("</tr>");
		out.println("<tr>");	
		out.println("<td style='vertical-align: top;'><font size=2>2 ª pes. (singular):</font></td>");
		out.println("<td style='vertical-align: top;'><input name='s_pes_singular'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><input name='trad_s_pes_singular'></td>");	
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><font size=2>3 ª pes. (singular):</font></td>");
		out.println("<td style='vertical-align: top;'><input name='t_pes_singular'><br>");	
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><input name='trad_t_pes_singular'></td>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><font size=2>1 ª pes. (plural):</font></td>");
		out.println("<td style='vertical-align: top;'><input name='p_pes_plural'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><input name='trad_p_pes_plural'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><font size=2>2 ª pes. (plural):</font></td>");
		out.println("<td style='vertical-align: top;'><input name='s_pes_plural'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><input name='trad_s_pes_plural'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><font size=2>3 ª pes. (plural):</font></td>");
		out.println("<td style='vertical-align: top;'><input name='t_pes_plural'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><input name='trad_t_pes_plural'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><font size=2>Tempo verbal:</font><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><small>");

      		ResultSet rs1 = st.executeQuery("SELECT codigo_tempo_verbal, nome_tempo_verbal FROM tempoverbal  WHERE codigo_idioma="+User.codigo_idioma+"");

		out.println("<select name='codigo_tempo_verbal'>");  
 		while(rs1.next()) 
		{
		out.println("<option value='"+rs1.getString("codigo_tempo_verbal")+"'>"+rs1.getString("nome_tempo_verbal")+"</option>");
		} 
		out.println("</select>");


		out.println("</small><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><br>");
		out.println("</td>");
		out.println("<td style='vertical-align: top;'><button value='Cadastrar Verbo' name='Cadastrar Conjugação'>Cadastrar Conjugação</button></td>");
		out.println("</tr>");
		out.println("</tbody>");
		out.println("</table>");
		out.println("<br>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

		rs.close();	
		rs1.close();
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

