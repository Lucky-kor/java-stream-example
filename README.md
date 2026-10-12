# 자바 Stream 학습 가이드

이 프로젝트는 반복문과 스트림의 비교부터 생성, 중간 연산, 최종 연산, Optional, 데이터 보고서까지 순서대로 학습하는 예제입니다.
각 `main`은 독립적으로 실행할 수 있습니다. **출력을 먼저 예상하고 → 실행하고 → 주석으로 이유를 확인하고 → 입력을 바꿔 보는 방식**으로 학습하세요.

## 학습 목표

- 데이터 소스 → 중간 연산 → 최종 연산으로 파이프라인을 구성한다.
- 스트림의 지연 실행, 일회성, 단락 평가를 설명한다.
- `filter`, `map`, `flatMap`, `mapToInt`의 입력·출력 타입을 구분한다.
- 정렬·중복 제거·잘라내기의 순서가 결과에 미치는 영향을 설명한다.
- 빈 검색·집계 결과를 Optional로 안전하게 처리한다.
- `collect`와 `reduce`를 목적에 맞게 사용한다.
- 그룹화·분할·그룹별 집계로 간단한 보고서를 만든다.
- 중복 키, 수정 불가 리스트, 병렬 처리의 외부 상태 변경을 주의한다.

## 실행 환경과 방법

**JDK 17 이상**을 사용하세요. 이 프로젝트는 외부 라이브러리, Maven, Gradle 없이 실행합니다.
스트림 자체는 Java 8부터 제공되지만 이 예제의 `List.of`는 Java 9, `Stream.toList`는 Java 16 이상 기능입니다.
JDK 21에서 전체 컴파일과 모든 `main` 실행을 확인했습니다.

### IntelliJ IDEA

1. 프로젝트를 열고 Project SDK를 JDK 17 이상으로 지정합니다.
2. `src`가 Sources Root인지 확인합니다. 필요하면 폴더 우클릭 → Mark Directory as → Sources Root를 선택합니다.
3. 학습할 파일을 열고 `main` 왼쪽 실행 아이콘을 누릅니다.
4. Run 창에서 제목·예상값·실제값을 비교합니다.

### 터미널(macOS/Linux)

프로젝트 루트에서 실행합니다. 개별 파일만 컴파일하면 다른 패키지의 공통 모델을 찾지 못할 수 있으므로 전체 소스를 컴파일합니다.

```sh
mkdir -p out
find src -name '*.java' > out/sources.txt
javac -encoding UTF-8 -d out @out/sources.txt
java -cp out streambasic.ImperativeProgramming
java -cp out intermediateoperator.FlatMapExample
java -cp out streampractice.Application
```

다른 예제는 마지막 인자를 표의 **실행 클래스**로 바꾸세요. `out`은 컴파일 산출물 폴더입니다.

## 권장 학습 순서

| 순서 | 실행 클래스 | 핵심 주제 |
| --- | --- | --- |
| 1 | `streambasic.PrintNumberOperator` | 외부·내부 반복, 람다, 메서드 참조 |
| 2 | `streambasic.ImperativeProgramming` | 반복문과 스트림으로 합계 비교 |
| 3 | `streambasic.StreamOperator` | 소스 공통 처리, 원본, 일회성 |
| 4 | `createstream.StreamCreator` | 배열·컬렉션·범위·무한 스트림, 기본형 |
| 5 | `streambasic.v2.StreamPractice` | 반복문 평균, 빈 대상 |
| 6 | `streambasic.v2.StreamPracticeV2` | 같은 문제를 스트림으로 풀기 |
| 7 | `intermediateoperator.Main` | filter/map/distinct/sorted/skip/limit |
| 8 | `intermediateoperator.FlatMapExample` | map과 flatMap 비교 **(추가)** |
| 9 | `sortedinterface.Example` | Comparable, Comparator, 동점 처리 |
| 10 | `terminaloperator.ExampleV1` | 지연 실행 관찰 |
| 11 | `terminaloperator.ExampleV2` | 요소별 처리, 단락 평가 |
| 12 | `terminaloperator.ExampleV3` | 리스트·맵·문자열 수집 |
| 13 | `terminaloperator.ExampleV4` | 조건 판정과 빈 스트림 |
| 14 | `terminaloperator.ExampleV5` | findFirst/findAny, 배열 수집 |
| 15 | `optional.OptionalExampleV1` | 생성, 변환, 기본값, 예외 |
| 16 | `terminaloperator.ReduceExample` | 항등값과 결합 법칙 **(추가)** |
| 17 | `terminaloperator.CollectorPitfallsExample` | 중복 키·리스트 수정 **(추가)** |
| 18 | `datareport.ExampleV1` | 그룹화와 그룹별 집계 |
| 19 | `datareport.ExampleV2` | 분할과 다중 그룹화 |
| 20 | `datareport.ExampleV3` | 기본형 집계와 통계 |
| 21 | `streampractice.Application` | 학생 보고서 종합 실습 |
| 22 | `streambasic.ParallelStreamExample` | 병렬 처리와 부수 효과 **(추가·심화)** |

기초부터 한 번 학습한 뒤 21번 종합 실습을 스스로 다시 구현하세요. 병렬 스트림은 마지막에 학습하면 됩니다.

## 1. 반복문에서 스트림으로

학습 파일: [PrintNumberOperator.java](src/streambasic/PrintNumberOperator.java), [ImperativeProgramming.java](src/streambasic/ImperativeProgramming.java)

반복문은 순회와 누적을 직접 작성합니다. 스트림은 요소 하나를 판정·변환하는 함수를 전달하고 순회를 맡깁니다.
`number -> System.out.println(number)`는 이 예제에서 `System.out::println`과 같은 일을 합니다.
모든 람다를 메서드 참조로 바꿀 수 있는 것은 아닙니다. `number -> number * 2`처럼 별도 계산이 있으면 람다가 자연스럽습니다.

```java
int sum = numbers.stream()
        .filter(number -> number > 4)
        .filter(number -> number % 2 == 0)
        .mapToInt(Integer::intValue)
        .sum();
```

입력 `[1, 3, 6, 7, 8, 11]`에서 선택되는 값은 `6`, `8`이고 합계는 `14`입니다.
반복문 두 풀이에서도 각각 별도의 누적 변수를 사용해 `14`가 나오는지 비교합니다.

**직접 해보기:** 조건을 `4 이상인 홀수`로 바꾸고 반복문과 스트림의 결과를 비교하세요. 빈 리스트에서도 합계가 어떻게 나오는지 확인하세요.

## 2. 파이프라인과 스트림의 일회성

학습 파일: [StreamOperator.java](src/streambasic/StreamOperator.java)

| 구분 | 대표 메서드 | 결과 |
| --- | --- | --- |
| 생성 | `stream`, `Arrays.stream`, `Stream.of` | 처리할 스트림 |
| 중간 연산 | `filter`, `map`, `sorted`, `distinct` | 이어서 처리할 스트림 |
| 최종 연산 | `sum`, `count`, `collect`, `toList`, `forEach` | 숫자·컬렉션·Optional 또는 출력 등의 동작 |

스트림은 데이터 저장소가 아니라 처리 파이프라인입니다. 최종 연산으로 소비한 스트림은 재사용할 수 없습니다.
예제에서는 재사용 시 발생하는 `IllegalStateException`을 잡아 원인을 출력하고, 원본의 `stream()`으로 새 스트림을 만듭니다.
리스트와 배열 모두 같은 스트림 연산으로 정렬할 수 있으며, 이 예제의 `sorted`는 원본 순서를 바꾸지 않습니다.

다만 스트림이 원본 객체를 복제하는 것은 아닙니다. 람다에서 객체의 setter를 호출하면 해당 객체의 상태는 바뀔 수 있습니다.
원본 컬렉션을 순회 중에 수정하거나 외부 누적 변수를 변경하는 코드는 피하세요.

**직접 해보기:** 정렬 후 원본을 출력하세요. 결과가 필요할 때마다 원본에서 새 스트림을 만들어 길이와 개수를 각각 구하세요.

## 3. 스트림 생성과 기본형 스트림

학습 파일: [StreamCreator.java](src/createstream/StreamCreator.java)

- 참조형 배열: `Arrays.stream(String[])`, `Stream.of(String[])`
- 컬렉션: `list.stream()`
- 기본형 배열: `Arrays.stream(int[])` → `IntStream`
- 범위: `IntStream.range(10, 15)`는 `10~14`, `rangeClosed(10, 15)`는 `10~15`
- 생성 함수: `Stream.iterate`로 이전 값에서 다음 값 생성, `Stream.generate`로 공급 함수 호출
- 빈 입력: `Stream.empty()`

`Stream.of(int[])`는 숫자 3개가 아니라 **배열 하나**를 요소로 갖는 `Stream<int[]>`입니다.
`IntStream`은 숫자 전용 `sum`, `average` 등을 제공합니다. `boxed()`는 `Stream<Integer>`, `mapToObj`는 원하는 참조형 스트림으로 바꿉니다.

무한 스트림은 `limit` 등으로 끝낼 수 있게 만들어야 합니다. `sorted().limit(5)`는 무한 입력 전체의 정렬을 기다리므로 끝나지 않습니다.
예제 난수는 시드 `42`를 고정해 실행마다 같은 결과를 비교할 수 있게 했습니다.

**직접 해보기:** 1부터 10까지 합계를 구하세요(`55`). `range`와 `rangeClosed`의 개수를 비교하세요. `iterate`로 처음 5개의 짝수를 만드세요.

## 4. 평균과 빈 결과

학습 파일: [StreamPractice.java](src/streambasic/v2/StreamPractice.java), [StreamPracticeV2.java](src/streambasic/v2/StreamPracticeV2.java)

두 예제는 같은 회원 데이터를 사용합니다. 남자 회원 `37세`, `23세`의 평균은 `30.0`입니다.
반복문은 인원이 0인지 먼저 검사하고, 스트림의 `average`는 `OptionalDouble`로 값이 없을 가능성을 표현합니다.

`orElse(0.0)`은 선택한 기본값일 뿐입니다. **계산 대상 없음과 실제 평균 0은 서로 다른 상태**입니다.
서비스나 보고서에서는 `isPresent`, 별도의 없음 표시, 예외 처리 중 목적에 맞는 정책을 선택하세요.

**직접 해보기:** 남자 회원을 모두 제거하세요. 빈 대상을 반복문은 어떻게 처리하고 스트림은 어떤 값을 반환하는지 비교하세요.

## 5. 중간 연산과 순서

학습 파일: [Main.java](src/intermediateoperator/Main.java)

| 연산 | 하는 일 | 주의할 점 |
| --- | --- | --- |
| `filter` | 조건이 true인 요소만 남김 | 요소 자체를 변환하지 않음 |
| `map` | 요소 하나를 다른 값 하나로 변환 | 스트림 요소 타입이 바뀔 수 있음 |
| `distinct` | 중복 제거 | 객체의 equals/hashCode 기준 |
| `sorted` | 자연 순서 또는 지정 기준 정렬 | 정렬에 필요한 입력을 모아야 함 |
| `skip` | 앞에서 지정 개수 제외 | 현재 순서에 적용 |
| `limit` | 최대 지정 개수만 선택 | 위치에 따라 결과가 달라짐 |

입력 `[5, 1, 4, 2, 3]`에서 `sorted().limit(3)`은 `[1, 2, 3]`, `limit(3).sorted()`는 `[1, 4, 5]`입니다.
정수 내림차순 비교에 `b - a`를 사용하면 오버플로우가 생길 수 있습니다. `Comparator.reverseOrder()`나 `Integer.compare`를 사용하세요.

**직접 해보기:** `distinct`와 `limit` 순서를 바꿔 보세요. 회원의 나이와 성별을 검사한 다음 이름만 남기는 각 단계의 타입을 적으세요.

## 6. map과 flatMap

학습 파일: [FlatMapExample.java](src/intermediateoperator/FlatMapExample.java) **(추가)**

입력은 `[[Java, SQL], [], [Java, Python]]`입니다.

- `map(List::size)` → `[2, 0, 2]`: 내부 리스트 하나를 크기 하나로 변환
- `map(List::stream)` → `Stream<Stream<String>>`: 내부 스트림이 요소로 남음
- `flatMap(List::stream)` → `[Java, SQL, Java, Python]`: 내부 요소를 펼침
- 그 뒤 `distinct()` → `[Java, SQL, Python]`

빈 내부 리스트는 펼치면 요소를 내보내지 않습니다. 주문별 상품 목록, 반별 학생 목록, 문장을 단어로 나누는 처리에서 같은 패턴을 사용할 수 있습니다.

**직접 해보기:** 전체 과목 중 길이가 4 이상인 이름만 남기세요. 중첩 목록에 빈 리스트를 하나 더 추가해 결과가 어떻게 달라지는지 설명하세요.

## 7. 정렬 기준과 동점 처리

학습 파일: [Example.java](src/sortedinterface/Example.java), [Student.java](src/sortedinterface/Student.java)

`Comparable.compareTo`는 클래스의 기본 순서를 정의합니다. 여기서는 나이 내림차순입니다.
`Comparator`는 특정 작업에 필요한 별도의 기준을 전달합니다.

```java
Comparator<Student> order = Comparator.comparingInt(Student::getAge)
        .reversed()
        .thenComparing(Student::getName);
```

나이는 내림차순, 동갑인 `가학생`과 `김러키`는 이름 오름차순입니다.
`reversed()`를 전체 체인의 마지막에 붙이면 이름 정렬까지 함께 뒤집힙니다.
`compareTo` 결과가 `0`이어도 두 객체가 `equals`로 같은 것은 아닐 수 있습니다. 따라서 같은 나이의 학생이 `distinct`에서 자동으로 제거되지는 않습니다.

**직접 해보기:** 나이 오름차순·동갑은 이름 내림차순으로 바꾸세요. `sorted()`와 `sorted(comparator)`를 비교하세요.

## 8. 지연 실행과 단락 평가

학습 파일: [ExampleV1.java](src/terminaloperator/ExampleV1.java), [ExampleV2.java](src/terminaloperator/ExampleV2.java)

중간 연산을 연결할 때는 처리 규칙만 구성하고, 최종 연산에서 실행을 시작합니다.
V1은 최종 연산 시작 안내가 나온 뒤 `조건 검사`, `변환`, `결과`가 출력되는 흐름을 보여 줍니다.
V2의 두 조건과 변환 결과는 반복문과 똑같이 `30, 90, 150, 210, 270`입니다.
`findFirst`는 처음 `number > 3`인 값 `4`를 찾으면 나머지 요소를 검사하지 않습니다.

모든 중간 연산이 언제나 실행되는 것은 아닙니다. `count`는 크기를 알 수 있으면 `map`/`peek` 같은 단계를 생략할 수 있습니다.
학습용 로그는 관찰 도구이며, `peek`나 조건 함수에 반드시 실행돼야 하는 저장·갱신 작업을 넣지 마세요.
`sorted` 같은 상태 유지 연산이 있으면 단순한 요소별 처리 흐름과 달라집니다.

**직접 해보기:** V1의 최종 연산을 잠시 제거해 로그를 비교하세요. V2의 `findFirst` 조건을 `number > 30`으로 바꿔 검사 범위를 확인하세요.

## 9. 최종 연산: 수집·판정·검색

학습 파일: [ExampleV3.java](src/terminaloperator/ExampleV3.java), [ExampleV4.java](src/terminaloperator/ExampleV4.java), [ExampleV5.java](src/terminaloperator/ExampleV5.java)

- `collect(toList())`: 여학생 이름 `[이자바, 나미녀]` 수집
- `collect(toMap(...))`: 이름을 키, 점수를 값으로 수집
- `joining(", ", "[", "]")`: 이름을 구분자·접두사·접미사로 연결
- `allMatch`/`anyMatch`/`noneMatch`: 전체/하나 이상/하나도 없음 판정
- `findFirst`: 만나는 순서가 있는 스트림에서 첫 요소 검색
- `findAny`: 어떤 요소가 선택될지 보장하지 않는 검색
- `toArray(String[]::new)`: 이름을 `String[]`로 수집

빈 스트림에서 `allMatch=true`, `anyMatch=false`, `noneMatch=true`입니다.
“학생이 있고 모두 합격”이라면 비어 있지 않은지도 함께 검사해야 합니다.
`findAny`가 현재 실행에서 첫 요소를 반환해도 그 결과를 고정된 규칙으로 기대하면 안 됩니다.

**직접 해보기:** 전원 미통과 데이터를 만들고 세 조건 판정을 비교하세요. 조건에 맞는 학생이 없을 때 이름 대신 `없음`이 출력되는지 확인하세요.

## 10. Optional을 안전하게 다루기

학습 파일: [OptionalExampleV1.java](src/optional/OptionalExampleV1.java)

| 메서드 | 사용 목적 |
| --- | --- |
| `of(value)` | null이 아닌 값 생성; null이면 예외 |
| `ofNullable(value)` | null이면 빈 Optional 생성 |
| `empty()` | 값 없음을 명시 |
| `ifPresent(action)` | 값이 있을 때 동작 실행 |
| `filter` / `map` | 값이 있을 때 조건 검사 / 변환 |
| `orElse(value)` | 미리 평가된 기본값 사용 |
| `orElseGet(supplier)` | 비어 있을 때만 기본값 생성 함수 호출 |
| `orElseThrow(supplier)` | 비어 있으면 목적에 맞는 예외 발생 |

`orElse(createDefaultName())`는 값이 있어도 함수가 실행됩니다. `orElseGet`은 비어 있을 때만 실행됩니다.
예제의 출력에서 “기본 이름 생성 함수 실행”이 언제 나오는지 확인하세요.
빈 Optional의 `get()`이나 `orElse(null)` 이후의 메서드 호출은 실패할 수 있습니다.

**직접 해보기:** 값 있음·없음 각각에서 기본값 함수 호출 횟수를 예상하세요. 검색 조건을 바꿔 `orElseThrow`의 성공과 실패를 비교하세요.

## 11. reduce와 항등값

학습 파일: [ReduceExample.java](src/terminaloperator/ReduceExample.java) **(추가)**

`reduce`는 요소를 누적해서 하나의 값으로 결합합니다. `[1, 2, 3, 4]`의 합은 `10`, 곱은 `24`입니다.
항등값은 결합해도 상대 값을 바꾸지 않는 값으로 덧셈에서는 `0`, 곱셈에서는 `1`입니다.
항등값이 있으면 빈 스트림에서 그 값을 반환하고, 항등값이 없으면 빈 Optional을 반환합니다.

결합 함수는 묶는 순서가 달라져도 결과가 같아야 합니다. 뺄셈은 `(1-2)-3=-4`, `1-(2-3)=2`로 달라져 병렬 reduce에 적합하지 않습니다.
변경 가능한 리스트를 누적하는 작업은 `collect`를 사용하세요. 정수 합계에는 기본형 스트림의 `sum()`이 더 직접적입니다.

**직접 해보기:** 곱셈의 항등값을 잘못해서 `0`으로 넣으면 어떤 결과가 나오는지 확인하세요. 빈 입력과 한 요소 입력도 비교하세요.

## 12. 중복 키와 수집 결과의 수정 가능 여부

학습 파일: [CollectorPitfallsExample.java](src/terminaloperator/CollectorPitfallsExample.java) **(추가)**

두 인자 `toMap`은 동일한 키가 두 번 나오면 실패합니다. 예제에서 `김코딩`의 점수는 `80`, `95`입니다.
병합 함수를 `Integer::max`로 지정하면 최고 점수 `95`만 남습니다. 두 학생 모두 보존하려면 `groupingBy`로 리스트에 모으세요.
실제 데이터에서는 이름이 고유하지 않을 수 있으므로 식별자와 중복 처리 정책부터 결정해야 합니다.

| 수집 방법 | 결과 리스트의 수정 가능 여부 |
| --- | --- |
| `stream.toList()` | 수정 불가 |
| `collect(Collectors.toList())` | API 계약에서 보장하지 않음 |
| `collect(Collectors.toCollection(ArrayList::new))` | 지정한 ArrayList로 수정 가능 |

수정 불가는 리스트의 추가·삭제·교체 제한입니다. 내부 객체까지 자동으로 불변이 되는 것은 아닙니다.
기본 `toMap`/`groupingBy`는 맵 순서를 보장하지 않습니다. 예제는 필요에 따라 `LinkedHashMap`(입력 순서), `TreeMap`(키 정렬)을 지정합니다.

**직접 해보기:** 중복 점수를 합산하는 정책으로 바꾸세요. 같은 이름의 학생 전체를 보존하는 결과와 비교하세요.

## 13. 그룹화·분할·통계 보고서

학습 파일: [ExampleV1.java](src/datareport/ExampleV1.java), [ExampleV2.java](src/datareport/ExampleV2.java), [ExampleV3.java](src/datareport/ExampleV3.java)

보고서 공통 입력은 [SampleStudents.java](src/datareport/SampleStudents.java)에 있습니다.
학년별 인원은 `{1=3, 2=2, 3=1}`이며 전체 점수 합계는 `538`, 평균은 약 `89.67`, 최솟값은 `80`, 최댓값은 `100`입니다.

- `groupingBy`: 학년·성별 등 여러 키로 분류
- downstream collector: 그룹 안에서 `mapping`, `counting`, `averagingInt`로 이름·인원·평균 집계
- `partitioningBy`: 조건의 true/false 두 그룹으로 분할; 빈 입력에서도 두 키 존재
- 다중 그룹화: 학년 → 성별 → 학생 목록
- `summarizingInt`: 인원·합계·평균·최솟값·최댓값을 한 번에 집계

90점 **이상** 분할에는 `>= 90`을 사용합니다. 통과자는 홍길동·신사임당·유관순입니다.
빈 숫자 스트림의 합은 `0`, 평균·최솟값·최댓값은 값이 없는 Optional입니다.
빈 `IntSummaryStatistics`의 min/max는 각각 `Integer.MAX_VALUE`/`Integer.MIN_VALUE` 초기값이므로 인원이 0인지 먼저 확인하세요.
큰 합계·곱에는 범위를 고려하세요. 예제의 곱은 `long`으로 계산하고 `Math.multiplyExact`로 범위 초과를 감지합니다.

**모델 이름 주의:** `terminaloperator.Student.getGrade()`는 기존 예제의 이름을 유지한 **점수**입니다.
`datareport.Student.getGrade()`는 **학년**, `getScore()`는 **점수**입니다. 서로 다른 패키지의 클래스를 혼동하지 마세요.
`intermediateoperator.Gender`는 문자열을 감싼 클래스, `terminaloperator.Gender`는 enum입니다.

**직접 해보기:** 학년별 최고 점수, 성별 총점, 학년별 90점 이상 인원을 구하세요. 한쪽 그룹이 빈 분할과 빈 그룹화 결과를 비교하세요.

## 14. 종합 실습

학습 파일: [Application.java](src/streampractice/Application.java)

예제는 통과자 이름, 상위 2명, 전체 평균, 결과별 이름을 출력합니다.
예상 결과는 통과자 `[민지A, 지훈C, 가영E]`, 상위 2명 `가영e·지훈c`, 평균 `81.4`입니다.
`Locale.ROOT`를 사용해 대문자 변환이 실행 환경의 기본 로케일에 좌우되지 않도록 했습니다.

**직접 해보기:** 코드를 가리고 아래 보고서를 새로 작성하세요.

1. 90점 이상 학생 이름만 선택한다.
2. 점수 내림차순, 동점은 이름 오름차순으로 상위 3명을 구한다.
3. 통과자 평균을 구하고 아무도 없으면 `계산 대상 없음`을 표시한다.
4. 통과·재학습 인원을 각각 집계한다.
5. 빈 목록·전원 미통과·전원 동점으로 바꾸어 결과를 확인한다.

추가 문제와 확인용 풀이: [STREAM_EXERCISES.md](docs/STREAM_EXERCISES.md)

## 15. 병렬 스트림의 주의점

학습 파일: [ParallelStreamExample.java](src/streambasic/ParallelStreamExample.java) **(추가·심화)**

순차·병렬 합계는 모두 `500500`입니다. 병렬 처리는 요소 처리를 여러 작업으로 나누므로 함수가 외부의 변경 가능한 상태에 의존하면 결과가 잘못될 수 있습니다.
예제의 잘못된 외부 누적 코드는 주석으로 남기고, 실행 코드는 `sum`과 `toList`로 안전하게 결과를 수집합니다.

병렬 `forEach`의 출력 순서는 고정되지 않습니다. 순서가 있는 소스의 결과 순서를 유지해야 하면 `forEachOrdered`를 사용합니다.
리스트에서 병렬 `map(...).toList()`로 수집한 결과도 만나는 순서를 유지합니다.
병렬 처리의 작업 분할·병합 비용 때문에 항상 더 빠르지는 않습니다. 작은 예제의 실행 시간으로 성능을 판단하지 마세요.

**직접 해보기:** 매핑 연산을 짝수 필터로 바꿔 순차·병렬 결과를 비교하세요. 출력 순서와 수집한 리스트 순서를 구분해서 설명하세요.

## 최종 점검 질문

1. 중간 연산과 최종 연산은 반환값과 실행 시점에서 어떻게 다른가요?
2. 원본 리스트를 다시 사용할 수 있어도 같은 스트림은 왜 재사용할 수 없나요?
3. `Stream<Integer>`와 `IntStream`은 어떤 연산에서 차이가 있나요?
4. `Stream.of(int[])`의 요소 수가 1인 이유는 무엇인가요?
5. `map(List::stream)`과 `flatMap(List::stream)`의 결과 타입은 무엇인가요?
6. `sorted().limit(3)`과 `limit(3).sorted()`는 언제 다른 결과를 내나요?
7. `distinct`는 사용자 정의 객체의 어떤 동작에 의존하나요?
8. 빈 스트림의 `allMatch`가 true라는 결과는 어떤 요구사항에서 추가 확인이 필요한가요?
9. `findFirst`와 `findAny`의 선택 규칙은 어떻게 다른가요?
10. `orElse`와 `orElseGet`은 기본값 생성 시점이 어떻게 다른가요?
11. 항등값 없는 `reduce`는 왜 Optional을 반환하나요?
12. 뺄셈을 병렬 `reduce`에 사용하면 안 되는 이유는 무엇인가요?
13. 동명이인 데이터를 `toMap`과 `groupingBy`로 각각 어떻게 처리하나요?
14. 수정 가능한 ArrayList를 확실히 얻으려면 어떤 수집 방법을 사용하나요?
15. `groupingBy`와 `partitioningBy`는 빈 입력에서 어떻게 다른가요?
16. 빈 통계의 최솟값을 실제 점수로 출력하면 안 되는 이유는 무엇인가요?
17. 병렬 람다에서 외부 리스트나 합계를 직접 변경하면 왜 문제가 되나요?
18. 스트림 사용이 원본 객체의 불변성을 보장하나요?

답하기 어려운 질문이 있으면 관련 예제로 돌아가 입력을 바꾸고 다시 실행하세요.

## 공식 학습 자료

코드에서 확인한 연산의 상세 계약을 읽을 때 참고하세요. 모든 메서드를 외우기보다 반환 타입과 빈 입력의 동작을 찾아보는 연습을 권장합니다.

- [Stream API (Java 21)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html): 파이프라인, 일회성, 각 연산의 계약
- [Collectors API (Java 21)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Collectors.html): 수집·그룹화·분할 메서드
- [Optional API (Java 21)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Optional.html): 값 없음의 처리와 기본값 메서드
