package intermediateoperator;

/**
 * 성별 문자열을 별도 객체로 감싼 기초 데이터 모델입니다.
 *
 * <p>new Gender("여")는 성별 문자열을 내부에 저장한 객체를 만듭니다.</p>
 *
 * <p>Member에는 이 객체를 저장하고 getGender로 안에 있는 문자열을 꺼냅니다.</p>
 *
 * <p>이 클래스는 객체의 getter를 이어 호출하는 사례를 보여 줍니다. 같은 이름의 terminaloperator.Gender enum과는 다른 타입입니다.</p>
 */
public class Gender {
    /**
     * 성별 문자열을 저장합니다.
     */
    private String gender;

    /**
     * 전달받은 값으로 성별 객체을 생성합니다.
     *
     * @param gender 성별 문자열
     */
    public Gender(String gender) {
        this.gender = gender;
    }

    /**
     * 성별 문자열을 읽습니다.
     *
     * @return 현재 객체에 저장된 성별 문자열
     */
    public String getGender() {
        return gender;
    }
}
