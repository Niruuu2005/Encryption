package Project;

import java.util.Arrays;

public class AES_ECB {
    public String result;
    public AES_ECB(String plaintext, String key) {
        char[] data_ = plaintext.toCharArray();
        byte[] sbox = Sbox(key);
        byte[] data = new byte[plaintext.length() / 2];
        int c = 0;
        for (int ch = 0; ch < data_.length; ch++) {
            if (ch < 16) {
                data[c] = (byte) (Integer.parseInt(("" + data_[ch] + data_[++ch]), 16));
                c++;
            }
        }

        int rounds = 10;

        String[] kround = new String[rounds];

        kround[0] = key;
        for (int a = 1; a < rounds; a++) {
            int len = a % key.length();
            kround[a] = key.substring(len) + key.substring(0, (len));
        }
        byte[] xor = new byte[16];
        for (String each : kround) {
            char[] k = each.toCharArray();
            byte[] b = new byte[k.length];
            for (int a = 0; a < k.length; a++) {
                b[a] = (byte) k[a];
            }

            for (int s = 0; s < b.length; s++) {
                xor[s] = (byte) (b[s] ^ data[s]);
            }
            data = mixedCol(shiftRows(subBytes(xor, sbox)));
        }
        result = new String(data);
    }

    public static byte[] subBytes(byte[] input, byte[] sbox) {
        byte[] output = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            int row = (input[i] >> 4) & 0x0F;
            int column = input[i] & 0x0F;
            output[i] = sbox[row * 16 + column];
        }
        return output;
    }

    public static byte[] shiftRows(byte[] input) {
        byte[] demo = new byte[input.length];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < (4); j++) {
                demo[i * 4 + (i + j) % 4] = input[i * 4 + j];
            }
        }
        return demo;
    }

    public static byte[] mixedCol(byte[] input) {
        byte[] demo = new byte[input.length];

        for (int col = 0; col < 4; col++) {
            int colIndex = col * 4;
            byte[] column = new byte[4];
            System.arraycopy(input, colIndex, column, 0, 4);

            byte[] mixedColumn = new byte[4];
            mixedColumn[0]
                    = (byte) (((int) column[0] * 2)
                    ^ ((int) column[3] * 1)
                    ^ ((int) column[2] * 1)
                    ^ ((int) column[1] * 3));
            mixedColumn[1]
                    = (byte) (((int) column[1] * 2)
                    ^ ((int) column[0] * 1)
                    ^ ((int) column[3] * 1)
                    ^ ((int) column[2] * 3));
            mixedColumn[2]
                    = (byte) (((int) column[2] * 2)
                    ^ ((int) column[1] * 1)
                    ^ ((int) column[0] * 1)
                    ^ ((int) column[3] * 3));
            mixedColumn[3]
                    = (byte) (((int) column[3] * 2)
                    ^ ((int) column[2] * 1)
                    ^ ((int) column[1] * 1)
                    ^ ((int) column[0] * 3));
            System.arraycopy(mixedColumn, 0, demo, colIndex, 4);
        }

        return demo;
    }

    
    static String key_ = "";

    public byte[] Sbox(String key) {
        byte[] sbox = new byte[256];
        char[] key_ = key.toCharArray();
        int count = 0;
        for (char ele : key_) {
            sbox[count] = (byte) ele > 0 ? (byte) ele : (byte) ((int) ele);
            count++;
        }

        for (int x = 1; x < key_.length; x++) {
            sbox[x * 16] = (byte) ((int) sbox[x] + 1);
            for (int c = 0; c < 16; c++) {
                if (sbox[c] == sbox[x * 16]) {
                    sbox[x * 16] = (byte) ((int) sbox[x * 16] + 1);
                    c = 0;
                }
            }

            for (int y = 1; y < 16; y++) {
                int ele = ((int) sbox[y] * (int) sbox[x * 16]) % 255;
                sbox[(x * 16) + y] = (byte) (ele < 0 ? 0 - ele : ele);
                for (int i = 0; i < (x * 16) + y; i++) {
                    if (sbox[i] == sbox[(x * 16) + y]) {
                        sbox[(x * 16) + y] += 1;
                        i = 0;
                    }
                }
            }
        }
        return sbox;
    }
}