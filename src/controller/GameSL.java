package controller;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Board;
import model.Game;
import model.Piece;
import model.PieceClass;
import model.Player;
import model.SquareComponentFactory;
import utils.GameSetting;

import java.util.HashMap;
import java.util.Map;
/** a utility used to save and load game model,
 *  game model would be saved as a xml file*/
public class GameSL {
	/***save game to XML file*/
    private static Document document;
    
	public static void init() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        } 
        catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

	/**
	 * Game Save and Load board method
	 * **/
	/**save board pieces into XML file**/
	public static void saveBoard(Game g,String fileName){
		init();
		Board board  = g.getBoard();
		Element root = document.createElement("board");   //XML root name
		
		Element width = document.createElement("width");
		width.appendChild(document.createTextNode(GameSetting.getInstance().getDimensionWidth()+""));
		
		Element height = document.createElement("height");
		height.appendChild(document.createTextNode(GameSetting.getInstance().getDimensionHeight()+""));
		
		root.appendChild(width);
		root.appendChild(height);
		
		Element active_player = document.createElement("activeplayer");
		active_player.appendChild(document.createTextNode(g.getActivePlayer().getPlayerName()));
		root.appendChild(active_player);

		for(Player p:g.getPlayers()){
			Element player = document.createElement("PLAYER");
			Element playerid = document.createElement("id");
			playerid.appendChild(document.createTextNode(p.getPlayerName()));
			
			Element undoCount = document.createElement("undocount");
			undoCount.appendChild(document.createTextNode(p.getUndoCount()+""));
			
			Element undoavaible = document.createElement("undoavailable");
			undoavaible.appendChild(document.createTextNode(p.isUndoAvailable()+""));
			
			player.appendChild(playerid);
			player.appendChild(undoCount);
			player.appendChild(undoavaible);
			
			root.appendChild(player);
		}
		
        document.appendChild(root);
        /**Get current pieces for player 1 and player 2**/
        
        ArrayList<Piece> p1 = board.getPiecesByPlayerID("p1");
        ArrayList<Piece> p2 = board.getPiecesByPlayerID("p2");
		  /**Loop through all pieces of player 1 and append their attribute into XML file**/
          for(int i=0; i<p1.size();i++)
		     {
					Element pieces = document.createElement("pieces"); 
					
			        Element player = document.createElement("player"); 			        
			        player.appendChild(document.createTextNode("p1")); 
			        pieces.appendChild(player); 
					 
			        Element name = document.createElement("name"); 
			        name.appendChild(document.createTextNode(p1.get(i).getPieceClassString())); 
			        pieces.appendChild(name); 
			        
			        Element posX = document.createElement("posX"); 
			        posX.appendChild(document.createTextNode(String.valueOf(p1.get(i).getPosX()))); 
			        pieces.appendChild(posX);
			        
			        Element posY = document.createElement("posY"); 
			        posY.appendChild(document.createTextNode(String.valueOf(p1.get(i).getPosY()))); 
			        pieces.appendChild(posY);
			        
			        Element hp = document.createElement("hp"); 
			        hp.appendChild(document.createTextNode(p1.get(i).getHealthyPoint()+"")); 
			        pieces.appendChild(hp);
			        
			        root.appendChild(pieces);		
				}
          /**Loop through all pieces of player 2 and append their attribute into XML file**/
		   for(int i=0; i<p2.size();i++)
		     {
					Element pieces = document.createElement("pieces"); 
					
			        Element player =document.createElement("player"); 			        
			        player.appendChild(document.createTextNode("p2")); 
			        pieces.appendChild(player); 
					 
			        Element name = document.createElement("name"); 
			        name.appendChild(document.createTextNode(p2.get(i).getPieceClassString())); 
			        pieces.appendChild(name); 
			        
			        Element posX = document.createElement("posX"); 
			        posX.appendChild(document.createTextNode(String.valueOf(p2.get(i).getPosX()))); 
			        pieces.appendChild(posX);
			        
			        Element posY = document.createElement("posY"); 
			        posY.appendChild(document.createTextNode(String.valueOf(p2.get(i).getPosY()))); 
			        pieces.appendChild(posY);
			        
			        Element hp = document.createElement("hp"); 
			        hp.appendChild(document.createTextNode(p2.get(i).getHealthyPoint()+"")); 
			        pieces.appendChild(hp);
			        
			        root.appendChild(pieces);		
				}
		  /***Write XML file**/
		TransformerFactory tf = TransformerFactory.newInstance(); 
        
		try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.println("Game Saved");
        } 
        catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } 
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } 
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } 
        catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
    }
	
	/**Load Game from XML file**/
	public static void loadBoard(Game g,String fileName) {
		/***Read XML file**/
		try {   
			   File file = new File(fileName);   
			   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
			   DocumentBuilder builder = factory.newDocumentBuilder();   
			   Document doc = builder.parse(file);   
			   
			   ArrayList<Piece> p1Pieces = new ArrayList<Piece>();
			   ArrayList<Piece> p2Pieces = new ArrayList<Piece>();
			   /**Get pieces paremeters from XML file**/			   
			   int width = Integer.parseInt(doc.getElementsByTagName("width").item(0).getFirstChild().getNodeValue());
			   int height = Integer.parseInt(doc.getElementsByTagName("height").item(0).getFirstChild().getNodeValue());
			   
			   GameSetting.getInstance().setDimensionHeight(height);
			   GameSetting.getInstance().setDimensionWidth(width);
			   
			   g.initializeBoard();
			   Board board = g.getBoard();
			   
			   NodeList playerLst = doc.getElementsByTagName("PLAYER");
			   for(int i = 0; i< playerLst.getLength();i++){
				   NodeList idLst =doc.getElementsByTagName("id");
				   NodeList countLst = doc.getElementsByTagName("undocount");
				   NodeList avaLst = doc.getElementsByTagName("undoavailable");
				   
				   String playerid = idLst.item(i).getFirstChild().getNodeValue();
				   int undoCount = Integer.parseInt(countLst.item(i).getFirstChild().getNodeValue());
				   boolean undoAvail = Boolean.parseBoolean(avaLst.item(i).getFirstChild().getNodeValue());
				   
				   Player  p = new Player(playerid);
				   p.setUndoCount(undoCount);
				   p.setUndoAvailable(undoAvail);
				   
				   g.addPlayer(p);
			   }
			   NodeList nl = doc.getElementsByTagName("pieces");  
			   /***Loop all pieces and re-build their status, position, HP, etc.**/
			   for (int i = 0; i < nl.getLength(); i++) {   
				   PieceClass pc = null;
				   int x=Integer.parseInt(doc.getElementsByTagName("posX").item(i).getFirstChild().getNodeValue());
				   int y=Integer.parseInt(doc.getElementsByTagName("posY").item(i).getFirstChild().getNodeValue());
				   int hp = Integer.parseInt(doc.getElementsByTagName("hp").item(i).getFirstChild().getNodeValue());
			
				   if(doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue().equals("MAGE"))	   
				   {
					   pc = PieceClass.MAGE;
				   }
				   else if(doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue().equals("HUNTER"))
				 {pc = PieceClass.HUNTER;} 
			else if(doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue().equals("PALADIN"))
				 {pc = PieceClass.PALADIN;}
			else if(doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue().equals("PRISST"))
				 {pc = PieceClass.PRISST;}
			else if(doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue().equals("ROGUE"))
				 {pc = PieceClass.ROGUE;}
			else if(doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue().equals("WARRIOR"))
				 {pc = PieceClass.WARRIOR;}		 	  
				   Piece p = (Piece) SquareComponentFactory.createPiece(pc,x,y);  
				   p.setHP(hp);
				   /***Put these pieces into Player 1 sets or Player 2 sets***/
				    if(doc.getElementsByTagName("player").item(i).getFirstChild().getNodeValue().equals("p1"))
				    {p1Pieces.add(p);}
				    else
				    {p2Pieces.add(p);}
			     }//end of for loop
			   board.playerPieces.clear();
			   
			   board.addPlayerPieces("p1", p1Pieces);
			   board.addPlayerPieces("p2", p2Pieces);
			   
			   String activeplayerName = doc.getElementsByTagName("activeplayer").item(0).getFirstChild().getNodeValue();
			   if(!activeplayerName.equals("p1")){
				   board.switchActivePieces();
			   }

			   //load completed with playerPiece output
			  } 
		catch (Exception e) {   
			   e.printStackTrace();   
			  }   
		 
	}

}
