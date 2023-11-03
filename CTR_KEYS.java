package Project;

public class CTR_KEYS {
    String keyBytes = "";
    public CTR_KEYS(){
        String key = "ABCD1234EFGH5678";
        int count = 16;
        for(byte e : key.getBytes()){
            keyBytes = keyBytes + String.format("%02x", e);
            if(count<0){
                break;
            }
        }
    }
}