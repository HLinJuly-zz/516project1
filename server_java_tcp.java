/**
 * 
 * @author hanglin
 *cite:
 *Calculate the expression copy from http://blog.csdn.net/linminqin/article/details/41804955
 *
 */

import java.io.*;
import java.net.*;
import java.text.MessageFormat;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
public class server_java_tcp {
	
	public static void main(String []args)throws IOException{
		
		if(args.length!=1)
			 throw new IllegalArgumentException("Parameter(s):<Port>");
		int servPort = Integer.parseInt(args[0]);
		server_java_tcp st=new server_java_tcp(servPort);
	}
	
	
	public server_java_tcp(int portNum){
		
		try{
			
			//input the number of port
			//System.out.println("Enter the number of port:");
			
		//	InputStreamReader isr0=new InputStreamReader(System.in);
			
			//BufferedReader br0=new BufferedReader(isr0);
			
			//int portNum=Integer.parseInt(br0.readLine());
			
			//listen to the port  doesnot work?
			ServerSocket ss = new ServerSocket(portNum);
			
			//ServerSocket ss = new ServerSocket(3300);
			
			//test
			//System.out.println("server listen at 3300");
			
			//wait for the connection of socket from client
			Socket s=ss.accept();
			
			
			//get the massage from the client
			InputStreamReader isr=new InputStreamReader(s.getInputStream());
			BufferedReader br=new BufferedReader(isr);
			
			PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
			//pw.println("hello, this is server.");
			//get the input massage

			
				//test the infoFromClient
				String infoFromClient = br.readLine();
				
				//test
				//System.out.println("server get"+infoFromClient);
				
				//Calculate the expression
		        ScriptEngineManager manager = new ScriptEngineManager();  
		        ScriptEngine engine = manager.getEngineByName("js");  
		        Object result = engine.eval(infoFromClient); 
		        //int sum = result.
		        int b=Integer.parseInt(result.toString());
		        pw.println(b);
		        //System.out.println("+So...");

		        for(int i=0;i<b;i++){
		        	
		        	pw.println("Socket Programming");
		        	//System.out.println("into the loop");
		        }
		        s.close();
		        ss.close();
		        
		
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
		
	}

}
