package coffee_machine_manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
// /dev/cu.usbmodem143401
public class CoffeeMachineManager {

	public static void main(String[] args) throws Exception{
		SerialCommChannel channel = new SerialCommChannel(args[0],9600);		
		System.out.println("Waiting Arduino for rebooting...");		
		Thread.sleep(4000);
		System.out.println("Ready.");	
		
		
		
		final JFrame frame = new JFrame();
		frame.setTitle(" Coffee Machine Manager ");		
		frame.setSize(750,500) ;
		final JPanel panel = new JPanel() ;
		frame.getContentPane().add( panel );
		panel.setLayout(null);
		
		JLabel lblModality = new JLabel("Modality: ");
		
		
		JLabel lblCoffee = new JLabel("Coffee: ");		
		JLabel lblTea = new JLabel("Tea: ");
		JLabel lblChocolate = new JLabel("Chocolate: ");
		JLabel lblNSelfTest = new JLabel("N. Self test: ");
		
		JButton btnRefill = new JButton("Refill");
		JButton btnRecover = new JButton("Recover");
		
		lblModality.setBounds(325, 50, 200, 50);
		lblTea.setBounds(325, 130, 100, 50);
		lblCoffee.setBounds(325, 90, 100, 50);
		lblChocolate.setBounds(325, 170, 100, 50);
		lblNSelfTest.setBounds(325, 350, 100, 50);
		
		
		btnRecover.setBounds(230, 300, 100, 50);
		btnRefill.setBounds(430, 300, 100, 50);
		
		panel.add(lblModality);
		panel.add(lblTea);
		panel.add(lblCoffee);	
		panel.add(lblChocolate);
		panel.add(btnRefill);
		panel.add(btnRecover);
		panel.add(lblNSelfTest);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		btnRefill.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				channel.sendMsg("REFILL");
				
			}
			
		});
		
		btnRecover.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				channel.sendMsg("RECOVER");
				
			}
			
		});
		
		String lastMsg = "";
		String modality = "";
		String nSelfTest = "";
		String[] quantityList = new String[3];
		String[] splittedMsg;
		
		
		
		while(true) {	
			
			String msg = channel.receiveMsg();
			splittedMsg = msg.split("\\|");
			if(!msg.equals(lastMsg) && splittedMsg.length == 5 && msg.contains("|")) {
				
				modality = splittedMsg[0];
				nSelfTest = splittedMsg[1];
				quantityList[0] = splittedMsg[2];
				quantityList[1] = splittedMsg[3];
				quantityList[2] = splittedMsg[4];
				nSelfTest = splittedMsg[1];
				
				lblModality.setText("Modality: "+ modality);
				lblTea.setText("TEA: "+ quantityList[0]);
				lblCoffee.setText("COFFEE: "+ quantityList[1]);
				lblChocolate.setText("CHOCOLATE: "+ quantityList[2]);
				lblNSelfTest.setText("Self Test: "+ nSelfTest);
				
				
				System.out.println(msg);
				lastMsg = msg;
			}
					
			Thread.sleep(50);
		}
	}

}
