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

public class expressao extends HttpServlet
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
		String sql = "SELECT codigo_usuario FROM usuario WHERE login_usuario='"+User.login_usuario+"'";
		Statement st = con.createStatement();
		ResultSet rs1 = st.executeQuery(sql);

     		while(rs1.next()) 
		{
			codigo_usuario = rs1.getInt("codigo_usuario");
		} 

      		ResultSet rs = st.executeQuery("SELECT codigo_expressao_idiomatica, expressao_idiomatica, traducao_expressao_idiomatica FROM expressaoidiomatica  WHERE codigo_idioma="+User.codigo_idioma+"");

		out.println("<html><head>");
		out.println("<meta content='text/html; charset=ISO-8859-1' http-equiv='content-type'>");
		out.println("<title>Expressão idiomática</title></head>");
		out.println("<body>");
		out.println("<form method='post' action='escreve_expressao' name='expressao'>");
		out.println("<br><font size=2><b>Expressão idiomática:</b></font><br><input type='text' size='60' name='expressao_idiomatica' value=''><br>");
		out.println("<br><font size=2><b>Tradução:</b></font><br><input type='text' size='60' name='traducao_expressao_idiomatica' value=''><br>");
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
			out.println("<td style='vertical-align: top;'><font size=2><b>Expressão idiomática: </b>"+rs.getString("expressao_idiomatica")+"</font>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><font size=2><b>Tradução: </b>"+rs.getString("traducao_expressao_idiomatica")+"</font><br>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><form method='post' action='deleta_expressao' name='deleta_expressao'><input name='codigo_expressao_idiomatica' value='"+rs.getInt("codigo_expressao_idiomatica")+"' type='hidden'><button value='Excluir' name='Excluir'>Excluir</button></form>");
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

