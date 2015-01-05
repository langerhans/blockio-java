package io.block.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.block.api.model.Input;
import io.block.api.model.Response;
import io.block.api.model.Signer;
import io.block.api.model.WithdrawSignRequest;
import junit.framework.TestCase;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Ignore;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

// TODO Most of these fail or are false positives, but the signing works fine... Such lazy, many not wow... Are the test vectors correct??
public class SigningUtilsTest extends TestCase {

    public void testPinToKey() throws Exception {
        String pin = "73a8c60ad5974b5830b3d6cf19adb567";
        byte[] key = SigningUtils.fromHex("9a292db5193b18aee4fcff41025d2abecbff45288d09660befe25ef2edd53523");

        byte[] generatedKey = SigningUtils.pinToKey(pin);

        assertTrue(Arrays.equals(key, generatedKey));
    }


    public void testDecrypt() throws Exception {
        String input = "block.io";
        byte[] key = SigningUtils.fromHex("9a292db5193b18aee4fcff41025d2abecbff45288d09660befe25ef2edd53523");
        byte[] enc = Base64.decode("wq2vFFLsn2wB1DUipnEGkw==");

        byte[] generatedEnc = SigningUtils.encryptPassphrase(input, key);
        byte[] generatedInput = SigningUtils.decryptPassphrase(generatedEnc, key);
        byte[] generatedInput2 = SigningUtils.decryptPassphrase(enc, key);

        assertTrue(Arrays.equals(enc, generatedEnc));
        assertEquals(input, new String(generatedInput));
        assertEquals(input, new String(generatedInput2));

    }

    public void testGetPrivKey() throws Exception {
        byte[] pass = "block.io".getBytes("UTF-8");
        byte[] privBytes = SigningUtils.fromHex("7a01628988d23fae697fa05fcdae5a82fe4f749aa9f24d35d23f81bee917dfc3");

        byte[] generatedPrivBytes = SigningUtils.getPrivKey(pass);

        assertTrue(Arrays.equals(privBytes, generatedPrivBytes));
    }

    public void testDerivePubKey() throws Exception {
        byte[] priv = SigningUtils.getPrivKey("block.io".getBytes("UTF-8"));
        byte[] pubKey = SigningUtils.fromHex("03359ac0aa241b1a40fcab68486f8a4b546ad3301d201c3645487093578592ec8f");

        byte[] generatedPub = SigningUtils.derivePublicKey(priv);

        assertTrue(Arrays.equals(pubKey, generatedPub));
    }

    public void testSignData() throws Exception {
        String input = "695369676e65645468697344617461546861744973323536426974734c6f6e67";
        byte[] privBytes = SigningUtils.fromHex("7a01628988d23fae697fa05fcdae5a82fe4f749aa9f24d35d23f81bee917dfc3");
        String signature = "304402205587dfc87c3227ad37b021c08c873ca4b1faada1a83f666d483711edb2f4f743022004ee40d9fe8dd03e6d42bfc7d0e53f75286125a591ed14b39265978ebf3eea36";

        String generatedSignature = SigningUtils.signData(input, privBytes);

        assertEquals(signature, generatedSignature);
    }

    public void testComplete() throws Exception {
        String dataToSign = "695369676e65645468697344617461546861744973323536426974734c6f6e67";
        String pin = "bc4779ff545bc04a54e6c32b7609a91b";
        String pass = "28+OsMzlhUYyVJ9PUlmZ+Q==";
        String signedData = "304402205587dfc87c3227ad37b021c08c873ca4b1faada1a83f666d483711edb2f4f743022004ee40d9fe8dd03e6d42bfc7d0e53f75286125a591ed14b39265978ebf3eea36";

        byte[] privKey =
                SigningUtils.getPrivKey(
                        SigningUtils.decryptPassphrase(
                                Base64.decode(pass),
                                SigningUtils.pinToKey(pin)));

        String generatedSignedData = SigningUtils.signData(dataToSign, privKey);

        assertEquals(signedData, generatedSignedData);
    }

    public void testFromFile() throws Exception {
        String request = new String( Files.readAllBytes(Paths.get(SigningUtilsTest.class.getResource("sample_signing_request.json").toURI())));
        String response = new String( Files.readAllBytes(Paths.get(SigningUtilsTest.class.getResource("sample_signing_response.json").toURI())));
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Response.ResponseWithdrawSignRequest signResquestR = gson.fromJson(request, Response.ResponseWithdrawSignRequest.class);
        WithdrawSignRequest signResquest = signResquestR.withdrawSignRequest;

        signResquest = SigningUtils.signWithdrawalRequest(signResquest, "64c8c9d431f693c8d76e8ee2b3020e0f");

        assertEquals(response, gson.toJson(signResquest, WithdrawSignRequest.class));
    }
}