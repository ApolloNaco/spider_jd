package JD.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

import JD.Threads.server.HandleGoodsurlMsg;
import JD.Threads.server.InsertGoodUrl;
import JD.server.server;


public class SeverGetGoodInfoFromClient
{
	

	public static void main(String[] args) throws IOException
	{

		ServerSocket ss = new ServerSocket(9993);
		
		InsertGoodUrl insert= new InsertGoodUrl();
		Thread tin= new Thread(insert);
     	tin.start();

		int i = 0;
		while (true)
		{
			//JD.Static.Static.GoodID= new ArrayBlockingQueue<String>(20000);

		
			// 每次都创建一个s对象 就不会出现多线程关闭的错误了 速度稍微慢一点
			server s = new server(ss);
			System.out.println("共连接+" + i);

			// 接受到的数据
			String msg = s.accpet();
			System.out.println("收集商品信息服务器收到msg:"+msg);
			
			Thread t = new Thread(s);
			s.setRms("ok");
			t.start();
			
			//String[] id=new String[]{};
			//String[] id=msg.split(",");
		    Thread HandleMsg=new Thread(new HandleGoodsurlMsg(msg));
		    
		    HandleMsg.start();
			
//			for (String singal : id)
//			{
//				String[] url = new String[]{};
//				url=singal.split(" ");
//				for (int ii=0;ii<url.length;ii++)
//				{
//					
//					//这里必须用put 不然不会notify
//					try
//					{
//						JD.Static.Static.GoodID.put(url[ii]);
//					} catch (InterruptedException e)
//					{
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
////				
//			}
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
