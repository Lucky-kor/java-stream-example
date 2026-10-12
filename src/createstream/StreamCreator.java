package createstream;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 데이터의 종류에 맞게 스트림을 만들고 숫자 스트림과 무한 스트림을 구분합니다.
 *
 * <p>문자열 배열은 Arrays.stream이나 Stream.of로, 리스트는 stream 메서드로 처리 통로를 만듭니다.</p>
 *
 * <p>{@code Stream<String>}의 꺾쇠 안 타입은 통로를 흐르는 요소가 String이라는 뜻입니다.</p>
 *
 * <p>IntStream은 int 숫자 전용 스트림이며 sum, average, min, max 같은 숫자 연산을 제공합니다.</p>
 *
 * <p>Stream.of(int[])는 숫자 각각이 아니라 배열 하나를 요소로 받습니다. 숫자를 각각 처리하려면 Arrays.stream(int[])을 사용합니다.</p>
 *
 * <p>range는 마지막 숫자를 제외하고 rangeClosed는 포함합니다. range(10, 15)는 10부터 14까지입니다.</p>
 *
 * <p>boxed는 int 값을 Integer 객체로 바꾸고, mapToObj는 숫자를 문자열 등 원하는 객체로 바꿉니다.</p>
 *
 * <p>iterate와 generate는 끝없이 값을 만들 수 있습니다. limit(5)는 최대 5개만 가져오도록 제한합니다.</p>
 *
 * <p>무한 스트림을 먼저 정렬하면 끝을 기다리므로 종료되지 않습니다. 반드시 먼저 처리 범위를 제한하세요.</p>
 */
public class StreamCreator {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>배열과 리스트에서 만든 스트림의 요소를 출력합니다.</li>
     * <li>기본형 배열, 범위, 타입 변환의 결과를 비교합니다.</li>
     * <li>끝없는 생성 흐름을 limit으로 제한하고 같은 시드의 난수 결과를 비교합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        String[] names = {"김러키", "바닐라", "김라떼"};
        System.out.println("Arrays.stream: " + Arrays.stream(names)
                .toList());
        System.out.println("Stream.of: " + Stream.of(names)
                .toList());
        System.out.println("컬렉션: " + List.of("Java", "Stream").stream()
                .toList());
        System.out.println("빈 스트림 개수: " + Stream.empty()
                .count()); // 0

        int[] numbers = {1, 2, 3};
        // int[]는 참조형 배열과 다릅니다. Stream.of(int[])는 배열 하나를 요소로
        // 갖는 Stream<int[]>입니다. 각 숫자를 처리하려면 Arrays.stream을 사용합니다.
        System.out.println("Stream.of(int[]) 요소 수: " + Stream.of(numbers)
                .count()); // 1
        System.out.println("Arrays.stream(int[]) 합계: " + Arrays.stream(numbers)
                .sum()); // 6
        System.out.println("평균: " + Arrays.stream(numbers)
                .average()
                .orElse(0.0)); // 2.0
        System.out.println("최솟값: " + Arrays.stream(numbers)
                .min()
                .orElse(-1)); // 1
        System.out.println("최댓값: " + Arrays.stream(numbers)
                .max()
                .orElse(-1)); // 3

        // range는 끝 제외, rangeClosed는 끝 포함입니다.
        System.out.println("range(10, 15): " + IntStream.range(10, 15)
                .boxed()
                .toList());
        System.out.println("rangeClosed(10, 15): " + IntStream.rangeClosed(10, 15)
                .boxed()
                .toList());
        // boxed(): IntStream → Stream<Integer>, mapToObj(): int → 원하는 참조 타입
        System.out.println("번호 이름: " + IntStream.rangeClosed(1, 3)
                .mapToObj(number -> "학생" + number)
                .toList());

        // 무한 스트림은 limit 등의 종료 조건이 필요합니다. 정렬을 먼저 하면
        // 모든 입력을 기다리므로 무한 스트림에서는 결과를 얻지 못합니다.
        System.out.println("iterate: " + Stream.iterate(1, number -> number + 2)
                .limit(5)
                .toList());
        System.out.println("generate: " + Stream.generate(() -> "Java")
                .limit(3)
                .toList());
        // 고정 시드로 실행마다 같은 난수를 생성하여 결과를 비교하기 쉽게 합니다.
        System.out.println("무한 난수 중 5개: " + new Random(42).ints(0, 10)
                .limit(5)
                .boxed()
                .toList());
        System.out.println("유한 난수 5개: " + new Random(42).ints(5, 0, 10)
                .boxed()
                .toList());
        System.out.println("대문자 변환: " + Stream.of("java", "stream")
                .map(name -> name.toUpperCase(Locale.ROOT))
                .toList());
    }
}
