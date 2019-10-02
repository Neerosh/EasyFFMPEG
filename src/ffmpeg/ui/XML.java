/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffmpeg.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Nerrosh
 */
public class XML {
    
    String preset,miMode,mcMode,meMode,vsbmc,me,scd,ffmpeg;
    int fps,crf;
    float scdThreshold;

    public XML(int fps,int crf,String preset,String miMode,String mcMode,String meMode,String vsbmc,String me,String scd, float scdThreshold) {
        this.fps=fps;
        this.crf=crf;
        this.preset=preset;
        this.miMode=miMode;
        this.mcMode=mcMode;
        this.meMode=meMode;
        this.vsbmc=vsbmc;
        this.me=me;
        this.scd=scd;
        this.scdThreshold=scdThreshold;
    }

    public XML() {
    }

    protected void WriteAll(){
        WriteXml("fps",String.valueOf(fps));
        WriteXml("crf",String.valueOf(crf));
        WriteXml("preset", preset);
        WriteXml("miMode", miMode);
        WriteXml("mcMode", mcMode);
        WriteXml("meMode", meMode);
        WriteXml("vsbmc", vsbmc);
        WriteXml("me", me);
        WriteXml("scd", scd);
        WriteXml("scdThreshold",String.valueOf(scdThreshold));
    }
    
    protected XML ReadAll(){
        fps=Integer.valueOf(ReadXML("fps"));
        crf=Integer.valueOf(ReadXML("crf"));
        preset=ReadXML("preset");
        miMode=ReadXML("miMode");
        mcMode=ReadXML("mcMode");
        meMode=ReadXML("meMode");
        vsbmc=ReadXML("vsbmc");
        me=ReadXML("me");
        scd=ReadXML("scd");
        scdThreshold=Float.valueOf(ReadXML("scdThreshold"));
        return this;
    }
    
    

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
    
    
    protected String ReadXML(String Name) {
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
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "- Favor especificar o caminho do executavel ffmpeg.exe.\n- Exemplo :\n PastaDoPrograma\\ffmpeg\\bin\\ffmpeg.exe",
                     "Arquivo de Configurações Não Encontrado", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
    
    
    
    
}
