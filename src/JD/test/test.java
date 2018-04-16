package JD.test;

import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.fabric.xmlrpc.base.Data;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

public class test
{
	public static JSONArray ja= new JSONArray();
	public static JSONObject j= new JSONObject();
	
	public static void main(String args[]) throws ClientProtocolException, IOException
	{
		
		//ja.put(index, value)
		
		//1.爬取时间
		Date date =new Date();
		String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
		System.out.println("1.爬取时间"+time);
		 j.put("Crawling_time", time);
		 
		 
		//2.商品ID	 
		 j.put("Goods_ID", "id");
		 System.out.println("2.商品ID:"+"id");
		
		//3 商品名称
		
		 String body="";
		 
		
		 //同一client不能多次请求
		 //HttpClient httpClient=HttpClients.createDefault();
		 long t1 =System.currentTimeMillis();
		 PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(200);// 设置最大连接数
			cm.setDefaultMaxPerRoute(200);// 对每个指定连接的服务器（指定的ip）可以创建并发20 socket进行访问
			// 上面的设置是我本机对服务器最大的连接数
			long t2=System.currentTimeMillis();
			System.out.println(t2-t1);
		
			CloseableHttpClient httpClient = HttpClients.custom()
					.setRetryHandler(new DefaultHttpRequestRetryHandler())// 设置请求超时后重试次数默认3次
					.setConnectionManager(cm).build();
			

			int i=0; 
  	   while(true)
		 {
			 
			 
			 i++;
			 //HttpClient httpClient=HttpClients.createDefault();
	   HttpClient httpClient2=HttpClients.createDefault();
		HttpGet get =new HttpGet("http://item.jd.com/10002932080.html");//10691283909.html");
		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		get.setHeader(":authority","item.jd.com");
		get.setHeader(":method","GET");
		get.setHeader(":path","/10071043028.html");
		get.setHeader(":scheme","https");
		get.setHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("accept-encoding","gzip, deflate, sdch");
		get.setHeader("accept-language","zh-CN,zh;q=0.8");
		get.setHeader("cache-control","max-age=0");
		get.setHeader("if-modified-since","Thu, 11 May 2017 06:20:55 GMT");
		get.setHeader("cookie","n=0; __utmz=122270672.1493804802.1.1.utmcsr=jd.com|utmccn=(referral)|utmcmd=referral|utmcct=/brand.aspx; ipLoc-djd=1-72-2799-0; mt_xid=V2_52007VwMQUl5dUVIbTilcVzILGlJeDE5ZFxwfQABnBkdOVQpaWgNLS1hVYgdBU10IAFovShhcA3sCFk5cUENZHkIZWA5iCiJQbVhiWh5PHVsMZwsQYl1eVl4%3D; ipLocation=%u5317%u4EAC; areaId=1; _jrda=1; wlfstk_smdl=dqen7rww09dufrizjc17ta9fqp0ebgpp; unpl=V2_ZzNtbUNQExF2WBJXfxteA2IAEQ5KU0FHcAAWBCwcWgUzUxZfclRCFXMUR1BnGVwUZwYZWEZcRhZFCEdkex5fDGQzEFtGVEMcdQF2ZHgZbA1XAxBdQVJAHXEPdmR9HWw1s5a5hcnAEVQ30fbxrbPyNWAGFVtLXkYRRQl2VUtSMgQqAxBdQVJAHXEPdlVLGg%3d%3d; CCC_SE=ADC_owMeSiInJACA2ioAPFmVTK9Z9t9YR6LEpdEZxAshhLji%2fZPJ8rWFVfWKyoK8kRnAxwDtHjDERNQTODIXZ37iEmxPR24uL5U5OAIa5ox4YXiVoIw5Dbt79qlvbrYh69w3EWOKIxtIuDSNEygujH6ok1NfTLS6OpsZjgdoHiSueGFZcWIR9bOeqIo1DgN0i5pfwf7LyNXOb0nRvOPvIE%2bH32hkhkzDiy4wjXeIbK0Sm24gxiWLtIDebr%2fNEiYJdVt%2b1X91MWk%2bSonNwteXKXXAxeqy11IHS37OLphTdvzaV2yJWHx5B8RFL7NfK%2bVsbDlYzcW4KWRZAaBJLTnLCBmdz3j3qIA7L5pZCtR8LnhNXDIueUjDKZvL2Twt4zgUQntH9ELsdvl3IzdS6%2f%2fSaY9V%2bo3H%2b3Th%2fwscPXPAbxRowg1mOpnJGJgin5acWooF6nOMW1g3pXWzKD4Oo8srLdH4j%2f7K4oTQZVOd8%2ftAR%2bfTyOT2hHXTlqcjP4j4EbxI3N67MVj%2fa6pwF9Pgof%2flG4LimKiyM5HZD7LEqVaqbyGh9Wsy2OjH0Q48IEsIrTaIMqMZftSOFk0hxBFATcP9NKZ2sWfY8OJ7%2bBwoJjAP0vGkvmYRhnun3l%2b7c5SuDjWGw%2fRu; __jda=122270672.131242956.1473433603.1494483461.1494484911.35; __jdb=122270672.1.131242956|35.1494484911; __jdc=122270672; __jdv=122270672|c.duomai.com|t_16282_37521818|uniongou|06a52ae25337422b953c49aaf471ea53|1494484910532; __jdu=131242956; 3AB9D23F7A4B3C9B=QHYZT35YOWZBXZ7W4K5I6WFDMFCTYF3M2T4FZNXKHHIBRJXRAFDI7RS4MPWNPL");
		CloseableHttpResponse response = (CloseableHttpResponse) httpClient2.execute(get);
		HttpEntity entity = response.getEntity();
		body = EntityUtils.toString(entity, "UTF-8");
		//System.out.println(body);
		System.out.println("请求了"+i+":"+body.length());
		if(i==15)
			break;
		if(body.length()>1000)
			break;
		
		EntityUtils.consume(entity);
		get.releaseConnection();
		response.close();
	
	 }
//		if(body.length()>500)
//			break;
//		 
		 System.out.println("请求次数:"+i);
		
		String regex = "<title>.{0,300}</title>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(body);
		if (m.find())
		{
		
			String s = m.group();
			s=s.substring(7,s.length()-24);
			System.out.println(s);
			
			j.put("Goods_Name",s);
			System.out.println("3.商品名称:"+s);
			

		}
		else
		{
			j.put("Goods_Name","null");
			System.out.println("3.商品名称:"+"null");
		}
		
		//15 商品现价
				//HttpClient httpClient4=HttpClients.createDefault();
				HttpGet get4 =new HttpGet("https://p.3.cn/prices/mgets?skuIds=J_4294168%2CJ_3312381%2CJ_1353403314&pdbp=0&pdtk=&pdpin=&pduid=131242956&source=list_pc_front&_=1494422447061");
				CloseableHttpResponse response4 = (CloseableHttpResponse) httpClient.execute(get4);
				HttpEntity entity4 = response4.getEntity();
				String body4 = EntityUtils.toString(entity4, "UTF-8");
				System.out.println(body4);
				
				String regex7 = "(id\":\"J_[0-9]*)|(\"p\":\"[0-9]*\\.[0-9]*)";
				Pattern p7 = Pattern.compile(regex7);
				Matcher m7= p7.matcher(body4);
				while (m7.find()) {
				
					String str=m7.group();	
					
					
					if(str.startsWith("id"))
						System.out.println(str.substring(7));
					
					if(str.startsWith("\"p"))
						System.out.println("15.价格"+str.substring(5));
					
				
					
				}
		
		
		
	
		//4.商品参数
		int s=body.indexOf("parameter2 p-parameter-list");
		int end=body.indexOf("</ul>",s);
		
		if (s == -1 || end == -1)
			j.put("Goods_params", "null");
		else
		{
			String info = body.substring(s + 30, end);
			System.out.println("4.商品参数:" + info);
			j.put("Goods_params", info);
		}
		
		
		
		//5.是否自营
		String regex2 = "<span>JD</span>自营";
		Pattern p2 = Pattern.compile(regex2);
		Matcher m2 = p2.matcher(body);
		if(m2.find())
		{
		
			
			System.out.println("5.是自营");
			j.put("self_support", true);

		}
		else
		{
			System.out.println("6.不是自营");
			j.put("self_support", false);
		}
		
		
		
		//6.是否支持7天
		if(body.indexOf("支持7天无理由退货")!=-1)
		{
			System.out.println("6.支持。。");
			j.put("Return_Support", true);
		}
		else
		{
			
			System.out.println("6.不支持。。");
			j.put("Return_Support", false);
		}
		
		
		//7.品牌
		String regex3 = "<li title=.{0,40}>";
		Pattern p3 = Pattern.compile(regex3);
		Matcher m3 = p3.matcher(body);
		if(m3.find())
		{
		
			String s3 = m3.group();
			String brand=s3.substring(11, s3.length()-2);
			System.out.println("7.品牌:"+brand);
			j.put("Goods_brand", brand);

		}else
		{
			j.put("Goods_brand", "null");
		}
		
		//8.商品分类
//		String regex_classification = "clstag=\"shangpin\\|keycount\\|product\\|mbNav.+</a>";
//		Pattern p_classification = Pattern.compile(regex_classification);
//		Matcher m_classification = p_classification.matcher(body);
//		StringBuffer classification = new StringBuffer("");
//		while (m_classification.find()) {
//			classification.append(m_classification.group().substring(43,m_classification.group().length()-4)+">");
//		}
//	
//		System.out.println("8商品分类:"+classification.toString());
		//System.out.println("商品分类:"+classification.toString().substring(0,classification.toString().length()-1));
      //  j.put("classification", classification.toString().substring(0,classification.toString().length()-1));
		
		//8.商品分类
		StringBuffer productclassification = new StringBuffer("");
		int productclassification_start = 0;
		int productclassification_end = 0;
		boolean flag= true;
		while (true) {

			productclassification_start = body.indexOf("mbNav", productclassification_end);
			productclassification_end = body.indexOf("</a>", productclassification_start);

			if (productclassification_start == -1) {
				flag=false;
				break;
			}
			productclassification
					.append(body.substring(productclassification_start + 9, productclassification_end) + ">");

		}
		if(flag==true)
		{
			String classification=productclassification.toString().substring(0, productclassification.toString().length() - 1);
		System.out.println("商品参数:"+classification );
		j.put("classification",classification );
		
		}else
		{
			j.put("classification","null" );
		}
		
		
        
      //9.img
//      		StringBuffer img = new StringBuffer("");
//      		int start = 0;
//      		int end1 = 0;
//      		while(true){
//      			
//      			start = body.indexOf("<li ><img alt=",end1);
//      			end1 = body.indexOf("</li>",start);
//      			
//      			if(start==-1){
//      				break;
//      			}
//      			img.append(body.substring(start,end1));
//      			
//      		}
//      		System.out.println("9.商品展示图片:"+img.toString());
      		
      	
		String imgurl="im";
		System.out.println(body);
		String regex9 = "<li.*>.*\\.jpg";
		Pattern p9 = Pattern.compile(regex9);
		Matcher m9 = p9.matcher(body);
		while(m9.find())
		{
		
			String s9 = m9.group();
			String img=s9.substring(11, s9.length()-2);
		//	System.out.println("imgs:"+img);
			
			int startn=img.indexOf("src='");
			int endn=img.indexOf("'",startn+5);
			System.out.println(img.substring(startn+7, endn));
	

		}
		

	
      		
      		
      		
      		
      		
      		
      	//10 评论
    		
    		//HttpClient httpClient3=HttpClients.createDefault();
    		HttpGet get3 =new HttpGet("https://club.jd.com/comment/productPageComments.action?productId=10005782293&score=0&sortType=5&page=0&pageSize=1&isShadowSku=0");
    		CloseableHttpResponse response3 = (CloseableHttpResponse) httpClient.execute(get3);
    		HttpEntity entity2 = response3.getEntity();
    		String body2 = EntityUtils.toString(entity2, "UTF-8");
    		System.out.println(body2);
    		int yh=body2.indexOf("commentCountStr\":\"");
    		int yh2=body2.indexOf("\"",yh+19);
    		System.out.println("commentCount,.....................:"+yh+","+yh2);//body2.substring(yh+20,yh2));
    	   System.out.println(body2.substring(yh+18,yh2));
    		
    	   
    	 int yh3 =body2.indexOf("goodRate\"");
   	    int yh4 =body2.indexOf("\"",yh3+9);
   	    
   	    System.out.println("goodRate:................................"+body2.substring(yh3,yh4));
    		
    			String regex6 = "(\"commentCountStr\":\".{0,7})|(\"goodRate\":[0-9].[0-9]*)|(,\"name\":.{0,20},\"s)";
    			Pattern p6 = Pattern.compile(regex6);
    			Matcher m6= p6.matcher(body2);
    			String aaa="";
    			while (m6.find()) {
    				
    				
    				String str=m6.group();
    				if(str.startsWith("\"comment"))
    				{
    				 // String comment_count=str.substring(19);
    				  System.out.println("10.评论总数"+str);
    				  
    				 // j.put("comment_count",comment_count );
    				}
    				
    				if(str.startsWith("\"good"))
    				{
    					
    					String comment_goodrate=str.substring(11);
    					System.out.println("11.好评率"+comment_goodrate);
    					
    					j.put("comment_goodrate",comment_goodrate );
    				}
    				
    				if(str.startsWith(",\"name"))
    				{
    					String Goods_feature=str.substring(9,str.length()-4);
    					System.out.println(Goods_feature);
    					if(str.indexOf("pin")==-1)
    						aaa=aaa+Goods_feature+",";
                        j.put("Goods_feature", Goods_feature);
                        
    				}
    				
    				
    				
    			}
    			System.out.println("aaa:"+aaa);
    		
      		
      		
      		
		//13.店铺 name
		String regex4 = "(clstag=\"shangpin\\|keycount\\|product\\|dianpuname1\">.*</a>)";
		Pattern p4 = Pattern.compile(regex4);
		Matcher m4 = p4.matcher(body);
		Boolean flag_shop_name=false;
		while(m4.find())
		{
			flag_shop_name=true;
			String s4 = m4.group();
			
			System.out.println("13.店铺:"+s4.substring(47, s4.length()-4));
			
			j.put("Shop_Name", s4.substring(47, s4.length()-4));

		}
		if(flag_shop_name==false)
		{
			j.put("Shop_Name", "null");

		}
		//店铺id
		int s3=body.indexOf("data-vid=\"");
		int e3=body.indexOf("\"",s3+11);
		if(s3!=-1||e3!=-1)
		{
		  System.out.println("店铺id:"+body.substring(s3+10,e3));
		  j.put("Shop_ID",body.substring(s3+10,e3));
		}
		else
		{
			j.put("1Shop_ID","null");
		}
		
	
		

	
		System.out.println(j.toString().length());
		
		//取数据
		
		String Crawling_time=j.getString("Crawling_time");
		String Goods_ID=j.getString("Goods_ID");
		String Goods_Name=j.getString("Goods_Name");
		String Goods_params=j.getString("Goods_params");
		Boolean self_support=j.getBoolean("self_support");
		Boolean Return_Support=j.getBoolean("Return_Support");
		String Goods_brand=j.getString("Goods_brand");
		String classification=j.getString("classification");
		//String Goods_img=j.getString("Goods_img");
		String comment_count=j.getString("comment_count");
		String comment_goodrate=j.getString("comment_goodrate");
		String Goods_feature=j.getString("Goods_feature");
		//String Goods_price=j.getString("Goods_price");
		String Shop_Name=j.getString("Shop_Name");
		String Shop_ID=j.getString("Shop_ID");

		}
		
		
//		//15 商品现价
//		HttpClient httpClient4=HttpClients.createDefault();
//		HttpGet get4 =new HttpGet("https://p.3.cn/prices/mgets?skuIds=J_4294168%2CJ_3312381%2CJ_1353403314&pdbp=0&pdtk=&pdpin=&pduid=131242956&source=list_pc_front&_=1494422447061");
//		CloseableHttpResponse response4 = (CloseableHttpResponse) httpClient4.execute(get4);
//		HttpEntity entity4 = response4.getEntity();
//		String body4 = EntityUtils.toString(entity4, "UTF-8");
//		System.out.println(body4);
//		
//		String regex7 = "(id\":\"J_[0-9]*)|(\"p\":\"[0-9]*\\.[0-9]*)";
//		Pattern p7 = Pattern.compile(regex7);
//		Matcher m7= p7.matcher(body4);
//		while (m7.find()) {
//		
//			String str=m7.group();	
//			
//			
//			if(str.startsWith("id"))
//				System.out.println(str.substring(7));
//			
//			if(str.startsWith("\"p"))
//				System.out.println("15.价格"+str.substring(5));
//			
//		
//			
//		}
		
		
		
		
		//
		//5 是否自营
		
		
		
		
		
		
		
		
		
		
		
		
		
//		https://p.3.cn/prices/mgets?skuIds=J_2342601%2CJ_3312381
//		//1 
//		HttpClient httpClient=HttpClients.createDefault();
//		HttpGet get =new HttpGet("https://item.jd.com/2342601.html");
//		CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(get);
//		HttpEntity entity = response.getEntity();
//		InputStream in = entity.getContent();
//		String body = EntityUtils.toString(entity, "UTF-8");
//		System.out.println(body);
	
}