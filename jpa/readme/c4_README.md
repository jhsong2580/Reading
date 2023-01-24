# 엔티티 매핑

---

## 테이블 별 유니크 제약조건 

```
@Table(name="MEMBER", uniqueConstraints = {@UniqueConstraint( //추가 //**
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"} )})
// NAME_AGE_UNIQUE 라는 이름의 제약조건을 만들고, "NAME"&"AGE" Column 이 유니크해야한다.
```

---

## 테이블 기본키 생성 전략
1. 직접할당
- 말 그대로, 엔티티 persist 전에 어플리케이션 내에서 ID를 설정 후 저장하는 전략이다.
```
@Id
@Column
private String id;

entity.setId("SET ID IN APPLICATION");

em.persist(entity);  
```
2. Identity 전략 
- 각 DBMS 전략 별 ID 자동생성(auto_increment..)
- JPA는 기본 키를 얻어오기 위해 데이터베이스를 추가로 조회한다.
- IDENTITY 식별자 생성 전략은 엔티티를 데이터베이스에 저장하여야 key값을 획득 가능하여, 쓰기 지연이 동작하지 않는다.  
```
@GeneratedValue(strategy = STRATEGY)

em.persist(entity) // save query 발생 

```

3. Sequence 전략
```
@GenerateValue(strategy = GenerateType.SEQUENCE,
                generator = "BOARD_SEQ_GENERATOR"
                )
                
em.persist(entity) // sequence table로 부터 id get query 발생, save는 미발생(쓰기지연저장소에 save sql저장)
```
- 사용할 데이터베이스 시퀀스를 매핑 후, 해당 시퀀스에서 ID값을 순차적으로 가져온다. 
- IDENTITY와는 다르게 persist 발생시 Sequence Table에서 ID를 가져와 Entity에 매핑 후 insert SQL은 쓰기지연 저장소에 저장한다.
  - 그 후 commit이 실행될때 DB에 넣어진다. 
- sequence 전략은 persist할때 2개의 쿼리가 나간다. 
  1. id 가져오기
  2. sequence table에 저장된 id값 +1
- 그렇기 때문에 데이터베이스에 쿼리 발생이 잦아 전략을 잘 활용해야함
  - allcationSize : 한번에 sequence id를 가져오는 갯수
    - 만약 50개를 가져올때 메모리에 시퀀스값을 저장하여 persist마다 메모리에서 id값을 가져오고, 시퀀스테이블에는 50을 더한다.

4. 테이블 전략
- 테이블 전략은 키 생성 전용 테이블을 하나 만들고 사용하는 것이며, 시퀀스 전략과 비슷하다.
- 이 전략은 테이블을 사용하므로 모든 데이터베이스에 적용할수 있다. (먼저 테이블을 생성 해 두어야 한다) 
```
DDL
create table MY_SEQUENCES(
  sequence_name varchar(255) not null,
  next_val bigint,
  primary key(sequence_name)
);
```
```
@TableGenerator(
  name = "BOARD_SEQ_GENERATOR", 
  table = "MY_SEQUENCES",
  pkColumeValue = "BOARD_SEQ", allocationSize = 1 // allocationSize : 시퀀스 한번 호출에 증가하는 수, pkColumeValue : 시퀀스 컬럼 명 여기서는 "sequence_name" 에 저장될 Key값 
)
...

@Id
@GenerateValue(strategy = GeterationType.TABLE,
  generator = "BOARD_SEQ_GENERATOR"
)
  
```

5. auto전략
- 데이터베이스에 따라 전략을 자동으로 선택한다.

  | DBMS      | Strategy |
  |:---------:|:--------:|
  | Oracle        |   SEQUENCE    |
  | MySQL |   IDENTITY    |

6. 정리
   1. 직접할당 : em.persist() 호출 전 application에서 직접 id저장해야함
   2. SEQUENCE : DB 시퀀스에서 id 가져온 후, DB 시퀀스의 ID 업데이트 진행 하고 ID 주입한다. 
   3. TABLE : 시퀀스용 테이블에서 id 가져온 후, DB 시퀀스의 ID 업데이트 진행 하고 ID 주입한다.
   4. IDENTITY : DB에 Entity Save 후에 식별자 값을 획득 후 영속성 컨텍스트에 저장한다. 