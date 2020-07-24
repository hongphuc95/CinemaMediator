package ied;

import javax.xml.xpath.*;
import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XPathQuery {
	
	public void XPathQuery() {
		
	}

    private static Object XPath(String uri, String requete, QName typeRetour){
		//Le dernier paramètre indique le type de résultat souhaité
		//XPathConstants.STRING: chaîne de caractères (String)
		//XPathConstants.NODESET: ensemble de noeuds DOM (NodeList)
		//XPathConstants.NODE: noeud DOM (Node) - le premier de la liste
		//XPathConstants.BOOLEAN: booléen (Boolean) - vrai si la liste n'est pas vide
		//XPathConstants.NUMBER: numérique (Double) - le contenu du noeud sélectionné transformé en Double

        try{
		//Transformation en document DOM du contenu XML
		DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
		DocumentBuilder parseur = fabrique.newDocumentBuilder();
		Document document = parseur.parse(uri);

		//création de l'objet XPath 
        	XPathFactory xfabrique = XPathFactory.newInstance();
        	XPath xpath = xfabrique.newXPath();
        
        	//évaluation de l'expression XPath
        	XPathExpression exp = xpath.compile(requete);
        	return exp.evaluate(document, typeRetour);
        	
        } catch(Exception e){
        	System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void getXPathResult(MovieInfo mi, String movie) {
    	movie = movie.replaceAll("\\s", "+");
    	String uri = "http://www.omdbapi.com/?apikey=8f60a1f&r=xml"
    			+ "&t=" + movie;
    	//System.out.println(uri);
    	Object o = XPath(uri, "/root/movie/@plot", XPathConstants.NODE);
    	if (o != null) {
    		Node node = (Node)o;
        	if (node.getTextContent() != null && !node.getTextContent().equals("")) {
        		mi.setPlot(node.getTextContent());
        	}
    	}
    }
        	
}