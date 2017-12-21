package bulb;

import java.awt.event.*;

class AListener2 implements ActionListener {
	private UI ui;
	
	public AListener2(UI ui) {
		this.ui = ui;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if ("1".equals(ae.getActionCommand())) {
			ui.c.setBright(1, "sudden", 30);
			ui.brightLabel.setText(1 + "");
			ui.slider.setValue(1);
		} else if ("25".equals(ae.getActionCommand())) {
			ui.c.setBright(25, "sudden", 30);
			ui.brightLabel.setText(25 + "");
			ui.slider.setValue(25);
		} else if ("50".equals(ae.getActionCommand())) {
			ui.c.setBright(50, "sudden", 30);
			ui.brightLabel.setText(50 + "");
			ui.slider.setValue(50);
		} else if ("100".equals(ae.getActionCommand())) {
			ui.c.setBright(100, "sudden", 30);
			ui.brightLabel.setText(100 + "");
			ui.slider.setValue(100);
		}
	}
}