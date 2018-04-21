import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadDatabase {

	public static void main (String[] args) {
		java.sql.Connection conn = null;
		ResultSet rs = null;
		PrintWriter writer  = null;
		
		//Make a directory called QueryData in the project directory and change this path
		// to point to that directory. "mesowest.out" file is the name of the query result file.
		File file = new File("/home/arman/GrpcJava/ProjectCluster/QueryData/mesowest.out");
		try {
			writer = new PrintWriter(file, "UTF-8");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connection succeeded again");

		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mesowestDB?&useSSL=false", "root", "root");
			System.out.println("Connection succeeded");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Statement stmt = conn.createStatement();
			
			//This query is a test query, we can also read the query from a file if neccesary.
			rs = stmt.executeQuery("SELECT * FROM FileData");
			while (rs.next()) {
				String STN = rs.getString("STN");
				String YYMMDD_HHMM = rs.getString("YYMMDD_HHMM");
				String MNET = rs.getString("MNET");
				String SLAT = rs.getString("SLAT");
				String SLON = rs.getString("SLON");
				String SELV = rs.getString("SELV");
				String TMPF = rs.getString("TMPF");
				String SKNT = rs.getString("SKNT");
				String DRCT = rs.getString("DRCT");
				String GUST = rs.getString("GUST");
				String PMSL = rs.getString("PMSL");
				String ALTI = rs.getString("ALTI");
				String DWPF = rs.getString("DWPF");
				String RELH = rs.getString("RELH");
				String WTHR = rs.getString("WTHR");
				String P24I = rs.getString("P24I");

				writer.println(STN + " " + YYMMDD_HHMM + " " + MNET + " " + SLAT + " " + SLON+ " " + SELV
						 + " " + TMPF + " " + SKNT + " " + DRCT + " " + GUST + " " + PMSL
						 + " " + ALTI + " " + DWPF + " " + RELH + " " + WTHR + " " + P24I);
						 
			}
			writer.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
}
