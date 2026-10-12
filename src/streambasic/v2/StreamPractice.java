package streambasic.v2;

import java.util.List;

/**
 * 남자 회원의 평균 나이를 반복문으로 계산하고 계산 대상이 없는 경우도 처리합니다.
 *
 * <p>Member는 회원 한 명의 이름, 성별, 나이를 묶은 객체입니다. getter로 필요한 값을 꺼냅니다.</p>
 *
 * <p>조건에 맞는 회원을 만날 때마다 나이 합계와 회원 수를 함께 늘립니다.</p>
 *
 * <p>37세와 23세 두 회원의 평균은 (37 + 23) / 2 = 30.0입니다.</p>
 *
 * <p>정수끼리 나누면 소수 부분이 사라질 수 있어 나누기 전에 합계를 double로 바꿉니다.</p>
 *
 * <p>대상이 0명이면 나눗셈을 하지 않고 계산 대상 없음이라고 출력합니다. 평균 0과 대상 없음은 다릅니다.</p>
 *
 * <p>sampleMembers는 회원 목록을 만들어 반환하는 메서드입니다. 다음 스트림 풀이에서도 같은 데이터를 사용합니다.</p>
 */
public class StreamPractice {
    // 두 풀이에서 같은 데이터를 사용해야 결과를 올바르게 비교할 수 있습니다.
    /**
     * 두 평균 풀이에서 공통으로 사용할 회원 데이터를 준비합니다.
     *
     * <p>남자 회원은 37세와 23세입니다. 매 호출마다 새 목록을 반환합니다.</p>
     *
     * @return 이름, 성별, 나이가 저장된 회원 여섯 명의 리스트
     */
    static List<Member> sampleMembers() {
        return List.of(new Member("김러키", "여", 7), new Member("김라떼", "여", 6),
                new Member("바닐라", "여", 4), new Member("한여름", "여", 17),
                new Member("이정민", "남", 37), new Member("김자바", "남", 23));
    }

    /**
     * 예제를 실행하고 단계별 결과를 콘솔에 출력합니다.
     *
     * <ol>
     * <li>공통 회원 목록에서 남자 회원의 평균을 출력합니다.</li>
     * <li>빈 회원 목록으로 같은 계산을 시도해 대상 없음 처리를 확인합니다.</li>
     * </ol>
     *
     * @param args 실행할 때 전달하는 문자열 배열이며 이 예제에서는 사용하지 않습니다.
     */
    public static void main(String[] args) {
        printAverage(sampleMembers(), "남"); // (37 + 23) / 2 = 30.0
        printAverage(List.of(), "남");       // 계산 대상 없음
    }

    /**
     * 지정한 성별 회원의 평균 나이를 출력합니다.
     *
     * <p>같은 조건에 맞는 회원의 나이 합과 인원 수를 센 뒤 나눕니다.
     * 계산 대상이 없으면 나누지 않고 안내를 출력합니다.</p>
     *
     * @param members 평균을 계산할 회원 목록
     * @param gender 선택할 성별 문자열
     */
    private static void printAverage(List<Member> members, String gender) {
        int sum = 0;
        int count = 0;
        for (Member member : members) {
            if (gender.equals(member.getGender())) {
                sum += member.getAge();
                count++;
            }
        }
        // 0명일 때 0 / 0.0은 NaN입니다. 나누기 전에 빈 대상을 처리합니다.
        if (count == 0) {
            System.out.println("평균 나이: 계산 대상 없음");
            return;
        }
        // 정수 나눗셈으로 소수부를 잃지 않도록 나누기 전에 double로 변환합니다.
        System.out.println("평균 나이: " + (double) sum / count);
    }
}
