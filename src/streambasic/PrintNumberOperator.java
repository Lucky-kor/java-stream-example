package streambasic;

import java.util.List;

/**
 * 같은 숫자를 네 가지 방법으로 출력하며 반복문에서 스트림으로 넘어가는 예제입니다.
 *
 * <p>리스트는 여러 값을 순서대로 담는 자료구조입니다. 여기서는 1부터 5까지 저장합니다.</p>
 *
 * <p>인덱스 for문과 향상된 for문에서는 우리가 반복을 직접 작성합니다. 이를 외부 반복이라고 부릅니다.</p>
 *
 * <p>스트림에서는 forEach에 요소 하나를 처리할 동작을 전달하면 스트림이 반복을 맡습니다. 이를 내부 반복이라고 부릅니다.</p>
 *
 * <p>number -> System.out.println(number)는 숫자 하나를 받아 출력하는 람다입니다.</p>
 *
 * <p>System.out::println은 같은 출력 메서드를 가리키는 메서드 참조이며, 이 람다를 짧게 표현한 것입니다.</p>
 *
 * <p>네 방식의 출력은 모두 1, 2, 3, 4, 5입니다. 결과가 같아도 반복을 작성하는 방식이 어떻게 다른지 비교하세요.</p>
 */
public class PrintNumberOperator {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>인덱스 for문과 향상된 for문의 출력 순서를 비교합니다.</li>
     * <li>람다와 메서드 참조를 사용하는 forEach의 출력이 같은지 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        System.out.println("=== 인덱스를 사용하는 for문 ===");
        for (int index = 0; index < numbers.size(); index++) {
            System.out.println(numbers.get(index));
        }
        System.out.println("=== 향상된 for문 ===");
        for (int number : numbers) {
            System.out.println(number);
        }
        // 스트림이 순회를 담당하고, 우리는 요소 하나에 적용할 동작만 전달합니다.
        System.out.println("=== 람다: number -> System.out.println(number) ===");
        numbers.stream()
                .forEach(number -> System.out.println(number));
        // 같은 인자를 그대로 메서드에 전달할 때 메서드 참조로 줄일 수 있습니다.
        System.out.println("=== 메서드 참조: System.out::println ===");
        numbers.stream()
                .forEach(System.out::println);
        // 여기서는 순차 스트림입니다. 병렬 forEach는 출력 순서를 보장하지 않습니다.
    }
}
