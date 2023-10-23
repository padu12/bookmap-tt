import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {
        int q;
        String s;
        int[][] queries;
        File file = new File("input.txt");
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String value = reader.readLine();
            String[] firstLine = value.split(" ");
            q = Integer.parseInt(firstLine[1]);
            queries = new int[q][3];
            s = reader.readLine();
            value = reader.readLine();
            for (int i = 0; value != null; i++) {
                String[] query = value.split(" ");
                for (int l = 0; l < 3; l++) {
                    queries[i][l] = Integer.parseInt(query[l]);
                }
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }

        for (int[] query : queries) {
            int left = query[0] - 1;
            int right = query[1] - 1;
            int k = query[2] - 1;
            String substring = s.substring(left, right + 1);
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

        File outputFile = new File("output.txt");
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }
        try {
            Files.write(outputFile.toPath(), stringBuilder.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write date to file", e);
        }
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
