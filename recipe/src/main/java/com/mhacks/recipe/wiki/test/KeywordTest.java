package com.mhacks.recipe.wiki.test;



        import com.mhacks.recipe.alchemy.api.AlchemyAPI;
        import com.mhacks.recipe.parser.src.Keyword;

        import org.xml.sax.SAXException;
        import org.w3c.dom.Document;
        import java.io.*;
        import java.util.Set;

        import javax.xml.parsers.ParserConfigurationException;
        import javax.xml.xpath.XPathExpressionException;
        import javax.xml.transform.Transformer;
        import javax.xml.transform.TransformerException;
        import javax.xml.transform.TransformerFactory;
        import javax.xml.transform.dom.DOMSource;
        import javax.xml.transform.stream.StreamResult;

class KeywordTest {
    public static void main(String[] args) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException {
        // Create an AlchemyAPI object.
        // api key is temporary ok so don't use it
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString("2094dd01fd7cbceb7e1bb916840e40e81f25d16f");

        // Extract topic keywords for a web URL.
        Document doc = alchemyObj.URLGetRankedKeywords("http://www.techcrunch.com/");
        System.out.println(getStringFromDocument(doc));

        // Extract topic keywords for a text string.
        doc = alchemyObj.TextGetRankedKeywords(
                "Hello there, my name is Bob Jones.  I live in the United States of America.  " +
                        "Where do you live, Fred?");
        System.out.println(getStringFromDocument(doc));

        Set<String> keywords = Keyword.getKeywordsFromText("3 eggs, lightly beaten with 1 tablespoon sesame oil");
        for (String keyword : keywords) {
            System.out.println(keyword);
        }
    }

    // utility function
    private static String getFileContents(String filename)
            throws IOException, FileNotFoundException
    {
        File file = new File(filename);
        StringBuilder contents = new StringBuilder();

        BufferedReader input = new BufferedReader(new FileReader(file));

        try {
            String line = null;


            while ((line = input.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        } finally {
            input.close();
        }


        return contents.toString();
    }

    // utility method
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
