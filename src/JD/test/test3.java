package JD.test;

import JD.Threads.client.SendIDtoSever;

public class test3
{

	public static void main(String[] args)
	{
		while(true)
		{
			
		Thread ReturnToServer=new Thread(new SendIDtoSever(new StringBuffer("aaaaaaaa"),"127.0.0.1",9997));
		ReturnToServer.start();
		
		}
	}
}
