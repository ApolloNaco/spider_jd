package JD.Threads.server;


import java.io.PrintStream;  
import java.net.Socket;  
  
/** 
 * 该类为多线程类，用于服务端 
 */  
public class ServerThreadAllocatingGetInfoWork implements Runnable {  
  
	private String rmsg;
    private Socket client = null;  
    public ServerThreadAllocatingGetInfoWork(Socket client){  
        this.client = client;  
  
        rmsg="";
		for(int index=0;index<100 ;index++)
		{
		  rmsg=rmsg+JD.Static.Static.GoodsID.poll()+" ";
		}
    }  
      
    @Override  
	public void run()
	{
		try
		{
			// 获取Socket的输出流，用来向客户端发送数据
			PrintStream out = new PrintStream(client.getOutputStream());
			// 获取Socket的输入流，用来接收从客户端发送过来的数据
			//BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));

			// 接收从客户端发送过来的数据
			//String str = buf.readLine();
			//System.out.println(str);

			out.println(rmsg);
			out.close();
			client.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
  
}  