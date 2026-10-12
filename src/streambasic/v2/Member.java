package streambasic.v2;

/**
 * 반복문과 스트림 평균 계산에서 공통으로 사용하는 회원 데이터입니다.
 *
 * <p>회원 한 명의 이름, 성별, 나이를 객체 하나로 묶습니다. 성별은 이 예제에서 남 또는 여라는 문자열로 저장합니다.</p>
 *
 * <p>생성자는 new Member를 호출할 때 전달한 값을 필드에 저장합니다. getter는 저장한 값을 읽습니다.</p>
 *
 * <p>filter에서는 getGender로 조건을 검사하고 mapToInt에서는 getAge로 나이를 꺼냅니다.</p>
 *
 * <p>Member::getAge는 member -> member.getAge()를 짧게 표현한 메서드 참조입니다.</p>
 *
 * <p>toString은 원본 회원 목록을 확인할 때 이름과 성별, 나이가 읽기 쉽게 보이도록 합니다.</p>
 */
public class Member {
    /**
     * 회원 이름을 저장합니다.
     */
    private String name;
    /**
     * 회원 성별 문자열을 저장합니다.
     */
    private String gender;
    /**
     * 회원 나이을 저장합니다.
     */
    private int age;

    /**
     * 전달받은 값으로 회원을 생성합니다.
     *
     * @param name 회원 이름
     * @param gender 회원 성별 문자열
     * @param age 회원 나이
     */
    public Member(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    /**
     * 회원 이름을 읽습니다.
     *
     * @return 현재 객체에 저장된 회원 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 회원 성별 문자열을 읽습니다.
     *
     * @return 현재 객체에 저장된 회원 성별 문자열
     */
    public String getGender() {
        return gender;
    }

    /**
     * 회원 나이을 읽습니다.
     *
     * @return 현재 객체에 저장된 회원 나이
     */
    public int getAge() {
        return age;
    }

    /**
     * 객체를 출력할 때 사람이 읽을 수 있는 설명으로 바꿉니다.
     *
     * @return 이 객체의 주요 필드를 나타내는 문자열
     */
    @Override
    public String toString() {
        return "이름: " + name + ", 성별: " + gender + ", 나이: " + age;
    }
}
