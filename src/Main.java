import model.InputFileInfo;
import service.AnswerGeneratorService;
import service.ParserService;
import service.ReaderService;
import service.WriterService;
import service.impl.AnswerGeneratorServiceImpl;
import service.impl.ParserServiceImpl;
import service.impl.ReaderServiceImpl;
import service.impl.WriterServiceImpl;

public class Main {
    public static void main(String[] args) {
        ReaderService reader = new ReaderServiceImpl();
        String inputFileContent = reader.read("input.txt");

        ParserService parser = new ParserServiceImpl();
        InputFileInfo inputFileInfo = parser.parse(inputFileContent);

        AnswerGeneratorService answerGenerator = new AnswerGeneratorServiceImpl();
        String answer = answerGenerator.generate(inputFileInfo);

        WriterService writer = new WriterServiceImpl();
        writer.write(answer, "output.txt");
    }
}
