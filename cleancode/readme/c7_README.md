# 오류처리 

---

### 오류코드보다 예외를 사용하라
- 오류코드를 사용하여 관리하면 호출한 함수에서 즉시 오류를 확인하여야 하여 코드가 복잡해진다. 
- 예외를 던지고 다른 곳에서 집중적으로 관리를 하게 되면, 논리가 오류 처리 코드와 뒤섞이지 않아 깔끔해진다. 


### try-catch-finally 문부터 작성하라
- 예외에서 프로그램 안에다 범위를 정하는 구문
- try블록에서 무슨 일이 생기든 catch문을 통해 개발자가 기대하는 상태를 정의하기 쉬워짐


### Unchecked Exception을 사용하라
- Check Exception은 OCP를 위반한다
  - 하위 메서드에서 Check Exception을 추가한다면 상위 메서드에서 모두 이 Exception을 처리해야한다
    - 확장에 열려있지 않다. 
- 아주 중요한 라이브러리를 작성한다면 모든 예외를 잡아야 하여, Checked Exception을 사용하는것이 낫다. 

### 예외에 의미를 제공하라
- 예외를 던질때는 전/후 상황을 충분히 덧붙인다. (예외의 원인과 결과)
- 호출 스택과 실패한 연산 이름, 유형도 같이 Exception Message에 추가해주면 Debug에 효과적이다.

### 호출자를 고려해 예외 클래스를 정의하라
- Application에서 오류를 정의할 때 개발자에게 가장 중요한 관심사는 "오류를 잡아내는 방법"이 되어야 한다. 

```
// 형편없는 예외처리
try{
  port.open();
} catch (DeviceResponseException e){
  reportPortError(e);
} catch (ATM1212UnlockedException e) {
  reportPortError(e);
} catch (GMXError e) {
  reportPortError(e);
} finally {
  ...
}
```
```
//  중복을 없앤 그나마 나은 예외처리
public class LocalPort {
  private final ACMEPort innerPort;
  
  public LocalPort(int portNumber) {
    innerPort = new ACMEPort(portNumber);
  }
  
  public void open () {
    try {
      innerPort.open();
    }catch (DeviceResponseException e) {
      throw new PortDeviceFailuer(e);
    }catch (ATM1212UnlockedException e) {
      throw new PortDeviceFailuer(e);
    }
  }
}

LocalPort port = new LocalPort(12);
try {
  port.open();
} catch (PortDeviceFailuer e) {
  reportError(e);
  logger.~~
} finally {
  //do something
}
```
- 위와 같이 LocalPort클래스처럼 ACME Port를 감싸 하나의 Exception으로 통일하여 관리한다. 
  - 이렇게 묶는다면 테스트를 할때도 편리해진다.

### 정상 흐름을 정의하라
- 비즈니스 로직과 오류 처리 로직이 분리되어 코드가 깨끗해 보이나, 오류 감지가 프로그램 언저리로 밀려난다. 멋진 처리 방식이지만 때로는 "예외에 의해 중단이 적합하지 않을수도 있다"
```
// 비용 청구 에플리케이션 총계 계산 로직
try {
  MealExpenses = expenses = expenseReportDAO.getMeals(employee.getId());
  m_total += expenses.getTotal(); // 구매를 했다면 가격에 추가를 하고
} catch( MealExpenseNotFound e) {
  m_total += getMealPerDiem(); // 구매를 하지 않았다면 기본 가격을 추가한다.
}
```
- 위 코드는 식비를 청구하지 않았을땐 기본 가격을 추가한다는 내용이다. 굳이 에러로 처리를 해야할까?
- 아래 처럼 예외로 처리하지 않고 캡슐화 하여 처리하는것이 더욱 깔끔하다
```
MealExpenses = expenses = expenseReportDAO.getMeals(employee.getId());
m_total += expenses.getTotal();

public class PerDiemMealExpenses implements MealExpenses {
  public int getTotal() {
    // 기본값으로 기본 식비 반환
  }
}
```


### null을 반환하지 마라
- NULL을 반환하게 되면, 이 메서드를 사용하는 호출자들에게 null처리를 위임한다. 
  - 이때 처리를 누락하게 되면 호출자가 생각하지 못한 NPE가 발생할수 있다. 
- null을 반환하는 대신에, 예외를 던지거나 Optional로 묶어 보낸다.

```
// NULL을 반환할 필요가 없을 경우
public List<Employee> getEmployees() {
  if(employee is empty)
    return null; // 굳이 null을 반환해야할까? 
    return Collections.emptyList(); // 빈 List를 반환하여 NPE를 피하자
  return employees;
}
```

```
// null대신 예외를 발생시키자
public double xProjection(Point p1, Point p2){
  if(isEmpty(p1) || isEmpty(p2) {
    throw InvalidArgumentException("p1,p2 required");
  }
  // do something
}
```

```
// assert를 사용하자
public double xProjection(Point p1, Point p2){
  assert p1 != null : "p1 required";
  assert p2 != null : "p2 required";
  //do something
}
```

### 결론
깨끗한 코드는 일기도 좋아야 하지만 안정성도 높아야 한다. 
오류 처리와 비즈니스 로직을 분리하여, 독립적 추론이 가능해지고 코드 유지보수성도 크게 높아진다. 