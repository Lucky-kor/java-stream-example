package streambasic.v2;

import java.util.List;
import java.util.OptionalDouble;

/**
 * 반복문으로 풀었던 평균 문제를 스트림의 세 단계로 다시 표현합니다.
 *
 * <p>filter는 남자 회원만 남기지만 여전히 요소의 타입은 Member입니다.</p>
 *
 * <p>mapToInt(Member::getAge)는 회원 객체를 나이 숫자로 바꿉니다. 이후 단계의 요소는 int입니다.</p>
 *
 * <p>average는 최종 연산이며 결과 타입은 double 자체가 아닌 OptionalDouble입니다.</p>
 *
 * <p>OptionalDouble은 평균 숫자가 있을 수도, 계산 대상이 없어 없을 수도 있다는 뜻입니다.</p>
 *
 * <p>orElse(0.0)는 값이 없는 경우 표시할 기본값을 정합니다. 0.0이 실제 계산된 평균이라는 뜻은 아닙니다.</p>
 *
 * <p>남자 회원 평균은 30.0이지만 100세 초과 회원의 평균은 값이 없습니다.</p>
 *
 * <p>다른 계산을 할 때는 members.stream()을 다시 호출해 새 처리 통로를 만듭니다.</p>
 */
public class StreamPracticeV2 {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>남자 회원만 선택해 나이 평균 30.0을 구합니다.</li>
     * <li>100세 초과 회원이 없어 OptionalDouble이 비어 있는지 확인합니다.</li>
     * <li>새 스트림으로 두 배 나이의 합 188을 구하고 원본 회원을 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<Member> members = StreamPractice.sampleMembers();
        OptionalDouble average = members.stream()
                .filter(member -> "남".equals(member.getGender()))
                .mapToInt(Member::getAge) // Member → int; 이후 숫자 전용 연산 사용
                .average();              // 대상이 없으면 OptionalDouble.empty()
        System.out.println("남자 회원 평균 나이: " + average
                .orElse(0.0)); // 30.0

        OptionalDouble missingAverage = members.stream()
                .filter(member -> member.getAge() > 100)
                .mapToInt(Member::getAge)
                .average();
        System.out.println("100세 초과 회원 존재: " + missingAverage
                .isPresent()); // false
        System.out.println("빈 평균의 표시용 기본값: " + missingAverage
                .orElse(0.0));
        // 기본값 0.0은 실제 평균이 0이라는 뜻이 아닙니다. 요구사항에 따라
        // 값 없음 자체를 표시하거나 예외를 던지는 편이 적절할 수도 있습니다.
        int doubledAgeSum = members.stream()
                .mapToInt(member -> member.getAge() * 2)
                .sum();
        System.out.println("나이를 두 배로 변환한 합계: " + doubledAgeSum); // 188
        System.out.println("원본 회원: " + members);
    }
}
