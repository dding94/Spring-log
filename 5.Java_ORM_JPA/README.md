# 출처
https://www.inflearn.com/course/ORM-JPA-Basic/dashboard
강사 : 김영한님

#핵심 정리
## 자바 ORM 표준 JPA 프로그래밍 - 기본편

JPA란?
- Java Persistence API
- 자바 진영의 ORM 기술 표준
- JPA는 인터페이스의 모음

ORM?
- Object-relational mapping(객체 관계 매핑)
- 객체는 객체대로, 관겨형 데이터베이스는 관겨형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑

JPA를 왜 사용해야 하는가?
- SQL 중심적인 개발에서 객체 중심으로 개발★
- 생산성
  - 저장: jpa.persist(member)
  - 조회: Member member = jpa.find(memberId)
  - 수정: memeber.setName("변경할 이름")
  - 삭제: jpa.remove(member)
- 유지보수 
  - 필드만 추가하면 SQL 은 JPA 가 처리
- 패러다임의 불일치 해결
- 성능
- 데이터 접근 추상화와 벤더 독립성
- 표준

 JPA의 성능 최적화 기능
 - 1차 캐시와 동일성(iedntity) 보장
   - 같은 트랜잭션 안에서는 같은 엔티티를 반환 - 약간의 조회 성능 향상
   - DB Isolation Level이 Read Commit 이어도 애플리케이션에서 Repeatable Read 보장 (DB를 잘 알아야함)
 - 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
   - 트랜잭션을 커밋할 때까지 INSERT SQL을 모음
   - JDBC BATCH SQL 기능을 사용해서 한번에 SQL 전송
 - 지연 로딩(Lazy Loading)
   - 지연 로딩: 객체가 실제 사용될 때 로딩
   - 즉시 로딩: JOIN SQL로 한번에 연관된 객체까지 미리 조회
   - 옵션을 사용해서 편하게 바꿀 수 있음
---
## Hello JPA - 애플리케이션 개발

주의
- 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
- 엔티티 매니저는 쓰레드간 공유X (사용하고 버려야 한다.)
- JPA의 모든 데이터 변경은 트랜잭션 안에서 실행

JPA를 사용하면 엔티티 객체를 중심으로 개발
- 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
- 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
- SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공

JPQL 란?
- 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
- 한마디로 객체 지향 SQL
- 엔티티 객체를 대상으로 쿼리

JPA에서 가장 중요한 2가지
- 객체와 관계형 데이터베이스 매핑하기
- 영속성 컨텍스트

### 영속성 컨텍스트
- JPA를 이해하는데 가장 중요한 용어
- "엔티티를 영구 저장하는 환경" 이라는 뜻
- EntityManager.persist(entity) 엔티티 매니저를 통해서 영속성 컨텍스트에 접근

엔티티의 생명주기
- 비영속 (new/transient): 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
- 영속 (managed): 영속성 컨텍스트 관리되는 상태
- 준영속 (detached): 영속성 컨텍스트에 저장되었다가 분리된 상태
- 삭제 (removed): 삭제된 상태

영속성 컨텍스트의 이점
- 1차 캐시
  - 동일 트랜잭션 안에서 한번 조회를하고 다시 조회를 할 때 db 에 쿼리가 날라가지 않는다.
  - <details><summary> 이미지 </summary>

    ![image](https://user-images.githubusercontent.com/21376853/153712357-c19078c3-1e52-42dc-81aa-dc1746ecf68e.png)
  </details>
   
- 동일성(identity) 보장
- 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
  - 커밋을 해야 DB에 쿼리를 날린다.
  - <details><summary> 이미지 </summary>

    ![image](https://user-images.githubusercontent.com/21376853/153712512-ae36c9f7-5f5b-4097-b423-bca129e054e7.png)
    </details>

- 변경 감지(Dirty Checking)
    - 영속성 엔티티 데이터 수정 후 커밋을 하면 기존 스냅샷과 비교해서 커밋때 JPA가 업데이트 쿼리도 자동으로 보냄.
    - <details><summary> 이미지 </summary>

      ![image](https://user-images.githubusercontent.com/21376853/153712528-c7b6afc3-ffcd-4490-8010-3d3679369789.png)
      </details>
- 지연 로딩(Lazy Loading)

### 플러시
- 영속성 컨텍스트를 비우지 않음 (실제 데이터베이스에 반영되는 것은 아님)
- 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
- 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 됨

사용 방법
- em.flush() - 직접 호출
- 트랜잭션 커밋 - 플러시 자동 호출
- JPQL 쿼리 실행 - 플러시 자동 호출

플러시 발생
- 변경 감지
- 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
- 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송

### 준영속 상태
- 영속 -> 준영속
- 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
- 영속성 컨텍스트가 제공하는 기능을 사용 못함

준영속 상태로 만드는 방법
- em.detach(entity)
  - 특정 엔티티만 준영속 상태로 전환
- em.clear()
  - 영속성 컨텍스트를 완전히 초기화
- em.close()
  - 영속성 컨텍스트를 종료
---
## 엔티티 매핑

### @Entity
- @Entity가 붙은 클래스는 JPA가 관리, 엔티티라 한다.
- JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 가 필수
- 주의
  - 기본 생성자 필수(파라미터가 없는 public 또는 protected 생성자)
  - final 클래스, enum, interface, inner 클래스 사용X
  - 저장할 필드에 final 사용 X

__@Entity 속성 정리__      
속성: name
- JPA에서 사용할 엔티티 이름을 지정한다.
- 기본값: 클래스 이름을 그대로 사용
- 같은 클래스 이름이 없으면 가급적 기본값을 사용한다.

__@Table__    
- @Table은 엔티티와 매핑할 데이블 지정
- <details><summary> 자세히 </summary>

  ![image](https://user-images.githubusercontent.com/21376853/153758327-5d43a366-e7b8-4e63-905f-a5b049518040.png)
  </details>

### 데이터베이스 스키마 자동 생성

DDL(Data Definition Language)  
- 데이터 정의어 란? 데이터베이스를 정의하는 언어이며, 데이터리를 생성, 수정, 삭제하는
등의 데이터의 전체의 골격을 결정하는 역할을 하는 언어 입니다.

데이터베이스 스키마 자동 생성
- DDL을 애플리 케이션 실행 시점에 자동 생성
- 테이블 중심 -> 객체 중심
- 개발 장비에서만 사용
- 속성
  - <details><summary> 이미지 </summary>

    ![image](https://user-images.githubusercontent.com/21376853/153845688-dbea0db7-6db6-4fda-966f-6b699da2a1e1.png)
  </details>
  
주의할 점
- 운영 장비에는 절대 create, create-drop, update 사용하면 안된다.
- 개발 초기 단계: create or update
- 테스트 서버: update or validate
- 스테이징과 운영 서버: validate or none

DDL 생성 기능
- 제약 조건 추가
  - ex) `@Column(nullable = false, length = 10)`
- 유니크 제약 조건 추가
  - `@Table(uniqueConstraints = {@UniqueConstraint( name = "NAME_AGE_UNIQUE",
    columnNames = {"NAME", "AGE"} )}) `
- DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.

### 필드와 컬럼 매핑


1. @Column
    - <details><summary>이미지 </summary>

      ![image](https://user-images.githubusercontent.com/21376853/153852658-66196d20-2ed5-4ee8-94ba-43a72b75d5fc.png)

   </details>    

2. @Enumerated
   - 자바 enum 타입을 매핑할 때 사용
   - 주의! ORDINAL 사용XXXXXXXXXXX
     - ORDINAL 을 사용하다 자바 enum 타입에 새로운 데이터가 추가되면 데이터베이스가 꼬일 수 있음.
   - ex) `@Enumerated(EnumType.STRING)`  기본값은 `EnumType.ORDINAL`

3. @Temporal
   - 날짜 타입을 매핑할 때 사용
   - LocalDate, LocalDateTime 을 사용할 때는 생략이 가능하다.
   - 사용 예) `@Temporal(TemporalType.TIMESTAMP)`

4. @Lob
   - 데이터베이스 BLOB, CLOB 타입과 매핑
   - @Lob에는 지정할 수 있는 속성이 없다.
   - 매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑

5. @Transient
   - 필드 매핑 X
   - 데이터베이스에 저장X, 조회X
   - 주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용
   - ex) `@Transient private int temp;`

### 기본 키 매핑

기본 키 매핑 어노테이션
- @ID
  - 직접할당: @ID만 사용
- @GeneratedValue
  - 자동생성
  - IDENTITY: 데이터베이스에 위임, MYSQL
  - SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE
    - @SequenceGenerator 필요
  - TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용
    - @TableGenerator 필요
  - AUTO: 방언에 따라 자동 지정, 기본값

IDENTITY 전략 - 특징
- 기본 키 생성을 데이터베이스에 위임
- 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용
- AUTO_INCREMENT는 데이터베이스에 INSERT SQL을 실행 한 이후에 ID 값을 알 수 있음
- IDENTITY전략은 em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회
- <details><summary> 매핑 방법</summary>

  ![image](https://user-images.githubusercontent.com/21376853/153866687-57bc0085-4fe9-4767-a7ec-1bf497e61477.png)

  </details>

SEQUENCE 전략 - 특징
- 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트(예: 오라클 시퀀스)
- 오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용
- <details><summary> 매핑 방법</summary>

  ![image](https://user-images.githubusercontent.com/21376853/153866833-3e3501e1-c766-4393-9aff-ecc83bb8400b.png)

  </details>

SEQUENCE - @SequenceGenerator 속성 // 설명 // 기본값
- `name`: 식별자 생성기 이름 - 필수
- `sequenceName`: 데이터베이스에 등록되어 있는 시퀀스 이름 - hibernate_sequence
- `initialValue`: DDL 생성시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 1 시각하는 수를 지정한다. - 1
- `allocationSize`: 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용, 데이터 베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있으면 이 값을 반드시 1로 설정) - 50
- `catalog`, `schema`: 데이터베이스 catalog, schema 이름

TABLE 전략
- 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
- 장점 : 모든 데이터베이스 적용 가능
- 단점 : 성능
- <details><summary> 매핑 방법</summary>

  ![image](https://user-images.githubusercontent.com/21376853/153877516-aa739160-2234-4a62-a863-5230fad21196.png)
  </details>

### (실전 예제1) 데이터 중심 설계의 문제점 
- 객체 설계를 테이블 설계에 맞춘 방식이다.
- 테이블의 외래키를 객체에 그대로 가져옴
- 객체 그래프 탐색이 불가능
- 참조가 없으므로 UML도 잘못됨

---
## 연관관계 매핑 기초

목표
- 객체와 테이블 연관관계의 차이 이해
- 객체의 참조와 테이블의 외래 키를 매핑
- 용어 이해
  - 방향(Direction): 단방향, 양방향
  - 다중성(Multiplicity): 다대일(N:1), 일대다(1:N), 일대일(1:1), 다대다(N:M) 이해
  - 연관관계의 주인(Owner): 객체 양방향 연관관계는 관리 주인이 필요

연관관계가 필요한 이유
- 객체지향 설계의 목표는 자율적인 객체들의 협력 공동체를 만드는 것이다.

객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력 관계를 만들 수 없다
- 테이블은 외래키로 조인을 사용해서 연관된 테이블을 찾는다.
- 객체는 참조를 사용해서 연관된 객체를 찾는다.
- 테이블과 객체 사이에는 이런 큰 간격이 있다.

단방향 연관관계
- N : 1 일 때 `@ManyToOne`, `@JoinColuim(name = "외래 키"`)` 사용

### 양방향 연관관계와 연관관계의 주인 1 - 기본

양방향 매핑

객체와 테이블이 관계를 맺는 차이
- 객체 => 연관관계 2개
  - 객체의 양방향 관계는 사실 양방향 관계가 아니다.
  - ex) 회원 -> 팀 연관관계 1개(단방향)
  - ex) 팀 -> 회원 연관관계 1개(단방향)
- 테이블 => 연관관계 1개
  - 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리
  - 회원 <-> 팀의 연관관계 1개(양방향)

    
### ★연관관계의 주인(Owner)   
양방향 매핑 규칙
- 객체의 두 관계중 하나를 연관관계의 주인으로 지정
- 연관관계의 주인만이 외래 키를 관리(등록, 수정)
- 주인이 아닌쪽은 읽기만 가능
- 주인은 mappedBy 속성 사용X
- 주인이 아니면 mappedBy 속성으로 주인 지정


### 양방향 연관관계와 연관관계의 주인 2 - 주의
- 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자.
  - em.flush 전에 값을 조회할때 1차캐시에 있는 값을 조회하는 이슈, 테스트 코드 작성시 문제될 가능성
- 연관관계 편의 메소드를 생성하자
  - this를 활용, 메소드의 이름은 setter가 아닌 다른 이름을 사용하자
- 양방향 매핑시 무한루프 주의
  - toString(), lombok, JSON 생성 라이브러리
  - 컨트롤러에서 엔티티를 반환 X !! dto 로 변환해서 반환

양방향 매핑 정리
- 단방향 매핑만으로도 이미 연관관계 매핑은 완료
- 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐이다.
- JPQL 에서 역방향으로 탐색할 일이 많음
- JPA 설계할 때 일단 단방향 매핑으로 설계를 끝내고 양방향은 필요할 때 추가해도 됨. (테이블에 영향을 주지 않음)

연관관계의 주인을 정하는 기준
- 비즈니스 로직을 기준으로 연관관계의 주인을 선택하면 안됨
- 연관관꼐의 주인은 외래 키의 위치를 기준으로 정해야함 (N인 경우)

---
## 다양한 연관관계 매핑

연관관계 매핑시 고려사항 3가지
- 다중성
  - 다대일 (`@ManyToOne`), 일대다(`@OneToMany`), 일대일(`@OneToOne`), 다대다(`@ManyToMany`)
- 단방향, 양방향
  - 테이블
    - 외래 키 하나로 양쪽 조인 가능
    - 사실 방향이라는 개념이 없음.
  - 객체
    - 참조용 필드가 있는 쪽으로만 참조 가능
    - 한쪽만 참조하면 단방향
    - 양쪽이 서로 참조하면 양방향
- 연관관계의 주인
  - 테이블은 외래 키 하나로 두 테이블이 연관관계를 맺음
  - 객체 양방향 관계는 A->B, B->A 처럼 참조가 2군데
  - 객체 양방향 관계는 참조가 2군데 있음. 둘중 테이블의 외래 키를 관리할 곳을 지정해야함
  - 연관관계의 주인: 외래 키를 관리하는 참조
  - 주인의 반대편: 외래 키에 영향을 주지 않음, 단순 조회만 가능

### 다대일 (N:1)

다대일 단방향
- 가장 많이 사용하는 연관관꼐
- 다대일의 반대는 일대다

다대일 양방향
- 외래 키가 있는 쪽이 연관관계의 주인
- 양쪽을 서로 참조하도록 개발

### 일대다 (1:N)
일대다 단방향
- <details><summary>실무에서 잘 사용 안하지만 정리함 </summary>

    - 일대다 단방향은 일대다(1:N) 에서 일(1)이 연관관계의 주인
    - 테이블 일대다 관계는 항상 다(N) 쪽에 외래 키가 있음
    - 객체와 테이블의 차이 때문에 반대편 테이블의 외래 키를 관리하는 특이한 구조
    - @JoinColumn을 꼭 사용해야함. 그렇지 않으면 조인 테이블 방식을 사용하여 중간에 테이블 하나 추가함
    - 단점
        - 엔티티가 관리하는 외래 키가 다른 테이블에 있음
        - 연관관계 관리를 위해 추가로 UPDATE SQL 실행
        - 일대다 단방향 매핑보다 다대일 양방향 매핑을 사용하자!

</details>

### 일대일 (1:1)

일대일 관계
- 일대일 관계는 그 반대도 일대일
- 주 테이블이나 대상 테이블 중에 외래 키 선택 가능
- 주 테이블 양방향 매핑은 외래 키가 있는 곳이 연관관계의 주인 반대편은 mappedBy 적용
- 대상 테이블에 외래 키 단방향정리는 지원 X, 양방향 관계를 써야한다.

주 테이블에 외래 키
- 주 객체가 대상 객체의 참조를 가지는 것 처럼, 주 테이블에 외래 키를 두고 대상 테이블을 찾음
- 객체지향 개발자 선호
- JPA 매핑 편리
- 장점: 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
- 단점: 값이 없으면 외래 키에 null 허용

대상 테이블에 외래 키
- 대상 테이블에 외래 키가 존재
- 전통적인 데이터베이스 개발자 선호
- 장점: 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
- 단점: 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨

### 다대다 (N:M)
실무에서 쓰이지 않는다..!

다대다
- 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없다.
- 중간에 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야한다.
- @ManyToMany 사용
- 연결 테이블이 단순히 연결만 하고 끝나지 않는다. 중간 테이블에 추가 정보를 넣지 못한다.
- 한계극복: 연결 테이블용 엔티티를 추가하여 다대다를 일대다 다대일 관계로 만든다
---
## 고급 매핑

### 상속관계 매핑
- 관계형 데이터베이스는 상속 관계가 없다.
- 슈퍼타입 서브타입 관계라는 모델링 기법이 객체 상속과 유사
- 상속관계 매핑: 객체의 상속과 구조 와 DB의 슈퍼타입 서브타입 관계를 매핑

슈퍼타입 서브타입 논리 모델을 실제 물리 모델로 구현하는 방법
- 각각 테이블로 변환 -> 조인 전략 (기본전략이라고 생각)
- 통합 테이블로 변환 -> 단일 테이블 전략
- 서브타입 테이블로 변환 -> 구현 클래스마다 테이블 전략(사용안함)

주요 어노테이션
- `@Inheritance(strategy=InheritanceType.XXX)`
  - `JOINED`: 조인 전략
  - `SINGLE_TABLE`: 단일 테이블 전략
  - `TABLE_PER_CLASS`: 구현 클래스마다 테이블 전략
- `@DiscriminatorColumn(name="DTYPE")`
  - 부모 클래스에 선언, 하위 클래스를 구분하는 용도
- `@DiscriminatorValue("XXX")`
  - 하위 클래스에 선언, 엔티티를 저장할 때 슈퍼타입의 구분 컬럼에 저장할 값을 지정
  - 지정을 따로 안할 경우 기본값으로 클래스 이름이 들어간다.
  
<details><summary> 조인 전략 </summary>

 ![image](https://user-images.githubusercontent.com/21376853/154309202-d4631ca9-aaaa-4054-8513-25c6c4a67da7.png)
</details>

- 장점
  - 테이블 정규화
  - 외래 키 참조 무결성 제약조건 활용 가능
  - 저장공간 효율화
- 단점
  - 조회시 조인을 많이 사용, 성능 저하
  - 조회 쿼리가 복잡함
  - 데이터 저장시 INSERT SQL 2번 호출

<details><summary> 단일 테이블 전략 </summary>


![image](https://user-images.githubusercontent.com/21376853/154309590-6d5b16d0-6cc1-49fe-9c6e-d43847e411aa.png)
</details>

- 장점
  - 조인이 필요 없으므로 일반적으로 조회 성능이 빠름
  - 조회 쿼리가 단순함
- 단점
  - 자식 엔티티가 매핑한 컬럼은 모두 null 허용
  - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다.
  - 상황에 따라서 조회 성능이 오히려 느려질 수 있다.

구현 클래스마다 테이블 전략
- 추천하지 않는 전략


### @MappedSuperclass
- 상속관계 매핑이 아님.
- 엔티티 X, 테이블과 매핑X 
- 테이블과 관계 없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할
  - 주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용
- 부모 클래스를 상속받는 자식 클래스에 매핑 정보만 제공
- 조회, 검색 불가(`em.find(BaseEntity)` 불가)
- 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장
[참고]: `@Entity` 클래스는 엔티티나 `@MappedSuperclass`로 지정한 클래스만 상속 가능

---
## 프록시와 연관관계 정리

### 프록시

기초
- `em.find()`: 데이터베이스를 통해서 실제 엔티티 객체 조회 
- `em.getReference()`: 데이터베이스 조회를 미루는 가짜(프록시)엔티티 객체 조회

프록시 특징
- 실제 클래스를 상속 받아서 만들어 지며 겉 모양이 같다.
- 프록시 객체는 실제 객체의 참조를 보관하여 호출을하면 실제 객체의 메소드를 호출한다.
- 프록시 객체는 처음 사용할 때 한 번만 초기화 된다.
- 프록시 객체를 초기화 할 때 프록시 객체가 실제 엔티티로 바뀌는게 아니라 프록시 객체를 통하여 실제 엔티티에 접근 가능하게 된다.
- 프록시 객체는 원본 엔티티를 상속받아 타입체크시 `==` 사용 하면 안된다. `instance of` 사용
- 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 `em.getReference()`를 호출해도 실제 엔티티 반환
  - JPA는 동일성을 보장하기 때문에, 같은 트랜잭션 안에서는 같은 엔티티를 반환
- 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생

### 즉시 로딩과 지연 로딩

지연 로딩
- 지연 로딩 LAZY을 사용해서 __프록시로 조회__
- 실제 엔티티를 사용하는 시점에 초기화(DB조회)

즉시로딩
- 한번에 관련 엔티티 전부 조회

프록시와 즉시로딩 주의
- 실무에서 지연 로딩만 사용
- 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
- 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다
  - 해결방법은 JPQL fetch 조인, 엔티티 그래프 기능 사용
- `@ManyToOne`, `@OneToOne` 은 디폴트값이 즉시 로딩 => LAZY로 설정!

### 영속성 전이 (CASCADE)
- 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속상태로 만들고 싶을 때
- ex) 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장
-`OneToMany(mappedBy="parent", cascade=CascadeType.PERSIST)
- 주의
  - 영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없음
  - 엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공
- 종류
  - ALL, PERSIST, REMOVE, MERGE, REFRESH, DETACH

CASCADE 사용할 때 참고
- 파일을 여러곳에서 관리, 다른 엔티티에서 관리하면 사용하면 안됨
- 단일 엔티티에 종속적일때 사용
- 단일 소유자, 라이프사이클이 같을 때 사용

### 고아 객체
- 고아 객체 제거: 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
- `orphanRemoval = true`
- 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능
- 참조하는 곳이 하나일 때 사용해야함!
- 특정 엔티티가 개인 소유할 때 사용
- `@OneToOne`, `@OneToMany`만 가능

### 영속성 전이 + 고아 객체, 생명주기
- CascadeType.ALL + orphanRemovel=true
- 스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거
- 두 옵션을 모두 활성화 화면 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있다.
- DDD 의 Aggregate Root 개념을 구현할 때 유용
---
## 값 타입

JPA의 데이터 타입 분류
- 엔티티 타입
  - `@Entity` 로 정의하는 객체
  - 데이터가 변해도 식별자로 지속해서 추적 가능
  - 예) 회원 엔티티의 키나 나이 값을 변경해도 식별자로 인식 가능
- 값 타입
  - int, Integer, String 처럼 단순히 값으로 사용하는 자바 기본타입이나 객체
  - 식별자가 없고 값만 있으므로 변경시 추적 불가
  - 예) 숫자 100을 200으로 변경하면 완전히 다른 값으로 대체
  
### 기본값 타입
- ex) String name, int age
- 생명주기를 엔티티에 의존
  - ex) 회원을 삭제하면 이름, 나이 필드도 함께 삭제
- 값 타입은 공유하면 안된다.
  - ex) 회원의 이름 변경시 다른 회원의 이름도 함께 변경되면 안됨

[참고]: 자바의 기본 타입(primitive type)은 절대 공유 X

### 임베디드 타입
- 새로운 값 타입을 직접 정의할 수 있음
- JPA는 임베디드 타입(embedded type)이라 함
- 주로 기본 값 타입을 모아 만들어서 복합 값 타입이라고도 함
  - ex) 클래스에 묶는거 (좌표, 주소 등등)
- 임베디드 타입 값이 null 이면 매핑한 컬럼 값은 모두 null

사용법
- `@Embeddable`: 값 타입을 정의하는 곳에 표시
- `@Embedded`: 값 타입을 사용하는 곳에 표시
- 기본 생성자 필수

장점
- 재사용, 높은 응집도
- 의미있는 메소드를 만들 수 있음
- 임베디드 타입을 포함한 모든 값 타입은, 값 타입을 소유한 엔티티에 생명주기를 의존함

임베디드 타입과 테이블 매핑
- 임베디드 타입은 엔티티의 값일 뿐이다.
- ★임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
- 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능

@AttributeOverride: 속성 재정의
- 한 엔티티에서 같은 값 타입을 사용하려면 `@AttributeOverrides`, `@AttributeOverride`를 사용해서 컬러 명 속성을 재정의

### 값 타입과 불변 객체

값 타입 공유 참조
- 임베디드 타입이 같은 값을 여러 엔티티에 공유하면 위험하다.
- 부작용(side effect) 발생

값 타입 복사
- 값 타입의 실제 인스턴스인 값을 공유하는 것은 위험
- 대신 값(인스턴스)를 복사해서 사용

객체 타입의 한계
- 값을 복사해서 사용하면 공유참조로 인해 발생하는 부작용을 피할 수 있다.
- 문제는 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본 타입이 아니라 객체 타입이다.
- 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다.
- 객체의 공유 참조는 피할 수 없다.

불변 객체
- 생성 시점 이후 절대 값을 변경할 수 없는 객체
- 객체 타입을 수정할 수 없게 만들어 부작용을 원천 차단.
- 값 타입은 불변 객체(immutable object)로 설계해야한다.
- 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않는다!

### 값 타입의 비교
- 동일성(identity) 비교: 인스턴스의 참조 값을 비교, == 사용
- 동등성(equivalence) 비교: 인스턴스의 값을 비교, equals() 사용
- 값 타입의 equals() 메소드를 적절하게 재정의(주로 모든 필드)

### 값 타입 컬렉션
- 값 타입을 하나 이상 저장할 때 사용
- `@ElementCollection`, `@CollectionTable` 사용
- 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
- 컬렉션을 저장하기 위한 별도의 테이블이 필요함

값 타입 컬렉션의 제약사항
- 값 타입은 엔티티와 다르게 식별자 개념이 없다.
- 값은 변경하면 추적이 어렵다.
- 캆 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장
- 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야 함: null 입력X, 중복 저장 X

값 타입 컬렉션 대안
- 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려
- 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
- 영속성 전이(Cascade) + 고아 객체 제거를 사용해서 값 타입 컬렉션 처럼 사용

정리
- 엔티티 타입의 특징
  - 식별자 O
  - 생명 주기 관리
  - 공유
- 값 타입의 특징
  - 식별자 X
  - 생명 주기를 엔티티에 의존
  - 공유하지 않는 것이 안전(복사해서 사용)
  - 불변 객체로 만드는 것이 안전

[주의]
- 값 타입은 정말 값 타입이라 판단될 때만 사용
- 엔티티와 값 타입을 혼동해서 엔티티를 값 타입으로 만들면 안됨
- 식별자가 필요하고, 지속해서 값을 추척 및 변경해야 한다면 그것은 값 타입이 아닌 엔티티이다.

---
## 객체지향 쿼리 언어 소개

### JPA는 다양한 쿼리 방법을 지원
- JPQL★
- JPA Criteria
- QueryDSL★
- 네이티브 SQL
- JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용

JPQL
- SQL을 추상화한 JPQL 이라는 객체 지향 쿼리 언어 제공, SQL과 문법 유사
- SQL을 추상화 해서 특정 데이터베이스 SQL에 의존 X , 객체지향 SQL 이다.
- 엔티티 객체 중심 개발
- 테이블이 아닌 엔티티 객체를 대상으로 검색
- 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능

QueryDSL
- 문자가 아닌 자바코드로 JPQL을 작성할 수 있다.
- JPQL 빌더 역할
- 컴파일 시점에 문법 오류를 찾을 수 있다.
- 동적쿼리 작성 편리
- 단순하고 쉽다.
- 실무사용 권장!

JPQL 문법   

- <details><summary> 종류 </summary>

  ![image](https://user-images.githubusercontent.com/21376853/154942835-7828d803-c93c-4055-90f2-5328fafab88b.png)

</details>

- ex) select m from Member as m where m.age > 18
- 엔티티와 속성은 대소문자 구분O (Member, age)
- JPQL 키워드는 대소문자 구분X (SELECT, FROM, where)
- 엔티티 이름 사용, 테이블 이름이 아님!
- 별칭은 필수(m) (as는 생략가능)

집합과 정렬
- select
  - count(m), //회원 수
  - sum(m.age), //나이 합
  - AVG(m.age), //평균 나이
  - MAX(m.age), //최대 나이
  - MIN(m.age) //최소 나이
- from Member m

TypeQuery, Query
- TypeQuery: 반환 탕비이 명확할 때 사용
- Query: 반환 타입이 명확하지 않을 때 사용

결과 조회 API
- query.getResultList(): 결과가 하나 이상일 때 리스트 반환
- query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환
  - 결과가 없거나, 둘 이상이면 Exception

### 프로젝션
- SELECT 절에 조회할 대상을 지정하는 것
- 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등 기본 데이터 타입)
  - SELECT m FROM Member m -> 엔티티 프로젝션
  - SELECT m.team FROM Member m -> 엔티티 프로젝션
  - SELECT m.address FROM Member m -> 임베디드 타입 프로젝션
  - SELECT m.username, m.age FROM Member m -> 스칼라 타입 프로젝션
  - DISTINCT 로 중복 제거

프로젝션 - 여러 값 조회
- SELECT m.username, m.age FROM Member m
1. QUery 타입으로 조회
2. Object[] 타입으로 조회
3. new 명령어로 조회
   1. 단순 값을 DTO로 바로 조회
   2. SELECT new jpql.UserDTO(m.useranme, m.age) FROM Member m
   3. 패키지 명을 포함한 전체 클래스 명 입력
   4. 순서와 타입이 일치하는 생성자 필요

### 페이징 API
- JPA는 페이징을 다음 두 API로 추상화 (방언에 따라 쿼리문이 달라짐)
    - <details><summary> 예시 </summary>
      
      ![image](https://user-images.githubusercontent.com/21376853/154954293-15e9d112-025a-4e79-b23d-2c6eca409943.png)

  </details>

- setFirstResult(int startPosition): 조회 시작 위치(0부터 시작)
- setMaxResults(int maxResult): 조회할 데이터 수

### 조인
- 내부 조인:
  - SELECT m FROM Member m [INNER] JOIN m.team t
- 외부 조인:
  - SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
- 세타 조인:
  - select count(m) from Member m, Team t where m.usernmae = t.name

조인 - ON 절
- ON절을 활용한 조인(JPA2.1부터)
  1. 조인 대상 필터링
     - 예) 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인
     - SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
  2. 연관관계 없는 엔티티 외부 조인
     - 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
     - SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name

### 서브 쿼리
서브 쿼리 지원 함수
- [NOT] EXISTS (subquery): 서브쿼리에 결과가 존재하면 참
  - {ALL | ANY | SOME} (subquery)
  - ALL 모두 만족하면 참
  - ANY, SOME: 같은 의미, 조건을 하나라도 만족하면 참
- [NOT] IN (subquery): 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참

JPA 서브 쿼리 한계
- JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
- SELECT 절도 가능(하이버네이트에서 지원)
- FROM 절의 서브 쿼리는 현재 JPQL에서 불가능
  - 조인으로 풀 수 있으면 풀어서 해결

### JPQL 타입 표현
- 문자: 'HELLO', 'She''s'
- 숫자: 10L(Long), 10D(Double), 10F(Float)
- Boolean: TRUE, FALSE
- ENUM: jpabook.MemberType.Admin(패키지명 포함)
- 엔티티 타입: TYPE(m) = Member(상속 관계에서 사용)

### 조건식 - CASE 식
- COALESCE: 하나씩 조회해서 null이 아니면 반환
  - 사용자 이름이 없으면 이름 없는 회원을 반환
  - select coalesce(m.username, '이름 없는 회원') from member m
- NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
  - 사용자 이름이 '관리자'면 null을 반환하고 나머지는 본인의 이름을 반환
  - select NULLIF(m.username, '관리자') from Member m

### JPQL 기본 함수
- CONCAT
- SUBSTRING
- TRIM
- LOWER, UPPER
- LENGTH
- LOCATE
- ABS, SQRT, MOD
- SIZE, INDEX(JPA 용도)

사용자 정의 함수 호출
- 하이버네이트는 사용전 방언에 추가해야 한다.
  - 사용하는 DB 방언을 상속받고, 사용자 정의함수를 등록한다.
---
## 객체지향 쿼리 언어2 - 중급 문법

### 경로 표현식
- `.`(점)을 찍어 객체 그래프를 탐색 하는 것

경로 표현식 용어 정리
- 상태 필드(state field): 단순히 값을 저장하기 위한 필드
  - 경로 탐색의 끝, 탐색 X
- 단일 값 연관 필드: `@ManyToOne`, `OneToOne`, 대상이 엔티티 (ex: m.team)
  - 묵시적 내부 조인(inner join)발생, 탐색 O
- 컬렉션 값 연관 필드: `@OneToMany`, `@ManyToMany`, 대상이 컬렉션 (ex: m.orders)
  - 묵시적 내부 조인 발생, 탐색 X
  - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능

명시적 조인
- join 키워드를 직접 사용
  - ex) select m from Member m join m.team t
- 묵시적 조인: 경로 표현식에 의해 묵시적으로 SQL 조인 발생(내부 조인만 가능)
  - ex) select m.team from Member m

실무에서는?
- 가급적 묵시적 조인 대신에 명시적 조인 사용!
- 조인은 SQL 튜닝에 중요 포인트
- 묵시적 조인은 조인이 일어나는 상황을 하눈에 파악하기 어려움

## JPQL - 페치 조인(fetch join)★ - 실무에서 정말 중요

페치 조인(fetch join)
- SQL 조인 종류 X
- JPQL 에서 성능 최적화를 위해 제공하는 기능
- 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능
- N+1문제를 해결할 수 있음.
- join fetch 명령어 사용

엔티티 페치 조인 ex)
- 회원을 조회하면서 연관된 팀도 함께 조회(SQL 한번에)
- SQL을 보면 회원 뿐만 아니라 팀(T.*) 도 함께 SELECT
- JPQL : `select m from Member m join fetch m.team`
  - 페치 조인으로 함께 조회하기 때문에 지연 로딩 X
- SQL : `SELECT M.*, T,* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID = T.ID`

페치 조인과 DISTINCT
- SQL의 DISTINCT 는 중복된 결과를 제거하는 명령
- JPQL의 IDSTINCT 2가지 기능 제공
  - 1.SQL에 DISTINCT를 추가
  - 2.애플리케이션에서 엔티티 중복 제거

페치 조인과 일반 조인의 차이
- JPQL은 결과를 반환할 때 연관관계 고려 X
- 단지 SELECT 절에 지정한 엔티티만 조회할 뿐
- 페치 조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시 로딩)
- __페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념__

페치 조인의 특징과 한계
- 페치 조인 대상에는 별칭을 줄 수 없다.
  - 하이버네이트는 가능, 가급적 사용 XXX
- 둘 이상의 컬렉션은 페치 조인 할 수 없다.
- 컬렉션을 페치 조인하면 페이징 API(`setFirstResult`, `setMaxResults`)를 사용할 수 없다.
  - N:1 은 문제가 없다. 1:N 이면 뒤집어서 사용
  - `@BatchSize` 로 해결 가능

### JPQL - 다형성 쿼리 (중요하진 않음)

TYPE
- 조회 대상을 특정 자식으로 한정
- 예) Item 중에 Book, Movie를 조회하라
- JPQL
  - `select i from Item i where type(i) IN(Book, Movie)`
- SQL
  - `select i from i where i.DTYPE in ('B', 'M')`

TREAT
- 자바의 타입 캐스팅과 유사
- 상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 때 사용
- FROM, WHERE, SELECT (하이버네이트 지원) 사용

### JPQL - 엔티티 직접 사용
엔티티 직접 사용 - 기본 키 값
- JPQL 에서 엔티티를 직접 사용하면 SQL 에서 해당 엔티티의 기본 키 값을 사용
- JPQL
  - `select count(m.id) from Member m` : 엔티티의 아이디를 사용
  - `select coutn m from Member m`: 엔티티를 직접 사용
- SQL
  - `select count(m.id) as cnt from Member m` 

### JPQL - Named 쿼리
- 미리 정의해서 이름을 부여해두고 사용하는 JPQL
- 정적 쿼리
- 어노테이션, XML 정의
- 애플리케이션 로딩 시점에 초기화 후 재사용
- 애플리케이션 로딩 시점에 쿼리를 검증(오류를 잡아준다)

### JPQL - 벌크 연산
- 변경감지 기능으로는 너무 많은 SQL을 실행하게 될 때
- `executeUpdate()` 사용
  - 이 return 은 영향받은 엔티티 수 반환
- UPDATE, DELETE 지원
- INSERT(insert into .. select, 하이버네이트 지원)

벌크 연산 주의!
- 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리
  - 벌크 연산을 먼저 실행
  - 벌크 연산 수행 후 영속성 컨텍스트 초기화

