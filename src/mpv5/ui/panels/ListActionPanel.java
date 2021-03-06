/*
 *      This is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License Version 2.1 as published by
 *      the Free Software Foundation, either version 2.1 of the License, or
 *      (at your option) any later version.
 *
 *      This is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *      GNU Lesser General Public License Version 2.1 for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License Version 2.1
 *      along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * ListActionPanel.java
 *
 * Created on 05.05.2012, 11:46:55
 */
package mpv5.ui.panels;

import groovy.lang.Closure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import mpv5.db.common.DatabaseObject;

/**
 *
 * @author andreas
 */
public class ListActionPanel extends javax.swing.JPanel {

    private final Closure<Void> closure;

    /** Creates new form ListActionPanel
     * @param model
     * @param closure  
     */
    public ListActionPanel(List<DatabaseObject> model, Closure<Void> closure) {
        initComponents();
        setupFilter();
        this.closure = closure;
        Object[][] d = new Object[model.size()][];
        for (int i = 0; i < model.size(); i++) {
            d[i] = new Object[]{Boolean.TRUE, model.get(i)};
        }

        jTable1.setModel(new DefaultTableModel(d, new String[]{
                    "Title 1", "Title 2"
                }));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        filterText = new mpv5.ui.beans.LabeledTextField();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        filterText.set_Label("Filter");
        filterText.setName("filterText"); // NOI18N
        jToolBar1.add(filterText);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

        jButton1.setText("OK");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMaximumSize(new java.awt.Dimension(100, 20));
        jButton1.setMinimumSize(new java.awt.Dimension(15, 20));
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(33, 20));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        add(jToolBar2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        handleSelected();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mpv5.ui.beans.LabeledTextField filterText;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables

    private void setupFilter() {
        final JTable table = jTable1;
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = filterText.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }
        });
    }

    private void handleSelected() {
        TableModel m = jTable1.getModel();
        for (int i = 0; i < m.getRowCount(); i++) {
            if ((Boolean) m.getValueAt(i, 0)) {
                closure.call(m.getValueAt(i, 1));
            }
        }
    }

  
}
