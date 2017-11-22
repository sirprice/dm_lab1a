import java.math.BigInteger;
import java.util.Random;

/**
 * Created by cj on 12/11/15.
 */
public class RSA {

    //number of bits specifying how many different characters we are able to encrypt
    private int numberOfBits = 0;
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger d;
    private BigInteger e;


    public RSA(int numberOfBits) {
        this.numberOfBits = numberOfBits;
    }

    public boolean generateKey() {

        boolean foundAKey = false;


        try {
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");


            Random rand = new Random();
            rand.setSeed(System.currentTimeMillis());

            /**
             Välj två primtal p och q och beräkna n = p * q
             */

            p = new BigInteger(numberOfBits / 2, 100, rand);
            System.out.println("p next probablePrime: " + p);

            q = new BigInteger(numberOfBits / 2, 100, rand);
            System.out.println("q next probablePrime: " + q);

            n = (p.multiply(q));

            /**
             Välj e så att e och phi = (p-1)*(q-1) är relativt prima
             */

            p = p.subtract(BigInteger.ONE);
            q = q.subtract(BigInteger.ONE);

            phi = p.multiply(q);

            while (!foundAKey) {

                e = new BigInteger(numberOfBits / 2, 100, rand); // bör vara mindre än phi

                if (e.gcd(phi).equals(BigInteger.ONE)) {
                    System.out.println("E och phi är relativt prima");
                    foundAKey = true;

                } else {
                    System.out.println("E och phi är inte relativt prima");
                }
            }


            /**
             d väljs nu som multiplicativa inversen till e modulus phi
             dvs. att d * e kongurent med 1 mod (phi)
             */
            d = e.modInverse(phi);


            System.out.println("Key generated");
            return true;


        } catch (ArithmeticException ae) {
            ae.printStackTrace();
            System.out.println("could not find good starting values for RSA ");

        }


        return false;
    }


    static public String encrypt(String message, PublicKey publicKey) {
        BigInteger msg = new BigInteger(message.getBytes());

        String cMsg = msg.modPow(publicKey.getE(), publicKey.getN()).toString(); // C = encrypt(T) = T^e mod(n)

        return cMsg;
    }

    public String decrypt(String message) {
        BigInteger msg = new BigInteger(message); // för den är redan i byteform

        String dMsg = new String(msg.modPow(d, n).toByteArray()); // T = decrypt(C) = C^d mod(n)

        return dMsg;
    }


    public PublicKey getPublicKey() {
        return new PublicKey(e, n);
    }

}
