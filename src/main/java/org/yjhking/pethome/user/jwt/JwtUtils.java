package org.yjhking.pethome.user.jwt;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

/**
 * JWT 密钥的解析和加密 工具类
 */
public class JwtUtils {
    
    private static final String JWT_PAYLOAD_USER_KEY = "user";
    
    
    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }
    
    /**
     * 私钥加密token
     *
     * @param userInfo   载荷中的数据
     * @param privateKey 私钥
     * @param expire     过期时间，单位分钟
     * @return JWT
     */
    public static String generateTokenExpireInMinutes(Object userInfo, PrivateKey privateKey, int expire) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JSONObject.toJSONString(userInfo))
                .setId(createJTI())
                //当前时间往后加多少分钟
                .setExpiration(DateTime.now().plusMinutes(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
        
    }
    
    /**
     * 私钥加密token
     *
     * @param userInfo   载荷中的数据
     * @param privateKey 私钥
     * @param expire     过期时间，单位秒
     * @return JWT
     */
    public static String generateTokenExpireInSeconds(Object userInfo, PrivateKey privateKey, int expire) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JSONObject.toJSONString(userInfo))
                .setId(createJTI())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }
    
    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return Jws<Claims>
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }
    
    
    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey, Class<T> userType) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        T t = JSONObject.parseObject(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType);
        claims.setLoginData(t);
        claims.setExpiration(body.getExpiration());
        return claims;
    }
    
    /**
     * 获取token中的载荷信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }
    
    public static void main(String[] args) throws Exception {
        // 1 获取token
        PrivateKey privateKey = RsaUtils.getPrivateKey(JwtUtils.class.getClassLoader().getResource("auth_rsa.pri").getFile());
        System.out.println(privateKey);
        String token = generateTokenExpireInSeconds(new User(1L, "zs"), privateKey, 10);
        System.out.println(token);
        
        // 2 解析token里面内容
        PublicKey publicKey = RsaUtils.getPublicKey(JwtUtils.class.getClassLoader().getResource("auth_rsa.pub").getFile());
        Payload<User> payload = getInfoFromToken(token, publicKey, User.class);
        System.out.println(payload);
//        Thread.sleep(11000); //超时后继续解析
        payload = getInfoFromToken(token, publicKey, User.class);
        System.out.println(payload);
        
    }
    
}

class User {
    private Long id;
    private String name;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    
    public User() {
    }
    
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}