package test;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;

import javax.swing.*;
public class MainFrame extends JFrame implements ActionListener{

	private JLabel jl1,jl2;
	private JTextField jt_username;
	private JPasswordField jp_password;
	private JButton jb_enter,jb_exit;
	private TrayIcon trayicon;
	private void initCompoenent()
	{
		setSize(300,200);
		setLayout(new GridLayout(3,2));
		jl1=new JLabel("用户名：");
		jl2=new JLabel("密码：");
		jt_username=new JTextField();
		jp_password=new JPasswordField();
		jb_enter=new JButton("登陆");
		jb_exit=new JButton("退出");
		jb_enter.addActionListener(this);
		jb_exit.addActionListener(this);
		add(jl1);
		add(jt_username);
		add(jl2);
		add(jp_password);
		add(jb_enter);
		add(jb_exit);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent ex) {
		// TODO Auto-generated method stub
		if(ex.getSource().equals(jb_enter))
		{
			String username=jt_username.getText().toString().trim();
			String password=String.copyValueOf(jp_password.getPassword());
			if(username.equals("passzh")&&password.equals("1988"))
			{
				JOptionPane.showMessageDialog(null, "登陆成功");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "用户名或密码错误");
			}
		}
		else if(ex.getSource().equals(jb_exit))
		{
			System.exit(0);
		}
		else if(ex.getSource().equals(trayicon))
		{
			if(!isVisible())
			{
			setVisible(true);
			toFront();
			}
		}
	}
	public MainFrame()
	{
		initCompoenent();
		if(!SystemTray.isSupported())
		{
			return;
		}
		else
		{
			SystemTray systemTray=SystemTray.getSystemTray();
			String title="登陆窗口";
			String company="passZh write";
			Image image=Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/sysTray.png"));
			trayicon=new TrayIcon(image,title+"/n"+company,createMenu());
			trayicon.addActionListener(this);
			try {
				systemTray.add(trayicon);
				trayicon.displayMessage(title, company, MessageType.INFO);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private PopupMenu createMenu()
	{
		PopupMenu menu=new PopupMenu();
		MenuItem exit=new MenuItem("close");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ex)
			{
				System.exit(0);
			}
		});
		MenuItem open=new MenuItem("open");
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ex)
			{
				if(!isVisible())
				{
					setVisible(true);
					toFront();
				}
				else
				{
					toFront();
				}
			}
		});
		menu.add(open);
		menu.addSeparator();
		menu.add(exit);
		return menu;
	}
	public static void main(String[] args)
	{
		MainFrame mai=new MainFrame();
	}
}
