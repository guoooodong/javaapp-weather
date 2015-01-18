package com.weather.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.sf.json.JSONObject;

public class WhetherFrame extends JFrame {
	/**
	 * @author guodont
	 * @param args
	 * 城市天气预报
	 */
	private StringBuilder strBuilder;	//字符流
	private String city;				//城市名称
	private String cityid;				//城市id
	private String templ;				//最低温度
	private String temph;				//最高温度
	private String weatherdec;			//天气描述
	
	public static void main(String[] args) {
		WhetherFrame whetherfrm = new WhetherFrame();
		whetherfrm.setVisible(true);
		whetherfrm.initData() ;			//初始化天气数据
		whetherfrm.initView() ;			//初始化窗体
	}

	private void initData() {
		/**
		 * 天气数据初始化方法
		 */
		try {
			URL url = new URL("http://www.weather.com.cn/data/cityinfo/101010100.html") ;	//创建url对象
			/**
			 * url.openStream () 字节流，需转换为缓冲字符流
			 */
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream())) ;	//创建缓冲输入流
			//StringBuffer 线程安全 StringBuffer 在单线程较快
			strBuilder = new StringBuilder() ;
			String str = null ;
			while((str = br.readLine()) != null) {
				strBuilder.append(str) ;	//追加一行字符串到 strBuilder
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(strBuilder.toString());
		
		JSONObject json = JSONObject.fromObject(strBuilder.toString()) ;
		JSONObject  weather = json.getJSONObject("weatherinfo") ;
		
		city = weather.getString("city") ;
		cityid = weather.getString("cityid") ;
		templ = weather.getString("temp1") ;
		temph = weather.getString("temp2") ;
		weatherdec = weather.getString("weather") ;		//获取天气信息并赋值
	}

	private void initView() {
		/**
		 * 窗体初始化方法
		 */
		MyPanel mainPanel = new MyPanel() ;	//创建显示面板
		this.add(mainPanel) ;
		this.setSize(850, 500);
		this.setLocation(300, 200);
	}
	
	class MyPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			/**
			 * 绘制天气信息
			 */
			super.paint(g);
			Font f = new Font("Microsoft Yahei",Font.BOLD,32) ;
			g.setFont(f );
			g.drawString(city+"天气"+"      "+weatherdec, 100,100);
			g.setFont(new  Font("Microsoft Yahei",Font.PLAIN,24));
			g.drawString("最高气温"+temph, 100,150);
			g.drawString("最低气温"+templ, 100,180);
		}
	}

}


