package streampractice;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 선택, 변환, 정렬, 집계를 사용해 학생 보고서를 만드는 종합 실습입니다.
 *
 * <p>먼저 80점 이상 학생을 filter로 남기고 이름 끝 영문자를 대문자로 바꿔 리스트에 모읍니다.</p>
 *
 * <p>결과는 민지A, 지훈C, 가영E입니다. 한글은 대문자와 소문자의 구분이 없어 그대로입니다.</p>
 *
 * <p>다음으로 점수 내림차순, 동점이면 이름 오름차순으로 정렬하고 상위 2명을 선택합니다.</p>
 *
 * <p>90점인 가영e와 지훈c가 상위 2명이고 전체 다섯 명의 평균은 81.4입니다.</p>
 *
 * <p>삼항 연산자 조건 ? 값1 : 값2는 조건이 참이면 값1, 거짓이면 값2를 선택합니다. 여기서는 통과와 재학습이라는 그룹 이름을 정합니다.</p>
 *
 * <p>각 단계의 요소가 학생인지 이름인지 점수인지 설명할 수 있는지 확인하고, 빈 입력과 동점 입력으로 다시 실행해 보세요.</p>
 */
public class Application {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>80점 이상 이름을 대문자로 변환해 수집합니다.</li>
     * <li>동점을 처리한 상위 2명과 전체 평균을 구합니다.</li>
     * <li>통과·재학습별 이름을 모으고 원본 학생 목록을 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = List.of(new Student("민지a", 85), new Student("수현b", 77),
                new Student("지훈c", 90), new Student("은우d", 65), new Student("가영e", 90));
        // 80점 이상인 학생의 이름을 대문자로 바꿉니다. 한글은 그대로,
        // a/c/e는 A/C/E로 바뀝니다. Locale.ROOT로 기본 로케일에 따른 차이를 피합니다.
        List<String> passedNames = students.stream()
                .filter(student -> student.getScore() >= 80)
                .map(student -> student.getName().toUpperCase(Locale.ROOT))
                .toList();
        System.out.println("80점 이상 이름: " + passedNames); // [민지A, 지훈C, 가영E]
        passedNames
                .forEach(System.out::println);

        // 점수 내림차순, 동점은 이름 오름차순으로 정렬한 뒤 상위 2명을 선택합니다.
        List<Student> topTwo = students.stream()
                .sorted(Comparator.comparingInt(Student::getScore)
                .reversed()
                .thenComparing(Student::getName))
                .limit(2)
                .toList();
        System.out.println("상위 2명: " + topTwo); // 가영e, 지훈c
        System.out.println("전체 평균: " + students.stream()
                .mapToInt(Student::getScore)
                .average()
                .orElse(0.0)); // 81.4
        Map<String, List<String>> namesByResult = students.stream()
                .collect(Collectors.groupingBy(
                student -> student.getScore() >= 80 ? "통과" : "재학습", TreeMap::new,
                Collectors.mapping(Student::getName, Collectors
                        .toList())));
        System.out.println("결과별 이름: " + namesByResult);
        System.out.println("원본 학생: " + students);
        // 직접 해보기: 통과 기준을 90으로 바꾸고, 상위 3명·통과자 평균을 구하세요.
        // 빈 목록, 전원 미통과, 동점 학생을 입력해 처리 결과를 확인하세요.
    }
}
