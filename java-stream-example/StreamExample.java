import java.util.ArrayList;
import java.util.List;

/**
 * 테스트 코드를 작성하는 클래스. main 메소드를 포함한다.
 * 
 */
public class StreamExample {

    private static long before;     // 실행 시작 시간
    private static long after;      // 실행 종료 시간

    /**
     * <pre>
     * 실행 시작 시간 측정 시 호출하는 메소드
     * </pre>
     * @see #endExecution
     */
    private static void startExecution() {
        before = System.currentTimeMillis();
    }

    /**
     * <pre>
     * 실행 종료 시간 측정 시 호출하는 메소드
     * </pre>
     * @see #startExecution
     */
    private static void endExecution() {
        after = System.currentTimeMillis();

        System.out.println("execute time : " + (after - before) + "ms");
    }

    /**
     * 테스트 코드를 작성하는 main 메소드
     */
    public static void main(String[] args) {
        List<Product> productList = new ArrayList<>();

        // Product 100,000개 생성
        for(int i = 0 ; i < 100000 ; ++i) {
            productList.add(new Product(RandomGenerator.getRandomString(), RandomGenerator.getRandomInt()));
        }
        
        // 테스트 1 (for-loop) : 가격이 짝수인 Product들의 이름을 "a"로 바꾸는 데 걸리는 시간 측정
        startExecution();
        for (int i = 0 ; i < productList.size() ; ++i) {
            if (productList.get(i).getPrice() % 2 == 0) {
                productList.get(i).setName("a");
            }
        }
        endExecution();


        // 테스트 2 (stream) : 가격이 짝수인 Product들의 이름을 "a"로 바꾸는 데 걸리는 시간 측정
        startExecution();
        productList.stream()
                   .filter(product -> product.getPrice() % 2 == 0)
                   .forEach(product -> product.setName("a"));
        endExecution();
    }
}