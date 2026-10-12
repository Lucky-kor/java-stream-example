package terminaloperator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 처리 결과를 출력만 하지 않고 리스트, 맵, 문자열로 모아 저장하는 예제입니다.
 *
 * <p>collect는 여러 요소를 원하는 결과로 모으는 최종 연산이고 Collectors는 모으는 방법을 제공합니다.</p>
 *
 * <p>여학생을 고른 뒤 이름만 꺼내 toList로 모으면 [이자바, 나미녀]가 됩니다.</p>
 *
 * <p>Map은 키로 값을 찾는 자료구조입니다. 여기서는 학생 이름이 키이고 그 학생의 점수가 값입니다.</p>
 *
 * <p>toMap의 첫 함수는 키, 둘째 함수는 값을 정합니다. 같은 이름이 반복될 경우 Integer::max로 큰 점수를 남깁니다.</p>
 *
 * <p>LinkedHashMap::new는 결과를 담을 맵을 새로 만드는 생성자 참조이며 입력 순서를 유지하도록 지정합니다.</p>
 *
 * <p>joining은 여러 이름을 하나의 문자열로 연결합니다. 쉼표는 이름 사이에, 대괄호는 전체 문자열 앞뒤에 붙습니다.</p>
 */
public class ExampleV3 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>여학생 이름 리스트를 수집합니다.</li>
     * <li>남학생 이름을 키, 점수를 값으로 하는 맵을 수집합니다.</li>
     * <li>전체 이름을 구분자가 있는 문자열 하나로 연결합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = SampleStudents.create();
        List<String> femaleNames = students.stream()
                .filter(student -> student.getGender() == Gender.FEMALE)
                .map(Student::getName)
                .collect(Collectors
                .toList());
        System.out.println("여학생 이름: " + femaleNames); // [이자바, 나미녀]
        // toMap은 키 함수와 값 함수를 받습니다. 같은 키가 있으면 합치는 규칙도 필요합니다.
        // 여기서는 이름이 서로 다릅니다. 중복 사례는 CollectorPitfallsExample에서 다룹니다.
        Map<String, Integer> maleScores = students.stream()
                .filter(student -> student.getGender() == Gender.MALE)
                .collect(Collectors.toMap(Student::getName, Student::getGrade,
                        Integer::max, LinkedHashMap::new));
        System.out.println("남학생 점수: " + maleScores); // {김코딩=100, 박해커=80}
        // LinkedHashMap은 입력 순서로 키를 순회합니다. 기본 toMap은 맵 순서를 보장하지 않습니다.
        String joined = students.stream()
                .map(Student::getName)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("이름 연결: " + joined);
    }
}
