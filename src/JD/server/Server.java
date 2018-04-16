package JD.server;

import java.io.IOException;
import java.net.ServerSocket;  
import java.net.Socket;

import JD.Threads.server.ServerThreadAllocatingGetUrlWork;

public class Server {  

	
	public static void main(String[] args) throws IOException
	{
		 ServerSocket server = new ServerSocket(9963);  
	        Socket client = null;  
	        while(true){  
	        	
	            client = server.accept();  
	            System.out.println("与客户端连接成功！");  
	            String rmsg="shoudao";

	            new Thread(new ServerThreadAllocatingGetUrlWork(client,rmsg)).start();  
	        }  
	        
	        //server.close();  
		
	}
	

}  