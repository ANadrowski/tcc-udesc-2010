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


public class prova extends HttpServlet
{
	
	private int codigo_prova;
	private String texto;

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

      		ResultSet rs = st.executeQuery("SELECT codigo_prova, assunto_prova, nota_prova, data_prova FROM prova  WHERE codigo_idioma="+User.codigo_idioma+"");

		out.println("<html><head>");
		out.println("<meta content='text/html; charset=ISO-8859-1' http-equiv='content-type'>");
		out.println("<title>Prova</title></head>");
		out.println("<body>");
		out.println("<form method='post' action='cadastra_prova' name='prova'>");
		out.println("<br><font size=2><b>Nota da prova:</b></font><br><input type='text' size='10' name='nota_prova' value=''><br>");
		out.println("<br><font size=2><b>Data da prova:</b></font><br><input type='text' size='30' name='data_prova' value=''><br>");
		out.println("<br><font size=2><b>Assunto da prova:</b></font><br><textarea cols='60' rows='2' name='assunto_prova'></textarea><br>");
		out.println("<div style='text-align: left;'>&nbsp;&nbsp;&nbsp; <button");
		out.println("value='Limpar' name='Limpar' type='reset'>Limpar</button><button");
		out.println("value='Cadastrar' name='Cadastrar'>Cadastrar</button><br>");
		out.println("</div></form>");
		out.println("<hr noshade='noshade' size='1px' align='left' width='503'>");

     		while(rs.next()) 
		{

			out.println("<table style='text-align: left; width: 546px; height: 64px;' border='0' cellpadding='2' cellspacing='2'>");
			out.println("<tbody>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;'><font size=2><b>Data: </b>"+rs.getString("data_prova")+"</font>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><font size=2><b>Nota: </b>"+rs.getString("nota_prova")+"</font><br>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><font size=2><b>Assunto: </b>"+rs.getString("assunto_prova")+"</font><br>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><form method='post' action='deleta_prova' name='deleta_prova'><input name='codigo_prova' value='"+rs.getInt("codigo_prova")+"' type='hidden'><button value='Excluir' name='Excluir'>Excluir</button></form>");
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

