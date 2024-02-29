package Key;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class EjJulio2 {
	
	//Verificar Firma
	public static void main(String[] args) throws Exception {
		KeyStore ks = KeyStore.getInstance("pkcs12");
		char [] password = "renn".toCharArray();
		ks.load(new FileInputStream("C:\\cygwin64\\home\\nzjha\\certs\\keystore.p12"), password);
		PublicKey pubKey = (PublicKey) ks.getCertificate("neo").getPublicKey();
		
		try (
				BufferedInputStream inF = new BufferedInputStream(
						EjJulio2.class.getResourceAsStream("C:\\Users\\nzjha\\Desktop\\Clase-eciplse\\ECIPLSE\\PSP\\pdf\\Colecciones.pdf"));
				BufferedInputStream inS = new BufferedInputStream(
						new FileInputStream(System.getProperty("user.home") + "//Desktop//Colecciones.pdf.sing"))
				) {
			Signature sign = Signature.getInstance("SHA512withRSA");
			sign.initVerify(pubKey);
			byte [] buffer = new byte[1024];
			int n;
			while ((n = inF.read(buffer)) > 0)
				sign.update(buffer, 0, n);
			System.out.println(sign.verify(inS.readAllBytes()));
		}
	}

}
