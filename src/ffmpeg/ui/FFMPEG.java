/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffmpeg.ui;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Nerrosh
 */
public class FFMPEG extends SwingWorker {

    String listacomando[];

    FFMPEG(String listacomando[]) {
        this.listacomando = listacomando;
    }

    @Override
    protected Object doInBackground() throws Exception {
        for (int a = 0; a < listacomando.length; a++) {
            try {

                Process p = Runtime.getRuntime().exec(listacomando[a]);
                //FFMPEG mostra as informações como erro  
                RT errorGobbler = new RT(p.getErrorStream(), "Output");
                // iniciar Thread
                errorGobbler.start();
                int exitVal = p.waitFor();
                System.out.println("ExitValue: " + exitVal);
                //por algum motivo set progress não gosta de valores que mudam colocar int resolveu o problema
                setProgress((int) a + 1);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar converter" + ex);
                return false;
            }

        }
        GUI.status.setText("Concluido");
        GUI.status.setForeground(Color.GREEN);
        return true;

    }

    @Override
    protected void done() {
        super.done(); //To change body of generated methods, choose Tools | Templates.
    }

}
