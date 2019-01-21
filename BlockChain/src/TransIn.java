
public class TransIn {
	public String transactionOutputId; //Reference to TransactionOutputs -> transactionId
	public TransOut UTXO; //Contains the Unspent transaction output
	
	public TransIn(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}
}
