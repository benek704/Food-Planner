package pl.coderslab.dao;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalDb {

    private static final String FILENAME = "src/main/webapp/META-INF/context.xml";
    private static final String PROPERTIES = "?useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true";

    public static Connection getConnection() throws ParserConfigurationException, IOException, SAXException, SQLException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(FILENAME);
        doc.getDocumentElement().normalize();

        NamedNodeMap resource = doc.getElementsByTagName("Resource").item(0).getAttributes();

        String username = resource.getNamedItem("username").getNodeValue();
        String password = resource.getNamedItem("password").getNodeValue();
        String url = resource.getNamedItem("url").getNodeValue();

        Connection connection = DriverManager.getConnection(url + PROPERTIES, username, password);
        connection.setAutoCommit(false);
        return connection;
    }
}
