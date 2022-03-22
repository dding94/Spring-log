## 모든 개발자를 위한 HTTP 웹 기본 지식
- 출처 : https://www.inflearn.com/course/http-%EC%9B%B9-%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC/dashboard

## 인터넷 네트워크
### IP (인터넷 프로토콜)

#### 인터넷 프로토콜 역할
- 지정한 IP 주소에 데이터를 패킷이라는 통신단위로 전달한다.
- 한계점
  - 비연결성 : 패킷을 받을 대상이 없거나 서비스 불능 상태여도 패킷 전송
  - 비신뢰성 : 중간에 패킷 소실, 패킷이 순서대로 오지 않을 수 있다.

### 인터넷 프로토콜 스택의 4계층
- 애플리케이션 계층 - HTTP, FTP
- 전송 계층 - TCP, UDP
- 인터넷 계층 - IP
- 네트워크 인터페이스 계층

### TCP 특징
전송제어 프로토콜(TransMission Control Protocol)
- IP 프로토콜의 문제를 해결
- 연결지향 - TCP 3 way handshake(가상연결) // syn , syn + ack, ack
- 데이터 전달 보증, 순서 보장, 신뢰할 수 있는 프로토콜
- 현재는 대부분 TCP 사용
### UDP 특징
- 하얀 도화지에 비유(기능이 거의 없음)
- 데이터 전달 순서가 보장되지는 않지만, 단순하고 빠름
- IP와 거의 같다. + PORT + 체크섬 정도만 추가
- 애플리케이션에서 추가 작업 필요

### TCP/IP 패킷 정보
- 출발지 IP, PORT
- 목적지 IP, PORT
- 전송 데이터 등등..

### PORT
- 같은 IP 내에서 동작하는 프로세스 구분
- 0 ~ 65535 할당 가능
- 0 ~ 1023 : 잘 알려진 포트, 사용하지 않는 것이 좋음
  - FTP - 20, 21
  - TELNET - 23
  - HTTP - 80
  - HTTPS - 443
- PORT는 쉽게 얘기하면 IP는 아파트이고 PORT는 동 호수 라고 생각하면 편하다.

### DNS
- IP는 변하기 쉽고, 외우기 어려운데 도메인 명을 등록해서 사용할 수 있다.

## URI와 웹 브라우저 요청 흐름
### URI
- 네트워크 상에서 __자원(Resource)__ 이 어디있는지를 알려주기 위한 규약
- URI (Uniform Resource Identifier) 은 로케이터('l'oacter) , 이름('n'ame) 또는 둘다 추가로 분류될 수 있다.
- Uniform : 리소스 식별하는 통일된 방식
- Resource : 자원, URI로 식별할 수 있는 모든 것(제한없음)
- Identifier: 다른 항목과 구분하는데 필요한 정보

### URL, URN
- URL : 리소스가 있는 위치를 지정
- URN : 리소스에 이름 부여
- URN : 이름만으로 실제 리소스를 찾을 수 있는 방법은 보편화 되지 않음
- URI은 URL과 URN을 포함해서 URI 와 URL 을 같은 의미로 얘기

### URL 전체 문법
scheme://[userinfo@]host[:port][/path][?query][#fragment]   
https://www.google.com:443/search?q=hello&hl=k

- scheme,userinfo, 호스트명, 포트 번호, 패스(/search), 쿼리 파라미터

### 웹 브라우저 요청 흐름
HTTP 메시지 전송   
<img src="https://user-images.githubusercontent.com/21376853/149317464-c8b247d2-fbb9-4afd-8475-a8ca2c85ef14.png" width="650" height="250">

## HTTP
모든 것이 HTTP : HTML, TEXT , 이미지 , 음성, 영상 등 거의 모든 형태의 데이터 전송 가능   
대부분이 HTTP/1.1 기반이고 최근에는 2 와 3 버전이 확산되는 추세!(성능개선이라 1.1을 확실하게 알면 된다)
- 단순하고 확장 가능하다.
- 기반 프로토콜 
  - TCP : HTTP/1.1 , HTTP/2
  - UDP : HTTP/3

### 클라이언트 서버 구조
HTTP는 서버와 클라이언트 구조로 이루어져있는데 두개를 개념적으로 분리하면 각각 독립적으로 진화 할 수 있다.   
클라이언트는 UI, UX 에 집중, 복잡한 비즈니스 로직은 서버에서 처리
- Request Response 구조
- 클라이언트는 서버에 요청을 보내고, 응답을 대기 서버가 요청에 대한 결과를 만들어서 응답

### 무상태 프로토콜
Stateless - 서버 개발은 최대한 무상태로 만들자!
- 서버가 클라이언트의 상태를 보존하지않는다.
- 서버 확장성이 높다(스케일 아웃 - 수평 확장 유리)
- 단점 : 클라이언트가 데이터 전송 하는양이 많다.
- 실무 한계
  - 모든 것을 무상태로 설계 할 수 있는 경우도 있고 없는 경우도 있다.
  - 무상태 예) 로그인이 필요 없는 단순한 서비스 소개 화면
  - 상태 유지 예) 로그인 - 로그인한 사용자의 경우 로그인 상태를 서버에 유지
  - 일반적으로 브라우저 쿠키와 서버 세션등을 사용해서 상태 유지
  - 상태 유지는 최소한만 사용

### 비 연결성(connectionless)
- HTTP는 기본이 연결을 유지하지 않는 모델
- 일반적으로 초 단위 이하의 빠른 속도로 응답
- 서버의 연결을 유지하지 않기 때문에 서버 자원을 매우 효율적으로 사용 가능

### 비 연결성 한계와 극복
- TCP/IP 연결을 새로 맺어야 함 - 3 way handshake 시간 추가
- 웹 브라우저로 사이트를 요청하면 HTML 뿐만아니라 자바스크립트, 이미지, css 등 다양한
수 많은 자원이 함께 다운로드
- 현재는 HTTP 지속 연결(Persistent Connections)로 해결
  - 클라이언트 와 서버를 연결하고 자원을 다운로드 받을 떄 까지 유지 후 종료
- HTTP/2, HTTP/3 에서 더 많은 최적화

### HTTP 메시지
HTTP 메시지 구조는 아래와 같이 이루어져 있다.
- start-line_시작라인
- header_헤더
- empty line (CRLF)_공백라인
- message body 


#### 시작 라인(요청 메시지) = request-line
GET/search?q=hello&hl=ko HTTP/1.1   
Host: www.google.com

요청 메시지 - HTTP 메서드 
- 종류: GET, POST, PUT, DELETE...
- 서버가 수행해야 할 동작 지정   

요청 메시지 - 요청 대상
- absolute-path[?query] (절대경로[?쿼리])
- 절대경로 = "/" 로 시작한다 라고 이해하면 된다.

요청 메시지 - HTTP 버전
- HTTP Version

#### 시작 라인(응답 메시지)   
status-line = HTTP-version SP status-code SP reason-phrase CRLF
- HTTP 버전
- HTTP 상태 코드: 요청 성공, 실패를 나타냄
  - 200 : 성공
  - 400 : 클라이언트 요청 오류
  - 500 : 서버 내부 오류

#### HTTP 헤더
header-field = field-name ":" OWS field-value OWS   
(OWS: 띄어쓰기 허용, field-name은 대소문자 구분 없음)

- HTTP 전송에 필요한 모든 부가정보
  - 예) 메시지 바디의 내용, 메시지 바디의 크기, 압축, 인증, 캐시 ..
 
#### HTTTP 메시지 바디
- 실제 전송할데이터
- HTML 문서, 이미지, 영상, JSON 등등 byte로 표현할 수 있는 모든 데이터

## HTTP 메서드
### HTTP API를 만들어 보자
요구사항 - 회원 정보관리 API를 만들어라
- __회원__ 목록 조회 /members
- __회원__ 조회 /members/{id}
- __회원__ 등록 /members/{id}
- __회원__ 수정 /members/{id}
- __회원__ 삭제 /members/{id}
- 참고 : 계층 구조상 상위를 컬렉션으로 보고 복수단어 사용 권장(member -> members)

★ 가장 중요한건 __리소스 식별__ 이다!! ★ 

#### 리소스의 의미는 뭘까
- 회원을 등록하고 수정하고 조회하는게 리로스가 아니다!
- 회원이라는 개념 자체가 바로 리소스 이다.
- 회원을 등록하고 수정하고 조회하는 것을 모두 배제
- 회원이라는 리소스만 식별하면 된다. -> 회원 리소스를 URI에 매핑

### 리소스와 행위를 분리
#### 가장 중요한 것은 리소스를 식별하는 것
- URI는 리소스만 식별!
- __리소스__ 와 해당 리소스를 대상으로 하는 __행위__ 를 분리
  - 리소스 : 회원
  - 행위 : 조회, 등록, 삭제, 변경
- 행위(메서드)는 어떻게 구분할까?

## HTTP 메서드 종류

### 1. GET
- 리소스 조회
- 서버에 전달하고 싶은 데이터는 query(쿼리 파라미터, 쿼리 스트링)을 통해서 전달
- 메시지 바디를 사용해서 데이터를 전달할 수 있지만, 지원하는 곳이 많지 않아 권장x

### 2. POST
- 요청 데이터 처리
- 메시지 바디를 통해 서버로 요청 데이터 전달
- 서버는 요청 데이터 처리
  - 메시지 바디를 통해 들어온 데이터를 처리하는 모든 기능을 수행
- 주로 전달된 데이터로 신규 리소스 등록, 프로세스 처리에 사용

#### [정리]
1. 새 리소스 생성(등록)
   - 서버가 아직 식별하지 않은 새 리소스 생성
2. 요청 데이터 처리
   - 단순히 데이터를 생성하거나, 변경하는 것을 넘어서 프로세스를 처리해야 하는 경우
   - 에) 주문에서 결제완료 -> 배달시작 -> 배달완료 처럼 단순히 값 변경을 넘어 프로세스의 상태가 변경되는 경우
   - POST의 결과로 새로운 리소스가 생성되지 않을 수 있음
   - 예)POST/orders/{orderld}/__start-delivery (컨트롤 URI)__ 
3. 다른 메서드로 처리하기 애매한 경우
   - 예)JSON으로 조회 데이터를 넘겨야 하는데, GET 메서드를 사용하기 어려운 경우(메시지 바디를 잘 허용하지 않아서)
   - 애매하면 POST

### 3. PUT
- 리소스를 완전히 대체
  - 기존에 있는 리소스는 사라지고 대체된다.
- 리소스가 없으면 생성
- 클라이언트가 리소스의 위치를 알고 URI를 지정!
  - POST는 리소스의 위치 지정이 안된다.

### 4. PATCH
- 리소스 '부분' 변경
  - 리소스의 일부를 변경한다.
  
### 5. DELETE
- 리소스 제거

## HTTP 메서드의 속성

### Safe 안전
- 호출해도 리소스를 변경하지 않는다.
  - 계속 호출해서 리소스가 쌓여서 장애가 생기면? -> 리소스만 고려한다, 그런거 고려 x

### Idempotent 멱등
- f(f(x)) = f(x)
- 한 번 호출하든 두 번 호출하든 100번 호출하든 결과가 똑같다.
- 멱등 메서드
  - GET, PUT, PATCH
  - POST -> 얘는 아님!!! 두 번 호출하면 두 번 결제가 중복해서 발생가능
- 활용
  - 자동 복구 메커니즘
  - 서버가 응답이 없을때, 클라이언트가 같은 요청을 재시도 해도 되는가? 판단 근거
  - 외부 요인으로 중간에 리소스가 변경되는 것 까지 고려 X

### Cacheable 캐시가능
- 응답 결과 리소스를 캐시해서 사용해도 되는가?
- GET, HEAD, POST, PATCH 캐시 가능 하지만 실제로는 GET, HEAD 정도만 캐시로 사용

## HTTP 메서드 활용

###  클라이언트에서 서버로 데이터 전송
데이터 전달 방식은 크게 2가지
- 쿼리 파라미터를 통한 데이터 전송
  - GET
  - 주로 정렬 필터(검색어)
- 메시지 바디를 통한 데이터 전송
  - POST, PUT, PATCH
  - 회원 가입, 상품 주문, 리소스 등록, 리소스 변경

4가지 상황
- 정적 데이터 조회
  - 이미지, 정적 테스트 문서
  - 조회는 GET 사용, 쿼리 파라미터 없이 리소스 경로로 단순하게 조회 가능
- 동적 데이터 조회 
  - 주로 검색, 게시판 목록에서 정렬 필터(검색어)
  - GET을 사용, 쿼리 파라미터 사용해서 데이터를 전달
- HTML Form을 통한 데이터 전송
  - GET, POST 만 지원한다.
  - 회원 가입, 상품 주문, 데이터 변경
  - 조회할때는 GET, 데이터를 바꿀땐 POST
- HTTP API를 통한 데이터 전송
  - HTML Form을 사용하지 않는 모든 경우
  - 서버 to 서버, 앱 클라이언트, 웹 클라이언트(AJAX)
  - POST, PUT, PATCH 메시지 바디를 통해 데이터 전송
  - GET 조회, 쿼리 파라미터로 데이터 전달

### HTTP API 설계 예시
HTTP API - 컬렉션
- POST 기반 등록 (실무에서 대부분 이 방식 사용)
- 클라이언트는 등록될 리소스의 URI를 모른다.
- 서버가 리소스 URI 결정
- 컬렉션
  - 서버가 관리하는 리소스 디렉토리
  - 서버가 리소스의 URI를 생성하고 관리

HTTP API - 스토어
- PUT 기반 등록
- 클라이언트가 리소스 URI를 알고 있어야 한다.
- 스토어(Stroe)
  - 클라이언트가 관리하는 리소스 저장소
  - 클라이언트가 리소스의 URI 를 알고 관리

HTTP FORM 사용
- 순수 HTML + HTML form 사용
- GET, POST만 지원
- 컨트롤 URI
  - GET, POST만 지원하므로 제약이 있음
  - 이런 제약을 해결하기 위해 '동사'로 된 리소스 경로 사용
  - ex) POST의 /new, /edit, /delete 가 컨트롤 URI

참고하면 좋은 URI 설계 개념
- <details><summary> 자세히 </summary>

  - 문서(document)
    - 단일 개념(파일 하나, 객체 인스턴스, 데이터베이스 row)
    - 예) /members/100, /files/star.jpg
    
  - 컬렉션(collection)
    - 서버가 관리하는 리소스 디렉터리
    - 서버가 리소스의 URI를 생성하고 관리
    - 예) /members

  - 스토어(store)
    - 클라이언트가 관리하는 자원 저장소
    - 클라이언트가 리소스의 URI를 알고 관리
    - 예) /files

  - 컨트롤러(controler), 컨트롤 URI
    - 문서, 컬렉션, 스토어로 해결하기 어려운 추가 프로세스 실행
    - 동사를 직접 사용
    - 예) /members/{id}/delete
    - 최대한 리소스 개념가지고 URI를 설계하고 해결하기 안될때 사용한다!

  - https://restfulapi.net/resource-naming
</details>

## HTTP 상태코드
상태코드 - 클라이언트가 보낸 요청의 처리 상태를 응답해서 알려주는 기능

- 1xx (Informational): 요청이 수신되어 처리중
- 2xx (Successful): 요청이 정상 처리
- 3xx (Redirection): 요청을 완료하려면 추가 행동이 필요
- 4xx (Client Error): 클라이언트 오류, 잘못된 문법등으로 서버가 요청을 수행할 수 없음
- 5xx (Server Error): 서버 오류, 서버가 정상 요청을 처리하지 못함

만약 모르는 상태 코드가 나타나면?
- 클라이언트가 인식할 수 없는 상태코드를 서버가 반환하면?
- 클라이언트는 상위 상태코드로 해석해서 처리
- 예)
  - 299 ??? -> 2xx (Successful)
  - 451 ??? -> 4xx (Client Error)
  - 599 ??? -> 5xx (Server Error)

#### 1xx (Informational)
요청이 수신되어 처리중
- 거의 사용하지 않아 생략

#### 2xx (Successful)
<details><summary> 클라이언트의 요청을 성공적으로 처리</summary>

- 200 OK
  - 요청 성공
- 201 Created
  - 요청 성공해서 새로운 리소스가 생성됨
  - 생성된 리소스는 응답의 Location 헤더 필드로 식별
- 202 Accepted
  - 배치 처리 같은 곳에서 사용
  - 예) 요청 접수 후 1시간 뒤에 배치 프로세스가 요청을 처리함
  - 잘 사용하지는 않음
- 204 No Content
  - 서버가 요청을 성공적으로 수행했지만, 응답 페이로드 본문에 보낼 데이터가 없음
  - 예) 웹 문서 편집기에서 save 버튼
  - save 버튼의 결과로 아무 내용이 없어도 되고, 같은 화면을 유지해야 한다.
  - 결과 내용이 없어도 204 메시지(2xx)만으로 성공을 인식할 수 있다.
</details>

#### 3xx (Redirection)
요청을 완료하기 위해 유저 에이전트(=웹 프로그램)의 추가 조치 필요   
웹 브라우저는 3xx 응답 결과에 Location 헤더가 있으면, Location 위치로 자동 이동

리다이렉션 이해 - 종류
- 영구 리다이렉션 - 특정 리소스의 URI가 영구적으로 이동
  - 예)/event -> /new-event
  - <details><summary> 301, 308 </summary>
  
    - 원래의 URL를 사용X, 검색 엔진 등에서도 변경 인지
    - __301 Moved Permanently__
      - 리다이렉트시 요청 메서드가 GET으로 변하고, 본문이 제거될 수 있음(MAY).
    - __308 Permanent Redirect__
      - 301과 기능은 같음, 실무에서 잘 사용하지 않음
      - 리다이렉트시 요청 메서드와 본문 유지(처음 POST를 보내면 리다이렉트도 POST 유지)
  </details>
- 일시 리다이렉션 - 일시적인 변경
  - 주문 완료 후 주문 내역 화면으로 이동
  - 따라서 검색 엔진 등에서 URL을 변경하면 안됨
  - <details><summary> 302, 307, 303</summary>
  
    - 302 Found (많은 애플리케이션 라이브러리들이 기본값으로 사용)
      - 리다이렉트시 요청 메서드가 GET으로 변하고, 본문이 제거될 수 있음(MAY)
    - 307 Temporary Redirect
      - 302와 기능은 같음
      - 리다이렉트시 요청 메서드와 본문 유지(요청 메서드를 변경하면 안된다. MUST NOT)
    - 303 See other
      - 302와 기능은 같음
      - 리다이렉트시 요청 메서드가 GET으로 변경
</details>
      
  - PRG: Post/Redirect/Get
    - <details><summary> 예시 </summary>

      - POST로 주문후에 새로 고침으로 인한 중복 주문 방지
      - POST로 주문후에 주문 결과 화면을  GET 메서드로 리다이렉트
      - 새로고침해도 결과 화면을 GET으로 조회, URL 이 이미 POST-> GET으로 리다이렉트
      - 중복 주문 대신에 결과 화면만 GET으로 다시 요청

</details>

  - 특수 리다이렉션
    - 결과 대신 캐시를 사용
    - <details><summary> 300, 304</summary>

      - 300 Multiple Chocices: 안쓴다.
      - 304 Not Modified
        - 캐시를 목적으로 사용
        - 클라이언트에게 리소스가 수정되지 않았음을 알려준다. 따라서 클라이언트는 로컬PC에 저장된 캐시를 재사용 한다.
          (캐시로 리다이렉트 한다)
        - 304 응답은 응답에 메시지 바디를 포함하면 안된다.(로컬 캐시를 사용해야 하므로)
        - 조건부 GET, HEAD 요청시 사용
      
</details>

#### 4xx (Client Error)
클라이언트 오류
- 클라이언트의 요청에 잘못된 문법등으로 서버가 요청을 수행할 수 없음
- 오류의 원인이 클라이언트에 있음
- 중요! 클라이언트가 이미 잘못된 요청, 데이터를 보내고 있기 때문에, 똑같은 재시도가 실패함

<details><summary> 400, 401, 403, 404</summary>
  
  - 400 Bad Request
    - 클라이언트가 잘못된 요청을 해서 서버가 요청을 처리할 수 없음
      - 요청 구문, 메시지 등등 오류
      - 클라이언트는 요청 내용을 다시 검토하고 보내야한다.
      - 예) 요청 파라미터가 잘못되거나, API 스펙이 맞지 않을 떄
  - 401 Unauthorized
    - 클라이언트가 해당 리소스에 대한 인증이 필요함
      - 인증(Authentication) 되지 않음
      - 401 오류 발생시 응답에 WWW-Authenticate 헤더와 함께 인증 방법을 설명
      - 참고
        - 인증(Authentication): 본인이 누구인지 확인,(로그인)
        - 인가(Authorization): 권한부여(ADMIN 권한 처럼, 인증이 있어야 인가가 있다)

  - 403 Forbidden
    - 서버가 요청을 이해했지만 승인을 거부
      - 주로 인증 자격증명은 있지만, 접근 권한이 불충분한 경우
      - 예) 어드민 등급이 아닌 로그인 한 사용자가 어드민 등급의 리소스에 접근하는 경우
  - 404 Not Found
    - 요청 리소스를 찾을 수 없음
      - 요청 리소스가 서버에 없거나 클라이언트가 권한이 부족한 리소스에 접근할 때 해당 리소스를 숨기고 싶을 때
</details>

#### 5xx (Server Error)
서버 오류
- 서버 문제로 오류 발생
- 서버에 문제가 있기 때문에 재시도 하면 성공할 수도 있음

<details><summary> 500, 503</summary>

  - 500 Internal Server Error
    - 서버 문제로 오류 발생, 애매하면 500오류를 띄우면 된다.
  - 503 Service Unavailable
    - 서비스 이용불가
      - 서버가 일시적인 과부하 또는 예정된 작업으로 잠시 요청을 처리할 수 없음
      - Retry-After 헤더 필드로 얼마뒤에 복구되는지 보낼 수도 있음

</details>

## HTTP 헤더 개요
용도
- HTTP 전송에 필요한 모든 부가 정보
- 예) 메시지 바디의 내용, 메시지 바디의 크기, 압축, 인증, 요청 클라이언트, 서버 정보, 캐시 관리 정보
- 표준 헤더가 너무 많음
- 필요시 임의의 헤더 추가 가능

### RFC723x 변화
- 엔티티(Entity) -> 표현(Representation)
- Representation =  Representation Metadata + Representation Data
- 표현 = 표현 메타데이터 + 표현 데이터

### HTTP BODY
- 메시지 본문(message body)을 통해 표현 데이터 전달
- 메시지 본문 = 페이로드(payload)
- 표현은 요청이나 응답에서 전달할 실제 데이터
- 표현 헤더는 표현 데이터를 해석할 수 있는 정보 제공
  - 데이터 유형(html, json), 데이터 길이, 압축 정보 등등
- 참고 : 표현 헤더는 표현 메타데이터와, 페이로드 메시지를 구분해야하지만 여기서는 생략


### 표현
- Content-Type: 표현 데이터의 형식
  - 미디어 타입, 문자 인코딩
  - 예) 
    - text/html; charset=utf-8
    - application/json
    - image/png
- Content-Encoding: 표현 데이터의 압축 방식
  - 표현 데이터를 압축하기 위해 사용
  - 데이터를 전달하는 곳에서 압축 후 인코딩 헤더 추가
  - 데이터를 읽는 쪽에서 인코딩 헤더의 정보로 압축 해제
  - 예) gzip, deflate, identity
- Content-Language: 표현 데이터의 자연 언어
  - 표현 데이터의 자연 언어를 표현
  - 예) ko, en, en-US
- Content-Length: 표현 데이터의 길이
 - 바이트 단위
 - Transfer-Encoding(전송 코딩)을 사용하면 Content-Length를 사용하면 안됨

### 협상(콘텐츠 네고시에이션)
클라이언트가 선호하는 표현 요청
- Accept: 클라이언트가 선호하는 미디어 타입 전달
- Accept-Charset: 클라이언트가 선호하는 문자 인코딩
- Accept-Encoding: 클라이언트가 선호하는 압축 인코딩
- Accept-Language: 클라이언트가 선호하는 자연 언어
- 협상 헤더는 요청시에만 사용!

### 협상과 우선순위1
- Quality Values(q) 값 사용
- 0~1, 클수록 높은 우선순위 생략하면 1이다.
- ex) Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
  - ko-KR;q=1 (q생략)
  - ko;q=0.9
  - en-Us;q=0.8
  - en;q=0.7
- 구체적인 것이 우선한다.
- 구체적인 것을 기준으로 미디어 타입을 맞춘다.

### 전송방식
- 단순 전송
  - 컨텐트에 대한 길이를 알 수 있을때 사용, 한번에 요청하고 한번에 받음
- 압축 전송
  - 압축방법을 넣어 압축해서 전송
- 분할 전송(Transfer-Encoding)
  - 쪼개서 덩어리씩 보냄, 다 받기전에 미리 표시, 컨텐트 랭스를 보내면 안됨.
- 범위 전송(Range,Content-Range)
  - 범위를 지정

### 일반 정보
#### From
유저 에이전트의 이메일 정보
- 일반적으로 잘 사용되지 않음
- 검색 엔진 같은 곳에서, 주로 사용
- 요청에서 사용

#### Referer
이전 웹페이지 주소
- 현재 요청된 페이지의 이전 웹 페이지 주소
- A -> B 로 이동하는 경우 B를 요청할 때 Referer: A를 포함해서 요청
- Referer를 사용해서 유입 경로 분석 가능
- 요청에서 사용

#### User-Agent
유저 에이전트 애플리케이션 정보
- 클라이언트의 애플리케이션 정보(웹 브라우저 정보, 등등)
- 통계 정보
- 어떤 종류의 브라우저에서 장애가 발생하는지 파악 가능
- 요청에서 사용

#### Server
요청을 처리하는 ORIGIN 서버의 소프트웨어 정보
- Server: nginx
- 응답에서 사용

#### Date
메시지가 발생한 날짜와 시간
- 응답에서 사용

### 특별한 정보

#### Host
요청한 호스트 정보(도메인)
- 요청에서 사용
- 필수
- 하나의 서버가 여러 도메인을 처리해야 할 때
- 하나의 IP 주소에 여러 도메인이 적용되어 있을 때

#### Location
페이지 리다이렉션
- 웹 브라우저는 3xx 응답의 결과에 Location 헤더가 있으면, Location 위치로 자동 이동(리다이렉트)
- 201 (Created): Location 값은 요청에 의해 생성된 리소스 URI
- 3xx (Redirection): Location 값은 요청을 자동으로 리디렉션하기 위한 대상 리소스를 가리킴

#### Allow
허용 가능한 HTTP 메서드 (잘안씀)
- 405 (Method Not Allowed) 에서 응답에 포함해야함
- Allow: GET,HEAD,PUT

#### Retry-After
유저 에이전트가 다음 요청을 하기까지 기다려야 하는 시간
- 503 (Service Unavailable): 서비스가 언제까지 불능인지 알려줄 수 있음
- Retry-After: 날짜표기 or 초단위 표기

### 인증
#### Authorization
클라이언트 인증 정보를 서버에 전달
- Authorization: Basic xxxxxxxxxxx

#### WWW-Authenticate
리소스 접근시 필요한 인증 방법 정의
- 401 Unauthorized 응답과 함께 사용
- WWW-Authenticate: Newauth realm="apps", type=1, title="Login to \"apps\"", Basic realm="simple"

### 쿠키
서버를 통해 인터넷 사용자의 컴퓨터에 설치되는 작은 기록 정보 파일을 일컫는다.
- Set-Cookie: 서버에서 클라이언트로 쿠키 전달(응답)
- Cookie: 클라이언트가 서버에서 받은 쿠키를 저장하고, HTTP 요청시 서버로 전달
- 쿠키를 사용하지 않으면 로그인해도 로그인 정보가 없어진다 -> HTTP는 무상태 프로토콜이기 때문

예) set-cookie: __sessionId=abcde1234; expires__=Sat, 26-Dec-2020 00:00:00 GMT; __path=/; domain__=.google.com; __Secure__

- 사용처
  - 사용자 로그인 세션 관리
  - 광고 정보 트래킹
- 쿠키 정보는 항상 서버에 전송됨
  - 네트워크 트래픽 추가 유발
  - 최소한의 정보만 사용(세션id, 인증 토큰)
  - 서버에 전송하지 않고, 웹 브라우저 내부에 데이터를 저장하고 싶으면 웹 스토리지(localStorage, sessionStorage) 참고
- 주의
  - 보안에 민감한 데이터는 저장하면 안됨(주민번호, 신용카드 번호 등)

#### 쿠키 - 생명주기
Expires, max-age
- Set-Cookie: __expires__=Sat, 26-Dec-2020 04:39:21 GMT
  - 만료일이 되면 쿠키 삭제
- Set-Cookie: __max-age__=3600 (3600초)
  - 0이나 음수를 지정하면 쿠키 삭제
- 세션 쿠키: 만료 날짜를 생략하면 브라우저 종료시 까지만 유지
- 영속 쿠키: 만료 날짜를 입력하면 해당 날짜까지 유지

#### 쿠키 - 도메인
- 예)domain=example.org
- 명시: 명시한 문서 기준 도메인 + 서브 도메인 포함
- domain=example.org를 지정해서 쿠키 생성
  - example.org는 물론이고 dev.example.org도 쿠키 접근
- 생략: 현재 문서 기준 도메인만 적용
- example.org에서 쿠키를 생성하고 domain 지정을 생략
  - example.org 에서만 쿠키 접근 , dev.example.org는 쿠키 미접근

#### 쿠키 - 경로
- 예)path=/home
- 이 경로를 포함한 하위 경로 페이지만 쿠키 접근
- 일반적으로 path=/ 루트로 지정
- 에)
  - path=/home 지정
  - /home, /home/lever1, /home/lever1/lever2 가능하지만 /hello 불가능

#### 쿠키 - 보안
Secure
- 쿠키는 http,https를 구분하지 않고 전송
- Secure를 적용하면 https인 경우에만 전송

HttpOnly
- XSS 공격 방지
- 자바스크립트에서 접근 불가(document.cookie)
- HTTP 전송에만 사용

SameSite
- XSRF 공격 방지
- 요청 도메인과 쿠키에 설정된 도메인이 같은 경우만 쿠키 전송

## HTTP 헤더2 - 캐시와 조건부 요청

### 검증 헤더
- 캐시 데이터와 서버 데이터가 같은지 검증하는 데이터
- Last-Modified , ETag

### 캐시
리소스의 복사본을 저장하고 있다가 요청 시에 그것을 제공
- 캐시 가능 시간동안 네트워크를 사용하지 않아도 된다
- 비싼 네트워크 사용량을 줄일 수 있다.
- 브라우저 로딩 속도가 매우 빠르다.
- 빠른 사용자 경험
- 캐시 시간 초과시에는 서버를 통해 데이터를 다시 조회하고, 캐시를 갱신한다.

### 검증 헤더와 조건부 요청1 Last-Modified
- 캐시 유효 시간이 초과해도, 서버의 데이터가 갱신되지 않으면 304 Not Modified + 헤더 메타 정보만 응답(바디X)
- 클라이언트는 서버가 보낸 응답 헤더 정보로 캐시의 메타 정보를 갱신
- 클라이언트는 캐시에 저장되어 있는 데이터 재활용
- 결과적으로 네트워크 다운로드가 발생하지만 용량이 적은 헤더 정보만 다운로드

### 검증 헤더와 조군부 요청2 ETag
Etag, If-None-Match
- Etag(Entity Tag) 캐시용 데이터에 임의의 고유한 버전 이름을 달아둠
- 데이터가 변경이 되면 이 이름을 바꾸어서 변경함(Hash를 다시 새성)
- 단순하게 Etag만 서버에 보내서 같으면 유지, 다르면 다시 받기 (같으면 304, 다르면 200)
- 캐시 제어 로직을 서버에서 완전히 관리
- 클라이언트는 단순히 이 값을 서버에 제공(클라이언트는 캐시 메커니즘을 모름)

### 캐시 제어 헤더

#### Cache-Control
캐시 지시어(directives)
- Cache-Control: max-age
  - 캐시 유효 시간, 초 단위
- Cache-Control: no-cache
  - 데이터는 캐시해도 되지만, 항상 원(origin) 서버에 검증하고 사용
- Cache-Control: no-store
  - 데이터에 민감한 정보가 있으므로 저장하면 안됨 (메모리에서 사용하고 최대한 빨리 삭제)

#### Pragma
캐시 제어(하위 호환)
- Pragma: no-cahce
- HTTP 1.0 하위 호환

#### Expires
캐시 만료일 지정(하위 호환)
- expires: Mon, 01 Jan 1990 00:00:00 GMT
- 캐시 만료일을 정확한 날짜로 지정
- HTTP 1.0 부터 사용
- 지금은 더 유연한 Cache-Control: max-age 권장
- max-age와 함께 사용하면 Expires는 무시

### 프록시 캐시
웹 사용자에 의해 번번히 요청되는 데이터를 사용자와 지리적으로 가까운 웹캐시 서버에 보관하여 빠른 서비스를 가능하게 함

#### Cache-Control
캐시 지시어(directives) - 기타
- Cache-Control: public
  - 응답이 public 캐시에 저장되어도 됨
- Cache-Control: private
  - 응답이 해당 사용자만을 위한 것임, private 캐시에 저장해야 함(기본값)
- Cache-Control: s-maxage
  - 프록시 캐시에만 적용되는 max-age
- Age: 60 (HTTP 헤더)
  - 오리진 서버에서 응답 후 프록시 캐시 내에 머문 시간(초)

### 캐시 무효화
#### Cache-Control
확실한 캐시 무효화 응답   
아래 적힌 부분을 다 적어야 한다.
- Cache-Control: no-cahce, no-store, must-revalidate
- Pragma: no-cache

- Cache-Control: must-revalidate
  - 캐시 만료후 최초 조회시 원 서버에 검증해야함
  - 원 서버 접근 실패시 반드시 오류가 발생해야함 - 504(Gateway Timeout)
  - must-revalidate는 캐시 유효 시간이라면 캐시를 사용함
  
#### no-cache vs must-revalidate
no-cahce
- 원서버에 접근할 수 없는 경우 서버 설정에 따라서 캐시 데이터를 반환할 수 있음
  
must-revalidate
- 원 서버에 접근할 수 없는 경우, 항상 오류가 발생해야함
- 504 Gateway Timeout 나오도록 한다.

