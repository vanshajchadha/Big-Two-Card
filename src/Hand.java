
/**
 * Hand class is a subclass of the CardList class, and is used to model a hand of cards to be played by a player in a Big Two Card game by implementing various methods of Hand as well as it's superclass 
 * @author vanshajchadha
 */
public abstract class Hand extends CardList {
	
	private static final long serialVersionUID = 6837533213688485357L;
	/**
	 * a CardGamePlayer object which stores the name and list of cards held by the player
	 */
	private CardGamePlayer player;
	
	/**
	 * A constructor for building a hand with the specified player and list of cards
	 * @param player a CardGamePlayer reference pointing to the object containing the name of the player along with the list of cards held by this player
	 * @param cards a CardList reference pointing to the object containing the list of cards that this player wishes to play as their hand
	 */
	public Hand(CardGamePlayer player, CardList cards){
		this.player=player;
		int length=cards.size();
		for(int i=0;i<length;i++){
			this.addCard(cards.getCard(i));
		}
	}
	
	/**
	 * Retrieves the player of this hand
	 * @return a CardGamePlayer reference pointing to the object containing the name and list of cards held by this player
	 */
	public CardGamePlayer getPlayer(){
		return player;
	}
	
	/**
	 * Retrieves the top card of this hand
	 * @return a Card reference pointing to the object containing the rank and suit value of the top card in this hand
	 */
	public Card getTopCard(){
		this.sort();   			 // Always gets overridden in the subclasses
		return this.getCard(this.size()-1);
	}
	
	/**
	 * Checks if this hand beats a specified hand
	 * @param hand a Hand reference pointing to the object containing the data for the player and list of cards they wish to play as their hand
	 * @return a boolean value indicating whether or not this hand beats the specified hand
	 */
	public boolean beats(Hand hand){
		if(this.size()==hand.size()){
			if(this.getType()=="Single" && hand.getType()=="Single"){
				BigTwoCard previous=(BigTwoCard) hand.getTopCard();
				BigTwoCard current=(BigTwoCard) this.getTopCard();
				if(current.compareTo(previous)==1){
					return true;
				}
					return false;
			}
			else if(this.getType()=="Pair" && hand.getType()=="Pair"){
				BigTwoCard previous=(BigTwoCard) hand.getTopCard();
				BigTwoCard current=(BigTwoCard) this.getTopCard();
				if(current.compareTo(previous)==1){
					return true;
				}
					return false;
			}
			else if(this.getType()=="Triple" && hand.getType()=="Triple"){
				BigTwoCard previous=(BigTwoCard) hand.getTopCard();
				BigTwoCard current=(BigTwoCard) this.getTopCard();
				if(current.compareTo(previous)==1){
					return true;
				}
					return false;
			}
			else if(this.getType()=="Straight" && hand.getType()=="Straight"){
				BigTwoCard previous=(BigTwoCard) hand.getTopCard();
				BigTwoCard current=(BigTwoCard) this.getTopCard();
				if(current.compareTo(previous)==1){
					return true;
				}
					return false;
			}
			else if(this.getType()=="Flush" && hand.getType()=="Flush"){
				BigTwoCard previous=(BigTwoCard) hand.getTopCard();
				BigTwoCard current=(BigTwoCard) this.getTopCard();
				if(current.getSuit()==previous.getSuit()){
					if(current.compareTo(previous)==1)
						return true;
					else
						return false;
				}
				else{
					if(current.getSuit()>previous.getSuit())
						return true;
					else
						return false;
				}
			}
			else if(this.getType()=="FullHouse" && hand.getType()=="FullHouse"){
				BigTwoCard previous=(BigTwoCard) hand.getTopCard();
				BigTwoCard current=(BigTwoCard) this.getTopCard();
				if(current.compareTo(previous)==1){
					return true;
				}
					return false;
			}
			else if(this.getType()=="Quad" && hand.getType()=="Quad"){
				BigTwoCard previous=(BigTwoCard) hand.getTopCard();
				BigTwoCard current=(BigTwoCard) this.getTopCard();
				if(current.compareTo(previous)==1){
					return true;
				}
					return false;
			}
			else if(this.getType()=="StraightFlush" && hand.getType()=="StraightFlush"){
				BigTwoCard previous=(BigTwoCard) hand.getTopCard();
				BigTwoCard current=(BigTwoCard) this.getTopCard();
				if(current.compareTo(previous)==1){
					return true;
				}
					return false;
			}
			else if(this.getType()=="Straight" && hand.getType()=="Flush")
				return false;
			
			else if(this.getType()=="Straight" && hand.getType()=="FullHouse")
				return false;
			
			else if(this.getType()=="Straight" && hand.getType()=="Quad")
				return false;
			
			else if(this.getType()=="Straight" && hand.getType()=="StraightFlush")
				return false;
			
			else if(this.getType()=="Flush" && hand.getType()=="Straight")
				return true;
			
			else if(this.getType()=="Flush" && hand.getType()=="FullHouse")
				return false;
			
			else if(this.getType()=="Flush" && hand.getType()=="Quad")
				return false;
			
			else if(this.getType()=="Flush" && hand.getType()=="StraightFlush")
				return false;
			
			else if(this.getType()=="FullHouse" && hand.getType()=="Straight")
				return true;
			
			else if(this.getType()=="FullHouse" && hand.getType()=="Flush")
				return true;
			
			else if(this.getType()=="FullHouse" && hand.getType()=="Quad")
				return false;
			else if(this.getType()=="FullHouse" && hand.getType()=="StraightFlush")
				return false;
			
			else if(this.getType()=="Quad" && hand.getType()=="Straight")
				return true;
			
			else if(this.getType()=="Quad" && hand.getType()=="Flush")
				return true;
			
			else if(this.getType()=="Quad" && hand.getType()=="FullHouse")
				return true;
			
			else if(this.getType()=="Quad" && hand.getType()=="StraightFlush")
				return false;
			
			else if(this.getType()=="StraightFlush" && hand.getType()=="Straight")
				return true;
			
			else if(this.getType()=="StraightFlush" && hand.getType()=="Flush")
				return true;
			
			else if(this.getType()=="StraightFlush" && hand.getType()=="FullHouse")
				return true;
			
			else if(this.getType()=="StraightFlush" && hand.getType()=="Quad")
				return true;
			
		}
			return false;
	}
	
	/**
	 * an abstract method to check if this hand is valid or not. It is overridden in each of Hand's subclasses as this behavior of the Hand class is common for all the subclasses
	 * @return a boolean value indicating whether or not this hand is valid
	 */
	abstract boolean isValid();
	
	/**
	 * an abstract method to return the String value for the type of this hand. It is overridden in each of Hand's subclasses as this behavior of the Hand class is common for all the subclasses
	 * @return a String value indicating the type of this hand 
	 */
	abstract String getType();
}
