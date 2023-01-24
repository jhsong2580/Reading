# 의미 있는 이름

---

### 의도를 분명히 밝혀라
```
//잘못된 사례
int d; // 경과 시간(단위 : 날짜)

//좋은 사례
int elapsedTimeInDays;
int daysSinceCreation;
int daysSinceModification;
int fileAgeInDays;
```

### 그릇된 정보를 피하라

- 약어를 사용할때 그릇된 정보를 피하자
  - hypotenuse -> hp
    - Health Point? Hp Printer?
  - List가 아닌 여러 계정을 묶을때 List를 변수에 붙이지 말자
    - accountList(x), accounts(o)

### 발음하기 쉬운 단어로 명명하라

### 검색하기 쉬운 이름을 사용하라 (원시값은 변수로 감싸자)
```
void getMembersOverTenYears(){
    memberRepository.findAllMtAge(10); //(x)
    
    private static int AGE_LIMIT = 10;
    memberRepository.findAllMtAge(AGE_LIMIT); // (o)
}
```

### 클래스나 객체 이름은 명사나 명사구가 적합하다.

### 메서드 이름은 동사나 동사구가 적합하다.
- get, set, is를 통해 좀더 효율적으로 표현한다. 
- 생성자를 중복정의 할 경우, 정적 팩토리 메서드를 사용한다. 

### 한 개념에 한 단어를 사용하라
- 어떤 것을 조회하는 메서드에 get, retrieve, fetch 등의 단어를 혼용하면 이해하기 어려워진다. 

### 의미 있는 맥락을 추가하라