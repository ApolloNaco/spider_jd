package JD.Threads.server;

public class HandleGoodsurlMsg extends Thread
{
	private String msg;
	
	
	
	 public HandleGoodsurlMsg(String msg)
	{
		super();
		this.msg = msg;
	}



	@Override
	public void run()
	{
		
		 String[] id=msg.split(",");
			
		 int count=0;
			for (String singal : id)
			{
				String[] url = new String[]{};
				url=singal.split(" ");
				for (int ii=0;ii<url.length;ii++)
				{
					
					//���������put ��Ȼ����notify
					try
					{
						JD.Static.Static.GoodID.put(url[ii]);
						count++;
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				System.out.println("�ռ�ID�������ռ�����ƷID:"+count+"��ID");
				
			}
	
	}

}
