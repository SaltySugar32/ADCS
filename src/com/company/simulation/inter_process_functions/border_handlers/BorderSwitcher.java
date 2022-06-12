package com.company.simulation.inter_process_functions.border_handlers;

import com.company.ProgramGlobals;
import com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations.EdgeCase;
import com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations.EqualsCase;
import com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations.NullCase;
import com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations.OppositesCase;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.DenoteFactor;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class BorderSwitcher {
    static IBorderHandler equalsCase = new EqualsCase();
    static IBorderHandler edgeCase = new EdgeCase();
    static IBorderHandler oppositesCase = new OppositesCase();
    static IBorderHandler nullCase = new NullCase();

    /**
     * Функция, выбирающая вид создаваемого волнового фронта
     * @param layerDescriptions Один или два воздействия - параметры границы и первый волновой фронт в волновой картине
     * @return Тип создаваемого волнового фронта
     */
    public static LayerDescription generateNewWaveFront(ArrayList<LayerDescription> layerDescriptions) {

        double currentSpeed;

        //Выбор типа деформации и от него следующей скорости волнового фронта
        if (layerDescriptions.get(0).getA2() > ProgramGlobals.getEpsilon()) {
            //creationE > 0 => растяжение

            currentSpeed = DenoteFactor.METERS.toMillis(
                    SimulationGlobals.getCharacteristicsSpeedStretching()
            );

        } else if (layerDescriptions.get(0).getA2() < -ProgramGlobals.getEpsilon()) {
            //creationE < 0 => сжатие

            currentSpeed = DenoteFactor.METERS.toMillis(
                    SimulationGlobals.getCharacteristicsSpeedCompression()
            );

        } else {
            System.out.println(nullCase);
            return nullCase.generateNewWaveFront(layerDescriptions, 0.0);
        }

        
        //Если нет первого волнового фронта, то создаём первый волновой фронт
        if (layerDescriptions.size() == 1) {
            System.out.println(edgeCase);
            return edgeCase.generateNewWaveFront(layerDescriptions, currentSpeed);
        }


        //Если произведение изменений перемещений двух волновых фронтов меньше нуля,
        // то мы работаем с противоположными волновыми фронтами (нуль к этому моменту мы уже отфильтровали)
        if (layerDescriptions.get(0).getA2() * layerDescriptions.get(1).getA2() < 0.0) {
            System.out.println(oppositesCase);
            return oppositesCase.generateNewWaveFront(layerDescriptions, 0.0);
        }

        System.out.println(equalsCase);
        //Ну а иначе обработчик сходных волновых фронтов
        return equalsCase.generateNewWaveFront(layerDescriptions, currentSpeed);
    }
}
