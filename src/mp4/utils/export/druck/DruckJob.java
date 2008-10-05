package mp4.utils.export.druck;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import mp4.interfaces.Printable;
import mp4.interfaces.Template;
import mp4.interfaces.Waiter;
import mp4.logs.*;
import mp4.main.Main;

/**
 *
 * @author anti43
 */
public class DruckJob implements Waiter {

    private PrintService prservDflt;
    private PrintService[] prservices;
    int idxPrintService = -1;
    private HashPrintRequestAttributeSet aset;
    private DocFlavor flavor;
    public static boolean FORCE_WIN_PRINT = false;

    public DruckJob() {
        aset = new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A4);
        this.flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        prservDflt = PrintServiceLookup.lookupDefaultPrintService();
        prservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
    }

    public DruckJob(DocFlavor flavor) {
        aset = new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A4);
        this.flavor = flavor;
        prservDflt = PrintServiceLookup.lookupDefaultPrintService();
        prservices = PrintServiceLookup.lookupPrintServices(flavor, aset);

    }

    /*
     * Prints a File
     */
    public void print(File file) throws FileNotFoundException, PrintException {

        if (null == prservices || 0 >= prservices.length) {
            if (null != prservDflt) {
                System.err.println("Nur Default-Printer, da lookupPrintServices fehlgeschlagen.");
                prservices = new PrintService[]{prservDflt};
            }
        }
        Log.Debug("Print-Services:");
        int i;
        for (i = 0; i < prservices.length; i++) {
            Log.Debug("  " + i + ":  " + prservices[i] + ((prservDflt != prservices[i]) ? "" : " (Default)"));
        }
        PrintService prserv = null;
        if (0 <= idxPrintService && idxPrintService < prservices.length) {
            prserv = prservices[idxPrintService];
        } else {
            if (!Arrays.asList(prservices).contains(prservDflt)) {
                prservDflt = null;
            }
            prserv = ServiceUI.printDialog(null, 50, 50, prservices, prservDflt, null, aset);
        }
        if (null != prserv) {
            Log.Debug("Ausgewaehlter Print-Service:");
            Log.Debug("      " + prserv);
            printPrintServiceAttributesAndDocFlavors(prserv);
            DocPrintJob pj = prserv.createPrintJob();
            FileInputStream fis = new FileInputStream(file);
            Doc doc = new SimpleDoc(fis, flavor, null);
            pj.print(doc, aset);
        }

    }

    /*
     * Prints a mp4.interfaces.Printable Object
     */
    private void print(Printable printable) {
        this.flavor = printable.getFlavor();
        try {
            if ((flavor == DocFlavor.INPUT_STREAM.PDF && Main.IS_WINDOWS)) {
                printWIN(printable.getFile());
            } else {
                print(printable.getFile());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            Log.Debug(fileNotFoundException);
        } catch (PrintException printException) {
            Log.Debug(printException);
        }

    }

    public void printWIN(File file) {
        try {
            if (file.getName().contains("pdf")) {
                Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h" + file.getPath());
            } else {
                print(file);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void printPrintServiceAttributesAndDocFlavors(PrintService prserv) {
        String s1 = null, s2;
        Attribute[] prattr = prserv.getAttributes().toArray();
        DocFlavor[] prdfl = prserv.getSupportedDocFlavors();
        if (null != prattr && 0 < prattr.length) {
            for (int i = 0; i < prattr.length; i++) {
                Log.Debug("      PrintService-Attribute[" + i + "]: " + prattr[i].getName() + " = " + prattr[i]);
            }
        }
        if (null != prdfl && 0 < prdfl.length) {
            for (int i = 0; i < prdfl.length; i++) {
                s2 = prdfl[i].getMimeType();
                if (null != s2 && !s2.equals(s1)) {
                    Log.Debug("      PrintService-DocFlavor-Mime[" + i + "]: " + s2);
                }
                s1 = s2;
            }
        }
    }

    /*
     * Prints a File or mp4.interfaces.Printable Object
     */
    public void set(Object object) {
        try {
            try {
                print((Printable) object);
            } catch (ClassCastException ex) {
                if (FORCE_WIN_PRINT) {
                    printWIN((File) object);
                } else {
                    print((File) object);
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            Log.Debug(fileNotFoundException);
        } catch (PrintException printException) {
            Log.Debug(printException);
        }
    }
}
