package lib;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * Block Class, the element to compose a Blockchain.
 */
public class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    private String hash;

    private String previousHash;

    private String data;

    private long timestamp;

    private long nonce;

    public Block() {}

    public Block(String hash, String previousHash, String data,
                 long timestamp) {
        this.hash = hash;
        this.previousHash = previousHash;
        this.data = data;
        this.timestamp = timestamp;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return hash + "#" + previousHash + "#" + data + "#" + timestamp + "#" + nonce;
    }

    public static Block fromString(String s) {
        String[] attributes = s.split("#");
        Block ret = new Block();
        ret.hash = attributes[0];
        ret.previousHash = attributes[1];
        ret.data = attributes[2];
        ret.timestamp = Long.parseLong(attributes[3]);
        ret.nonce = Long.parseLong(attributes[4]);
        return ret;
    }

}
