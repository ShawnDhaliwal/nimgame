
public class StateNim extends State {
	
    public int coinsRemaining = 13;
    public int player1Coins = 0;
    public int player2Coins = 0;
    
    public StateNim() {
    	
        this.coinsRemaining = 13;  
        this.player1Coins = 0;  
        this.player2Coins = 0;  
        player = 1;
    }
    
    public StateNim(StateNim state) {
    	
        this.coinsRemaining = state.coinsRemaining; 
        this.player1Coins = state.player1Coins;
        this.player2Coins = state.player2Coins;
        this.player = state.player;
    }
    
    public String toString() {
        String ret = "";
        ret = ""+coinsRemaining;
    	return ret;
    }
}
