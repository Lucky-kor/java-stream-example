package sortedinterface;

import java.util.Comparator;
import java.util.List;

/**
 * 객체의 기본 정렬과 작업마다 지정하는 정렬을 비교하고 동점 처리 방법을 배웁니다.
 *
 * <p>숫자와 달리 Student 객체에는 이름과 나이가 함께 있으므로 무엇을 기준으로 정렬할지 정해야 합니다.</p>
 *
 * <p>Comparable은 클래스가 가진 기본 기준이고 Comparator는 현재 작업에 전달하는 별도 기준입니다.</p>
 *
 * <p>sorted()는 Student.compareTo를 사용합니다. 이 클래스의 기본 기준은 나이 내림차순입니다.</p>
 *
 * <p>Comparator.comparing(Student::getName)은 학생에게서 이름을 꺼내 이름을 비교하라는 뜻입니다.</p>
 *
 * <p>comparingInt(Student::getAge).reversed()는 나이 내림차순, thenComparing은 동갑일 때 사용할 두 번째 기준입니다.</p>
 *
 * <p>8세인 가학생과 김러키는 이름 오름차순으로 가학생이 먼저 나옵니다.</p>
 *
 * <p>마지막 중첩 목록 예제는 정렬과 별도로 flatMap의 펼치기를 복습합니다.</p>
 */
public class Example {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>기본 나이 정렬과 이름 기준 정렬을 비교합니다.</li>
     * <li>나이가 같으면 이름으로 순서를 정하는 기준을 적용합니다.</li>
     * <li>원본 순서를 확인하고 중첩 언어 목록을 펼칩니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = List.of(new Student("이정민", 30), new Student("김러키", 8),
                new Student("김라떼", 7), new Student("바닐라", 6), new Student("한여름", 48),
                new Student("가학생", 8)); // 나이가 같은 경우도 비교합니다.
        // 인자 없는 sorted는 Student.compareTo를 사용합니다. 여기서는 나이 내림차순.
        System.out.println("기본 정렬(나이 내림차순): " + students.stream()
                .sorted()
                .toList());
        Comparator<Student> byName = Comparator.comparing(Student::getName);
        System.out.println("이름 오름차순: " + students.stream()
                .sorted(byName)
                .toList());
        // 나이 내림차순 다음 이름 오름차순: reversed의 위치에 주의합니다.
        Comparator<Student> byAgeThenName = Comparator.comparingInt(Student::getAge)
                .reversed()
                .thenComparing(Student::getName);
        System.out.println("나이 내림차순, 동갑은 이름순: " + students.stream()
                .sorted(byAgeThenName)
                .toList());
        System.out.println("원본 순서: " + students);
        List<List<String>> languages = List.of(List.of("Java", "SQL"), List.of("Python"), List.of());
        System.out.println("중첩 목록 펼치기: " + languages.stream()
                .flatMap(List::stream)
                .toList());
    }
}
