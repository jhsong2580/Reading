# 클래스

---
### 클래스 체계
1. static, public 변수
2. private 변수
3. public 함수

### 캡슐화
- 변수와 함수는 공개하지 않는 편이 낫지만 반드시 숨겨야한다는 법칙도 없음.
- 하지만 캡슐화를 풀어주는 결정은 최후의 수단이므로 되도록이면 공개하지 말자

### 클래스는 작아야 한다. 
- 한 클래스에 많은 책임이 들어가면 안된다.
- 클래스의 이름은 "해당 클래스의 책임"을 기술해야 한다. 

### SRP(단일책임원칙)
- 클래스는 하나의 책임을 가지고 있어야 한다 
- 큰 클래스 하나가 아닌 작은 클래스 여러개로 이루어진 시스템이 더욱 바람직하다 .

### 응집도
- 클래스틑 인스턴스 변수가 작아야 한다. 
- 모든 인스턴스 변수를 메서드마다 사용하는 클래스가 응집도가 높다. 
- 아래 Stack이란 클래스틑 size를 제외한 모든 메서드가 모든 인스턴스 변수를 사용한다. 
- 구현하다보면 몇몇 메서드만 특정 인스턴스 변수들을 사용하는 모습들이 나오는데, 이 시점이 클래스를 나눠야 하는 시점이다. 
```
public class Stack {
    private int topOfStack = 0;
    List<Integer> elements = new LinkedList<Integer>();
    
    public int size(){
        return topOfStack;
    }
    
    public void push(int element){
        topOfStack++;
        elements.add(element);
    }   
    
    public int pop()  throws PoppedWhenEmpty {
        if (topOfStack == 0){
            throw new PoppedWhenEmpty();
        }
        
        int element = elements.get(--topOfStack);
        elements.remove(topOfStack);
        
        return element;
    }
}
```

### 응집도를 유지하려면 작은 클래스 여럿이 나온다. 
- 큰 함수를 작은 함수 여럿으로 나누기만 해도 클래스 수가 많아진다. 
- 큰 함수를 작은함수로 쪼개다 보면, 응집되는 인스턴스변수 - 함수를 찾을수 있게 되어 클래스를 쪼갤수가 있고, 이를 통해 프로그램에 점점 더 체계가 잡히고 구조가 투명해지게 된다. 

### 변경하기 쉬운 클래스 
- 대다수의 시스템은 지속적인 변경이 가해진다. 그리고 뭔가 변경할 때 마다 시스템이 의도적으로 동작하지 않을 위험이 따른다. 
  - 깨긋한 시스템은 클래스를 체계적으로 정리하여, 변경에 대한 위험성을 낮춘다.
- 아래는 Sql을 지원하는 클래스인데, 만약 update DDL을 지원하려면 반드시 Sql클래스를 손대야 한다. 그리고 select 쿼리에 대한 수정도 Sql 클래스를 수정해야하니 "SRP"를 위반하는 결과를 낳는다.
```
// 깨끗하지 않은 시스템 (변경이 어려움)
public class Sql { 
    public Sql(String table, Column[] colums);
    public String create();
    public String insert(Object[] fields);
    public selectAll();
    ... 
}
```
- 아래 코드는 각 쿼리의 공통기능(쿼리 생성 설정, 쿼리 생성)을 추상화하고 각 기능 별로 구현체로 만든 것이다. 
- 만약 select 쿼리에 대한 수정이 있을시, "SelectSql" Class만 수정을 할수 있고, 만약 Update쿼리를 추가한다면 해당 쿼리에 대한 구현체를 생성해 주면 될것이다. 
```
// 깨끗한 시스템
abstract public class Sql { 
    public Sql(String table, Column[] columns);
    abstract public String generate();
}

public class CreateSql extends Sql {
    public CreateSql(String table, Column[] columns);
    @Override public String generate();
}

public class SelectSql extends Sql {
    public CreateSql(String table, Column[] columns);
    @Override public String generate();
}
```

### 변경으로부터 격리
- 요구사항은 변하기 마련이고 그에 따라 코드도 변하기 마련이다. 
- 상세한 구현에 의존하는 클라이언트 클래스는, 의존하는 구현체가 변경되면 위험에 빠져 Interface, Abstract Class로 격리한다. 
  - DIP : OOP의 SOLID원칙중하나로써, 추상화에 의존해야지 구현체에 의존하면 안된다.
- 구현체에 의존하는 코드는 테스트 할때도 어렵다. 
```
public interface StockExchange { 
    Money currentPrice (String symbol); 
}

public class TokyoStockExchange implements StockExchange {
    Money currentPrice (String symbol){
        // 5분마다 값이 달라진다. 
    }
}

@ReqArgConstructor
public Portfolio { //StockExchange API로 부터 포트폴리오 값을 계산한다. 
    private final StockExchange exchange;
}

```
- 만약 위 코드 기반으로 Portfolio에 대한 테스트코드를 만들때, Portfolio Class가 TokyoStockExchange Class 구현체를 직접 의존하고있으면 5분마다 값이 달라지는 문제때문에 테스트가 불가능하다. 
- 위처럼 추상화에 의존하고있으면 StockExchange Interface를 상속하는, 고정된 값을 반환하는 테스트 구현체를 만들어 주입을 해주면 테스트가 가능하다. 

