package sortedinterface;

/**
 * 나이로 기본 정렬할 수 있도록 Comparable을 구현한 학생 데이터입니다.
 *
 * <p>implements {@code Comparable<Student>}는 다른 학생과 순서를 비교하는 compareTo 메서드를 제공하겠다는 뜻입니다.</p>
 *
 * <p>compareTo가 음수이면 현재 학생이 먼저, 양수이면 상대 학생이 먼저, 0이면 정렬 기준상 같은 순위입니다.</p>
 *
 * <p>Integer.compare(other.age, this.age)는 나이가 많은 학생이 앞에 오도록 순서를 정합니다.</p>
 *
 * <p>두 학생의 나이가 같아도 이름이나 객체가 같다는 뜻은 아닙니다. 이 기본 정렬에서는 이름으로 동점을 해결하지 않습니다.</p>
 *
 * <p>getter는 정렬 기준에 필요한 값을 읽고 toString은 결과 목록에 이름과 나이가 보이게 합니다.</p>
 */
public class Student implements Comparable<Student> {
    /**
     * 학생 이름을 저장합니다.
     */
    private final String name;
    /**
     * 학생 나이을 저장합니다.
     */
    private final int age;

    /**
     * 전달받은 값으로 학생을 생성합니다.
     *
     * @param name 학생 이름
     * @param age 학생 나이
     */
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
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
     * 학생 나이을 읽습니다.
     *
     * @return 현재 객체에 저장된 학생 나이
     */
    public int getAge() {
        return age;
    }

    // 음수: this가 먼저, 양수: other가 먼저, 0: 정렬 기준에서 동등합니다.
    // other와 this의 순서를 바꿔 나이 내림차순을 정의합니다.
    // 정렬 기준이 같은 것과 equals로 같은 객체인 것은 별개의 개념입니다.
    /**
     * 현재 학생과 상대 학생의 나이로 내림차순 순서를 비교합니다.
     *
     * @param other 현재 학생과 순서를 비교할 상대 학생
     * @return 현재 학생이 먼저면 음수, 상대 학생이 먼저면 양수, 동갑이면 0
     */
    @Override
    public int compareTo(Student other) {
        return Integer.compare(other.age, this.age);
    }

    /**
     * 객체를 출력할 때 사람이 읽을 수 있는 설명으로 바꿉니다.
     *
     * @return 이 객체의 주요 필드를 나타내는 문자열
     */
    @Override
    public String toString() {
        return name + " (" + age + "세)";
    }
}
