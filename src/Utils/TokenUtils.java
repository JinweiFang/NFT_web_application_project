package Utils;

import java.util.Base64;
import java.util.UUID;

public class TokenUtils {
    public static String generatePasswordResetToken() {
        UUID token = UUID.randomUUID();
        return new String(Base64.getEncoder().encode(token.toString().getBytes()));
    }
}
