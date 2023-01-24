# 연관관계 매핑 기초 
- 키워드
  - 방향 : 양방향 / 단방향 이 있다. 
    - 객체에만 방향이 존재하고, 테이블 관계는 항상 양방향 관계이다. 
  - 다중성 : N:1, 1:N, 1:1, N:N 다중성이 있다. 
  - 연관관계의 주인 : 객체를 양방향 연관고나계로 만들면 연관관계의 주인을 정해야 한다(mappedBy)
---

### 단방향 연관관계
- N:1 단방향 연관관계
  - N쪽 Entity에서 관계를 설정한다(@ManyToOne)

--- 

### @JoinColumn
1. name : 매핑할 외래키 이름 (default : 필드명+_+참조하는테이블컬럼명)
2. referencedColumnName : 외래키가 참조하는 대상 테이블 컬럼 명 
3. foreignKey : 외래키 제약조건 직접 지정(ddl create/update 일때 사용 가능)

---
---

# 연관관계 사용

### 조회
```
String jpql = "select m from Member m join m.team t where " + "t.name=:teamName";

List<Member> members = em.createQuery(jpql, Member.class)
    .setParameter("teamName","팀1")
    .getResultList();
```

---
---

# 연관관계의 주인

- 객체에는 양방향 연관관계라는 것이 없다. 서로 다른 단방향 관계 2개를 묶어 양방향인 것처럼 보이게 할뿐이다. 

