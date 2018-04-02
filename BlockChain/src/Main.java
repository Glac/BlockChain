import java.sql.Timestamp;
import java.util.ArrayList;

public class Main {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	
    public static void main(String[] args) {


        String[] genesisData = {"First block data0000"};
        Block genesisBlock = new Block( 0 , genesisData);
        blockchain.add(genesisBlock);

        String[] block2Data = {"Second block data0000"};
        Block block2 = new Block(genesisBlock.getBlockHash(),block2Data);
        blockchain.add(block2);

        
        String[] block3Data = {"Third block data0000"};
        Block block3 = new Block(block2.getBlockHash(),block3Data);      
        blockchain.add(block3);
        
        String[] block4Data = {"Third block data0000"};
        Block block4 = new Block(block3.getBlockHash(),block4Data);      
        blockchain.add(block4);
        
        String[] block5Data = {"Third block data0000"};
        Block block5 = new Block(block4.getBlockHash(),block5Data);      
        blockchain.add(block5);
        
        String[] block6Data = {"Third block data0000"};
        Block block6 = new Block(block5.getBlockHash(),block6Data);      
        blockchain.add(block6);
        
        String[] block7Data = {"Third block data0000"};
        Block block7 = new Block(block6.getBlockHash(),block7Data);      
        blockchain.add(block7);
        
        System.out.println("Hash of genesisBlock");
        System.out.println(genesisBlock.getBlockHash());
        System.out.println("Hash of block2");
        System.out.println(block2.getBlockHash());
        System.out.println("Hash of block3");
        System.out.println(block3.getBlockHash());
        System.out.println("Hash of block3");
        System.out.println(block3.getBlockHash());
        System.out.println("Hash of block4");
        System.out.println(block4.getBlockHash());
        System.out.println("Hash of block5");
        System.out.println(block5.getBlockHash());
        System.out.println("Hash of block6");
        System.out.println(block6.getBlockHash());
        System.out.println("Hash of block7");
        System.out.println(block7.getBlockHash());
        
        long startTime = System.nanoTime();

        System.out.println("The validity of the blockchain now is: " + checkValidity() );
        System.out.println("=========Test the validity by changing the data in blocks");
        System.out.println("previous data value: " + block2.getData()[0]);
        String[] list_t = {"a as"};
        block2.setData(list_t);
        System.out.println("data value now : " + block2.getData()[0]);
        System.out.println("The validity of the blockchain now is: " + checkValidity() );
        
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);

    }
    
    public static boolean checkValidity(){
        for(int i = 0; i<blockchain.size()-1; i++){
        	if(blockchain.get(i).getBlockHash() != blockchain.get(i+1).getPreviousHash())
        		return false;
        }
        return true;
    }
    
}
