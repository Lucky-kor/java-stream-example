package datareport;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * 학생 점수의 합계, 평균, 최솟값, 최댓값과 한 번에 얻는 통계를 비교합니다.
 *
 * <p>학생 객체 자체를 더할 수는 없으므로 mapToInt로 점수만 꺼낸 다음 숫자 연산을 적용합니다.</p>
 *
 * <p>전체 합계는 538, 평균은 약 89.67입니다. 남학생의 최고 점수는 95, 최저 점수는 80입니다.</p>
 *
 * <p>sum은 빈 입력에 0을 반환하지만 average, min, max는 관측값이 없어 빈 Optional을 반환합니다.</p>
 *
 * <p>reduce로 더해도 같은 합을 얻습니다. 곱셈에서는 1부터 누적하고 범위가 커질 수 있어 long을 사용합니다.</p>
 *
 * <p>summarizingInt는 인원, 합계, 평균, 최솟값, 최댓값을 IntSummaryStatistics라는 결과 객체에 함께 담습니다.</p>
 *
 * <p>빈 통계 객체의 min과 max는 실제 점수가 아니라 비교를 시작하기 위한 초기값입니다. 인원이 0인지 먼저 확인하세요.</p>
 *
 * <p>Math::multiplyExact는 곱셈 결과가 long 범위마저 넘으면 예외를 발생시켜 범위 초과를 알리는 메서드 참조입니다.</p>
 */
public class ExampleV3 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>기본형 스트림으로 전체 점수와 남학생 점수를 집계합니다.</li>
     * <li>reduce의 합·곱과 여러 통계를 함께 구한 결과를 확인합니다.</li>
     * <li>빈 숫자 스트림과 빈 통계 객체의 차이를 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Student> students = SampleStudents.create();
        System.out.println("점수 합계: " + students.stream()
                .mapToInt(Student::getScore)
                .sum()); // 538
        System.out.println("점수 평균: " + students.stream()
                .mapToInt(Student::getScore)
                .average()
                .orElse(0.0)); // 약 89.67
        System.out.println("남학생 최고 점수: " + students.stream()
                .filter(s -> "남".equals(s.getGender()))
                .mapToInt(Student::getScore)
                .max()
                .orElse(-1)); // 95
        System.out.println("남학생 최저 점수: " + students.stream()
                .filter(s -> "남".equals(s.getGender()))
                .mapToInt(Student::getScore)
                .min()
                .orElse(-1)); // 80
        // grade는 이 패키지에서 '학년'입니다. 점수 합계는 score를 사용해야 합니다.
        int sumWithReduce = students.stream()
                .map(Student::getScore)
                .reduce(0, Integer::sum);
        System.out.println("reduce 점수 합계: " + sumWithReduce); // 538
        // 곱셈의 항등값은 1입니다. 누적 결과가 커질 수 있어 곱하기 전에 long으로 변환합니다.
        long product = students.stream()
                .filter(s -> "남".equals(s.getGender()))
                .mapToLong(Student::getScore)
                .reduce(1L, Math::multiplyExact);
        System.out.println("남학생 점수 곱: " + product); // 646000
        // multiplyExact는 long 범위도 넘으면 예외를 던집니다. long도 무제한은 아닙니다.

        IntSummaryStatistics stats = students.stream()
                .collect(Collectors.summarizingInt(Student::getScore));
        System.out.println("통계 인원: " + stats.getCount()); // 6
        System.out.println("통계 합계: " + stats.getSum());   // 538 (long)
        System.out.println("통계 평균: " + stats.getAverage());
        System.out.println("통계 최솟값: " + stats.getMin()); // 80
        System.out.println("통계 최댓값: " + stats.getMax()); // 100

        System.out.println("빈 int 스트림 합계: " + List.<Student>of().stream()
                .mapToInt(Student::getScore)
                .sum());
        OptionalDouble emptyAverage = List.<Student>of().stream()
                .mapToInt(Student::getScore)
                .average();
        System.out.println("빈 평균: " + emptyAverage); // OptionalDouble.empty
        IntSummaryStatistics emptyStats = new IntSummaryStatistics();
        System.out.println("빈 통계: " + emptyStats);
        // 빈 통계: count=0, sum=0, average=0, min=Integer.MAX_VALUE, max=Integer.MIN_VALUE.
        // min/max를 실제 관측값으로 사용하기 전에 count > 0인지 확인해야 합니다.
    }
}
