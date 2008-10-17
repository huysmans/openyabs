/*
 * productImporter.java
 *
 * Created on 27. Januar 2008, 21:56
 */
package mp4.items.visual;

import java.util.logging.Level;
import java.util.logging.Logger;
import mp4.frames.mainframe;

import java.awt.Cursor;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import compat.mp2.mainFrame;
import compat.mp2.rechnung;
import mp4.utils.ui.Position;

import mp4.items.visual.Help;
import mp4.items.visual.Popup;
import mp4.datenbank.verbindung.ConnectionHandler;
import mp4.items.Rechnung;
import mp4.items.RechnungPosten;
import mp4.items.Customer;
import mp4.items.Product;
import mp4.logs.*;
import mp4.utils.datum.DateConverter;
import mp4.utils.tabellen.models.DefaultHelpModel;

/**
 *
 * @author  anti43
 */
public class mp2Importer extends javax.swing.JFrame {

    private ArrayList liste;
    private String[][] datstr;
    private String[] header;
    private mainframe mainframe;
    private String[][] kund;
    private String[][] rechngs;
    private mainFrame mainf;
    private boolean schliessen = false;
    private boolean kunda = false;
    private boolean recha = true;
    private Double betrag = 0d;

    /** Creates new form productImporter
     * @param frame 
     */
    public mp2Importer(mainframe frame) {
        initComponents();
        new Position(this);
        this.mainframe = frame;
        this.setVisible(rootPaneCheckingEnabled);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MP DB Import");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Import: Daten einer fr�heren Version"));

        jButton1.setText("Daten suchen");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("Abbruch");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton4.setText("Daten �bernehmen");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("Hilfe");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel3.setText("Kunden:");

        jLabel4.setText("Rechnungen:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton4)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(166, 166, 166))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked

        this.dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    @SuppressWarnings("unchecked")
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        boolean succ = true;
        kund = null;
        rechngs = null;
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            mainf = new mainFrame();

            compat.mp2.kunde kunde = new compat.mp2.kunde(mainf);
            compat.mp2.rechnung rechn = new compat.mp2.rechnung(mainf);


            Log.Debug(this,"Kundendaten einlesen..");
            kund = kunde.askForNamesAnd();
            jTable1.setModel(new DefaultTableModel(kund, mainf.initialColumnNames));
            Log.Debug(this,"Rechnungsdaten einlesen..");
            rechngs = rechn.askForNumbersAnd();
            jTable2.setModel(new DefaultTableModel(rechngs, mainf.initialRColumnNames));

            if (kund.length > 0) {
                kunda = true;
            }
            if (rechngs.length > 0) {
                recha = true;
            }

        } catch (Exception exception) {
            Popup.error("Keine Datenbank einer fr�heren Version (V2.x) gefunden.\nEin Import ist nicht m�glich.", Popup.ERROR);
            succ = false;
//            exception.printStackTrace();

        } finally {


            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }




        if (succ) {
            getJButton4().setEnabled(true);
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int taxcount = 0;
        if (jButton4.isEnabled()) {

            if (schliessen) {
                this.dispose();
            } else {

                if (kunda) {

                    for (int i = 0; i < kund.length; i++) {
                        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                        //Kundennummer", "Firma", "Anrede", "Vorname", "Name", "Str", "PLZ", "Ort", 
                        //"Tel", "Mobil", "Mail", "Webseite", "Notizen", "nn", "deleted"

                        Customer c = new Customer(ConnectionHandler.instanceOf());

                        c.setNummer(kund[i][1]);
                        c.setFirma(kund[i][2]);
                        c.setAnrede(kund[i][3]);
                        c.setVorname(kund[i][4]);
                        c.setName(kund[i][5]);
                        c.setStr(kund[i][6]);
                        c.setPLZ(kund[i][7]);
                        c.setOrt(kund[i][8]);
                        c.setTel(kund[i][9]);
                        c.setMobil(kund[i][10]);
                        c.setMail(kund[i][11]);
                        c.setWebseite(kund[i][12]);
                        c.setNotizen(kund[i][12 + 1]);
                        try {

                            c.save();
                        } catch (Exception ex) {
                            Log.Debug(this,ex);
                        }
                        c = null;
                        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                }

                if (recha) {

                    for (int i = 0; i < rechngs.length; i++) {
                        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//rechnungnummer", "nummer", "posten1", "preis1", "posten2", "preis2", 
//                    "posten3", "preis3", "posten4", "preis4", "posten5", 
//                            "preis5", "posten6", "preis6", "posten7", "preis7", 
//                            "posten8", "preis8", "posten9", "preis9", "posten10", 
//                            "preis10", "datum", "gesamtpreis", "mwst"};
                        Log.Debug(this,"MP2 Rechnung instanzieren : id:" + rechngs[i][0]);
                        compat.mp2.rechnung r = new rechnung(mainf, rechngs[i][0]);
                        Log.Debug(this,"MP2 Kunde Instanzieren : id:" + r.kundenID);

                        compat.mp2.kunde k = new compat.mp2.kunde(mainf, r.kundenID);
                        Log.Debug(this,"MP3 Kunde Instanzieren : Nummer:" + k.Kundennummer);
                        Customer c = null;
//     Log.Debug(this,rechngs);
                        Rechnung b = new Rechnung(ConnectionHandler.instanceOf());
                        try {
                            c = new Customer( k.Kundennummer);
                        } catch (Exception ex) {
                            Logger.getLogger(mp2Importer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        b.setRechnungnummer(rechngs[i][1]);
                        b.setKundenId(c.getId());

                        b.setDatum(DateConverter.getDate(rechngs[i][23]));

                        b.setBezahlt(true);


                        // **********************EUR**********************************************************
                        betrag = 0d;
                        Double allovertax = 0d;
                        Double nettobetrag = 0d;



                        //anzahl,bezeichnung,mehrwertsteuer,nettopreis


                        try {
                            for (int z = 3; z < 23; z += 2) {


                                betrag = betrag + (Double.valueOf(rechngs[i][z + 1].toString().replaceAll(",", ".")) * (Double.valueOf((Double.valueOf(rechngs[i][25].toString().replaceAll(",", ".")) / 100) + 1)));


                                taxcount = taxcount + 100;

                                allovertax = allovertax + ((Double.valueOf(rechngs[i][25].toString().replaceAll(",", "."))) + 100);
                                Log.Debug(this,allovertax);
                            }
                        } catch (Exception exception) {
                            Log.Debug(this,exception);
                        }



                        b.setGesamtpreis(betrag);
                        b.setGesamttax(allovertax / taxcount);
                        taxcount = 0;


                        b.save();

                        for (int z = 3; z < 23; z += 2) {

                            RechnungPosten bp = new RechnungPosten(ConnectionHandler.instanceOf());

                            bp.setRechnungid(b.getId());
                            bp.setPosten(rechngs[i][z]);



                            bp.setAnzahl(1d);
                            bp.setSteuersatz(Double.valueOf(rechngs[i][25]));
                            bp.setPreis(Double.valueOf(rechngs[i][z + 1]));



                            bp.save();
                            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }

                    }

                }

                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                Popup.notice("Import erfolgreich, \n" + kund.length + " Kunden und " + rechngs.length + " Rechnungen importiert.");
                this.dispose();

                schliessen = true;
            }
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        new Help(new DefaultHelpModel("MP Version 2.x Import",
                "<P><FONT FACE='DejaVu Sans, sans-serif'><FONT SIZE=3 STYLE='font-size: 12pt'>" +
                "MP Version 3.x sucht nach einer vorhandenen Datenbank-Installation von MP Version 2.x." +
                "</FONT></FONT></P><P><FONT FACE='DejaVu Sans, sans-serif'><FONT SIZE=3 STYLE='font-size: 12pt'>" +
                "Diese sollte sich im versteckten Verzeichnis <B>&lt;homeverzeichnis&gt;/.mp</B> befinden. </FONT>" +
                "</FONT></P><P><FONT FACE='DejaVu Sans, sans-serif'><FONT SIZE=3 STYLE='font-size: 12pt'>" +
                "Die gefundenen Daten werden im Vorschaufenster angezeigt und k&ouml;nnen nun &uuml;bernommen werden." +
                "</FONT></FONT></P>"));
    }//GEN-LAST:event_jButton6ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getJButton4() {
        return jButton4;
    }
}

