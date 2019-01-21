import java.util.ArrayList;
import java.util.Date;

public class Block {
	

	public String currHash;
	public String prevHash; 
	public String root_of_merkle;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
	public long timeStamp; //as number of milliseconds since 1/1/1970.
	public int nonce;
	
	//Block Constructor.  
	public Block(String previousHash ) {
		this.prevHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.currHash = getHash(); 
	}
	
	//Calculate new hash based on blocks contents
	public String getHash() {
		String calculatedhash = Utility.messageDiegest( 
				prevHash + Long.toString(timeStamp) + Integer.toString(nonce) + root_of_merkle);
		return calculatedhash;
	}
	
	
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		root_of_merkle = Utility.getRoot(transactions);
		String target = Utility.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!currHash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			currHash = getHash();
		}
		System.out.println("Block Mined!!! : " + currHash);
	}
	
	
	//Add transactions to this block
	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) 
			return false;		
		if((! prevHash.equals("0"))) {
			if((transaction.processTrans() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}

		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}
	
}
