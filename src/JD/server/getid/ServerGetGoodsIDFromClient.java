package JD.server.getid;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import JD.Threads.server.InsertGoodUrl;

import JD.Threads.server.ServerThreadGetGoodsUrlFromClient;



public class ServerGetGoodsIDFromClient
{
	private int  port;

	
	

	public ServerGetGoodsIDFromClient(int port)
	{
		super();
		this.port = port;
	}



	public void start() throws IOException
	{
		
		InsertGoodUrl insert= new InsertGoodUrl();
		Thread tin= new Thread(insert);
     	tin.start();

		 int i = 0;

			
		  ServerSocket server = new ServerSocket(port);		
		  System.out.println("�ռ�ID����������......");
	        while(true){ 
	        	
	        	
	        	// TODO
	        	Socket client = null;
	        	 client = server.accept();  
	            System.out.println("�ռ�ID��������ͻ������ӳɹ���������"+i+"��"); 
	            i++;
	            
	         
	           
	            
              
	            new Thread(new ServerThreadGetGoodsUrlFromClient(client,"ok")).start();  
               
	        }  
	       

		}

}
