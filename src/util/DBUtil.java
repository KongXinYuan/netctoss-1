package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;


/**
 * ʹ�����ӳؼ����������ݿ�����
 * @author ZengHongfu
 *
 */
public class DBUtil {
	//���ݿ����ӳ�
	private static BasicDataSource ds;
	//Ϊ��ͬ�̹߳�������
	private static ThreadLocal<Connection> tl;
	
	static{
		try {
			//���������ļ�
			Properties prop=new Properties();
			InputStream is=DBUtil.class.getClassLoader().getResourceAsStream("util/config.properties");
			prop.load(is);
			is.close();
			
			//��ʼ�����ӳ�
			ds=new BasicDataSource();
			//��������
			ds.setDriverClassName(prop.getProperty("driver"));
//			ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			//����url
			ds.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
			//�������ݿ��û���
			ds.setUsername("system");
			//�������ݿ�����
			ds.setPassword("362423");
			//��ʼ���ӳش�С
			ds.setInitialSize(1);
			//�����������
//			ds.setMaxTotal(2);
			//���õȴ�ʱ��
//			ds.setMaxWaitMillis(Integer.parseInt(prop.getProperty("maxwait")));
			//��������������
//			ds.setMaxIdle(Integer.parseInt(prop.getProperty("maxidle")));
			//������С��������
//			ds.setMinIdle(Integer.parseInt(prop.getProperty("minidle")));
			
			//��ʼ���̱߳���
			tl=new ThreadLocal<Connection>();		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException{
		/**
		 * ͨ�����ӳػ�ȡһ����������
		 */
		Connection conn=ds.getConnection();
		tl.set(conn);
		return conn;
	}
	
	public static void closeConnection(){
		try {
			Connection conn=tl.get();
			if(conn!=null){
				//�ָ�����Ϊ�Զ��ύ����
				conn.setAutoCommit(true);
				/**
				 * ͨ�����ӳػ�ȡ��Connection
				 * ��close��������ʵ���ϲ�û�н����ӹر�
				 * ���ǽ����ӹ黹
				 */
				conn.close();
				tl.remove();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

