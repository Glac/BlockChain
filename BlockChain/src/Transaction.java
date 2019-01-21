import java.security.*;
import java.util.ArrayList;

public class Transaction {
	
	public String id; 
	public PublicKey sender;
	public PublicKey reciepient; 
	public float value; 
	public byte[] signature; 
	public ArrayList<TransIn> in = new ArrayList<TransIn>();
	public ArrayList<TransOut> out = new ArrayList<TransOut>();
	
	private static int trans_time = 0;  
	
	public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransIn> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.in = inputs;
	}
	
	
	/**
	 * @exception 	to process a transaction 
	 * @return 		true if the transaction has been processed successfully
	 */
	public boolean processTrans() {
		
		if(verifySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
				
		//Gathers transaction inputs (Making sure they are unspent):
		for(TransIn i : in) {
			i.UTXO = TransCoin.UTXOs.get(i.transactionOutputId);
		}

		//Checks if transaction is valid:
		if(getInputsValue() < TransCoin.minimumTransaction) {
			System.out.println("Transaction Inputs too small: " + getInputsValue());
			System.out.println("Please enter the amount greater than " + TransCoin.minimumTransaction);
			return false;
		}
		
		//Generate transaction outputs:
		float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
		id = calulateHash();
		out.add(new TransOut( this.reciepient, value,id)); //send value to recipient
		out.add(new TransOut( this.sender, leftOver,id)); //send the left over 'change' back to sender		
				
		//Add outputs to Unspent list
		for(TransOut o : out) {
			TransCoin.UTXOs.put(o.id , o);
		}
		
		//Remove transaction inputs from UTXO lists as spent:
		for(TransIn i : in) {
			if(i.UTXO == null) continue; //if Transaction can't be found skip it 
			TransCoin.UTXOs.remove(i.UTXO.id);
		}
		
		return true;
	}
	
	
	/**
	 * @return the total value of this block
	 */
	public float getInputsValue() {
		float total = 0;
		for(TransIn i : in) {
			if(i.UTXO == null) 
				continue;
			total += i.UTXO.value;
		}
		return total;
	}
	
	
	/**
	 * @param privateKey
	 */
	public void generateSignature(PrivateKey privateKey) {
		String data = Utility.getStringFromKey(sender) + Utility.getStringFromKey(reciepient) + Float.toString(value)	;
		signature = Utility.sign(privateKey,data);		
	}
	
	
	public boolean verifySignature() {
		String data = Utility.getStringFromKey(sender) + Utility.getStringFromKey(reciepient) + Float.toString(value)	;
		return Utility.verifyECDSASig(sender, data, signature);
	}
	
	
	/**
	 * @return the total value of the output of the block
	 */
	public float getOutputsValue() {
		float total = 0;
		for(TransOut o : out) {
			total += o.value;
		}
		return total;
	}
	
	
	/**
	 * @return a string that digested the keys and value and times of the transaction 
	 */
	private String calulateHash() {
		// increase the transaction times in order to 
		// avoid the identical transaction
		trans_time++; 
		return Utility.messageDiegest(
				Utility.getStringFromKey(sender) +
				Utility.getStringFromKey(reciepient) +
				Float.toString(value) + trans_time
				);
	}
}
