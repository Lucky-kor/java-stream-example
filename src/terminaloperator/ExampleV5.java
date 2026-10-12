package terminaloperator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 찾은 학생이 없을 가능성을 처리하고 이름을 배열에 담는 예제입니다.
 *
 * <p>findFirst와 findAny는 학생 객체를 직접 반환하지 않고 {@code Optional<Student>}를 반환합니다.</p>
 *
 * <p>Optional은 학생이 들어 있을 수도, 검색 결과가 없어 비어 있을 수도 있는 결과 상자라고 이해하면 됩니다.</p>
 *
 * <p>List에는 입력 순서가 있으므로 첫 여학생은 이자바입니다. findAny는 반드시 첫 여학생을 고른다는 규칙이 없습니다.</p>
 *
 * <p>Optional의 map(Student::getName)은 학생이 있을 때만 이름을 꺼내 {@code Optional<String>}으로 바꿉니다.</p>
 *
 * <p>orElse("없음")으로 상자 안 이름을 꺼내고, 비어 있으면 대신 없음이라는 문자열을 받습니다.</p>
 *
 * <p>toArray(String[]::new)는 남학생 이름을 문자열 배열에 모읍니다. 배열 생성 방법을 전달해 String[] 타입을 지정합니다.</p>
 */
public class ExampleV5 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>첫 여학생과 여학생 중 한 명의 이름을 안전하게 꺼냅니다.</li>
     * <li>100점 초과 학생 검색이 실패하면 없음이라고 출력합니다.</li>
     * <li>남학생 이름을 문자열 배열로 수집합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = SampleStudents.create();
        // List는 만나는 순서가 있으므로 첫 여학생은 이자바입니다.
        Optional<Student> first = students.stream()
                .filter(student -> student.getGender() == Gender.FEMALE)
                .findFirst();
        System.out.println("첫 여학생: " + first
                .map(Student::getName)
                .orElse("없음"));
        // findAny는 어떤 요소를 선택할지 보장하지 않습니다. 병렬 처리에서도
        // 반드시 첫 요소가 필요하면 findFirst를 사용합니다.
        Optional<Student> any = students.stream()
                .filter(student -> student.getGender() == Gender.FEMALE)
                .findAny();
        System.out.println("여학생 중 한 명: " + any
                .map(Student::getName)
                .orElse("없음"));
        Optional<Student> missing = students.stream()
                .filter(s -> s.getGrade() > 100)
                .findFirst();
        System.out.println("100점 초과 학생: " + missing
                .map(Student::getName)
                .orElse("없음"));
        // orElse(null) 후 getName()을 호출하면 빈 결과에서 NPE가 발생할 수 있습니다.
        String[] maleNames = students.stream()
                .filter(s -> s.getGender() == Gender.MALE)
                .map(Student::getName)
                .toArray(String[]::new);
        System.out.println("남학생 이름 배열: " + Arrays.toString(maleNames));
        // 인자 없는 toArray()는 Object[]를 반환합니다. 생성자 참조로 String[] 타입을 지정합니다.
    }
}
