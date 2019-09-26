/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffmpeg.ui;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Nerrosh
 */
public class GUI extends JFrame {

    XML xml = new XML();
    static JTextArea output = new JTextArea();
    static JLabel status = new JLabel("Não iniciado");

    private static void ClearLists(DefaultListModel d1) {
        d1.clear();
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
        JScrollPane inputScroll = new JScrollPane(inputList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane outputScroll = new JScrollPane(output, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Borda
        inputScroll.setBorder(BorderFactory.createTitledBorder(null, "Arquivos Selecionados:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.PLAIN, 12)));
        outputScroll.setBorder(BorderFactory.createTitledBorder(null, "Texto de Saida (CMD):", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.PLAIN, 12)));

        //propriedades label status
        status.setHorizontalAlignment(JLabel.CENTER);
        status.setVerticalAlignment(JLabel.CENTER);
        status.setForeground(Color.BLUE);

        //auto scroll (puxa para o texto mais recente)
        DefaultCaret caret = (DefaultCaret) output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        //format
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setGroupingUsed(false);
        //Valores
        String[] miModeList = {"mci", "blend", "dub"};
        String[] mcModeList = {"obmc", "aobmc"};
        String[] meModeList = {"bidir","bilat"};

        JLabel labelInput = new JLabel("Arquivo de Entrada: ");
        JLabel labelOutput = new JLabel("Arquivo de Saida: ");
        JLabel labelFfmpeg = new JLabel("Pasta do FFMPEG :");
        JLabel labelFps = new JLabel("Frames por Segundo :");
        JLabel labelMiMode = new JLabel("Mi_Mode :");
        JLabel labelMcMode = new JLabel("Mc_Mode :");
        JLabel labelMeMode = new JLabel("Me_Mode :");
        JFormattedTextField fps = new JFormattedTextField(nf);
        JComboBox miMode = new JComboBox(miModeList);
        JComboBox mcMode = new JComboBox(mcModeList);
        JComboBox meMode = new JComboBox(meModeList);
        JTextField localInput = new JTextField();
        JTextField localOutput = new JTextField();
        JTextField localFfmpeg = new JTextField();
        JButton searchInput = new JButton("Selecionar Arquivo");
        JButton searchFfmpeg = new JButton("Selecionar FFMPEG");
        JButton start = new JButton("Iniciar Conversão");
        JButton remove = new JButton("Remover Selecionado");
        JProgressBar pr = new JProgressBar();

        //Verifica XML
        localFfmpeg.setText(xml.SearchXML("FFMPEG"));

        //valores iniciais / configuração de campos
        miMode.setSelectedIndex(0);
        mcMode.setSelectedIndex(1);
        meMode.setSelectedIndex(0);
        fps.setText("60");

        //Tamanhos fontes
        labelInput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelOutput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelFfmpeg.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelFps.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelMiMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelMcMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelMeMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        fps.setFont(new Font("Helvetica", Font.PLAIN, 12));
        miMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        mcMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        meMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        localInput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        localOutput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        localFfmpeg.setFont(new Font("Helvetica", Font.PLAIN, 12));
        inputList.setFont(new Font("Helvetica", Font.PLAIN, 12));
        output.setFont(new Font("Helvetica", Font.PLAIN, 12));
        status.setFont(new Font("Helvetica", Font.BOLD, 14));
        searchInput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        searchFfmpeg.setFont(new Font("Helvetica", Font.PLAIN, 12));
        start.setFont(new Font("Helvetica", Font.PLAIN, 12));
        remove.setFont(new Font("Helvetica", Font.PLAIN, 12));
        pr.setFont(new Font("Helvetica", Font.PLAIN, 12));

        
        //Tamanhos frame
        labelFfmpeg.setSize(120, 20);
        localFfmpeg.setSize(480, 25);
        searchFfmpeg.setSize(150, 30);
        
        labelFps.setSize(130, 20);
        fps.setSize(50, 25);
        labelMiMode.setSize(60, 20);
        miMode.setSize(60, 25);
        labelMcMode.setSize(60, 20);
        mcMode.setSize(70, 25);
        labelMeMode.setSize(60,20);
        meMode.setSize(60,25);

        labelInput.setSize(120, 20);
        localInput.setSize(480, 25);
        searchInput.setSize(150, 30);
        
        labelOutput.setSize(120, 20);
        localOutput.setSize(480, 25);

        pr.setSize(600, 30);
        start.setSize(150, 30);
        status.setSize(150, 30);

        inputScroll.setSize(600, 140);
        remove.setSize(160, 30);
        outputScroll.setSize(750, 140);
        
        panel.setSize(900, 500);

        //Opções Painel
        panel.setLayout(null);

        //Adição ao Painel
        inputScroll.getViewport().add(inputList);
        outputScroll.getViewport().add(output);
        
        panel.add(labelFfmpeg);
        panel.add(localFfmpeg);
        panel.add(searchFfmpeg);
        panel.add(labelFps);
        panel.add(fps);
        panel.add(labelMiMode);
        panel.add(miMode);
        panel.add(labelMcMode);
        panel.add(mcMode);
        panel.add(labelMeMode);
        panel.add(meMode);
        panel.add(labelInput);
        panel.add(localInput);
        panel.add(searchInput);
        panel.add(labelOutput);
        panel.add(localOutput);
        panel.add(pr);
        panel.add(start);
        panel.add(status);
        panel.add(inputScroll);
        panel.add(remove);
        panel.add(outputScroll);
        
        

        //Local no painel (ordem cima para baixo)
        labelFfmpeg.setLocation(30, 20);
        localFfmpeg.setLocation(150, 20);
        searchFfmpeg.setLocation(670, 17);
        
        labelFps.setLocation(15, 60);
        fps.setLocation(150, 60);
        labelMiMode.setLocation(210, 60);
        miMode.setLocation(270, 60);
        labelMcMode.setLocation(350, 60);
        mcMode.setLocation(413, 60);
        labelMeMode.setLocation(500,60);
        meMode.setLocation(563, 60);
        
        labelInput.setLocation(30, 140);
        localInput.setLocation(150, 140);
        searchInput.setLocation(670, 137);
        
        labelOutput.setLocation(30, 180);
        localOutput.setLocation(150, 180);

        pr.setLocation(30, 215);
        start.setLocation(670, 213);
        status.setLocation(670, 240);

        inputList.setLocation(1, 1);
        inputScroll.setLocation(30, 250);
        remove.setLocation(665, 350);
        
        outputScroll.setLocation(30, 400);
        
        frame.add(panel);

        //Opções do Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(850, 600);
        frame.setTitle("Teste Filtro Minterpolate FFMPEG");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        miMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (miMode.getSelectedItem().toString().equals("mci")) {
                    mcMode.setEnabled(true);
                } else {
                    mcMode.setEnabled(false);
                }
            }
        });

        //Ações dos Botões
        searchInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(frame, "Escolha um Arquivo", FileDialog.LOAD);
                fileDialog.setMultipleMode(true);
                fileDialog.setVisible(true);

                if (fileDialog.getFile() != null) {
                    if (fileDialog.getFiles().length <=1 ) {
                        localInput.setText(fileDialog.getDirectory()+fileDialog.getFile());
                        localOutput.setText(fileDialog.getDirectory() + "Out_" + fileDialog.getFile());
                    } else {
                        localInput.setText("Multiplos Arquivos");
                        localOutput.setText("Multiplos Arquivos");
                    }
                    ClearLists(dlmInput);
                    ClearLists(dlmOutput);
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

        status.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("text".equals(evt.getPropertyName()) && evt.getNewValue().equals("Concluido")) {
                    searchInput.setEnabled(true);
                    searchFfmpeg.setEnabled(true);
                    start.setEnabled(true);
                    remove.setEnabled(true);

                    ClearLists(dlmInput);
                    ClearLists(dlmOutput);
                }
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String[] listacomando = new String[dlmInput.getSize()];
                status.setText("Em Andamento");
                status.setForeground(Color.RED);
                pr.setMaximum(dlmInput.getSize());
                pr.setValue(0);
                pr.setString(0 + " de " + dlmInput.getSize());
                pr.setStringPainted(true);
                //progressWorker.execute();
                String input = "", output = "";
                String intro = "\"" + localFfmpeg.getText() + "\"";
                //formação do comando ffmpeg
                for (int a = 0; a < dlmInput.getSize(); a++) {
                    input = " -i \"" + dlmInput.getElementAt(a) + "\" ";
                    output = "-filter:v \"minterpolate=fps=" + fps.getText() + ":mi_mode=" + miMode.getSelectedItem();
                    if (miMode.getSelectedItem().toString().equals("mci") && mcMode.getSelectedItem() != "") {
                        output = output + ":mc_mode=" + mcMode.getSelectedItem();
                    }
                    output = output + ":me_mode="+meMode.getSelectedItem()+":vsbmc=1\" -codec:v libx264 -crf 18 -preset slow -threads 1 -c:a copy \"" + dlmOutput.getElementAt(a) + "\" ";

                    listacomando[a] = intro + "" + input + "" + output;
                }
                //execução do comando

                FFMPEG ffmpeg = new FFMPEG(listacomando);
                ffmpeg.addPropertyChangeListener(
                        new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("progress".equals(evt.getPropertyName())) {
                            pr.setValue((Integer) evt.getNewValue());
                            pr.setString((Integer) evt.getNewValue() + " de " + pr.getMaximum() + ".");
                            pr.setStringPainted(true);
                        }
                    }
                });
                ffmpeg.execute();
                searchInput.setEnabled(false);
                searchFfmpeg.setEnabled(false);
                start.setEnabled(false);
                remove.setEnabled(false);

            }
        }
        );
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputList.getSelectedIndex() >= 0) {
                    dlmOutput.removeElementAt(inputList.getSelectedIndex());
                    dlmInput.removeElementAt(inputList.getSelectedIndex());
                }
            }
        });

    }

}
