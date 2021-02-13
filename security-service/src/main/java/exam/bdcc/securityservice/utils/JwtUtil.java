package exam.bdcc.securityservice.utils;

public class JwtUtil {
    public static String SECRET = "MySecretPass";
    public static String AUTH_HEADER = "Authorization";
    public static String PREFIX = "Bearer ";
    public static long EXPIRE_ACCESS_TOKEN = 2*60*1000;
    public static long EXPIRE_REFRESH_TOKEN = 5*60*1000;
}
