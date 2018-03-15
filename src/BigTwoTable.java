import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * BigTwoTable class implements the CardGameTable interface and is used to build a GUI for the Big Two card game and handle all the user actions like mouse click using listeners and event-handlers
 * @author vanshajchadha
 */


public class BigTwoTable implements CardGameTable {
	private final static String newline = "\n";
	private CardGame game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;
	private ArrayList<ImageLocation> images;
	private boolean active;	
	private JTextArea chat;
	private JTextField msgspace;
	private JMenuItem conn;
	
	/**
	 * A constructor for initializing the BigTwoTable object by creating all the components and adding it to the frame along with storing the images into arrays
	 * @param game a CardGame reference which can contain the reference of any class that implements the CardGame interface which in this case is the BigTwo class that contains the information about the player list, hands played and player currently playing
	 */
	public BigTwoTable(CardGame game){
		this.game=game;
		frame=new JFrame();
		frame.setTitle("Big Two Card Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar=new JMenuBar();
		JMenu menu = new JMenu("Game");
		conn = new JMenuItem("Connect");
		JMenuItem quit = new JMenuItem("Quit");
		menu.add(conn);
		conn.setEnabled(false);
		menu.add(quit);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		conn.addActionListener(new ConnectMenuItemListener());
		quit.addActionListener(new QuitMenuItemListener());
		
		JPanel big=new JPanel();
		big.setLayout(new BorderLayout());
		JPanel container=new JPanel();
		playButton=new JButton("Play");
		playButton.addActionListener(new PlayButtonListener());
		passButton=new JButton("Pass");
		passButton.addActionListener(new PassButtonListener());
		JLabel label=new JLabel("Message:");
		label.setForeground(Color.white);
		msgspace=new JTextField(35);
		container.add(playButton);
		container.add(passButton);
		container.setBackground(Color.BLACK);
		big.add(container, BorderLayout.CENTER);
		msgspace.addActionListener(new MsgListener());
		JPanel M=new JPanel();
		M.add(label);
		M.add(msgspace);
		M.setBackground(Color.BLACK);
		big.add(M,BorderLayout.EAST);
		big.setPreferredSize(new Dimension(1000,50));
		big.setBackground(Color.BLACK);
		frame.add(big,BorderLayout.SOUTH);
		
		cardBackImage=new ImageIcon("src/cards/backcard.gif").getImage();
		avatars=new Image[4];
		for(int i=0;i<4;i++){
			avatars[i]=new ImageIcon("src/superheroes/"+i+".png").getImage();
		}
		cardImages=new Image[4][13];
		for(int i=0;i<4;i++){
			for(int j=0;j<13;j++){
				if(i==0){
					cardImages[i][j]=new ImageIcon("src/cards/"+j+"d.gif").getImage();
				}
				else if(i==1){
					cardImages[i][j]=new ImageIcon("src/cards/"+j+"c.gif").getImage();
				}
				else if(i==2){
					cardImages[i][j]=new ImageIcon("src/cards/"+j+"h.gif").getImage();
				}
				else{
					cardImages[i][j]=new ImageIcon("src/cards/"+j+"s.gif").getImage();
				}
			}
		}
		active=true;
		
		bigTwoPanel=new BigTwoPanel();
		bigTwoPanel.setPreferredSize(new Dimension(500,600));
		msgArea=new JTextArea();
		JScrollPane scroller1 = new JScrollPane(msgArea);
	    scroller1.setVerticalScrollBarPolicy(
	             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroller1.setHorizontalScrollBarPolicy(
	             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    chat=new JTextArea();
		JScrollPane scroller2 = new JScrollPane(chat);
	    scroller2.setVerticalScrollBarPolicy(
	             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroller2.setHorizontalScrollBarPolicy(
	             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                scroller1, scroller2);
	    splitPane1.setDividerLocation(350);
	    splitPane1.setResizeWeight(0.5);
	    splitPane1.setEnabled(false);
	    
	    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                bigTwoPanel, splitPane1);
	    splitPane.setDividerLocation(650);
	    splitPane.setResizeWeight(0.5);
	    splitPane.setEnabled(false);
	    frame.add(splitPane,BorderLayout.CENTER);
		
		frame.setSize(1000,700);
	    frame.setVisible(true);
	}
	
	/**
	 * ImageLocation is an inner class which is used to store the dimensions and position of an image in the JFrame
	 * @author vanshajchadha
	 */
	public class ImageLocation {
		private int x;
		private int y;
		private int width;
		private int height;
		
		/**
		 * A constructor for initializing an ImageLocation object with the position coordinates of the image in the JFrame and it's dimensions like the width and height
		 * @param x  an integer value containing the x-coordinate of the upper left corner of the image
		 * @param y an integer value containing the y-coordinate of the upper left corner of the image
		 * @param width an integer value containing the width of the image
		 * @param height an integer value containing the height of the image
		 */
		public ImageLocation(int x, int y, int width, int height){
			this.x=x;
			this.y=y;
			this.width=width;
			this.height=height;
		}
		
		/**
		 * Retrieves the x-coordinate of the upper left corner of the image
		 * @return an integer value containing the x-coordinate of the upper left corner of the image
		 */
		public int getx(){
			return x;
		}
		
		/**
		 * Retrieves the y-coordinate of the upper left corner of the image
		 * @return an integer value containing the y-coordinate of the upper left corner of the image
		 */
		public int gety(){
			return y;
		}
		
		/**
		 * Retrieves the width of the image
		 * @return an integer value containing the width of the image
		 */
		public int getwidth(){
			return width;
		}
		
		/**
		 * Retrieves the height of the image
		 * @return an integer value containing the height of the image
		 */
		public int getheight(){
			return height;
		}
		
	}

	
	/**
	 * BigTwoPanel is an inner class that is a subclass of the JPanel class and implements the MouseListener interface and is used to draw the card game table in the GUI and handle all the mouse clicks performed by the user in order to select cards using the mouseClicked event-handler
	 * @author vanshajchadha
	 */
	class BigTwoPanel extends JPanel implements MouseListener{

		private static final long serialVersionUID = -4423615053943175975L;
		
		/**
		 * A constructor for initializing the BigTwoPanel object by registering itself to the MouseListener in order to recognize the mouse clicks made by the user on the card game table
		 */
		public BigTwoPanel(){		
			this.addMouseListener(this);
		}
		
		/**
		 * Draws the images, lines, strings along with painting the background of the panel representing the card game table using the Graphics object and is a overridden method of the JPanel class 
		 */
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Color color=new Color(0,153,76);
			Graphics2D g2=(Graphics2D)g;
			setOpaque(true);
			setBackground(color);

			int c=50,count=0;
			BigTwoClient bigTwo=(BigTwoClient) game;
			ArrayList<CardGamePlayer> list=bigTwo.getPlayerList();
			
			for(Image i:avatars){
				CardGamePlayer player=list.get(count++);
				if(player.getName()!=null){
					g2.drawString(player.getName(), 12, c-20);
					g2.drawImage(i, 20, c, this);
					g2.drawLine(0, c+80, 1000, c+80);
				}
				c+=120;
			}
			
			c=25;
			images=new ArrayList<ImageLocation>();
			for(int i=0;i<4;i++){
					if(i==bigTwo.getPlayerID()){
						CardList cardList=list.get(i).getCardsInHand();
						for(int k=0;k<cardList.size();k++){
							int r=cardList.getCard(k).getRank();
							int s=cardList.getCard(k).getSuit();
							if(selected!=null){
								if(selected[k]){
									g2.drawImage(cardImages[s][r], 120+k*36, c-10, this);
									ImageLocation imgLoc=new ImageLocation(120+k*36,c-10,cardImages[s][r].getWidth(this),cardImages[s][r].getHeight(this));
									images.add(imgLoc);
								}
								else{
									g2.drawImage(cardImages[s][r], 120+k*36, c, this);
									ImageLocation imgLoc=new ImageLocation(120+k*36,c,cardImages[s][r].getWidth(this),cardImages[s][r].getHeight(this));
									images.add(imgLoc);
								}
							}
						}
					}
					else{
						
						for(int j=0;j<list.get(i).getNumOfCards();j++){
							g2.drawImage(cardBackImage, 120+j*36, c, this);
						}
					}
				c+=120;
			}
			
			if(bigTwo.getHandsOnTable().size()==0){
				g2.drawString("No cards on the table yet!", 10, 510);
			}
			else{
				g2.drawString("Played by "+bigTwo.getHandsOnTable().get(bigTwo.getHandsOnTable().size()-1).getPlayer().getName(), 10, 510);
				for(int i=0;i<bigTwo.getHandsOnTable().get(bigTwo.getHandsOnTable().size()-1).size();i++){
					Card card=bigTwo.getHandsOnTable().get(bigTwo.getHandsOnTable().size()-1).getCard(i);
					int r=card.getRank();
					int s=card.getSuit();
					g2.drawImage(cardImages[s][r], 150+i*15,  500, this);
				}
			}
			
		}

		/**
		 * An Event-handler which performs certain operations on the instance variables in order to update them after every click on the card game table which are reflected back in the GUI as repaint method is invoked at the end
		 */
		public void mouseClicked(MouseEvent e) {	
			if(active){
				int x=e.getX();
				int y=e.getY();
				int c=0,i=0;
				
				for(;i<images.size()-1;i++){
					if(!selected[i+1]){
						if(x>=images.get(i).x && x<(images.get(i).x+images.get(i).width) && y>=images.get(i).y && y<(images.get(i).y+10) && selected[i]){
							selected[i]=false;	
							c++;
							break;
						}
						else if(x>=images.get(i).x && x<(images.get(i).x+36) && y>=images.get(i).y && y<(images.get(i).y+images.get(i).height) && selected[i]){
							selected[i]=false;	
							c++;
							break;
						}
						else if(x>=images.get(i).getx() && x<images.get(i+1).getx() && y>=images.get(i).gety() && y<(images.get(i).getheight()+images.get(i).gety()) && !selected[i]){
							selected[i]=true;
							c++;
							break;
						}
					}
					else{
						if(x>=images.get(i).getx() && x<(images.get(i).getx()+images.get(i).getwidth()) && y>=(images.get(i).gety()+images.get(i).getheight()-10) && y<(images.get(i).gety()+images.get(i).getheight()) && !selected[i]){
							selected[i]=true;	
							c++;
							break;
						}
						else if(x>=images.get(i).getx() && x<(images.get(i).getx()+36) && y>=(images.get(i).gety()) && y<(images.get(i).gety()+images.get(i).getheight()) && !selected[i]){
							selected[i]=true;	
							c++;
							break;
						}
						else if(x>=images.get(i).getx() && x<images.get(i+1).getx() && y>=images.get(i).gety() && y<(images.get(i).gety()+images.get(i).getheight()) && selected[i]){
							selected[i]=false;
							c++;
							break;
						}
					}
				}
						
				if(c==0){     		// For the last card
					if(x>=images.get(i).getx() && x<(images.get(i).getwidth()+images.get(i).getx()) && y>=images.get(i).gety() && y<(images.get(i).getheight()+images.get(i).gety())){
						if(selected[i])
							selected[i]=false;
						else
							selected[i]=true;
					}
				}
				repaint();
			}
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}
		
	}

	/** 
	 * {@inheritDoc}
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}

	/** 
	 * {@inheritDoc}
	 */
	public int[] getSelected() {
		ArrayList<Integer> temp=new ArrayList<Integer>();
		for(int i=0;i<selected.length;i++){
			if(selected[i])
				temp.add(i);
		}
		int result[]=null;
		if(temp.size()==0)
			return result;
		else{
			result=new int[temp.size()];
			for(int i=0;i<temp.size();i++){
				result[i]=temp.get(i);
			}
			return result;
		}
	}
	
	/**
	 * Sets the Connection menu item back to enabled from the disabled state
	 */
	public void setConnButton(){
		conn.setEnabled(true);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	public void resetSelected() {
		BigTwoClient bigTwo=(BigTwoClient) game;
		this.selected=new boolean[bigTwo.getPlayerList().get(bigTwo.getPlayerID()).getNumOfCards()];
		repaint();
	}

	/** 
	 * {@inheritDoc}
	 */
	public void repaint() {
		bigTwoPanel.repaint();
	}

	/** 
	 * {@inheritDoc}
	 */
	public void printMsg(String msg) {
		msgArea.append(msg+newline);
	}

	/** 
	 * {@inheritDoc}
	 */
	public void clearMsgArea() {
		msgArea.setText("");
	}
	
	/**
	 * Prints the message sent by a player in the chat text area 
	 * @param msg a String value pointing to the object containing the message sent by the player
	 */
	public void printChat(String msg){
		chat.append(msg+newline);
	}
	
	/**
	 * Clears the chat text area 
	 */
	public void clearChat(){
		chat.setText("");
	}
	
	/** 
	 * {@inheritDoc}
	 */
	public void reset() {
		BigTwoClient bigTwo=(BigTwoClient) game;
		clearMsgArea();
		printMsg("All players are ready. Game starts.");
		printMsg(bigTwo.getPlayerList().get(bigTwo.getCurrentIdx()).getName()+"'s chance:");
		if(bigTwo.getCurrentIdx()==bigTwo.getPlayerID())
			enable();
		else
			disable();
		resetSelected();
	}

	/** 
	 * {@inheritDoc}
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		active=true;
	}

	/** 
	 * {@inheritDoc}
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		active=false;
	}
	
	/**
	 * MsgListener is an inner class that implements the ActionListener interface and is used to handle the text written in the message text field by any user when the user presses enter
	 * @author vanshajchadha
	 */
	class MsgListener implements ActionListener{

		/**
		 * An Event-handler which gets the text written by the user and sends it back to the server
		 */
		public void actionPerformed(ActionEvent e) {
			BigTwoClient bigTwoClient=(BigTwoClient) game;
			String m=msgspace.getText();
			bigTwoClient.sendMessage(new CardGameMessage(CardGameMessage.MSG,-1,m));
			msgspace.setText("");
		}
		
	}
	
	/**
	 * PlayButtonListener is an inner class that implements the ActionListener interface and is used to handle the button-click events when the Play button is clicked in the GUI by performing certain operations and invoking certain methods
	 * @author vanshajchadha
	 */
	class PlayButtonListener implements ActionListener{

		/**
		 * An Event-handler which invokes the makeMove method of the BigTwo class in order to check if the move is valid or not and accordingly perform operations like displaying messages in the text area and updating a player's list of cards along with repainting the GUI
		 */
		public void actionPerformed(ActionEvent e) {
			BigTwoClient bigTwoClient=(BigTwoClient)game;
			int cards[]=getSelected();
			if(cards!=null)
				bigTwoClient.makeMove(-1,cards);
			resetSelected();
		}
		
	}
	
	/**
	 * PassButtonListener is an inner class that implements the ActionListener interface and is used to handle the button-click events when the Pass button is clicked in the GUI by performing certain operations and invoking certain methods
	 * @author vanshajchadha
	 */
	class PassButtonListener implements ActionListener{

		/**
		 * An Event-handler which invokes the makeMove method of the BigTwo class in order to check if the move is valid or not and accordingly perform operations like displaying messages in the text area and updating a player's list of cards along with repainting the GUI
		 */
		public void actionPerformed(ActionEvent e) {
			//BigTwoClient bigTwoClient=(BigTwoClient)game;
			game.makeMove(-1,null);
			resetSelected();
		}	
	}
	
	/**
	 * ConnectMenuItemListener is an inner class that implements the ActionListener interface and is used to handle the menu-item-click events when the Connect menu item is selected in the GUI
	 * @author vanshajchadha
	 */
	class ConnectMenuItemListener implements ActionListener{

		/**
		 * An Event-handler which is used to reconnect the users back to the game in case the server shuts down unexpectedly by invoking makeConnection method
		 */
		public void actionPerformed(ActionEvent e) {
			BigTwoClient bigTwoClient=(BigTwoClient) game;
			bigTwoClient.makeConnection();
		}
		
	}
	
	/**
	 * QuitMenuItemListener is an inner class that implements the ActionListener interface and is used to handle the menu-item-click events when the Quit menu item is selected in the GUI by terminating the application
	 * @author vanshajchadha
	 */
	class QuitMenuItemListener implements ActionListener{

		/**
		 * An Event-handler which simply terminates the application by exiting it
		 */
		public void actionPerformed(ActionEvent e) {
			BigTwoClient bigTwoClient=(BigTwoClient) game;
			bigTwoClient.quit();
			System.exit(0);
		}
		
	}
	
}
