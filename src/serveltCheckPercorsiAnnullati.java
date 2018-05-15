import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.HttpURLConnection;
	import java.net.URL;
	import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
	import org.json.JSONObject;

	@WebServlet("/serveltCheckPercorsiAnnullati")
	public class serveltCheckPercorsiAnnullati  extends HttpServlet {
		private static final long serialVersionUID = 1L;
		private final String USER_AGENT = "Mozilla/5.0";
		private String cod="";
		private String username="";	
		private String partenza ="";
		private String destinazione ="";
		private String data ="";
		
		
		
		JSONObject js = new JSONObject();

	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    public serveltCheckPercorsiAnnullati() {
			// TODO Auto-generated constructor stub
		
	        super();
	        // TODO Auto-generated constructor stub
	    }

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("prova servlet 1"); 
			// TODO Auto-generated method stub
		}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			System.out.println(this.getClass());
			// PRENDIAMO IL CODICE CHE ARRIVA DALL'APP
			queryDB qb = new queryDB();
		
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = request.getReader();
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line).append('\n');
				}
			} finally {
				reader.close();
			}

			JSONObject j;
			try {
		   
			j= new JSONObject(sb.toString());
			username = j.getString("user");
			
			//System.out.println("risultato servlet prova: "+username);
			}
			catch (Exception e) {e.printStackTrace();
				// TODO: handle exception
			}
			
			
			ResultSet rs;
			
			try {
				rs=qb.query("select * from percorso where cod in (select codpercorso from richiesta where nomeutenterichiedente= '"+ username +"') and  stato = 'annullato'");
				//select * from carpooling.percorso where cod in(select codpercorso from carpooling.richiesta where nomeutenterichiedente='b') and stato = 'annullato' 
				rs.next();
				 cod=rs.getString("cod");
				 partenza=rs.getString("indirizzopart");
				 destinazione=rs.getString("indirizzodest");
				 data=rs.getString("data");
				
			//System.out.println("il percorso annullato � cod:"+cod+" da "+ partenza+"in data "+data);
			}
			 catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

			try {
				
				    js.put("partenza", partenza.toString());
					js.put("destinazione", destinazione.toString());
					js.put("data", data.toString());
					js.put("cod", cod).toString();
					
			}
			 catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().write(js.toString());
			
		}

	}
