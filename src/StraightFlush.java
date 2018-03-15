import java.util.ArrayList;

/**
 * StraightFlush class is a subclass of the Hand class and is used to model a StraightFlush hand and check for the validity of the hand to be a StraightFlush using the inherited as well as the overridden methods
 * @author vanshajchadha
 */
public class StraightFlush extends Hand{
	
	private static final long serialVersionUID = -8843143144662718345L;
	/**
	 *  an ArrayList to store all the 5 cards in sorted order in order to compute the top card
	 */
	ArrayList<BigTwoCard> ace=new ArrayList<BigTwoCard>();

	/**
	 * A constructor for initializing the StraightFlush hand with a game player having a set of cards which he wishes to play in the StraightFlush hand but first it initializes the instance variables of the Hand superclass
	 * @param player a CardGamePlayer reference pointing to the object containing the name of the player along with the list of cards held by this player
	 * @param cards a CardList reference pointing to the object containing the list of cards that this player wishes to play as their hand
	 */
	StraightFlush(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	
	/**
	 * Retrieves the top card of this StraightFlush hand
	 * @return a Card reference pointing to the object containing the rank and suit value of the top card in this hand
	 */
	public Card getTopCard(){
		BigTwoCard top=ace.get(0);
		for(int i=1;i<ace.size();i++){
			BigTwoCard card=ace.get(i);
			if(card.getRank()==13){
				ace.set(i,new BigTwoCard(card.getSuit(),0));
				card=ace.get(i);
			}
			if(card.getRank()==14){
				ace.set(i,new BigTwoCard(card.getSuit(),1));
				card=ace.get(i);
			}
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
		int c=0,d=0;
		for(int i=0;i<size();i++){
			if(getCard(i).getRank()==2){
				for(int j=0;j<size();j++){
					if(getCard(j).getRank()==0 || getCard(j).getRank()==1)
						c++;
				}
			}	
		}
		if(c!=0){
			return false;
		}
		else{
			if(contains(new BigTwoCard(0,0))|| contains(new BigTwoCard(1,0)) || contains(new BigTwoCard(2,0)) || contains(new BigTwoCard(3,0))){
				d++;
				for(int i=0;i<size();i++){
					ace.add((BigTwoCard)getCard(i));
				}
				if(contains(new BigTwoCard(0,1))|| contains(new BigTwoCard(1,1)) || contains(new BigTwoCard(2,1)) || contains(new BigTwoCard(3,1)))
					d++;
			}
			else{
				for(int i=0;i<size();i++)
					ace.add((BigTwoCard)getCard(i));
			}
			if(d==2){
				for(int i=0;i<ace.size();i++){
					if(getCard(i).getRank()==0)
						ace.set(i,new BigTwoCard(getCard(i).getSuit(),13));
					else if(getCard(i).getRank()==1)
						ace.set(i,new BigTwoCard(getCard(i).getSuit(),14));
				}
			}
			else if(d==1){
				for(int i=0;i<ace.size();i++){
					if(getCard(i).getRank()==0)
						ace.set(i,new BigTwoCard(getCard(i).getSuit(),13));
				}
			}
			ace.sort(null);
			c=0;
			for(int j=0;j<ace.size()-1;j++){
				if((ace.get(j).getRank()-ace.get(j+1).getRank()==-1) && (ace.get(j).getSuit()==ace.get(j+1).getSuit()))
					c++;
			}
			if(c==4)
				return true;
			else
				return false;
		}
	}

	/**
	 * Returns the String value for the type of this hand. It is overridden as this behavior of the Hand class is common for this subclass
	 * @return a String value indicating the type of this hand 
	 */
	@Override
	String getType() {
		return "StraightFlush";
	}

}
