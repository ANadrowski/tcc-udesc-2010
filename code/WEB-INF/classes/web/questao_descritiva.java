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

public class questao_descritiva extends HttpServlet
{
	
	private String codigo_atividade;
	private String frase;
	private String traducao;

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";


	public void doPost( HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException 
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

		codigo_atividade = req.getParameter("codigo_atividade");
		int codigo_atividade_int = Integer.parseInt(codigo_atividade);  

		frase = req.getParameter("frase");
		traducao = req.getParameter("traducao");


		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	

			Statement st = con.createStatement();

			String sql = "INSERT INTO questaodescritiva (codigo_atividade, frase, traducao) VALUES ('"+codigo_atividade_int+"', '"+frase+"', '"+traducao+"')";

			st.executeUpdate( sql );

			out.println("<html><head>");
			out.println("<title></title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<form method='post' action='questao_descritiva' name='questao_descritiva'>Frase: <br>");
			out.println("<textarea cols='60' rows='2' name='frase'></textarea><br>");
			out.println("<br>");
			out.println("Tradução:<br>");
			out.println("<textarea cols='60' rows='2' name='traducao'></textarea><br>");
			out.println("<button value='Gerar questão' name='Gerar questão'>Gerar Questão</button><br>");
			out.println("<br>");
			out.println("<div style='text-align: right;'><br></div>");
			out.println("</form>");
			out.println("</body></html>");
			out.println("");


			con.close();
			st.close();
			
				res.sendRedirect( "/outroidioma/questao_descritiva" );
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

