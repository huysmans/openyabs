/*
 * productImporter.java
 *
 * Created on 27. Januar 2008, 21:56
 */
package mp4.items.visual;

import java.awt.Cursor;
import java.io.File;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


import mp4.items.People;

import mp4.logs.*;
import mp4.items.visual.Popup;
import mp4.datenbank.verbindung.ConnectionHandler;
import mp4.interfaces.Waiter;
import mp4.items.Kunde;

import mp4.items.Hersteller;
import mp4.items.HistoryItem;
import mp4.items.Lieferant;
import mp4.utils.export.textdatei.Kontaktliste;
import mp4.utils.files.TextDatFile;
import mp4.utils.tabellen.DataModelUtils;
import mp4.utils.tabellen.models.DefaultHelpModel;
import mp4.utils.tasks.Job;
import mp4.utils.ui.Position;

/**
 *
 * @author  anti43
 */
public class csvKontaktImporter extends javax.swing.JFrame implements Waiter {

    private static csvKontaktImporter frame;

    public static void instanceOf() {
        if (frame == null) {
            frame = new csvKontaktImporter();
        }
        frame.setVisible(true);
    }
    private String[] header;
    private Task task;

    /** Creates new form productImporter */
    public csvKontaktImporter() {
        initComponents();
        new Position(this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MP CSV Import");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Import: Daten aus einer CSV -Datei"));

        jButton2.setText("Abbruch");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton4.setText("Daten �bernehmen");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setText("Kontaktimport: W�hlen Sie eine CSV-Datei.");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jProgressBar1.setStringPainted(true);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Einstellungen");

        jLabel2.setText("Trennzeichen:");

        jCheckBox1.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup1.add(jCheckBox1);
        jCheckBox1.setText("Komma");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup1.add(jCheckBox2);
        jCheckBox2.setSelected(true);
        jCheckBox2.setText("Semikolon");

        jLabel3.setBackground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Typ:");

        jRadioButton3.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup2.add(jRadioButton3);
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("Kunde");

        jRadioButton2.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup2.add(jRadioButton2);
        jRadioButton2.setText("Lieferant");

        jRadioButton1.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setText("Hersteller");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jRadioButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox2)))))
                .addContainerGap(326, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextField1.setText("Datei w�hlen!");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton3.setText("...");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton6.setText("Hilfe");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton1.setText("Datei einlesen");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Datei");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addGap(6, 6, 6)
                        .addComponent(jButton1))
                    .addComponent(jLabel5))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton6)
                    .addComponent(jButton1))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jButton2)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        this.dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    @SuppressWarnings({"unchecked", "unchecked"})
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        boolean succ = true;
        header = Kontaktliste.header;
        String trenner = ";";
        if (jCheckBox1.isSelected()) {
            trenner = ",";
        } else if (jCheckBox2.isSelected()) {
            trenner = ";";
        }

        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        Job job;
        try {
            TextDatFile dat = new TextDatFile(new File(jTextField1.getText()), jTable1);
            dat.setFieldSeparator(trenner);
            dat.setHeader(new String[]{"Name","Vorname","Mail","Mobil","Telefon", "Fax","PLZ", "Strasse", "Ort", "Firma","Notizen"});

            job = new Job(dat, this, jProgressBar1);
            job.execute();

        } catch (Exception ex) {
            succ = false;
            new Popup(ex.getMessage(), Popup.ERROR);
            ex.printStackTrace();
        } finally {

            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        if (succ) {
            getJButton4().setEnabled(true);
        }
    }//GEN-LAST:event_jButton1MouseClicked

    @Override
    public void set(Object o) {
        this.header = ((TextDatFile) o).getHeader();
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            jTextField1.setText(fc.getSelectedFile().toString());
        }

    }//GEN-LAST:event_jButton3MouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (jButton4.isEnabled()) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task = new Task(this);
            task.execute();

            jButton2.setText("Beenden");
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        new Help(new DefaultHelpModel("CSV Import",
                "<P><FONT SIZE='3' FACE='DejaVu Sans, sans-serif'>Die zu " +
                "importierenden Daten m&uuml;ssen in dieser Form vorliegen:</P>" +
                "<P><FONT  SIZE='3' FACE='DejaVu Sans, sans-serif'>" +
                "<B>Name,Vorname,Mail,Mobil,Telefon,Fax,PLZ,Strasse,Ort,Firma,Notizen</P>"));
    }//GEN-LAST:event_jButton6ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    public javax.swing.JButton getJButton4() {
        return jButton4;
    }

    class Task extends SwingWorker<Void, Void> {

        private csvKontaktImporter frame;

        /*
         * Main task. Executed in background thread.
         */
        public Task(csvKontaktImporter frame) {
            this.frame = frame;
        }

        @Override
        public Void doInBackground() {
            int h = 0;
            Object[][] data;


            if ((JOptionPane.showConfirmDialog(frame, "Wirklich alle Daten �bernehmen? Dies wird m�glicherweise einige Zeit dauern!", "Sicher?", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
                if (jTable1.getModel() != null) {
                    data = DataModelUtils.tableModelToArray(jTable1);
                    Date d = new Date();
                    Log.Debug(this, "Einlesen gestartet: " + d, true);
                    frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                    frame.jProgressBar1.setMaximum(data.length);
                    frame.jProgressBar1.setMinimum(0);

                    for (int i = 0; i < data.length; i++) {
                        People contact = null;
                        if (jRadioButton3.isSelected()) {
                            contact = new Kunde();
                            contact.setNummer(((Kunde)contact).getNfh().getNextNumber());
                        } else if (jRadioButton2.isSelected()) {
                            contact = new Lieferant();
                            contact.setNummer(((Lieferant)contact).getNfh().getNextNumber());
                        } else if (jRadioButton1.isSelected()) {
                            contact = new Hersteller();
                            contact.setNummer(((Hersteller)contact).getNfh().getNextNumber());
                        }
                        try {
//"Name","Vorname","Mail","Mobil","Telefon", "Fax","PLZ", "Strasse", "Ort", "Firma","Notizen"	
                            contact.setName((String) data[i][0]);
                            contact.setVorname((String) data[i][1]);
                            contact.setMail((String) data[i][2]);
                            
                            contact.setMobil((String) data[i][3]);
                            contact.setTel((String) data[i][4]);
                            contact.setFax((String) data[i][5]);
                            
                            contact.setPLZ(((String) data[i][6]));
                            contact.setStr(((String) data[i][7]));
                            contact.setOrt(((String) data[i][8]));
                            
                            contact.setFirma(((String) data[i][9]));
                            contact.setNotizen(((String) data[i][10]));           
                        } catch (Exception e) {
                            Log.Debug(e);
                        }
                        try {
                            contact.save();
                            Log.Debug(this, contact.getName(), true);
                            Log.Debug(this, "---------------------------", true);
                            h++;
                            frame.jProgressBar1.setValue(i);
                            frame.jLabel1.setText(h + " Kontakte angelegt");
                        } catch (Exception exception) {
                            Log.Debug(exception);
                        }


                    }
                    d = new Date();
                    new HistoryItem(ConnectionHandler.instanceOf(),"Datenimport", h + " Kontakte importiert.");
                    frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    frame.getJButton4().setEnabled(false);
                    frame.jProgressBar1.setValue(0);
                }
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
