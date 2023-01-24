# 함수

---

### 함수를 만들때에는 작게 만들어라

- if, else, while문에 들어가는 블록응ㄴ 한줄이여야 한다.

### 서술적인 이름을 사용하라

- 일므이 길어도 상관없으니 함수의 기능을 잘 표현하는 이름을 선택한다. 
- 하지만 일관성을 지켜야 한다. 모듈 내에서 함수 이름은 같은 문구, 명사, 동사를 사용한다. 

### 함수 인수

- 가장 이상적인 인수 개수는 0개이나 그렇게 하기엔 불가능하니 최대 3개 이상은 피하는것이 낫다.
- 플래그 인수는 절대 사용하지 말자
  - 플래그 인수를 사용하면, 해당 함수가 다양한 로직을 처리한다는 것을 공표한다는 의미이다.(별루당..)
- 단항 형식(인수 1개)
  - 인수에 질문을 던지는 경우
    ```
    boolean fileExists("MyFile"); 
    ```
  - 인수를 뭔가로 변환해 반환하는 경우
    ```
    InputStream fileOpen("MyFile");  
    ```
  - 시스템 상태를 변화시키는 경우 (이때는 이벤트로 시스템 상태를 변화시킨다는 것을 해놓아야함)
    ```
    setStatus(SystemStatus.ACTIVE); 
    ```
    
- 인수 개수를 줄이기 위해서는, 클래스로 묶을수 있나 확인하자.
```
//before
Circle makeCircle(double x, double y, double radius);

//after
Circle makeCircle(Point center, double radius);
```
- 동사와 키워드를 잘 활용하자
  - 함수 이름은 동사로, 인자는 명사로 설정을 했을때 가독성은 뛰어나진다. 
    

### 부수 효과를 일으키지 마라

- 부수 효과를 설정하면, 함수에 하나의 기능만 한다고 설정하고서 다른 기능을 추가하여 장애를 초래한다. 
- 아래 코드를 보면 checkPassword  함수가 패스워드 체크만 하고 있는 것처럼 보이나, 내용을 보면 Session 초기화도 동시에 진행하고 있다. 
```
public class UserValidator {
    private Cryptographer cryptographer;
    
    public boolean checkPassword(String userName, String password) {
        User user = UserGateway.findByName(userName);
        if(user != User.NULL) {
            String codedPhrase = user.getPhraseEncodedByPassword();
            String phrase = cryptographer.decrypt(codedPhrase, password);
            if("Valid Password".equals(phrase)) {
                Session.initialize();  // ??????????
                return true;
            }
        }
    }
    
    return false;
}
```
- 위처럼 구현하는 것보다는 아래와 같이 구현하는 것이 낫다.
```
// 함수 명으로 표현. 하지만 단일책임원칙은 따르지 못한다.
public boolean checkPasswordAndInitializeSession(String userName, String password);

//함수 분리
boolean checkPasswordResult = checkPassword(String userName, String password);
if(checkPasswordResult){
    sessionInitialize();
}
```

### 명령과 조회를 분리하라
```
boolean set(String attribute, String value); // attribute field에 value를 설정하는데 반환값이 boolean..? 설정 결과인가?

void set(String attribute, String value); // ㅇㅇ 설정하는군
```

### 오류 코드보다 예외를 사용하라

### 중복을 없애라
- 반복하는 코드들을 함수로 추출하여 재사용하라.
- interface, AOP등을 사용하여 공통 로직을 추출하여 재사용하라. 

### 구조적 프로그래밍