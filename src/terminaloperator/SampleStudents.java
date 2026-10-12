package terminaloperator;

import java.util.List;

/**
 * 최종 연산과 Optional 예제에 사용할 학생 네 명을 준비합니다.
 *
 * <p>점수는 100, 80, 90, 60이며 남학생 두 명과 여학생 두 명입니다. 같은 입력으로 서로 다른 연산의 결과를 비교합니다.</p>
 *
 * <p>static 메서드는 new로 이 클래스를 만들지 않고 SampleStudents.create()처럼 클래스 이름으로 호출합니다.</p>
 */
public final class SampleStudents {
    /**
     * 데이터 생성 메서드만 사용하는 보조 클래스이므로 외부에서 객체를 만들지 못하게 합니다.
     */
    private SampleStudents() {
    }

    /**
     * 예제마다 사용할 새로운 학생 객체와 목록을 만듭니다.
     *
     * <p>List.of로 만든 목록은 요소를 추가하거나 삭제할 수 없습니다. 목록 수정 제한과 학생 객체의 수정 가능 여부는 별개의 문제입니다.</p>
     *
     * @return 정해진 순서의 학생 데이터를 담은 리스트
     */
    public static List<Student> create() {
        return List.of(new Student("김코딩", 100, Gender.MALE),
                new Student("박해커", 80, Gender.MALE),
                new Student("이자바", 90, Gender.FEMALE),
                new Student("나미녀", 60, Gender.FEMALE));
    }
}
