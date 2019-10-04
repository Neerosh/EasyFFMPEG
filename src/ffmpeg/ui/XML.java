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

    private String preset, miMode, mcMode, meMode, vsbmc, me, scd, ffmpeg, fps, crf, scdThreshold;
    Properties properties = new Properties();
    File file = new File("properties.xml");

    public XML(String fps, String crf, String ffmpeg, String preset, String miMode, String mcMode, String meMode, String vsbmc, String me, String scd, String scdThreshold) {
        this.fps = fps;
        this.crf = crf;
        this.ffmpeg = ffmpeg;
        this.preset = preset;
        this.miMode = miMode;
        this.mcMode = mcMode;
        this.meMode = meMode;
        this.vsbmc = vsbmc;
        this.me = me;
        this.scd = scd;
        this.scdThreshold = scdThreshold;
    }
    
    public XML(){}

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String getMiMode() {
        return miMode;
    }

    public void setMiMode(String miMode) {
        this.miMode = miMode;
    }

    public String getMcMode() {
        return mcMode;
    }

    public void setMcMode(String mcMode) {
        this.mcMode = mcMode;
    }

    public String getMeMode() {
        return meMode;
    }

    public void setMeMode(String meMode) {
        this.meMode = meMode;
    }

    public String getVsbmc() {
        return vsbmc;
    }

    public void setVsbmc(String vsbmc) {
        this.vsbmc = vsbmc;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getScd() {
        return scd;
    }

    public void setScd(String scd) {
        this.scd = scd;
    }

    public String getFfmpeg() {
        return ffmpeg;
    }

    public void setFfmpeg(String ffmpeg) {
        this.ffmpeg = ffmpeg;
    }

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }

    public String getCrf() {
        return crf;
    }

    public void setCrf(String crf) {
        this.crf = crf;
    }

    public String getScdThreshold() {
        return scdThreshold;
    }

    public void setScdThreshold(String scdThreshold) {
        this.scdThreshold = scdThreshold;
    }


    protected void WriteAll() {
        properties.setProperty("fps", fps);
        properties.setProperty("crf", crf);
        properties.setProperty("ffmpeg", ffmpeg);
        properties.setProperty("preset", preset);
        properties.setProperty("miMode", miMode);
        properties.setProperty("mcMode", mcMode);
        properties.setProperty("meMode", meMode);
        properties.setProperty("vsbmc", vsbmc);
        properties.setProperty("me", me);
        properties.setProperty("scd", scd);
        properties.setProperty("scdThreshold", scdThreshold);
        StoreXml();
        ReadAll();

    }
    
    
    protected void ReadAll() {   
        fps = ReadXML("fps");
        crf = ReadXML("crf");
        ffmpeg = ReadXML("ffmpeg");
        preset = ReadXML("preset");
        miMode = ReadXML("miMode");
        mcMode = ReadXML("mcMode");
        meMode = ReadXML("meMode");
        vsbmc = ReadXML("vsbmc");
        me = ReadXML("me");
        scd = ReadXML("scd");
        scdThreshold = ReadXML("scdThreshold");

    }

    protected void StoreXml() {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.storeToXML(fileOut, null);
            fileOut.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    } // criar um metodo para adicionar propriedades e outro para escrever ao xml quando tudo for finalizado

    protected void WriteXml(String Name, String Value) {
        System.out.println("\n" + Name + " " + Value);
        properties.setProperty(Name, Value);
    }

    protected String ReadXML(String Name) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            properties.loadFromXML(fileIn);
            //Numeração e passagem por todos as chaves
            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                if (key.equals(Name)) {
                    //System.out.println(key + ": " + value);
                    return value;
                }

            }
        } catch (FileNotFoundException e) {
            if (Name.equals("ffmpeg")) {
                JOptionPane.showMessageDialog(null, "- Favor especificar o caminho do executavel ffmpeg.exe.\n- Exemplo :\n PastaDoPrograma\\ffmpeg\\bin\\ffmpeg.exe",
                        "Arquivo de Configurações Não Encontrado", JOptionPane.WARNING_MESSAGE);
                return "0";
            }
        } catch (Exception e) {
            
            System.out.println(e);
            return "0";
        }
        return "0";
    }

}
