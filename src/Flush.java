/**
 * Flush class is a subclass of the Hand class and is used to model a Flush hand and check for the validity of the hand to be a Flush using the inherited as well as the overridden methods
 * @author vanshajchadha
 */
public class Flush extends Hand{

	private static final long serialVersionUID = -8200653372398000985L;

	/**
	 * A constructor for initializing the Flush hand with a game player having a set of cards which he wishes to play in the Flush hand but first it initializes the instance variables of the Hand superclass
	 * @param player a CardGamePlayer reference pointing to the object containing the name of the player along with the list of cards held by this player
	 * @param cards a CardList reference pointing to the object containing the list of cards that this player wishes to play as their hand
	 */
	Flush(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	
	/**
	 * Retrieves the top card of this Flush hand
	 * @return a Card reference pointing to the object containing the rank and suit value of the top card in this hand
	 */
	public Card getTopCard(){
		BigTwoCard top=(BigTwoCard)getCard(0);
		for(int i=1;i<size();i++){
			BigTwoCard card=(BigTwoCard)getCard(i);
			if(card.compareTo(top)==1)
				top=card;
		}
		return top;
	}
	
	/**
	 * Checks if this hand is actually valid or not. It is overridden as this behavior of the Hand class is common for this subclasses
	 * @return a boolean value indicating whether or not this hand is valid
	 */
	@Override
	boolean isValid() {
		int count=0;
		for(int i=0;i<size()-1;i++){
			if(getCard(i).getSuit()==getCard(i+1).getSuit())
				count++;
		}
		if(count==4)
			return true;
		else
			return false;
	}

	/**
	 * Returns the String value for the type of this hand. It is overridden as this behavior of the Hand class is common for this subclass
	 * @return a String value indicating the type of this hand 
	 */
	@Override
	String getType() {
		return "Flush";
	}

}
