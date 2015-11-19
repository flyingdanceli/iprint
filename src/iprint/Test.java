/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iprint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

/**
 *
 * @author lixiang
 */
public class Test {
    public static void main(String args[]){
        

     
        //设置打印数据的格式，此处为图片gif格式  
        DocFlavor psInFormat = DocFlavor.INPUT_STREAM.PNG;  
        //创建打印数据  
//      DocAttributeSet docAttr = new HashDocAttributeSet();//设置文档属性  
//      Doc myDoc = new SimpleDoc(psStream, psInFormat, docAttr);  
//        Doc myDoc = new SimpleDoc(psStream, psInFormat, null);  
          
        //设置打印属性  
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();  
        aset.add(new Copies(1));//打印份数，3份  
        PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);  
  
        // this step is necessary because I have several printers configured  
        //将所有查找出来的打印机与自己想要的打印机进行匹配，找出自己想要的打印机  
        PrintService myPrinter = null;  
        for (int i = 0; i < services.length; i++) {  
            System.out.println("=========================================service found: " + services[i]);  
            String svcName = services[i].toString();  
            if (svcName.contains("Snagit 11")|| true) {  
                myPrinter = services[i];  
                System.out.println("my printer found: " + svcName);  
                //System.out.println("my printer found: " + myPrinter);  
                String nm = System.getProperty("java.awt.printerjob", null);
                 System.out.println("my printer nm: " + nm);  
               //可以输出打印机的各项属性  
                AttributeSet att = myPrinter.getAttributes();  
                for (Attribute a : att.toArray()) {  

                    String attributeName;  
                    String attributeValue;  

                    attributeName = a.getName();  
                    attributeValue = att.get(a.getClass()).toString();  

                    System.out.println(attributeName + " : " + attributeValue);  
                }  
            }  
        }  
    }
}
