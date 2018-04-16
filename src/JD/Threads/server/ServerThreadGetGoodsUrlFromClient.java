package JD.Threads.server;

import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;  
  
/** 
 * ����Ϊ���߳��࣬���ڷ���� 
 */  
public class ServerThreadGetGoodsUrlFromClient implements Runnable {  
  
	private String rmsg;
    private Socket client = null;  
    public ServerThreadGetGoodsUrlFromClient(Socket client,String rmsg){  
        this.client = client;  
        this.rmsg=rmsg;
    }  
      
    @Override  
	public void run()
	{
		try
		{
			// ��ȡSocket���������������ͻ��˷�������
			PrintStream out = new PrintStream(client.getOutputStream());
			// ��ȡSocket�����������������մӿͻ��˷��͹���������
			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));

			// ���մӿͻ��˷��͹���������
			String str = buf.readLine();
			
			//System.out.println("��������ȡ������"+str);
			
			
			 Thread HandleMsg=new Thread(new HandleGoodsurlMsg(str));
			    
			 HandleMsg.start();
			

			out.println(rmsg);
			
			
			client.close();
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
  
}  