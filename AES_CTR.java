package Project;

public class AES_CTR {
    public String cipher = "";

    public AES_CTR(String Plaintext, String NonceIV, String Key) {
        AES_ECB ecb = new AES_ECB(NonceIV, Key);
        String KeyStream= ecb.result;

        char pt[] = Plaintext.toCharArray();
        char keyCTR[] = KeyStream.toCharArray();

        for (int i = 0; i < pt.length; i++) {
            // System.out.println(pt[i]+" 1" + keyCTR[i]+" 2");
            cipher = cipher + ((char) ((int) pt[i] ^ (int) keyCTR[i]));
        }
    }
}