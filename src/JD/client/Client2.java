package JD.client;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;  
import java.net.SocketTimeoutException;  
  
public class Client2
{
	private String ip;
	private int port;
	
	public Client2(String ip, int port)
	{
		super();
		this.ip = ip;
		this.port = port;
	}

	public String sendAndGet(String msg) throws IOException
	{
		// �ͻ��������뱾����20006�˿ڽ���TCP����
		Socket client = new Socket(ip, port);
		client.setSoTimeout(10000);

		PrintStream out = new PrintStream(client.getOutputStream());
		// ��ȡSocket�����������������մӷ���˷��͹���������
		BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));	
		out.println(msg);

		try
		{
			// �ӷ������˽��������и�ʱ�����ƣ�ϵͳ���裬Ҳ�����Լ����ã������������ʱ�䣬����׳����쳣
			String smsg = buf.readLine();
			return smsg;
		} catch (SocketTimeoutException e)
		{
			System.out.println("Time out, No response");
		}finally
		{
			
			if (client != null)
			{
				System.out.println("client close");
				// ������캯�������������ӣ���ر��׽��֣����û�н��������ӣ���Ȼ���ùر�
				client.close(); // ֻ�ر�socket������������������Ҳ�ᱻ�ر�
			}
			
		}
		
		return null;

		
	}
}