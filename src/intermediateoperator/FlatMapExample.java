package intermediateoperator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 리스트 안의 리스트를 사용해 map과 flatMap의 차이를 배웁니다.
 *
 * <p>입력은 [Java, SQL], 빈 목록, [Java, Python]이라는 세 개의 과목 목록입니다.</p>
 *
 * <p>map은 입력 하나를 결과 하나로 바꿉니다. 목록을 크기로 바꾸면 결과는 2, 0, 2입니다.</p>
 *
 * <p>map(List::stream)은 목록 하나를 스트림 하나로 바꿀 뿐, 그 안의 과목을 꺼내지는 않습니다.</p>
 *
 * <p>flatMap은 목록마다 만든 스트림 안의 과목을 꺼내 하나의 통로로 이어 줍니다.</p>
 *
 * <p>따라서 결과는 Java, SQL, Java, Python이고 빈 내부 목록은 아무 요소도 내보내지 않습니다.</p>
 *
 * <p>distinct를 이어 붙이면 반복된 Java가 제거됩니다. split으로 문장을 나눈 배열에도 같은 펼치기를 적용할 수 있습니다.</p>
 */
public class FlatMapExample {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>map으로 과목 목록별 크기를 구합니다.</li>
     * <li>flatMap으로 모든 과목을 펼치고 중복을 제거합니다.</li>
     * <li>문장 하나를 단어 여러 개로 바꾸어 같은 원리를 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<List<String>> classes = List.of(List.of("Java", "SQL"), List.of(), List.of("Java", "Python"));
        // map: List<String> 하나를 크기 하나로 변환합니다. 빈 반도 0으로 남습니다.
        System.out.println("반별 수강 과목 수: " + classes.stream()
                .map(List::size)
                .toList()); // [2, 0, 2]
        // map(List::stream)의 결과 타입은 Stream<Stream<String>>입니다.
        // flatMap은 각 내부 스트림의 요소를 이어 Stream<String>으로 만듭니다.
        System.out.println("모든 수강 과목: " + classes.stream()
                .flatMap(List::stream)
                .toList());
        System.out.println("중복 없는 과목: " + classes.stream()
                .flatMap(List::stream)
                .distinct()
                .toList());
        // 문자열 하나가 단어 여러 개가 되는 사례입니다. split 결과 배열도 펼칩니다.
        System.out.println("문장 → 단어: " + Stream.of("Java Stream", "Stream API")
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .distinct()
                .toList());
        // 직접 해보기: flatMap 뒤에 filter로 길이가 4 이상인 과목만 남겨 보세요.
    }
}
