import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
	
	public HashMap<String,TransOut> UTXOs = new HashMap<String,TransOut>();
	
	public Wallet() {
		generateKeys();
	}
		
	public void generateKeys() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); //256 
	        KeyPair keyPair = keyGen.generateKeyPair();
	        // Set the public and private keys from the keyPair
	        privateKey = keyPair.getPrivate();
	        publicKey = keyPair.getPublic();
	        
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public Transaction sendCoins(PublicKey _recipient,float value ) {
		if(getBalance() < value) {
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return null;
		}
		ArrayList<TransIn> inputs = new ArrayList<TransIn>();
		
		float total = 0;
		for (Map.Entry<String, TransOut> item: UTXOs.entrySet()){
			TransOut UTXO = item.getValue();
			total += UTXO.value;
			inputs.add(new TransIn(UTXO.id));
			if(total > value) break;
		}
		
		Transaction newTransaction = new Transaction(publicKey, _recipient , value, inputs);
		newTransaction.generateSignature(privateKey);
		
		for(TransIn input: inputs){
			UTXOs.remove(input.transactionOutputId);
		}
		
		return newTransaction;
	}
	
	
	public float getBalance() {
		float total = 0;	
        for (Map.Entry<String, TransOut> item: TransCoin.UTXOs.entrySet()){
        	TransOut UTXO = item.getValue();
            if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
            	UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
            	total += UTXO.value ; 
            }
        }  
		return total;
	}
	
}


