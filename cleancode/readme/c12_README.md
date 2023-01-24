# 창발성

---
### 창발적인 설계로 깔끔한 코드를 구현하자
- 단순 한 설계 규칙 4가지..
  1. 모든 테스트를 실행한다.
  2. 중복을 없앤다.
  3. 프로그래머 의도를 표현한다
  4. 클래스와 메서드 수를 최소로 줄인다

### 단순한 설계규칙 1. 모든 테스트를 실행하라 
- 설계는 의도한 대로 돌아가는 시스템을 내놓아야 한다. 문서로는 시스템을 완벽하게 설계했지만, 시스템이 의도대로 돌아가는지 검증할 방법이 필요하다
  - 테스트코드가 그것이다.
- 테스트가 가능한 시스템을 만든다면 자연스럽게 크키가 작고, 목적 하나만 수행하는 클래스가 나와 SRP가 동시에 지켜진다. 
  - 테스트 케이스가 많을수록, 개발자는 테스트가 쉽게 코드를 작성한다. 
- 결합도가 높으면 테스트 케이스를 작성하기 어렵다. 
  - 개발자는 이 문제를 해결하기 위해 DI, DIP 등의 기법을 지켜 결합도를 낮추는 행동을 취해야한다.

### 단순한 설계규칙 2~4 : 리팩터링
- 테스트케이스를 모두 작성했다면 코드와 클래스를 정리해도 괜찮다. 구체적으로는 코드를 점진적으로 리팩터링 해 나간다.
  - 코드를 정리하면서 간간히 테스트를 수행하여 기존 시스템의 정상 유무를 확인하며 나아갈수있다. 
- 리팩터링 단계에서는 소프트웨어 설계 품질을 높이는 기법이라면 무엇이든 적용해도 괜찮다. 응집도를 높이고, 결합도를 낮추고, 관심사를 분리하고, 시스템 관심사를 모듈로 나누고, 클래스나 함수의 의도를 잘 나타내고...

### 중복을 없애라 
- 우수한 설계에서 중복은 옳지 않다. 중복은 추가작업, 추가위협, 불필요한 복잡도를 뜻하기 때문이다. 
```
//중복이 있는 코드 
int size() {
  return arr.length();
};
boolean isEmpty() {
  return 0 == arr.length();
}

//중복을 없앤 코드 
boolean isEmpty() {
    return 0 == size();
}
```
- 아래 함수를 리팩터링 해보자
```
// 기본코드
public void scaleToOneDimension (
  float desiredDimension, float imageDimension
) {
  if (Math.abs(desiredDimension - imageDimension) < errorThreshold ) {
    return;
  }
  float scalingFactor = desiredDimension / imageDimension;
  scalingFactor = (float)(Math.floor(scalingFactor * 100) * 0.01f);
  
  RenderedOp newImage = ImageUtilities.getScaledImage(
    image, scalingFactor, scalingFactor
  );
  image.dispose();
  System.gc();
  image = new Image;
}

public synchornized void rotate(int degree) {
    RenderedOp newImage = ImageUtilities.getRotatedImage(
    image, degree
  );
  image.dispose();
  System.gc();
  image = new Image;
}
```
```
// 이미지를 노출 시키는 로직이 중복된다. 분리하자
public void scaleToOneDimension (
  float desiredDimension, float imageDimension
) {
  if (Math.abs(desiredDimension - imageDimension) < errorThreshold ) {
    return;
  }
  float scalingFactor = desiredDimension / imageDimension;
  scalingFactor = (float)(Math.floor(scalingFactor * 100) * 0.01f);
  
  RenderedOp newImage = ImageUtilities.getScaledImage(
    image, scalingFactor, scalingFactor
  );
  
  replaceImage(newImage);
}

public synchornized void rotate(int degree) {
    RenderedOp newImage = ImageUtilities.getRotatedImage(
    image, degree
  );
  
  replaceImage(newImage);
}

private void replaceImage(RenderedOp newImage) {
  image.dispose();
  System.gc();
  image = new Image;
}
```
- 위처럼 함수를 분리하니 중복은 사라졌지만 SRP가 지켜지지 않은걸 확인할수 있다.
  1. 그림을 회전시키고 Scale Out/In 하는 기능
  2. 그림을 변경하는 기능 
- SRP를 지키기 위해 최종적으로 클래스를 분리하는것 까지 리팩토링이 계속된다. 
- 이토록 공통적은 메서드를 새 메서드로 뽑다 보면 클래스가 SRP를 위반함을 알수 있다. 

### 표현하라
1. 좋은 이름을 선택한다. 이름과 기능이 완전히 딴판인 클래스나 함수로 유지보수하는 담당자를 놀라게하면 안된다. 
2. 함수와 클래스 크기를 가능한 줄인다. 작은 클래스와 작은 함수는 이름 짓기도 쉽고, 구현하기도 쉽고, 이해하기도 쉽다. 
3. 표준 명칭을 사용한다. 클래스가 COMMAND가 VISITOR와 같은 표준 패턴을 사용해 구현된다면 클래스 이름에 패턴 이름을 넣어준다. 
4. 단위 테스트 케이스를 꼼꼼히 작성한다. 테스트 케이스는 소위 '예제'로 보여주는 문서 이다.
