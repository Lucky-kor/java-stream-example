package terminaloperator;

/**
 * 최종 연산에서 선택하고 이름·점수를 꺼내는 학생 한 명의 데이터입니다.
 *
 * <p>객체는 서로 관련된 값을 하나로 묶습니다. 이 학생 객체에는 이름, 점수, 성별이 들어 있습니다.</p>
 *
 * <p>new Student("김코딩", 100, Gender.MALE)은 세 값을 생성자에 전달해 학생 한 명을 만듭니다.</p>
 *
 * <p>getName 등 getter는 저장된 값을 읽는 메서드입니다. Student::getName은 student -> student.getName()와 같은 뜻입니다.</p>
 *
 * <p>이 패키지의 grade는 기존 이름을 유지한 점수입니다. datareport.Student의 학년 grade와 구분하세요.</p>
 *
 * <p>final 필드는 생성자에서 저장한 값을 나중에 다시 대입할 수 없도록 합니다. toString은 출력할 때 보일 설명을 만듭니다.</p>
 */
public class Student {
    /**
     * 학생 이름을 저장합니다.
     */
    private final String name;
    /**
     * 학생 점수(학년이 아님)을 저장합니다.
     */
    private final int grade;
    /**
     * 학생 성별 enum 상수을 저장합니다.
     */
    private final Gender gender;

    /**
     * 전달받은 값으로 학생을 생성합니다.
     *
     * @param name 학생 이름
     * @param grade 학생 점수(학년이 아님)
     * @param gender 학생 성별 enum 상수
     */
    public Student(String name, int grade, Gender gender) {
        this.name = name;
        this.grade = grade;
        this.gender = gender;
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
     * 학생 점수(학년이 아님)을 읽습니다.
     *
     * @return 현재 객체에 저장된 학생 점수(학년이 아님)
     */
    public int getGrade() {
        return grade;
    }

    /**
     * 학생 성별 enum 상수을 읽습니다.
     *
     * @return 현재 객체에 저장된 학생 성별 enum 상수
     */
    public Gender getGender() {
        return gender;
    }
    /**
     * 객체를 출력할 때 사람이 읽을 수 있는 설명으로 바꿉니다.
     *
     * @return 이 객체의 주요 필드를 나타내는 문자열
     */
    @Override
    public String toString() {
        return name + " (" + grade + "점, " + gender + ")";
    }
}
