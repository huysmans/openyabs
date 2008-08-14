/*
 * mpInstaller.java
 *
 * Created on 25. Oktober 2007, 19:21
 */
/*
 *  This file is part of MP by anti43 /GPL.
 *  
 *      MP is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      MP is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *  
 *      You should have received a copy of the GNU General Public License
 *      along with MP.  If not, see <http://www.gnu.org/licenses/>.
 */
package mp3.installer;

import java.util.logging.Level;
import java.util.logging.Logger;
import mp4.datenbank.verbindung.Conn;
import java.io.IOException;
import java.awt.Cursor;
import java.io.File;
import javax.swing.JFileChooser;
import mp3.classes.interfaces.Constants;
import mp3.classes.interfaces.ProtectedStrings;
import mp3.classes.interfaces.Strings;
import mp4.utils.windows.Position;
import mp3.classes.layer.Popup;
import mp3.classes.utils.DesktopIcon;
import mp4.utils.files.FileDirectoryHandler;
import mp3.classes.utils.JarFinder;
import mp3.classes.utils.Log;
import mp4.frames.license;
import mp4.main.Main;

/**
 * @author  anti43
 */
public class MpInstaller extends javax.swing.JFrame implements ProtectedStrings, Strings {

    private static MpInstaller instance;

    /**
     * 
     * @return
     */
    public static MpInstaller instanceOf() {
        if (instance == null) {
            return new MpInstaller(true);
        } else {
            return instance;
        }
    }
    private String url;
    private String workdir;
    private File pdf_root_dir;
    private File backup_dir;
    private File public_dir;
    private JFileChooser fc;
    private File lib_dir;
    private File install_lib_dir;
    private File install_templates_dir;
    private File pdf_offer_dir;
    private File pdf_bill_dir;
    private File pdf_mahnung_dir;
    private File templates_dir;
    private File cache_dir;
//    
//    private String pdf_root_dirPATH;
//    private String backup_dirPATH;
//    private String public_dirPATH;
//    private String lib_dirPATH;
//    private String install_lib_dirPATH;
//    private String install_templates_dirPATH;
//    private String pdf_offer_dirPATH;
//    private String pdf_bill_dirPATH;
//    private String pdf_mahnung_dirPATH;
//    private String templates_dirPATH;
//    private String cache_dirPATH;
    /** Creates new form mpInstaller
     */
    public MpInstaller() {

        instance = this;
        initComponents();
        
        try {
            buildPath();
            
        } catch (IOException ex) {
            Log.Debug(ex);
        }

        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setSelectedFile(new File(USER_HOME));

        new Position(this);
        this.setVisible(true);
        Log.Debug(JAVA_VERSION);
    }

    public MpInstaller(boolean silent) {
        try {
            buildPath();
            createDirs();
        } catch (IOException ex) {
            Log.Debug(ex);
        }
    }

    private void buildPath() throws IOException {
        try {
            workdir = JarFinder.getPathOfJar(JAR_NAME);
        } catch (Exception ex) {
            Log.Debug(ex);
        }
        System.out.println("Workdir: " + workdir);
        
        try {
            public_dir = new File(USER_HOME + SEP + PROG_NAME);
            backuppathtf.setText(getPublic_dir().getCanonicalPath() + File.separator + BACKUPS_SAVE_DIR);
            pdfpathtf.setText(getPublic_dir().getCanonicalPath() + File.separator + PDF);
        } catch (Exception iOException) {
            backuppathtf.setText(USER_HOME);
            pdfpathtf.setText(USER_HOME);
        }


        lib_dir = new File(getPublic_dir().getCanonicalPath() + File.separator + LIB_DIR);
        install_lib_dir = new File(workdir + SEP + LIB_DIR);
        install_templates_dir = new File(workdir + SEP + TEMPLATES_DIR);
        cache_dir = new File(DBROOTDIR + SEP + CACHE_DIR);

        if (Main.BACKUP_DIR == null) {
            backup_dir = new File(this.backuppathtf.getText());
        } else {
            backup_dir = new File(Main.PDFDIR);
        }


        if (Main.TEMPLATEDIR == null) {
            templates_dir = new File(getPublic_dir().getCanonicalPath() + File.separator + TEMPLATES_DIR);
        } else {
            templates_dir = new File(Main.PDFDIR);
        }


        if (Main.PDFDIR == null) {
            pdf_root_dir = new File(this.pdfpathtf.getText());
        } else {
            pdf_root_dir = new File(Main.PDFDIR);
        }

        pdf_offer_dir = new File(getPdf_root_dir().getCanonicalPath() + File.separator + OFFER_SAVE_DIR);
        pdf_bill_dir = new File(getPdf_root_dir().getCanonicalPath() + File.separator + BILL_SAVE_DIR);
        pdf_mahnung_dir = new File(getPdf_root_dir().getCanonicalPath() + File.separator + ARREAR_SAVE_DIR);
    }

    private void createDirs() {
        Log.Debug("Verzeichnisse anlegen..", true);
        if (getTemplates_dir().mkdirs() && getBackup_dir().mkdirs() && getPdf_root_dir().mkdirs() && getPdf_bill_dir().mkdirs() && getPdf_offer_dir().mkdirs() && getPdf_mahnung_dir().mkdirs() && getCache_dir().mkdirs()) {
            Log.Debug("Erfolgreich!", true);
        }
    }

    private void deleteFiles() {
        try {
            File fil = new File(workdir);
            FileDirectoryHandler.deleteTree(fil);
        } catch (IOException ex) {
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
private void initComponents() {

jPanel1 = new javax.swing.JPanel();
jPanel2 = new javax.swing.JPanel();
jScrollPane1 = new javax.swing.JScrollPane();
jTextArea1 = new javax.swing.JTextArea();
jLabel2 = new javax.swing.JLabel();
jButton1 = new javax.swing.JButton();
jButton2 = new javax.swing.JButton();
jCheckBox1 = new javax.swing.JCheckBox();
jLabel3 = new javax.swing.JLabel();
jButton3 = new javax.swing.JButton();
jLabel1 = new javax.swing.JLabel();
jCheckBox2 = new javax.swing.JCheckBox();
backuppathtf = new javax.swing.JTextField();
jButton4 = new javax.swing.JButton();
jLabel4 = new javax.swing.JLabel();
pdfpathtf = new javax.swing.JTextField();
jLabel5 = new javax.swing.JLabel();
jButton5 = new javax.swing.JButton();

setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
setTitle("Installation");
setResizable(false);

jPanel1.setBackground(new java.awt.Color(255, 255, 255));

jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Installation"));

jScrollPane1.setBorder(null);
jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
jScrollPane1.setFocusable(false);
jScrollPane1.setOpaque(false);
jScrollPane1.setRequestFocusEnabled(false);
jScrollPane1.setVerifyInputWhenFocusTarget(false);
jScrollPane1.setWheelScrollingEnabled(false);

jTextArea1.setColumns(20);
jTextArea1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
jTextArea1.setLineWrap(true);
jTextArea1.setRows(5);
jTextArea1.setText("Dieses Installationsprogramm wird die Datenbank und ben�tigte Verzeichnisse erstellen.\n\nAnschlie�end k�nnen Sie das Programm starten.");
jTextArea1.setWrapStyleWord(true);
jTextArea1.setFocusable(false);
jTextArea1.setOpaque(false);
jTextArea1.setRequestFocusEnabled(false);
jTextArea1.setVerifyInputWhenFocusTarget(false);
jScrollPane1.setViewportView(jTextArea1);

jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
jLabel2.setText("Willkommen zur Installation der MP Rechnungs -und Kundenverwaltung!");

jButton1.setText("Weiter");
jButton1.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButton1ActionPerformed(evt);
}
});

jButton2.setText("Abbruch");
jButton2.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButton2ActionPerformed(evt);
}
});

jCheckBox1.setSelected(true);
jCheckBox1.setText("Desktopicon anlegen");

jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/mp.png"))); // NOI18N
jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

jButton3.setText("Lizenz anzeigen");
jButton3.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButton3ActionPerformed(evt);
}
});

jLabel1.setText("Sie m�ssen die Bedingungen der GPL akzeptieren, um dieses Programm verwenden zu d�rfen.");

jCheckBox2.setText("Ich akzeptiere diese Bedingungen.");

backuppathtf.setEditable(false);

jButton4.setText("W�hlen");
jButton4.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButton4ActionPerformed(evt);
}
});

jLabel4.setText("Wo soll MP Ihre Backup -Dateien speichern?");

pdfpathtf.setEditable(false);

jLabel5.setText("Wo soll MP Ihre PDF -Dokumente speichern?");

jButton5.setText("W�hlen");
jButton5.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButton5ActionPerformed(evt);
}
});

org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
jPanel2.setLayout(jPanel2Layout);
jPanel2Layout.setHorizontalGroup(
jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
.add(jPanel2Layout.createSequentialGroup()
.addContainerGap()
.add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
.add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 435, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
.add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
.add(jPanel2Layout.createSequentialGroup()
.add(jLabel2)
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
.add(jLabel3))
.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
.add(jButton3)
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
.add(jCheckBox2)
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.add(jButton2)
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
.add(jButton1)))
.add(jLabel1)
.add(jCheckBox1)
.add(jLabel4)
.add(jLabel5)
.add(jPanel2Layout.createSequentialGroup()
.add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
.add(pdfpathtf)
.add(backuppathtf, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
.add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
.add(jPanel2Layout.createSequentialGroup()
.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
.add(jButton4))
.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
.add(jButton5)))))
.addContainerGap(5, Short.MAX_VALUE))
);
jPanel2Layout.setVerticalGroup(
jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
.add(jPanel2Layout.createSequentialGroup()
.add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
.add(jPanel2Layout.createSequentialGroup()
.addContainerGap()
.add(jLabel2)
.add(16, 16, 16)
.add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
.add(jLabel3))
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
.add(jLabel5)
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
.add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
.add(jButton5)
.add(pdfpathtf, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
.add(jLabel4)
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
.add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
.add(backuppathtf, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
.add(jButton4))
.add(18, 18, 18)
.add(jCheckBox1)
.add(18, 18, 18)
.add(jLabel1)
.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
.add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
.add(jButton1)
.add(jButton2)
.add(jButton3)
.add(jCheckBox2))
.addContainerGap())
);

org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
jPanel1.setLayout(jPanel1Layout);
jPanel1Layout.setHorizontalGroup(
jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
.add(jPanel1Layout.createSequentialGroup()
.addContainerGap()
.add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 517, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
);
jPanel1Layout.setVerticalGroup(
jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
.add(jPanel1Layout.createSequentialGroup()
.addContainerGap()
.add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
);

org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
getContentPane().setLayout(layout);
layout.setHorizontalGroup(
layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
.add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
);
layout.setVerticalGroup(
layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
.add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
);

pack();
}// </editor-fold>//GEN-END:initComponents
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    public void copyFiles() throws Exception {

        if (!public_dir.exists() && getInstall_lib_dir().exists()) {
            if (getPublic_dir().mkdirs()) {
                try {

                    if (!Main.FORCE_NO_FILE_COPY) {
                        Log.Debug("Libraries kopieren..", true);
                        FileDirectoryHandler.copyDirectory(getInstall_lib_dir(), getLib_dir());
                        Log.Debug("MP Jar kopieren..", true);
                        FileDirectoryHandler.copyDirectory(new File(workdir + File.separator + ProtectedStrings.JAR_NAME), new File(getPublic_dir().getAbsolutePath() + File.separator + ProtectedStrings.JAR_NAME));
                    }

                    Log.Debug("Templates kopieren..", true);
                    FileDirectoryHandler.copyDirectory(getInstall_templates_dir(), getTemplates_dir());
                    Log.Debug("Installation abgeschlossen.", true);

                } catch (IOException ex) {
                    Popup.error(ex.getMessage(), "Es ist ein Fehler aufgetreten:");
                    Log.Debug("Es ist ein Fehler aufgetreten: " + ex.getMessage(), true);
                }
            } else {

                Log.Debug("Es ist ein Fehler aufgetreten,\n�berpr�fen Sie Ihre Berechtigungen!", true);
            }
        }

    }

    public static void writeDesktopIcon() {
        if (Main.IS_WINDOWS) {
            DesktopIcon.createWindowsDesktopIcon();
        } else {
            DesktopIcon.createLinuxDesktopIcon();
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
        createDirs();
        
        if (jCheckBox2.isSelected()) {
            try {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                if (makeDB()) {
                    if (jCheckBox1.isSelected()) {
                        MpInstaller.writeDesktopIcon();
                    }
                    if (!Main.FORCE_NO_FILE_COPY) {
                        this.copyFiles();
                    }
                    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    new Popup("Sie k�nnen das Programm nun starten.", Popup.NOTICE);
                    System.exit(0);
                } else {
                    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Popup.error(ex.getMessage(), "Fehler bei der Installation.");
                Popup.notice(PERMISSION_DENIED, Popup.ERROR);
            } finally {
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    license l = new license();
    new Position(l);
    l.setVisible(rootPaneCheckingEnabled);
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        try {
            pdfpathtf.setText(fc.getSelectedFile().getCanonicalPath());
        } catch (IOException ex) {
            Log.Debug(ex);
        }
    }
}//GEN-LAST:event_jButton5ActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        try {
            backuppathtf.setText(fc.getSelectedFile().getCanonicalPath());
        } catch (IOException ex) {
            Log.Debug(ex);
        }
    }
}//GEN-LAST:event_jButton4ActionPerformed
    /**
     * @param args the command line arguments
     */
// Variables declaration - do not modify//GEN-BEGIN:variables
javax.swing.JTextField backuppathtf;
javax.swing.JButton jButton1;
javax.swing.JButton jButton2;
javax.swing.JButton jButton3;
javax.swing.JButton jButton4;
javax.swing.JButton jButton5;
javax.swing.JCheckBox jCheckBox1;
javax.swing.JCheckBox jCheckBox2;
javax.swing.JLabel jLabel1;
javax.swing.JLabel jLabel2;
javax.swing.JLabel jLabel3;
javax.swing.JLabel jLabel4;
javax.swing.JLabel jLabel5;
javax.swing.JPanel jPanel1;
javax.swing.JPanel jPanel2;
javax.swing.JScrollPane jScrollPane1;
javax.swing.JTextArea jTextArea1;
javax.swing.JTextField pdfpathtf;
// End of variables declaration//GEN-END:variables
    public boolean makeDB() {
        if (!Main.FORCE_NO_DATABASE) {
            url = Constants.MPPATH + File.separator + Constants.DATABASENAME;
            Conn c = null;
            try {
                c = Conn.instanceOf(url, true);
            } catch (Exception ex) {
                Popup.warn(ex.getMessage(), Popup.ERROR);
            }
            return Conn.isTablesCreated();
        }
        return false;
    }

    public File getPdf_root_dir() {
        return pdf_root_dir;
    }

    public File getBackup_dir() {
        return backup_dir;
    }

    public File getPublic_dir() {
        return public_dir;
    }

    public File getLib_dir() {
        return lib_dir;
    }

    public File getInstall_lib_dir() {
        return install_lib_dir;
    }

    public File getInstall_templates_dir() {
        return install_templates_dir;
    }

    public File getPdf_offer_dir() {
        return pdf_offer_dir;
    }

    public File getPdf_bill_dir() {
        return pdf_bill_dir;
    }

    public File getPdf_mahnung_dir() {
        return pdf_mahnung_dir;
    }

    public File getTemplates_dir() {
        return templates_dir;
    }

    public File getCache_dir() {
        return cache_dir;
    }

    public String getPathpdf_root_dir() {
        return pdf_root_dir.getPath();
    }

    public String getPathbackup_dir() {
        return backup_dir.getPath();
    }

    public String getPathpublic_dir() {
        return public_dir.getPath();
    }

    public String getPathlib_dir() {
        return lib_dir.getPath();
    }

    public String getPathinstall_lib_dir() {
        return install_lib_dir.getPath();
    }

    public String getPathinstall_templates_dir() {
        return install_templates_dir.getPath();
    }

    public String getPathpdf_offer_dir() {
        return pdf_offer_dir.getPath();
    }

    public String getPathpdf_bill_dir() {
        return pdf_bill_dir.getPath();
    }

    public String getPathpdf_mahnung_dir() {
        return pdf_mahnung_dir.getPath();
    }

    public String getPathtemplates_dir() {
        return templates_dir.getPath();
    }

    public String getPathcache_dir() {
        return cache_dir.getPath();
    }
}
