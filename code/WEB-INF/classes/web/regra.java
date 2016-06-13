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


public class regra extends HttpServlet
{
	
	private int codigo_ditado;
	private String texto;
	private int codigo_usuario;	

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

      		ResultSet rs = st.executeQuery("SELECT nome_regra, codigo_regra, descritivo_regra, exemplo_regra FROM regragramatical  WHERE codigo_idioma="+User.codigo_idioma+"");

		out.println("<html><head>");
		out.println("<meta content='text/html; charset=ISO-8859-1' http-equiv='content-type'>");
		out.println("<title>Regra gramatical</title></head>");
		out.println("<body>");
		out.println("<form method='post' action='escreve_regra' name='regra'>");
		out.println("<br><font size=2><b>Nome da regra:</b</font><br><input type='text' size='60' name='nome_regra' value=''><br>");
		out.println("<br><font size=2><b>Descritivo:</b></font><br><textarea cols='60' rows='2' name='descritivo_regra'></textarea><br>");
		out.println("<br><font size=2><b>Exemplo:</b></font><br><textarea cols='60' rows='2' name='exemplo_regra'></textarea><br>");
		out.println("<div style='text-align: left;'>&nbsp;&nbsp;&nbsp; <button");
		out.println("value='Limpar' name='Limpar' type='reset'>Limpar</button><button");
		out.println("value='Escrever' name='Escrever'>Escrever</button><br>");
		out.println("</div></form>");
		out.println("<hr noshade='noshade' size='1px' align='left' width='503'>");

     		while(rs.next()) 
		{

			out.println("<table style='text-align: left; width: 546px; height: 64px;' border='0' cellpadding='2' cellspacing='2'>");
			out.println("<tbody>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;'><font size=2><b>Nome da regra: </b>"+rs.getString("nome_regra")+"</font>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><font size=2><b>Descritivo: </b>"+rs.getString("descritivo_regra")+"</font><br>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><font size=2><b>Exemplo: </b>"+rs.getString("exemplo_regra")+"</font><br>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><form method='post' action='deleta_regra' name='deleta_regra'><input name='codigo_regra' value='"+rs.getInt("codigo_regra")+"' type='hidden'><button value='Excluir' name='Excluir'>Excluir</button></form>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;'>");
			out.println("</td></tr>");
			out.println("</tbody>");
			out.println("</table>");
		} 

      		out.println("</UL>");
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
		out.println("Login: "+User.login_usuario);
		out.println("Codigo user: "+User.codigo_usuario);
    	}
	}
}

