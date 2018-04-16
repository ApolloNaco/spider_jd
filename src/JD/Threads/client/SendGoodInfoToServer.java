package JD.Threads.client;

import java.io.IOException;

import JD.client.Client;
import JD.client.ClientGetGoodsID;

public class SendGoodInfoToServer extends Thread
{
	private  String goodsInfo;
	private  String ip;
	private int port;

	public SendGoodInfoToServer(String goodsInfo,String ip,int port)
	{
		super();
		this.goodsInfo = goodsInfo;
		this.ip=ip;
		this.port=port;
	}


	@Override
	public void run()
	{
		//System.out.println("Goodsurl://///"+GoodsUrl.toString().substring(1,1000));
		
		
	        Client cr = new Client(ip, port);
		    try
			{
				System.out.println("服务器返回信息:"+cr.sendAndGet(goodsInfo));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		
		
		
	}
}
