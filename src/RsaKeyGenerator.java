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


    public RsaKeyGenerator(int numberOfBitsThatWillCreateOurRSAKey) {
        this.numberOfBitsThatWillCreateOurRSAKey = numberOfBitsThatWillCreateOurRSAKey;
    }

    public boolean generateKey() {
        boolean foundAKey = false;

        while (!foundAKey) {


            try {
                System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");


                Random rand = new Random();
                rand.setSeed(System.currentTimeMillis());

                p = p.valueOf(rand.nextLong());
                System.out.println("orginal p: " + p);

                q = q.valueOf(rand.nextLong());
                System.out.println("orginal q: " + q);

                p = p.nextProbablePrime();
                System.out.println("p next probablePrime: " + p);

                q = q.nextProbablePrime();
                System.out.println("q next probablePrime: " + q);

                n = (p.multiply(q));
                p = p.subtract(BigInteger.ONE);
                q = q.subtract(BigInteger.ONE);

                phi = p.multiply(q);

                e = e.valueOf(rand.nextLong()); // borde vara mindre än phi

                if (e.gcd(phi).equals(BigInteger.ONE)) {
                    System.out.println("E är prima");
                } else{
                    System.out.println("E är inte inte prima");
                }

                d = e.modInverse(phi);

                foundAKey = true;

                System.out.println("Key generated");
                return true;


            } catch (ArithmeticException ae) {
                System.out.println("could not find good starting values for RSA ");

            }
        }

        return false;
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
