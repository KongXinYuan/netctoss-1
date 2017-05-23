package com.zhf.action.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.struts2.interceptor.SessionAware;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class VerificationCodeAction implements SessionAware{
	private Map<String,Object> session;
	private InputStream is;
	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String execute(){
		int width=60;//ͼƬ��
		int hight=25;//ͼƬ��
		int fontSize=16;//�����С
		BufferedImage image=new BufferedImage( width,hight, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g=image.getGraphics();//��ȡ����
		g.setColor(Color.white);
		g.fillRect(0, 0,width, hight);//���ָ������
		g.setColor(Color.black);
		g.drawRect(0, 0,width, hight);//���߿�
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", Font.BOLD, fontSize));
		Random random=new Random();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<4;i++){
			int temp=random.nextInt(10);
			sb.append(temp);
		}
		String str=sb.toString();
		g.drawString(str,10,20);//��2��������x,y���꣬���ַ������½��ڻ����е�λ��
		g.dispose();//�ر���Դ
		session.put("verificationCode", str);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(bos);  
        try {
			jpeg.encode(image);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        byte[] bts = bos.toByteArray();  
        is = new ByteArrayInputStream(bts); 
		
		return "success";
	}
}
