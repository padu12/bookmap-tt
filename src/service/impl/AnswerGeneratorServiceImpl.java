package service.impl;

import model.InputFileInfo;
import service.AnswerGeneratorService;

public class AnswerGeneratorServiceImpl implements AnswerGeneratorService {
    public String generate(InputFileInfo inputFileInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] query : inputFileInfo.getQueries()) {
            int left = query[0] - 1;
            int right = query[1] - 1;
            int k = query[2] - 1;
            String substring = inputFileInfo.getString().substring(left, right + 1);
            char charWithKIndex = substring.charAt(k);
            int inputCount = findInputNumberByIndex(substring, k);
            if (charWithKIndex == 'A') {
                int indexOfBInput = findIndexByInputCountAndChar(substring, 'B', inputCount);
                stringBuilder.append(indexOfBInput).append(System.lineSeparator());
            } else if (charWithKIndex == 'B') {
                int indexOfBInput = findIndexByInputCountAndChar(substring, 'A', inputCount);
                stringBuilder.append(indexOfBInput).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }
    private static int findInputNumberByIndex(String string, int k) {
        int counter = 0;
        char targetChar = string.charAt(k);
        for (int i = 0; i <= k; i++) {
            if (string.charAt(i) == targetChar) {
                counter++;
            }
        }
        return counter;
    }

    private static int findIndexByInputCountAndChar(String string, char ch, int inputCount) {
        int counter = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ch) {
                counter++;
                if (counter == inputCount) {
                    return ++i;
                }
            }
        }
        return -1;
    }
}
