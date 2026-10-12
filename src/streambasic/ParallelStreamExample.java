package streambasic;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 순차 처리와 병렬 처리를 비교하고 함께 쓰는 변수의 위험을 배우는 심화 예제입니다.
 *
 * <p>순차 스트림은 요소를 순서대로 처리하고 병렬 스트림은 처리할 일을 여러 작업으로 나눌 수 있습니다.</p>
 *
 * <p>1부터 1000까지의 합은 두 방식 모두 500500입니다. sum은 나눠 계산한 결과를 합치는 일을 담당합니다.</p>
 *
 * <p>여러 작업이 같은 외부 변수에 동시에 값을 더하면 갱신이 겹쳐 결과를 잃을 수 있습니다. 이를 공유 상태 문제라고 부릅니다.</p>
 *
 * <p>그래서 외부 배열의 sum[0]을 갱신하거나 외부 ArrayList에 직접 add하는 대신 sum이나 수집 연산을 사용합니다.</p>
 *
 * <p>병렬 forEach의 출력 순서는 보장되지 않습니다. 순서가 있는 입력에서 출력 순서를 유지하려면 forEachOrdered를 사용합니다.</p>
 *
 * <p>순서 있는 리스트를 toList로 수집하면 결과의 입력 순서는 유지되지만, 실제 계산이 그 순서대로 진행됐다는 뜻은 아닙니다.</p>
 *
 * <p>병렬 처리는 일을 나누고 합치는 비용이 있어 항상 더 빠르지는 않습니다. 기초 예제를 이해한 뒤 학습하세요.</p>
 */
public class ParallelStreamExample {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>같은 입력의 순차·병렬 합계가 같은지 비교합니다.</li>
     * <li>병렬 변환 결과를 외부 변수 없이 리스트로 수집합니다.</li>
     * <li>forEach와 forEachOrdered의 출력 순서를 비교합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 1000)
                .boxed()
                .toList();
        long sequentialSum = numbers.stream()
                .mapToLong(Integer::longValue)
                .sum();
        long parallelSum = numbers.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
        System.out.println("순차 합계: " + sequentialSum); // 500500
        System.out.println("병렬 합계: " + parallelSum);   // 500500
        // 다음과 같은 코드는 여러 스레드가 같은 값을 갱신하므로 잘못된 결과가 나올 수 있습니다.
        // int[] sum = {0};
        // numbers.parallelStream().forEach(number -> sum[0] += number);
        // ArrayList에 외부에서 add하는 방식도 피하고 collect/toList를 사용합니다.
        List<Integer> squares = List.of(1, 2, 3, 4, 5).parallelStream()
                .map(number -> number * number)
                .toList();
        System.out.println("입력 순서를 보존한 제곱 목록: " + squares); // [1, 4, 9, 16, 25]
        System.out.println("=== 병렬 forEach: 실행마다 순서가 달라질 수 있음 ===");
        List.of(1, 2, 3, 4, 5).parallelStream()
                .forEach(System.out::println);
        System.out.println("=== 병렬 forEachOrdered: 1, 2, 3, 4, 5 순서 ===");
        List.of(1, 2, 3, 4, 5).parallelStream()
                .forEachOrdered(System.out::println);
        // 작은 데이터는 작업 분할·병합 비용 때문에 병렬 처리가 더 느릴 수 있습니다.
        // 이 예제는 성능 측정이 아닙니다. 실제 데이터와 작업으로 측정한 뒤 선택합니다.
    }
}
