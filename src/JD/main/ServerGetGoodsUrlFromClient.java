package JD.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

import JD.Threads.server.InsertGoodUrl;
import JD.server.server;


public class ServerGetGoodsUrlFromClient
{
	

	public static void main(String[] args) throws IOException
	{

		ServerSocket ss = new ServerSocket(9995);
		
		InsertGoodUrl insert= new InsertGoodUrl();
		Thread tin= new Thread(insert);
     	tin.start();

		int i = 0;
		while (true)
		{
			//JD.Static.Static.GoodID= new ArrayBlockingQueue<String>(20000);

		
			// ÿ�ζ�����һ��s���� �Ͳ�����ֶ��̹߳رյĴ����� �ٶ���΢��һ��
			server s = new server(ss);
			System.out.println("������+" + i);

			// ���ܵ�������
			String msg = s.accpet();
			System.out.println("�ռ���Ϣ�������յ�msg");
			
			Thread t = new Thread(s);
			s.setRms("ok");
			t.start();
			
			//String[] id=new String[]{};
			String[] id=msg.split(",");
			
			for (String singal : id)
			{
				String[] url = new String[]{};
				url=singal.split(" ");
				for (int ii=0;ii<url.length;ii++)
				{
					
					//���������put ��Ȼ����notify
					try
					{
						JD.Static.Static.GoodID.put(url[ii]);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
//				
			}
			//.Static.Static.GoodID=null;
//	
//			
////				Thread t = new Thread(s);
////				s.setRms("ok");
////				t.start();
//			
			i++;
		}
	}

}