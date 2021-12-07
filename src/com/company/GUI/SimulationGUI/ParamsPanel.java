package com.company.GUI.SimulationGUI;

import com.company.ProgramGlobals;
import com.company.Simulation.SimulationSynchronizerThread;
import com.company.Simulation.SimulationVariables.SimulationGlobals;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Hashtable;

/**
 * Панель изменения парамеров отображения
 */
public class ParamsPanel extends JPanel {

    // Ползунок FPS
    static final int FPS_MIN = 0;
    static final int FPS_MAX =15;
    static final int FPS_INIT =5;
    JSlider FPS_slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

    // Ползунок OPS
    static final int OPS_MIN = 0;
    static final int OPS_MAX = 500;
    static final int OPS_INIT = 100;
    JSlider OPS_slider = new JSlider(JSlider.HORIZONTAL, OPS_MIN, OPS_MAX, OPS_INIT);

    // Ползунок дельты времени
    static final int timeDelta_MIN = 0;
    static final int timeDelta_MAX = 10;
    static final int timeDelta_INIT = 5;
    JSlider timeDelta_slider = new JSlider(JSlider.HORIZONTAL, timeDelta_MIN, timeDelta_MAX, timeDelta_INIT);

    /**
     * Панель изменения парамеров отображения
     *
     * @param ServerThread Поток
     */
    ParamsPanel(SimulationSynchronizerThread ServerThread){

        //Отрисовка панели
        FPS_slider.setMajorTickSpacing(5);
        FPS_slider.setMinorTickSpacing(1);
        FPS_slider.setPaintTicks(true);
        FPS_slider.setPaintLabels(true);

        OPS_slider.setMajorTickSpacing(250);
        OPS_slider.setMinorTickSpacing(50);
        OPS_slider.setPaintTicks(true);
        OPS_slider.setPaintLabels(true);

        timeDelta_slider.setMajorTickSpacing(5);
        timeDelta_slider.setMinorTickSpacing(1);
        timeDelta_slider.setPaintTicks(true);
        timeDelta_slider.setPaintLabels(true);

        Hashtable labelTable = new Hashtable();
        labelTable.put(0, new JLabel("0.0") );
        labelTable.put(5, new JLabel("0.5") );
        labelTable.put(10, new JLabel("1.0") );
        timeDelta_slider.setLabelTable( labelTable );

        JPanel slider_panel = new JPanel();
        JLabel FPS_label = new JLabel("Кадры в секунду ("+FPS_slider.getValue()+")", SwingConstants.CENTER);
        JLabel OPS_label = new JLabel("Операции в секунду ("+OPS_slider.getValue()+")", SwingConstants.CENTER);
        JLabel timeDelta_label = new JLabel("Дельта времени, мкс ("+(double)timeDelta_slider.getValue()/10+")", SwingConstants.CENTER);
        GridLayout layout = new GridLayout(2,0, 50, 5);
        slider_panel.setLayout(layout);

        slider_panel.add(FPS_label);
        slider_panel.add(OPS_label);
        slider_panel.add(timeDelta_label);

        slider_panel.add(FPS_slider);
        slider_panel.add(OPS_slider);
        slider_panel.add(timeDelta_slider);

        this.add(slider_panel, BorderLayout.SOUTH);

        /**
         * Обработчики событий изменения ползунка FPS
         */
        FPS_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                // Остановка решателя
                ServerThread.setNextJobPAUSE();

                // Изменение FPS
                int value = (FPS_slider.getValue()==0)? 1:FPS_slider.getValue();
                ProgramGlobals.setFramesPerSecond(value);
                FPS_label.setText("Кадры в секунду ("+value+")");

                // Возобновление решателя
                ServerThread.setNextJobRESUME();
            }
        });

        /**
         * Обработчики событий изменения ползунка OPS
         */
        OPS_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                // Остановка решателя
                ServerThread.setNextJobPAUSE();

                // Изменение OPS
                int value = (OPS_slider.getValue()==0)? 1:OPS_slider.getValue();
                ProgramGlobals.setOperationsPerSecond(value);
                OPS_label.setText("Операции в секунду ("+value+")");

                // Возобновление решателя
                ServerThread.setNextJobRESUME();
            }
        });

        /**
         * Обработчики событий изменения ползунка дельты времени
         */
        timeDelta_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                // Остановка решателя
                ServerThread.setNextJobPAUSE();

                // Изменение дельты времени
                double value = timeDelta_slider.getValue()==0? 0.1:(double)timeDelta_slider.getValue()/10;
                SimulationGlobals.setSimulationTimeDelta(value);
                timeDelta_label.setText("Дельта времени ("+value+")");

                // Возобновление решателя
                ServerThread.setNextJobRESUME();
            }
        });

    }
}
