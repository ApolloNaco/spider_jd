package JD.server.getinfo;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mysql.jdbc.Connection;

import JD.Static.Static;
import JD.Threads.server.ServerThreadAllocatingGetInfoWork;
import JD.Threads.server.ServerThreadAllocatingGetUrlWork;

public class ServerAllocatingGetGoodsInfoWork
{
	public int port;
	
	
	public ServerAllocatingGetGoodsInfoWork(int port)
	{
		super();
		this.port = port;
	}

	public static void GetGoodsIDfromDB() throws SQLException 
	{
		
		 

		    String sql =" select id from jdgoodsid limit 0,1000";
		 
		 
		    PreparedStatement ptmt = (PreparedStatement) JD.Static.Static.conn.prepareStatement(sql);
		 
		    System.out.println("数据库查询..");

		    ResultSet rs = ptmt.executeQuery();

		    int i=0;
		 while(rs.next())
	     {
			 i++;

			 System.out.println(i);
			
	      JD.Static.Static.GoodsID.add(rs.getString(1));	    	

	     }
		 System.out.println("GoodsIDsize:"+JD.Static.Static.GoodsID.size());
		 
	}
		  
	public  void start() throws IOException, SQLException
	{
		// TODO
		System.out.println("分配获取商品信息任务服务器初始化中......");
		GetGoodsIDfromDB();
		ServerSocket server = new ServerSocket(port);
		
		long size=JD.Static.Static.GoodsID.size();
		System.out.println("分配获取商品信息任务服务器开启......"+"待爬ID共:"+JD.Static.Static.GoodsID.size()+"个");
		
		
		Socket client = null;  
		while (true)
		{
			
			
			
			 client = server.accept();
			 size=size-100;
				System.out.println("与客户端连接成功!待爬id:"+size);
			 new Thread(new ServerThreadAllocatingGetInfoWork(client)).start();    
			
			 
			 
		}
	}

}
