import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

    public class XpathExpressionResolver {

        private static Document xmlDocument;
        private static XPath xPath;
        private String xmlFileUrl;


        public XpathExpressionResolver(String xmlFileUrl){
            this.xmlFileUrl = xmlFileUrl;
            this.xmlDocument = createXmlDocument();
            this.xPath = createXPath();
        }


        public Object resolve(String xPathExpression, QName qName){
            if (qName.equals(XPathConstants.NODESET)) {
                return getNodeValues(getXmlNodes(xPathExpression));
            }else{
                return getXmlString(xPathExpression);
            }
        }

        private XPath createXPath(){
            XPathFactory xpathFactory = XPathFactory.newInstance();
            return xpathFactory.newXPath();
        }

        private Document createXmlDocument() {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            try{
                DocumentBuilder builder = factory.newDocumentBuilder();
                return builder.parse(xmlFileUrl);
            }catch (ParserConfigurationException | IOException | SAXException e){
                e.printStackTrace();
                return null;
            }
        }

        private String getXmlString(String xpathExpression){
            try{
                XPathExpression expr = xPath.compile(xpathExpression);
                return (String) expr.evaluate(xmlDocument, XPathConstants.STRING);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to evaluate Xpath Expression");
            }
        }

        private NodeList getXmlNodes(String xpathExpression){
            try{
                XPathExpression expr = xPath.compile(xpathExpression);
                return (NodeList) expr.evaluate(xmlDocument, XPathConstants.NODESET);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to evaluate Xpath Expression");
            }
        }

        private List<String> getNodeValues(NodeList nodeList){
            List nodeValueList = new ArrayList();
            for (int i = 0; i < nodeList.getLength(); i++)
                nodeValueList.add(nodeList.item(i).getNodeValue());
            return nodeValueList;
        }
    }
