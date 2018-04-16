package JD.client;

public class Main2
{
	public static void main(String[] args)
	{
		ClientGetGoodsInfo c=new ClientGetGoodsInfo("127.0.0.1", 9996, 9993,1);
		c.start();
	}

}
