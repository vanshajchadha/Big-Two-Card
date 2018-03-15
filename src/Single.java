
/**
 * Single class is a subclass of the Hand class and is used to model a Single hand and check for the validity of the hand to be a Single using the inherited as well as the overridden methods
 * @author vanshajchadha
 */
public class Single extends Hand{

	private static final long serialVersionUID = -3241663778029222333L;

	/**
	 * A constructor for initializing the Single hand with a game player having a set of cards which he wishes to play in the Single hand but first it initializes the instance variables of the Hand superclass
	 * @param player a CardGamePlayer reference pointing to the object containing the name of the player along with the list of cards held by this player
	 * @param cards a CardList reference pointing to the object containing the list of cards that this player wishes to play as their hand
	 */
	Single(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	
	/**
	 * Retrieves the top card of this Single hand
	 * @return a Card reference pointing to the object containing the rank and suit value of the top card in this hand
	 */
	public Card getTopCard(){
		return getCard(0);   
	}
	
	/**
	 * Checks if this hand is actually valid or not. It is overridden as this behavior of the Hand class is common for this subclass
	 * @return a boolean value indicating whether or not this hand is valid
	 */
	@Override
	boolean isValid() {
		return true;
	}

	/**
	 * Returns the String value for the type of this hand. It is overridden as this behavior of the Hand class is common for this subclass
	 * @return a String value indicating the type of this hand 
	 */
	@Override
	String getType() {
		return "Single";
	}
	

}
