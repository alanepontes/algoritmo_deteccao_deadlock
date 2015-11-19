package com.alane.so;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TelaDeLog {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaDeLog window = new TelaDeLog();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaDeLog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 867, 552);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JTextArea textAreaLog = new JTextArea();
		textAreaLog.setBackground(new Color(32, 178, 170));
		textAreaLog.setEditable(false);
		frame.getContentPane().add(textAreaLog, BorderLayout.CENTER);
		Log.setTextAreaLog(textAreaLog);
		
		JPanel panelButtonClear = new JPanel();
		panelButtonClear.setBackground(new Color(255, 0, 0));
		frame.getContentPane().add(panelButtonClear, BorderLayout.NORTH);
		
		
		//frame.getContentPane().add(panelButtonClear, FlowLayout.RIGHT);
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelButtonClear.setLayout(flowLayout);
		JButton buttonClear = new JButton("Clear log");
		buttonClear.setBackground(new Color(255, 255, 255));
		buttonClear.addActionListener(new AcaoButtonClear());
		
		panelButtonClear.add(buttonClear);
		
		frame.setVisible(true);
	}
	
	class AcaoButtonClear implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Log.clear();
		}
		
	}
}
