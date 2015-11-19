/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iprint;

import applets.ViewerFrame;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author lixiang
 */
public class PrintRunnable implements Runnable {

    private String urlStr;

    public PrintRunnable(String urlStr) {
        urlStr = urlStr.substring(9,urlStr.length());
        try {
            this.urlStr = "http://"+java.net.URLDecoder.decode(urlStr, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PrintRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        if (urlStr.indexOf("view=true") != -1) {
            view();
        } else {
            print();
        }

    }

    public void view() {
        URL url;
        JasperPrint jasperPrint = null;
        try {
            url = new URL(urlStr);
            if (url != null) {
                try {
                    if (jasperPrint == null) {
                        jasperPrint = (JasperPrint) JRLoader.loadObject(url);
                    }
                    if (jasperPrint != null) {
                       // jasperPrint.setProperty("printService", "HP");
                        ViewerFrame viewerFrame = new ViewerFrame(jasperPrint);
                        viewerFrame.setVisible(true);
                    }
                } catch (Exception e) {
                    StringWriter swriter = new StringWriter();
                    PrintWriter pwriter = new PrintWriter(swriter);
                    e.printStackTrace(pwriter);
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Iprint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print() {
        try {
            // Add your handling code here:
            URL url;
            JasperPrint jasperPrint = null;
            url = new URL(urlStr);
            if (url != null) {
                if (jasperPrint == null) {
                    try {
                        jasperPrint = (JasperPrint) JRLoader.loadObject(url);
                    } catch (Exception e) {
                        StringWriter swriter = new StringWriter();
                        PrintWriter pwriter = new PrintWriter(swriter);
                        e.printStackTrace(pwriter);
                    }
                }

                if (jasperPrint != null) {
                    //jasperPrint.setProperty("printService", "HP");
                    final JasperPrint print = jasperPrint;
                    
                    Thread thread = new Thread(
                            new Runnable() {
                                public void run() {
                                    try {
                                        JasperPrintManager.printReport(print, true);
                                    } catch (Exception e) {
                                        StringWriter swriter = new StringWriter();
                                        PrintWriter pwriter = new PrintWriter(swriter);
                                        e.printStackTrace(pwriter);
                                        JOptionPane.showMessageDialog(null, swriter.toString());
                                    }
                                }
                            }
                    );

                    thread.start();
                }

            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(PrintRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
