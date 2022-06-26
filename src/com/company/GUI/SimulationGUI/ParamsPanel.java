package com.company.GUI.SimulationGUI;

import com.company.GUI.Database.DBHandler;
import com.company.GUI.GUIGlobals;
import com.company.ProgramGlobals;
import com.company.simulation.simulation_variables.SimulationTime;
import com.company.thread_organization.SimulationSynchronizerThread;
import com.company.thread_organization.thread_states.NextThreadState;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Панель изменения парамеров отображения
 */
public class ParamsPanel extends JPanel {

    // Ползунок FPS
    static final int FPS_MIN = 0;
    static final int FPS_MAX = 50;
    static final int FPS_INIT =ProgramGlobals.getFramesPerSecond();
    JSlider FPS_slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

    // Ползунок OPS
    static final int OPS_MIN = 0;
    static final int OPS_MAX = 100;
    static final int OPS_INIT = ProgramGlobals.getOperationsPerSecond();
    JSlider OPS_slider = new JSlider(JSlider.HORIZONTAL, OPS_MIN, OPS_MAX, OPS_INIT);

    // Ползунок дельты времени
    static final int timeDelta_MIN = 0;
    static final int timeDelta_MAX = 1000;
    static final int timeDelta_INIT = (int) (ProgramGlobals.getSimulationTimeMultiplier() * 1000);
    JSlider timeDelta_slider = new JSlider(JSlider.HORIZONTAL, timeDelta_MIN, timeDelta_MAX, timeDelta_INIT);

    public JLabel simulationTime;

    /**
     * Панель изменения парамеров отображения
     *
     * @param ServerThread Поток
     */
    ParamsPanel(SimulationSynchronizerThread ServerThread, SimulationForm parent){

        //Отрисовка панели
        FPS_slider.setMajorTickSpacing(25);
        FPS_slider.setMinorTickSpacing(5);
        FPS_slider.setPaintTicks(true);
        FPS_slider.setPaintLabels(true);

        OPS_slider.setMajorTickSpacing(50);
        OPS_slider.setMinorTickSpacing(10);
        OPS_slider.setPaintTicks(true);
        OPS_slider.setPaintLabels(true);

        timeDelta_slider.setMajorTickSpacing(500);
        timeDelta_slider.setMinorTickSpacing(100);
        timeDelta_slider.setPaintTicks(true);
        timeDelta_slider.setPaintLabels(true);

        Hashtable labelTable = new Hashtable();
        labelTable.put(0, new JLabel("0.001") );
        labelTable.put(500, new JLabel("0.5") );
        labelTable.put(1000, new JLabel("1.0") );
        timeDelta_slider.setLabelTable( labelTable );

        JButton pauseButton = new JButton("START");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ServerThread.getNextJob() == NextThreadState.RESUME) {
                    ServerThread.setNextJobPAUSE();
                    pauseButton.setText("START");
                }
                else {
                    ServerThread.setNextJobRESUME();
                    pauseButton.setText("PAUSE");
                }
            }
        });

        JButton stopButton = new JButton("STOP");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerThread.setNextJobSTOP();
                pauseButton.setText("START");
            }
        });

        // Загрузка иконки для кнопок
        try {
            BufferedImage pauseIcon = ImageIO.read(new File(DBHandler.resourcesPath + "play-pause.png"));
            BufferedImage stopIcon = ImageIO.read(new File(DBHandler.resourcesPath + "stop.png"));
            pauseButton.setIcon(new ImageIcon(pauseIcon));
            stopButton.setIcon(new ImageIcon(stopIcon));
        }
        catch(IOException ex){}

        JPanel slider_panel = new JPanel();
        JLabel FPS_label = new JLabel("Кадры в секунду ("+FPS_slider.getValue()+")", SwingConstants.CENTER);
        JLabel OPS_label = new JLabel("Операции в секунду ("+OPS_slider.getValue()+")", SwingConstants.CENTER);
        JLabel timeDelta_label = new JLabel("Дельта времени, " + SimulationTime.getSimulationTimePow().getName()
                + " ("+(double)timeDelta_slider.getValue()/1000+")", SwingConstants.CENTER);

        simulationTime = new JLabel("Текущее время симуляции: 0 c.", SwingConstants.CENTER);

        slider_panel.add(FPS_label);
        slider_panel.add(OPS_label);
        slider_panel.add(timeDelta_label);
        slider_panel.add(pauseButton);

        slider_panel.add(FPS_slider);
        slider_panel.add(OPS_slider);
        slider_panel.add(timeDelta_slider);
        slider_panel.add(stopButton);

        GridLayout layout = new GridLayout(2,0, 50, 5);
        slider_panel.setLayout(layout);

        this.setPreferredSize(new Dimension(GUIGlobals.graph_frame_width-50,110));

        this.setLayout(new BorderLayout());

        this.add(simulationTime, BorderLayout.NORTH);
        this.add(slider_panel, BorderLayout.SOUTH);

        this.setBorder(BorderFactory.createEmptyBorder(0,10,5,10));

        /**
         * Обработчики событий изменения ползунка FPS
         */
        FPS_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Изменение FPS
                int value = (FPS_slider.getValue()==0)? 1:FPS_slider.getValue();
                ProgramGlobals.setFramesPerSecond(value);
                parent.updateTimer();
                FPS_label.setText("Кадры в секунду ("+value+")");
            }
        });

        /**
         * Обработчики событий изменения ползунка OPS
         */
        OPS_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Изменение OPS
                int value = (OPS_slider.getValue()==0)? 1:OPS_slider.getValue();
                ProgramGlobals.setOperationsPerSecond(value);
                OPS_label.setText("Операции в секунду ("+value+")");
            }
        });

        /**
         * Обработчики событий изменения ползунка дельты времени
         */
        timeDelta_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Изменение дельты времени
                double value = timeDelta_slider.getValue()==0? 0.001:(double)timeDelta_slider.getValue()/1000;
                ProgramGlobals.setSimulationTimeMultiplier(value);
                timeDelta_label.setText("Дельта времени, мс ("+value+")");
            }
        });

    }
}
