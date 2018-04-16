package JD.server.getinfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

import JD.Threads.server.HandleGoodsurlMsg;
import JD.Threads.server.InsertGoodUrl;
import JD.Threads.server.ServerThreadGetGoodsInfo;


public class SeverGetGoodInfoFromClient
{
	public int port;
	

	public SeverGetGoodInfoFromClient(int port)
	{
		super();
		this.port = port;
	}


	public  void start() throws IOException
	{

		ServerSocket server = new ServerSocket(port);
		System.out.println("�ռ���Ʒ��Ϣ����������....");
		//�������ݿ��߳�
		InsertGoodUrl insert= new InsertGoodUrl();
		Thread tin= new Thread(insert);
     	tin.start();

		int i = 0;
		
		while (true)
		{
			//JD.Static.Static.GoodID= new ArrayBlockingQueue<String>(20000);

			Socket client = null;
			client = server.accept();
			System.out.println("������+" + i);
			
			new Thread(new ServerThreadGetGoodsInfo(client, "ok")).start();			

			//System.out.println("worng:"+JD.Static.ClientStatic.wrong[1]);

		
			i++;
		}
	}

}
