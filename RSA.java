package Project;

import java.math.*;
import java.security.SecureRandom;

public class RSA {
    public static BigInteger n;
    public static BigInteger e;
    public static BigInteger d;
    public static BigInteger totient;

    public String private_key = "(" + String.valueOf(n) + "," + String.valueOf(e) + ")";
    public String public_key = "(" + String.valueOf(n) + "," + String.valueOf(d) + ")";

    public static void exponentGen() {
        BigInteger p = generatePrime(2048);
        BigInteger q = generatePrime(2048);

        while (p.equals(q)) {
            q = generatePrime(2048);
        }

        n = p.multiply(q);
        totient = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        e = expogen();

        d = e.modInverse(totient);
    }

    public static BigInteger generatePrime(int bit) {
        SecureRandom random = new SecureRandom();
        BigInteger prime = BigInteger.probablePrime(bit, random);
        return prime;
    }

    public static BigInteger expogen() {
        SecureRandom s = new SecureRandom();
        BigInteger e = BigInteger.ZERO;
        while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(totient) >= 0
                || !e.gcd(totient).equals(BigInteger.ONE)) {
            e = new BigInteger(2048, s);
        }
        return e;
    }

    public BigInteger encrypt(BigInteger data, String pubk) {
        pubk = pubk.substring(1, pubk.length() - 1);
        String[] pub = pubk.split(",");
        BigInteger pubN = new BigInteger(pub[0]);
        BigInteger pubE = new BigInteger(pub[1]);
        return data.modPow(pubE, pubN);
    }

    public BigInteger decrypt(BigInteger data, String prk) {
        prk = prk.substring(1, prk.length() - 1);
        String[] privateKey = prk.split(",");
        BigInteger priN = new BigInteger(privateKey[0]);
        BigInteger prid = new BigInteger(privateKey[1]);
        return data.modPow(prid, priN);
    }
}