package client.crypto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by cj on 2017-11-20.
 */
public class PublicKey implements Serializable {

    private BigInteger e;
    private BigInteger n;


    public PublicKey(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getN() {
        return n;
    }

    @Override
    public String toString() {
        return "PublicKey{" +
                "e=" + e +
                ", n=" + n +
                '}';
    }
}