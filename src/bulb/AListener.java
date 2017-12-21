package bulb;

import java.awt.event.*;

class AListener implements ActionListener {
	private UI ui;
	
	public AListener(UI ui) {
		this.ui = ui;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if ("on".equals(ae.getActionCommand())) {
			ui.c.setPower("on", "sudden", 30);
		} else if ("off".equals(ae.getActionCommand())) {
			ui.c.setPower("off", "sudden", 30);
		}
	}
}