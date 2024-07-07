package com.company.server.simulation.general;

import com.company.ProgramGlobals;
import com.company.client.gui.DataHandler;
import com.company.client.gui.GUIGlobals;
import com.company.server.runtime.enums.DenoteFactor;
import com.company.server.runtime.enums.SimulationTimePow;
import com.company.server.runtime.vars.SimGlobals;
import com.company.server.runtime.vars.SimTime;
import com.company.server.simulation.border.Border;
import com.company.server.simulation.border.BorderDescription;
import com.company.server.simulation.border.BorderSwitcher;
import com.company.server.simulation.border.cases.*;
import com.company.server.simulation.enums.WaveType;
import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

// Общее решение задачи
public class GeneralSolution {
    private ArrayList<BorderDescription> borderDisplacementFunctions;
    private ArrayList<LayerDescription> layerDescriptions;

    public GeneralSolution(){

        this.borderDisplacementFunctions = SimGlobals.getBorderDisplacementFunctions();

        testEnv();

        // look createBorderWaveFronts in Border

        ArrayList<LayerDescription> wavePicture = new ArrayList<>();
        layerDescriptions = createBorderWaveFronts(wavePicture);



        System.out.println(getWafeFrontStrings(layerDescriptions));

    }

    // перевод фронтов в строки
    private ArrayList<String> getWafeFrontStrings(ArrayList<LayerDescription> layerDescriptions){
        ArrayList<String> waveFrontStrings = new ArrayList<>();
        for (var waveFront : layerDescriptions){
            switch(waveFront.getWaveType()) {
                case HALF_SIGNOTON:
                    if (Math.abs(Math.abs(waveFront.getSpeed()) - Math.abs(SimGlobals.getCharacteristicsSpeedCompression())) < ProgramGlobals.getEpsilon())
                        waveFrontStrings.add("gamma(a)");
                    else
                        waveFrontStrings.add("gamma(b)");
                    break;
                case SHOCK_WAVE:
                    waveFrontStrings.add("sigma");
                    break;
                case SIMPLE_FRACTURE:
                    if (Math.abs(Math.abs(waveFront.getSpeed()) - Math.abs(SimGlobals.getCharacteristicsSpeedCompression())) < ProgramGlobals.getEpsilon())
                        waveFrontStrings.add("xi(a)");
                    else
                        waveFrontStrings.add("xi(b)");
                    break;
                default:
                    break;
            }
        }
        Collections.reverse(waveFrontStrings);
        return waveFrontStrings;
    }

    public ArrayList<LayerDescription> createBorderWaveFronts(ArrayList<LayerDescription> currentWavePicture) {
        //Получаем список всех возможных новых волновых фронтов,
        // которые должны появиться на границе за прошедшую дельту времени
        ArrayList<BorderDescription> linearFunctions = borderDisplacementFunctions;

        //Если новых волновых фронтов нет, то возвращаем предыдущую волновую картину
        if (linearFunctions.size() == 0)
            return currentWavePicture;

        //Создаём новую волновую картину на основе данных из старой
        var newWavePicture = new LinkedList<>(currentWavePicture);

        //Проходимся по всем волновым фронтам, которые мы должны были получить
        for (var linearFunction : linearFunctions) {
            System.out.println('-');
            //Оболочка пары волновых фронтов - граничное воздействие и ближайший к границе полупространства
            var waveFrontWrapper = new ArrayList<LayerDescription>();

            /*
             * Добавляем граничное воздействие
             * Здесь A2 = -k для определения знака деформации
             */
            System.out.println("[" + linearFunction.b() + "]");
            waveFrontWrapper.add(new LayerDescription(
                    linearFunction.b(),
                    linearFunction.k(),
                    -linearFunction.k(),
                    linearFunction.startTime(),
                    WaveType.NULL
            ));

            //Если волновая картина не пуста, то добавляем в оболочку ближайший к границе полупространства
            if (newWavePicture.size() != 0) {
                waveFrontWrapper.add(newWavePicture.peek());

                System.out.println("-----" + newWavePicture.size());
                if (ProgramGlobals.getLogLevel() == 1) {
                    System.out.println("PEEK");
                    //WTF WITH THIS EXCEPTION HANDLER REQUIREMENTS!?
                    System.out.println("A0 = " + newWavePicture.peek().getA0());
                    System.out.println("A1 = " + newWavePicture.peek().getA1());
                    System.out.println("A2 = " + newWavePicture.peek().getA2());
                    System.out.println("V = " + newWavePicture.peek().getSpeed());
                    System.out.println("X = " + newWavePicture.peek().getCurrentX());
                    System.out.println("U = " + newWavePicture.peek().calculateLayerDisplacement());
                    System.out.println("T = " + newWavePicture.peek().getLayerStartTime());
                    System.out.println("^^^^^^^^^^^^^");
                }
            }

            //Создаём на основе пары волновых фронтов новый волновой фронт
            var newLayerDescription = generateNewWaveFront(waveFrontWrapper);
            //Если не создан, то повторяем цикл
            if (newLayerDescription == null)
                continue;

            //Смещение волнового фронта в обратном направлении,
            //дабы потом вместе со всеми фронтами обработать его смещение.
            //Начальное время минус время в предыдущий момент времени.
            double deltaTime = linearFunction.startTime() -
                    (SimTime.getSimulationTime() - SimTime.getSimulationTimeDelta());

            //Если создалось больше одного волнового фронта, то сначала вычисляем и пушим более быстрый
            if (newLayerDescription.size() == 2) {
                //Вычисляем координату волнового фронта до текущего шага времени
                newLayerDescription.get(1)
                        .setCurrentX(newLayerDescription.get(1).getCurrentX()
                                - newLayerDescription.get(1).getSpeed() * deltaTime);

                //Добавляем новый волновой фронт в картину мира
                newWavePicture.push(newLayerDescription.get(1));
            }

            //Вычисляем координату волнового фронта до текущего шага времени
            newLayerDescription.get(0)
                    .setCurrentX(newLayerDescription.get(0).getCurrentX()
                            - newLayerDescription.get(0).getSpeed() * deltaTime);

            //Добавляем новый волновой фронт в картину мира
            newWavePicture.push(newLayerDescription.get(0));
        }

        return new ArrayList<>(newWavePicture);
    }

    IBorderHandler caseEquals = new CaseEquals();
    IBorderHandler caseEdge = new CaseEdge();
     IBorderHandler caseShockWave = new CaseShockWave();
    IBorderHandler caseLayer = new CaseLayer();

    IBorderHandler caseStop = new CaseStop();
    IBorderHandler caseNull = new CaseNull();
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> layerDescriptions) {

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

    private ArrayList<BorderDescription> initBorderDisplacementFunctions(double[][] coordinates, DenoteFactor denoteFactorU, SimulationTimePow simulationTimePow) {
        ArrayList<BorderDescription> borderDisplacementFunctions = new ArrayList<>();

        //Берём каждый следующий после нулевого индекса индекс и на их основе генерируем последовательность
        // коэффициентов линейных функций
        //coordinates[0][index] = x = currentT
        //coordinates[1][index] = y = currentU
        for (int index = 0; index < coordinates[0].length - 1; index++) {
            double currentT = coordinates[0][index] * simulationTimePow.getPow();
            double currentU = denoteFactorU.toMillis(coordinates[1][index]);
            double endT = coordinates[0][index + 1] * simulationTimePow.getPow();
            double endU = denoteFactorU.toMillis(coordinates[1][index + 1]);

            //k = следующее значение перемещения минус значение перемещения в момент разрыва,
            // делённое на следующее время минус текущее время перегиба.
            //То есть k = (endU(endT) - currentU(currentT)) / (endT - currentT)
            double k = (endU - currentU) / (endT - currentT);

            borderDisplacementFunctions.add(new BorderDescription(k, currentU, currentT));
        }

        return borderDisplacementFunctions;
    }

    private void testEnv(){
        SimGlobals.setSimulationGlobals(
                4.7,
                4.8,
                2200.0,
                2.1
        );

        SimulationTimePow simulationTimePow = SimulationTimePow.MILLISECONDS;

        this.borderDisplacementFunctions = initBorderDisplacementFunctions(testCoordinates, DenoteFactor.MILLI, simulationTimePow);
    }
    private double[][] testCoordinates = {
            {0.0, 94.32387312186978, 267.1118530884808, 399.8330550918197, 563.4390651085141},
            {0.0, -1.6730401529636714, -3.068833652007648, -1.5200764818355639, 1.137667304015296}
    };
}
