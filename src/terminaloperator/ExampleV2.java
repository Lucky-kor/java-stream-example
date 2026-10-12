package terminaloperator;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 여러 조건을 연결한 스트림을 반복문과 비교하고 첫 결과에서 멈추는 검색을 확인합니다.
 *
 * <p>1부터 30까지 중 홀수이면서 3의 배수인 값은 3, 9, 15, 21, 27입니다.</p>
 *
 * <p>각 값에 10을 곱하면 30, 90, 150, 210, 270이 출력됩니다. 반복문도 같은 결과를 냅니다.</p>
 *
 * <p>순차 filter와 map은 목록을 단계마다 새로 저장하는 대신 요소 하나를 조건과 변환에 통과시킵니다.</p>
 *
 * <p>findFirst는 조건을 처음 만족한 값을 찾으면 검색을 끝내는 최종 연산입니다.</p>
 *
 * <p>number > 3 조건에서는 1, 2, 3, 4까지만 검사하고 4를 찾습니다. 이렇게 필요한 만큼만 처리하는 것을 단락 평가라고 부릅니다.</p>
 *
 * <p>findFirst의 반환값은 {@code Optional<Integer>}이며 orElse(-1)는 찾지 못했을 때 사용할 기본값입니다.</p>
 */
public class ExampleV2 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>연속된 조건과 변환으로 다섯 숫자를 출력합니다.</li>
     * <li>같은 계산을 반복문으로 작성해 결과를 비교합니다.</li>
     * <li>findFirst의 검사 로그가 4에서 멈추는지 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 30)
                .boxed()
                .toList();
        System.out.println("=== 스트림 결과: 30, 90, 150, 210, 270 ===");
        numbers.stream()
                .filter(number -> number % 2 != 0)
                .filter(number -> number % 3 == 0)
                .map(number -> number * 10)
                .forEach(System.out::println);
        System.out.println("=== 같은 조건의 반복문 ===");
        for (int number : numbers) {
            if (number % 2 != 0 && number % 3 == 0) {
                System.out.println(number * 10);
            }
        }
        // findFirst는 결과를 찾으면 이후 요소를 검사하지 않습니다.
        // 이 순차 예제에서는 1, 2, 3, 4만 검사하고 4를 반환합니다.
        System.out.println("=== 단락 평가 ===");
        int first = numbers.stream()
                .filter(number -> {
            System.out.println("검사: " + number);
            return number > 3;
        })
        .findFirst()
        .orElse(-1);
        System.out.println("처음 찾은 값: " + first);
    }
}
