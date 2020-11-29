package user.main;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Page extends JPanel{
	TalkMain talkMain;
	
	public TalkMain getTalkMain() {
		return talkMain;
	}
	
	public Page(TalkMain talkMain) {
		this.talkMain = talkMain;
		this.setPreferredSize(new Dimension(TalkMain.WIDTH-50, TalkMain.HEIGHT-150));
	}
	
}
