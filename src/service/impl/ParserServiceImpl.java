package service.impl;

import model.InputFileInfo;
import service.ParserService;

public class ParserServiceImpl implements ParserService {
    private static final int FIRST_LINE_INDEX = 0;
    private static final int SECOND_LINE_INDEX = 1;
    private static final int THIRD_LINE_INDEX = 2;
    private static final int QUANTITY_OF_QUERIES_INDEX = 1;
    private static final int NUMBER_OF_ARGUMENTS_IN_QUERY = 3;

    public InputFileInfo parse(String info) {
        InputFileInfo inputFileInfo = new InputFileInfo();
        String[] infoLines = info.split(System.lineSeparator());
        inputFileInfo.setString(infoLines[SECOND_LINE_INDEX]);
        inputFileInfo.setQueries(parseQueries(infoLines));
        return inputFileInfo;
    }

    private static int[][] parseQueries(String[] infoLines) {
        int parsedQuantityOfQueries = getQuantityOfQueries(infoLines);
        int[][] queries = new int[parsedQuantityOfQueries][NUMBER_OF_ARGUMENTS_IN_QUERY];
        for (int i = THIRD_LINE_INDEX, k = 0; i < infoLines.length; i++, k++) {
            String[] query = infoLines[i].split(" ");
            for (int l = 0; l < NUMBER_OF_ARGUMENTS_IN_QUERY; l++) {
                queries[k][l] = Integer.parseInt(query[l]);
            }
        }
        return queries;
    }

    private static int[] parseOneQuery(String query) {
        byte[] bytes = query.getBytes();
        int[] resultQuery = new int[NUMBER_OF_ARGUMENTS_IN_QUERY];
        for (int i = bytes.length - 1, l = 0, k = 2; i >= 0; i--) {
            if (bytes[i] != 32) {
                resultQuery[k] += (int) ((bytes[i] - 48) * Math.pow(10, l));
                l++;
            } else {
                k--;
                l = 0;
            }
        }
        return resultQuery;
    }

    private static int getQuantityOfQueries(String[] infoLines) {
        String[] lengthOfStringAndQuantityOfQueries = infoLines[FIRST_LINE_INDEX].split(" ");
        return Integer.parseInt(lengthOfStringAndQuantityOfQueries[QUANTITY_OF_QUERIES_INDEX]);
    }
}
