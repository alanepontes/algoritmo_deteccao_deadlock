package com.alane.so;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.namespace.QName;

public class TelaDeInicializacao {

	private JFrame frame;
	private JTextField tfQtdRecursos;
	private JTextField tfQtdProcessos;
	private JTextField tfTempoVerificacao;
	private JButton btnStart;
	//private SistemaOperacional sistemaOperacional;

	private Integer qtdProcessos = null;
	private Integer tempoSolicitacao = null;
	private Integer tempoUtilizacao = null;
	private Integer qtdRecursos = null;
	private Integer tempoDeVerificacao = null;

	/**
	 * Create the application.
	 */
	public TelaDeInicializacao() {
		initialize();
		//this.sistemaOperacional = sistemaOperacional;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 215);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		JPanel panelDeInicializacao = new JPanel();
		frame.getContentPane().add(panelDeInicializacao, BorderLayout.CENTER);
		panelDeInicializacao.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 153, 255));
		panel.setBounds(1, 1, 443, 37);
		panelDeInicializacao.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel labelProcessos = new JLabel(
				"Simula\u00E7\u00E3o SO ");
		labelProcessos.setForeground(new Color(255, 255, 255));
		labelProcessos.setFont(new Font("Sylfaen", Font.ITALIC, 18));
		panel.add(labelProcessos);

		JLabel label = new JLabel("");
		label.setBounds(145, 1, 144, 37);
		panelDeInicializacao.add(label);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(51, 153, 255));
		panel_3.setBounds(1, 112, 443, 37);
		panelDeInicializacao.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_3 = new JLabel(
				"Quantidade de processos                                :");
		lblNewLabel_3.setFont(new Font("Sylfaen", Font.PLAIN, 13));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		panel_3.add(lblNewLabel_3);

		tfQtdProcessos = new JTextField();
		panel_3.add(tfQtdProcessos);
		tfQtdProcessos.setColumns(10);

		JLabel label_5 = new JLabel("");
		label_5.setBounds(145, 112, 144, 37);
		panelDeInicializacao.add(label_5);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 0, 0));
		panel_1.setBounds(1, 149, 443, 37);
		panelDeInicializacao.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnStart = new JButton("Start");
		panel_1.add(btnStart);
		btnStart.addActionListener(new AcaoButtonStart(frame));

		JPanel panel_5 = new JPanel();
		panel_5.setFont(new Font("Sylfaen", Font.PLAIN, 11));
		panel_5.setBackground(new Color(51, 153, 255));
		panel_5.setBounds(1, 75, 443, 37);
		panelDeInicializacao.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_1 = new JLabel(
				"Tempo de verifica\u00E7\u00E3o para deadlock     :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		panel_5.add(lblNewLabel_1);

		tfTempoVerificacao = new JTextField();
		panel_5.add(tfTempoVerificacao);
		tfTempoVerificacao.setColumns(10);

		JLabel label_6 = new JLabel("");
		label_6.setBounds(145, 149, 144, 37);
		panelDeInicializacao.add(label_6);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 153, 255));
		panel_2.setBounds(1, 38, 443, 37);
		panelDeInicializacao.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel(
				"Quantidade de recursos                                  :");
		lblNewLabel.setFont(new Font("Sylfaen", Font.PLAIN, 13));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		panel_2.add(lblNewLabel);

		tfQtdRecursos = new JTextField();
		panel_2.add(tfQtdRecursos);
		tfQtdRecursos.setColumns(10);

		JLabel label_7 = new JLabel("");
		label_7.setBounds(1, 186, 144, 37);
		panelDeInicializacao.add(label_7);

		frame.setVisible(true);
	}

	public Integer getQtdProcessos() {
		return qtdProcessos;
	}

	public void setQtdProcessos(Integer qtdProcessos) {
		this.qtdProcessos = qtdProcessos;
	}

	public Integer getTempoSolicitacao() {
		return tempoSolicitacao;
	}

	public void setTempoSolicitacao(Integer tempoSolicitacao) {
		this.tempoSolicitacao = tempoSolicitacao;
	}

	public Integer getTempoUtilizacao() {
		return tempoUtilizacao;
	}

	public void setTempoUtilizacao(Integer tempoUtilizacao) {
		this.tempoUtilizacao = tempoUtilizacao;
	}

	public Integer getQtdRecursos() {
		return qtdRecursos;
	}

	public void setQtdRecursos(Integer qtdRecursos) {
		this.qtdRecursos = qtdRecursos;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	public void setBtnStart(JButton btnStart) {
		this.btnStart = btnStart;
	}

	/*public SistemaOperacional getSistemaOperacional() {
		return sistemaOperacional;
	}

	public void setSistemaOperacional(SistemaOperacional sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}*/

	class AcaoButtonStart implements ActionListener {
		private JFrame tela;
		public AcaoButtonStart(JFrame tela) {
			this.tela = tela;;
		}
		// qtdProcessos = null;
		// tempoSolicitacao = null;
		// tempoUtilizacao = null;
		// Integer qtdRecursos = null;
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				qtdProcessos = Integer.parseInt((tfQtdProcessos.getText()));
				
				qtdRecursos = Integer.parseInt(tfQtdRecursos.getText());
				tempoDeVerificacao = Integer.parseInt(tfTempoVerificacao.getText());
				
				/*if (qtdProcessos != null && qtdProcessos.intValue() >= 10) {
					JOptionPane
							.showMessageDialog(
									frame,
									"O campo quantidade de processos deve ser no máximo 9.",
									"ERRO", JOptionPane.ERROR_MESSAGE);
					tfQtdProcessos.setText("");
				}
				System.out.println(qtdRecursos.intValue());
				if (qtdRecursos != null  && qtdRecursos.intValue() >= 10) {
					JOptionPane
							.showMessageDialog(
									frame,
									"O campo quantidade de recurso deve ser no máximo 9.",
									"ERRO", JOptionPane.ERROR_MESSAGE);
					tfQtdRecursos.setText("");
				}*/
				
				SistemaOperacional sistemaOperacional  = new SistemaOperacional(qtdProcessos, qtdRecursos, tempoDeVerificacao);
				
				this.tela.setVisible(false);
				for(int i = 0; i < qtdProcessos; i++) {
					new TelaInicializaProcesso(sistemaOperacional, frame);
				}
				sistemaOperacional.start();
				new TelaPrincipal(sistemaOperacional);
				
			} catch (Exception e2) {
				if (qtdProcessos == null) {
					JOptionPane
							.showMessageDialog(
									frame,
									"O campo quantidade de processos deve ser um número inteiro, positivo e diferente de zero.",
									"ERRO", JOptionPane.ERROR_MESSAGE);
					tfQtdProcessos.setText("");
				}
				
				if (qtdRecursos == null) {
					JOptionPane
							.showMessageDialog(
									frame,
									"O campo quantidade de recurso deve ser um número inteiro, positivo e diferente de zero.",
									"ERRO", JOptionPane.ERROR_MESSAGE);
					tfQtdRecursos.setText("");
				}
				//System.out.println(qtdProcessos.intValue());
				if (tempoDeVerificacao == null) {
					JOptionPane
							.showMessageDialog(
									frame,
									"O campo tempo de verificação deve ser um número inteiro, positivo e diferente de zero.",
									"ERRO", JOptionPane.ERROR_MESSAGE);
					tfTempoVerificacao.setText("");
				}
			}
		}

	}
}
