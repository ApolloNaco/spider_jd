package JD.Static;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Static
{
	
	//ǰ������������
	public static BlockingQueue<String> filter1 = new  ArrayBlockingQueue<String>(10000);
	public static HashSet<String> filter2 =new HashSet<>();
	//�������������url
	public static HashSet<String> GoodsUrl =new HashSet<>();
	public static ConcurrentLinkedQueue<String> Pageurl= new ConcurrentLinkedQueue<>();
	
	
	public static BlockingQueue<String> GoodID =new ArrayBlockingQueue<String>(20000);
	
	public static Connection conn=getConn();
    public static Connection getConn()
	{

    	
		 String driver = "com.mysql.jdbc.Driver";
		    //��ʾ����Ҫ�ַ���
		    String url = "jdbc:mysql://localhost:3306/jd?useUnicode=true&characterEncoding=utf8";
		    String username = "root";
		    String password = "root";
		    Connection conn = null;
		    try {
		        Class.forName(driver); //classLoader,���ض�Ӧ����
		        conn = (Connection) DriverManager.getConnection(url, username, password);
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    } catch (SQLException e) {
		        e.printStackTrace();
		        

		    }
		    return conn;
	}
    
    
    //
    public static ConcurrentLinkedQueue<String> GoodsID= new ConcurrentLinkedQueue<>();
    

}
