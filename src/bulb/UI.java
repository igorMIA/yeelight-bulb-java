package bulb;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

class UI extends JFrame {
	public Command c;
	private String data, bright, before;
	
	JButton onButton, offButton, startButton, connectButton, mod1, mod2, mod3, mod4;
	JSlider slider;
	JLabel brightLabel, connectLabel, scrollLabel, modsLabel;
	JTextField ip, port;
	JTextArea history;
	
	public UI(String name, Command c) {
		super(name);
		this.c = c;
		AListener ai = new AListener(this);
		AListener2 ai2 = new AListener2(this);
		
		JPanel panel = new JPanel();
		setContentPane(panel);
		setResizable(false);
		setSize(300, 362);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		history = new JTextArea(11,25);
		history.setEditable(false);
		history.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(history);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel middlePanel = new JPanel();
		middlePanel.add(scroll);
		
		ip = new JTextField(9);
		port = new JTextField(4);
		startButton = new JButton("SEARCH");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (history.getText().equals("Receive timed out")) {
					history.setText("");
				}
				ArrayList<String> arr = c.connect();
				ip.setText(arr.get(0));
				port.setText(arr.get(1));
				data = arr.get(2);
				if (data.equals("Receive timed out")) {
					history.append(data);
					data = "";
				}
				bright = arr.get(3);
			}
		});
		connectButton = new JButton("CONNECT");
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (c.set(ip.getText(), Integer.parseInt(port.getText()))) {
					connectLabel.setForeground(Color.GREEN);
					connectLabel.setText("Connected");
					brightLabel.setText(bright);
					slider.setValue(Integer.parseInt(bright));
					history.setText("");
					history.append(data);
				} 		
			}
		});
		mod1 = new JButton("1");
		mod1.setActionCommand("1");
		mod1.addActionListener(ai2);
		mod2 = new JButton("25");
		mod2.setActionCommand("25");
		mod2.addActionListener(ai2);
		mod3 = new JButton("50");
		mod3.setActionCommand("50");
		mod3.addActionListener(ai2);
		mod4 = new JButton("100");
		mod4.setActionCommand("100");
		mod4.addActionListener(ai2);
		JPanel modPanel = new JPanel();
		modPanel.add(mod1);
		modPanel.add(mod2);
		modPanel.add(mod3);
		modPanel.add(mod4);
		onButton = new JButton("ON");
		onButton.setActionCommand("on");
		onButton.addActionListener(ai);
		offButton = new JButton("OFF");
		offButton.setActionCommand("off");
		offButton.addActionListener(ai);
		
		connectLabel = new JLabel("Disconnected");
		connectLabel.setForeground(Color.RED);
		brightLabel = new JLabel("none");
		scrollLabel = new JLabel("Bright");
		modsLabel = new JLabel("Bright mods");
		slider = new JSlider(JSlider.HORIZONTAL, 1, 100, 100);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				brightLabel.setText(slider.getValue() + "");
			}
		});
		slider.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent ae) {}

			@Override
			public void mouseEntered(MouseEvent ae) {}

			@Override
			public void mouseExited(MouseEvent ae) {}

			@Override
			public void mousePressed(MouseEvent ae) {
				before = brightLabel.getText();
			}

			@Override
			public void mouseReleased(MouseEvent ae) {
				if (!before.equals(brightLabel.getText())) {
					c.setBright(slider.getValue(), "sudden", 30);
				}
			}
		});
		
		panel.add(startButton);
		panel.add(ip);
		panel.add(port);
		panel.add(connectButton);
		panel.add(connectLabel);
		panel.add(onButton);
		panel.add(offButton);
		panel.add(scrollLabel);
		panel.add(slider);
		panel.add(brightLabel);
		panel.add(modsLabel);
		panel.add(modPanel);
		panel.add(middlePanel);
		panel.validate();
	}
}