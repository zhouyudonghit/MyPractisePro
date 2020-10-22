package com.example.localuser.retrofittest.SocketTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	public static int BUFFER_SIZE = 1024;
	public static int SERVER_PORT = 10086;
	
	public void startServer()
	{
		ServerThread serverThread = new ServerThread();
		serverThread.start();
	}
	
	public class ServerThread extends Thread
	{
		public void run()
		{
			try {
				ServerSocket serverSocket =new ServerSocket(SERVER_PORT);
				while(true)
				{
			       Socket socket = serverSocket.accept();
			       System.out.println("a new client connected");
			       new ReadThread(socket).start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class ReadThread extends Thread
	{
		private Socket mSocket;
		private InputStream is;
		private BufferedInputStream bis;
		
		public ReadThread(Socket socket)
		{
			mSocket = socket;
		}
		
		@Override
		public void run()
		{
			System.out.println("read thread begins running");
			if(mSocket == null)
			{
				return;
			}
			try {
				is = mSocket.getInputStream();
				bis = new BufferedInputStream(is,2*BUFFER_SIZE);
				byte[] originBytes = new byte[BUFFER_SIZE];
				while(bis != null)
				{
					byte temp[] = null;
					int readLength;
//					if((readLength = bis.read(originBytes)) != -1)
//					{
//						System.out.println("1,readLength = "+readLength);
//						if(temp == null)
//						{
//							//��������
//							int length = 1024;
//							temp = new byte[length];
//						}
//						System.arraycopy(originBytes, 0, temp, 0, readLength);
//						while((readLength = bis.read(originBytes)) != -1)
//						{
//							System.out.println("2,readLength = "+readLength);
//							if(temp == null)
//							{
//								//��������
//								int length = 1024;
//								temp = new byte[length];
//							}
//							System.arraycopy(originBytes, 0, temp, 0, readLength);
//						}
//						System.out.println("3,readLength = "+readLength);
//						if(temp != null)
//						{
//						    System.out.println(temp.length);
//						    StringBuilder sb = new StringBuilder();
//						    sb.append(new String(temp,"utf-8"));
//						    System.out.println("receive data:"+sb.toString());
//						    new WriteThread(mSocket).start();
//						}
//					}
					
					while((readLength = bis.read(originBytes)) != -1)
					{
						System.out.println("2,readLength = "+readLength);
						if(temp == null)
						{
							//��������
							int length = 1024;
							temp = new byte[length];
						}
						if(readLength == 1)
						{
							int data = SocketUtils.bytes2Int(originBytes);
							break;
						}
						System.arraycopy(originBytes, 0, temp, 0, readLength);
						
					}
					System.out.println("3,readLength = "+readLength);
					if(temp != null)
					{
					    System.out.println(temp.length);
					    StringBuilder sb = new StringBuilder();
					    sb.append(new String(temp,"utf-8"));
					    System.out.println("receive data:"+sb.toString());
					    new WriteThread(mSocket).start();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
//				if(bis != null)
//				{
//					try {
//						is.close();
//						bis.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
			}
		}
	}
	
	public class WriteThread extends Thread
	{
		private Socket mSocket;
		private OutputStream os;
		private BufferedOutputStream bos;
		
		public WriteThread(Socket socket)
		{
			mSocket = socket;
		}
		
		public void run()
		{
			if(mSocket == null)
			{
				return;
			}
			try {
				os = mSocket.getOutputStream();
				bos = new BufferedOutputStream(os);
				String s = "this is response from server";
				bos.write(s.getBytes("utf-8"));
				bos.flush();
				bos.write(0);
				bos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
//				if(bos != null)
//				{
//					try {
//						os.close();
//						bos.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
			}
		}
	}
}
