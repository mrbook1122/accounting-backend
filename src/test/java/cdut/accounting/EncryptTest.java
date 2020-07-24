package cdut.accounting;

import cdut.accounting.utils.RSAUtils;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@SpringBootTest
public class EncryptTest {
    @Test
    void test() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String pk = RSAUtils.getPublicKeyStr();
        System.out.println(pk);
        String data = RSAUtils.publicEncrypt("abc");
        System.out.println(data);
        System.out.println(RSAUtils.privateDecrypt(data));
    }
}
