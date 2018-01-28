import static java.lang.System.*;

import java.text.DecimalFormat;
import java.util.*;

public class ShortestJobFirst {

    //declaration of global variables
    private static int numberOfProcess;
    private static float averageWaitingTime, averageTurnAroundTime;
    private static float waitingTime, turnAroundTime;
    private static DecimalFormat decimalFormat;
    private static List<Integer> listOfBurstTime;
    private static List<Float> turnAroundTimeList;
    private static List<Map.Entry<String, Integer>> list;
    private static Map<String, Integer> processValueMap;
    private static Scanner scn;

    public static void main(String[] args) {
        scn = new Scanner(in);

        listOfBurstTime = new ArrayList<>();
        out.println("\n\t\tSHORTEST JOB FIRST (SJF)");
        out.print("\nEnter the total number of process: ");

        boolean continueLoop = true;

        while (continueLoop) {
            try {
                numberOfProcess = scn.nextInt();
                continueLoop = false;
            } catch (InputMismatchException e) {
                //skip the key mismatched input.
                scn.nextLine();
                err.println("Error! Enter Accepted Integers only!");
            }
        }

        out.println("\n\nEnter the burst time:\n");
        getTheBurstTime();

        out.print("\n\n");

        saveInMap();

        getTheAverageWaitingTime();
        decimalFormat = new DecimalFormat("0.#");
        out.println("THE AVERAGE WAITING TIME IS: " + decimalFormat.format(averageWaitingTime));

        getTheTotalTurnaroundTime();
        out.print("THE AVERAGE TURNAROUND TIME IS: " + decimalFormat.format(averageTurnAroundTime));


        out.print("\n\n");
        showTheGanttChart();
    }

    private static void saveInMap() {
        String[] processName = new String[numberOfProcess];
        for (int index = 0; index < listOfBurstTime.size(); index++) {
            processName[index] = "P" + (index + 1);
        }

        processValueMap = new HashMap<>();
        for (int index = 0; index < listOfBurstTime.size(); index++)
            processValueMap.put(processName[index], listOfBurstTime.get(index));

        sortMapByValue(processValueMap);
    }

    private static void sortMapByValue(Map<String, Integer> map) {

        list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
    }

    private static void showTheGanttChart() {
        out.println("------------------------Gantt Chart------------------------");
        for (Map.Entry<String, Integer> entry : list){
            out.print("|\t" + entry.getKey() + "\t");
        }
        out.println("|");

        for (Map.Entry<String, Integer> entry : list)
            out.print("|\t" + entry.getValue() + "\t");
        out.println("|");

        out.print(0);
        for (int index = 0; index < listOfBurstTime.size(); index++) {
            out.print("\t\t" + decimalFormat.format(turnAroundTimeList.get(index)));
        }

        out.println("\n\n");

    }

    private static void getTheTotalTurnaroundTime() {
        turnAroundTimeList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            turnAroundTime += entry.getValue();
            turnAroundTimeList.add(turnAroundTime);
            averageTurnAroundTime += turnAroundTime;
        }
        averageTurnAroundTime = averageTurnAroundTime / numberOfProcess;
    }

    private static void getTheAverageWaitingTime() {
        int index = 0;
        for (Map.Entry<String, Integer> entry : list) {
            if (index < list.size() - 1) {
                waitingTime += entry.getValue();
                averageWaitingTime += waitingTime;
                index++;
            }
        }
        averageWaitingTime = averageWaitingTime / numberOfProcess;

    }

    private static void getTheBurstTime() {
        for (int index = 0; index < numberOfProcess; index++) {
            out.print("P[" + (index + 1) + "]: ");
            int burstTime = 0;
            boolean continueLoop = true;
            while (continueLoop) {
                try {
                    burstTime = scn.nextInt();
                    continueLoop = false;
                } catch (InputMismatchException e) {
                    //skip the error input
                    scn.nextLine();
                    err.println("Error! Enter Accepted Integers only!");
                }
            }
            listOfBurstTime.add(burstTime);

        }
    }
}
