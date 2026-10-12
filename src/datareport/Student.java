package datareport;

/**
 * 보고서 작성에 필요한 이름, 성별, 학년, 점수를 묶은 학생 데이터입니다.
 *
 * <p>grade는 학년이고 score는 점수입니다. getGrade로 그룹을 나누고 getScore로 평균을 구합니다.</p>
 *
 * <p>new Student("홍길동", "남", 1, 95)는 1학년이며 95점인 학생을 생성합니다.</p>
 *
 * <p>getter는 필드를 읽고 setter는 필드를 변경합니다. this.score는 현재 객체의 필드, score는 전달받은 매개변수입니다.</p>
 *
 * <p>매개변수 없는 생성자는 기본값으로 시작합니다. 문자열은 null, 정수는 0이므로 데이터를 넣기 전에 사용할 때 주의하세요.</p>
 *
 * <p>스트림 처리 중 setter를 호출하면 원본 학생도 바뀝니다. 학습 예제에서는 값을 읽어 새 결과로 만드는 데 집중합니다.</p>
 */
public class Student {

    /**
     * 학생 이름을 저장합니다.
     */
    private String name;
    /**
     * 학생 성별 문자열을 저장합니다.
     */
    private String gender;
    /**
     * 학생 학년을 저장합니다.
     */
    private int grade;
    /**
     * 학생 점수을 저장합니다.
     */
    private int score;

    /**
     * 필드를 자바 기본값으로 초기화한 학생을 생성합니다.
     */
    public Student() {
    }

    /**
     * 전달받은 값으로 학생을 생성합니다.
     *
     * @param name 학생 이름
     * @param gender 학생 성별 문자열
     * @param grade 학생 학년
     * @param score 학생 점수
     */
    public Student(String name, String gender, int grade, int score) {
        this.name = name;
        this.gender = gender;
        this.grade = grade;
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
     * 학생 이름을 새 값으로 변경합니다.
     *
     * @param name 새로 저장할 학생 이름
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 학생 성별 문자열을 읽습니다.
     *
     * @return 현재 객체에 저장된 학생 성별 문자열
     */
    public String getGender() {
        return gender;
    }

    /**
     * 학생 성별 문자열을 새 값으로 변경합니다.
     *
     * @param gender 새로 저장할 학생 성별 문자열
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 학생 학년을 읽습니다.
     *
     * @return 현재 객체에 저장된 학생 학년
     */
    public int getGrade() {
        return grade;
    }

    /**
     * 학생 학년을 새 값으로 변경합니다.
     *
     * @param grade 새로 저장할 학생 학년
     */
    public void setGrade(int grade) {
        this.grade = grade;
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
     * 학생 점수을 새 값으로 변경합니다.
     *
     * @param score 새로 저장할 학생 점수
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 객체를 출력할 때 사람이 읽을 수 있는 설명으로 바꿉니다.
     *
     * @return 이 객체의 주요 필드를 나타내는 문자열
     */
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", grade=" + grade +
                ", score=" + score +
                '}';
    }
}
