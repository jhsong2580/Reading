# 형식 맞추기
- 프로그래머라면 형식을 맞춰 깔끔하게 코드를 짜야한다. 코드의 형식을 맞추기 위한 간단한 규칙을 정하고, 모두가 그 규칙을ㄷ 따라야 한다. 
---

### 형식을 맞추는 목적
- 코드의 형식은 의소소통의 일환으로, 서로에 의해 정해진 프로토콜에 의거하여 개발해야한다.

### 원활한 소통을 장려하는 코드 형식
1. 적절한 행 길이
- 일반적으로 큰 파일보다 작은 파일이 이해하기 쉽다.
2. 신문기사처럼 작성하기
- 이름은 간단하면서도 설명이 가능하게 짓는다. 
- 소스파일 첫부분은 고차원 개념과 알고리즘을 설명한다. 
3. 개념은 빈 행으로 분리하라
- 각 행은 수식이나 절을 나타내고, 행 묶음 별로 빈 행으로 분리하자
4. 세로 밀집도
- 서로 밀접한 코드 행은 세로로 가까이 놓아야 한다. 
```
//before
public class A {
    // dummy
    // description
    private String m_className;
    
    // dummy
    //description
    proviate List<Property> m_properties = new ArrayList();
    public void addProperty(Property property) {
        m_properties.add(property);
    }
}

//after refactoring (하나의 논리는 세로로 밀집도가 높아야 보기 편하다)
public class A {
    private String m_className;
    private List<Property> m_properties = new ArrayList();
    public void addProperty(Property property){
        //do something
    }
}
```

5. 수직거리
- 변수선언 : 변수는 사용되는 위치에 최대한 가까이 선언한다. 
- 인스턴스 변수 : 클래스 맨 처음에 선언하고 변수 간 세로로 거리를 두지 않는다. 
- 종속함수 : 한 함수가 다른 함수를 호출한다면, 두 함수는 세로로 가까이 배치한다. 또한 가능하다면 호출하는 함수를 호출되는 함수보다 먼저 배치한다. 