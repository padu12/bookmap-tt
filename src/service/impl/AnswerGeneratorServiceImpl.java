package service.impl;

import model.InputFileInfo;
import service.AnswerGeneratorService;

public class AnswerGeneratorServiceImpl implements AnswerGeneratorService {
    private int[] inputCountsOfA;
    private int[] inputCountsOfB;
    private int[] inputCountsOfAInverted;
    private int[] inputCountsOfBInverted;

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
        int indexA = 0;
        int indexB = 0;
        inputCountsOfA = new int[inputFileInfo.getString().length()];
        inputCountsOfB = new int[inputFileInfo.getString().length()];
        inputCountsOfAInverted = new int[inputFileInfo.getString().length()];
        inputCountsOfBInverted = new int[inputFileInfo.getString().length()];
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'A') {
                inputCountsOfA[indexA] = i;
                inputCountsOfAInverted[i] = indexA++;
            } else {
                inputCountsOfB[indexB] = i;
                inputCountsOfBInverted[i] = indexB++;
            }
        }
    }

    private int findLettersLocalInputCountByIndex(String string, int localKey, int leftBorder) {
        int globalKey = localKey + leftBorder;
        char currentChar = string.charAt(globalKey);
        int globalIndexOfFirstLetter = string.indexOf(currentChar, leftBorder);
        if (currentChar == 'A') {
            int preInputCount = inputCountsOfAInverted[globalIndexOfFirstLetter];
            return inputCountsOfAInverted[globalKey] - preInputCount;
        } else {
            int preInputCount = inputCountsOfBInverted[globalIndexOfFirstLetter];
            return inputCountsOfBInverted[globalKey] - preInputCount;
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
        if (globalIndexOfFirstLetter < leftBorder || globalIndexOfFirstLetter > rightBorder) {
            return -1;
        }
        int preInputCount;
        int globalIndexOfGoalLetter;
        if (letter == 'A') {
            preInputCount = inputCountsOfAInverted[globalIndexOfFirstLetter];
            globalIndexOfGoalLetter = inputCountsOfA[localInputCount + preInputCount];
        } else {
            preInputCount = inputCountsOfBInverted[globalIndexOfFirstLetter];
            globalIndexOfGoalLetter = inputCountsOfB[localInputCount + preInputCount];
        }
        int localIndexOfGoalLetter = globalIndexOfGoalLetter - leftBorder;
        if (localIndexOfGoalLetter > rightBorder - leftBorder) {
            return -1;
        } else {
            return localIndexOfGoalLetter + 1;
        }
    }
}
