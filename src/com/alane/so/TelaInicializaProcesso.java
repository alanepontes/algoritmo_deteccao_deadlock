package com.alane.so;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaInicializaProcesso {

	private JDialog jDialog;
	
	private JTextField tfTempoDeUtilizacao;
	private JTextField tfTempoDeSolicitacao;
	private JButton btnStart;
	//private SistemaOperacional sistemaOperacional;

	
	private Integer tempoSolicitacao = null;
	private Integer tempoUtilizacao = null;
	private SistemaOperacional sistemaOperacional;
	public static int idProcesso = 1
			;
	int id;
	
	/**
	 * Create the application.
	 */
	public TelaInicializaProcesso(SistemaOperacional sistemaOperacional, JFrame frame) {
		this.sistemaOperacional = sistemaOperacional;
		this.id = idProcesso;
		idProcesso++;
		initialize(frame);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame frame) {
		jDialog = new JDialog(frame, true);
		jDialog.setBounds(100, 100, 450, 178);
		//jDialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		jDialog.setResizable(false);

		JPanel panelDeInicializacao = new JPanel();
		jDialog.getContentPane().add(panelDeInicializacao, BorderLayout.CENTER);
		panelDeInicializacao.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 153, 255));
		panel.setBounds(1, 1, 443, 37);
		panelDeInicializacao.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel labelProcessos = new JLabel(
				"Inicializando Processo " + this.id);
		labelProcessos.setForeground(new Color(255, 255, 255));
		labelProcessos.setFont(new Font("Sylfaen", Font.ITALIC, 18));
		panel.add(labelProcessos);

		JLabel label = new JLabel("");
		label.setBounds(145, 1, 144, 37);
		panelDeInicializacao.add(label);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(51, 153, 255));
		panel_3.setBounds(1, 74, 443, 37);
		panelDeInicializacao.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_3 = new JLabel(
				"Tempo de solicita\u00E7\u00E3o                                  :");
		lblNewLabel_3.setFont(new Font("Sylfaen", Font.PLAIN, 13));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		panel_3.add(lblNewLabel_3);

		tfTempoDeSolicitacao = new JTextField();
		panel_3.add(tfTempoDeSolicitacao);
		tfTempoDeSolicitacao.setColumns(10);

		JLabel label_5 = new JLabel("");
		label_5.setBounds(145, 112, 144, 37);
		panelDeInicializacao.add(label_5);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 0, 0));
		panel_1.setBounds(1, 112, 443, 37);
		panelDeInicializacao.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnStart = new JButton("Start");
		panel_1.add(btnStart);
		btnStart.addActionListener(new AcaoButtonStart(jDialog));


		JLabel label_6 = new JLabel("");
		label_6.setBounds(145, 149, 144, 37);
		panelDeInicializacao.add(label_6);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 153, 255));
		panel_2.setBounds(1, 38, 443, 37);
		panelDeInicializacao.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel(
				"Tempo de utiliza\u00E7\u00E3o                                  :");
		lblNewLabel.setFont(new Font("Sylfaen", Font.PLAIN, 13));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		panel_2.add(lblNewLabel);

		tfTempoDeUtilizacao = new JTextField();
		panel_2.add(tfTempoDeUtilizacao);
		tfTempoDeUtilizacao.setColumns(10);

		JLabel label_7 = new JLabel("");
		label_7.setBounds(1, 186, 144, 37);
		panelDeInicializacao.add(label_7);
		
		jDialog.setVisible(true);

	}
	
	class AcaoButtonStart implements ActionListener {
		private JDialog tela;
		public AcaoButtonStart(JDialog tela) {
			this.tela = tela;;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				tempoSolicitacao = Integer.parseInt((tfTempoDeSolicitacao.getText()));
				System.out.println(tempoSolicitacao);
				
				tempoUtilizacao = Integer.parseInt(tfTempoDeUtilizacao.getText());
				System.out.println(tempoUtilizacao);
				
				getSistemaOperacional().criarProcesso(sistemaOperacional, tempoSolicitacao, tempoUtilizacao);
				
				this.tela.setVisible(false);
			} catch (Exception e2) {
				if (tempoSolicitacao == null) {
					JOptionPane
							.showMessageDialog(
									jDialog,
									"O campo tempo de solicitação deve ser um número inteiro, natural e diferente de zero.",
									"ERRO", JOptionPane.ERROR_MESSAGE);
					tfTempoDeSolicitacao.setText("");
				}
				
				if (tempoUtilizacao == null) {
					JOptionPane
							.showMessageDialog(
									jDialog,
									"O campo tempo de utilização de recurso deve ser um número inteiro e natural.",
									"ERRO", JOptionPane.ERROR_MESSAGE);
					tfTempoDeUtilizacao.setText("");
				}
				
			}
		}
	}


	public SistemaOperacional getSistemaOperacional() {
		return sistemaOperacional;
	}

}
