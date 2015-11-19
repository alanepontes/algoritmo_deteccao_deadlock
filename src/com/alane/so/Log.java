package com.alane.so;

import javax.swing.JTextArea;

public class Log {
	private static String textLog;
	private static JTextArea textAreaLog;
	
	public synchronized static void print(String textLog) {
		Log.textLog = textLog;
		
		if(Log.textAreaLog != null) {
			Log.textAreaLog.setText(textAreaLog.getText() + textLog);
			Log.textAreaLog.repaint();
			Log.textAreaLog.validate();
		}
		
		System.out.print(textLog);
	}
	
	public synchronized static void println(String textLog) {
		Log.textLog = textLog + "\n";
		
		if(Log.textAreaLog != null) {
			Log.textAreaLog.setText(textAreaLog.getText() + textLog + "\n");
			Log.textAreaLog.repaint();
			Log.textAreaLog.validate();
		}
		
		System.out.println(textLog);
	}
	
	public static String getTextLog() {
		return Log.textLog;
	}
	
	public static JTextArea getTextAreaLog() {
		return textAreaLog;
	}

	public static void setTextAreaLog(JTextArea textAreaLog) {
		Log.textAreaLog = textAreaLog;
		Log.textAreaLog.setText(textLog);
		Log.textAreaLog.repaint();
		Log.textAreaLog.validate();
	}
	
	public synchronized static void clear() {
		textLog = "";
		
		if(Log.textAreaLog != null) {
			Log.textAreaLog.setText("");
			Log.textAreaLog.repaint();
			Log.textAreaLog.validate();
		}
	}
}
