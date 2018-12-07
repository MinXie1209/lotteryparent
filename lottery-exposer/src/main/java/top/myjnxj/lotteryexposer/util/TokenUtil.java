package top.myjnxj.lotteryexposer.util;

import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


public class TokenUtil {
	/**
	 * 密文，用于加密解密Signature
	 */
	private static final String JWT_SECRET = "jxyuen#c(j12@lds)dnsaiougd6flag7fv'jduNWH98NQWEDU9[BBC886Q3D4";
	/**
	 * 创建jwt
	 *
	 * @param subject
	 * @param ttlMillis 过期的时间长度
	 * @return
	 * @throws Exception
	 */
	public static String createJWT(String subject, long ttlMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//签发jwt的时间
		long nowMillis=System.currentTimeMillis();
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setIssuedAt(new Date())
				.setSubject(subject)
				.signWith(signatureAlgorithm, key)
				.setExpiration(new Date(nowMillis + ttlMillis));

		return builder.compact();
	}
	private static SecretKey generalKey() {
		//秘钥
		String stringKey =TokenUtil.JWT_SECRET;
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}
	/**
	 * 验证token
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception{
		SecretKey key = generalKey();
		Claims claims = Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(jwt).getBody();
		return claims;
	}
	public static void main(String[] args) {
		try {
			String token=TokenUtil.createJWT("", 30*1000L);
			Thread.sleep(3000);
			System.out.println(TokenUtil.parseJWT(token).getSubject());
		}catch (ExpiredJwtException e) {
			System.out.println("token过期");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
