/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SplashScreen.java
 *
 * Created on 30.03.2009, 21:55:52
 */
package mpv5.ui.dialogs;

import javax.swing.DefaultComboBoxModel;
import mpv5.db.common.Context;
import mpv5.db.common.DatabaseObject;
import mpv5.db.common.DatabaseSearch;
import mpv5.db.common.NodataFoundException;
import mpv5.ui.frames.MPView;
import mpv5.utils.html.HtmlParser;
import mpv5.utils.models.MPTableModel;
import mpv5.utils.tables.TableFormat;

/**
 *
 *  
 */
public class Search2 extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;
    private String additionalSearchCondition = "";
    private static String oldSelection;

    public static DatabaseObject showSearchFor(Context t) {
        Search2 s = new Search2(t);
        s.setVisible(true);
        s.requestFocus();
        return s.getSelectedObject();
    }
    private Context context;
    private DatabaseObject selection;

    @Override
    public void dispose() {
        setVisible(false);
    }

    /** Creates new form SplashScreen
     * @param addtabs Add the selected Object to the main tab pane
     */
    private Search2(Context t) {
        super(MPView.getIdentifierFrame());
        initComponents();
        context = t;
        typelabel.setText(t.getDbIdentity().toUpperCase());
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(MPView.getIdentifierFrame());
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
        jLabel2 = new javax.swing.JLabel();
        key = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        typelabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        java.util.ResourceBundle bundle = mpv5.i18n.LanguageManager.getBundle(); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("Search2.jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(bundle.getString("Search2.jLabel1.text")); // NOI18N
        jLabel1.setToolTipText(bundle.getString("Search2.jLabel1.toolTipText")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(bundle.getString("Search2.jLabel2.text")); // NOI18N
        jLabel2.setToolTipText(bundle.getString("Search2.jLabel2.toolTipText")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        key.setEditable(true);
        key.setName("key"); // NOI18N
        key.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyActionPerformed(evt);
            }
        });

        jCheckBox1.setText(bundle.getString("Search2.jCheckBox1.text")); // NOI18N
        jCheckBox1.setToolTipText(bundle.getString("Search2.jCheckBox1.toolTipText")); // NOI18N
        jCheckBox1.setName("jCheckBox1"); // NOI18N

        jButton1.setText(bundle.getString("Search2.jButton1.text")); // NOI18N
        jButton1.setToolTipText(bundle.getString("Search2.jButton1.toolTipText")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(bundle.getString("Search2.jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

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
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        typelabel.setText(bundle.getString("Search2.typelabel.text")); // NOI18N
        typelabel.setName("typelabel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typelabel, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(key, 0, 163, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(typelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(key, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

         if (context != null) {
                try {
                    selection = DatabaseObject.getObject(context, Integer.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
                } catch (Exception ex) {
                    mpv5.logging.Log.Debug(this, ex.getMessage());
                }
                this.dispose();
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        if (evt.getClickCount() > 1) {
            if (context != null) {
                try {
                    selection = DatabaseObject.getObject(context, Integer.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
                } catch (NodataFoundException ex) {
                    mpv5.logging.Log.Debug(ex);
                }
                this.dispose();
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        search();
}//GEN-LAST:event_jButton1ActionPerformed

    private void keyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyActionPerformed
        search();
}//GEN-LAST:event_keyActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox key;
    private javax.swing.JLabel typelabel;
    // End of variables declaration//GEN-END:variables

    private void search() {
        String newSelection = (String) key.getSelectedItem();

        if (newSelection != null && !newSelection.equals(oldSelection)) {
            key.addItem(newSelection);
            oldSelection = newSelection;
        }

        if (newSelection == null || newSelection.equals("null")) {
            newSelection = "";
        }

        Object[][] data = null;

        context.addReference(Context.getGroup());
        DatabaseSearch s = new DatabaseSearch(context);
        data = s.getValuesFor(context.getDbIdentity() + ".ids," + context.getDbIdentity() + ".cname," + "groups.cname," + context.getDbIdentity() + ".dateadded", new String[]{"cname"}, newSelection, !jCheckBox1.isSelected());
        jTable1.setModel(new MPTableModel(data));


        TableFormat.stripFirstColumn(jTable1);
        TableFormat.format(jTable1, 1, 100);
    }

    private DatabaseObject getSelectedObject() {
        return selection;
    }

    /**
     * @return the additionalSearchCondition
     */
    public String getAdditionalSearchCondition() {
        return additionalSearchCondition;
    }

    /**
     * @param additionalSearchCondition the additionalSearchCondition to set
     */
    public void setAdditionalSearchCondition(String additionalSearchCondition) {
        this.additionalSearchCondition = additionalSearchCondition;
    }
}
