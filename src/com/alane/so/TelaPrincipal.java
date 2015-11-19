package com.alane.so;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class TelaPrincipal {

	private static JFrame frame;
	public JTextField getTextFieldEliminarProcesso() {
		return textFieldEliminarProcesso;
	}

	private JTextField textFieldEliminarProcesso;
	private int quantidadeDeProcessos;
	private int quantidadeDeRecursos;
	SistemaOperacional sistemaOperacional;
	int idProcesso;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public TelaPrincipal(SistemaOperacional sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
		this.quantidadeDeProcessos = sistemaOperacional.getQuantidadeDeProcessos();
		this.quantidadeDeRecursos = sistemaOperacional.getQuantidadeDeRecursos();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		
		frame.setBounds(0, 0, width - 10, height - 50);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		Canvas panelCenter = new PainelDesenhoProcessos(this.sistemaOperacional, this.quantidadeDeProcessos, this.quantidadeDeRecursos);
		panelCenter.setBackground(new Color(32, 178, 170));
		frame.getContentPane().add(panelCenter, BorderLayout.CENTER);
		
		JPanel panelSouth = new JPanel();
		panelSouth.setBackground(new Color(255, 0, 0));
		frame.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
		
		textFieldEliminarProcesso = new JTextField();
		panelSouth.add(textFieldEliminarProcesso);
		textFieldEliminarProcesso.setColumns(5);
		
		JButton buttonEliminarProcesso = new JButton("Eliminar processo");
		panelSouth.add(buttonEliminarProcesso);
		buttonEliminarProcesso.addActionListener(new AcaoButtonEliminarProcesso());
		
		ButtonGroup buttonGroup = new ButtonGroup();  
		
		JRadioButton radioButtonTelaDeLog = new JRadioButton("Tela de log");
		//radioButtonTelaDeLog.setBackground(new Color(255, 0, 0));
		buttonGroup.add(radioButtonTelaDeLog);
		panelSouth.add(radioButtonTelaDeLog);
		
		radioButtonTelaDeLog.addActionListener(new AcaoRadioButtonTelaDeLog());
		radioButtonTelaDeLog.setSelected(false);
		  
		
		//JRadioButton radioButtonTelaDeadlock = new JRadioButton("Tela de deadlock");
		//radioButtonTelaDeadlock.setBackground(new Color(255, 153, 51));
		//buttonGroup.add(radioButtonTelaDeadlock);
		//panelSouth.add(radioButtonTelaDeadlock);
		
		//frame.pack();
		
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				frame.setState(frame.MAXIMIZED_BOTH);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				frame.setState(frame.MAXIMIZED_BOTH);
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	

	class AcaoRadioButtonTelaDeLog implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
				new TelaDeLog();
		}
		
	}
	
	class AcaoButtonEliminarProcesso implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				idProcesso = Integer.parseInt((getTextFieldEliminarProcesso().getText()));
				System.out.println(getTextFieldEliminarProcesso());
				sistemaOperacional.eliminarProcesso(idProcesso);
				getTextFieldEliminarProcesso().setText("");
			} catch (Exception e2) {
				if (getTextFieldEliminarProcesso() == null) {
					JOptionPane
							.showMessageDialog(
									frame,
									"O campo quantidade de processos deve ser um número inteiro, natural e diferente de zero.",
									"ERRO", JOptionPane.ERROR_MESSAGE);
					getTextFieldEliminarProcesso().setText("");
				}
		}

				
		}
		
	}
}
