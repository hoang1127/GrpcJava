import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WriteDatabase {

	public static void main(String[] args) {

		java.sql.Connection conn = null;

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
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        String insertRecord = "INSERT INTO FileData VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(insertRecord);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        
		if (conn != null) {
			
			//Change this path to your path where mesowest.out file exists.
			//The Netty code will drop the file in a downloads folder so the path should point
			// to the file in that downloads folder.
			File file = new File("/home/arman/GrpcJava/ProjectCluster/downloads/mesowest.out");
			int count = 1;
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			       String[] elementsArray = line.split(" ");
			       for(int i = 0; i < elementsArray.length; i++) {
			    	   if(elementsArray[i].equals("")) {
			    		   
			    	   } else {
			    		   if (elementsArray[i].contains("/")) {
			    			   Timestamp timestamp = getTimestamp(elementsArray[i]);
			    			   try {
									statement.setTimestamp(count,timestamp);
								} catch (SQLException e) {
									e.printStackTrace();
								}
			    		   } else {
			    			   try {
									statement.setString(count,elementsArray[i]);
								} catch (SQLException e) {
									e.printStackTrace();
								}
			    		   }			    		   
			    		   count++;				    	  
			    	   }			    	   
			       }
			       count = 1;
			       int insertResult = 0;
				   try {
				    	insertResult = statement.executeUpdate();
				   } catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				   }
			       if (insertResult > 0) {
			           System.out.println("Inserted row successfully. ");
			       }
			       
			    }
			}
//			
//            }
			 catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public static Timestamp getTimestamp(String str) {
		
		Date parsedDate = null;
		
		String date = str.substring(0, 4) + "/" + str.substring(4,6) + "/" + str.substring(6, 8) 
						+ " " + str.substring(9, 11) + ":" + str.substring(11, 13);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			parsedDate = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Timestamp timestamp = new Timestamp(parsedDate.getTime());
		return timestamp;
				
	}
}
