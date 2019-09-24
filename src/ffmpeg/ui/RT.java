/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffmpeg.ui;

/**
 *
 * @author Nerrosh
 */
import java.io.*;

class RT extends Thread {

    InputStream is;
    String type;

    RT(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public void run() {

        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
            GUI.output.setText(GUI.output.getText()+"\n "+line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
