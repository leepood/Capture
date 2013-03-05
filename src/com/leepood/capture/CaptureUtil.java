package com.leepood.capture;

public class CaptureUtil {

	static
	{
		System.loadLibrary("png");
		System.loadLibrary("capture");
	}
	
	/**
	 * НиЭМ
	 * @param fileName
	 */
	public static native void capture(String fileName);
	
	
	
	
}
