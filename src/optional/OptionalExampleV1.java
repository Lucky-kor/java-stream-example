package optional;

import terminaloperator.SampleStudents;
import terminaloperator.Student;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Optional에 값이 있는 경우와 없는 경우를 직접 만들고 안전하게 사용하는 방법을 배웁니다.
 *
 * <p>{@code Optional<Student>}는 Student가 있을 수도 없을 수도 있음을 표현합니다. Optional 자체를 null로 두는 것과 다릅니다.</p>
 *
 * <p>of는 null이 아닌 값을 담고, ofNullable은 null일 때 빈 상자를 만들며, empty는 빈 상자를 직접 만듭니다.</p>
 *
 * <p>ifPresent는 값이 있을 때만 동작을 실행합니다. filter와 map 역시 값이 있을 때만 검사하고 변환합니다.</p>
 *
 * <p>orElse는 기본값을 받아 결과를 꺼냅니다. 메서드 인자를 먼저 계산하므로 기본값 생성 함수는 값이 있어도 실행됩니다.</p>
 *
 * <p>orElseGet은 함수 자체를 전달하고 상자가 비어 있을 때만 그 함수를 실행합니다.</p>
 *
 * <p>orElseThrow는 꼭 필요한 값이 없을 때 예외로 실패를 알립니다. 여기서는 catch로 메시지를 출력해 실패 원인을 관찰합니다.</p>
 *
 * <p>스트림 filter는 여러 학생을 선택하지만 Optional filter는 이미 상자에 있는 한 학생만 검사한다는 차이에 주의하세요.</p>
 */
public class OptionalExampleV1 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>of, ofNullable, empty로 값 있음과 없음을 만듭니다.</li>
     * <li>조건 검사와 이름 변환 후 기본값으로 결과를 꺼냅니다.</li>
     * <li>기본값 함수 실행 로그로 orElse와 orElseGet을 비교합니다.</li>
     * <li>검색 결과가 없는 경우 사용자 메시지의 예외를 처리합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = SampleStudents.create();
        Optional<Student> present = Optional.of(students.get(0)); // null을 넣으면 NPE
        Student nullableStudent = null;
        Optional<Student> nullable = Optional.ofNullable(nullableStudent); // null이면 empty
        Optional<Student> empty = Optional.empty();
        System.out.println("of 값 존재: " + present
                .isPresent()); // true
        System.out.println("ofNullable(null): " + nullable);     // Optional.empty
        System.out.println("empty: " + empty);

        present
                .ifPresent(student -> System.out.println("존재할 때 출력: " + student.getName()));
        System.out.println("90점 이상 이름: " + present
                .filter(s -> s.getGrade() >= 90)
                .map(Student::getName)
                .orElse("없음")); // 김코딩
        // get()은 빈 Optional에서 예외를 던집니다. 기본값이나 예외 정책을 명시합니다.
        System.out.println("빈 이름의 기본값: " + empty
                .map(Student::getName)
                .orElse("미등록"));

        // orElse의 인자는 값이 있어도 먼저 평가됩니다. orElseGet은 값이 없을 때만 호출합니다.
        Optional<String> existingName = Optional.of("김코딩");
        System.out.println("orElse 결과: " + existingName
                .orElse(createDefaultName()));
        System.out.println("orElseGet 결과: " + existingName
                .orElseGet(OptionalExampleV1::createDefaultName));
        System.out.println("빈 값 orElseGet: " + Optional.<String>empty()
                .orElseGet(OptionalExampleV1::createDefaultName));

        // 반드시 있어야 하는 데이터라면 예외를 선택합니다. 이 예제는 실패를
        // 관찰하는 것이 목적이므로 catch하여 나머지 실행도 정상적으로 마칩니다.
        try {
            students.stream()
                    .filter(student -> student.getGrade() > 100)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("100점 초과 학생이 없습니다."));
        } catch (NoSuchElementException exception) {
            System.out.println("검색 실패 처리: " + exception.getMessage());
        }
    }

    /**
     * 값이 없을 때 사용할 이름을 생성하며 호출 여부도 출력합니다.
     *
     * <p>반환 문자열보다 실행 로그를 관찰하는 것이 목적입니다.
     * orElse는 값이 있어도 호출하고 orElseGet은 비어 있을 때만 호출하는지 비교하세요.</p>
     *
     * @return 기본 표시 이름인 미등록
     */
    private static String createDefaultName() {
        System.out.println("기본 이름 생성 함수 실행");
        return "미등록";
    }
}
