import com.kudlaienko.parser.Xml;
import com.kudlaienko.parser.engine.xml.ParserFactory;
import com.kudlaienko.parser.engine.xml.ParserFactoryImpl;
import com.kudlaienko.parser.engine.xml.property.Document;
import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.shell.CompoundValidator;
import com.kudlaienko.parser.shell.Validator;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.exceptions.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class MainXmlExample {

    private static final String CONTENT = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>\n\n" +
            "<main>\n" +
            "<objects>\n" +
            "<object>\n" +
            "<id>\n" +
            "12000A" +
            "</id>\n" +
            "<name>\n" +
            "Client Name" +
            "</name>\n" +
            "</object>\n" +
            "</objects>\n" +
            "</main>\n";

    public static void main(String[] args) throws ParseException, IOException, ValidationException, InterruptedException {
//        wait input
//        new BufferedReader(new InputStreamReader(System.in)).readLine();
//        xmlParseMem(CONTENT);
        xmlParseFile(ClassLoader.getSystemResource("note.xml").getPath());
//        xmlParseFile(ClassLoader.getSystemResource("noteError.xml").getPath());
//        xmlParseFile(ClassLoader.getSystemResource("CDCatalog.xml").getPath());
//        xmlParseFile(ClassLoader.getSystemResource("PlantCatalog.xml").getPath());
//        xmlParseFile(ClassLoader.getSystemResource("breakfastMenu.xml").getPath());
//        while (1==1) {
//            Thread.currentThread().sleep(200);
//            xmlParseFile(ClassLoader.getSystemResource("booksCatalog.xml").getPath());
//        }


//        Validation test: remove data inside one of the tags in the note.xml file
        Validator<Tag> tagToIsValid = tag -> {
            if ("to".equals(tag.getName()) &&
                    tag.getValue() == null) {
                throw new ValidationException("Field is empty: 'to'");
            }
        };
        Validator<Tag> tagFromIsValid = tag -> {
            if ("from".equals(tag.getName()) &&
                    tag.getValue() == null) {
                throw new ValidationException("Field is empty: 'from'");
            }
        };
        Validator<Tag> tagHeadingIsValid = tag -> {
            if ("heading".equals(tag.getName()) &&
                    tag.getValue() == null) {
                throw new ValidationException("Field is empty: 'heading'");
            }
        };
        Validator<Tag> tagBodyIsValid = tag -> {
            if ("body".equals(tag.getName()) &&
                    tag.getValue() == null) {
                throw new ValidationException("Field is empty: 'body'");
            }
        };
        CompoundValidator<Tag> tagValidator = new CompoundValidator<>(tagBodyIsValid, tagFromIsValid, tagHeadingIsValid, tagToIsValid);
        xmlParseFile(ClassLoader.getSystemResource("note.xml").getPath(), tagValidator);
    }

    private static void xmlParseFile(String path) throws ParseException, ValidationException, IOException {
        Validator<Tag> tagValidator = null;
        xmlParseFile(path, tagValidator);
    }

    private static void xmlParseFile(String path, Validator<Tag> validator) throws ParseException, ValidationException, IOException {
        ParserFactory parserFactory = new ParserFactoryImpl();
        Xml xml = new Xml(parserFactory.getDocumentParser());
        Instant start = Instant.now();
        Document document = xml.parse(Paths.get(path), validator);
        Instant end = Instant.now();
        System.out.println(document);
        Duration duration = Duration.between(start, end);
        System.out.println("Duration: " + duration.toMillis() + " ms");
    }

//    private static void xmlParseMem(String content) throws ParseException, ValidationException {
//        Xml xml = new Xml(ParserFactoryImpl.getDocumentParser());
//
//        Instant start = Instant.now();
//        Document document = xml.parse(content);
//        Instant end = Instant.now();
//        System.out.println(document);
//        Duration duration = Duration.between(start, end);
//        System.out.println("Duration: " + duration.toMillis() + " ms");
//
//    }
}