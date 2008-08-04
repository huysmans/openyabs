/*
 * customers.java
 *
 * Created on 28. Dezember 2007, 19:17
 */
package mp4.panels.rechnungen;

import java.awt.event.ActionEvent;
import javax.swing.Icon;
import mp4.klassen.objekte.RechnungPosten;
import mp4.klassen.objekte.Rechnung;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import mp3.classes.interfaces.Strings;

import mp3.classes.layer.visual.CustomerPicker;
import mp3.classes.layer.visual.DatePick;
import mp3.classes.utils.Formater;
import mp3.classes.utils.Log;
import mp3.classes.layer.Popup;
import mp4.utils.tabellen.CalculatedTableValues;
import mp4.utils.tabellen.models.PostenTableModel;
import mp3.classes.layer.visual.ProductPicker;
import mp4.datenbank.verbindung.ConnectionHandler;

import mp4.klassen.objekte.Customer;
import mp4.klassen.objekte.HistoryItem;
import mp4.klassen.objekte.Product;
import mp3.classes.visual.main.mainframe;

import handling.pdf.PDF_Rechnung;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import mp4.einstellungen.Einstellungen;

import mp4.einstellungen.Programmdaten;
import mp4.klassen.objekte.Angebot;
import mp4.utils.windows.panelInterface;
import mp4.utils.combobox.CheckComboListener;
import mp4.utils.combobox.CheckComboRenderer;
import mp4.utils.datum.DateConverter;
import mp4.utils.tabellen.DataModelUtils;
import mp4.utils.tabellen.SelectionCheck;
import mp4.utils.tabellen.TableCalculator;
import mp4.utils.tabellen.TableCellEditorForDezimal;
import mp4.utils.tabellen.TableFormat;
import mp4.utils.tabellen.models.BillListTableModel;
import mp4.utils.tabellen.models.BillSearchListTableModel;

/**
 *
 * @author  anti43
 */
public class billsView extends javax.swing.JPanel implements panelInterface, mp4.datenbank.struktur.Tabellen {

    private Rechnung currentBill;
    private String[][] liste;
    private Customer customer;
    private mainframe mainframe;
    private TableCellEditor editor;
    private SimpleDateFormat df;
    private Customer oldcustomer;
    private double defaultTaxRate = 0d;
    private Einstellungen settings;
    private boolean pdf = false;
    private int taxcount = 0;
    private TableCalculator calculator;
    private boolean edited = false;

    /** Creates new form customers
     * @param frame 
     */
    public billsView(mainframe frame) {
        initComponents();

        settings = Einstellungen.instanceOf();
        defaultTaxRate = Double.valueOf(settings.getGlobaltax());
        currentBill = new Rechnung();
        this.customer = new Customer(ConnectionHandler.instanceOf());
        this.mainframe = frame;

        try {
            updateListTable();
            fetchBillsOfTheMonth();
        } catch (Exception e) {
            e.printStackTrace();
            Log.Debug(e.getMessage());
        }

        renewTableModel();
        resizeFields();
        TableFormat.stripFirst(jTable1);

        jTextField7.setText(DateConverter.getDefDateString(new Date()));
        jTextField11.setText(DateConverter.getDefDateString(new Date()));
        this.jCheckBox4.setSelected(Programmdaten.instanceOf().getBILLPANEL_CHECKBOX_MITLIEFERSCHEIN_state());
        jTextField6.setEnabled(false);

        calculator = new TableCalculator(jTable1, this);
        calculator.setBruttoTextField(jTextField8);
        calculator.setTaxTextField(jTextField9);
        calculator.setStopped(false);

        jComboBox1.setModel(new DefaultComboBoxModel(currentBill.getZeilenHandler().getListVorlagen()));
        jComboBox1.addActionListener(new CheckComboListener());
        jComboBox1.setRenderer(new CheckComboRenderer());

    }

    public void addProductToBillsTable(Product product) {
        ((PostenTableModel) jTable1.getModel()).addProduct(jTable1, product);
    }

    private void createNew() {
        CalculatedTableValues calculated;
        PostenTableModel m;

        if (hasCustomer() && validDate()) {

            SelectionCheck selection = new SelectionCheck(jTable1);
            calculated = DataModelUtils.calculateTableCols(jTable1, 0, 3, 4);
            m = (PostenTableModel) jTable1.getModel();

            Rechnung bill = new Rechnung();
            
            bill.setDatum(DateConverter.getDate(jTextField7.getText()));
            bill.setRechnungnummer(bill.getNextBillNumber());
            if (DateConverter.getDate(jTextField11.getText()) != null) {
                bill.setAusfuehrungsDatum(DateConverter.getDate(jTextField11.getText()));
            } else {
                bill.setAusfuehrungsDatum(bill.getDatum());
            }
            bill.setKundenId(getCustomer().getId());
            bill.setGesamtpreis(calculated.getBruttobetrag());
            bill.setBezahlt(jCheckBox2.isSelected());
            bill.setStorno(jCheckBox3.isSelected());
            bill.add(m);

            if (jTextField12.getText() != null && DateConverter.getDate(jTextField12.getText()) != null) {
                if (bill.getAngebot() != null) {
                    bill.getAngebot().setAuftragdatum(DateConverter.getDate(jTextField12.getText()));
                    bill.getAngebot().save();
                } else {
                    bill.setAngebot(new Angebot());
                    bill.getAngebot().setAuftragdatum(DateConverter.getDate(jTextField12.getText()));
                    bill.getAngebot().save();
                }
            }

            if (getCurrent().getZeilenHandler().getList().size() > 0) {
                bill.getZeilenHandler().add(getCurrent().getZeilenHandler().getList());
            }

            if (bill.save()) {

                this.setEdited(false);
                mainframe.setMessage("Rechnung Nummer: " + bill.getRechnungnummer() + " angelegt.");
                new HistoryItem(Strings.BILL, "Rechnung Nummer: " + bill.getRechnungnummer() + " angelegt.");
                setBill(bill);
//                this.setBill(new Rechnung());

                updateListTable();
                resizeFields();
            } else {
                new Popup(Popup.GENERAL_ERROR);
            }
        } else {

            new Popup("Sie m�ssen einen (validen) Kunden ausw�hlen.", Popup.NOTICE);
        }
    }

    private void fetchBillsOfTheMonth() {
        jTextField2.setText(DateConverter.getTodayDefMonth());
        this.jTable3.setModel(new BillSearchListTableModel());
        TableFormat.stripFirst(jTable3);
    }

    private boolean hasValidCurrentBill() {
        return getCurrent().hasId();
    }

    private void nachricht(String string) {
        mainframe.getNachricht().setText(string);
    }

    public void setEdited(boolean bool) {
        this.edited = bool;
    }

    private void renewTableModel() {
        getJTable1().setModel(new PostenTableModel());
        jTable1.getColumnModel().getColumn(1).setCellEditor(new TableCellEditorForDezimal(new JFormattedTextField()));
        jTable1.getColumnModel().getColumn(3).setCellEditor(new TableCellEditorForDezimal(new JFormattedTextField()));
        jTable1.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorForDezimal(new JFormattedTextField()));
        jTable1.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorForDezimal(new JFormattedTextField()));
        TableFormat.stripFirst(jTable1);
    }

    public void resizeFields() {
        TableFormat.resizeCols(jTable1, new Integer[]{null, 40, null, 50, 60, 60}, true);
    }

    public void updateListTable() {
        this.jTable2.setModel(new BillListTableModel());
        TableFormat.stripFirst(jTable2);
        TableFormat.resizeCols(jTable2, new Integer[]{null, 120, 100, null, null, 50, 50}, true);
    }

    public void setBill(Rechnung current) {

        this.currentBill = current;
        this.setCustomer(new Customer(current.getKundenId()));

        if (current.getAngebot() != null) {
            jTextField13.setText(DateConverter.getDefDateString(current.getAngebot().getDatum()));
        } else {
            jTextField13.setText(null);
        }
        this.jTextField6.setText(current.getRechnungnummer());
        jTextField6.setBackground(Color.WHITE);
        this.jTextField7.setText(DateConverter.getDefDateString(current.getDatum()));
        this.jTextField11.setText(DateConverter.getDefDateString(current.getAusfuehrungsDatum()));

        this.jTextField10.setText(current.getMahnungen().toString());

        if (current.isBezahlt()) {
            this.jCheckBox2.setSelected(true);
        } else {
            this.jCheckBox2.setSelected(false);
        }

        if (current.isStorno()) {
            this.jCheckBox3.setSelected(true);
        } else {

            if (current.isVerzug()) {
                this.jCheckBox3.setSelected(false);
                this.jCheckBox2.setBackground(Color.RED);
            } else {
                this.jCheckBox3.setSelected(false);
                this.jCheckBox2.setBackground(new Color(212, 208, 200));
            }
        }

        if (current.getAngebot() != null && current.getAngebot().hasId()) {
            jTextField12.setText(DateConverter.getDefDateString(current.getAngebot().getAuftragdatum()));
            jTextField13.setText(current.getAngebot().getAngebotnummer());
        }

        setBetreffZeilen(current);

        this.getJTable1().setModel(current.getProductlistAsTableModel());
        TableFormat.stripFirst(getJTable1());
        resizeFields();
    }

    public void setCustomer(Customer c) {

        oldcustomer = this.customer;
        this.customer = c;

        jLabel19.setText(c.getKundennummer());
        jTextField5.setText(c.getName());
        jTextField4.setText(c.getFirma());

        if (getCustomer().isDeleted()) {
            jLabel19.setForeground(Color.GRAY);
        }
    }

    private boolean hasCustomer() {
        if (getCustomer() != null) {
            if (getCustomer().getId() != null && customer.getId() > 0 && !customer.isDeleted()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton6 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton12 = new javax.swing.JButton();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable()

        {
            public boolean isCellEditable(int x, int y) {
                return false;
            }
        }
        ;
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField8 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jTextField10 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField11 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable()
        {
            public boolean isCellEditable(int x, int y) {
                return false;
            }
        }

        ;

        jLabel14.setText("jLabel14");

        setBackground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/medium/undo.png"))); // NOI18N
        jButton6.setToolTipText("Rueckgaengig");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton6);

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/medium/tab_remove.png"))); // NOI18N
        jButton20.setToolTipText("Tab schliessen");
        jButton20.setFocusable(false);
        jButton20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton20.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton20);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/medium/filesave.png"))); // NOI18N
        jButton4.setToolTipText("Speichern");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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
        jToolBar1.add(jButton4);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/medium/new_window.png"))); // NOI18N
        jButton3.setToolTipText("Als neue Rechnung anlegen");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator2);

        jButton13.setText("Storno");
        jButton13.setFocusable(false);
        jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }
        });
        jToolBar1.add(jButton13);

        jButton14.setText("Bezahlt");
        jButton14.setFocusable(false);
        jButton14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton14MouseClicked(evt);
            }
        });
        jToolBar1.add(jButton14);

        jButton15.setText("Mahnung");
        jButton15.setFocusable(false);
        jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton15);
        jToolBar1.add(jSeparator1);

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/medium/print_printer.png"))); // NOI18N
        jButton12.setToolTipText("Drucken");
        jButton12.setFocusable(false);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });
        jToolBar1.add(jButton12);

        jCheckBox4.setText("mit Lieferschein");
        jCheckBox4.setFocusable(false);
        jCheckBox4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jCheckBox4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jCheckBox4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox4ItemStateChanged(evt);
            }
        });
        jToolBar1.add(jCheckBox4);

        jCheckBox5.setText("mit Angebot");
        jCheckBox5.setFocusable(false);
        jCheckBox5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(jCheckBox5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setOpaque(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Datens�tze"));

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setAutoscrolls(true);

        jTable3.setAutoCreateRowSorter(true);
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "id", "Nummer", "Datum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setRequestFocusEnabled(false);
        jTable3.setUpdateSelectionOnSort(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Suche"));
        jPanel4.setMaximumSize(new java.awt.Dimension(240, 32767));

        jLabel2.setText("Nummer:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Datum:");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Firma:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3)
                    .addComponent(jTextField2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel( new DefaultTableModel()
        );
        jTable1.setCellSelectionEnabled(true);
        jTable1.setDragEnabled(true);
        jTable1.setFillsViewportHeight(true);
        jTable1.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(jTable1);

        jLabel11.setText("Gesamtbruttopreis");

        jLabel12.setText("Steuer");

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Nettopreise");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jCheckBox2.setText("Bezahlt");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("Storniert");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jTextField10.setEditable(false);
        jTextField10.setText("0");

        jLabel13.setText("Mahnungen");

        jToolBar2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jButton8.setText("Zeile hinzu");
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton8);

        jButton11.setText("Zeile l�schen");
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });
        jToolBar2.add(jButton11);

        jButton7.setText("AC");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton7);
        jToolBar2.add(jSeparator3);

        jButton5.setText("Produkt hinzu");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

        jPanel9.setBackground(new java.awt.Color(227, 219, 202));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextField4.setEditable(false);

        jTextField5.setEditable(false);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/small/search.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Kunde");

        jLabel6.setText("Name");

        jLabel7.setText("Firma");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addGap(17, 17, 17))
        );

        jPanel10.setBackground(new java.awt.Color(227, 219, 202));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Rechnung");

        jLabel10.setText("Datum");

        jTextField7.setEditable(false);
        jTextField7.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jTextField6.setEditable(false);
        jTextField6.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField6MouseClicked(evt);
            }
        });

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/small/search.png"))); // NOI18N
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/small/cal.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jButton10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton10KeyPressed(evt);
            }
        });

        jLabel9.setText("Nummer");

        jComboBox1.setMaximumRowCount(10);
        jComboBox1.setFocusCycleRoot(true);
        jComboBox1.setFocusTraversalPolicyProvider(true);

        jTextField11.setEditable(false);
        jTextField11.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel15.setText("Ausf�hren");

        jLabel16.setText("Betreff");

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/small/edit.png"))); // NOI18N
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/small/cal.png"))); // NOI18N
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jButton17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton17KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField7)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton10, 0, 0, Short.MAX_VALUE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox1, 0, 129, Short.MAX_VALUE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton17, 0, 0, Short.MAX_VALUE)
                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel8))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel9)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap(25, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel16)
                            .addComponent(jButton18)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel15)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17))))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(227, 219, 202));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel18.setText("Nummer");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel17.setText("Angebot");

        jTextField13.setEditable(false);
        jTextField13.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/small/search.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel20.setText("Auftrag");

        jTextField12.setEditable(false);
        jTextField12.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel21.setText("Datum");

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bilder/small/cal.png"))); // NOI18N
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jButton19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton19KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(89, 89, 89))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField13)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(jButton1)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel21)
                    .addComponent(jButton19)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13))
                            .addComponent(jCheckBox3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12))
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox2)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, 0, 169, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Allgemein", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane4.setAutoscrolls(true);

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nummer", "Datum", "Firma", "Ort"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Liste", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setBetreffZeilen(Rechnung current) {

        jComboBox1.setModel(new DefaultComboBoxModel(current.getZeilenHandler().getDisplayListData()));
        jComboBox1.addActionListener(new CheckComboListener());
        jComboBox1.setRenderer(new CheckComboRenderer());
    }

    private void jTextField1ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        this.jTable3.setModel(new BillSearchListTableModel("rechnungnummer", jTextField1.getText()));
        TableFormat.stripFirst(jTable3);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        this.jTable3.setModel(new BillSearchListTableModel("datum", jTextField2.getText()));
        TableFormat.stripFirst(jTable3);
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed

        this.jTable3.setModel(new BillSearchListTableModel("firma", jTextField3.getText()));
        TableFormat.stripFirst(jTable3);
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton3MouseClicked (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked

        createNew();

    }//GEN-LAST:event_jButton3MouseClicked

    private void jTable2MouseClicked (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        SelectionCheck selection = new SelectionCheck(jTable2);

        if (evt.getClickCount() >= 2 && selection.checkID() && evt.getButton() == MouseEvent.BUTTON1) {
            try {
                this.setBill(new Rechnung(selection.getId()));
                jTabbedPane1.setSelectedIndex(0);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    public void save() {

        CalculatedTableValues calculated;
        PostenTableModel m;

        if (hasValidCurrentBill()) {
            if (hasCustomer() && validDate()) {


                SelectionCheck selection = new SelectionCheck(jTable1);
                calculated = DataModelUtils.calculateTableCols(jTable1, 0, 3, 4);
                m = (PostenTableModel) jTable1.getModel();

                Rechnung bill = currentBill;

                bill.setDatum(DateConverter.getDate(jTextField7.getText()));
                bill.setAusfuehrungsDatum(DateConverter.getDate(jTextField11.getText()));
                bill.setKundenId(getCustomer().getId());
                bill.setBezahlt(jCheckBox2.isSelected());
                bill.setStorno(jCheckBox3.isSelected());
                bill.setGesamtpreis(calculated.getBruttobetrag());
                bill.add(m);

                if (jTextField12.getText() != null && DateConverter.getDate(jTextField12.getText()) != null) {
                    if (bill.getAngebot() != null) {
                        bill.getAngebot().setAuftragdatum(DateConverter.getDate(jTextField12.getText()));
                        bill.getAngebot().save();
                    } else {
                        bill.setAngebot(new Angebot());
                        bill.getAngebot().setAuftragdatum(DateConverter.getDate(jTextField12.getText()));
                        bill.getAngebot().save();
                    }
                }

                if (bill.save()) {

                    this.setEdited(false);
                    mainframe.setMessage("Rechnung Nummer: " + bill.getRechnungnummer() + " gespeichert.");
                    new HistoryItem(Strings.BILL, "Rechnung Nummer: " + bill.getRechnungnummer() + " gespeichert.");
                    this.setBill(bill);

                } else {
                    new Popup(Popup.GENERAL_ERROR);
                }
            } else {

                new Popup("Sie m�ssen einen (validen) Kunden ausw�hlen.", Popup.ERROR);
            }

            updateListTable();
            resizeFields();
        } else {
            if (Popup.Y_N_dialog("M�chten Sie die Rechnung anlegen?")) {
                createNew();
            }
        }
    }

    private void jButton4MouseClicked (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        save();
    }//GEN-LAST:event_jButton4MouseClicked

    private void jTable3MouseClicked (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked

        SelectionCheck selection = new SelectionCheck(jTable3);

        if (evt.getClickCount() >= 2 && selection.checkID()) {

            try {
                this.setBill(new Rechnung(selection.getId()));
            } catch (Exception exception) {
                Log.Debug(exception);
            }
        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void clear() {

        this.customer = new Customer();
        this.currentBill = new Rechnung();

        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");

        renewTableModel();
        resizeFields();
    }

    private void jButton7ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        this.clear();
        setEdited(true);

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8MouseClicked (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked

        DataModelUtils.addRowToTable(getJTable1());
        setEdited(true);
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton9ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        new CustomerPicker(this);
        setEdited(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        new DatePick(jTextField7);
        setEdited(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jCheckBox1ItemStateChanged (java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged

        if (jCheckBox1.isSelected()) {
            calculator.setNettoprices(true);
        } else {
            calculator.setNettoprices(false);
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jButton3ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//       
//        jButton3MouseClicked(new MouseEvent(jTable1, WIDTH, WIDTH, WIDTH, WIDTH, WIDTH, WIDTH, nettoprices));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton11MouseClicked (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked

        setEdited(true);
        TableModel m = getJTable1().getModel();

        if (m.getValueAt(getJTable1().getSelectedRow(), 0) != null) {
            RechnungPosten b = new RechnungPosten(ConnectionHandler.instanceOf(), m.getValueAt(getJTable1().getSelectedRow(), 0).toString());
            b.destroy();
            for (int i = 0; i < getJTable1().getColumnCount(); i++) {
                m.setValueAt(null, getJTable1().getSelectedRow(), i);
            }
        } else {
            for (int i = 0; i < getJTable1().getColumnCount(); i++) {
                m.setValueAt(null, getJTable1().getSelectedRow(), i);
            }
        }

    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton10KeyPressed (java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton10KeyPressed
//        jButton1MouseClicked(new MouseEvent(jTable1, WIDTH, WIDTH, WIDTH, WIDTH, WIDTH, WIDTH, nettoprices));
    }//GEN-LAST:event_jButton10KeyPressed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        new ProductPicker(this);
        setEdited(true);
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseClicked
        jButton4MouseClicked(evt);

        if (currentBill != null && currentBill.hasId()) {
            new PDF_Rechnung(currentBill);
            new HistoryItem(ConnectionHandler.instanceOf(), Strings.BILL, "Rechnung Nummer: " + currentBill.getRechnungnummer() + " als PDF erzeugt.");
        }
    }//GEN-LAST:event_jButton12MouseClicked

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked

        this.currentBill.setStorno(true);
        this.jButton4MouseClicked(evt);
        setEdited(true);
    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseClicked

        this.currentBill.setBezahlt(true);
        this.jButton4MouseClicked(evt);
        setEdited(true);
    }//GEN-LAST:event_jButton14MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        if (currentBill.hasId()) {
            new MahnungView(currentBill, customer, this);
        }
        setEdited(true);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTextField6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MouseClicked
        jCheckBox2.setSelected(false);
        jTextField6.setEnabled(true);
    }//GEN-LAST:event_jTextField6MouseClicked

private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
}//GEN-LAST:event_jButton8ActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
}//GEN-LAST:event_jButton4ActionPerformed

private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
    new DatePick(jTextField11);
    setEdited(true);
}//GEN-LAST:event_jButton17ActionPerformed

private void jButton17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton17KeyPressed
}//GEN-LAST:event_jButton17KeyPressed

private void jCheckBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox4ItemStateChanged

    Programmdaten.instanceOf().setBILLPANEL_CHECKBOX_MITLIEFERSCHEIN(jCheckBox4.isSelected());
}//GEN-LAST:event_jCheckBox4ItemStateChanged

private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
}//GEN-LAST:event_jButton16ActionPerformed

private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
    new DatePick(jTextField12);
}//GEN-LAST:event_jButton19ActionPerformed

private void jButton19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton19KeyPressed
}//GEN-LAST:event_jButton19KeyPressed

private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
    new billsNotesEditor(currentBill, this);
    setEdited(true);
}//GEN-LAST:event_jButton18ActionPerformed

private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
    setEdited(true);
}//GEN-LAST:event_jCheckBox2ActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
}//GEN-LAST:event_jButton5ActionPerformed

private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
    setEdited(true);
}//GEN-LAST:event_jCheckBox3ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton10;
    public javax.swing.JButton jButton11;
    public javax.swing.JButton jButton12;
    public javax.swing.JButton jButton13;
    public javax.swing.JButton jButton14;
    public javax.swing.JButton jButton15;
    public javax.swing.JButton jButton16;
    public javax.swing.JButton jButton17;
    public javax.swing.JButton jButton18;
    public javax.swing.JButton jButton19;
    public javax.swing.JButton jButton20;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton7;
    public javax.swing.JButton jButton8;
    public javax.swing.JButton jButton9;
    public javax.swing.JCheckBox jCheckBox1;
    public javax.swing.JCheckBox jCheckBox2;
    public javax.swing.JCheckBox jCheckBox3;
    public javax.swing.JCheckBox jCheckBox4;
    public javax.swing.JCheckBox jCheckBox5;
    public javax.swing.JComboBox jComboBox1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel20;
    public javax.swing.JLabel jLabel21;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel11;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel9;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JToolBar.Separator jSeparator1;
    public javax.swing.JToolBar.Separator jSeparator2;
    public javax.swing.JToolBar.Separator jSeparator3;
    public javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTable1;
    public javax.swing.JTable jTable2;
    public javax.swing.JTable jTable3;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField10;
    public javax.swing.JTextField jTextField11;
    public javax.swing.JTextField jTextField12;
    public javax.swing.JTextField jTextField13;
    public javax.swing.JTextField jTextField2;
    public javax.swing.JTextField jTextField3;
    public javax.swing.JTextField jTextField4;
    public javax.swing.JTextField jTextField5;
    public javax.swing.JTextField jTextField6;
    public javax.swing.JTextField jTextField7;
    public javax.swing.JTextField jTextField8;
    public javax.swing.JTextField jTextField9;
    public javax.swing.JToolBar jToolBar1;
    public javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
    private boolean validDate() {
        if (Formater.check(jTextField7.getText(), Formater.DATE)) {
            return true;
        } else {
            new Popup("Sie m�ssen ein Datum angeben.", Popup.ERROR);
            return false;
        }
    }

    public Rechnung getCurrent() {
        if (currentBill != null) {
            return currentBill;
        } else {
            return new Rechnung(ConnectionHandler.instanceOf());
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public javax.swing.JTextField getJTextField4() {
        return jTextField4;
    }

    public javax.swing.JTable getJTable1() {
        return jTable1;
    }

    public void updateTables() {

        setBetreffZeilen(getCurrent());
        updateListTable();

    }

    public void close() {
        if (isEdited()) {
            if (Popup.Y_N_dialog("�nderungen verwerfen?")) {
                ((JTabbedPane) this.getParent()).remove(this);
            }
        } else {
            ((JTabbedPane) this.getParent()).remove(this);
        }
    }

    public void undo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void redo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void changeTabIcon(Icon icon) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEdited() {
        return edited;
    }
}




    


