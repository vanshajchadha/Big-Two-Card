import java.util.ArrayList;

/**
 * Quad class is a subclass of the Hand class and is used to model a Quad hand and check for the validity of the hand to be a Quad using the inherited as well as the overridden methods
 * @author vanshajchadha
 */
public class Quad extends Hand{
	
	private static final long serialVersionUID = -5519314319880504295L;
	/**
	 * an ArrayList to store the 4 out 5 cards which have the same rank as they constitute a Quad hand
	 */
	ArrayList<Card> quad=new ArrayList<Card>();
	
	/**
	 * A constructor for initializing the Quad hand with a game player having a set of cards which he wishes to play in the Quad hand but first it initializes the instance variables of the Hand superclass
	 * @param player a CardGamePlayer reference pointing to the object containing the name of the player along with the list of cards held by this player
	 * @param cards a CardList reference pointing to the object containing the list of cards that this player wishes to play as their hand
	 */
	Quad(CardGamePlayer player, CardList cards){
		super(player,cards);
	}

	/**
	 * Retrieves the top card of this Quad hand
	 * @return a Card reference pointing to the object containing the rank and suit value of the top card in this hand
	 */
	public Card getTopCard(){
		Card top=quad.get(0);
		for(int i=1;i<quad.size();i++){
			if(quad.get(i).getSuit()>top.getSuit())
				top=quad.get(i);
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
		sort();
		for(int i=0;i<size();i++){
			quad.add(getCard(i));
		}
		if(quad.get(0).getRank()!=quad.get(1).getRank())
			quad.remove(0);
		else if(quad.get(quad.size()-1).getRank()!=quad.get(quad.size()-2).getRank())
			quad.remove(quad.size()-1);
		if(quad.size()==4){
			for(int i=0;i<quad.size()-1;i++){
				if(quad.get(i).getRank()==quad.get(i+1).getRank())
					count++;
			}
			if(count==3)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	/**
	 * Returns the String value for the type of this hand. It is overridden as this behavior of the Hand class is common for this subclass
	 * @return a String value indicating the type of this hand 
	 */
	@Override
	String getType() {
		return "Quad";
	}

}
