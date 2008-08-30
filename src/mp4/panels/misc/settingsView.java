/*
 * settingsView.java
 *
 * Created on 30. August 2008, 12:04
 */
package mp4.panels.misc;

import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import mp3.classes.interfaces.panelInterface;
import mp3.classes.layer.DefaultHelpModel;
import mp3.classes.layer.People;
import mp3.classes.layer.visual.Help;
import mp4.einstellungen.Einstellungen;
import mp4.frames.mainframe;
import mp4.installation.Setup;
import mp4.utils.tabellen.TableFormat;

/**
 *
 * @author  Andreas
 */
public class settingsView extends javax.swing.JPanel implements panelInterface {

    private Einstellungen data;
    private Einstellungen oldData;
    private TableModel model;
    private mainframe mainframe;
    private TableCellEditor editor;

    /** Creates new form settingsView
     * @param frame 
     */
    public settingsView(mainframe frame) {

        initComponents();

        data = Einstellungen.newInstanceOf();
        oldData = data;
        this.mainframe = frame;

        this.jTable1.setModel(data.getDefaultTablemodel());
        TableFormat.resizeCols(jTable1, new Integer[]{150, 300}, false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
private void initComponents() {

jPanel1 = new javax.swing.JPanel();
jScrollPane1 = new javax.swing.JScrollPane();
jTable1 = new javax.swing.JTable();
jButton2 = new javax.swing.JButton();
jButton3 = new javax.swing.JButton();
jButton4 = new javax.swing.JButton();
jLabel1 = new javax.swing.JLabel();
jButton5 = new javax.swing.JButton();
jButton6 = new javax.swing.JButton();
jLabel2 = new javax.swing.JLabel();

jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Einstellungen"));

jTable1.setModel(new javax.swing.table.DefaultTableModel(
	new Object [][] {
		{null, null},
		{null, null},
		{null, null},
		{null, null},
		{null, null},
		{null, null},
		{null, null},
		{null, null},
		{null, null},
		{null, null}
	},
	new String [] {
		"Option", "Wert"
	}
) {
	Class[] types = new Class [] {
		java.lang.String.class, java.lang.String.class
	};
	boolean[] canEdit = new boolean [] {
		false, true
	};

	public Class getColumnClass(int columnIndex) {
		return types [columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return canEdit [columnIndex];
	}
});
jScrollPane1.setViewportView(jTable1);

jButton2.setText("Speichern");
jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
jButton2MouseClicked(evt);
}
});

jButton3.setText("Reset");
jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
jButton3MouseClicked(evt);
}
});

jButton4.setText("Option...");
jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
jButton4MouseClicked(evt);
}
});
jButton4.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButton4ActionPerformed(evt);
}
});

jLabel1.setText("Markieren Sie eine Zeile und klicken \"Option\", um Verzeichnisse auszuw�hlen.");

jButton5.setText("Desktopicon anlegen");
jButton5.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButton5ActionPerformed(evt);
}
});

jButton6.setText("Hilfe");
jButton6.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButton6ActionPerformed(evt);
}
});

jLabel2.setText("Manche Einstellungen ben�tigen einen Programmneustart!");

javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
jPanel1.setLayout(jPanel1Layout);
jPanel1Layout.setHorizontalGroup(
jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
.addGroup(jPanel1Layout.createSequentialGroup()
.addComponent(jButton6)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jButton5)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
.addComponent(jButton3)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jButton2))
.addGroup(jPanel1Layout.createSequentialGroup()
.addComponent(jLabel1)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jButton4))
.addComponent(jLabel2))
.addContainerGap())
);
jPanel1Layout.setVerticalGroup(
jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jButton4)
.addComponent(jLabel1))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jLabel2)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jButton2)
.addComponent(jButton3)
.addComponent(jButton5)
.addComponent(jButton6))
.addContainerGap())
);

javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
this.setLayout(layout);
layout.setHorizontalGroup(
layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGap(0, 482, Short.MAX_VALUE)
.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
);
layout.setVerticalGroup(
layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGap(0, 300, Short.MAX_VALUE)
.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
);
}// </editor-fold>//GEN-END:initComponents

private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
    editor = jTable1.getCellEditor();
    if (editor != null) {
        editor.stopCellEditing();
    }

    data.setModel(jTable1.getModel());
    data.save();
    mainframe.setMessage("Einstellungen gespeichert.");
//            this.close();
}//GEN-LAST:event_jButton2MouseClicked

private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
    this.jTable1.setModel(oldData.getDefaultTablemodel());
    TableFormat.resizeCols(jTable1, new Integer[]{150, 300}, false);
    data.setModel(jTable1.getModel());
    data.save();
    mainframe.setMessage("Einstellungen wiederhergestellt.");
}//GEN-LAST:event_jButton3MouseClicked

private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
    JFileChooser fc = new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {


        try {
            jTable1.setValueAt(fc.getSelectedFile().toString(), jTable1.getSelectedRow(), 1);

        } catch (Exception exception) {
        }

    }
}//GEN-LAST:event_jButton4MouseClicked

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jButton4ActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    Setup.writeDesktopIcon();
}//GEN-LAST:event_jButton5ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    new Help(new DefaultHelpModel("Einstellungen",
            "<P><FONT FACE='DejaVu Sans, sans-serif'>Sie m&uuml;ssen den Pfad zu Ihrem PDF-Programm angeben, <BR>um erstellte Rechnungen sofort anzusehen und auszudrucken.<BR>Unter  <B>KDE 3.5</B> ist dies z.B.  <B>KPdf </B>  ( /opt/kde3/bin/kpdf ).</FONT></P>" +
            "<P><FONT FACE='DejaVu Sans, sans-serif'>Sie m&uuml;ssen den Pfad zu Ihrem Internet Browser-Programm angeben, <BR>um direkt zum Hilfeforum zu gelangen.</FONT></P>" +
            "<P><FONT FACE='DejaVu Sans, sans-serif'>Der Internetbrowser <B>Firefox 2</B> befindet sich unter <B>OpenSuse 10.x</B> unter: /usr/bin/firefox</FONT></P>"));
}//GEN-LAST:event_jButton6ActionPerformed
// Variables declaration - do not modify//GEN-BEGIN:variables
private javax.swing.JButton jButton2;
private javax.swing.JButton jButton3;
private javax.swing.JButton jButton4;
private javax.swing.JButton jButton5;
private javax.swing.JButton jButton6;
private javax.swing.JLabel jLabel1;
private javax.swing.JLabel jLabel2;
private javax.swing.JPanel jPanel1;
private javax.swing.JScrollPane jScrollPane1;
private javax.swing.JTable jTable1;
// End of variables declaration//GEN-END:variables
    public void updateTables() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() {
        ((JTabbedPane) this.getParent()).remove(this);
    }

    public void undo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void redo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void changeTabText(String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEdited() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setContact(People contact) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public People getContact() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void switchTab(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}