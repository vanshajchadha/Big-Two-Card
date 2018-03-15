
/**
 * BigTwoCard class is a subclass of the Card class, and is used to model a card used in a Big Two card game by allocating the rank and suit value to a Card along with performing multiple operations on it
 * @author vanshajchadha
 */
public class BigTwoCard extends Card {
	
	private static final long serialVersionUID = -5822018182891811204L;

	/**
	 * A constructor for building a BigTwoCard object by initializing it using the value of rank and suits passed which first initializes the value of its superclass's Cards' rank and suits variable using the latter's constructor
	 * @param suit an integer value denoting the suit of the card
	 * @param rank an integer value denoting the rank of the card
	 */
	public BigTwoCard(int suit, int rank){
		super(suit,rank);
	}
	
	/**
	 * Overrides superclass's Cards' method to compare this card with the specified card for order according to the BigTwo game rules unlike other card games
	 * @param card
	 *            the card to be compared
	 * @return a negative integer, zero, or a positive integer as this card is
	 *         less than, equal to, or greater than the specified card in a game of BigTwo
	 */
	public int compareTo(Card card){
		if(this.rank!=card.rank){
			if(this.rank==1)
				return 1;
			else if(card.rank==1)
				return -1;
			else if(this.rank==0)
				return 1;
			else if(card.rank==0)
				return -1;
			else{
				if(this.rank>card.rank)
					return 1;
				else
					return -1;
			}
		}
		else{
			if(this.suit>card.suit)
				return 1;
			else if(card.suit>this.suit)
				return -1;
			else
				return 0;
		}
	}
	
}
