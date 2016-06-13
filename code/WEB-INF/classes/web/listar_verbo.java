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

public class listar_verbo extends HttpServlet
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

      			ResultSet rs = st.executeQuery("SELECT codigo_verbo, verbo FROM verbo  WHERE codigo_idioma="+User.codigo_idioma+"");



			out.println("<html><head><title></title></head>");
			out.println("<body>");
			out.println("<font size=2><b><br>Exibição da conjugação de um verbo cadastrado: </font></b><br><br><br>");
			out.println("<form method='post' action='listar_verbo_2' name='listar_verbo_2'>");
			out.println("<table style='text-align: left; width: 470px; height: 42px;' border='0' cellpadding='2' cellspacing='2'>");
			out.println("<tbody>");
			out.println("<tr>");
			out.println("<td style='vertical-align: top;'><font size=2>Selecione o verbo que deseja listar:</font></td>");
			out.println("<td style='vertical-align: top;'>");

			out.println("<select name='codigo_verbo'>");  
 			while(rs.next()) 
			{
			out.println("<option value='"+rs.getString("codigo_verbo")+"'>"+rs.getString("verbo")+"</option>");
			} 
			out.println("</select>");

			out.println("</td>");
			out.println("<td style='vertical-align: top;'><label><button value='Listar' name='Listar'>Listar</button></label></td>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");

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

