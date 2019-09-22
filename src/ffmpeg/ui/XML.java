/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffmpeg.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author Nerrosh
 */
public class XML {
    
    protected void WriteXml(String Name, String Value) {
        try {
            Properties properties = new Properties();
            //adicão da configuração 
            File file = new File("properties.xml");
            properties.setProperty(Name, Value);
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.storeToXML(fileOut, "Configurações");
            fileOut.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected String SearchXML(String Name) {
        try {
            File file = new File("properties.xml");
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.loadFromXML(fileInput);
            fileInput.close();
            //Numeração e passagem por todos as chaves
            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                System.out.println(key + ": " + value);
                if (key.equals(Name)) {
                    return value;
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
}
