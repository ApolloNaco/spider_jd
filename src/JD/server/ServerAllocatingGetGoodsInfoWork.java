package JD.server;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.ServerSocket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mysql.jdbc.Connection;

import JD.Static.Static;

public class ServerAllocatingGetGoodsInfoWork
{
	
	
	public static void GetGoodsIDfromDB() throws SQLException 
	{
		
		 

		    String sql =" select id from jdgoodsid ";
		 
		 
		    PreparedStatement ptmt = (PreparedStatement) JD.Static.Static.conn.prepareStatement(sql);
		 

		    ResultSet rs = ptmt.executeQuery();

		 while(rs.next())
	     {
			
	      JD.Static.Static.GoodsID.add(rs.getString(1));	    	

	     }
		 System.out.println("GoodsIDsize:"+JD.Static.Static.GoodsID.size());
		 
	}
		  
	public static void main(String[] args) throws IOException, SQLException
	{
		// TODO
		GetGoodsIDfromDB();
		ServerSocket ss = new ServerSocket(9994);
		
		
		int i=0;
		while (true)
		{
			
			System.out.println("�����ȡ��Ʒ��Ϣ����������......");
			
			server s = new server(ss);
			System.out.println("������+"+i);
			
			//���ܵ�������
			String msg = s.accpet();
			

			System.out.println("�������յ�"+msg);
			
			
			if(msg.equals("giveme100"))
			{
				String rmsg="";
				for(int index=0;index<100 ;index++)
				{
				  rmsg=rmsg+JD.Static.Static.GoodsID.poll()+" ";
				}
				System.out.println(rmsg);
			  Thread t= new Thread(s);
			  s.setRms(rmsg);
			  t.start();		
			}
			i++;
		}
	}

}
