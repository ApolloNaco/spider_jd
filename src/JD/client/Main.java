package JD.client;

public class Main
{
	public static void main(String[] args)
	{
		//3.client port1���ӷַ������������port2�����ռ���Ϣ������ ��4������ָ����ȡ�߳�����
		ClientGetGoodsID c=new ClientGetGoodsID("172.18.112.148",9996,9997,15);
		c.start();
	}

}
