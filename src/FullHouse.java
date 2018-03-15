import java.util.ArrayList;

/**
 * FullHouse class is a subclass of the Hand class and is used to model a FullHouse hand and check for the validity of the hand to be a FullHouse using the inherited as well as the overridden methods
 * @author vanshajchadha
 */
public class FullHouse extends Hand{
	
	private static final long serialVersionUID = 5168059214175164419L;
	/**
	 * an ArrayList to store the 3 out 5 cards having the same rank which constitute a FullHouse hand
	 */
	ArrayList<Card> trip=new ArrayList<Card>();
	/**
	 * an ArrayList to store the 2 out 5 cards having the same rank which constitute a FullHouse hand
	 */
	ArrayList<Card> doub=new ArrayList<Card>();
	
	/**
	 * A constructor for initializing the FullHouse hand with a game player having a set of cards which he wishes to play in the FullHouse hand but first it initializes the instance variables of the Hand superclass
	 * @param player a CardGamePlayer reference pointing to the object containing the name of the player along with the list of cards held by this player
	 * @param cards a CardList reference pointing to the object containing the list of cards that this player wishes to play as their hand
	 */
	FullHouse(CardGamePlayer player, CardList cards){
		super(player,cards);
	}

	/**
	 * Retrieves the top card of this FullHouse hand
	 * @return a Card reference pointing to the object containing the rank and suit value of the top card in this hand
	 */
	public Card getTopCard(){
		Card top=trip.get(0);
		for(int i=1;i<trip.size();i++){
			if(trip.get(i).getSuit()>top.getSuit())
				top=trip.get(i);
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
			trip.add(getCard(i));
		}
		if(trip.get(0).getRank()==trip.get(1).getRank() && trip.get(1).getRank()!=trip.get(2).getRank()){
			doub.add(trip.get(0));
			doub.add(trip.get(1));
			trip.remove(0);
			trip.remove(0);
		}
		else if(trip.get(trip.size()-1).getRank()==trip.get(trip.size()-2).getRank() && trip.get(trip.size()-2).getRank()!=trip.get(trip.size()-3).getRank()){
			doub.add(trip.get(trip.size()-1));
			doub.add(trip.get(trip.size()-2));
			trip.remove(trip.size()-1);
			trip.remove(trip.size()-1);
		}
		if(trip.size()==3 && doub.size()==2){
			for(int i=0;i<trip.size()-1;i++){
				if(trip.get(i).getRank()==trip.get(i+1).getRank())
					count++;
			}
			if(doub.get(0).getRank()==doub.get(1).getRank())
				count++;
			
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
		return "FullHouse";
	}


}
