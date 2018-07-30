package com.mouse.study.test.hutool;

import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @author lwf
 * @date 2018/7/25
 * use: 验签测试
 */
public class AsymmetricCryptoTest {

    public static final String RSA_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAO2u+9vkuQGYwe2Yw0XFXRdhCkvwY4328H2STJjeW2LGDJqYYQVbpw1CNwJ0hKKcpk1/APENbdv84RP7x3YEkQVNoB0uSj8qnpUsnuyBdxLdToohikvOrNRWgQx/ZvgFE+rWjka9wVaKqLUbUWxpq9GiUAPFa78kYOABD8dIMtg9AgMBAAECgYEAgpNzQiaxjLMDNyiJfrcioUlqgrWZu9BB5nqNIh5mTilHm1bDVlI3wAz0c6DXjQ5KPqDbP5KFHCoc7QGRXsC7egNBX9kNtL7ZCuYw78pE5sNM4+885fgoqaBCbnc+PxgyAqQ+ZIO5u6QKXQpEoe7PpvxCVBAGyn/1klaQVidUivECQQD87PnV05v8ibOv0N6cSpEZ8s/mdFVDSw0sFBdxMseFGY/WjDl1g9ZQCuwjrcT5S/mnYgb6MzRJn+s0rfCFlImLAkEA8JKURMVg6GqIleQq4e03uqEZ6AgErBlh2e+1/T9vgij6n/ueZysamHydZAupk3Wsfn1bkmdA4zqOCf7UZueOVwJAHDwIF8qrmyF0IahbcW8Ri6gDdWJ/MifqrIUBqO1WQJF98SFuOKQjBIRzn/gCCSJmGD1lMgENUTq88wCH3SGbyQJBAJzEuDAUe3EZM0aSOEufvQg2QV6OExVfOP+/ENYmB3FHaQLmAjRyx1MFKb9vRiMctLp80DaYaJVqq/Lhh+JDFOMCQQCXrBhjTx4KfLzfUhOVzm5D8w5sAn9Sg1jDeMwe8tyiyUBbbkw+k9qK0YLOfnwKuC3MNI5URjaKyLzilPDZZkrs";

    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcp9NU29EfqPReJLGBS0WZwCKxORrc4IQpKbup1cF4KzQnpMCwcJXF9KW1vJ/ZzOMwAlGfhq2V96MGPOO6T/Zkesasjdmy19wnOdzDxGXu2pEMbFMDOonYxf1m5/VNs2+TZ18eyW585XefXoNlYCzg6RJmXK0fZ1UPAU9ZxgocEQIDAQAB";

    String str = "https://www.baidu.com|0|regUserByFive|0002900F0352200|201807240000002|https://www.baidu.com|1|1.00";

    @org.junit.Test
    public void generatorKey() {
        RSA crypto = new RSA(RSA_PRIVATE_KEY, RSA_PUBLIC_KEY);
        System.out.println(crypto.getPrivateKeyBase64());
        System.out.println(crypto.getPublicKeyBase64());
        System.out.println(crypto.encryptHex(str, KeyType.PrivateKey));
        System.out.println(crypto.encryptStr(str, KeyType.PrivateKey));
        System.out.println(crypto.encryptBase64(str, KeyType.PrivateKey));
    }

    @org.junit.Test
    public void generatorAsym() {
        AsymmetricCrypto crypto = new AsymmetricCrypto(AsymmetricAlgorithm.RSA, RSA_PRIVATE_KEY, RSA_PUBLIC_KEY);
        System.out.println(crypto.getPrivateKeyBase64());
        System.out.println(crypto.getPublicKeyBase64());
        String encryptBase64 = crypto.encryptBase64(str, KeyType.PrivateKey);
        System.out.println("base64密文：" + encryptBase64);
        System.out.println("hex密文：" + crypto.encryptHex(str, KeyType.PrivateKey));
        System.out.println("原文：" + crypto.decryptFromBase64(encryptBase64, KeyType.PublicKey));
    }


}
