package intermediateoperator;

/**
 * 성별과 나이로 회원을 선택한 뒤 이름을 추출하기 위한 데이터입니다.
 *
 * <p>이름과 나이는 문자열·숫자로 저장하지만 성별은 Gender 객체로 저장합니다.</p>
 *
 * <p>member.getGender()는 Gender 객체를, 그 객체의 getGender()는 실제 성별 문자열을 반환합니다.</p>
 *
 * <p>따라서 member.getGender().getGender()는 두 객체를 차례로 따라가 성별을 읽는 표현입니다.</p>
 *
 * <p>회원의 나이는 조건 검사에, 회원의 이름은 최종 결과 리스트를 만드는 데 사용합니다.</p>
 */
public class Member {
    /**
     * 회원 이름을 저장합니다.
     */
    private String name;
    /**
     * 회원 나이을 저장합니다.
     */
    private int age;
    /**
     * 회원 성별을 담은 Gender 객체을 저장합니다.
     */
    private Gender gender;

    /**
     * 전달받은 값으로 회원을 생성합니다.
     *
     * @param name 회원 이름
     * @param age 회원 나이
     * @param gender 회원 성별을 담은 Gender 객체
     */
    public Member(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
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
     * 회원 나이을 읽습니다.
     *
     * @return 현재 객체에 저장된 회원 나이
     */
    public int getAge() {
        return age;
    }

    /**
     * 회원 성별을 담은 Gender 객체을 읽습니다.
     *
     * @return 현재 객체에 저장된 회원 성별을 담은 Gender 객체
     */
    public Gender getGender() {
        return gender;
    }
}
