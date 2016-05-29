/* model class for game board
 * implemented by Singleton design pattern
 * */

package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.GameSetting;

public class Board {
	private final int num = 3;
	private Square[][] squares;
	
	private Map<String,Player> players = new HashMap<String, Player>();
	public  Map<String, ArrayList<Piece>> playerPieces = new HashMap<String, ArrayList<Piece>>();
	private ArrayList<Piece> activePlayerPieces = null;
	private Player activePlayer;	
	private int turnCount = 0;
	
	private GameSetting settings = GameSetting.getInstance();
	
	private int boardHeight;
	private int boardWidth;
	private boolean undoFlag = false;
	
	public Player getActivePlayer(){
		return this.activePlayer;
	}

	int pieceNum;
	//use singleton pattern to initialize board instance
	//for there is only one board in a game
	
	public Board(){
		boardHeight = settings.getDimensionHeight();
		boardWidth = settings.getDimensionWidth();
		pieceNum = settings.getPieceNumber();
		
		squares = new Square[boardHeight][boardWidth];	
		initSquares();
	}

	private void initSquares() {
		for(int i = 0; i < boardHeight; i++){
			for(int j = 0; j < boardWidth; j++){
				squares[i][j] = new Square(i, j);
			}
		}
	}
	
	public ArrayList<Piece> getPieces(){
		ArrayList<Piece> allPieces = new ArrayList<Piece>();
		for(Map.Entry<String, ArrayList<Piece>> entry : playerPieces.entrySet()){		
			allPieces.addAll(entry.getValue());
		}
		return allPieces;
	}

	public Piece getPieceByXandY(int x, int y){
		return (Piece) getSquare(x, y).getPiece();
	}
	
	private void setPiece(int x,int y, Piece p){
		Square s = this.getSquare(x,y);
		if(s != null){
		    s.addPiece(p);
		}
		else{
//			throw Exception
		}
	}

	public Square getSquare(int x, int y) {
		return this.squares[x][y];
	}

	public void setPieceforPlayer(Player p1, PieceClass[] p1Pieces, int posY) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		playerPieces.put(p1.getPlayerName(), pieces);
		int j =0;
		
		for(int i = 0; i < p1Pieces.length; i++){
			
			for(; j < this.pieceNum/p1Pieces.length * (i+1); j++){
				
				int posX = j;				
				if(j == this.boardHeight){
					if(posY < this.boardWidth/2){
						posY++;
					}else{
						posY--;
					}
				}
				
				if(j >= this.boardHeight){
					posX = j - this.boardHeight;
				}
				
				Piece p = (Piece) SquareComponentFactory.createPiece(p1Pieces[i],posX, posY);
				this.setPiece(posX, posY , p);
			    pieces.add(p);
			}
		}
	}

	public boolean piecesOfOnePlayerAllRemoved() {
		boolean removed = false;
		for(ArrayList<Piece> pieceList : playerPieces.values()){
			if(pieceList.isEmpty()){
				removed = true;
			}
		}
		return removed;
	}

	public void switchActivePieces() {
		if(undoFlag){
			this.getActivePlayer().banUndo();
			undoFlag = false;
		}

		int playerID = (turnCount++)%2 +1;
		String playerName = "p"+ playerID;
//		System.out.println(playerName);
		activePlayer = players.get(playerName);
		if(activePlayer == null){
			System.out.println("player error!");
		}
		
		for(Map.Entry<String, ArrayList<Piece>> entry : playerPieces.entrySet()){
			if(entry.getKey().equals(playerName)){
				for(Piece p: entry.getValue()){
					p.setPieceMovable();
				}
			}
			else{
				for(Piece p: entry.getValue()){
					p.setPieceUnmovable();
				}
			}
		}
		
	}

	public ArrayList<Piece> getActivePieces() {
		return this.activePlayerPieces;
	}
	
	public boolean isPiece(int x, int y){
		return squares[x][y].getPiece()!=null;
	}


	public void movePieceFromTo(int pieceX, int pieceY, int x, int y) {
		Piece p = this.getPieceByXandY(pieceX, pieceY);
		p.moveTo(x, y);
		this.getSquare(pieceX, pieceY).removePiece();;
		this.setPiece(x, y, p);
	}

	public void attackFromAtoB(int aPosX, int aPosY, int bPosX, int bPosY) {
		Piece pa = this.getPieceByXandY(aPosX, aPosY);
		Piece pb = this.getPieceByXandY(bPosX, bPosY);
		
		pb.getHurt(pa.getPower());
		
		if(pb.getHealthyPoint() <= 0){
			this.squares[bPosX][bPosY].removePiece();
		}else{
		}
		
		
	}
	
	public ArrayList<Piece> getPiecesByPlayerID(String id){
		return this.playerPieces.get(id);
	}

	public int getBoardHeight() {
		return this.boardHeight;
	}

	public int getBoardWidth() {
		return this.boardWidth;
	}

	public void markActivePlayerTurnUndo() {
		this.undoFlag  = true;
	}

	public void addPlayerPieces(String playerID, ArrayList<Piece> playerPieces) {
		this.playerPieces.put(playerID, playerPieces);
		for(Piece p :playerPieces){
			int x = p.getPosX();
			int y = p.getPosY();
			this.squares[x][y].addPiece(p);
		}
		
	}

	public void addPlayer(Player player) {
		players.put(player.getPlayerName(), player);
	}
}
