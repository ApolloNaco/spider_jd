package JD.Threads.server;


import java.io.PrintStream;  
import java.net.Socket;  
  
/** 
 * ����Ϊ���߳��࣬���ڷ���� 
 */  
public class ServerThreadAllocatingGetUrlWork implements Runnable {  
  
	private String rmsg;
    private Socket client = null;  
    public ServerThreadAllocatingGetUrlWork(Socket client,String rmsg){  
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