package JD.Threads.server;

import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import JD.Static.Static;  
  
/** 
 * ����Ϊ���߳��࣬���ڷ���� 
 */  
public class ServerThreadGetGoodsInfo implements Runnable {  
  
	private String rmsg;
    private Socket client = null;  
    public ServerThreadGetGoodsInfo(Socket client,String rmsg){  
        this.client = client;  
        this.rmsg=rmsg;
    }  
    
    public void insert(String str) throws SQLException
    {
    	
    	 
    	  String sql = "insert into goods(Crawling_time,Goods_ID,Goods_Name,"
    	  		                       + "Goods_params,self_support,Return_Support,"
    	  		                       + "Goods_brand,classification,Goods_img,"
    	  		                       + "comment_count,comment_goodrate,Goods_feature,"
    	  		                       + "Goods_price,Shop_Name,Shop_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		
		
    	
    	  JSONObject j =new JSONObject(str);
    	  
    	  String Crawling_time=j.getString("Crawling_time");
		    System.out.println("1.Crawling_time:"+Crawling_time);
		    
			String Goods_ID=j.getString("Goods_ID");
			System.out.println("2.Goods_ID:"+Goods_ID);
			
			String Goods_Name=j.getString("Goods_Name");
			System.out.println("3.Goods_Name:"+Goods_Name);
			
			
			String Goods_params=j.getString("Goods_params");
			System.out.println("4.Goods_params:"+Goods_params);
			
			Boolean self_support=j.getBoolean("self_support");
			System.out.println("5.self_support:"+ self_support);
			
			Boolean Return_Support=j.getBoolean("Return_Support");
			System.out.println("6.Return_Support:"+ Return_Support);
			
			String Goods_brand=j.getString("Goods_brand");
			System.out.println("7.Goods_brand:"+ Goods_brand);
			
			String classification=j.getString("classification");
			System.out.println("8.classification:"+classification);
			
			String Goods_img=j.getString("Goods_img");
			System.out.println("9.Goods_img:"+Goods_img);
			
			String comment_count=j.getString("comment_count");
			System.out.println("10.comment_count:"+comment_count);
			
			String comment_goodrate=j.getString("comment_goodrate");
			System.out.println("11.comment_goodrate:"+ comment_goodrate);
			
			String Goods_feature=j.getString("Goods_feature");
			System.out.println("12.Goods_feature:"+Goods_feature);
			
			String Goods_price=j.getString("Goods_price");
			System.out.println("13.Goods_price:"+Goods_price);
			
			String Shop_Name=j.getString("Shop_Name");				
			System.out.println("14.Shop_Name:"+Shop_Name);
			
			String Shop_ID=j.getString("Shop_ID");
			System.out.println("15.Shop_ID:"+Shop_ID);
		
		synchronized (Static.conn)
		{
			

			PreparedStatement ptmt = (PreparedStatement) Static.conn.prepareStatement(sql);
			ptmt.setString(1, Crawling_time);
			ptmt.setString(2, Goods_ID);
			ptmt.setString(3, Goods_Name);
			ptmt.setString(4, Goods_params);
			ptmt.setBoolean(5, self_support);
			ptmt.setBoolean(6, Return_Support);
			ptmt.setString(7, Goods_brand);
			ptmt.setString(8, classification);
			ptmt.setString(9, Goods_img);
			ptmt.setString(10, comment_count);
			ptmt.setString(11, comment_goodrate);
			ptmt.setString(12, Goods_feature);
			ptmt.setString(13, Goods_price);
			ptmt.setString(14, Shop_Name);
			ptmt.setString(15, Shop_ID);

			ptmt.execute();
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
			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));

			// ���մӿͻ��˷��͹���������
			String str = buf.readLine();
			
			System.out.println("��������ȡ������"+str);
			
			insert(str);
			System.out.println("���ݿ�ɹ�����");
		  
			
			if(str.equals("close"))
			{
				
				
				client.close();
				Exception e=new Exception("close");
				throw e;
			}
			
		

			out.println(rmsg);
			
			out.close();
			client.close();
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
  
}  