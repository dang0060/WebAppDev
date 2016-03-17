/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.dataobjects;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alayna
 */
public class Keychain {
    
    private static Keychain keychain = null;
    private static NodeList list;
    
    private Keychain(){}
    
    public static Keychain getInstance(){
        if (keychain==null){
            try {
                keychain = new Keychain();
                File keyfile = new File("C:\\keychain.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(keyfile);
                doc.getDocumentElement().normalize();
                list = doc.getElementsByTagName("key");
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(Keychain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return keychain;
    }
    
    
    public String getKey(String name){
        String key=null;
        for(int i=0;i<list.getLength();i++){
            if(list.item(i).getNodeType()==Node.ELEMENT_NODE){
                Element element=(Element)list.item(i);
                if(element.getAttribute("name").equals(name))
                    key=element.getAttribute("value");    
            }  
        }
            
        return key;
    }
}
