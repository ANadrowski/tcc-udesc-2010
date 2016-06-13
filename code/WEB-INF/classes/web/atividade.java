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


public class atividade extends HttpServlet
{
	
	private int codigo_atividade;
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

      			ResultSet rs = st.executeQuery("SELECT codigo_atividade, nome_atividade FROM atividade WHERE codigo_idioma="+User.codigo_idioma+"");

			out.println("<html><head>");
			out.println("<meta content='text/html; charset=ISO-8859-1' http-equiv='content-type'>");
			out.println("<title>Atividade</title></head>");
			out.println("<font size=2>Nome da nova atividade: </font><br>");
			out.println("<form method='post' action='cadastra_atividade' name='cadastra_atividade'>");
			out.println("<br><input type='text' size='60' name='nome_atividade' value=''><br>");
			out.println("<div style='text-align: left;'>");
			out.println("<br><button value='Cadastrar' name='Cadastrar'>Cadastrar</button><br>");
			out.println("</div></form>");
			out.println("<body>");
			out.println("<hr noshade='noshade' size='1px' align='left' width='503'>");
			out.println("<br><br><font size=2> <b> Atividades cadastradas: </b></font><br><br>");


     			while(rs.next()) 
			{

				out.println("<table style='text-align: left; width: 546px; height: 64px;' border='0' cellpadding='2' cellspacing='2'>");
				out.println("<tbody>");
				out.println("<tr>");
				out.println("<td style='vertical-align: top;'><font size=2><i>"+rs.getString("nome_atividade")+"</i></font>");
				out.println("</td></tr>");
				out.println("<tr>");
				out.println("<td style='vertical-align: top;' width='80'><form method='post' action='questao_descritiva' name='questao_descritiva'><input name='codigo_atividade' value='"+rs.getInt("codigo_atividade")+"' type='hidden'><button value='Manipular' name='Manipular'>Manipular</button></form>");
				out.println("<hr noshade='noshade' size='1px' align='left' width='250'>");
				out.println("</td></tr>");
				out.println("<tr>");
				out.println("<td style='vertical-align: top;'>");
				out.println("</td></tr>");
				out.println("</tbody>");
				out.println("</table>");
			} 

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

