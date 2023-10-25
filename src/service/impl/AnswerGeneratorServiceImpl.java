package service.impl;

import java.util.HashMap;
import java.util.Map;
import model.InputFileInfo;
import service.AnswerGeneratorService;

public class AnswerGeneratorServiceImpl implements AnswerGeneratorService {
    private final Map<Integer, Integer> inputCountsOfA = new HashMap<>();
    private final Map<Integer, Integer> inputCountsOfB = new HashMap<>();
    private final Map<Integer, Integer> inputCountsOfAInverted = new HashMap<>();
    private final Map<Integer, Integer> inputCountsOfBInverted = new HashMap<>();

    public String generate(InputFileInfo inputFileInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        fillCounters(inputFileInfo);
        for (int[] query : inputFileInfo.getQueries()) {
            int leftBorder = query[0] - 1;
            int rightBorder = query[1] - 1;
            int localKey = query[2] - 1;
            String string = inputFileInfo.getString();
            int localInputCount = findLettersLocalInputCountByIndex(string, localKey, leftBorder);
            char charWithKIndex = string.charAt(localKey + leftBorder);
            char invertedLetter = charWithKIndex == 'A' ? 'B' : 'A';
            int localIndexOfInvertedLetter = findLocalIndexByInputCountAndChar(
                    string,
                    invertedLetter,
                    localInputCount,
                    leftBorder,
                    rightBorder
            );
            stringBuilder.append(localIndexOfInvertedLetter).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private void fillCounters(InputFileInfo inputFileInfo) {
        char[] chars = inputFileInfo.getString().toCharArray();
        int inputCountOfA = 1;
        int inputCountOfB = 1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'A') {
                inputCountsOfA.put(i, inputCountOfA);
                inputCountsOfAInverted.put(inputCountOfA++, i);
            } else {
                inputCountsOfB.put(i, inputCountOfB);
                inputCountsOfBInverted.put(inputCountOfB++, i);
            }
        }
    }

    private int findLettersLocalInputCountByIndex(String string, int localKey, int leftBorder) {
        int globalKey = localKey + leftBorder;
        char currentChar = string.charAt(globalKey);
        int globalIndexOfFirstLetter = string.indexOf(currentChar, leftBorder);
        if (currentChar == 'A') {
            int preInputCount = inputCountsOfA.get(globalIndexOfFirstLetter) - 1;
            return inputCountsOfA.get(globalKey) - preInputCount;
        } else {
            int preInputCount = inputCountsOfB.get(globalIndexOfFirstLetter) - 1;
            return inputCountsOfB.get(globalKey) - preInputCount;
        }
    }

    private int findLocalIndexByInputCountAndChar(
            String string,
            char letter,
            int localInputCount,
            int leftBorder,
            int rightBorder
    ) {
        int globalIndexOfFirstLetter = string.indexOf(letter, leftBorder);
        int preInputCount;
        int globalIndexOfGoalLetter;
        if (letter == 'A') {
            preInputCount = inputCountsOfA.get(globalIndexOfFirstLetter) - 1;
            globalIndexOfGoalLetter = inputCountsOfAInverted.get(localInputCount + preInputCount);
        } else {
            preInputCount = inputCountsOfB.get(globalIndexOfFirstLetter) - 1;
            globalIndexOfGoalLetter = inputCountsOfBInverted.get(localInputCount + preInputCount);
        }
        if (globalIndexOfGoalLetter > rightBorder - leftBorder) {
            return -1;
        } else {
            return globalIndexOfGoalLetter - leftBorder + 1;
        }
    }
}
