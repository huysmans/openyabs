/*
 * Logger.java
 *
 * Created on 18. Februar 2008, 22:14
 */
package mpv5.logging;

import java.io.File;
import mpv5.utils.files.*;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.Element;
import mpv5.ui.misc.Position;

/**
 *
 *  
 */
public class LogConsole extends javax.swing.JFrame {

    private static File logfile = null;
    public static boolean FILE_LOG_ENABLED = false;
    public static boolean CONSOLE_LOG_ENABLED = false;
    public static boolean WINDOW_LOG_ENABLED = false;
    private static FileReaderWriter logwriter;
    private static final long serialVersionUID = 1L;

    /**
     * Enable/disable log stream targets
     * @param fileLog
     * @param consoleLog
     * @param windowLog
     */
    public static void setLogStreams(boolean fileLog, boolean consoleLog, boolean windowLog) {
        FILE_LOG_ENABLED = fileLog;
        CONSOLE_LOG_ENABLED = consoleLog;
        WINDOW_LOG_ENABLED = windowLog;
    }

    /**
     * Set a file to log to. A null value for file stops file logging
     * @param file The log file
     * @throws java.lang.Exception
     */
    public static void setLogFile(String file) throws Exception {
        if (file != null) {
            LogConsole.logfile = new File(file);
            logfile.delete();
            if (logfile.createNewFile() && !logfile.canWrite()) {
                throw new Exception("Fehler in " + logfile.getCanonicalPath());
            } else {
                FILE_LOG_ENABLED = true;
                logwriter = new FileReaderWriter(logfile);
                Log.Debug(LogConsole.class, "Logging to File: " + logfile.getPath());
            }
        } else {
            FILE_LOG_ENABLED = false;
        }
    }
    private static int line = 0;

    /** Creates new form Logger */
    public LogConsole() {
        initComponents();
    }

    /**
     * Append a new line to the logging object
     * @throws java.io.IOException
     */
    public void log() throws IOException {
        log("\n");
    }

    /**
     * Log the String value of the given Object
     * @param object Null objects will lead to the String "NULL"
     * @throws java.io.IOException Is thrown if an invalid log file is specified
     */
    public synchronized void log(final Object object) throws IOException {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                line++;
                if (FILE_LOG_ENABLED) {
                    logwriter.write(line + ": " + object.toString());
                }
                if (CONSOLE_LOG_ENABLED) {
                    System.out.println(object);
                }
                if (WINDOW_LOG_ENABLED) {
                    if (object != null) {
                        jTextArea1.append("\n" + line + ": " + object.toString());
                    } else {
                        jTextArea1.append("\n" + line + ": " + "NULL");
                    }
                }

                Document document = jTextArea1.getDocument();
                Element rootElem = document.getDefaultRootElement();
                int numLines = rootElem.getElementCount();
                Element lineElem = rootElem.getElement(numLines - 1);
                int lineStart = lineElem.getStartOffset();
                int lineEnd = lineElem.getEndOffset();
                jTextArea1.setCaretPosition(lineStart);
            }
        };
        Thread t = new Thread(runnable);
        t.start();
    }

    /**
     * Show the log konsole (will change the log level to DEBUG and enable Window logging)
     * @return 
     */
    public JComponent open() {
        Log.setLogLevel(Log.LOGLEVEL_DEBUG);
        WINDOW_LOG_ENABLED = true;
        return getRootPane();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MP Logger");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setAutoscrolls(true);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(51, 0, 0));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Flush");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(319, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    flush();
}//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new LogConsole().setVisible(true);
            }
        });
    }

    /**
     * Flush the current logging object
     */
    public void flush() {
        if (FILE_LOG_ENABLED) {
            logwriter.flush();
        }
        if (WINDOW_LOG_ENABLED) {
            jTextArea1.setText(null);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
    // End of variables declaration
}
