import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class GameNim extends Game {

	int WinningScore = 10;
    int LosingScore = -10;
    int NeutralScore = 0;     

    public GameNim() {
    	currentState = new StateNim();
    }
    
    public boolean isWinState(State state)
    {
        StateNim tstate = (StateNim) state;
        //player who did the last move
        int coinsRemaining = tstate.coinsRemaining;

        if(coinsRemaining == 1){
            return true;
        }

        return false;
    }

    public boolean isStuckState(State state) {

        
        return false;
    }
	
	public Set<State> getSuccessors(State state)
    {
		if(isWinState(state))
			return null;
		
		Set<State> successors = new HashSet<State>();
        StateNim tstate = (StateNim) state;
        
        StateNim successor_state;
        
        if (tstate.coinsRemaining > 1) {
            successor_state = new StateNim(tstate);

            if(tstate.coinsRemaining == 2){
                if(state.player == 1){
                    successor_state.player1Coins ++;
                } else {
                    successor_state.player2Coins ++;
                }
                successor_state.coinsRemaining = 1;
                successor_state.player = (state.player==0 ? 1 : 0);              
                successors.add(successor_state);
            }

            else if(tstate.coinsRemaining == 3){
                if(state.player == 1){
                    successor_state.player1Coins += 2;
                } else {
                    successor_state.player2Coins += 2;
                }
                successor_state.coinsRemaining = 1;
                successor_state.player = (state.player==0 ? 1 : 0);              
                successors.add(successor_state);
            }

            else if(tstate.coinsRemaining == 4){
                if(state.player == 1){
                    successor_state.player1Coins += 3;
                } else {
                    successor_state.player2Coins += 3;
                }
                successor_state.coinsRemaining = 1;
                successor_state.player = (state.player==0 ? 1 : 0);              
                successors.add(successor_state);
            }
            
            else if(tstate.coinsRemaining > 4){
                for(int i = 1; i<4; i++){
                    successor_state = new StateNim(tstate);
                    if(state.player == 1){
                        successor_state.player1Coins += i;
                    } else {
                        successor_state.player2Coins += i;
                    }
                    successor_state.coinsRemaining -=i;
                    successor_state.player = (state.player==0 ? 1 : 0);   
                    successors.add(successor_state);
                }
            }

        }
    
        return successors;
    }	
    
    public double eval(State state) 
    {   
    	if(isWinState(state)) {
    		//player who made last move
    		int previous_player = (state.player==0 ? 1 : 0);
    	
	    	if (previous_player==0) //computer wins
	            return WinningScore;
	    	else //human wins
	            return LosingScore;
    	}

        return NeutralScore;
    }
    
    
    public static void main(String[] args) throws Exception {
        
        Game game = new GameNim(); 
        Search search = new Search(game);
        int depth = 8;
        
        //needed to get human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Coins Remaining: 13");
        while (true) {
        	
        	StateNim 	nextState = null;
        	
            switch ( game.currentState.player ) {
              case 1: //Human
                  
            	  //get human's move
                  System.out.print("How how many coins you are picking up? ");
                  int coinsPickingUp = Integer.parseInt( in.readLine() );
                  while(coinsPickingUp > 3 || coinsPickingUp < 1){
                        System.out.print("Please pick up either 1 2 or 3 coins \n");
                        System.out.print("How how many coins you are picking up? ");
                        coinsPickingUp = Integer.parseInt( in.readLine() ); 
                    }
                  nextState = new StateNim((StateNim)game.currentState);
                  while(coinsPickingUp > nextState.coinsRemaining){
                        System.out.print("Invalid move");
                        System.out.print("How how many coins you are picking up? ");    
                        coinsPickingUp = Integer.parseInt( in.readLine() );              
                  }
                  nextState.player = 1;
                  nextState.player1Coins += coinsPickingUp;
                  nextState.coinsRemaining -= coinsPickingUp;
                  System.out.println("Human: \n" + nextState.player1Coins);
                  break;
                  
              case 0: //Computer
            	  
            	  nextState = (StateNim)search.bestSuccessorState(depth);
            	  nextState.player = 0;
            	  System.out.println("Computer: \n" + nextState.player2Coins);
                  break;
            }


            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);
            System.out.println("Coins Remaining: "+nextState.coinsRemaining);
            //Who wins?
            if ( game.isWinState(game.currentState) ) {
            
            	if (game.currentState.player == 1) //i.e. last move was by the computer
            		System.out.println("Computer wins!");
            	else
            		System.out.println("You win!");
            	
            	break;
            }
            

            }
        }
    
}