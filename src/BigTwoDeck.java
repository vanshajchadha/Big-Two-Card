
/**
 * BigTwoDeck class is a subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game
 * @author vanshajchadha
 */
public class BigTwoDeck extends Deck {
	
	private static final long serialVersionUID = 8254481884293570270L;

	/**
	 * Initializes a deck of Big Two cards by removing all cards from the deck, creating 52 Big Two cards and adding them to the deck
	 */
	public void initialize(){
			removeAllCards();
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 13; j++) { 
					addCard(new BigTwoCard(i, j));
				}
			}
	}
}
