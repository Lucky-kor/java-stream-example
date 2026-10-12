package terminaloperator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 같은 이름이 두 번 나오는 데이터와 수정할 수 없는 리스트를 안전하게 처리합니다.
 *
 * <p>Map에는 같은 키로 두 값을 따로 저장할 수 없습니다. 김코딩의 80점과 95점을 넣으려면 처리 규칙이 필요합니다.</p>
 *
 * <p>두 인자 toMap에는 중복 처리 규칙이 없으므로 예외가 발생합니다. 예제는 이를 catch해 설명을 출력합니다.</p>
 *
 * <p>Integer::max를 병합 함수로 지정하면 두 점수 중 높은 95를 남깁니다. 병합은 중복 값을 하나로 결합한다는 뜻입니다.</p>
 *
 * <p>둘 다 보존해야 하면 groupingBy로 같은 이름 아래 학생 리스트를 만듭니다.</p>
 *
 * <p>Stream.toList 결과에는 add로 요소를 추가할 수 없습니다. Collectors.toList도 수정 가능하다고 보장하지 않습니다.</p>
 *
 * <p>추가할 목록이 필요하면 toCollection(ArrayList::new)로 사용할 리스트 종류를 명시합니다.</p>
 *
 * <p>리스트를 수정하지 못한다는 것과 리스트 안 학생 객체의 내용이 바뀌지 않는다는 것은 별개입니다.</p>
 */
public class CollectorPitfallsExample {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>중복 키가 만드는 예외를 확인하고 최고 점수를 남기는 규칙을 적용합니다.</li>
     * <li>동명이인을 모두 보존하는 그룹화 결과와 비교합니다.</li>
     * <li>수정 불가 리스트와 ArrayList의 add 결과를 비교합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = List.of(new Student("김코딩", 80, Gender.MALE),
                new Student("김코딩", 95, Gender.MALE), new Student("이자바", 90, Gender.FEMALE));
        // 이름은 실제 서비스에서 고유 식별자가 아닐 수 있습니다.
        try {
            students.stream()
                    .collect(Collectors.toMap(Student::getName, Student::getGrade));
        } catch (IllegalStateException exception) {
            System.out.println("중복 키 실패: 김코딩이라는 키가 두 번 나옵니다.");
        }
        // 병합 함수는 기존 값과 새 값을 받아 최종 값을 정합니다. 여기서는 높은 점수 선택.
        Map<String, Integer> highest = students.stream()
                .collect(Collectors.toMap(
                Student::getName, Student::getGrade, Integer::max, LinkedHashMap::new));
        System.out.println("동명이인 최고 점수: " + highest); // {김코딩=95, 이자바=90}
        // 둘 다 보존해야 하면 Map<String, List<Student>>로 그룹화하는 편이 맞습니다.
        System.out.println("동명이인 전체 보존: " + students.stream()
                .collect(Collectors.groupingBy(
                Student::getName, LinkedHashMap::new, Collectors
                        .toList())));

        List<String> readOnly = students.stream()
                .map(Student::getName)
                .toList();
        try {
            readOnly.add("새 학생");
        } catch (UnsupportedOperationException exception) {
            System.out.println("Stream.toList() 결과는 요소 추가·삭제·교체가 불가능합니다.");
        }
        // Collectors.toList()는 수정 가능 여부를 계약으로 보장하지 않습니다.
        // 수정 가능한 ArrayList가 필요하면 생성자를 명시합니다.
        List<String> mutable = students.stream()
                .map(Student::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        mutable.add("새 학생");
        System.out.println("수정 가능한 목록: " + mutable);
        // 수정 불가 리스트도 내부 객체까지 불변으로 만드는 것은 아닙니다.
    }
}
