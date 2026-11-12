package org.yjhking.pethome.user.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class JwtKeys {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtKeys(@Value("${PETHOME_JWT_PRIVATE_KEY_FILE}") String privateKeyFile,
                   @Value("${PETHOME_JWT_PUBLIC_KEY_FILE}") String publicKeyFile)
            throws IOException, GeneralSecurityException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        privateKey = (RSAPrivateKey) factory.generatePrivate(
                new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(privateKeyFile))));
        publicKey = (RSAPublicKey) factory.generatePublic(
                new X509EncodedKeySpec(Files.readAllBytes(Paths.get(publicKeyFile))));
        if (publicKey.getModulus().bitLength() < 2048
                || !publicKey.getModulus().equals(privateKey.getModulus())) {
            throw new IllegalArgumentException("Configure a matching RSA key pair of at least 2048 bits");
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
