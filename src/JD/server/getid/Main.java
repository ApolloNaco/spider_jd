package JD.server.getid;


import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class Main
{
	public static void main(String[] args) throws ClientProtocolException, InterruptedException, IOException
	{
		//1.SeverAllocatingGetIDWork 
	
		SeverAllocatingGetIDWork sID = new SeverAllocatingGetIDWork(9996);
		sID.start();
	
	
		
		
		
	}

}
