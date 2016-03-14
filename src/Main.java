import com.kudlaienko.parser.Xml;
import com.kudlaienko.parser.engine.xml.parser.token.XmlTokenFactory;
import com.kudlaienko.parser.engine.xml.property.Document;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.exceptions.ValidationException;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws ParseException, IOException, ValidationException {
//        xmlParseMem();
        xmlParseFile();
    }

    private static void xmlParseFile() throws ParseException, ValidationException, IOException {
        Xml xml = new Xml(XmlTokenFactory.getDocumentToken());
        Document document = xml.parse(Paths.get("/Users/artemkudlaienko/Documents/test.xml \n"));
//        Document document = xml.parse(Paths.get("~/Documents/test.xml"));
        System.out.println(document);
    }

    private static void xmlParseMem() throws ParseException, ValidationException {
        Xml xml = new Xml(XmlTokenFactory.getDocumentToken());
        String content = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>\n\n" +
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

        Document document = xml.parse(content);
        System.out.println(document);

    }
}