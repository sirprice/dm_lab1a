package client;

import client.client.Client;

/**
 * Created by cj on 2017-11-20.
 */
public class Main {

    public static void main(String[] args) {
        boolean willInitiateContact;
        if (args[3].equals("true")) {
            willInitiateContact = true;
        } else {
            willInitiateContact = false;
        }

        Client client = new Client(
                args[0],
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2]),
                willInitiateContact);

        client.run();
    }
}
