package datareport;

import java.util.List;

/**
 * 보고서 예제에 사용할 학생 여섯 명을 준비합니다.
 *
 * <p>학년은 1, 2, 3이고 전체 점수 합계는 538입니다. 여러 보고서에서 같은 입력을 사용하므로 집계 결과를 비교할 수 있습니다.</p>
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
        return List.of(new Student("홍길동", "남", 1, 95),
                new Student("강감찬", "남", 2, 80), new Student("이순신", "남", 1, 85),
                new Student("신사임당", "여", 2, 90), new Student("유관순", "여", 3, 100),
                new Student("문성실", "여", 1, 88));
    }
}
