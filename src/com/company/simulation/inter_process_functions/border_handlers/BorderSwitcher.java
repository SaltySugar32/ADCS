package com.company.simulation.inter_process_functions.border_handlers;

import com.company.ProgramGlobals;
import com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations.*;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class BorderSwitcher {
    static IBorderHandler equalsCase = new CaseEquals();
    static IBorderHandler edgeCase = new CaseEdge();
    static IBorderHandler shockWaveCase = new CaseShockWave();

    static IBorderHandler layerCase = new CaseLayer();
    static IBorderHandler stopCase = new CaseStop();
    static IBorderHandler nullCase = new CaseNull();

    /**
     * Функция, выбирающая вид создаваемого волнового фронта
     * @param layerDescriptions Один или два воздействия - параметры границы и первый волновой фронт в волновой картине
     * @return Тип создаваемого волнового фронта
     */
    public static ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> layerDescriptions) {

        double currentSpeed;

        //Выбор типа деформации и от него следующей скорости волнового фронта
        if (layerDescriptions.get(0).getA2() > ProgramGlobals.getEpsilon()) {
            //creationE > 0 => растяжение

            currentSpeed = SimulationGlobals.getCharacteristicsSpeedStretching();

        } else if (layerDescriptions.get(0).getA2() < -ProgramGlobals.getEpsilon()) {
            //creationE < 0 => сжатие

            currentSpeed = SimulationGlobals.getCharacteristicsSpeedCompression();

        } else {
            if (layerDescriptions.size() == 1) {
                if (ProgramGlobals.getLogLevel() == 1)
                    System.out.println(nullCase);

                return nullCase.generateNewWaveFront(layerDescriptions, 0.0);
            } else {

                //Если изменение смещения на создаваемом волновом фронте равно нулю,
                // и количество существующих волновых фронтов больше одного, то обрабатываем как стоп
                if (ProgramGlobals.getLogLevel() == 1)
                    System.out.println(stopCase);

                //Если у нас было впереди растяжение, то стоп воздействует как сжатие
                if (layerDescriptions.get(1).getA2() > ProgramGlobals.getEpsilon()) {
                    currentSpeed = SimulationGlobals.getCharacteristicsSpeedStretching();
                } else {
                    //Если у нас впереди было сжатие, то стоп воздействует как растяжение
                    currentSpeed = SimulationGlobals.getCharacteristicsSpeedCompression();
                }

                return stopCase.generateNewWaveFront(layerDescriptions, currentSpeed);
            }
        }

        
        //Если нет первого волнового фронта, то создаём первый волновой фронт
        if (layerDescriptions.size() == 1) {
            if (ProgramGlobals.getLogLevel() == 1)
                System.out.println(edgeCase);

            return edgeCase.generateNewWaveFront(layerDescriptions, currentSpeed);
        }

        //Если произведение изменений перемещений двух волновых фронтов меньше нуля,
        // то мы работаем с противоположными волновыми фронтами (нуль к этому моменту мы уже отфильтровали)
        if (layerDescriptions.get(0).getA2() * layerDescriptions.get(1).getA2() < 0.0) {
            if (ProgramGlobals.getLogLevel() == 1)
                System.out.println(shockWaveCase);

            //Если e- < 0, то образуется ударная волна
            if (layerDescriptions.get(0).getA2() < 0.0)
                return shockWaveCase.generateNewWaveFront(layerDescriptions, 0.0);

            //если же e- > 0, то образуется недеформируемый слой
            if (layerDescriptions.get(0).getA2() > 0.0)
                return layerCase.generateNewWaveFront(layerDescriptions, 0.0);
        }

        if (ProgramGlobals.getLogLevel() == 1)
            System.out.println(equalsCase);
        //Ну а иначе обработчик сходных волновых фронтов
        return equalsCase.generateNewWaveFront(layerDescriptions, currentSpeed);
    }
}
