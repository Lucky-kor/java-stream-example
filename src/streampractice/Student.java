package streampractice;

/**
 * 종합 실습에서 사용하는 이름과 점수로 구성된 학생 데이터입니다.
 *
 * <p>학생 한 명을 new Student("민지a", 85)처럼 만들고 리스트에 여러 학생을 담습니다.</p>
 *
 * <p>getScore는 통과 여부와 순위를 정할 점수를, getName은 이름 수집과 동점 정렬에 사용할 이름을 반환합니다.</p>
 *
 * <p>Student::getScore는 student -> student.getScore()와 같은 메서드 참조입니다.</p>
 *
 * <p>toString을 재정의해 학생 객체를 출력할 때 클래스 주소 형태 대신 이름과 점수가 보이게 합니다.</p>
 */
public class Student {

    /**
     * 학생 이름을 저장합니다.
     */
    private String name;
    /**
     * 학생 점수을 저장합니다.
     */
    private int score;

    /**
     * 전달받은 값으로 학생을 생성합니다.
     *
     * @param name 학생 이름
     * @param score 학생 점수
     */
    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * 학생 이름을 읽습니다.
     *
     * @return 현재 객체에 저장된 학생 이름
     */
    public String getName() {
        return name;
    }
    /**
     * 학생 점수을 읽습니다.
     *
     * @return 현재 객체에 저장된 학생 점수
     */
    public int getScore() {
        return score;
    }

    /**
     * 객체를 출력할 때 사람이 읽을 수 있는 설명으로 바꿉니다.
     *
     * @return 이 객체의 주요 필드를 나타내는 문자열
     */
    @Override
    public String toString() {
        return name + " (" + score + "점)";
    }
}
