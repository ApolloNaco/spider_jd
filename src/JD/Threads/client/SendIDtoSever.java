package JD.Threads.client;

import java.io.IOException;

import JD.client.Client;
import JD.client.Client2;
import JD.client.ClientGetGoodsID;

public class SendIDtoSever extends Thread
{
	private  StringBuffer GoodsUrl=new StringBuffer("");
	private  String ip;
	private int port;

	public SendIDtoSever(StringBuffer goodsUrl,String ip,int port)
	{
		super();
		GoodsUrl = goodsUrl;
		this.ip=ip;
		this.port=port;
	}


	@Override
	public void run()
	{
		//System.out.println("Goodsurl://///"+GoodsUrl.toString().substring(1,1000));
		//System.out.println("GoodsurlLen"+GoodsUrl.length());
		String[] s=GoodsUrl.toString().split(" ");
		System.out.println("----------------------------");
		
		
		//for(String a:s)
		//System.out.println(a);
		
		//System.out.println(GoodsUrl.length()+" ......................"+msg);
		
		
		
		//���ݷֿ�
	
	    System.out.println("���ݱ���Ϊ"+s.length+"�鷢�͸�������");
		for(int i=1;i<=s.length/2000;i++)
		{
			int index=i*2000;
		    s[index]=s[index]+",";
		}
		
		String GoodsUrl2=new String("");
		for(String url:s)
		{
			GoodsUrl2=GoodsUrl2+url+" ";
			
		}
		String[] rmsg=GoodsUrl2.split(",");
		System.out.println("........."+rmsg.length);
		
		
		
		for(int i=0;i<rmsg.length;i++)
		{
			
	        Client2 cr = new Client2(ip, port);
		    try
			{
				System.out.println("������������Ϣ:"+cr.sendAndGet(rmsg[i]));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		
		}
		
	}
}
