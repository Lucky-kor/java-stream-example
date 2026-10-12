package datareport;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 참·거짓 두 그룹으로 나누는 분할과 여러 기준으로 나누는 그룹화를 비교합니다.
 *
 * <p>partitioningBy는 조건을 만족하면 true, 아니면 false라는 키 아래 요소를 모읍니다.</p>
 *
 * <p>90점 이상 조건은 >= 90입니다. 점수가 정확히 90인 신사임당도 통과 그룹에 들어갑니다.</p>
 *
 * <p>true 그룹은 홍길동, 신사임당, 유관순이며 false 그룹은 강감찬, 이순신, 문성실입니다.</p>
 *
 * <p>partitioningBy는 빈 입력에서도 true와 false 키를 만들고 각각 빈 리스트를 담습니다.</p>
 *
 * <p>다중 groupingBy는 먼저 학년으로 나누고 각 학년 안에서 다시 성별로 나누는 처리입니다.</p>
 *
 * <p>groupingBy는 입력에 있는 키만 만듭니다. 3학년에 남학생이 없으면 남이라는 그룹을 자동으로 만들지 않습니다.</p>
 */
public class ExampleV2 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>90점 이상과 미만으로 분할한 학생과 이름을 확인합니다.</li>
     * <li>빈 입력에서도 두 분할 그룹이 존재하는지 확인합니다.</li>
     * <li>학년별 그룹 안에서 다시 성별로 학생을 나눕니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = SampleStudents.create();
        // 90점 '이상'이므로 >=입니다. 90점인 신사임당도 true 그룹에 들어갑니다.
        Map<Boolean, List<Student>> partition = students.stream()
                .collect(Collectors.partitioningBy(student -> student.getScore() >= 90));
        System.out.println("90점 이상 학생: " + partition.get(true));
        System.out.println("90점 미만 학생: " + partition.get(false));
        Map<Boolean, List<String>> names = students.stream()
                .collect(Collectors.partitioningBy(
                student -> student.getScore() >= 90,
                Collectors.mapping(Student::getName, Collectors
                        .toList())));
        System.out.println("통과 이름: " + names.get(true)); // [홍길동, 신사임당, 유관순]
        System.out.println("미통과 이름: " + names.get(false)); // [강감찬, 이순신, 문성실]
        // partitioningBy는 한쪽에 데이터가 없어도 true와 false 키를 모두 갖습니다.
        Map<Boolean, List<Student>> emptyPartition = List.<Student>of().stream()
                .collect(Collectors.partitioningBy(student -> student.getScore() >= 90));
        System.out.println("빈 입력 true 그룹: " + emptyPartition.get(true)); // []
        System.out.println("빈 입력 false 그룹: " + emptyPartition.get(false)); // []

        Map<Integer, Map<String, List<Student>>> byGradeAndGender = students.stream()
                .collect(Collectors.groupingBy(Student::getGrade, TreeMap::new,
                        Collectors.groupingBy(Student::getGender, TreeMap::new, Collectors
                                .toList())));
        System.out.println("학년 → 성별 학생: " + byGradeAndGender);
        // groupingBy는 실제로 발견한 키만 만듭니다. 3학년에는 남 그룹이 없습니다.
    }
}
