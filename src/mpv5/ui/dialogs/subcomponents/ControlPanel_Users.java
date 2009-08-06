package mpv5.ui.dialogs.subcomponents;

import java.awt.Component;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mpv5.data.PropertyStore;
import mpv5.db.common.Context;
import mpv5.db.common.DatabaseObject;
import mpv5.db.common.DatabaseSearch;
import mpv5.db.common.NodataFoundException;
import mpv5.db.common.QueryHandler;
import mpv5.db.objects.Item;
import mpv5.globals.Headers;
import mpv5.globals.Messages;
import mpv5.logging.Log;
import mpv5.i18n.LanguageManager;
import mpv5.ui.dialogs.ControlApplet;
import mpv5.ui.dialogs.Popup;
import mpv5.ui.frames.MPView;
import mpv5.ui.panels.DataPanel;
import mpv5.usermanagement.MPSecurityManager;
import mpv5.db.objects.User;
import mpv5.utils.arrays.ArrayUtilities;
import mpv5.utils.date.DateConverter;
import mpv5.utils.models.MPComboBoxModelItem;
import mpv5.utils.models.MPTableModel;
import mpv5.utils.tables.Selection;
import mpv5.utils.tables.TableFormat;
import mpv5.utils.text.MD5HashGenerator;
import mpv5.utils.ui.TextFieldUtils;

/**
 *
 * 
 */
public class ControlPanel_Users extends javax.swing.JPanel implements ControlApplet, DataPanel {

    private static final long serialVersionUID = 1L;
    /**
     * This unique name identifies this control applet
     */
    public final String UNAME = "users";
    private PropertyStore oldvalues;
    private User dataOwner;
    private static ControlPanel_Users ident;

    public ControlPanel_Users() {
        if (MPSecurityManager.checkAdminAccess()) {
            initComponents();
            refresh();
            setVisible(true);
        }
    }

    public ControlPanel_Users(User user) {
        if (MPSecurityManager.checkAdminAccess()) {
            initComponents();
            refresh();
            setDataOwner(user,true);
            setVisible(true);
        }
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        laf = new mpv5.ui.beans.LabeledTextField();
        language = new javax.swing.JComboBox();
        locale = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        countrylist = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        accountlist = new javax.swing.JComboBox();
        statuslist = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        fullname = new mpv5.ui.beans.LabeledTextField();
        mail = new mpv5.ui.beans.LabeledTextField();
        cname = new mpv5.ui.beans.LabeledTextField();
        password = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        enabled = new javax.swing.JCheckBox();
        inthighestright = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        groupname = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        rgroups = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        datelastlog = new javax.swing.JTextField();
        loggedin = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        java.util.ResourceBundle bundle = mpv5.i18n.LanguageManager.getBundle(); // NOI18N
        setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ControlPanel_Users.border.title"))); // NOI18N
        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ControlPanel_Users.jScrollPane1.border.title"))); // NOI18N
        jScrollPane1.setName("jScrollPane1"); // NOI18N

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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setDragEnabled(true);
        jTable1.setFillsViewportHeight(true);
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ControlPanel_Users.jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ControlPanel_Users.jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        laf.set_Label(bundle.getString("ControlPanel_Users.laf._Label")); // NOI18N
        laf.setName("laf"); // NOI18N

        language.setName("language"); // NOI18N

        locale.setName("locale"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel4.setText(bundle.getString("ControlPanel_Users.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel5.setText(bundle.getString("ControlPanel_Users.jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        countrylist.setName("countrylist"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel7.setText(bundle.getString("ControlPanel_Users.jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel8.setText(bundle.getString("ControlPanel_Users.jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        accountlist.setName("accountlist"); // NOI18N

        statuslist.setName("statuslist"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel9.setText(bundle.getString("ControlPanel_Users.jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(laf, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(countrylist, 0, 254, Short.MAX_VALUE)
                            .addComponent(locale, javax.swing.GroupLayout.Alignment.TRAILING, 0, 254, Short.MAX_VALUE)
                            .addComponent(language, 0, 254, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statuslist, 0, 253, Short.MAX_VALUE)
                            .addComponent(accountlist, 0, 253, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(laf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(language, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(locale, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countrylist, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountlist, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statuslist, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ControlPanel_Users.jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        fullname.set_Label(bundle.getString("ControlPanel_Users.fullname._Label")); // NOI18N
        fullname.setName("fullname"); // NOI18N

        mail.set_Label(bundle.getString("ControlPanel_Users.mail._Label")); // NOI18N
        mail.setName("mail"); // NOI18N

        cname.set_Label(bundle.getString("ControlPanel_Users.cname._Label")); // NOI18N
        cname.setName("cname"); // NOI18N

        password.setText(bundle.getString("ControlPanel_Users.password.text")); // NOI18N
        password.setName("password"); // NOI18N

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel2.setText(bundle.getString("ControlPanel_Users.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        enabled.setBackground(new java.awt.Color(255, 255, 255));
        enabled.setText(bundle.getString("ControlPanel_Users.enabled.text")); // NOI18N
        enabled.setName("enabled"); // NOI18N

        inthighestright.setName("inthighestright"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel3.setText(bundle.getString("ControlPanel_Users.jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        groupname.setName("groupname"); // NOI18N

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel6.setText(bundle.getString("ControlPanel_Users.jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        rgroups.setBackground(new java.awt.Color(255, 255, 255));
        rgroups.setText(bundle.getString("ControlPanel_Users.rgroups.text")); // NOI18N
        rgroups.setName("rgroups"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rgroups, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(fullname, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                                    .addComponent(mail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                                    .addComponent(cname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel6))
                                        .addGap(24, 24, 24)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                            .addComponent(inthighestright, javax.swing.GroupLayout.Alignment.TRAILING, 0, 182, Short.MAX_VALUE)
                                            .addComponent(groupname, 0, 182, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enabled)))
                        .addContainerGap())
                    .addComponent(jLabel3)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(mail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rgroups)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(groupname, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inthighestright, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(enabled))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ControlPanel_Users.jPanel5.border.title"))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel1.setText(bundle.getString("ControlPanel_Users.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        datelastlog.setEditable(false);
        datelastlog.setText(bundle.getString("ControlPanel_Users.datelastlog.text")); // NOI18N
        datelastlog.setName("datelastlog"); // NOI18N

        loggedin.setBackground(new java.awt.Color(255, 255, 255));
        loggedin.setText(bundle.getString("ControlPanel_Users.loggedin.text")); // NOI18N
        loggedin.setEnabled(false);
        loggedin.setName("loggedin"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loggedin)
                    .addComponent(datelastlog, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(datelastlog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loggedin, javax.swing.GroupLayout.PREFERRED_SIZE, 15, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton4.setText(bundle.getString("ControlPanel_Users.jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton4);

        jButton3.setText(bundle.getString("ControlPanel_Users.jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton3);

        jButton2.setText(bundle.getString("ControlPanel_Users.jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton2);

        jButton1.setText(bundle.getString("ControlPanel_Users.jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1);

        add(jPanel6, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        reset();
}//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (dataOwner != null) {
            DatabaseObject dato = dataOwner;
            dato.getPanelData(this);
            if (dato.save()) {
                if (MPView.getUser().equalTo((User) dato)) {
                    MPView.getUser().reset();
                }
            } else {
                showRequiredFields();
            }
        }
}//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (dataOwner == null) {
            dataOwner = new User();
        }
        DatabaseObject dato = dataOwner;
        if (QueryHandler.instanceOf().clone(Context.getUser()).checkUniqueness(Context.getUser().getUniqueColumns(), new JTextField[]{cname.getTextField()})) {
            dato.getPanelData(this);
            dato.setIDS(-1);
            if (dato.save()) {
            } else {
                showRequiredFields();
            }
        }

        refresh();
}//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        Selection s = new Selection(jTable1);
        DatabaseObject obj;
        if (s.checkID()) {
            try {
                obj = DatabaseObject.getObject(Context.getUser(), s.getId());
                setDataOwner(obj,true);
            } catch (NodataFoundException ex) {
                Log.Debug(this, ex);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (Popup.Y_N_dialog(Messages.REALLY_DELETE)) {
            if (dataOwner != null) {
                DatabaseObject dato = dataOwner;
                dato.getPanelData(this);
                dato.delete();
            }
            refresh();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    public void setValues(PropertyStore values) {
    }

    public String getUname() {
        return UNAME;
    }

    public void reset() {
        DatabaseObject dato = dataOwner;

        dato.getPanelData(this);
        dato.reset();
        setDataOwner(dato,true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox accountlist;
    private mpv5.ui.beans.LabeledTextField cname;
    private javax.swing.JComboBox countrylist;
    private javax.swing.JTextField datelastlog;
    private javax.swing.JCheckBox enabled;
    private mpv5.ui.beans.LabeledTextField fullname;
    private javax.swing.JComboBox groupname;
    private javax.swing.JComboBox inthighestright;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private mpv5.ui.beans.LabeledTextField laf;
    private javax.swing.JComboBox language;
    private javax.swing.JComboBox locale;
    private javax.swing.JCheckBox loggedin;
    private mpv5.ui.beans.LabeledTextField mail;
    private javax.swing.JPasswordField password;
    private javax.swing.JCheckBox rgroups;
    private javax.swing.JComboBox statuslist;
    // End of variables declaration//GEN-END:variables
    public String laf_;
    public String language_ = "buildin_en";
    public String locale_ = "en_US";
    public String defcountry_ = "";
    public String fullname_;
    public String mail_;
    public String cname_;
    public String password_;
    public boolean isenabled_;
    public boolean isloggedin_;
    public boolean isrgrouped_;
    public int inthighestright_ = 9;
    public int intdefaultaccount_ = 9;
    public int intdefaultstatus_;
    public int intaddedby_ = 4343;
    public Date datelastlog_ = new Date();
    public int ids_;
    public int groupsids_ ;
    public java.util.Date dateadded_ = new java.util.Date();

    public void collectData() {

        laf_ = laf.get_Text();

        if (language.getSelectedItem() != null) {
            language_ = String.valueOf(((MPComboBoxModelItem) language.getSelectedItem()).getId());
        }

//        if (countrylist.getSelectedItem() != null) {
        defcountry_ = String.valueOf(countrylist.getSelectedItem());
//        }

        if (locale.getSelectedItem() != null) {
            locale_ = String.valueOf(((MPComboBoxModelItem) locale.getSelectedItem()).getId());
        }

        fullname_ = fullname.get_Text();
        mail_ = mail.get_Text();
        cname_ = cname.get_Text();

        if (password.getPassword().length > 0 && !Arrays.equals(password.getPassword(), new JPasswordField().getPassword())) {
            try {
                password_ = MD5HashGenerator.getInstance().hashData(password.getPassword());
            } catch (NoSuchAlgorithmException ex) {
                Log.Debug(ex);
            }
        }
        isenabled_ = enabled.isSelected();

        if (inthighestright.getSelectedItem() != null) {
            inthighestright_ = Integer.valueOf(((MPComboBoxModelItem) inthighestright.getSelectedItem()).getId());
        }

        if (groupname.getSelectedItem() != null) {
            groupsids_ = Integer.valueOf(((MPComboBoxModelItem) groupname.getSelectedItem()).getId());
        } else {
            groupsids_ = 1;
        }

        if (accountlist.getSelectedItem() != null) {
            intdefaultaccount_ = Integer.valueOf(((MPComboBoxModelItem) accountlist.getSelectedItem()).getId());
        } else {
            intdefaultaccount_ = 1;
        }

        if (statuslist.getSelectedItem() != null) {
            intdefaultstatus_ = Integer.valueOf(((MPComboBoxModelItem) statuslist.getSelectedItem()).getId());
        } else {
            intdefaultstatus_ = 1;
        }

        isrgrouped_ = rgroups.isSelected();
    }

    public void exposeData() {

        try {
            laf.set_Text(laf_);
            language.setSelectedIndex(MPComboBoxModelItem.getItemID(language_, language.getModel()));
            locale.setSelectedIndex(MPComboBoxModelItem.getItemID(locale_, locale.getModel()));
            countrylist.setSelectedIndex(MPComboBoxModelItem.getItemIDfromValue(defcountry_, countrylist.getModel()));
            fullname.set_Text(fullname_);
//        password.setText(password_);
            mail.set_Text(mail_);
            cname.set_Text(cname_);
            enabled.setSelected(isenabled_);
            rgroups.setSelected(isrgrouped_);
            inthighestright.setSelectedIndex(MPComboBoxModelItem.getItemID(String.valueOf(inthighestright_), inthighestright.getModel()));
            loggedin.setSelected(isloggedin_);
            datelastlog.setText(DateConverter.getDefDateString(datelastlog_));
            groupname.setSelectedIndex(MPComboBoxModelItem.getItemID(String.valueOf(groupsids_), groupname.getModel()));
            accountlist.setSelectedIndex(MPComboBoxModelItem.getItemID(String.valueOf(intdefaultaccount_), accountlist.getModel()));
            statuslist.setSelectedIndex(MPComboBoxModelItem.getItemIDfromValue(Item.getStatusString(intdefaultstatus_) , statuslist.getModel()));
        } catch (Exception e) {
//            Log.Debug(this, e);
        }
    }

    public DatabaseObject getDataOwner() {
        return dataOwner;
    }

    public void setDataOwner(DatabaseObject object, boolean p) {
        dataOwner = (User) object;
        if (p) {
            dataOwner.setPanelData(this);
            this.exposeData();
        }
    }

    public void refresh() {

        try {
            language.setModel(LanguageManager.getLanguagesAsComboBoxModel());
            locale.setModel(LanguageManager.getLocalesAsComboBoxModel());
            countrylist.setModel(LanguageManager.getCountriesAsComboBoxModel());
        } catch (Exception e) {
            Log.Debug(this, e);
        }

        inthighestright.setModel(MPSecurityManager.getRolesAsComboBoxModel());
        jTable1.setModel(new MPTableModel(new Class[]{String.class, String.class, String.class, String.class, Boolean.class, Boolean.class}, TableFormat.changeToClassValue(QueryHandler.instanceOf().clone(Context.getUser()).select(Context.DETAILS_USERS, (String[]) null), Boolean.class, new int[]{4, 5}), Headers.USER_DETAILS.getValue()));

        groupname.setModel(new DefaultComboBoxModel(
                MPComboBoxModelItem.toItems(new DatabaseSearch(Context.getGroup()).getValuesFor(Context.getGroup().getSubID(), null, ""))));
        accountlist.setModel(new DefaultComboBoxModel(
                MPComboBoxModelItem.toItems(new DatabaseSearch(Context.getAccounts()).getValuesFor(Context.getAccounts().getSubID(), null, ""))));
        statuslist.setModel(MPComboBoxModelItem.toModel(Item.getStatusStrings()));

        TableFormat.stripFirstColumn(jTable1);
        TableFormat.format(jTable1, 1, 120);
        TableFormat.format(jTable1, 4, 80);
        TableFormat.format(jTable1, 5, 80);
    }

    public void paste(DatabaseObject dbo) {
        if (dbo.getDbIdentity().equals(Context.getUser().getDbIdentity())) {
            setDataOwner(dbo,true);
        } else {
            MPView.addMessage(Messages.NOT_POSSIBLE.toString() + Messages.ACTION_PASTE);
        }
    }

    public void showRequiredFields() {
        TextFieldUtils.blinkerRed(fullname);
    }

    public void showSearchBar(boolean show) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Component getAndRemoveActionPanel() {
         this.remove(jPanel6);validate();
        return jPanel6;
    }

    @Override
    public void actionAfterSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
        @Override
    public void actionAfterCreate() {
    }
}