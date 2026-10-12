package terminaloperator;

import java.util.List;
import java.util.stream.Stream;

/**
 * 출력 메시지로 스트림이 실제로 실행되는 시점을 확인하는 예제입니다.
 *
 * <p>filter와 map을 연결할 때는 실행할 규칙을 만들 뿐 아직 요소를 검사하지 않습니다.</p>
 *
 * <p>최종 연산 forEach를 호출하면 이름 하나씩 조건 검사와 변환이 실행됩니다. 이런 동작을 지연 실행이라고 부릅니다.</p>
 *
 * <p>Kim은 조건에서 탈락하고 Alice는 조건을 통과한 뒤 Alice님으로 바뀌어 출력됩니다.</p>
 *
 * <p>Bob은 탈락하고 Anna는 Anna님으로 출력됩니다. 조건을 통과하지 못한 이름에는 map이 실행되지 않습니다.</p>
 *
 * <p>람다 블록의 return은 해당 이름을 다음 단계로 넘길지 판단한 true 또는 false를 돌려줍니다.</p>
 *
 * <p>조건 함수 내부 출력은 이번 학습의 관찰 도구입니다. 실제 데이터 처리에서는 외부 상태를 바꾸는 동작을 피하세요.</p>
 */
public class ExampleV1 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>로그를 출력하는 filter와 map으로 파이프라인을 구성합니다.</li>
     * <li>최종 연산 전 안내가 먼저 출력되는지 확인합니다.</li>
     * <li>forEach를 시작한 뒤 요소별 조건 검사와 변환 순서를 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<String> names = List.of("Kim", "Alice", "Bob", "Anna");
        System.out.println("=== 파이프라인 생성 ===");
        Stream<String> selected = names.stream()
                .filter(name -> {
                    // 관찰을 위한 출력입니다. 실무의 조건 함수는 부수 효과 없이 작성합니다.
                    System.out.println("조건 검사: " + name);
                    return name.startsWith("A");
                })
                .map(name -> {
                    System.out.println("변환: " + name);
                    return name + "님";
                });
        System.out.println("=== 아직 조건 검사·변환은 실행되지 않았습니다 ===");
        System.out.println("=== 최종 연산 시작 ===");
        selected
                .forEach(name -> System.out.println("결과: " + name));
        // 순차 filter/map/forEach에서는 요소 하나가 통과한 뒤 다음 요소로 넘어갑니다.
        // sorted 같은 상태 유지 연산이 있으면 처리 흐름이 달라집니다.
        // count는 크기를 알면 중간 연산을 생략할 수도 있으므로 실행 관찰에는 부적합합니다.
    }
}
