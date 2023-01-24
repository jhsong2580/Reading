# 객체와 자료구조 

---
### 자료 추상화
- 구현을 노출하느냐, 숨기느냐 잘 골라 설정해야한다.

#1 구체적인 Point 클래스
```
public class Point {
    public double x;
    public double y;
}
```

#2 추상적인 Point 클래스
```
public interface Point {
    double getX();
    double getY();
    void setChartesian(double x, double y);
    double getR();
    double getTheta();
    void setPolar(double r, double theta);
}
```
#1 클래스는 직교좌표계를 사용하며, 구현을 노출하고 있고 #2 클래스는 어느 좌표계를 사용하는지 모르며 구현을 숨기고 있다. 

---
### 자료 객체 비대칭
- 객체는 추상화 뒤로 자료를 숨긴 채 자료를 다르눈 함수만 공개한다.
- 자료구조는 자료를 그대로 공개하며, 별다른 함수는 제공하지 않는다.
  - Geometry Class에 둘레 길이를 구하는 perimeter()함수를 추가하고 싶을때, 
    - 절차적인 도형 : 도형 Class는 여향이 없고 Geometry 함수를 수정해야한다.
    - 다형적인 도형 : 도형 Class, Interface에 영향이 있다. 하지만 도형을 추가함에 있어서 영향 범위가 작다.(새로운 Class 생성)
  - 즉 객체지향 코드에서 어려운 변경은 절차적인 코드에서 쉽고, 반대도 성립한다. 
  - 절차적인 도형
    ```
    public class Square {
     public Point topLeft;
     public double side;
    }  
  
    public class Rectangle {
    public Point topLeft;
    public double bottomRight;
    }
  
    public class Geometry {
    public final double PI = 3.14;
    
    public double getArea(Object space) {
        if(shape instanceof Square){
        // do something
       }
        if(shape instanceof Rectalgle){
        // dosomething
       }
    }
    }
    ```
    - 다형적인 도형
    ```
    interface Shape {
    double area();
    }
  
    public class Square implements Shape {
    // setting variables
    @Override
    public double area() {
      // return Square area
    }
    }  
  
    public class Rectangle implements Shape {
      // setting variables
    @Override
    public double area() {
      // return Rectangle area
    }
    }

    public class Geometry {
    public double area(Shape shape) {
      return shape.area();
    }
    }
    ```
  


---

### 디미터법칙
- 모듈은 자신이 조작하는 객체의 내부를 몰라야한다는 법칙
- 아래 "before"코드는 "기차충돌" 을 일으킨다.
  - getOptions().getScratchDir().....
  - "after"코드로 변경하여 사용하면 더욱 가시성이 뛰어나다.
- 아래 코드는 ctxt, Options, ScrtachDir이 객체라면 디미터 법칙을 위반하고, 자료구조라면 그렇지 않다. 
  - 자료구조는 함수없이 "공개변수만 포함"하기때문

```
//before
final String outputDir = ctxt.getOptions().getScratchDir().getAbsolutePath();

//after
Options opts = ctxt.getOptions();
File scratchDir = opts.getScratchDir();
final String outputDir = scratchDir.getAbsolutePath();
```

### 구조체 감추기
- 만약 위 ctxt, Options, ScratchDir이 객체라면 디미터법칙을 위반한다. 
- 아래 코드로 해당 문제를 해결할수 있으나 문제가 있다. 
  - 1번코드 문제 : ctxt객체에 공개해야하는 메서드가 많아진다. 
  - 2번코드 문제 : getScratchDirectoryOption()이 객체가 아닌 자료구조를 반환한다. 
```
1. ctxt.getAbsolutePathOfScratchDirectoryOption(); 
2. ctxt.getScratchDirectoryOption().getAbsolutePath();
```
- ctxt가 객체라면 사용자는 "~~를 해라"라고 말해야지, ~~를 드러내라 라고 하면 안된다. 
  - path를 가져오는 로직을, path를 가져온 후 처리하는 로직까지 ctxt 객체가 처리하면 드러내지 않고 로직 구현이 가능하다. 

---
### DTO
- 공개변수만 있고 함수가 없는 클래스이다. 


---
# 결론
1. 객체는 동작을 공개하고 자료를 숨긴다. (함수는 public, 변수는 private)
2. 자료구조는 method없이 변수를 공개한다(getter)