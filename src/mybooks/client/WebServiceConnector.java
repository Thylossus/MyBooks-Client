/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybooks.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mybooks.beans.Data;
import org.xml.sax.SAXException;

/**
 * The web service connector provides a connection to the MyBooks-Bookkeeping
 * web application and handles the communication with it. It implements the
 * singelton pattern, i.e. via <code>getInstance()</code> the connection can be
 * obtained.
 *
 * @author Tobias Kahse <tobias.kahse@outlook.com>
 */
public class WebServiceConnector {

    private static final WebServiceConnector instance = new WebServiceConnector();
    private static final String baseUrl = "http://localhost:8080/MyBooks-Bookkeeping";

    private URL url;
    private XmlParser parser;
    private String jsessionid;

    public static WebServiceConnector getInstance() {
        return WebServiceConnector.instance;
    }

    private WebServiceConnector() {
        this.url = null;
        this.parser = null;
        this.jsessionid = null;
    }

    private HttpURLConnection connect(String command) throws IOException {
        return this.connect(command, true);
    }

    private HttpURLConnection connect(String command, boolean input) throws MalformedURLException, IOException {
        this.url = new URL(WebServiceConnector.baseUrl + command);

        HttpURLConnection con = (HttpURLConnection)this.url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(input);
        con.setUseCaches(false);

        con.setRequestProperty("user-agent", "MyBooks-Client");
        
        if (this.jsessionid != null) {
            con.setRequestProperty("Cookie", "JSESSIONID=" + URLEncoder.encode(this.jsessionid, "UTF-8"));
        }

        return con;
    }

    private boolean send(HttpURLConnection con, HashMap<String, String> params) {
        try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {

            Set<String> keys = params.keySet();
            Iterator<String> keyIterator = keys.iterator();
            String nextKey;
            String param;

            while (keyIterator.hasNext()) {
                nextKey = keyIterator.next();

                param = nextKey + "=" + params.get(nextKey);

                if (keyIterator.hasNext()) {
                    param += "&";
                }

                out.writeBytes(param);
            }
            
            out.flush();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(WebServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    private ArrayList<Data> receive(HttpURLConnection con) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser saxParser = factory.newSAXParser();
        this.parser = new XmlParser();

        InputStream in = con.getInputStream();

        String cookies = con.getHeaderField("Set-Cookie");
        
        if (cookies != null && !cookies.isEmpty()) {
            String[] cookie = cookies.split(",");
            String key, value;
            String[] kvPair;
            
            for (String c : cookie) {
                kvPair = c.split("=");
                key = kvPair[0];
                value = kvPair[1];
                
                if (key.equalsIgnoreCase("JSESSIONID")) {
                    this.jsessionid = value;
                }
            }
        }
        
        saxParser.parse(in, this.parser);

        return this.parser.getData();
    }

    public ArrayList<Data> getData(String command) throws ConnectionException {
        try {
            HttpURLConnection con = this.connect(command);

            try {
                return this.receive(con);
            } catch (IOException ex) {
                throw new ConnectionException("Reading data from input stream failed.", command, ex);
            } catch (ParserConfigurationException | SAXException ex) {
                throw new ConnectionException("Constructing the parser failed.", command, ex);
            }
        } catch (IOException ex) {
            throw new ConnectionException("Connection failed.", command, ex);
        }
    }

    public ArrayList<Data> getData(String command, HashMap<String, String> params) throws ConnectionException {
        try {
            HttpURLConnection con = this.connect(command);

            if (this.send(con, params)) {
                try {
                    return this.receive(con);
                } catch (IOException ex) {
                    throw new ConnectionException("Reading data from input stream failed.", command, ex);
                } catch (ParserConfigurationException | SAXException ex) {
                    throw new ConnectionException("Constructing the parser failed.", command, ex);
                }
            } else {
                throw new ConnectionException("Could not send parameters.", command, params);
            }

        } catch (IOException ex) {
            throw new ConnectionException("Connection failed.", command, ex);
        }
    }

}
