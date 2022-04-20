import java.util.Random;


/**
 * Product 클래스에 넣어줄 무작위 문자열, 무작위 정수를 만들어주는 클래스
 */
public class RandomGenerator {

    public static int MIN_LETTER = 'a';
    public static int MAX_LETTER = 'z';
    public static int MAX_LENGTH = 20;

    public static String getRandomString() {
        return new Random().ints(MIN_LETTER, MAX_LETTER + 1) // 'a' ~ 'z'
                .limit(MAX_LENGTH)  // 최대 20글자
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append) // 각 character를 병합
                .toString();
    }

    public static int getRandomInt() {
        return new Random().nextInt(100000);
    }
}