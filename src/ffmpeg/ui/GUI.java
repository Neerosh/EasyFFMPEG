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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
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
            UIManager.put("OptionPane.titleFont", new Font("Helvetica", Font.PLAIN, 12));
            UIManager.put("OptionPane.messageFont", new Font("Helvetica", Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont", new Font("Helvetica", Font.PLAIN, 14));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao aplicar tema do sistema, ou alterar as opções de interface\n" + e, "Erro UIManager", JOptionPane.ERROR_MESSAGE);
        }
        //Declaração de variaveis
        DefaultListModel dlmInput = new DefaultListModel();
        DefaultListModel dlmOutput = new DefaultListModel();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JList inputList = new JList(dlmInput);
        JScrollPane inputScroll = new JScrollPane(inputList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane outputScroll = new JScrollPane(output, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPanel optionsFfmpeg = new JPanel();

        //Borda
        inputScroll.setBorder(BorderFactory.createTitledBorder(null, "Arquivos Selecionados :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.PLAIN, 12)));
        outputScroll.setBorder(BorderFactory.createTitledBorder(null, "Texto de Saida (CMD) :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.PLAIN, 12)));
        optionsFfmpeg.setBorder(BorderFactory.createTitledBorder(null, "Opções FFMPEG :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.PLAIN, 12)));

        //propriedades label status
        status.setHorizontalAlignment(JLabel.CENTER);
        status.setVerticalAlignment(JLabel.CENTER);
        status.setForeground(Color.BLUE);

        //auto scroll (puxa para o texto mais recente)
        DefaultCaret caret = (DefaultCaret) output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        //format
        NumberFormat nfInteger = NumberFormat.getIntegerInstance();
        nfInteger.setGroupingUsed(false);

        NumberFormat nf2 = NumberFormat.getNumberInstance();
        //nf2.setGroupingUsed(false);
        //Valores
        String[] miModeList = {"mci", "blend", "dub"};
        String[] mcModeList = {"obmc", "aobmc"};
        String[] meModeList = {"bidir", "bilat"};
        String[] vsbmcList = {"Habilitado", "Desabilitado"};
        String[] meList = {"esa", "tss", "tlds", "ntss", "fss", "ds", "hexbs", "epzs", "umh"};
        String[] scdList = {"Desabilitado", "fdiff"};

        JLabel labelInput = new JLabel("Arquivo de Entrada :");
        JLabel labelOutput = new JLabel("Arquivo de Saida :");
        JLabel labelFfmpeg = new JLabel("Pasta do FFMPEG :");
        JLabel labelFps = new JLabel("Frames por Segundo :");
        JLabel labelMiMode = new JLabel("MI Mode :");
        JLabel labelMcMode = new JLabel("MC Mode :");
        JLabel labelMeMode = new JLabel("ME Mode :");
        JLabel labelVsbmc = new JLabel("VSBMC :");
        JLabel labelMe = new JLabel("ME :");
        JLabel labelScd = new JLabel("SCD :");
        JLabel labelScd_threshold = new JLabel("SCD threshold :");
        JFormattedTextField fps = new JFormattedTextField(nfInteger);
        JFormattedTextField scd_threshold = new JFormattedTextField(nf2);
        JComboBox miMode = new JComboBox(miModeList);
        JComboBox mcMode = new JComboBox(mcModeList);
        JComboBox meMode = new JComboBox(meModeList);
        JComboBox vsbmc = new JComboBox(vsbmcList);
        JComboBox scd = new JComboBox(scdList);
        JComboBox me = new JComboBox(meList);

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
        vsbmc.setSelectedIndex(0);
        me.setSelectedIndex(7);
        fps.setText("60");
        scd.setSelectedIndex(1);
        scd_threshold.setText("5.0");

        //Tamanhos fontes
        labelInput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelOutput.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelFfmpeg.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelFps.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelMiMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelMcMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelMeMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelVsbmc.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelMe.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelScd.setFont(new Font("Helvetica", Font.PLAIN, 12));
        labelScd_threshold.setFont(new Font("Helvetica", Font.PLAIN, 12));
        fps.setFont(new Font("Helvetica", Font.PLAIN, 12));
        scd_threshold.setFont(new Font("Helvetica", Font.PLAIN, 12));
        miMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        mcMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        meMode.setFont(new Font("Helvetica", Font.PLAIN, 12));
        vsbmc.setFont(new Font("Helvetica", Font.PLAIN, 12));
        me.setFont(new Font("Helvetica", Font.PLAIN, 12));
        scd.setFont(new Font("Helvetica", Font.PLAIN, 12));
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
        labelMeMode.setSize(60, 20);
        meMode.setSize(60, 25);
        labelVsbmc.setSize(60, 20);
        vsbmc.setSize(100, 20);
        labelMe.setSize(60, 20);
        me.setSize(60, 20);
        labelScd.setSize(60, 20);
        scd.setSize(100, 20);
        labelScd_threshold.setSize(90, 20);
        scd_threshold.setSize(40, 25);

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

        panel.setSize(850, 650);
        optionsFfmpeg.setSize(620, 110);
        //Opções Painel
        panel.setLayout(null);
        optionsFfmpeg.setLayout(null);

        //Adição ao Painel
        inputScroll.getViewport().add(inputList);
        outputScroll.getViewport().add(output);
        optionsFfmpeg.add(labelFps);
        optionsFfmpeg.add(fps);
        optionsFfmpeg.add(labelMiMode);
        optionsFfmpeg.add(miMode);
        optionsFfmpeg.add(labelMcMode);
        optionsFfmpeg.add(mcMode);
        optionsFfmpeg.add(labelMeMode);
        optionsFfmpeg.add(meMode);
        optionsFfmpeg.add(labelVsbmc);
        optionsFfmpeg.add(vsbmc);
        optionsFfmpeg.add(labelMe);
        optionsFfmpeg.add(me);
        optionsFfmpeg.add(labelScd);
        optionsFfmpeg.add(scd);
        optionsFfmpeg.add(labelScd_threshold);
        optionsFfmpeg.add(scd_threshold);

        panel.add(labelFfmpeg);
        panel.add(localFfmpeg);
        panel.add(searchFfmpeg);
        panel.add(optionsFfmpeg);

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

        optionsFfmpeg.setLocation(15, 55);

        labelFps.setLocation(10, 30);
        fps.setLocation(140, 27);
        labelMiMode.setLocation(200, 30);
        miMode.setLocation(260, 30);
        labelMcMode.setLocation(330, 30);
        mcMode.setLocation(395, 30);
        labelMeMode.setLocation(480, 30);
        meMode.setLocation(545, 30);

        labelVsbmc.setLocation(40, 70);
        vsbmc.setLocation(95, 70);
        labelMe.setLocation(205, 70);
        me.setLocation(235, 70);
        labelScd.setLocation(305, 70);
        scd.setLocation(345, 70);
        labelScd_threshold.setLocation(455, 70);
        scd_threshold.setLocation(550, 67);

        labelInput.setLocation(30, 180);
        localInput.setLocation(150, 180);
        searchInput.setLocation(670, 177);

        labelOutput.setLocation(30, 220);
        localOutput.setLocation(150, 220);

        pr.setLocation(30, 255);
        start.setLocation(670, 253);
        status.setLocation(670, 280);

        inputList.setLocation(1, 1);
        inputScroll.setLocation(30, 290);
        remove.setLocation(665, 390);

        outputScroll.setLocation(30, 440);

        frame.add(panel);

        //Opções do Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(850, 650);
        frame.setTitle("EasyFFMPEG");
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

        scd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scd.getSelectedItem().toString().equals("fdiff")) {
                    scd_threshold.setEnabled(true);
                } else {
                    scd_threshold.setEnabled(false);
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
                    if (fileDialog.getFiles().length <= 1) {
                        localInput.setText(fileDialog.getDirectory() + fileDialog.getFile());
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
                String input = "", output = "";
                String intro = "\"" + localFfmpeg.getText() + "\"";

                //formação do comando ffmpeg
                for (int a = 0; a < dlmInput.getSize(); a++) {
                    //sem necessidade de limpar as string somente mudar o valor
                    input = " -i \"" + dlmInput.getElementAt(a) + "\" ";
                    output = "-filter:v \"minterpolate=fps=" + fps.getText() + ":mi_mode=" + miMode.getSelectedItem();

                    if (miMode.getSelectedItem().toString().equals("mci") && mcMode.getSelectedItem() != "") {
                        output = output + ":mc_mode=" + mcMode.getSelectedItem();
                    }
                    output = output + ":me_mode=" + meMode.getSelectedItem() + ":me=" + me.getSelectedItem();

                    if (vsbmc.getSelectedItem().equals("Habilitado")) {
                        output = output + ":vsbmc=1";
                    } else {
                        output = output + ":vsbmc=0";
                    }
                    output = output + ":me=" + me.getSelectedItem();

                    if (scd.getSelectedItem().toString().equals("Desabilitado")) {
                        output = output + ":scd=none";
                    }
                    output = output + ":scd=" + scd.getSelectedItem() + ":scd_threshold=" + scd_threshold.getText();
                    output = output + "\" -codec:v libx264 -crf 18 -preset slow -threads 1 -c:a copy \"" + dlmOutput.getElementAt(a) + "\" ";

                    listacomando[a] = intro + "" + input + "" + output;
                    System.out.println(listacomando[a]);
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
