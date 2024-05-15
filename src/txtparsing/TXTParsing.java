package txtparsing;

import txtparsing.MyDoc;
import utils.IO;
import java.util.ArrayList;
import java.util.List;

public class TXTParsing {

    public static List<MyDoc> parse(String file) throws Exception {
        List<MyDoc> parsedDocs = new ArrayList<>();
        try {
            // Parse txt file
            String txtFileContent = IO.ReadEntireFileIntoAString(file);
            String[] docs = txtFileContent.split("///");
            System.out.println("Read: " + docs.length + " docs");

            // Parse each document from the txt file
            for (String doc : docs) {
                //System.out.println("Parsing: " + doc);
                String[] lines = doc.split("\n");
                if (lines.length >= 2) {
                    String title = lines[1];
                    String mesh = lines[2];
                    MyDoc myDoc = new MyDoc(title, mesh);
                    parsedDocs.add(myDoc);
                } else {
                    System.out.println("Invalid document format: " + doc);
                }
            }

        } catch (Throwable err) {
            err.printStackTrace();
            throw new Exception("Error parsing file: " + file, err);
        }
        return parsedDocs;
    }
}
