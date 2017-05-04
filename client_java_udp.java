 /**  
 *      
 * UDP client 
 * @author hanglin
 * 
 * cite:
 * 1: int into byte[] copy from http://blog.sina.com.cn/s/blog_415bd70701000638.html
 * 
 */ 

import java.io.*;    
import java.net.*;    
   
public class client_java_udp {    
    private byte[] buffer = new byte[1024];    
    
    private DatagramSocket ds = null;    
   
    public client_java_udp() throws Exception {    
        ds = new DatagramSocket();    
    }    
           
    
    public final DatagramSocket getSocket() {    
        return ds;    
    }    
    
//send info to server    
    public final DatagramPacket send(final String host, final int port,    
            final byte[] bytes) throws IOException {    
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, InetAddress    
                .getByName(host), port);    
        ds.send(dp);    
        return dp;    
    }    
    
//receive the info from server   
    public final String receive(final String lhost, final int lport)    
            throws Exception {    
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);    
        ds.receive(dp);    
        String info = new String(dp.getData(), 0, dp.getLength()); 
        
        return info;    
    }    
    
    // close   
    public final void close() {    
        try {    
            ds.close();    
        } catch (Exception ex) {    
            ex.printStackTrace();    
        }    
    }    
    
   
    public static void main(String[] args) throws Exception {    
        client_java_udp client = new client_java_udp();   
        
    	System.out.println("Enter server name or IP address:");
		//input the IP
		BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

		
			
			String str1=br1.readLine();		
			
			//input the port num
			System.out.println("Enter port:");
			InputStreamReader isr=new InputStreamReader(System.in);
			
			BufferedReader br=new BufferedReader(isr);
			
			int portNum=Integer.parseInt(br.readLine());
        
        
        String serverHost = str1;    
        int serverPort = portNum; 
        
        if(portNum>65535){
			System.out.println("Invalid port number. Terminating.");
			
		}else{
        
			//enter expression
			System.out.println("Enter expression: ");
			InputStreamReader isr3=new InputStreamReader(System.in);
			
			BufferedReader br3=new BufferedReader(isr3);
			String expression=br3.readLine();
			int len = expression.length();
			String length = String.valueOf(len);
			//int into byte[]
			//int len = expression.length();
			//ByteArrayOutputStream boutput = new ByteArrayOutputStream();
			//DataOutputStream doutput = new DataOutputStream(boutput);
			//doutput.writeInt(len);
			//byte[] length = boutput.toByteArray();
			
			//set the timeout
		
			//try 3 times to send the expression and length
			int tries=0;
			boolean receivedResponse = false;
			
			if((!receivedResponse)&&(tries<3)){
				
				client.send(serverHost, serverPort,length.getBytes());	
		        
		        client.send(serverHost, serverPort, expression.getBytes());
					
					String info0 = client.receive(serverHost, serverPort);
					if(info0.equals("ACK0")){
						receivedResponse = true;
					}
					//wait for 1000 ms
					Thread.sleep(1000);
					tries++;
				
			}else{
				System.out.println("Failed to send Expression. Terminating.");
				client.close();
			}
			
			      
        
        
        
        //get massage from server
        //length of total result
        String info = client.receive(serverHost, serverPort);    
        System.out.println( info);
        int result = Integer.parseInt(info);
        String ack1="ACK1";
        if(info!=null){
        	
        	client.send(serverHost, serverPort, ack1.getBytes());
        }else {
			System.out.println("Could not fetch result. Terminating.");
			client.close();       	
        }
        	
        String ack2="ACK2";
        
        for(int i=0;i<result;i++){
        	
            String info1 = client.receive(serverHost, serverPort);    
            System.out.println( info1);
            
            if(info1!=null){
            	
            	client.send(serverHost, serverPort, ack2.getBytes());
            }else {
    			System.out.println("Could not fetch result. Terminating.");
    			client.close();       	
            }
        	
        	
        	
        }
        
        
        
        client.close();
		}
    }    
}