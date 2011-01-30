  /*
 * login.java
 *
 * Created on 7. August 2008, 21:47
 */
package mpv5.ui.dialogs;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mpv5.Main;
import mpv5.db.common.NodataFoundException;
import mpv5.globals.LocalSettings;
import mpv5.globals.Messages;
import mpv5.logging.Log;
import mpv5.ui.misc.Position;
import mpv5.db.objects.User;
import mpv5.ui.dialogs.subcomponents.wizard_DBSettings_manage_1;
import mpv5.utils.text.MD5HashGenerator;

/**
 *
 *   
 */
public class LoginToInstanceScreen extends javax.swing.JDialog {

    private java.util.ResourceBundle bundle = mpv5.i18n.LanguageManager.getBundle();

    /**
     * Show the instance login screen, modal
     */
    public static void load() {
        LoginToInstanceScreen s = new LoginToInstanceScreen();
        s.requestFocus();
        s.dispose();
    }

    private LoginToInstanceScreen() {
        super();
        initComponents();
        setModalityType(ModalityType.APPLICATION_MODAL);

        setList();

        if (!LocalSettings.getProperty("lastuser").equals("null")) {
            jCheckBox1.setSelected(true);
            try {
                jTextField1.setText(new User(Integer.valueOf(LocalSettings.getProperty("lastuser"))).getName());
            } catch (NodataFoundException ex) {
                Log.Debug(this, ex.getMessage());
            }
            jPasswordField1.requestFocus();
        }

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }

            private void close() {
                dispose();
            }
        });
        Position position = new Position(this);
        this.setVisible(true);
    }

    private void ddispose() {
        this.dispose();
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
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mPCombobox1 = new mpv5.ui.beans.MPCombobox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = mpv5.i18n.LanguageManager.getBundle(); // NOI18N
        setTitle(bundle.getString("LoginToInstanceScreen.title")); // NOI18N
        setAlwaysOnTop(true);
        setFocusTraversalPolicyProvider(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("LoginToInstanceScreen.jPanel1.border.title"))); // NOI18N

        jLabel1.setText(bundle.getString("LoginToInstanceScreen.jLabel1.text")); // NOI18N

        jLabel2.setText(bundle.getString("LoginToInstanceScreen.jLabel2.text")); // NOI18N

        jButton1.setText(bundle.getString("LoginToInstanceScreen.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPasswordField1.setToolTipText(bundle.getString("LoginToInstanceScreen.jPasswordField1.toolTipText")); // NOI18N
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mpv5/resources/images/32/lock.png"))); // NOI18N

        jCheckBox1.setText(bundle.getString("LoginToInstanceScreen.jCheckBox1.text")); // NOI18N

        jCheckBox2.setText(bundle.getString("LoginToInstanceScreen.jCheckBox2.text")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel4.setText(bundle.getString("LoginToInstanceScreen.jLabel4.text")); // NOI18N

        jLabel5.setText(bundle.getString("LoginToInstanceScreen.jLabel5.text")); // NOI18N

        mPCombobox1.setOpaque(true);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mpv5/resources/images/16/add.png"))); // NOI18N
        jButton2.setText(bundle.getString("LoginToInstanceScreen.jButton2.text")); // NOI18N
        jButton2.setAlignmentY(0.0F);
        jButton2.setIconTextGap(0);
        jButton2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mpv5/resources/images/16/remove.png"))); // NOI18N
        jButton3.setText(bundle.getString("LoginToInstanceScreen.jButton3.text")); // NOI18N
        jButton3.setAlignmentY(0.0F);
        jButton3.setIconTextGap(0);
        jButton3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(mPCombobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
                    .addComponent(mPCombobox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox1)
                    .addComponent(jButton1)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    login();
}//GEN-LAST:event_jButton1ActionPerformed

private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
    login();

}//GEN-LAST:event_jPasswordField1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    Wizard w = new Wizard(false);
    w.setModalityType(w.getModalityType().APPLICATION_MODAL);
    w.addPanel(new wizard_DBSettings_manage_1(w));
    w.showWiz();
    setList();
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    Object idobj = mPCombobox1.getSelectedItemId();

    if (idobj != null) {
        Integer id = Integer.valueOf(idobj.toString());
        try {
            LocalSettings.removeInstance(id);
        } catch (Exception ex) {
            Log.Debug(ex);
        }

        setList();
    }

}//GEN-LAST:event_jButton3ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private mpv5.ui.beans.MPCombobox mPCombobox1;
    // End of variables declaration//GEN-END:variables

    private void login() {
        Object idobj = mPCombobox1.getSelectedItemId();

        if (idobj != null) {

            Integer id = Integer.valueOf(idobj.toString());
            Log.Debug(Main.class, "Switching connection id to: " + id);
            LocalSettings.setConnectionID(id);
            try {
                LocalSettings.read();
                LocalSettings.apply();
            } catch (Exception ex) {
                Log.Debug(Main.class, ex.getMessage());
                Popup.error(this, "Local settings file not readable: " + LocalSettings.getLocalFile() + "\n" + ex);
            }
            User user = mpv5.usermanagement.MPSecurityManager.checkAuth(jTextField1.getText(), new String(jPasswordField1.getPassword()));
            if (user != null) {
                user.login();
                this.setVisible(false);

                if (jCheckBox1.isSelected()) {
                    LocalSettings.setProperty("lastuser", mpv5.db.objects.User.getCurrentUser().__getIDS().toString());
                } else {
                    LocalSettings.setProperty("lastuser", "INSTANCE");
                }

                if (jCheckBox2.isSelected()) {
                    try {
                        LocalSettings.setProperty("lastuserpw", MD5HashGenerator.getInstance().hashData(jPasswordField1.getPassword()));
                    } catch (NoSuchAlgorithmException ex) {
                        Log.Debug(ex);
                    }
                } else {
                    LocalSettings.setProperty("lastuserpw", "fdgdfDDRDFGFGFCVGEDgefg45g");
                }
                LocalSettings.save();
                this.dispose();
            } else {
                jLabel4.setText(Messages.ACCESS_DENIED.getValue());
            }
        }
    }

    private void setList() {
        try {
            List<Integer> list = LocalSettings.getConnectionIDs();
            Object[][] data = new Object[list.size()][2];
            for (int i = 0; i < list.size(); i++) {
                Integer integer = list.get(i);
                data[i][0] = integer;
                data[i][1] = "Connection ID " + integer;
            }
            mPCombobox1.setModel(data);
        } catch (Exception ex) {
            Log.Debug(ex);
        }
    }
}
