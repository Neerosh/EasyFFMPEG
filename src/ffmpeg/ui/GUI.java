/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffmpeg.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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

    JFrame frame;
    JPanel panel, optionsFfmpeg;
    JLabel labelInput, labelOutput, labelFfmpeg;
    //Componentes OptionsFfmpeg
    JTextField localInput, localOutput, localFfmpeg;
    JButton searchFfmpeg, saveOptions, defaultOptions, infoOptions;
    JLabel labelFps, labelPreset, labelMiMode, labelMcMode, labelMeMode, labelCrf, labelVsbmc, labelMe, labelScd, labelScdThreshold;
    JFormattedTextField fps, crf, scdThreshold;
    JComboBox preset, miMode, mcMode, meMode, vsbmc, scd, me;
    //Componentes panel
    JButton searchInput, start, remove;
    JProgressBar pr;
    static JLabel status;
    JList inputList;
    JScrollPane inputScroll, outputScroll;
    static JTextArea output;

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
            UIManager.put("ProgressBar.foreground", Color.GREEN);

            //Muda a fonte de tudo 
            java.util.Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof javax.swing.plaf.FontUIResource) {
                    UIManager.put(key, new Font("Helvetica", Font.PLAIN, 12));
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao aplicar tema do sistema, ou alterar as opções de interface\n" + e, "Erro UIManager", JOptionPane.ERROR_MESSAGE);
        }

        
        NumberFormat nfInteger = NumberFormat.getIntegerInstance();
        nfInteger.setGroupingUsed(false);
        NumberFormat nf2 = NumberFormat.getNumberInstance();
        DefaultListModel dlmInput = new DefaultListModel();
        DefaultListModel dlmOutput = new DefaultListModel();
        //Valores comboBox
        String[] presetList = {"ultrafast", "superfast", "veryfast", "faster", "fast", "medium", "slow", "slower", "veryslow"};
        String[] miModeList = {"mci", "blend", "dub"};
        String[] mcModeList = {"obmc", "aobmc"};
        String[] meModeList = {"bidir", "bilat"};
        String[] vsbmcList = {"Habilitado", "Desabilitado"};
        String[] meList = {"esa", "tss", "tlds", "ntss", "fss", "ds", "hexbs", "epzs", "umh"};
        String[] scdList = {"Desabilitado", "fdiff"};

        //Componentes
        frame = new JFrame();
        panel = new JPanel();
        optionsFfmpeg = new JPanel();
        labelInput = new JLabel("Arquivo de Entrada :");
        labelOutput = new JLabel("Arquivo de Saida :");
        labelFfmpeg = new JLabel("Caminho FFMPEG.exe :");
        //Componentes OptionsFfmpeg
        localInput = new JTextField();
        localOutput = new JTextField();
        localFfmpeg = new JTextField();
        searchFfmpeg = new JButton("Selecionar FFMPEG");
        saveOptions = new JButton("Salvar Configurações");
        defaultOptions = new JButton("Resetar Configurações");
        infoOptions = new JButton("Informação Opções");
        labelFps = new JLabel("FPS :");
        labelPreset = new JLabel("Preset :");
        labelMiMode = new JLabel("MI Mode :");
        labelMcMode = new JLabel("MC Mode :");
        labelMeMode = new JLabel("ME Mode :");
        labelCrf = new JLabel("CRF :");
        labelVsbmc = new JLabel("VSBMC :");
        labelMe = new JLabel("ME :");
        labelScd = new JLabel("SCD :");
        labelScdThreshold = new JLabel("SCD threshold :");
        fps = new JFormattedTextField(nfInteger);
        crf = new JFormattedTextField(nfInteger);
        scdThreshold = new JFormattedTextField(nf2);
        preset = new JComboBox(presetList);
        miMode = new JComboBox(miModeList);
        mcMode = new JComboBox(mcModeList);
        meMode = new JComboBox(meModeList);
        vsbmc = new JComboBox(vsbmcList);
        scd = new JComboBox(scdList);
        me = new JComboBox(meList);
        //Componentes panel
        searchInput = new JButton("Selecionar Arquivo");
        start = new JButton("Iniciar Conversão");
        remove = new JButton("Remover Selecionado");
        pr = new JProgressBar();
        status = new JLabel("Não iniciado");
        output = new JTextArea();
        inputList = new JList(dlmInput);
        inputScroll = new JScrollPane(inputList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputScroll = new JScrollPane(output, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        if (xml.file.exists()) {
            xml.ReadAll();
            localFfmpeg.setText(xml.getFfmpeg());
            fps.setText(xml.getFps());
            preset.setSelectedItem(xml.getPreset());
            miMode.setSelectedItem(xml.getMiMode());
            mcMode.setSelectedItem(xml.getMcMode());
            meMode.setSelectedItem(xml.getMeMode());
            crf.setText(xml.getCrf());
            vsbmc.setSelectedItem(xml.getVsbmc());
            me.setSelectedItem(xml.getMe());
            scd.setSelectedItem(xml.getScd());
            scdThreshold.setText(xml.getScdThreshold());
        } else {
            JOptionPane.showMessageDialog(this,"Arquivo de configurações não encontrado.\nAs configurações aparecerão no modelo padrão.","Aviso",JOptionPane.WARNING_MESSAGE);
            setDefaultOptions();
        }

        status.setHorizontalAlignment(JLabel.CENTER);
        status.setVerticalAlignment(JLabel.CENTER);
        status.setForeground(Color.BLUE);
        
        //auto scroll (puxa para o texto mais recente)
        DefaultCaret caret = (DefaultCaret) output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        //Borda
        inputScroll.setBorder(BorderFactory.createTitledBorder(null, "Arquivos Selecionados :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.PLAIN, 12)));
        outputScroll.setBorder(BorderFactory.createTitledBorder(null, "Texto de Saida (CMD) :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.PLAIN, 12)));
        optionsFfmpeg.setBorder(BorderFactory.createTitledBorder(null, "Opções FFMPEG :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.PLAIN, 12)));
        status.setFont(new Font("Helvetica", Font.BOLD, 14));

        //Tamanhos componentes
        //Componentes OptionsFfmpeg
        optionsFfmpeg.setSize(815, 165);
        labelFfmpeg.setSize(140, 20);
        localFfmpeg.setSize(480, 25);
        searchFfmpeg.setSize(150, 30);
        saveOptions.setSize(150, 30);
        defaultOptions.setSize(160, 30);
        infoOptions.setSize(150, 30);
        labelFps.setSize(130, 20);
        fps.setSize(30, 20);
        labelPreset.setSize(130, 20);
        preset.setSize(80, 20);
        labelMiMode.setSize(60, 20);
        miMode.setSize(60, 20);
        labelMcMode.setSize(60, 20);
        mcMode.setSize(70, 20);
        labelMeMode.setSize(60, 20);
        meMode.setSize(60, 20);
        labelCrf.setSize(60, 20);
        crf.setSize(30, 20);
        labelVsbmc.setSize(60, 20);
        vsbmc.setSize(100, 20);
        labelMe.setSize(60, 20);
        me.setSize(60, 20);
        labelScd.setSize(60, 20);
        scd.setSize(100, 20);
        labelScdThreshold.setSize(90, 20);
        scdThreshold.setSize(30, 20);
        //Componentes panel
        panel.setSize(850, 650);
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
        outputScroll.setSize(790, 150);

        //Opções Painel/componenetes
        panel.setLayout(null);
        optionsFfmpeg.setLayout(null);
        inputScroll.getViewport().add(inputList);
        outputScroll.getViewport().add(output);

        //Conjunto de componenetes
        JComponent[] optionsComponents = {labelFfmpeg, localFfmpeg, saveOptions, defaultOptions, searchFfmpeg, infoOptions,
            labelInput, labelOutput, labelFfmpeg, labelFps, labelPreset, labelMiMode, labelMcMode, labelMeMode,
            labelCrf, labelVsbmc, labelMe, labelScd, labelScdThreshold, fps, crf, scdThreshold, preset, miMode,
            mcMode, meMode, vsbmc, scd, me};
        JComponent[] panelComponents = {optionsFfmpeg, labelInput, localInput, searchInput, labelOutput,
            localOutput, pr, start, status, inputScroll, remove, outputScroll};

        //adição automatica de componentes
        for (int o = 0; o < optionsComponents.length; o++) {
            optionsFfmpeg.add(optionsComponents[o]);
        }
        for (int o = 0; o < panelComponents.length; o++) {
            panel.add(panelComponents[o]);
        }

        //Local no painel
        //Componentes OptionsFfmpeg
        optionsFfmpeg.setLocation(10, 5);
        labelFfmpeg.setLocation(10, 20);
        localFfmpeg.setLocation(150, 20);
        searchFfmpeg.setLocation(650, 17);
        labelFps.setLocation(10, 60);
        fps.setLocation(45, 60);
        labelPreset.setLocation(85, 60);
        preset.setLocation(130, 60);
        labelMiMode.setLocation(220, 60);
        miMode.setLocation(275, 60);
        labelMcMode.setLocation(345, 60);
        mcMode.setLocation(405, 60);
        labelMeMode.setLocation(485, 60);
        meMode.setLocation(545, 60);
        saveOptions.setLocation(650, 57);
        infoOptions.setLocation(10, 125);
        defaultOptions.setLocation(645, 125);
        labelCrf.setLocation(10, 100);
        crf.setLocation(45, 100);
        labelVsbmc.setLocation(85, 100);
        vsbmc.setLocation(135, 100);
        labelMe.setLocation(245, 100);
        me.setLocation(275, 100);
        labelScd.setLocation(345, 100);
        scd.setLocation(385, 100);
        labelScdThreshold.setLocation(495, 100);
        scdThreshold.setLocation(585, 100);
        //Componentes panel
        labelInput.setLocation(30, 180);
        localInput.setLocation(150, 180);
        searchInput.setLocation(660, 195);
        labelOutput.setLocation(30, 220);
        localOutput.setLocation(150, 220);
        pr.setLocation(30, 255);
        start.setLocation(660, 253);
        status.setLocation(660, 280);
        inputList.setLocation(1, 1);
        inputScroll.setLocation(30, 290);
        remove.setLocation(660, 390);
        outputScroll.setLocation(30, 440);

        //Opções do Frame
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(850, 650);
        frame.setTitle("EasyFFMPEG");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Ações Compoenentes
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
                    scdThreshold.setEnabled(true);
                } else {
                    scdThreshold.setEnabled(false);
                }
            }
        });
        
        //Ações dos Botões
        searchFfmpeg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                fc.setSize(500, 500);

                fc.setFileFilter(new FileNameExtensionFilter("Executaveis (*.exe)", "exe"));
                fc.showOpenDialog(frame);

                if (fc.getSelectedFile() != null) {
                    localFfmpeg.setText(fc.getSelectedFile().getAbsolutePath());
                    xml.ReadAll();
                    xml.setFfmpeg(localFfmpeg.getText());
                    xml.WriteAll();
                }
            }
        });

        defaultOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(rootPane, "Restaurar Configuração Padrão?\nA configuração só sera salva caso seja utilizado o botão 'Salvar Configurações'.",
                        "Restaurar Configuração Padrão?", JOptionPane.YES_NO_OPTION);
                if (confirm == 0) {
                    setDefaultOptions();
                    
                }

            }

        });

        infoOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(URI.create("http://ffmpeg.org/ffmpeg-filters.html#minterpolate"));
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }
        });

        saveOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xml = new XML(fps.getText(), crf.getText(), localFfmpeg.getText(),
                        preset.getSelectedItem().toString(), miMode.getSelectedItem().toString(), mcMode.getSelectedItem().toString(),
                        meMode.getSelectedItem().toString(), vsbmc.getSelectedItem().toString(), me.getSelectedItem().toString(),
                        scd.getSelectedItem().toString(), scdThreshold.getText());
                xml.WriteAll();
                JOptionPane.showConfirmDialog(rootPane, "Configurações salvas com sucesso.",
                        "Salvar Configurações", JOptionPane.DEFAULT_OPTION);
                
            }
        });

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

        status.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("text".equals(evt.getPropertyName()) && evt.getNewValue().equals("Concluido")) {
                    searchInput.setEnabled(true);
                searchFfmpeg.setEnabled(true);
                start.setEnabled(true);
                remove.setEnabled(true);
                saveOptions.setEnabled(true);
                defaultOptions.setEnabled(true);

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
                    output = output + ":scd=" + scd.getSelectedItem() + ":scd_threshold=" + scdThreshold.getText();
                    output = output + "\" -codec:v libx264  -crf " + crf.getText() + " -preset " + preset.getSelectedItem() + " -c:a copy \"" + dlmOutput.getElementAt(a) + "\" ";

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
                saveOptions.setEnabled(false);
                defaultOptions.setEnabled(false);

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

    private void setDefaultOptions() {
        //valores iniciais (Default) / configuração de campos
        preset.setSelectedIndex(6);
        miMode.setSelectedIndex(0);
        mcMode.setSelectedIndex(1);
        meMode.setSelectedIndex(0);
        vsbmc.setSelectedIndex(0);
        me.setSelectedIndex(7);
        fps.setText("60");
        crf.setText("18");
        scd.setSelectedIndex(1);
        scdThreshold.setText("5.0");
    }

}
