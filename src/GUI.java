import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.plaf.ButtonUI;
import javax.print.attribute.standard.Compression;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private JTextField textField_Path;
	private String algo;

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
		frame.setBounds(100, 100, 568, 365);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		String[] algorithms = { "select algorithm", "LZ77", "LZW" };
		JComboBox<String> comboBox_Algor = new JComboBox(algorithms);
		comboBox_Algor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				algo = comboBox_Algor.getSelectedItem().toString();

			}
		});
		comboBox_Algor.setBounds(334, 48, 161, 24);
		frame.getContentPane().add(comboBox_Algor);

		textField_Path = new JTextField();
		textField_Path.setBounds(109, 132, 114, 19);
		frame.getContentPane().add(textField_Path);
		textField_Path.setColumns(10);

		JButton button_Browse = new JButton("Browse");
		button_Browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				int retVal = fc.showOpenDialog(frame);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					textField_Path.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});

		button_Browse.setBounds(350, 129, 117, 25);
		frame.getContentPane().add(button_Browse);

		JButton btnNewButton_Compress = new JButton("Compress");
		btnNewButton_Compress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (algo.equals("LZ77")) {
					LZ77.compress(textField_Path.getText());
				} else if (algo.equals("LZW")) {
					LZW.compress(textField_Path.getText());
				}
			}
		});
		btnNewButton_Compress.setBounds(26, 271, 172, 25);
		frame.getContentPane().add(btnNewButton_Compress);

		JButton btnNewButton_Decompress = new JButton("Decompress");
		btnNewButton_Decompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (algo.equals("LZ77")) {
					LZ77.decompress();
				} else if (algo.equals("LZW")) {
					LZW.decompress();
				}
			}
		});
		btnNewButton_Decompress.setBounds(349, 271, 172, 25);
		frame.getContentPane().add(btnNewButton_Decompress);
	}
}
