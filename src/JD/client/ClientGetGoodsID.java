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
				
              //失败处理
				if (i == 2)
					break;
				HttpClient client = HttpClients.createDefault();
				HttpGet get = new HttpGet("http://list.jd.com/list.html?cat="+url);
				CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);

				// 得到String
				HttpEntity enity = response.getEntity();
				String body = EntityUtils.toString(enity, "UTF-8");

				//System.out.println(body);
				i++;
				
				
				
				
                //把第一页的商品加入
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
				
				
				
	
				
				
				String regex = "<em>共<b>[0-9]+</b>页";
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(body);
				
	

				//判断是否开始入队
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
		cm.setMaxTotal(200);// 设置最大连接数
		cm.setDefaultMaxPerRoute(200);// 对每个指定连接的服务器（指定的ip）可以创建并发20 socket进行访问
		// 上面的设置是我本机对服务器最大的连接数
		
		//建立client
		CloseableHttpClient httpClient = HttpClients.custom()
				.setRetryHandler(new DefaultHttpRequestRetryHandler())// 设置请求超时后重试次数默认3次
				.setConnectionManager(cm).build();

		int len=getPage(url);

		//从第二页开始爬
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
				
				System.out.println("所有的子线程都结束了");
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
		System.out.println("客户端收到:"+msg);
		
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
			System.out.println("任务完成..");
			
			StringBuffer str2=new StringBuffer("close");
			Thread ReturnToServer=new Thread(new SendIDtoSever(str2,ip,port2));
			ReturnToServer.start();
			break;
		}
			
		
		getGoodsUrl(msg);
		StringBuffer str2=new StringBuffer(GoodsUrl.toString());
		
	
		
		//新建一个线程返回 速度提升了很多 注意要这样new String
		Thread ReturnToServer=new Thread(new SendIDtoSever(str2,ip,port2));	
		ReturnToServer.start();
		
		
		GoodsUrl.delete(0,GoodsUrl.length());



		}
	}
		
		
	}


