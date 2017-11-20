import java.io.Serializable;

/**
 * Created by cj on 2017-11-20.
 */
public class User implements Serializable {

    private PublicKey publicKey;
    private String name;

    public User(PublicKey publicKey, String name) {
        this.publicKey = publicKey;
        this.name = name;
    }


    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getName() {
        return name;
    }
}
