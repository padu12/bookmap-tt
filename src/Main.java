import model.InputFileInfo;
import service.impl.AnswerGeneratorServiceImpl;
import service.impl.ParserServiceImpl;
import service.impl.ReaderServiceImpl;
import service.impl.WriterServiceImpl;

public class Main {
    public static void main(String[] args) {
        ReaderServiceImpl reader = new ReaderServiceImpl();
        String inputFileContent = reader.read("input.txt");

        ParserServiceImpl parser = new ParserServiceImpl();
        InputFileInfo inputFileInfo = parser.parse(inputFileContent);

        AnswerGeneratorServiceImpl answerGenerator = new AnswerGeneratorServiceImpl();
        String answer = answerGenerator.generate(inputFileInfo);

        WriterServiceImpl writer = new WriterServiceImpl();
        writer.write(answer, "output.txt");
    }
}
