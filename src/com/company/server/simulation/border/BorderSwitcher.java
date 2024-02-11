package com.company.server.simulation.border;

import com.company.ProgramGlobals;
import com.company.server.simulation.border.cases.*;
import com.company.server.runtime.vars.SimGlobals;
import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;

public class BorderSwitcher {
    static IBorderHandler caseEquals = new CaseEquals();
    static IBorderHandler caseEdge = new CaseEdge();

    static IBorderHandler caseShockWave = new CaseShockWave();
    static IBorderHandler caseLayer = new CaseLayer();

    static IBorderHandler caseStop = new CaseStop();
    static IBorderHandler caseNull = new CaseNull();

    /**
     * Функция, возвращающая обработчик создания нового слоя деформации на границе
     * @param layerDescriptions Один или два воздействия - параметры границы и первый слой деформации в волновой картине
     * @return Обработчик создания нового слоя деформации
     */
    public static ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> layerDescriptions) {

        double currentSpeed;

        //Выбор типа деформации и от него следующей скорости волнового фронта
        if (layerDescriptions.get(0).getA2() > ProgramGlobals.getEpsilon()) {
            //creationE > 0 => растяжение

            currentSpeed = SimGlobals.getCharacteristicsSpeedStretching();

        } else if (layerDescriptions.get(0).getA2() < -ProgramGlobals.getEpsilon()) {
            //creationE < 0 => сжатие

            currentSpeed = SimGlobals.getCharacteristicsSpeedCompression();

        } else {
            //Если деформация околонулевая, то...

            if (layerDescriptions.size() == 1) {
                if (ProgramGlobals.getLogLevel() == 1)
                    System.out.println(caseNull);

                //Если ещё не были созданы волновые фронты, то ничего не создаём
                return caseNull.generateNewWaveFront(layerDescriptions, 0.0);
            } else {

                //Если изменение смещения на создаваемом волновом фронте равно нулю,
                // и количество существующих волновых фронтов больше одного, то обрабатываем как стоп
                if (ProgramGlobals.getLogLevel() == 1)
                    System.out.println(caseStop);

                //Если у нас было впереди растяжение, то стоп воздействует как сжатие
                if (layerDescriptions.get(1).getA2() > ProgramGlobals.getEpsilon()) {
                    currentSpeed = SimGlobals.getCharacteristicsSpeedStretching();
                } else {
                    //Если у нас впереди было сжатие, то стоп воздействует как растяжение
                    currentSpeed = SimGlobals.getCharacteristicsSpeedCompression();
                }

                return caseStop.generateNewWaveFront(layerDescriptions, currentSpeed);
            }
        }

        //Задаём корректное значение деформации волнового фронта слева
        //Впрочем, оно всё равно игнорируется, но для галочки пусть тут будет
        layerDescriptions.get(0).setA2(0.0 - layerDescriptions.get(0).getA1() / currentSpeed);

        
        //Если нет первого волнового фронта, то создаём первый волновой фронт
        if (layerDescriptions.size() == 1) {
            if (ProgramGlobals.getLogLevel() == 1)
                System.out.println(caseEdge);

            return caseEdge.generateNewWaveFront(layerDescriptions, currentSpeed);
        }

        //Если произведение изменений перемещений двух волновых фронтов меньше нуля,
        // то мы работаем с противоположными волновыми фронтами (нуль к этому моменту мы уже отфильтровали)
        if (layerDescriptions.get(0).getA2() * layerDescriptions.get(1).getA2() < 0.0) {
            if (ProgramGlobals.getLogLevel() == 1)
                System.out.println(caseShockWave);

            //Если e- < 0, то образуется ударная волна
            if (layerDescriptions.get(0).getA2() < 0.0)
                return caseShockWave.generateNewWaveFront(layerDescriptions, 0.0);

            //если же e- > 0, то образуется недеформируемый слой
            if (layerDescriptions.get(0).getA2() > 0.0)
                return caseLayer.generateNewWaveFront(layerDescriptions, 0.0);
        }

        if (ProgramGlobals.getLogLevel() == 1)
            System.out.println(caseEquals);
        //Ну а иначе обработчик сходных волновых фронтов
        return caseEquals.generateNewWaveFront(layerDescriptions, currentSpeed);
    }
}
