/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffmpeg.ui;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Nerrosh
 */
public class GUI extends JFrame {

    XML xml = new XML();

    private static void ClearLists(DefaultListModel d1, DefaultListModel d2) {
        d1.clear();
        d2.clear();
    }

    GUI() {
        initComponents();
    }

    private void initComponents() {
        //Utiliza os componentes com o tema do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao aplicar tema do windows" + e);
        }
        //Declaração de variaveis
        DefaultListModel dlmInput = new DefaultListModel();
        DefaultListModel dlmOutput = new DefaultListModel();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JList inputList = new JList(dlmInput);
        JScrollPane inputScroll = new JScrollPane(inputList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        JList outputList = new JList(dlmOutput);
        JScrollPane outputScroll = new JScrollPane(outputList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JLabel labelInput = new JLabel("Arquivo de Entrada: ");
        JLabel labelOutput = new JLabel("Arquivo de Saida: ");
        JLabel labelFfmpeg = new JLabel("Pasta do FFMPEG :");
        JTextField localInput = new JTextField();
        JTextField localOutput = new JTextField();
        JTextField localFfmpeg = new JTextField();
        JButton searchInput = new JButton("Selecionar Arquivo");
        JButton searchFfmpeg = new JButton("Selecionar FFMPEG");
        JButton start = new JButton("Iniciar Conversão");
        JProgressBar pr = new JProgressBar();

        //Verifica XML
        localFfmpeg.setText(xml.SearchXML("FFMPEG"));

        //Tamanhos
        labelInput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelOutput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelFfmpeg.setFont(new Font("Helvetica", Font.PLAIN, 12));
        localInput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        localOutput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        localFfmpeg.setFont(new Font("Helvetica", Font.PLAIN, 12));
        inputList.setFont(new Font("Helvetica", Font.PLAIN, 12));
        outputList.setFont(new Font("Helvetica", Font.PLAIN, 12));
        searchInput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        searchFfmpeg.setFont(new Font("Helvetica", Font.PLAIN, 12));
        start.setFont(new Font("Helvetica", Font.PLAIN, 12));
        pr.setFont(new Font("Helvetica", Font.PLAIN, 12));

        labelInput.setSize(120, 20);
        labelOutput.setSize(120, 20);
        labelFfmpeg.setSize(120, 20);
        localInput.setSize(400, 25);
        localOutput.setSize(400, 25);
        localFfmpeg.setSize(400, 25);
        searchInput.setSize(150, 30);
        searchFfmpeg.setSize(150, 30);
        start.setSize(150, 30);
        pr.setSize(520, 30);
        inputScroll.setSize(500, 140);
        outputScroll.setSize(500, 140);
        panel.setSize(900, 500);

        //Opções Painel
        panel.setLayout(null);

        //Adição ao Painel
        inputScroll.getViewport().add(inputList);
        outputScroll.getViewport().add(outputList);
        panel.add(labelInput);
        panel.add(labelOutput);
        panel.add(labelFfmpeg);
        panel.add(localInput);
        panel.add(localOutput);
        panel.add(localFfmpeg);
        panel.add(searchInput);
        panel.add(searchFfmpeg);
        panel.add(start);
        panel.add(pr);
        panel.add(inputScroll);
        panel.add(outputScroll);

        //Local no painel
        labelFfmpeg.setLocation(30, 25);
        labelInput.setLocation(30, 75);
        labelOutput.setLocation(30, 115);
        localFfmpeg.setLocation(150, 25);
        localInput.setLocation(150, 75);
        localOutput.setLocation(150, 115);
        pr.setLocation(30, 150);
        searchFfmpeg.setLocation(560, 22);
        searchInput.setLocation(560, 72);
        start.setLocation(560, 150);
        inputList.setLocation(1, 1);
        outputList.setLocation(1, 1);
        inputScroll.setLocation(50, 200);
        outputScroll.setLocation(50, 350);
        frame.add(panel);

        //Opções do Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(850, 550);
        frame.setTitle("Teste Filtro Minterpolate FFMPEG");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Ações dos Botões
        searchInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(frame, "Escolha um Arquivo", FileDialog.LOAD);
                fileDialog.setMultipleMode(true);
                fileDialog.setVisible(true);

                if (fileDialog.getFile() != null) {
                    ClearLists(dlmInput, dlmOutput);
                    for (int a = 0; a < fileDialog.getFiles().length; a++) {
                        dlmInput.addElement(fileDialog.getFiles()[a].getAbsolutePath());
                        if (fileDialog.getFiles().length > 1) {
                            dlmOutput.addElement(fileDialog.getDirectory() + "\\Out_" + fileDialog.getFiles()[a].getName());
                        } else {
                            dlmOutput.addElement(fileDialog.getDirectory() + "Out_" + fileDialog.getFiles()[a].getName());
                        }

                    }
                }

            }
        }
        );

        searchFfmpeg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                fc.setSize(500, 500);

                fc.setFileFilter(new FileNameExtensionFilter("Executaveis (*.exe)", "exe"));
                fc.showOpenDialog(frame);

                if (fc.getSelectedFile() != null) {
                    localFfmpeg.setText(fc.getSelectedFile().getAbsolutePath());
                    xml.WriteXml("FFMPEG", fc.getSelectedFile().getAbsolutePath());
                }
            }
        });

        start.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String[] listacomando = new String[dlmInput.getSize()];
                pr.setMaximum(dlmInput.getSize());
                pr.setStringPainted(true);
                //progressWorker.execute();
                String input = "", output = "";
                String intro = "\"" + localFfmpeg.getText() + "\"";
                //formação do comando ffmpeg
                for (int a = 0; a < dlmInput.getSize(); a++) {
                    input = " -i \"" + dlmInput.getElementAt(a) + "\" ";
                    output = "-filter:v \"minterpolate=fps=60:mi_mode=mci:mc_mode=aobmc:me_mode=bidir:vsbmc=1\" -codec:v libx264 -crf 18 -preset slow -threads 1 -c:a copy \"" + dlmOutput.getElementAt(a) + "\" ";
                    listacomando[a] = intro + "" + input + "" + output;
                }
                //execução do comando
                FFMPEG ffmpeg = new FFMPEG(listacomando);

                ffmpeg.addPropertyChangeListener(
                        new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("progress".equals(evt.getPropertyName())) {
                            pr.setValue((Integer) evt.getNewValue());
                            pr.setString((Integer) evt.getNewValue()+" de "+pr.getMaximum()+".");
                            pr.setStringPainted(true);
                        }
                    }
                });
                ffmpeg.execute();

                

                searchInput.setEnabled(false);
                searchFfmpeg.setEnabled(false);
                start.setEnabled(false);
                if(pr.getMaximum()==pr.getValue()){
                searchInput.setEnabled(true);
                searchFfmpeg.setEnabled(true);
                start.setEnabled(true);
                }
                
                ClearLists(dlmInput, dlmOutput);

            }
        }
        );

    }

}
