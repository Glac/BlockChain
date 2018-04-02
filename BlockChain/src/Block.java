import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Arrays;

public class Block {
	

	private int previousHash;
	private String[] data;
	private int blockHash;
	private Timestamp timestamp;
	
	public Block(int previousHash, String[] data) {
		this.previousHash = previousHash;
		this.data = data;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		String nonce = nonceGenerator();
		
		Object[] contents = {Arrays.hashCode(data),timestamp,nonce, previousHash};
		this.blockHash = Arrays.hashCode(contents);
	}

	public static  String nonceGenerator(){ 
	    SecureRandom secureRandom = new SecureRandom();
	    StringBuilder stringBuilder = new StringBuilder();
	    for (int i = 0; i < 150; i++) {
	        stringBuilder.append(secureRandom.nextInt(50));
	    }
	    String randomNumber = stringBuilder.toString();
	    return randomNumber;		   	   
	}
	
	/*
	private static String encodeHex(byte[] digest) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < digest.length; i++) {
	        sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	}
	
	public static String digest(String alg, String input) {
	    try {
	        MessageDigest md = MessageDigest.getInstance(alg);
	        byte[] buffer = input.getBytes("UTF-8");
	        md.update(buffer);
	        byte[] digest = md.digest();
	        return encodeHex(digest);
	    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return e.getMessage();
	    }
	}
	*/
	public int getPreviousHash() {
		return previousHash;
	}


	public String[] getData() {
		return data;
	}


	public int getBlockHash() {
		return blockHash;
	}

	public void setPreviousHash(int previousHash) {
		this.previousHash = previousHash;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	public void setBlockHash(int blockHash) {
		this.blockHash = blockHash;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
	
	
}
