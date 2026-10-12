package streambasic;

import java.util.List;

/**
 * 4보다 큰 짝수의 합계를 반복문과 스트림으로 각각 구하는 예제입니다.
 *
 * <p>입력 숫자는 1, 3, 6, 7, 8, 11입니다. 두 조건에 모두 맞는 숫자는 6과 8이므로 합은 14입니다.</p>
 *
 * <p>반복문에서는 숫자를 꺼내 조건을 검사하고 sum에 더하는 과정을 직접 작성합니다.</p>
 *
 * <p>스트림은 생성 → 조건 선택 → 숫자 타입 변환 → 합계 계산이라는 처리 단계를 연결합니다.</p>
 *
 * <p>filter의 람다는 요소 하나를 받아 true 또는 false를 반환합니다. true인 요소만 다음 단계로 넘어갑니다.</p>
 *
 * <p>mapToInt는 Integer 객체 스트림을 int 전용 스트림으로 바꿉니다. Integer::intValue는 number -> number.intValue()와 같은 뜻입니다.</p>
 *
 * <p>sum은 스트림을 소비해 숫자 하나를 반환하는 최종 연산입니다. 중간 연산과 달리 다음 스트림을 반환하지 않습니다.</p>
 *
 * <p>각 풀이의 누적 변수를 따로 사용해야 같은 숫자를 두 번 더하는 실수를 피할 수 있습니다.</p>
 */
public class ImperativeProgramming {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>중첩 조건문과 결합 조건문으로 각각 합계를 구합니다.</li>
     * <li>같은 조건을 두 filter로 표현하고 mapToInt 다음 sum으로 합계를 구합니다.</li>
     * <li>세 합계가 모두 14이며 원본 리스트가 그대로인지 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 3, 6, 7, 8, 11);

        // 명령형: 순회, 조건 검사, 누적 과정을 직접 작성합니다.
        // 다른 풀이와 누적 변수를 공유하면 같은 값을 두 번 더할 수 있습니다.
        int nestedSum = 0;
        for (int number : numbers) {
            if (number > 4) {
                if (number % 2 == 0) {
                    nestedSum += number;
                }
            }
        }

        int combinedSum = 0;
        for (int number : numbers) {
            if (number > 4 && number % 2 == 0) {
                combinedSum += number;
            }
        }

        // 선언형: 무엇을 선택하고 어떻게 변환·집계할지 파이프라인으로 표현합니다.
        int streamSum = numbers.stream()      // 생성: Stream<Integer>
                .filter(number -> number > 4) // 중간 연산: 조건에 맞는 요소만 통과
                .filter(number -> number % 2 == 0)
                .mapToInt(Integer::intValue) // 중간 연산: IntStream으로 변환
                .sum();                      // 최종 연산: int 결과를 반환

        System.out.println("중첩 조건문 합계: " + nestedSum);   // 14
        System.out.println("결합 조건문 합계: " + combinedSum); // 14
        System.out.println("스트림 합계: " + streamSum);       // 14
        System.out.println("원본 리스트: " + numbers); // 필터링은 원본 요소를 삭제하지 않습니다.
    }
}
