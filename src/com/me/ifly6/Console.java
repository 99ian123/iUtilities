package com.me.ifly6;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class Console extends JFrame implements KeyListener, ActionListener{

	/*
	 * THINGS TO DO:
	 * IMPLEMENT A CHANGE DIRECTORY SYSTEM.
	 * FIX THE SWTICH SCREEN MECHANISM
	 */

	private static final long serialVersionUID = 1L;

	// SWING DATA
	JFrame frame = new JFrame("iUtilities " + Info.version);
	JPanel pane = new JPanel();
	public static JTextArea output = new JTextArea();
	public static JTextArea log = new JTextArea();
	public static JTextField input = new JTextField();
	JScrollPane scp = new JScrollPane(output);

	// INTERNAL DATA
	public static String preoperand;
	public static String[] operand;
	static String computername = "Unknown";
	static String[] mem = new String[10];
	static final String starter = "== iUtilities Console " + Info.version + " == " + 
			"\nHello " + System.getProperty("user.name") + "!" + 
			"\nType 'help' for help.";

	JMenuBar menubar = new JMenuBar();
	JMenu menufile = new JMenu("File");
	JMenu menucomm = new JMenu("Commands");
	JMenu menuview = new JMenu("View");
	JMenu menuhelp = new JMenu("Help");
	JMenu menuabout = new JMenu("About");
	JMenuItem test = new JMenuItem("test");
	JMenuItem export = new JMenuItem("Exportation");
	JMenuItem script = new JMenuItem("Script Input");
	JMenuItem mindterm = new JMenuItem("Mindterm");
	JMenuItem purge = new JMenuItem("Inactive Memory Purge");
	JMenuItem debug = new JMenuItem("Log Console");
	JMenuItem info = new JMenuItem("System Readout");
	JMenuItem clear = new JMenuItem("Clear Screen");
	JMenuItem defaultCarat = new JMenuItem("Snap to Bottom");
	JMenuItem viewswitch = new JMenuItem("Switch View");
	JMenuItem del = new JMenuItem("Delete iUtilities Files");
	JMenuItem term = new JMenuItem("Terminate Process");
	JMenuItem about = new JMenuItem("About");
	JMenuItem help = new JMenuItem("Help");
	JMenuItem changelog = new JMenuItem("Changelog");
	JMenuItem updates = new JMenuItem("Updates");

	Console()
	{
		// Base GUI, in Swing.
		frame.setBounds(50, 50, 670, 735);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container con = getContentPane();
		getContentPane().setLayout(new BorderLayout());
		con.add(this.pane);
		pane.setLayout(new BorderLayout());
		frame.add(pane);

		pane.add(scp, BorderLayout.CENTER);
		pane.add(input, BorderLayout.SOUTH);
		output.setEditable(false);
		input.addKeyListener(this);

		Font font = new Font("Monaco", 0, 11);
		output.setFont(font);
		output.setBackground(Color.black);
		output.setForeground(Color.green);

		input.setFont(font);
		input.setBackground(Color.black);
		input.setForeground(Color.green);
		input.setCaretColor(Color.green);
		this.pane.setBackground(Color.DARK_GRAY);
		DefaultCaret caret = (DefaultCaret)output.getCaret();
		caret.setUpdatePolicy(2);

		// MENUBAR CREATION
		menubar.add(menufile);
		menubar.add(menucomm);
		menubar.add(menuview);
		menubar.add(menuhelp);
		menubar.add(menuabout);

		// File
		menufile.add(export);
		menufile.add(script);
		menufile.add(mindterm);
		export.addActionListener(this);
		script.addActionListener(this);
		mindterm.addActionListener(this);
		// Commands
		menucomm.add(purge);
		menucomm.add(debug);
		menucomm.add(info);
		menucomm.add(term);
		menuabout.add(test);
		purge.addActionListener(this);
		debug.addActionListener(this);
		info.addActionListener(this);
		term.addActionListener(this);
		test.addActionListener(this);
		// View
		menuview.add(clear);
		menuview.add(defaultCarat);
		menuview.add(viewswitch);
		menuview.add(del);
		clear.addActionListener(this);
		defaultCarat.addActionListener(this);
		viewswitch.addActionListener(this);
		del.addActionListener(this);
		// Help
		menuhelp.add(about);
		menuhelp.add(help);
		menuhelp.add(changelog);
		menuhelp.add(updates);
		about.addActionListener(this);
		help.addActionListener(this);
		changelog.addActionListener(this);
		updates.addActionListener(this);

		pane.add(menubar, BorderLayout.NORTH);
		pane.setVisible(true);
		frame.setVisible(true);
		log.append("\nJava Swing GUI Initialised and Rendered");
	}

	// MAIN THREAD.
	public static void main(String[] args) throws UnknownHostException {
		new Console();
		output.append(starter);
		computername = InetAddress.getLocalHost().getHostName(); 

		Date date = new Date();
		log.append("\niUtilities " + Info.version + " Initialised. Date: " + date);
	}

	// EVENT HANDLER
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 10) {
			try {
				TextProc.processing(null);
			} catch (InterruptedException e1) { log.append("\nkeyPressed Error");
			} catch (IOException e1) { log.append("\nkeyPressed Error"); }
		}
		if (keyCode == 38){ input.setText(preoperand); }
	}
	public void keyReleased(KeyEvent arg0) { }
	public void keyTyped(KeyEvent arg0) { }

	// ACTIONPREFORMED LISTENER FOR ALL THE DAMN BUTTONS
	public void actionPerformed(ActionEvent e) {
		Object eventSource = e.getSource();
		if (eventSource == export) {
			output.append("\n" + computername + "~ $ File>Export ");
			try {
				ToolbarProc.save();
			} catch (IOException e1) { log.append("\nExport Failed, IOException"); }
		}
		if (eventSource == script) {
			output.append("\n" + computername + "~ $ File>Script");
			ToolbarProc.script();
			log.append("Script Look Executed. May or may not have run.");
		}
		if (eventSource == mindterm) {
			output.append("\n" + computername + "~ $ File>Mindterm");
			try {
				ToolbarProc.mindterm();
			} catch (IOException e1) { log.append("\nMindterm Download Failed: IOException"); }
			log.append("\nMindterm Download Commenced.");
		}
		if (eventSource == test){
			output.append("\n" + computername + "~ $ About>Test");
			try {
				ToolbarProc.save();
			} catch (IOException e1) { log.append("\nTEST");
			}
			if (eventSource == purge) {
				output.append("\n" + computername + "~ $ Command>Purge");
				try {
					ToolbarProc.purge();
				} catch (IOException e1) { log.append("\nPurge Failed: IOException");}
			}
			if (eventSource == debug) {
				output.append("\n" + computername + "~ $ Command>Debug");
				try {
					ToolbarProc.debug();
				} catch (IOException e1) { log.append("\nBug JTextArea Export Failed: IOException"); }
			}
			if (eventSource == info){
				output.append("\n" + computername + "~ $ Command>System Readout");
				try {
					ToolbarProc.info();
				} catch (InterruptedException e1) { log.append("\nInformation Not Exported: InterruptedException");
				} catch (IOException e1) { log.append("\nInformation Not Exported: IOException"); }
			}
			if (eventSource == clear){
				ToolbarProc.clear();
			}
			if (eventSource == defaultCarat){
				ConInfClass.append(computername + "~ $ View>Snap to Bottom");
				ToolbarProc.defaultCarat();
			}
			// Needs Work
			if (eventSource == viewswitch){
				ConInfClass.append(computername + "~ $ View>Switch View");
				// right now it doesn't do shit. it just sets the screen as starter. please rewrite this algorithm.
			}
			if (eventSource == del){
				ConInfClass.append(computername + "~ $ View>Delete iUtilities Files");
				try {
					ToolbarProc.delete();
				} catch (IOException e1) { log.append("\nDeletion Failed: IOException"); }
			}
			if (eventSource == term){
				ConInfClass.append(computername + "~ $ Commands>Terminate Process");
				ToolbarProc.term();
			}
			if (eventSource == about) {
				ConInfClass.append(computername + "~ $ Help>About");
				ToolbarProc.about();
			}
			if (eventSource == help){
				try {
					TextProc.help(null);
				} catch (IOException e1) { log.append("\nHelp Invocation Failed: IOException"); }
			}
			if (eventSource == changelog){
				ConInfClass.append(computername + "~ $ changelog");
				try {
					TextProc.changelog(null);
				} catch (IOException e1) { log.append("Changelog Invocation Failed: IOException"); }
			}
			if (eventSource == updates){
				ConInfClass.append(computername + "~ $ Help>Updates");
				// Fix THIS
			}
		}
	}
}
