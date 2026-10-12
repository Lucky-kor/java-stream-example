package datareport;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 학년과 성별로 학생을 나누고 각 그룹의 이름, 인원, 평균을 구합니다.
 *
 * <p>groupingBy는 같은 기준 값을 가진 학생을 한 그룹으로 모으는 수집 방법입니다.</p>
 *
 * <p>학년이 키인 {@code Map<Integer, List<Student>>}에서 1이라는 키로 1학년 학생 목록을 찾을 수 있습니다.</p>
 *
 * <p>TreeMap::new는 키를 정렬하는 맵을 사용하라는 뜻으로 학년이 1, 2, 3 순서로 출력됩니다.</p>
 *
 * <p>mapping은 각 그룹 안에서 학생을 이름으로 바꿉니다. counting은 학생 수를, averagingInt는 점수 평균을 구합니다.</p>
 *
 * <p>그룹 안에 적용하는 수집 방법을 downstream collector라고 부릅니다. 처음에는 그룹별 처리 규칙이라는 뜻으로 이해하세요.</p>
 *
 * <p>학년별 인원은 1학년 3명, 2학년 2명, 3학년 1명입니다. 평균은 각각 약 89.33, 85.0, 100.0입니다.</p>
 *
 * <p>이 패키지의 grade는 학년, score는 점수입니다. getter 이름을 구분하고 연산을 연결하세요.</p>
 */
public class ExampleV1 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>학년별·성별 학생 목록을 모읍니다.</li>
     * <li>성별 그룹 안에서 학생 객체 대신 이름을 수집합니다.</li>
     * <li>학년별 인원과 점수 평균을 구합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = SampleStudents.create();
        // 분류 함수는 그룹의 키를 만듭니다. 같은 학년의 학생이 같은 리스트에 모입니다.
        Map<Integer, List<Student>> byGrade = students.stream()
                .collect(Collectors.groupingBy(
                Student::getGrade, TreeMap::new, Collectors
                        .toList()));
        System.out.println("학년별 학생: " + byGrade);
        // TreeMap을 지정해 키를 정렬합니다. 기본 groupingBy의 맵 순서는 보장되지 않습니다.
        Map<String, List<Student>> byGender = students.stream()
                .collect(Collectors.groupingBy(
                Student::getGender, TreeMap::new, Collectors
                        .toList()));
        System.out.println("성별 학생: " + byGender);
        Map<String, List<String>> namesByGender = students.stream()
                .collect(Collectors.groupingBy(
                Student::getGender, TreeMap::new,
                Collectors.mapping(Student::getName, Collectors
                        .toList())));
        System.out.println("성별 이름: " + namesByGender);
        // counting은 리스트 대신 각 그룹의 요소 개수를 Long으로 반환합니다.
        System.out.println("학년별 인원: " + students.stream()
                .collect(Collectors.groupingBy(
                Student::getGrade, TreeMap::new, Collectors.counting()))); // {1=3, 2=2, 3=1}
        System.out.println("학년별 평균: " + students.stream()
                .collect(Collectors.groupingBy(
                Student::getGrade, TreeMap::new, Collectors.averagingInt(Student::getScore))));
        // 1학년 약 89.33, 2학년 85.0, 3학년 100.0
    }
}
