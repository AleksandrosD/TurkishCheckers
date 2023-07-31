package application;

public class Player implements Turn{
	
	private boolean isTurn;
	private int team;
	/**
	 * Constructor of the player.
	 * @param team
	 * @param isTurn
	 */
	public Player(int team, boolean isTurn) {
		this.team = team;
		this.isTurn = isTurn;
	}

	@Override
	/**
	 * Changes the player turns, this function comes from the interface.
	 */
	public void changeTurn() {
		
		if(isTurn == true) {
			isTurn = false;
			
		}else {
			isTurn = true;
		}
			
	}
	/**
	 * Gets whos turn is next.
	 * @return
	 */
	public boolean getTurn() {
		return isTurn;
	}
	/**
	 * Gets which that the player is Black or White.
	 * @return
	 */
	public int getTeam() {
		return team;
	}
}
