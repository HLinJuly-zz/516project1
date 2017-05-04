/**
 * 
 * @author hanglin
 * cite
 * Calculate the expression copy from http://blog.csdn.net/linminqin/article/details/41804955
 */

import java.io.*;    
import java.net.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;       
   
public class server_java_udp {    
    private byte[] buffer = new byte[1024];    
        
    private DatagramSocket ds = null;    
    
    private DatagramPacket packet = null;    
    
    private InetSocketAddress socketAddress = null;    
    
    private String orgIp;    
    
   
    public server_java_udp(String host, int port) throws Exception {    
        socketAddress = new InetSocketAddress(host, port);    
        ds = new DatagramSocket(socketAddress);    
       // System.out.println("server begin to work");    
    }    
        
    public final String getOrgIp() {    
        return orgIp;    
    }    
    
    //set the timeout....doesnot work well
    //cancel
    public final void setSoTimeout(int timeout) throws Exception {    
        ds.setSoTimeout(timeout);    
    }    
    
//timeout cancel   
    public final int getSoTimeout() throws Exception {    
        return ds.getSoTimeout();    
    }    
    
//bind the host and port   
    public final void bind(String host, int port) throws SocketException {    
        socketAddress = new InetSocketAddress(host, port);    
        ds = new DatagramSocket(socketAddress);    
    }    
    
    
//receive the massage from client   
    public final String receive() throws IOException {    
        packet = new DatagramPacket(buffer, buffer.length);    
        ds.receive(packet);    
        orgIp = packet.getAddress().getHostAddress();    
        String info = new String(packet.getData(), 0, packet.getLength());    
        //System.out.println("get info：" + info);    
        return info;    
    }    
    
//send the massage to client    
    public final void response(String info) throws IOException {    
       // System.out.println("server send to client");    
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length, packet    
                .getAddress(), packet.getPort());    
        dp.setData(info.getBytes());    
        ds.send(dp); 
        //test
        //System.out.println(dp);
    }    
    
//set the length of the buff   
    public final void setLength(int bufsize) {    
        packet.setLength(bufsize);    
    }    
    
   //get the ip
    public final InetAddress getResponseAddress() {    
        return packet.getAddress();    
    }    
    
  //get the port
    public final int getResponsePort() {    
        return packet.getPort();    
    }    
    
//close the socket   
    public final void close() {    
        try {    
            ds.close();    
        } catch (Exception ex) {    
            ex.printStackTrace();    
        }    
    }    
    
    
    public static void main(String[] args) throws Exception {    
        String serverHost = "127.0.0.1";    
       //input the num of port
        int serverPort = Integer.parseInt(args[0]); 
        //int serverPort = 3300;//change later
        server_java_udp udpServerSocket = new server_java_udp(serverHost, serverPort);    
         
            String a = udpServerSocket.receive();
            long timeA = System.currentTimeMillis();
            String b = udpServerSocket.receive();
            int a0=Integer.parseInt(a);
            
            long timeB = System.currentTimeMillis();
            
            long waittime0=timeB-timeA;
            
            if(a0==b.length()&&(waittime0<500)){
            
            	udpServerSocket.response("ACK0");
            	
			//Calculate the expression
	        ScriptEngineManager manager = new ScriptEngineManager();  
	        ScriptEngine engine = manager.getEngineByName("js");  
	        Object result = engine.eval(b); 
	        //int sum = result.
	        int c=Integer.parseInt(result.toString());
	        
	        System.out.println(c);
	        
            String cResult = String.valueOf(c);
            String sP="Socket Programming";
            //String the total result's length
            //String length1=String.valueOf(cResult.length());
                   
            
            //udpServerSocket.response(cResult); 
            
            //begin to send the result and socket programming
            
            //send result.length and result
			int tries=0;
			boolean receivedResponse = false;
			
			if((!receivedResponse)&&(tries<3)){
				
				//udpServerSocket.response(length1);	
		        
				udpServerSocket.response(cResult);
					
					String info0 = udpServerSocket.receive();
					if(info0.equals("ACK1")){
						receivedResponse = true;
					}
					//wait for 1000 ms
					Thread.sleep(1000);
					tries++;
				
			}else{
				System.out.println("Result transmission failed Terminating.");
				udpServerSocket.close();
			}
			
            
            
            //send "socket programming"
            for(int i=0;i<c;i++){
            	
            	
    			int tries2=0;
    			boolean receivedResponse2 = false;
    			
    			if((!receivedResponse2)&&(tries2<3)){
    				
    				udpServerSocket.response(sP);
    		        
    				//udpServerSocket.response(cResult);
    					
    					String info0 = udpServerSocket.receive();
    					if(info0.equals("ACK2")){
    						receivedResponse2 = true;
    					}
    					//wait for 1000 ms
    					Thread.sleep(1000);
    					tries2++;
    				
    			}else{
    				System.out.println("Result transmission failed Terminating.");
    				udpServerSocket.close();
    			}	
            	
            }
            
            
            
            
            
            }else{
            	
            	System.out.println("Did not receive valid expression from client. Terminating.");
            	udpServerSocket.close();
            } 
          
            
            
            
    }    
}