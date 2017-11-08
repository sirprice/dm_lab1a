import java.math.BigInteger;
import java.util.Random;
import java.util.function.BiFunction;

/**
 * Created by cj on 12/11/15.
 */
public class RsaKeyGenerator {
	private int numberOfBitsThatWillCreateOurRSAKey = 0;
	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private BigInteger phi;
	private BigInteger d;
	private BigInteger e;


	public RsaKeyGenerator(int numberOfBitsThatWillCreateOurRSAKey){
		this.numberOfBitsThatWillCreateOurRSAKey = numberOfBitsThatWillCreateOurRSAKey;
	}

	public BigInteger generateKey(){

		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());

		p = p.valueOf(rand.nextLong());
		q = q.valueOf(rand.nextLong());

		p = p.nextProbablePrime();
		q = q.nextProbablePrime();

		n = (p.multiply(q));
		p = p.subtract(BigInteger.ONE);
		q = q.subtract(BigInteger.ONE);

		phi = p.multiply(q);

		e = e.valueOf(rand.nextLong()); // borde vara mindre än phi

		if ( e.gcd(phi).equals(BigInteger.ONE )){
			System.out.println("E är Waleed");
		}else
			System.out.println("E är inte Waleed");

		d = e.modInverse(phi);


		return null;
	}

	public BigInteger getE() {
		return e;
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getD() {
		return d;
	}
}
