package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Conexion;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerFacilidades extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tblFacilidades;
	private JButton btnVolver;
	private DefaultTableModel model;
    private Object row[];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VerFacilidades dialog = new VerFacilidades(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VerFacilidades(String IdSelPropiedad) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VerFacilidades.class.getResource("/Image/homepage_home_house_icon_153873.png")));
		setTitle("Facilidades\r\n");
		setBounds(100, 100, 326, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(UIManager.getBorder("DesktopIcon.border"));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					tblFacilidades = new JTable();
					scrollPane.setViewportView(tblFacilidades);
					String headers1[] = {"Facilidades"};
					model = new DefaultTableModel();
					model.setColumnIdentifiers(headers1);
					tblFacilidades.setModel(model);
			        
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnVolver = new JButton("Volver\r\n");
				btnVolver.setActionCommand("Cancel");
				buttonPane.add(btnVolver);
			}
		}
		setLocationRelativeTo(null);
		loadTable(IdSelPropiedad);
	}
	
	private void loadTable(String idPropiedad) {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "EXEC sp_CargarFacilidades "+ idPropiedad +" ;";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Facilidad");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}
}
