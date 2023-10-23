package service.impl;

import model.InputFileInfo;
import service.ParserService;

public class ParserServiceImpl implements ParserService {
    public InputFileInfo parse(String info) {
        InputFileInfo inputFileInfo = new InputFileInfo();
        String[] infoLines = info.split(System.lineSeparator());
        inputFileInfo.setString(infoLines[1]);
        int parsedQuantityOfQueries = Integer.parseInt(infoLines[0].split(" ")[1]);
        int[][] queries = new int[parsedQuantityOfQueries][3];
        for (int i = 2; i < infoLines.length; i++) {
            String[] query = infoLines[i].split(" ");
            for (int l = 0; l < 3; l++) {
                queries[i - 2][l] = Integer.parseInt(query[l]);
            }
        }
        inputFileInfo.setQueries(queries);
        return inputFileInfo;
    }
}
