package com.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sender.*;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.TextField;

public class GUI {
	private static JTabbedPane tabbedPane;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 310, 229);
		frame.getContentPane().add(scrollPane);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		scrollPane.setViewportView(tabbedPane);

		Button button = new Button("Nuevo");
		button.setForeground(new Color(0, 0, 0));
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBackground(new Color(255, 222, 173));
		button.setActionCommand("Nuevo mensaje");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String asunto = JOptionPane.showInputDialog("Inserte el asunto del mensaje de correo", "Nuevo Asunto");
				while (asunto.isEmpty()) {
					asunto = JOptionPane.showInputDialog("Debes incluir algo en el asunto", "Nuevo Asunto");
				}
				tabbedPane.addTab(asunto, new JTextArea());
			}
		});
		button.setBounds(326, 23, 100, 21);
		frame.getContentPane().add(button);

		TextField textField = new TextField();
		textField.setText("Destino");
		textField.setForeground(Color.GRAY.darker());
		textField.setBounds(10, 0, 310, 21);
		textField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setText("Destino");
				}
				textField.setForeground(Color.GRAY.darker());
				textField.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (textField.getText().equals("Destino")) {
					textField.setText("");
				}
				textField.setForeground(Color.BLACK);
				textField.setCursor(new Cursor(Cursor.TEXT_CURSOR));

			}

		});

		frame.getContentPane().add(textField);

		Button button_1 = new Button("Borrar");
		button_1.setBackground(new Color(255, 192, 203));
		button_1.setForeground(new Color(0, 0, 0));
		button_1.setFont(new Font("Arial", Font.PLAIN, 12));
		button_1.setBounds(326, 50, 100, 21);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabbedPane.getTabCount() > 0) {
					tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
				}
			}
		});
		frame.getContentPane().add(button_1);

		Button button_2 = new Button("Enviar");
		button_2.setForeground(new Color(0, 0, 0));
		button_2.setFont(new Font("Arial", Font.PLAIN, 12));
		button_2.setBackground(new Color(152, 251, 152));
		button_2.setBounds(326, 77, 100, 21);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea msg = (JTextArea) tabbedPane.getComponent(tabbedPane.getSelectedIndex());
				String subj = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
				new clientSenderSMTP().send("myclientsmtp@gmail.com", "QWErty123.", textField.getText(), msg.getText(),
						subj);
				tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
		});
		frame.getContentPane().add(button_2);
	}
}
