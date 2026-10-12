package terminaloperator;

/**
 * 최종 연산 예제에서 사용할 성별을 정해진 두 상수로 표현합니다.
 *
 * <p>enum은 사용할 수 있는 값을 미리 이름 붙여 나열한 타입입니다.
 * 여기서는 문자열을 직접 쓰는 대신 Gender.MALE과 Gender.FEMALE 중 하나를 사용합니다.</p>
 *
 * <p>정해진 상수이므로 ==로 비교할 수 있습니다. 임의의 문자열 내용 비교에는 equals를 사용해야 합니다.</p>
 */
public enum Gender {
    /** 이 예제의 남학생을 나타내는 상수입니다. */
    MALE,

    /** 이 예제의 여학생을 나타내는 상수입니다. */
    FEMALE
}
