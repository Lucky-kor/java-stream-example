package intermediateoperator;

import java.util.Comparator;
import java.util.List;

/**
 * 중간 연산이 요소를 선택하고 변환하는 과정과 연결 순서의 영향을 관찰합니다.
 *
 * <p>중간 연산은 다음 단계에서 처리할 스트림을 반환하므로 점(.)으로 다른 연산을 이어 붙일 수 있습니다.</p>
 *
 * <p>filter는 조건에 맞는 동물 이름만 남기고 map(String::length)는 이름을 글자 수로 바꿉니다.</p>
 *
 * <p>distinct는 같은 값을 한 번만 남기고 sorted는 남은 값을 정렬합니다.</p>
 *
 * <p>skip(1)은 현재 순서의 첫 요소를 제외하고 limit(3)은 그다음 최대 세 요소만 선택합니다.</p>
 *
 * <p>5, 1, 4, 2, 3을 정렬한 뒤 세 개 고르면 1, 2, 3이지만 먼저 세 개 고른 뒤 정렬하면 1, 4, 5입니다.</p>
 *
 * <p>정렬은 입력을 모아 순서를 정하므로 filter와 map처럼 항상 요소 하나가 즉시 통과하는 것은 아닙니다.</p>
 *
 * <p>마지막 회원 예제에서는 성별과 나이를 검사한 뒤 회원 객체에서 이름만 꺼냅니다.</p>
 */
public class Main {
    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>문자열 정렬, 길이 변환, 중복 제거를 차례로 비교합니다.</li>
     * <li>skip과 limit으로 선택하는 범위를 확인합니다.</li>
     * <li>정렬과 limit 순서를 바꾸고 회원 조건 선택에도 적용합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        List<String> animals = List.of("Tiger", "Lion", "Monkey", "Duck", "Horse", "Cow", "Lion");
        System.out.println("오름차순: " + animals.stream()
                .sorted()
                .toList());
        System.out.println("내림차순: " + animals.stream()
                .sorted(Comparator.reverseOrder())
                .toList());
        // 정수 비교에서도 b - a 대신 Integer.compare 또는 Comparator를 사용합니다.
        // 뺄셈 비교는 큰 값에서 오버플로우로 순서가 뒤집힐 수 있습니다.
        System.out.println("길이 4 이상 → 길이 → 중복 제거: " + animals.stream()
                .filter(animal -> animal.length() >= 4)
                .map(String::length)
                .distinct()
                .sorted()
                .toList()); // [4, 5, 6]
        // distinct는 equals를 기준으로 중복을 판단합니다. 사용자 객체에서는
        // equals/hashCode를 정의하지 않으면 내용이 같아도 별개로 남을 수 있습니다.
        System.out.println("중복 제거 후 두 번째부터 3개: " + animals.stream()
                .distinct()
                .skip(1)
                .limit(3)
                .toList()); // [Lion, Monkey, Duck]

        List<Integer> numbers = List.of(5, 1, 4, 2, 3);
        System.out.println("정렬 후 상위 3개: " + numbers.stream()
                .sorted()
                .limit(3)
                .toList()); // [1, 2, 3]
        System.out.println("처음 3개를 정렬: " + numbers.stream()
                .limit(3)
                .sorted()
                .toList()); // [1, 4, 5]
        // sorted는 상태를 유지하는 중간 연산입니다. 유한 입력을 모아 정렬한 뒤
        // 전달하므로 filter/map처럼 언제나 요소 하나씩 끝까지 흐르는 것은 아닙니다.
        Gender female = new Gender("여");
        List<Member> members = List.of(new Member("민지", 20, female), new Member("수현", 17, female));
        System.out.println("성인 여자 회원 이름: " + members.stream()
                .filter(member -> "여".equals(member.getGender().getGender()))
                .filter(member -> member.getAge() >= 18)
                .map(Member::getName)
                .toList());
    }
}
