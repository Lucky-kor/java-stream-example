package terminaloperator;

import java.util.List;

/**
 * 조건을 만족하는지 true 또는 false로 답하는 세 가지 최종 연산을 비교합니다.
 *
 * <p>allMatch는 모든 요소가 조건을 만족하는지, anyMatch는 하나라도 만족하는지 검사합니다.</p>
 *
 * <p>noneMatch는 조건을 만족하는 요소가 하나도 없는지 검사합니다. 아무나 불만족하면 true라는 뜻은 아닙니다.</p>
 *
 * <p>현재 목록에는 남학생과 여학생이 있으므로 모두 여학생은 false, 여학생이 한 명 이상은 true입니다.</p>
 *
 * <p>가장 낮은 점수도 60이므로 모두 40점 초과와 40점 미만 학생 없음은 true입니다.</p>
 *
 * <p>빈 목록에서는 allMatch와 noneMatch가 true이고 anyMatch가 false입니다. 조건을 위반하는 요소도 만족하는 요소도 없기 때문입니다.</p>
 *
 * <p>모든 학생이 통과했다는 판단에 학생 존재까지 필요하면 목록이 비어 있지 않은지도 별도로 확인해야 합니다.</p>
 */
public class ExampleV4 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>실제 학생 목록에 전체·일부·없음 판정을 적용합니다.</li>
     * <li>빈 학생 목록에 같은 종류의 판정을 적용해 차이를 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = SampleStudents.create();
        // 결과가 결정되면 나머지 요소를 모두 검사할 필요가 없는 단락 평가 연산입니다.
        System.out.println("모두 여학생: " + students.stream()
                .allMatch(student -> student.getGender() == Gender.FEMALE)); // false
        System.out.println("모두 40점 초과: " + students.stream()
                .allMatch(student -> student.getGrade() > 40)); // true
        System.out.println("여학생이 한 명 이상: " + students.stream()
                .anyMatch(student -> student.getGender() == Gender.FEMALE)); // true
        System.out.println("40점 미만 학생 없음: " + students.stream()
                .noneMatch(student -> student.getGrade() < 40)); // true

        List<Student> empty = List.of();
        // 빈 집합에서는 조건을 어기는 요소도, 조건을 만족하는 요소도 없습니다.
        System.out.println("빈 목록 allMatch: " + empty.stream()
                .allMatch(s -> s.getGrade() >= 90)); // true
        System.out.println("빈 목록 anyMatch: " + empty.stream()
                .anyMatch(s -> s.getGrade() >= 90)); // false
        System.out.println("빈 목록 noneMatch: " + empty.stream()
                .noneMatch(s -> s.getGrade() >= 90)); // true
        // '학생이 존재하며 모두 통과'가 목적이면 !students.isEmpty() 조건도 필요합니다.
    }
}
