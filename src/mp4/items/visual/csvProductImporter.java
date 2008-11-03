/*
 * productImporter.java
 *
 * Created on 27. Januar 2008, 21:56
 */
package mp4.items.visual;

import com.Ostermiller.util.CSVParser;
import java.awt.Cursor;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import mp4.interfaces.ContactPanel;
import mp4.items.People;

import mp4.items.Steuersatz;
import mp4.items.visual.Help;
import mp4.logs.*;
import mp4.items.visual.Popup;
import mp4.datenbank.verbindung.ConnectionHandler;
import mp4.interfaces.DataPanel;
import mp4.items.Hersteller;
import mp4.items.visual.SupplierPicker;

import mp4.items.ProductImporteur;
import mp4.items.HistoryItem;
import mp4.items.Product;
import mp4.items.ProductGroupCategory;
import mp4.items.ProductGroupFamily;
import mp4.items.ProductGroupGroup;
import mp4.items.handler.ProductGroupHandler;
import mp4.items.Lieferant;
import mp4.utils.tabellen.TableFormat;
import mp4.utils.tabellen.models.DefaultHelpModel;
import mp4.utils.tabellen.models.MPTableModel;
import mp4.utils.ui.Position;
import org.supercsv.cellprocessor.constraint.StrMinMax;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCSVException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author  anti43
 */
public class csvProductImporter extends javax.swing.JFrame implements ContactPanel, DataPanel {

    private static csvProductImporter frame;

    public static void instanceOf() {

        if (frame == null) {
            frame = new csvProductImporter();
        }

        frame.setVisible(true);
    }
    private CSVParser p;
    private ArrayList liste;
    private String[][] datstr;
    private String[] header;
    public ProductImporteur[] data;
    public Lieferant supplier = null;
    private Task task;
    private Hersteller hersteller = null;
    private CsvPreference pref;
    private CellProcessor[] processors;
    private boolean succ;

    /** Creates new form productImporter */
    public csvProductImporter() {
        initComponents();
        header = new String[]{"produktnummer", "name", "text", "vk",
                    "ek", "tax", "herstellerid", "warengruppenkategorie", "warengruppenfamilie",
                    "warengruppe", "url", "ean", "lieferantenid"
                };



        processors = new CellProcessor[]{
                    new StrMinMax(0, 499),
                    new StrMinMax(0, 499),
                    new StrMinMax(0, 499),
                    new StrMinMax(0, 999),
                    new StrMinMax(0, 999),
                    new StrMinMax(0, 999),
                    new StrMinMax(0, 999),
                    new StrMinMax(0, 999),
                    new StrMinMax(0, 999),
                    new StrMinMax(0, 999),
                    new StrMinMax(0, 999),
                    new StrMinMax(0, 999),
                    new StrMinMax(0, 999)
                };


        pref = CsvPreference.STANDARD_PREFERENCE;
        if (jCheckBox2.isSelected()) {
            pref = CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE;
        }
        
        TableFormat.makeUneditable(jTable1);
       
        new Position(this);

    }

    @SuppressWarnings("unchecked")
    public csvProductImporter(File file) {
        initComponents();
        new Position(this);
        if (file != null) {
            this.jTextField1.setText(file.getPath());
        }

        this.setVisible(rootPaneCheckingEnabled);

        readin();
        

        if (succ) {
            getJButton4().setEnabled(true);
            TableFormat.makeUneditable(jTable1);
        }
    }

    public void setSupplier(Lieferant supplier) {
        this.jTextField2.setText(supplier.getFirma());
        this.supplier = supplier;
    }

    public void setContact2(Hersteller hersteller) {
        this.jTextField3.setText(hersteller.getFirma());
        this.hersteller = hersteller;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MP CSV Import");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Import: Daten aus einer CSV -Datei"));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Produknummer", "Produktname", "Langtext", "VK", "EK", "Mehrwertsteuer", "Warengruppenkategorie", "Warengruppenfamilie", "Warengruppe", "Produktbild-URL", "EAN"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Produktimport: W�hlen Sie eine CSV-Datei.");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jProgressBar1.setStringPainted(true);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Einstellungen");

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

        jCheckBox3.setBackground(new java.awt.Color(153, 153, 153));
        jCheckBox3.setText("Ohne 1. Zeile");

        jLabel8.setText("Header:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox2)))))
                .addContainerGap(138, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jLabel8))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Optionale Angaben (wird auf alle Produkte angewendet) ");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jLabel3.setText("Lieferant: ");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jLabel4.setText("Hersteller:");

        jButton7.setText("W�hlen");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setText("W�hlen");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5)
                            .addComponent(jButton7))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Datei");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jLabel7))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton6)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton4.setText("Daten �bernehmen");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setText("Abbruch");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addGap(61, 61, 61)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton4)))
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

    @SuppressWarnings({"unchecked", "unchecked", "unchecked"})
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        readin();

        if (succ) {
            getJButton4().setEnabled(true);
        }
    }//GEN-LAST:event_jButton1MouseClicked

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

        new Help(new DefaultHelpModel("Produktimport", "<p><font  SIZE='3' face='DejaVu Sans, sans-serif'>" +
                "Die zu importierenden Daten m&uuml;ssen in dieser Form vorliegen:" +
                "</p><p><font  SIZE='3' face='DejaVu Sans, sans-serif'>" +
                "<b>&lt;produktnummer&gt;;&lt;name&gt;;&lt;text&gt;;&lt;vk&gt;;&lt;ek&gt;;&lt;tax&gt;;&lt;herstellerid&gt;;&lt;warengruppenkategorie" +
                "&gt;;&lt;warengruppenfamilie&gt;;&lt;warengruppe&gt;;&lt;url&gt;;&lt;ean&gt;;&lt;lieferantenid&gt;</b></p><p>" +
                "<font SIZE='3'  face='DejaVu Sans, sans-serif'>Benutzen Sie die Zahl 1 f�r die default Hersteller ID und Lieferanten ID." +
                "</p><p><font SIZE='3'  face='DejaVu Sans, sans-serif'>Kontrollieren Sie die Korrektheit ihrer " +
                "Daten in der Vorschautabelle.<br></p>"));
    }//GEN-LAST:event_jButton6ActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    new SupplierPicker(this);
}//GEN-LAST:event_jButton5ActionPerformed

private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
}//GEN-LAST:event_jTextField2ActionPerformed

private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jTextField3ActionPerformed

private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

    new ManufacturerPicker(this);
}//GEN-LAST:event_jButton7ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getJButton4() {
        return jButton4;
    }

    @SuppressWarnings({"unchecked"})
    private void readin() {
        succ = true;
        ProductImporteur user = new ProductImporteur();
        liste = new ArrayList();
        header = new String[]{"produktnummer", "name", "text", "vk",
                    "ek", "tax", "herstellerid", "warengruppenkategorie", "warengruppenfamilie",
                    "warengruppe", "url", "ean", "lieferantenid"
                };

        try {

            processors = new CellProcessor[]{
                        new StrMinMax(0, 499),
                        new StrMinMax(0, 499),
                        new StrMinMax(0, 499),
                        new StrMinMax(0, 999),
                        new StrMinMax(0, 999),
                        new StrMinMax(0, 999),
                        new StrMinMax(0, 999),
                        new StrMinMax(0, 999),
                        new StrMinMax(0, 999),
                        new StrMinMax(0, 999),
                        new StrMinMax(0, 999),
                        new StrMinMax(0, 999),
                        new StrMinMax(0, 999)
                    };


            pref = CsvPreference.STANDARD_PREFERENCE;
            if (jCheckBox2.isSelected()) {
                pref = CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE;
            }

            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

            ICsvBeanReader inFile = new CsvBeanReader(new FileReader(jTextField1.getText()), pref);
            try {
//                final String[] header = inFile.getCSVHeader(true);

                while ((user = inFile.read(ProductImporteur.class, header, processors)) != null) {
                    liste.add(user);
                }


            } catch (SuperCSVException ex) {
                succ = false;
                new Popup(ex.getMessage(), Popup.ERROR);
                ex.printStackTrace();
            } catch (IOException ex) {
                succ = false;
                new Popup(ex.getMessage(), Popup.ERROR);

                ex.printStackTrace();
            } finally {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                inFile.close();

            }

        } catch (Exception ex) {
            succ = false;
            new Popup(ex.getMessage(), Popup.ERROR);
            ex.printStackTrace();
        }

        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            user = new ProductImporteur();
            data = ProductImporteur.listToImporteurArray(liste, supplier, hersteller);
            datstr = user.getData(data);
//
//            Thread.sleep(2500);//Wait for the data..
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            jTable1.setModel(new MPTableModel(datstr, header));


        } catch (Exception ex) {
            succ = false;
            new Popup(ex.getMessage(), Popup.ERROR);
            ex.printStackTrace();
        }

    }

    class Task extends SwingWorker<Void, Void> {

        private csvProductImporter thisa;
        private ProductGroupCategory newcat;
        private ProductGroupFamily newfam;
        private ProductGroupGroup newgrp;
        private String fam;
        private String grp;
        /*
         * Main task. Executed in background thread.
         */

        public Task(csvProductImporter thisa) {

            this.thisa = thisa;
        }

        @Override
        public Void doInBackground() {
            int h = 0;
            Date datum = new Date();
            String cat;
            boolean news = false;

            if ((JOptionPane.showConfirmDialog(thisa, "Wirklich alle Daten �bernehmen? Dies wird m�glicherweise einige Zeit dauern!", "Sicher?", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {

                Date d = new Date();
//                    Log.setLogLevel(Log.LOGLEVEL_DEBUG);
                Log.Debug(this, "Einlesen gestartet: " + d, true);
                setCursor(new Cursor(Cursor.WAIT_CURSOR));

                Log.Debug(this, "Expected data: " + data.length);
                jProgressBar1.setMaximum(data.length);
                jProgressBar1.setMinimum(0);

                for (int i = 0; i < data.length; i++) {

                    Log.Debug(this, "Getting grouphandler...");
                    Product pg = new Product(ConnectionHandler.instanceOf());
                    ProductGroupHandler handler = ProductGroupHandler.instanceOf();

                    try {
                        Log.Debug(this, "Try: " + (i + 1));
                        Log.Debug(this, "Step " + 1 + ": ");
                        pg.setNummer(data[i].getProduktnummer());
                        Log.Debug(this, "Step " + 2 + ": ");
                        pg.setName(data[i].getName());
                        Log.Debug(this, "Step " + 3 + ": ");
                        pg.setDatum(datum);
                        Log.Debug(this, "Step " + 4 + ": ");
                        pg.setEK(Double.valueOf(data[i].getEk()));
                        Log.Debug(this, "Step " + 5 + ": ");
                        pg.setVK(Double.valueOf(data[i].getVk()));
                        Log.Debug(this, "Step " + 6 + ": ");
                        pg.setEan(data[i].getEan());
                        Log.Debug(this, "Step " + 7 + ": ");
                        pg.setHersteller(new Hersteller(data[i].getHersteller()));
                        Log.Debug(this, "Step " + 8 + ": ");
                        pg.setTaxID(new Steuersatz().findIDWithOrCreate(Double.valueOf(data[i].getTax())).getId());
                        Log.Debug(this, "Step " + 9 + ": ");
                        pg.setText(data[i].getText());
                        Log.Debug(this, "Step " + 10 + ": ");
                        pg.setUrl(data[i].getUrl());
                        Log.Debug(this, "Step " + 11 + ": ");
                        pg.setLieferant(new Lieferant(Integer.valueOf(data[i].getLieferantenid())));
                    } catch (Exception numberFormatException) {
                        Log.Debug(this, numberFormatException);
                    }

                    Log.Debug(this, "Getting groups...");
                    cat = data[i].getWarengruppenkategorie();
                    fam = data[i].getWarengruppenfamilie();
                    grp = data[i].getWarengruppe();

                    Log.Debug(this, pg.getName() + ":Produkt \n" + cat, true);
                    Log.Debug(this, fam, true);
                    Log.Debug(this, grp, true);

                    Log.Debug(this, "---------------------------", true);


                    if (!cat.equals("null") && !fam.equals("null") && !grp.equals("null")) {

                        int z = handler.exists(cat, handler.CATEGORY);
                        if (z == 0) {

                            newcat = new ProductGroupCategory(ConnectionHandler.instanceOf());

                            newcat.setName(cat);
                            newcat.save();
                            z = newcat.getID();
                            news = true;
                        } else {//                            

                            newcat = handler.getCategory(z);
                        }

                        int f = handler.existFam(fam);
                        if (f == 0) {
                            Log.Debug(this, "Creating Productfamily: " + fam + " " + f, true);
                            newfam = new ProductGroupFamily(ConnectionHandler.instanceOf());
                            newfam.setName(fam);
                            newfam.setKategorieid(z);
                            newfam.save();
                            f = newfam.getID();
                            news = true;
                        } else {
                            Log.Debug(this, "Existing Productfamily: " + fam + " " + f, true);
                            newfam = handler.getFamily(f);
                        }



                        int l = handler.exists(grp, handler.GROUP);
                        if (l == 0) {

                            newgrp = new ProductGroupGroup(ConnectionHandler.instanceOf());
                            newgrp.setName(grp);
                            newgrp.setFamilienid(f);
                            newgrp.save();
                            news = true;
                        } else {
                            newgrp = handler.getGroup(l);
                        }


                        pg.setWarengruppenId(newgrp.getID());


                        if (news) {
                            handler.getCats(true); //speed?

                        } //speed?

                        news = false;


                    } else {

                        pg.setWarengruppenId(1);

                    }



                    pg.save();
                    h++;
                    jProgressBar1.setValue(i);

                    jLabel1.setText("Produkte angelegt");
                    pg = null;
                }
                d = new Date();

                Popup.notice("Einlesen beendet: " + d + " Produkte versucht: " + h);
                new HistoryItem(ConnectionHandler.instanceOf(), "Datenimport", "Produkte importiert.");

                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                getJButton4().setEnabled(false);
                jProgressBar1.setValue(0);
            }

            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
//        Toolkit.getDefaultToolkit().beep();
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));


        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setProduct(Product p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setContact(People contact) {
        jTextField2.setText(contact.getFirma());
        this.supplier = (Lieferant) contact;
    }

    public void setTax(Steuersatz steuersatz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
