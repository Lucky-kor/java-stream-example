package terminaloperator;

import java.util.List;
import java.util.Optional;

/**
 * 숫자 여러 개를 하나의 값으로 합치는 reduce의 계산 과정을 배웁니다.
 *
 * <p>reduce는 지금까지 누적한 값과 다음 요소를 결합해 새 누적값을 만드는 연산입니다.</p>
 *
 * <p>덧셈에서는 초기 누적값 0에 1, 2, 3, 4를 차례로 더해 10을 얻습니다.</p>
 *
 * <p>어떤 값에 더해도 그 값을 바꾸지 않는 0을 덧셈의 항등값이라고 부릅니다. 곱셈의 항등값은 1입니다.</p>
 *
 * <p>항등값을 주면 빈 입력에서도 그 값을 결과로 돌려줍니다. 항등값이 없으면 값이 없을 수 있어 Optional을 반환합니다.</p>
 *
 * <p>Integer::sum은 (left, right) -> left + right와 같은 덧셈 메서드 참조입니다.</p>
 *
 * <p>결합 법칙은 숫자를 어느 묶음부터 계산해도 결과가 같다는 조건입니다. 뺄셈은 묶는 순서에 따라 값이 달라집니다.</p>
 *
 * <p>마지막 뺄셈 reduce는 잘못된 결합 함수의 학습용 사례입니다. 반복문처럼 계산된다고 일반화하지 마세요.</p>
 */
public class ReduceExample {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>덧셈과 곱셈의 서로 다른 항등값을 비교합니다.</li>
     * <li>항등값 유무에 따른 반환 타입과 빈 입력 결과를 확인합니다.</li>
     * <li>뺄셈의 묶는 순서가 결과를 바꾸는 이유를 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4);
        // 항등값: 어떤 요소와 결합해도 그 요소를 바꾸지 않는 값입니다.
        // 덧셈은 0, 곱셈은 1입니다. 첫 누적값으로도 사용됩니다.
        System.out.println("합: " + numbers.stream()
                .reduce(0, Integer::sum)); // 10
        System.out.println("곱: " + numbers.stream()
                .reduce(1, (left, right) -> left * right)); // 24
        Optional<Integer> sumWithoutIdentity = numbers.stream()
                .reduce(Integer::sum);
        System.out.println("항등값 없는 합: " + sumWithoutIdentity); // Optional[10]
        System.out.println("빈 합(항등값 있음): " + List.<Integer>of().stream()
                .reduce(0, Integer::sum)); // 0
        System.out.println("빈 합(항등값 없음): " + List.<Integer>of().stream()
                .reduce(Integer::sum));

        // reduce는 결합 순서가 달라져도 결과가 같아야 합니다(결합 법칙).
        // 뺄셈은 (1-2)-3 != 1-(2-3)이므로 병렬 reduce에 적합하지 않습니다.
        System.out.println("(1 - 2) - 3: " + ((1 - 2) - 3)); // -4
        System.out.println("1 - (2 - 3): " + (1 - (2 - 3))); // 2
        // 이 작은 예제의 순차 결과는 -10이지만 병렬에서도 같다고 기대하면 안 됩니다.
        System.out.println("학습용 잘못된 뺄셈 reduce: " + numbers.stream()
                .reduce(0, (a, b) -> a - b));
        // 리스트 같은 변경 가능한 컨테이너를 누적하려면 collect를 사용합니다.
    }
}
