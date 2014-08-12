/* New Comment */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.activation.MimetypesFileTypeMap;

import java.net.*;
//hfdhhhhhhv
//import org.apache.tika.metadata.Metadata;
//import org.apache.tika.parser.AutoDetectParser;
//import org.apache.tika.parser.Parser;
//import org.apache.tika.sax.BodyContentHandler;
//import org.xml.sax.ContentHandler;


public class NonFormFileUploader {

	//static final String UPLOAD_URL = "http://localhost:8080/CodeWeb/ReceiveFileServlet";   http://localhost:8080/WRA2/ReceiveFileServlet  //http://localhost:8080/WRA2/jsoncall?call=ReceiveFileServlet
	//static final String UPLOAD_URL = "http://localhost:8080/WRA2/ReceiveFileServlet"; 
	static final String UPLOAD_URL = "http://localhost:8081/WRA2/jsoncall";
	//static final String UPLOAD_URL = "http://localhost:8080/WRA2/jsoncall?call=UploadDocumentTRACKID&TRACKID=2&FILE=true&DOCTYPE=1&F1=24&F2=Umair&F3=123&F4=50000&F5=345";
	static final int BUFFER_SIZE = 4096;
	
 
    public static void main(String[] args) throws IOException {
        // takes file path from first program's argument
       String filePath = args[0];
    	//String filePath ="D:\\evo.png";
        File uploadFile = new File(filePath);
 
		String[] fileNameExt=filePath.split(".");
		
			
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
 
		//FileNameMap fileNameMap = URLConnection.getFileNameMap();
		
		//String mimeType =  fileNameMap.getContentTypeFor(uploadFile.getName());
		
		 System.out.println("MIMe Type:"+new MimetypesFileTypeMap().getContentType(filePath));

//		  Path p = Path(filePath);
			//String s = Files.probeContentType(p);
			//System.out.println("Mime type" + s);
			
                        URL u = new URL(UPLOAD_URL);
                        URLConnection uc = null;
                        uc = u.openConnection();
//                        type = uc.getContentType();
                
                 System.out.println("File Typ: "  +uc.getContentType());
                 
                  String fname= uploadFile.getName();
                  System.out.println("File name:"+fname);
                 
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			String mimeType = fileNameMap.getContentTypeFor(fname);
			System.out.println("mimeType="+  mimeType);
			
               //BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

                        
			
		 //System.out.println("File Type:"+Files.probeContentType(filePath));	
 
		//String strContentType = request.getContentType();
        System.out.println("File to upload: " + filePath);
 
        // creates a HTTP connection
        URL url = new URL(UPLOAD_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        // sets file name as a HTTP header
		
		
		  httpConn.setRequestProperty("DOCTYPE", "3");
		  httpConn.setRequestProperty("pid", "282880666932668");
		  httpConn.setRequestProperty("sid", "767211449");
		  httpConn.setRequestProperty("F1", "1");
		  httpConn.setRequestProperty("F2", "2");
		  httpConn.setRequestProperty("F3", "3");
		  httpConn.setRequestProperty("F4", "4");
		  httpConn.setRequestProperty("F5", "5");
                  httpConn.setRequestProperty("mimetype", mimeType);
		  
        httpConn.setRequestProperty("fileName", uploadFile.getName());
 
        // opens output stream of the HTTP connection for writing data
        OutputStream outputStream = httpConn.getOutputStream();
 
        // Opens input stream of the file for reading data
        FileInputStream inputStream = new FileInputStream(uploadFile);
 

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        System.out.println("Start writing data...");
 
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
 
        System.out.println("Data was written.");
        outputStream.close();
        inputStream.close();
 
        // always check HTTP response code from server
        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // reads server's response
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String response = reader.readLine();
            System.out.println("Server's response: " + response);
        } else {
            System.out.println("Server returned non-OK code: " + responseCode);
        }
    }
	
}
