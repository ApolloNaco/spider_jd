package JD.Threads.server;


import java.io.PrintStream;  
import java.net.Socket;  
  
/** 
 * ����Ϊ���߳��࣬���ڷ���� 
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
			// ��ȡSocket���������������ͻ��˷�������
			PrintStream out = new PrintStream(client.getOutputStream());
			// ��ȡSocket�����������������մӿͻ��˷��͹���������
			//BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));

			// ���մӿͻ��˷��͹���������
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