import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {
	
	public static String encodeToBase58(String msg) {
		
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
			RIPEMD_160 ripe = new RIPEMD_160();
			String ripemd160Msg = ripe.getHash(hash.toString());
			String encodedMsg = Base58.encode(ripemd160Msg.getBytes());
			return encodedMsg;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		
	}

}
