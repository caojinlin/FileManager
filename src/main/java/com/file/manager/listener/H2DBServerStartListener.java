package com.file.manager.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.sasl.SaslServer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.file.manager.util.ConfigHelper;
import org.h2.tools.Server;

import com.file.manager.util.*;

 
 /**
 * @ClassName: H2DBServerStartListener
 * @Description: 用于启动H2数据库服务的监听器，在应用系统初始化时就启动H2数据库的服务
 * @date: 2014-12-20 下午11:43:39
 *
 */ 
 public class H2DBServerStartListener implements ServletContextListener {
 
	 
     //H2数据库服务器启动实例
     private Server server;
     /* 
      * Web应用初始化时启动H2数据库
      */
     public void contextInitialized(ServletContextEvent sce) {
         try {  
        	 
             System.out.println("正在启动h2数据库...");
             //使用org.h2.tools.Server这个类创建一个H2数据库的服务并启动服务，由于没有指定任何参数，那么H2数据库启动时默认占用的端口就是8082
             server = Server.createTcpServer().start(); 
             System.out.println("h2数据库启动成功...");
             System.out.println("正在创建业务表结构...");
             createTable();
             System.out.println("创建业务表成功...");
         } catch (SQLException e) {  
             System.out.println("启动h2数据库出错：" + e.toString());  
             e.printStackTrace();  
             throw new RuntimeException(e);  
         }  
     }
 
     /* 
      * Web应用销毁时停止H2数据库
      */
     public void contextDestroyed(ServletContextEvent sce) {
         if (this.server != null) {
             // 停止H2数据库
             this.server.stop();
             this.server = null;
         }
     }
     
     private void createTable(){
    	 Statement stmt = null;  
         Connection conn = null;  
         try {  
             Class.forName(ConfigHelper.getValue("db_driver"));
             conn = DriverManager.getConnection(  
                     ConfigHelper.getValue("db_url"),
                     ConfigHelper.getValue("db_username"),  
                     ConfigHelper.getValue("db_password"));  
             // add application code here  
             stmt = conn.createStatement(); 
             
             String createSql = "";
             String createIndexSql = "";
             String insertSql = "";
             //创建业务表Upload_Info以及索引
             createSql = ConfigHelper.getValue("create_upload_info_sql");
             stmt.executeUpdate(createSql);  
             createIndexSql = ConfigHelper.getValue("index_upload_info_uploadid_sql");
             stmt.executeUpdate(createIndexSql);
             
             //创建业务表Upload_Detail以及索引
             createSql = ConfigHelper.getValue("create_upload_detail_sql");
             stmt.executeUpdate(createSql);  
             createIndexSql = ConfigHelper.getValue("index_upload_detail_uploadid_sql");
             stmt.executeUpdate(createIndexSql);
             createIndexSql = ConfigHelper.getValue("index_upload_detail_selected_sql");
             stmt.executeUpdate(createIndexSql);
             
             //创建业务表Upload_Analysis以及索引
             createSql = ConfigHelper.getValue("create_upload_analysis_sql");
             stmt.executeUpdate(createSql);
             createIndexSql = ConfigHelper.getValue("index_upload_analysis_uploadid");
             stmt.executeUpdate(createIndexSql);
             createIndexSql = ConfigHelper.getValue("index_upload_analysis_subid");
             stmt.executeUpdate(createIndexSql);
             createIndexSql = ConfigHelper.getValue("index_upload_analysis_classicstatus");
             stmt.executeUpdate(createIndexSql);
             createIndexSql = ConfigHelper.getValue("index_upload_analysis_selected");
             stmt.executeUpdate(createIndexSql);
             
             //创建configuration表,插入几个默认值
             createSql = ConfigHelper.getValue("create_configuration");
             int res = stmt.executeUpdate(createSql);  
//             System.out.println("res = " + res);
             //创建usertable表 创建admin用户
             createSql = ConfigHelper.getValue("create_usertable_sql");
             stmt.executeUpdate(createSql);
             String selectSql = "select * from user_table where username='admin'";
             ResultSet exe = stmt.executeQuery(selectSql);
             if(!exe.next()){
            	 insertSql=ConfigHelper.getValue("insert_usertable_admin");
            	 insertSql = StringUtils.getStringByEncoding(insertSql, "utf-8");
            	 stmt.executeUpdate(insertSql);
             }
         } catch (Exception e) {  
             e.printStackTrace();  
         } finally {  
             try {  
                 if (stmt != null) {  
                     stmt.close();  
                     stmt = null;  
                 }  
                 if (conn != null) {  
                     conn.close();  
                     conn = null;  
                 }  
             } catch (SQLException e) {  
                 e.printStackTrace();  
             }  
         }  

     }
 }