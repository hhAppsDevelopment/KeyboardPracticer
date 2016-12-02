package IA;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Mainprog {
	boolean b;
	char c;
	GameHelper g;
	JFrame frame;
	JPanel panel, paneldown, panelbackground;
	JButton button, statbutton;
	JLabel labelword, labelpoint, labeltime, labelmistake ;
        JRadioButton small,caps,marks; 
	Timer timer;
	JTextField timefield, namefield;
	JLabel label;
	Font font;
	String name;
        int interval;
	int time;
	int l;
	public static void main (String[] args) {
		Mainprog MP = new Mainprog();
		MP.SetUpMenu();
	
	}
	public void SetUpMenu(){
	
		if (b == false)frame = new JFrame("Keyboard Practice Game");
                b = true;
		panel = new JPanel ();
		JLabel namelabel = new JLabel ("Enter your name");
                namefield = new JTextField("Anonymous");
                JLabel timelabel = new JLabel ("Game duration (s)");
                timefield = new JTextField ("60");
                JLabel keylabel = new JLabel ("Used keys:");
                ButtonGroup keys = new ButtonGroup();
                small  = new JRadioButton("small");
                caps = new JRadioButton("BoTh");
                marks = new JRadioButton("Aa-Bb!");
                keys.add(small);
                keys.add(caps);
                keys.add(marks);
                marks.setSelected(true);
                button = new JButton ("Start Game");
		button.addActionListener(new StartButtonListener());
		statbutton = new JButton ("Show Statistics");
		statbutton.addActionListener(new StatButtonListener());
		panel.add(namelabel);
                panel.add(namefield);
                panel.add(timelabel);
                panel.add(timefield);
                panel.add(keylabel);
                panel.add(small);
                panel.add(caps);
                panel.add(marks);
                panel.add(button);
		panel.add(statbutton);
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setSize(350,150);	
		timer = new Timer();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
		
			public void windowClosing (WindowEvent e)
		{
			timer.cancel();
			e.getWindow().dispose();
			System.exit(0);}
		}
		);
	}
	public void SetUpGame(){
		panelbackground = new JPanel();
		panel = new JPanel();
		paneldown = new JPanel();
		labelword = new JLabel("jjj");
		labelpoint = new JLabel("Points Scored");
		labeltime = new JLabel ("Time left: "+ interval+" s");
		labelmistake = new JLabel("Mistakes");
		
		panelbackground.setLayout(new GridLayout(0,1));
		paneldown.setLayout(new GridLayout(0,1));
		panel.setLayout(new GridLayout(0,3));
		
		labelpoint.setHorizontalAlignment(SwingConstants.CENTER);
		labeltime.setHorizontalAlignment(SwingConstants.CENTER);
		labelmistake.setHorizontalAlignment(SwingConstants.CENTER);
		
		font = new Font(null, Font.BOLD, 40);
		labelword.setFont(font);
		labelword.setForeground(Color.RED);
		
		panel.setBackground(Color.LIGHT_GRAY);
		
		panelbackground.add(panel);
		panelbackground.add(paneldown);
		panel.add(labelword);
		paneldown.add(labeltime);
		paneldown.add(labelpoint);
		paneldown.add(labelmistake);
		frame.setContentPane(panelbackground);
		frame.setVisible(true);
		frame.setSize(500,  500);
		paneldown.addKeyListener(new OurKeyListener());
		paneldown.setFocusable(true);
		paneldown.requestFocus();
	}
	class StartButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			try
                        {
                            interval = Integer.parseInt(timefield.getText());
                        }
                        catch(NumberFormatException nfe)
                        {
                        }
  			if (interval>0){
                            time = interval;
                            name = namefield.getText();
                            SetUpGame ();
                            StartGame ();}
                        else
                            JOptionPane.showMessageDialog(panel, "Time must be a positive number", "Error", JOptionPane.ERROR_MESSAGE);
		}
					
	}
	
	class OurKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if(c == e.getKeyChar ()){
				c = newChar ();
                                setUpPanel(c);
				g.newPointScored();
				labelpoint.setText("Points Scored: "+g.getPoints());
			}
			else if (e.getKeyCode() != KeyEvent.VK_SHIFT )
			{
				g.newMistake();
				labelmistake.setText("Mistakes: "+ g.getMistakes());
			}
		
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
	}
	public void StartGame (){
		g = new GameHelper();
		g.newGame();
		c = newChar();
                setUpPanel(c);
		timer();
	}
	public char newChar() {
            char d;
            if(small.isSelected())
                    d = g.getrandomSmallLetter();
                else if(caps.isSelected())
                    d = g.getrandomLetter();
                else
                    d = g.getrandomLetterOrMark();
		return d;
	}
	public void endGame (){
		frame.setVisible(false);
		ToplistManager tm = new ToplistManager();
		tm.setTime(time);
		tm.writeNewScore(namefield.getText(),g.getPoints());
		int i = JOptionPane.showConfirmDialog(null, "Time is up! Your final score is:"+ g.getPoints()+ "\n Do you want another game?", "Times up", JOptionPane.YES_NO_OPTION);
		if (i == JOptionPane.YES_OPTION){
			SetUpMenu();
		}
		else
		{
                    
			String topscores = "";
                        for(i=0;i<Math.min(tm.readPoints().size(),5);i++)
                            topscores = topscores+(i+1)+". "+tm.readPoints().get(i)+"\n\r";
                        JOptionPane.showMessageDialog(null, topscores, "Toplist", JOptionPane.OK_OPTION);
			System.exit(0);}			
	}
	public void timer(){
		timer = new Timer ();
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run() {
				labeltime.setText("Time left: "+ Integer.toString(setInterval())+ " s" );
			}
		}, 1000, 1000);
	}
	public int setInterval(){
		if (interval == 1){
			timer.cancel ();
			endGame();
		}
		return --interval;
	}
	public void setUpPanel (char c){
		panel.removeAll ();
		l = (int) Math.round(Math.random()*8);
		for(int i = 0; i<l; i++){
			label = new JLabel();
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setForeground(Color.WHITE);
			label.setFont(font);
			label.setText(String.valueOf(newChar()));
			panel.add(label);
		}
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setFont(font);
		label.setText(String.valueOf(c));
		panel.add(label);
		
		for(int i = 0; i<(9-l-1); i++){
			label = new JLabel();
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setForeground(Color.WHITE);
			label.setFont(font);
			label.setText(String.valueOf(newChar()));
			panel.add(label);
			}
			panel.validate();
			panel.repaint();
			
		
			
			
	}
	public int getInterval() {
		return time;
	}
	class StatButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ToplistManager tm = new ToplistManager();
			//System.out.println(tm.readPoints());
			String topscores = "";
                        for(int i=0;i<Math.min(tm.readPoints().size(),5);i++)
                            topscores = topscores+(i+1)+". "+tm.readPoints().get(i)+"\n\r";
                        JOptionPane.showMessageDialog(null, topscores, "Toplist", JOptionPane.OK_OPTION);
		}
}
}
