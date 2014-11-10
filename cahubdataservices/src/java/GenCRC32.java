import java.util.zip.CRC32;

public class GenCRC32 {
	public static void main(String[] args){
	CRC32 crc = new CRC32();
	byte[] bytes = args[0].getBytes();
	crc.update(bytes);
	System.out.println(Long.toHexString(crc.getValue()));

	}
}
