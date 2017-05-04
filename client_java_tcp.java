/**
 *  
 * @author hanglin
 *
 */

import java.net.*;
import java.io.*;

public class client_java_tcp {
	
	
	public static void main(String []args){
		
		client_java_tcp ct = new client_java_tcp();
	}
	
	public client_java_tcp(){
		
		try{
		System.out.println("Enter server name or IP address:");
		//input the IP
		BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

		
			
			String str1=br1.readLine();		
			
			//input the port num
			System.out.println("Enter port:");
			InputStreamReader isr=new InputStreamReader(System.in);
			
			BufferedReader br=new BufferedReader(isr);
			int portNum=Integer.parseInt(br.readLine());
			
			//
			//System.out.println(portNum);
			//test the port value
			if(portNum>65535){
				System.out.println("Invalid port number. Terminating.");
				
			}else{
			
			//Socket() connect to the server
			Socket s= new Socket( str1 , portNum);

			
			PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
			
			//enter expression
			System.out.println("Enter expression: ");
			InputStreamReader isr3=new InputStreamReader(System.in);
			
			BufferedReader br3=new BufferedReader(isr3);
			String expression=br3.readLine();
			//send to server
			pw.println(expression);
			
			
			//get info from server
			InputStreamReader isr2=new InputStreamReader(s.getInputStream());
			
			BufferedReader br2=new BufferedReader(isr2);
			
			//test
			//System.out.println("say somthing to server");
			//String info=br.readLine();
			//send to server
			//pw.println(info);
			
			//get the info from server
			String res=null;
			while((res=br2.readLine())!=null){
			System.out.println(res);
			}
			
			s.close();
			}
			
			
			
		} catch(UnknownHostException u){//catch (ConnectException u){
			System.out.println("Could not connect to server. Terminating.");
		}catch(SocketTimeoutException sT){
			System.out.println("Could not fetch result. Terminating.");
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
