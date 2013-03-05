package com.leepood.capture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CaptureService extends Service implements Runnable {

	private UDPServer udpServer = null;

	private static final int TARGET_PORT = 7749;

	private volatile boolean isRun = false;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		udpServer = UDPServer.getInstance();
		new Thread(this).start();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		isRun = true;
		while (isRun) {
			String fileName = "/sdcard/ad/Snapshot_"
					+ System.currentTimeMillis() + ".jpg";
			CaptureUtil.capture(fileName);
			byte[] data = fileToBytes(fileName);
			udpServer.sendPacket(data, TARGET_PORT, "192.168.1.105");
			File f = new File(fileName);
			f.delete();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isRun = false;
		super.onDestroy();
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	private byte[] fileToBytes(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			byte[] data = new byte[fis.available()];
			fis.read(data);
			fis.close();
			return data;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
