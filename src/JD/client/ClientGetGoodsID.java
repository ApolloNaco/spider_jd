package JD.client;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import JD.Threads.client.GetGoodsUrl;
import JD.Threads.client.SendIDtoSever;



public class ClientGetGoodsID
{
	public static StringBuffer GoodsUrl=new StringBuffer("");
	public static String ip;
	public static int  port;
	public static int port2;
	public static int ThreadCount;
	
	public ClientGetGoodsID(String i,int p,int p2,int tc)
	{
		ip=i;
		port=p;
		port2=p2;	
		if(ThreadCount>15)
		ThreadCount=15;
		
		ThreadCount=tc;
		
	}
	
	public static int getPage(String url) 
	{

		int i = 0;
		while (true)
		{
			try
			{
				
              //ʧ�ܴ���
				if (i == 2)
					break;
				HttpClient client = HttpClients.createDefault();
				HttpGet get = new HttpGet("http://list.jd.com/list.html?cat="+url);
				CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);

				// �õ�String
				HttpEntity enity = response.getEntity();
				String body = EntityUtils.toString(enity, "UTF-8");

				//System.out.println(body);
				i++;
				
				
				
				
                //�ѵ�һҳ����Ʒ����
				String regex2 = "data-sku=\"[0-9]+\" ven";
				Pattern p2 = Pattern.compile(regex2);
				Matcher m2 = p2.matcher(body);

						
				while(m2.find())
				{

					String s = m2.group();	
					//System.out.println(s.substring(10, s.length()-1));
					String url2=s.substring(10, s.length()-5);
 					//System.out.println(url2);
 		
					GoodsUrl.append(url2+" ");
			    }
				
				
				
	
				
				
				String regex = "<em>��<b>[0-9]+</b>ҳ";
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(body);
				
	

				//�ж��Ƿ�ʼ���
				if (m.find())
				{
					
					String s = m.group();	
					
					int len =Integer.valueOf(s.substring(8, s.length()-5));
					return len;
			
				}
				else
				{
					return 1;
				}
				
			} catch (Exception e)
			{
				e.printStackTrace();
				continue;
			}
		}
		return 1;

	}
	public static void getGoodsUrl(String url)
	{
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(ThreadCount);
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);// �������������
		cm.setDefaultMaxPerRoute(200);// ��ÿ��ָ�����ӵķ�������ָ����ip�����Դ�������20 socket���з���
		// ������������ұ����Է���������������
		
		//����client
		CloseableHttpClient httpClient = HttpClients.custom()
				.setRetryHandler(new DefaultHttpRequestRetryHandler())// ��������ʱ�����Դ���Ĭ��3��
				.setConnectionManager(cm).build();

		int len=getPage(url);

		//�ӵڶ�ҳ��ʼ��
		for (int i = 2; i <=len; i++)
		{
			HttpGet httpget = new HttpGet("http://list.jd.com/list.html?cat="+url+"&page="+i);
			//System.out.println(httpget.getURI());
			 fixedThreadPool.execute(new GetGoodsUrl(httpClient, httpget));
			 httpget.releaseConnection();
		}

		fixedThreadPool.shutdown();
		while (true)
		{

			if (fixedThreadPool.isTerminated())
			{
				try
				{
					httpClient.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("���е����̶߳�������");
				break;

			}
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	public  void start()
	{
		while(true)
		{
        Client c= new Client(ip, port);
		String msg = null;
		try
		{
			msg = c.sendAndGet("giveme");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("�ͻ����յ�:"+msg);
		
		if(msg.equals("close"))
		{
			try
			{
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("�������..");
			
			StringBuffer str2=new StringBuffer("close");
			Thread ReturnToServer=new Thread(new SendIDtoSever(str2,ip,port2));
			ReturnToServer.start();
			break;
		}
			
		
		getGoodsUrl(msg);
		StringBuffer str2=new StringBuffer(GoodsUrl.toString());
		
	
		
		//�½�һ���̷߳��� �ٶ������˺ܶ� ע��Ҫ����new String
		Thread ReturnToServer=new Thread(new SendIDtoSever(str2,ip,port2));	
		ReturnToServer.start();
		
		
		GoodsUrl.delete(0,GoodsUrl.length());



		}
	}
		
		
	}


