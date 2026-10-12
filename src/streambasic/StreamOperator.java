package streambasic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 리스트와 배열을 같은 방식으로 정렬하고 스트림을 다시 사용할 수 없는 이유를 확인합니다.
 *
 * <p>리스트와 배열은 데이터를 담는 그릇이고, 스트림은 그 데이터를 처리하는 통로입니다.</p>
 *
 * <p>fruits.stream()과 Arrays.stream(fruitArray)는 서로 다른 그릇에서 스트림을 만듭니다.</p>
 *
 * <p>sorted는 정렬된 요소를 다음 단계로 전달하는 중간 연산이고, toList는 결과를 리스트에 담는 최종 연산입니다.</p>
 *
 * <p>최종 연산을 마친 스트림은 소비된 상태이므로 같은 변수로 다시 처리하면 IllegalStateException이 발생합니다.</p>
 *
 * <p>try-catch는 실패할 수 있는 코드를 실행하고 예외가 발생하면 대신 설명을 출력하는 문법입니다.</p>
 *
 * <p>원본 리스트는 그대로 남아 있으므로 fruits.stream()을 다시 호출해 새 스트림을 만들 수 있습니다.</p>
 *
 * <p>이 예제의 정렬은 원본을 바꾸지 않지만, 람다에서 객체의 setter를 호출하면 객체 내용은 바뀔 수 있습니다.</p>
 */
public class StreamOperator {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>리스트와 배열을 각각 정렬한 결과와 원본을 출력합니다.</li>
     * <li>이미 사용한 스트림을 재사용해 발생한 예외를 처리합니다.</li>
     * <li>원본에서 새 스트림을 생성해 요소 개수 3을 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<String> fruits = List.of("바나나", "사과", "오렌지");
        String[] fruitArray = {"바나나", "사과", "오렌지"};
        Stream<String> listStream = fruits.stream();
        List<String> sortedList = listStream
                .sorted()
                .toList();
        List<String> sortedArray = Arrays.stream(fruitArray)
                .sorted()
                .toList();
        System.out.println("리스트 정렬: " + sortedList);
        System.out.println("배열 정렬: " + sortedArray);
        System.out.println("원본 리스트: " + fruits);
        System.out.println("원본 배열: " + Arrays.toString(fruitArray));

        // 최종 연산 후 같은 스트림으로 다시 파이프라인을 만들 수 없습니다.
        // 학습용 실패는 잡아서 실행을 정상 종료합니다.
        try {
            listStream
                    .sorted()
                    .toList();
        } catch (IllegalStateException exception) {
            System.out.println("재사용 실패: 스트림은 한 번만 소비할 수 있습니다.");
        }
        // 원본 컬렉션에서 새 스트림을 생성하면 다시 처리할 수 있습니다.
        System.out.println("새 스트림의 개수: " + fruits.stream()
                .count()); // 3
        // 원본 불변은 여기서 사용한 연산의 특성입니다. 객체의 setter를 호출하면
        // 스트림 안에서도 원본 객체가 바뀔 수 있으므로 부수 효과를 피합니다.
    }
}
