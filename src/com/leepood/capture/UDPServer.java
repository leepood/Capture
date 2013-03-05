package com.leepood.capture;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class UDPServer implements Runnable {

	private static UDPServer instance = null;

	private static final int PORT = 9904;

	private DatagramSocket socketServer = null;

	/* 发送队列 */
	private LinkedBlockingQueue<DatagramPacket> packetsQueue = new LinkedBlockingQueue<DatagramPacket>();

	public synchronized static UDPServer getInstance() {
		if (null == instance) {
			instance = new UDPServer();
		}
		return instance;
	}

	private UDPServer() {
		try {
			socketServer = new DatagramSocket(PORT);

			new Thread(this).start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 发送数据包
	 * 
	 * @param data
	 * @param targetPort
	 * @param targetIP
	 */
	public void sendPacket(byte[] data, int targetPort, String targetIP) {
		try {

			byte[] toSend = new byte[4 + data.length];
			byte[] length = int2bytes(data.length);
			System.arraycopy(length, 0, toSend, 0, 4);
			System.arraycopy(data, 0, toSend, 4, data.length);
			DatagramPacket p = new DatagramPacket(toSend, toSend.length,
					new InetSocketAddress(targetIP, targetPort));
			packetsQueue.add(p);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private byte[] int2bytes(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	@Override
	public void run() {
		try {
			DatagramPacket packet = null;
			while ((packet = packetsQueue.take()) != null) {
				socketServer.send(packet);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void release() {
		socketServer.close();
		socketServer = null;
	}

}
