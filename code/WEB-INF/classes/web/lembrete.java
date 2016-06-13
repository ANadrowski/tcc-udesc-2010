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


public class lembrete extends HttpServlet
{
	
	private int codigo_lembrete;
	private String texto;
//	private Date data_lembrete;
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

      		ResultSet rs = st.executeQuery("SELECT codigo_lembrete, lembrete, data_lembrete FROM lembrete  WHERE codigo_usuario="+codigo_usuario+"");

		out.println("<html><head>");
		out.println("<meta content='text/html; charset=ISO-8859-1' http-equiv='content-type'>");
		out.println("<title>Lembretes</title>");
		out.println("<style type='text/css'>");
		out.println(".estilotextarea {width:500px;height:80px;border: 1px dotted #000099;}");
		out.println("</style>");
		out.println("</head><body>");
		out.println("<form method='post' action='escreve_lembrete' name='lembrete'><font size=2><b>Lembrete:</b</font><br><textarea cols='68' rows='4'");
		out.println("name='texto'></textarea><br>");
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
			out.println("<td style='vertical-align: top;'><b><font size=2>Data do registro: "+rs.getString("data_lembrete")+"</font></b>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'> <font size=2><b>Lembrete: </b>"+rs.getString("lembrete")+"</font><br>");
			out.println("</td></tr>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;' width='80'><form method='post' action='deleta_lembrete' name='deleta_lembrete'><input name='codigo_lembrete' value='"+rs.getInt("codigo_lembrete")+"' type='hidden'><button value='Excluir' name='Excluir'>Excluir</button></form>");
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
		out.println("Codigo user: "+codigo_usuario);
    	}
	}
}

