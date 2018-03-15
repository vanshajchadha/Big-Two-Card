import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.lang.Thread;

import javax.swing.JOptionPane;

/**
 * BigTwoClient class implements the CardGame and NetworkGame interfaces and is responsible for holding data for all the players/ clients playing the BigTwo card game as well as sending and receiving messages to and from the server
 * @author vanshajchadha
 */
public class BigTwoClient implements CardGame,NetworkGame {
	
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx;
	private BigTwoTable table;

	/**
	 * A constructor for initializing the BigTwoClient object by taking the name of the player as input and creating the playerList, handsOnTable ArrayLists as well as invoking BigTwoTable object responsible for creating the GUI
	 */
	public BigTwoClient(){
		playerName=JOptionPane.showInputDialog(null,"Please enter your name:");
		if(playerName!=null){
			playerList=new ArrayList<CardGamePlayer>();
			for(int i=0;i<4;i++){
				playerList.add(new CardGamePlayer());
				playerList.get(i).setName(null);
			}
			handsOnTable=new ArrayList<Hand>();
			table=new BigTwoTable(this);
			table.disable();
			makeConnection();	
		}
	}
	
	/**
	 * ServerHandler an inner class that implements the Runnable interface and is instantiated in makeConnection method in order to create a new thread for receiving messages
	 * @author vanshajchadha
	 */
	private class ServerHandler implements Runnable{
		private ObjectInputStream ois;
		
		/**
		 * The new thread created executes this which performs reading and parsing of messages sent by the server to the clients or the users of the game 
		 */
		public void run(){
			try {
				ois = new ObjectInputStream(sock.getInputStream());
			} catch (Exception e1) {
				
			}
			
			CardGameMessage gameMessage;
			try {
				while ((gameMessage = (CardGameMessage) ois.readObject())!=null) {
					parseMessage(gameMessage);
				}
			} catch(EOFException ex){
				
			} catch (Exception e) {
				
			} 

		}
	}
	
	/**
	 * Creates a BigTwoClient object and thereby starts the client side of the game
	 * @param args an array of String values used for command line
	 */
	public static void main(String[] args){
		BigTwoClient bigTwoClient=new BigTwoClient();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	public void setPlayerID(int playerID) {
		this.playerID=playerID;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	public String getPlayerName() {
		return playerName;
	}

	/** 
	 * {@inheritDoc}
	 */
	public void setPlayerName(String playerName) {
		this.playerName=playerName;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	public String getServerIP() {
		return serverIP;
	}

	/** 
	 * {@inheritDoc}
	 */
	public void setServerIP(String serverIP) {
		this.serverIP=serverIP;
	}

	/** 
	 * {@inheritDoc}
	 */
	public int getServerPort() {
		return serverPort;
	}

	/** 
	 * {@inheritDoc}
	 */
	public void setServerPort(int serverPort) {
		this.serverPort=serverPort;
	}
	
	/**
	 * Closes the socket connection when the player wants to quit the game
	 */
	public void quit(){
		try {
			oos.close();
			sock.close();
		} catch (IOException e) {
			System.out.println("Could not close the socket");
			e.printStackTrace();
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	public void makeConnection() {
		try{
			sock=new Socket("127.0.0.1",2396);
			oos=new ObjectOutputStream(sock.getOutputStream());
			oos.flush();
			sendMessage(new CardGameMessage(CardGameMessage.JOIN,-1, playerName));
			sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
		}
		catch (Exception ex) { 
			table.setConnButton();
			System.out.println("Failed to connect with the server!");
			ex.printStackTrace(); 
		}
		Thread receivemsg=new Thread(new ServerHandler());
		receivemsg.start();
	}

	/** 
	 * {@inheritDoc}
	 */
	public synchronized void parseMessage(GameMessage message) {
		if(message.getType()==0){			// PLAYER_LIST
			this.playerID=message.getPlayerID();
			if(message.getData() instanceof String[]){
				String[] str=(String[]) message.getData();
				for(int i=0;i<4;i++)
					playerList.get(i).setName(str[i]);
			}
		}
		
		else if(message.getType()==1){  	// JOIN
			if(message.getData() instanceof String){
				String str=(String) message.getData();
				playerList.get(message.getPlayerID()).setName(str);
			}
			table.repaint();
		}
		
		else if(message.getType()==2){		// FULL
			table.printMsg("The server is full right now. Kindly join the game later!");
		}
		
		else if(message.getType()==3){		// QUIT
			playerList.get(message.getPlayerID()).setName("");
			table.disable();
			if(!endOfGame()){
				for(int i=0;i<4;i++){
					playerList.get(i).removeAllCards();
				}
				sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
			}
			handsOnTable.clear();
			table.repaint();
		}
		
		else if(message.getType()==4){		// READY
			table.printChat(playerList.get(message.getPlayerID()).getName()+" is ready");
		}
		
		else if(message.getType()==5){		// START
			table.clearChat();
			if(message.getData() instanceof BigTwoDeck){
				BigTwoDeck d=(BigTwoDeck) message.getData();
				start(d);
			}
		}
		
		else if(message.getType()==6){		// MOVE
			if(message.getData() instanceof int[]){
				int[] a=(int[]) message.getData();
				checkMove(message.getPlayerID(),a);
			}
			else if(message.getData()==null){
				checkMove(message.getPlayerID(),null);
			}
			table.repaint();
		}
		
		else if(message.getType()==7){		// MSG
			if(message.getData() instanceof String){
				String msg=(String) message.getData();
				table.printChat(msg);
			}
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	public synchronized void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	/** 
	 * {@inheritDoc}
	 */
	public Deck getDeck() {
		return deck;
	}

	/** 
	 * {@inheritDoc}
	 */
	public ArrayList<CardGamePlayer> getPlayerList() {
		return playerList;
	}

	/** 
	 * {@inheritDoc}
	 */
	public ArrayList<Hand> getHandsOnTable() {
		return handsOnTable;
	}

	/** 
	 * {@inheritDoc}
	 */
	public int getCurrentIdx() {
		return currentIdx;
	}

	/** 
	 * {@inheritDoc}
	 */
	public void start(Deck deck) {
		for(int i=0;i<4;i++){
			playerList.get(i).removeAllCards();
		}
		handsOnTable.clear();
		int count=0;
		for(int i=0;i<13;i++){
			for(int j=0;j<4;j++){
				playerList.get(j).addCard((BigTwoCard)deck.getCard(count));		// Distributing cards to all 4 players
				count+=1;
			}
		}  
		BigTwoCard three_D=new BigTwoCard(0,2);  	// The card 3 of Diamonds
		
		for(int i=0;i<4;i++){
			CardList cardList=playerList.get(i).getCardsInHand();
			if(cardList.contains(three_D)){
					currentIdx=i;
					break;
			}
		}
		
		table.setActivePlayer(currentIdx);
		table.reset();	
	}

	/** 
	 * {@inheritDoc}
	 */
	public synchronized void makeMove(int playerID, int[] cardIdx) {
		sendMessage(new CardGameMessage(CardGameMessage.MOVE,-1,cardIdx));	
	}

	/** 
	 * {@inheritDoc}
	 */
	public synchronized void checkMove(int playerID, int[] cardIdx) {
		CardGamePlayer lastactive=playerList.get(currentIdx);
		if(handsOnTable.size()!=0)
			lastactive=handsOnTable.get(handsOnTable.size()-1).getPlayer();    // To prevent the same player from passing again in the same round
		BigTwoCard three_D=new BigTwoCard(0,2);
		Hand hand=null;
		boolean success=false;
		
		if(lastactive.getName()==playerList.get(playerID).getName()){
				if(handsOnTable.size()==0){ 		// First player
					if(cardIdx==null)
						table.printMsg("{Pass} <== Not a legal move!");
					else{				
						CardList handPlayed=playerList.get(currentIdx).play(cardIdx);
						
						if(handPlayed.contains(three_D)){
							hand=composeHand(playerList.get(currentIdx), handPlayed);
							if(hand==null){
								String s="";
								for (int i = 0; i < handPlayed.size(); i++) 
									s+="["+handPlayed.getCard(i).toString()+"] ";
								table.printMsg(s+" <== Not a legal move!");
							}
							else{
								success=true;
								handsOnTable.add(hand);	
								String s="{"+hand.getType()+"}  ";
								for (int i = 0; i < handPlayed.size(); i++) 
									s+="["+handPlayed.getCard(i).toString()+"] ";
								table.printMsg(s);
								playerList.get(currentIdx).removeCards(handPlayed);
							}	
						}
						else{
							String s="";
							for (int i = 0; i < handPlayed.size(); i++) 
								s+="["+handPlayed.getCard(i).toString()+"] ";
							table.printMsg(s+" <== Hand must contain 3 of Diamonds!");
						}
					}	
				}
				
				
				else{					// Rest of the players who played the last hand on the table
					if(cardIdx==null){
						table.printMsg("{Pass} <== Not a legal move!");
					}
					else{
						CardList handPlayed=playerList.get(currentIdx).play(cardIdx);
						hand=composeHand(playerList.get(currentIdx), handPlayed);
						if(hand==null){
							String s="";
							for (int i = 0; i < handPlayed.size(); i++) 
								s+="["+handPlayed.getCard(i).toString()+"] ";
							table.printMsg(s+" <== Not a legal move!");
						}
						else{
							success=true;
							handsOnTable.add(hand);	
							String s="{"+hand.getType()+"}  ";
							for (int i = 0; i < handPlayed.size(); i++) 
								s+="["+handPlayed.getCard(i).toString()+"] ";
							table.printMsg(s);
							playerList.get(currentIdx).removeCards(handPlayed);
						}
					}
			   }
		}
		
		else{
				if(cardIdx==null){
					table.printMsg("{Pass}");
					success=true;
				}
				else{
					CardList handPlayed=playerList.get(currentIdx).play(cardIdx);
					hand=composeHand(playerList.get(currentIdx), handPlayed);
					if(hand==null){
						String s="";
						for (int i = 0; i < handPlayed.size(); i++) 
							s+="["+handPlayed.getCard(i).toString()+"] ";
						table.printMsg(s+" <== Not a legal move!");
					}
					else{
						if(hand.beats(handsOnTable.get(handsOnTable.size()-1))){
							success=true;
							lastactive=playerList.get(currentIdx);
							handsOnTable.add(hand);	
							String s="{"+hand.getType()+"}  ";
							for (int i = 0; i < handPlayed.size(); i++) 
								s+="["+handPlayed.getCard(i).toString()+"] ";
							table.printMsg(s);
							playerList.get(currentIdx).removeCards(handPlayed);
						}
						else{
							String s="{"+hand.getType()+"}  ";
							for (int i = 0; i < handPlayed.size(); i++) 
								s+="["+handPlayed.getCard(i).toString()+"] ";
							table.printMsg(s+" <== Not a legal move!");
							
						}
					}
				}
		}
		
		
		if(endOfGame()){
			table.clearMsgArea();
			String s="Game ends\n";
			for(int i=0;i<4;i++){
				if(playerList.get(i).getNumOfCards()!=0)
					s+="Player "+i+" has "+playerList.get(i).getNumOfCards()+" cards in hand.\n";
				else
					s+="Player "+i+" wins the game.\n";
			}	
			handsOnTable.clear();
			table.disable();
			JOptionPane.showMessageDialog(null, s);
			sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
		}
		
		else{
			if(success){
				currentIdx++;
				currentIdx%=4;
				table.setActivePlayer(currentIdx);
				table.printMsg(playerList.get(currentIdx).getName()+"'s chance:");
				if(currentIdx==getPlayerID())
					table.enable();
				
				else
					table.disable();
			}
			else{
				table.printMsg(playerList.get(currentIdx).getName()+"'s chance:");
			}
		}
		
	}

	/** 
	 * {@inheritDoc}
	 */
	public boolean endOfGame() {
		boolean ans=false;
		for(int i=0;i<4;i++){
			if(playerList.get(i).getNumOfCards()==0)
				ans=true;
		}
		return ans;
	}

	/**
	 * Returns a valid hand from the specified list of cards of the player and null is no valid hand can be composed from the specified list of cards
	 * @param player a CardGamePlayer reference pointing to the object containing the name of the player and list of cards held by him in the game
	 * @param cards a CardList reference pointing to the object containing the set of cards which need to be checked to see if they form a valid hand or not
	 * @return a Hand reference pointing to the object containing the data for the player along with the list of cards forming a valid hand, otherwise null
	 */
	Hand composeHand(CardGamePlayer player, CardList cards){
		int numOfCardsInHand=cards.size();
		if(numOfCardsInHand==1){
			Single single=new Single(player,cards);
			
			if(single.isValid())
				return single;
			else 
				return null;
		}
		else if(numOfCardsInHand==2){
			Pair pair=new Pair(player,cards);
			
			if(pair.isValid())
				return pair;
			else 
				return null;
		}
		else if(numOfCardsInHand==3){
			Triple triple=new Triple(player,cards);
			
			if(triple.isValid())
				return triple;
			else 
				return null;
		}
		else if(numOfCardsInHand==5){
			Quad quad=new Quad(player,cards);
			Flush flush=new Flush(player,cards);
			Straight straight=new Straight(player,cards);
			StraightFlush straightflush=new StraightFlush(player,cards);
			FullHouse fullhouse=new FullHouse(player,cards);
			
			if(quad.isValid())
				return quad;
			else if(flush.isValid())
				return flush;
			else if(fullhouse.isValid())
				return fullhouse;
			else if(straight.isValid())
				return straight;
			else if(straightflush.isValid())
				return straightflush;
			else
				return null;
		}
		else
			return null;
	}

}

