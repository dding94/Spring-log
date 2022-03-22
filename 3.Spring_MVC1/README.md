# 출처
- url : https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-mvc-1/dashboard
강사 : 김영한님

# 핵심정리
## 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술

### 웹 서버(Web Server)
- HTTP 기반으로 동작
- 정적 리소스 제공, 기타 부가기능
- 정적(파일) HTML, CSS, JS, 이미지, 영상
- 예) NGINX, APACHE

### 웹 애플리케이션 서버(WAS)
- HTTP 기반으로 동작
- 웹 서버 기능 포함 + (정적 리소스 제공 가능)
- 프로그램 코드를 실행해서 애플리케이션 로직 수행
  - 동적 HTML, HTTP API(JSON)
  - 서블릿, JSP, 스프링 MVC
- 예) 톰캣(Tomcat), jetty, udertow

### 웹 서버, 웹 애플리케이션 서버 차이
- 웹 서버는 정적 리소스(파일), WAS 는 애플리케이션 로직
- WAS는 애플리케이션 코드를 실행하는데 더 특화


### 웹 시스템 구성 - WAS, DB
- WAS, DB 만으로 시스템 구성 가능
- WAS는 정적 리소스, 애플리 케이션 로직 모두 제공 가능
- 단점
  - WAS가 너무 많은 역할을 담당, 서버 과부하 우려
  - 가장 비싼 애플리케이션 로직이 정적 리소스 때문에 수행이 어려울 수 있음
  - WAS 장애시 오류 화면도 노출 불가능

### 웹 시스템 구성 - WEB, WAS, DB
- 정적 리소스는 웹 서버가 처리
- 웹 서버는 애플리케이션 로직같은 동적인 처리가 필요하면 WAS에 요청을 위임
- WAS는 중요한 애플리케이션 로직 처리 전담
- 효율적인 리소스 관리
  - 정적 리소스가 많이 사용되면 Web 서버 증설, 애플리케이션 리로스가 많이 사용되면 WAS 증설
- 정적 리소스만 제공하는 웹 서버는 잘 죽지않음
- 애플리케이션 로직이 동작하는 WAS 서버는 잘 죽음, WAS DB 장애시 WEB 서버가 오류화면 제공 가능

### 서블릿
특징
![서블릿](https://user-images.githubusercontent.com/21376853/149795097-3db219f4-1cf9-4222-8aa2-4ae6596bf9c1.png)
- urlPatterns(/hello)의 URL이 호출되면 서블릿 코드가 실행
- HTTP 요청 정보를 편리하게 사용할 수 있는 HttpServletRequest
- HTTP 응답 정보를 편리하게 제공할 수 있는 HttpServletResponse
- 개발자는 HTTP 스펙을 매우 편리하게 사용
- HTTP 요청시
  - WAS는 Request, Response 객체를 새로 만들어서 서블릿 객체 호출
  - 개발자는 Request 객체에서 HTTP 요청 정보를 편리하게 꺼내서 사용
  - 개발자는 Response 객체에 HTTP 응답 정보를 편리하게 입력
  - WAS는 Response 객체에 담겨있는 내용으로 HTTP 응답 정보를 생성
  
서블릿 컨테이너
- 톰캣처럼 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 한다.
- 서블릿 컨테이너는 서블릿 객체를 생성, 초기화, 호출, 종료하는 생명주기 관리
- 서블릿 객체는 싱글톤으로 관리
  - <details><summary> 자세히 </summary>
  
    - 고객의 요청이 올 때 마다 계속 객체를 생성하는 것은 비효율
    - 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용
    - 모든 고객 요청은 동일한 서블릿 객체 인스턴스에 접근
    - __공유 변수 사용 주의__
    - 서블릿 컨테이너 종료시 함께 종료
  </details>

- JSP도 서블릿으로 변환 되어서 사용
- ★동시 요청을 위한 멀티 쓰레드 처리 지원

### ★★ 쓰레드 ★★
스레드는 어떠한 프로그램 내에서, 특히 프로세스 내에서 실행되는 흐름의 단위를 말한다.
- 애플리케이션 코드를 하나하나 순차적으로 실행하는 것은 쓰레드
- 자바 메인 메서드를 처음 실행하면 main 이라는 이름의 쓰레드가 실행
- 쓰레드가 없다면 자바 애플리케이션 실행이 불가능
- 쓰레드는 한번에 하나의 코드 라인만 수행
- 동시 처리가 필요하다면 쓰레드를 추가로 생성

### 요청마다 쓰레드 생성
장점
- 동시 요청을 처리할 수 있다.
- 리소스(CPU,메모리)가 허용할 때 까지 처리가능
- 하나의 쓰레드가 지연 되어도, 나머지 쓰레드는 정상 동작한다.

단점
- 쓰레드 생성 비용은 매우 비싸다.
  - 고객의 요청이 올 때 마다 쓰레드를 생성하면, 응답 속도가 늦어진다.
- 쓰레드는 컨텍스트 스위칭 비용이 발생한다.
- 쓰레드 생성에 제한이 없다.
  - 고객 요청이 너무 많이 오면, CPU, 메모리 임계점을 넘어서 서버가 죽을 수 있다.

### 쓰레드 풀
요청마다 쓰레드 생성의 단점 보완   

특징
- 필요한 쓰레드를 쓰레드 풀에 보관하고 관리한다.
- 쓰레드 풀에 생성 가능한 쓰레드의 최대치를 관리한다. 톰캣은 최대 200개 기본 설정(변경가능)

사용
- 쓰레드가 필요하면, 이미 생성되어 있는 쓰레드를 쓰레드 풀에서 꺼내서 사용한다.
- 사용을 종료하면 쓰레드 풀에 해당 쓰레드를 반납한다.
- 최대 쓰레드가 모두 사용중이어서 쓰레드 풀에 쓰레드가 없다면?
  - 기다리는 요청은 거절하거나 특정 숫자만큼만 대기하도록 설정할 수 있다.

장점
- 쓰레드가 미리 생성되어 있으므로, 쓰레드를 생성하고 종료하는 비용(CPU)이 절약되고, 응답 시간이 빠르다.
- 생성 가능한 쓰레드의 최대치가 있으므로 너무 많은 요청이 들어와도 기존 요청은 안전하게 처리할 수 있다.

### 쓰레드 풀 실무팁!
- WAS의 주요 튜닝 포인트는 최대 쓰레드(max thread) 수이다.
- 이 값을 너무 낮게 설정하면 
  - 동시 요청이 많으면, 서버 리소스는 여유롭지만 클라이언트는 금방 응답 지연
- 이 값을 너무 높게 설정하면?
  - 동시 요청이 많으면, CPU, 메모리 리소스 임계점 초과로 서버 다운
- 장애 발생시?
  - 클라우드면 일단 서버부터 늘리고, 이후에 튜닝
  - 클라우드가 아니면 열심히 튜닝
- 적정 숫자는 어떻게 찾나요?
  - 애플리케이션 로직의 복잡도,CPU 메모리, IO 리소스 상황에 따라 모두 다름
  - 최대한 실제 서비스와 유사하게 성능테스트를 해본다. (툴: 아파치 ab, 제이미터, nGrinder)

### WAS의 멀티 쓰레드 지원
- 멀티 쓰레드에 대한 부분은 WAS가 처리
- 개발자가 멀티 쓰레드 관련 코드를 신경쓰지 않아도 됨
- 개발자는 마치 싱글 쓰레드 프로그래밍 하듯이 편리하게 소스 코드를 개발
- 멀티 쓰레드 환경이므로 싱글톤 객체(서블릿, 스프링 빈)는 주의해서 사용!


## HTML, HTTP API, CSR, SSR

백엔드 개발자는 아래 세가지를 고민해야한다.
### 정적 리소스
- 고정된 HTML 파일, CSS, JS, 이미지, 영상 등을 제공
- 주로 웹 브라우저

### HTML 페이지
- 동적으로 필요한 HTML 파일을 생성해서 전달
- 웹 브라우저: HTML 해석

### HTTP API
- HTML이 아니라 데이터를 전달
- 주로 JSON 형식 사용
- 다양한 시스템에서 호출
- 데이터만 주고 받음, UI 화면이 필요하면 클라이언트가 별도 처리
- UI 클라이언트 접점
  - 앱 클라이언트(아이폰, 안드로이드, PC앱)
  - 웹 브라우저에서 자바스크립트를 통한 HTTP API 호출
  - React, vue.js 같은 웹 클라이언트
- 서버 to 서버
  - 주문 서버 -> 결제 서버
  - 기업간 데이터 통신

### SSR - 서버 사이드 렌더링
- HTML 최종 결과를 서버에서 만들어서 웹 브라우저에 전달
- 주로 정적인 화면에 사용
- 관련기술 : JSP, 타임리프 -> 백엔드 개발자

### CSR - 클라이언트 사이드 렌더링
- HTML 결과를 자바스크립트를 사용해 웹 브라우저에서 동적으로 생성해서 적용
- 주로 동적인 화면에 사용, 웹 환경을 마치 앱 처럼 필요한 부분부분 변경할 수 있음
- 예) 구글 지도, Gmail, 구글 캘린더
- 관련기술: React, Vue.js -> 프론트엔드 개발자


## 자바 웹 기술 역사
<details><summary>과거 기술</summary>

  - 서블릿 - 1997
    - HTML 생성이 어려움
  - JSP - 1999
    - HTML 생성은 편리하지만, 비즈니스 로직까지 너무 많은 역할 담당
  - 서블릿, JSP 조합 MVC 패턴 사용
    - 모델, 뷰 컨트롤러로 역할을 나누어 개발
  - MVC 프레임워크 춘추 전국 시대 - 2000년 초 ~ 2010년 초
    - MVC 패턴 자동화, 복잡한 웹 기술을 편리하게 사용할 수 있는 다양한 기능 지원
    - 스트럿츠, 앱워크, 스프링 MVC(과거 버전)
</details>
    
현재 사용 기술
  - 애노테이션 기반의 스프링 MVC 등장
    - @Controller
    - MVC 프레임워크의 춘추 전국 시대 마무리
  - 스프링 부트의 등장
    - 스프링 부트는 서버를 내장
    - 과거에는 서버에 WAS를 직접 설치하고, 소스는 War파일을 만들어서 설치한 WAS에 배포
    - 스프링 부트는 빌드 결과(Jar)에 WAs 서버 포함 -> 빌드 배포 단순화

### 자바 웹 기술 역사
최신 기술 - 스프링 웹 플럭스(WebFlux)
<details><summary> 자세히 </summary>
  
  - 특징
    - 비동기 넌 블러킹 처리
    - 최소 쓰레드로 최대 성능 - 쓰레드 컨텍스트 스위칭 비용 효율화
    - 함수형 스타일로 개발 - 동시처리 코드 효율화
    - 서블릿 기술 사용 X
  - 그런데..
    - 웹 플럭스는 기술적 난이도 매우 높음
    - 아직은 RDB 지원 부족
    - 일반 MVC의 쓰레드 모델도 충분히 빠르다
    - 실무에서 아직 많이 사용하지는 않음 (전체 1% 이하)
</details>

### 자바 뷰 템플릿 역사

- JSP
  - 속도 느림, 기능 부족
- 프리마커(Freemarker), 벨로시티(Velocity)
  - 속도 문제 해결, 다양한 기능
- 타임리프(Thymeleaf)
  - 내추럴 템플릿: HTML의 모양을 유지하면서 뷰 템플릿 적용 가능
  - 스프링 MVC와 강력한 기능 통합
  - 최선의 선택, 단 성능은 프리마커, 벨로시티가 더 빠름

## Hello 서블릿
스프링 부트는 서블릿을 직접 등록해서 사용 할 수 있도록 `@ServletComponentScan`을 지원한다.

`@WebServlet`
- name: 서블릿 이름
- urlPatterns: URL 매핑

HTTP 요청을 통해 매핑된 URL이 호출되면 서블릿 컨테이너는 다음 메서드를 실행한다.
- `protected void service(HttpServletRequest request, HttpServletResponse response)`

HTTP 요청 메시지 로그로 확인하기
- application.properties 에 `logging.level.org.apache.coyote.http11=debug`
- 서버가 받은 HTTP 요청 메시지를 출력한다.
- 운영서버에 남기면 성능 저하가 일어날 수 있다. 개발 단계에서만 적용하자

### HttpServletRequest 역할
- 서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱한다. 그리고 그
결과를 `HttpServletRequest` 객체에 담아서 제공한다.

임시 저장소 기능   
- 해당 HTTP 요청이 시작부터 끝날 때 가지 유지되는 임시 저장소 기능
  - 저장: `request.setAttribute(name, value)`
  - 조회: `request.getAttribute(name)`

세션 관리 기능
- `request.getSession(create: true)`


## HTTP 요청 데이터 - 개요
HTTP 요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 방법을 알아보자   

주로 다음 3가지 방법을 사용한다.
- GET - 쿼리 파라미터
  - /url?username=hello&age=20
  - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
  - 예) 검색, 필터, 페이징등에서 많이 사용하는 방식
- POST - HTML Form
  - content-type: application/x-www-form-urlencoded
  - 메시지 바디에 쿼리 파라미터 형식으로 전달 username=hello&age=20
  - 예) 회원 가입, 상품 주문, HTML Form 사용
- HTTP message body에 데이터를 직접 담아서 요청
  - HTTP API에서 주로 사용, JSON, XML, TEXT
- 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH

### HTTP 요청데이터 - GET 쿼리 파라미터
메시지 바디 없이, URL의 쿼리 파라미터를 사용해서 데이터를 전달하자.   
쿼리 파라미터는 URL에 다음과 같이 `?` 를 시작으로 보낼 수있다. 추가 파라미터는 `&`로 구분하면 된다.

서버에서는 `HttpServletRequest`가 제공하는 다음 메서드를 통해 쿼리 파라미터를 편리하게 조회할 수 있다.
- `getParameterNames` 전체 파라미터 조회
- `getParameter` 단일 파라미터 조회
  - 중복일때는 `request.getParameterValues()` 의 첫 번째 값을 반환한다.
- `getParameterValues` 중복되는 파라미터 조회

### HTTP 요청데이터 - POST HTML Form
HTML의 Form 을 사용해서 클라이언트에서 서버로 데이터를 전송해보자
주로 회원가입, 상품 주문 등에서 사용하는 방식이다.

특징
- content-type:`application/x-www-form-urlencoded`
- 메시지 바디에 쿼리 파라미터 형식으로 데이터를 전달한다. `username=hello&age=20`

`application/x-www-form-urlencoded` 형식은 앞서 GET에서 살펴본 쿼리 파라미터 형식과 같다. 따라서 쿼리 파라미터 조회 메서드를 그대로 사용하면 된다.   
클라이언트(웹 브라우저) 입장에서는 두 방식에 차이가 있지만, 서버입장에서는 둘의 형식이 동일하므로,`request.getParamter()`로 편리하게 구분없이
조회 할 수 있다.

정리하면 `request.getParameter()`는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원한다.

참고   
content-type은 HTTP 메시지 바디의 데이터 형식을 지정
- GET URL 쿼리 파라미터 형식으로 데이터 전달하면 바디를 사용하지 않기 때문에 content-type이 없다.
- POST HTML Form 형식으로 데이터를 전달하면 HTTP 메시지 바디에 해당 데이터를 포함. 어떤 형식인지 content-type을 지정해야 하고,
이렇게 폼으로 데이터를 전송하는 형식을 `application/x-www-form-urlencoded`라 한다.

### HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트
HTTP message body에 데이터를 직접 담아서 요청
- HTTP API에서 주로 사용, JSON, XML, TEXT
- 데이터 형식은 주 로 JSON 사용
- POST, PUT, PATCH

참고
- InputStream은 byte코드를 반환을 하기때문에 우리가 읽을 수 있는 문자(String)로 보려면 문자표(Charset)를 지정해야한다.

### HTTP 요청 데이터 - API 메시지 바디 - JSON
데이터 전송 POST, content-type:application/json, message body: `{"username": "hello", "age": 20}`
- 결과 : `messageBody = {"username": "hello", "age": 20}`

참고
- JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 Jackson, Gson 같은 JSON 변환 라이브러리를 추가해야 한다.
스프링 부트로 Spring MVC를 선택하면 기본으로 Jackson 라이브러리(`ObjectMapper`)를 함께 제공한다.
- HTML form 데이터도 메시지 바디를 통해 전송되므로 직접 읽을 수 있다. 하지만 편리한 파라미터 조회

### HTTPServletResponse - 기본 사용법

HTTP 응답 메시지 생성
- HTTP 응답코드 지정
- 헤더 생성
- 바디 생성

편의 기능 제공
- Content-Type, 쿠키, Redirect

### HTTP 응답 데이터 - API JSON
HTTP 응답을 JSON 으로 반환할 때는 content-type 을 `application/json`로 지정해야 한다.   
jackson 라이브러리가 제공하는 `objectMapper.writeValueAsString()`를 사용하면 객체를 JSON 문자로 변경할 수 있다.


## JSP로 회원 관리 웹 애플리케이션 만들기
- `<%@ page contentType="text/html;charset=UTF-8" language="java" %>`   
  - 첫 줄은 JSP 문서라는 뜻 JSP 문서는 이렇게 시작해야 한다.

JSP는 자바 코드를 그대로 다 사용할 수 있다.
- `<%@ page import="hello.servlet.domain.member.MemberRepository" %>`
  - 자바의 import 문과 같다.
- `<% ~~~ %>`
  - 이 부분에는 자바 코드 입력 가능
- `<%= ~~ %>`
  - 이 부분에는 자바 코드를 출력할 수 있다.

서블릿 코드와 같지만 다른점이 있다면 HTML 중심으로 하고 , 자바 코드를 부분부분 입력하고 출력한다.

### 서블릿과 JSP의 한계
서블릿으로 개발할 때는 뷰(View)화면을 위한 HTML을 만드는 작업이 자바 코드에 섞여서 지저분하고 복잡했다.   
JSP를 사용한 덕분에 뷰를 생성하는 HTML 작업을 깔끔하게 가져가고, 중간중간 동적으로 변경이 필요한 부분에만 자바 코드를 적용했다.   
JSP코드를 보면 상위 절반은 비즈니스 로직이있고 하위 절반만 결과를 HTML로 보여주기 위한 뷰 영역이다. JAVA 코드, 데이터를 조회하는 리포지토리 등등
다양한 코드가 모두 JSP에 노출되어 있고 JSP가 너무 많은 역할을 한다. 그래서 MVC 패턴이 등장하게 된다.

## MVC 패턴 - 개요
#### 너무 많은 역할
하나의 서블릿이나 JSP만으로 비즈니스 로직과 뷰 렌더링까지 모두 처리하게 되면, 너무 많은 역할을 하게 되고
결과적으로 유지보수가 어려워진다. UI나 비즈니스로직 딱 한부분을 수정해야하는데 둘다있는 코드를 손봐야하기 때문이다.

#### 변경의 라이프 사이클
정말 중요하다. 진짜 문제는 UI와 비즈니스 로직의 변경의 라이프 사이클이 다르다는 점이다.
예를들어 UI를 일부 수정하는 일과 비즈니스 로직을 수정하는 일을 각각 다르게 발생할 가능성이 높고 대부분 서로에게
영향을 주지 않는다. 이렇게 변경의 라이프 사이클이 다른 부분을 하나의 코드로 관리하는 것은 유지보수하기 좋지 않다.

#### 기능 특화
특히 JSP 같은 뷰 템플릿 화면은 렌더링 하는데 최적화 되어 있기 떄문에 이 부분의 업부만 담당하는 것이 가장 효과 적이다.

### Model View Controller
MVC 패턴은 지금까지 학습한 것 처럼 하나의 서블릿이나, JSP로 처리하던 것을 컨트롤러(Controller)와 뷰(View)라는 영역으로 서로
역할을 나눈 것을 말한다. 웹 애플리케이션은 보통 이 MVC 패턴을 사용한다.

컨트롤러
- HTTP 요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행한다. 그리고 뷰에 전달한 결과 데이터를 조회해서 모델에 담는다.

모델
- 뷰에 출력할 데이터를 담아둔다. 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나
데이터 접근을 몰라도 되고, 화면을 렌더링 하는 일에 집중할 수 있다.

뷰
- 모델에 담겨있는 데이터를 사용해서 화면에 그리는 일에 집중한다. 여기서는 HTML을 생성하는 부분을 말한다.

MVC 패턴   

![제목 없음](https://user-images.githubusercontent.com/21376853/150525238-d6754d78-6b95-420e-8e6b-081ea1f8ca39.png)

## MVC 패턴 - 적용
서블릿을 컨트롤러로 사용하고, JSP를 뷰로 사용해서 MVC패턴을 적용해보자.   
Model은 HttpServletRequest 객체를 사용한다. request는 내부에 데이터 저장소를 가지고 있는데, `request.setAttribute()`,`request.getAttribute()`를
사용하면 데이터를 보관하고, 조회할 수 있다.

`WEB-INF`
- 이 경로안에 JSP가 있으면 외부에서 직접 JSP를 호출할 수 없다. 우리가 기대하는 것은 항상 컨트롤러를 통해서
JSP를 호출하는 것이다.

redirect vs forward
- 리다이렉트는 실제 클라이언트(웹 브라우저)에 응답이 나갔다가, 클라이언트가 redirect 경로로 다시 요청한다.
따라서 클라이언트가 인지할 수 있고, URL 경로도 실제로 변경된다. 반면에 포워드는 서버 내부에서 일어나는 호출이기
클라이언트가 전혀 인지하지 못한다.

## MVC 패턴 - 한계
단점
1. 포워드 중복
   - View로 이동하는 코드 항상 중복
2. ViewPath에 중복 
   - /WEB-INF/views/ , .jsp
   - 만약 jsp가 아닌 thymeleaf 같은 다른 뷰로 변경하려면 전체 코드를 바꿔야함. 
3. 사용하지 않는 코드
4. 공통 처리가 어렵다.
   - 공통 기능을 메서드로 뽑으면 될 것 같지만, 결과적으로 해당 메서드를 항상 호출해야 하고 이것 자체도 중복이다.

정리하면 공통 처리가 어렵다라는 문제가 있다. 프론트 컨트롤러 패턴을 도입하면 해결할 수 있다!!

## MVC 프레임워크 만들기
<details><summary> 프론트 컨트롤러 도입 전/후 img</summary>

![제목 없음](https://user-images.githubusercontent.com/21376853/150569146-422cc347-11d7-45e0-b445-e5bdfe6485f5.png)
</details>

FrontController 패턴 특징
- 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
- 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출
- 입구를 하나로!
- 공통 처리 가능
- 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨

스프링 웹 MVC와 프론트 컨트롤러
- 스프링 웹 MVC의 핵심도 바로 FrontController
- 스프링 웹 MVC의 `DispatcherServlet`이 FrontController 패턴으로 구현되어 있음

### 프론트 컨트롤러 도입 - v1
프론트 컨트롤러를 단계적으로 도입하기, 기존 코드를 최대한 유지하면서 프론트 컨트롤러를 도입하자.

<details><summary> V1 구조</summary>

![제목 없음](https://user-images.githubusercontent.com/21376853/150628558-0b0ab610-a669-4117-afe5-6e156530768d.png)
</details>

서블릿과 비슷한 모양의 컨트롤러 인터페이스를 도입. 각 컨트롤러들은 이 인터페이스를 구현하고 프론트 컨트롤러는 이 인터페이스를
호출해서 구현과 관계없이 로직의 일관성을 가지게 됨.

urlPatterns
- `urlPatterns = "/front-controller/v1/*`: `/front-controller/v1`를 포함한 하위 모든 요청은 서블릿에서 받아들인다.
- 예) `/front-controller/v1`,  `/front-controller/v1/a`, `/front-controller/v1/a/b`

controllerMap
- key: 매핑 URL
- value: 호출될 컨트롤러

service()
- 먼저 `requestURI`를 조회하여 실제 호출할 컨르롤러를 `controllerMap`에서 찾는다. 만약 없다면 404상태코드 반환
- 컨트롤러를 찾고 `controller.process(request,response);`을 호출해서 해당 컨트롤러를 실행한다.

### View 분리 - v2
<details><summary> V2 구조</summary>

![제목 없음](https://user-images.githubusercontent.com/21376853/150631212-22509e46-a5f1-4176-9d53-57602adafb38.png)
</details>

`MyView` 객체를 만들어 프론트 컨트롤러의 `render()`를 호출하는 부분을 모두 일관되게 처리할 수 있다.
각각의 컨트롤러는 `MyView` 객체를 생성만 해서 반환하면 된다.

### Model 추가 - v3
<details><summary> V3 구조</summary>

![image](https://user-images.githubusercontent.com/21376853/150772817-2144b3bb-eb48-4dfe-a2b8-3577f92660cd.png)

</details>

ModelView
- 서블릿의 종속성을 제거하기 위해 Model을 직접 만들고, 추가로 View 이름까지 전달하는 객체를 만들어 보자.
- 컨트롤러에서는 HttpServletRequest를 사용할 수 없어 Model이 별도로 필요하다.

뷰 리졸버
- `MyView view = viewResolver(viewName)`
- 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경. 그리고 실제 물리 경로가 있는 MyView 객체를 반환
- 논리 뷰를 받게되면 실제 파일 경로가 바껴도 프론트컨트롤러 하나만 수정을 하면 된다.

### 단순하고 실용적인 컨트롤러 - v4
앞서 만든 v3 컨트롤러는 서블릿 종속성을 제거하고 뷰 경로의 중복을 제거하는 등, 잘 설계 되었지만 개발자 입장에서는 항상
ModelView 객체를 생성하고 반환해야 하는 부분이 번거롭다. 이를 매개변수를 통해 해결해 보자.

<details><summary> V4 구조</summary>

![image](https://user-images.githubusercontent.com/21376853/150778968-9b741064-aae9-40eb-a9e2-0c3216742430.png)

</details>

기본적인 구조는 V3와 같다. 대신에 컨트롤러가 `ModelView`를 반환하지 않고, `ViewName`만 반환한다.   
이번 버전은 인터페이스에 ModelView가 없고 model 객체는 파라미터로 전달되기 때문에 그냥 사용하면 되고, 결과로 뷰의 이름만 반환해주면 된다.

모델 객체 전달
`Map<String, Object> model = new HashMap<>();` 추가
- 모델 객체를 프론트 컨트롤러에서 생성해서 넘겨준다. 컨트롤러에서 모델 객체에 값을 담으면 여기에 그대로 담겨있게 된다.

### 유연한 컨트롤러1 - v5
지금까지는 한가지 방식의 컨트롤러 인터페이스만 사용할 수 있다.   
`ControllerV3`,`ControllerV4`는 완전히 다른 인터페이스라 호환이 불가하다. 이럴때 어댑터 패턴을 사용해서
프론트 컨트롤러가 다양한 방식의 컨트롤러를 처리할 수 있도록 변경해보자.

<details><summary> V5 구조</summary>

![image](https://user-images.githubusercontent.com/21376853/150796946-36457ba1-a9ea-477a-adad-d7fc9c958865.png)

</details>

핸들러 어탭터
- 중간에 어탭터 역할을 하는 어탭터이다. 여기서 어탭터 역할을 해주는 덕분에 다양한 종류의 컨트롤러를 호출할 수 있다.

핸들러
- 컨트롤러의 이름을 더 넓은 범위인 핸들러로 변경했다. 그 이유는 이제 어탭터가 있기 때문에 꼭 컨트롤러의 개념 뿐만 아니라 
어떠한 것이든 해당하는 종류의 어댑터만 있으면 다 처리할 수 있기 때문이다.

MyHandlerAdapter 인터페이스
- `boolean supports(Object handler`
  - handler는 컨트롤러 이다.
  - 어댑터가 해당 컨트롤러를 처리할 수 있는지 판단하는 메서드.
- `ModelView handle(HttpServletRequest request, HttpServletResponse response, Object
  handler)`
    - 어댑터는 실제 컨트롤러를 호출하고, 그 결과를 ModelView를 반환해야 한다.
    - 실제 컨트롤러가 ModelView를 반환하지 못하면, 어탭터가 ModelView를 직접 생성해서라도 반환해야 한다.
    - 이전에는 프론트 컨트롤러가 실제 컨트롤러를 호출했지만 이제는 이 어댑터를 통해서 실제 컨트롤러가 호출된다.





## 스프링 MVC 전체 구조

<details><summary> SpringMVC 구조</summary>

![image](https://user-images.githubusercontent.com/21376853/150930346-f8ff8bcb-032b-4c6e-b37f-0098d5f01fa5.png)

</details>

### DispatcherServlet 구조 살표기
스프링 MVC의 프론트 컨트롤러가 바로 디스패처 서블릿이다.   
이 디스패처 서블릿이 바로 스프링 MVC 의 핵심

__DispacherServlet 서블릿 등록__
- `DispatcherServlet` 도 부모 클래에서 `HttpServlet`을 상속 받아서 사용하고, 서블릿으로 동작한다.
  - DispatcherServlet -> FrameworkServlet -> HttpServletBean -> HttpServlet
- 스프링 부트는 `DispatcherServlet`을 서블릿으로 자동으로 등록하면서 모든 경로 (`urlPatterns="/")` 에 대해서 매핑한다.
  - 참고: 더 자세한 경로가 우선순위가 높다. 그래서 기존에 등록한 서블릿도 함께 동작한다.
 

__동작 순서__
1. __핸들러 조회__ : 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.
2. __핸들러 어댑터 조회__: 핸들러를 실행할 수 있는 핸들러 어탭터를 조회한다.
3. __핸들러 어탭터 실행__: 핸들러 어댑터를 실행한다.
4. __핸들러 실행__: 핸들러 어댑터가 실제 핸들러를 실행한다.
5. __ModelAndView 반환__: 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView 로 변환해서 반환한다.
6. __viewResolver 호출__: 뷰 리졸버를 찾고 실행한다.
7. __View 반환__: 뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 담당하는 뷰 객체를 반홚나다.
8. __뷰 렌더링__: 뷰를 통해서 뷰를 렌더링 한다.

__인터페이스 살펴보기__
- 스프링 MVC의 큰 강점은 `DispatcherServlet` 코드의 변경없이, 원하는 기능을 변경하거나 확장할 수 있다는 점이다.

### 핸들러 매핑과 핸들러 어댑터

스프링 부트가 자동 등록하는 핸들러 매핑과 핸들러 어댑터

__HandlerMapping__
```
0 = RequestMappingHandlerMapping: 애노테이션 기반의 컨트롤러인 @RequestMapping 에서 사용
1 = BeanNameUrlHandlerMapping:  스프링 빈의 이름으로 핸들러를 찾는다.
```
__HandlerAdapter__
```
0 = RequestMappingHandlerAdapter: 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
1 = HttpRequestHandlerAdapter: HttpRequestHandler 처리
2 = SimpleControllerHandlerAdapter: Controller 인터페이스(애노테이션X, 과거에 사용)처리
```
핸들러 매핑도, 핸들러 어댑터도 모두 순서대로 찾고 만약 없으면 다음 순서로 넘어간다.

__@RequestMapping__  
- 주로 사용되는 애노테이션 기반의 컨트롤러를 지원하는 매핑과 어댑터, 뒤에서 정리

### 뷰 리졸버

뷰 리졸버 - InternalResourceViewResolver
- 스프링 부트는 `InternalResourceViewResolver`라는 뷰 리졸버를 자동 등록하는데, 이때 `application.properties`에 등록한 `spring.mvc.view.prefix`,
`spring.mvc.view.suffix` 설정 정보를 사용해서 등록한다.

스프링 부트가 자동 등록하는 뷰 리졸버
```
1 = BeanNameViewResolver: 빈이름으로 뷰를 찾아서 반환한다. (예: 엑셀 파일 생성 기능에 사용)
2 = InternalResourceViewResolver: JSP를 처리할 수 있는 뷰를 반환한다.
```

## 스프링 MVC 시작하기

__@RequestMapping__
- 요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다. 애노테이션 기반으로 동작하기 때문에, 메서드의 이름은 임의로 지으면 된다.
- 가장 우선순위가 높은 핸들러 매핑과 핸들러 어댑터이다.
  - `RequestMappingHandlerMapping` 
    - 스프링 빈 중에서 `@RequestMapping` 또는 `@Controller` 가 클래스 레벨에 붙어 있는 경우에 매핑 정보로 인식한다.
  - `RequestMappingHandlerAdapter`

__@Controller__
- 스프링이 자동으로 스프링 빈으로 등록한다. (내부에 `@Component` 애노테이션이 있어서 컴포넌트 스캔 대상이 된다.)
- 스프링 MVC에서 애노테이션 기반 컨트롤러로 인식한다.

__ModelAndView__
- 모델과 뷰 정보를 담아서 반환하면 된다.

### 스프링 MVC - 컨트롤러 통합
`@RequestMapping`
- 메서드 단위에 적용이 아닌 클래스 하나로 통합이 가능하다.
- 컨트롤러 클래스를 통합하는 것을 넘어서 조합도 가능하다.
- 클래스 레벨에 `@RequestMapping` 을 두면 메서들 레벨과 조합이 된다.

### 스프링 MVC - 실용적인 방식
Model 파라미터
- Model을 파라미터로 받을 수 있는 편의기능을 제공한다.

ViewName 직접 반환
- 뷰의 논리 이름을 반환할 수 있따.

__@RequestParam__ 사용
- 스프링은 HTTP 요청 파라미터를 `@RequestParam`으로 받을 수 있다. 
- `@RequestParam("username")` 은 ` request.getParameter("username")` 와 거의 같은 코드라 생각하면 된다.

@RequestMapping -> @GetMapping, @PostMapping
- `@RequestMapping`은 URL만 매칭하는 것이 아니라, HTTP Method도 함께 구분할 수 있다.

## 스프링 MVC - 기본기능

[참고] 
- Jar : 항상 내장 서버(톰캣등)을 사용하고, `webapp` 경로도 사용하지 않는다. 내장 서버 사용에 최적화 되어 있는 기능.
- War : WAS서버를 서블릿 컨테이너 별도로 설치하고 Build 된 파일을 넣을 때, JSP 사용할 때

### 로깅 간단히 알아보기
운영 시스템에서는 `System.out.println()` 같이 콘솔을 사용해서 필요한 정보를 출력하지 않고, 별도의 로깅 라이브러리를 사용해서 출력한다.

로깅 라이브러리
- 스프링 부트 라이브러리를 사용하면 스프링 부트 로깅 라이브러리가 함께 포함된다.(`spring-boot-starter-logging`)
- 스프링 부트 로깅 라이브러리는 기본으로 다음 로깅 라이브러리를 사용한다.
  - SLF4J , Logback

로그 선언
- `private Logger log = LoggerFactory.getLogger(getClass())`
- `private static final Logger log = LoggerFactory.getLogger(Xxx.class)`
- `@Slf4j`: 롬복 사용 가능

매핑 정보
- `@RestController`
  - `@Controller`는 반환 값이 `String`이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.
  - `@RestController`는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다. 뒤에서 더 자세히 설명!

테스트
- 로그가 출력되는 포멧 확인
  - 시간, 로그 레벨, 프로세스 ID, 쓰레드 명, 클래스 명, 로그 메시지
- 로그 레벨 설정을 변경해서 출력 결과를 보자
  - LEVEL: `TRACE > DEBUG > INFO > WARN > ERROR`
  - 개발 서버는 debug 출력
  - 운영 서버는 info 출력

로그 레벨 설정
- `application.properties` 에서 관리한다.

올바른 로그 사용법
- `log.debug("data = " + data)`
  - 로그 출력 레벨을 info로 설정해도 해당 코드가 실제 실행이 된다. 결과적으로 문자 더하기 연산이 발생 실무에서 사용 하면 욕먹는다.
- `log.debug("data={}", data)` 를 사용하자!!!!

로그 사용시 장점
- 쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.
- 로그 레벨에 따라 특정서버에 특정로그를 출력할 수 있다. 상황에 맞게 조절 가능
- 시스템 아웃 콘솔뿐만 아니라 파일, 네트워크 등 별도의 위치에 남길 수 있다. 파일로 남길 때는 일별, 특정 용량에 따라 로그 분할 가능
- 성능도 System.out 보다 좋다 (내부 버퍼링, 멀티 쓰레드 등등) 실무에서는 꼭 로그 사용!

### 요청 매핑

1. HTTP 메서드
   - `@RequestMapping`에 `method` 속성으로 HTTP 메서드를 지정하지 않으면 HTTP 메서드와 무관하게 호출된다.
   - GET, HEAD, POST, PUT, PATCH, DELETE 모두 허용
2. HTTP 메서드 매핑
   - method = RequestMethod.GET..POST 등
3. HTTP 메서드 매핑 축약
   - HTTP 메서드를 축약한 애노테애이션을 사용하는 것이 더 직관적
   - `GetMapping` , `@PostMapping` ...
4. PathVariable(경로 변수) 사용
   - 최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호
   - /mapping/userA  , /users/1
   - `@RequestMapping`은 URL 경로를 템플릿화 할 수 있는데, `@PathVariable`을 사용하면 매칭 되는 부분을 편리하게 조회할 수 있다.
   - `@PathVariable`의 이름과 파라미터 이름이 같으면 생략 가능
5. 특정 파라미터 조건 매핑
   - 특정 파라미터가 있거나 없는 조건 추가, 잘 사용 x
6. 특정 헤더 조건 매핑
   - 파라미터 매핑과 비슷하지만, HTTP 헤더를 사용한다.
7. 미디어 타입 조건 매핑 - HTTP 요청 Content-Type, consume
   - HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑, 맞지 않으면 415 반환 
8. 미디어 타입 조건 매핑 - HTTP 요청 Accept, produce
   - HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매칭, 맞지않으면 406 반환

### HTTP 요청 - 기본, 헤더 조회
HTTP 헤더 정보를 조회하는 방법을 알아보자.
- `HttpServletRequest`
- `HttpServletResponse`
- `HttpMethod`: HTTP 메서드를 조회 한다.
- `Locale`: Locale 정보를 조회한다.
- `@RequestHeader MultiValueMap<String, String> headerMap`
  - 모든 HTTP 헤더를 MultiValueMap 형식으로 조회한다.
  - MAP 과 유사한데, 하나의 키에 여러 값을 받을 수 있따.
- `@RequestHeader("host") String host`
  - 특정 HTTP 헤더를 조회한다.
  - 속성 : 필수 값 여부 : `required` , 기본 값 속성 : `dafaultValue`
- `@CookieValue(value = "myCookie", required = false) String cookie`
  - 특정 쿠키를 조회한다.
  - 속성 : 필수 값 여부 : `required` , 기본 값 속성 : `dafaultValue`

### HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form

클라이언트에서 서버로 요청 데이터를 전달할 때는 주로 다음 3가지 방법 사용
1. GET - 쿼리 파라미터 : 메시지 바디없이, URL의 쿼리 파라미터에 데이터를 포함
2. POST - HTML Form : 메시지 바디에 쿼리 파라미터 형식으로 전달
3. HTTP message body 에 데이터를 직접 담아서 요청

GET 쿼리 파라미터 전송 방식이든, POST HTML Form 전송 방식이든 둘다 형식이 같으므로 구분없이 조회 가능.   
이것을 간단히 `요청 파라미터(request parameter) 조회` 라 한다.


[참고] : `Jar` 사용하면 `webapp` 경로를 사용할 수 없다. 이제부터 정적 리소스도 클래스 경로에 함께 포함해야 한다.

### HTTP 요청 파라미터 - @RequestParam
스프링이 제공하는 `@RequestParam`을 사용하면 요청 파라미터를 매우 편리하게 사용할 수 있다.
- `RequestParam`: 파라미터 이름으로 바인딩
- `@ResponseBody`: View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력

@RequestParam의 `name(value)` 속성이 파라미터 이름으로 사용
- @RequestParam("__username__") String __memberName__
- => request.getParameter("__username__)

HTTP 파라미터 이름이 변수 이름과 같으면 `@RquestParam(name="xx")` 생략 가능하다.  
`String` ,`int`, `Integer` 등의 단순 타입이면 `@RequestParam`도 생략 가능

`@RequestParam.required`
- 파라미터 필수 여부
- 기본 값이 파라미터 필수(true) 이다. 
- 파라미터가 들어오지 않으면 400 예외 발생
- `request-param?username=` 과 같이 파라미터 이름만 있고 값이 없으면 빈 문자로 통과된다.
- `required = false` 처리하면 null 이 입력된다. 자료형은 null 이 들어갈 수 없으므로 주의한다.

기본 값 적용 - requestParamDefault
- 파라미터에 값이 없는 경우 `defaultValue`를 사용하면 기본 값 적용이 가능하다.
- 이미 기본 값이 있기 때문에 `required`의 의미가 없다.
- `defaultValue`는 빈 문자의 경우에도 설정한 기본 값이 적용 된다.

파라미터를 Map으로 조회하기 - requestMap
- 파라미터를 Map, MultiValueMap 으로 조회 할 수 있다.
- `@RequestParam Map`
  - `Map(key = value)`
- `@RequestParam MultiValueMap`
  - `MultiValueMap(key=[value1, value2, ... ] ` ex) (key = userIds, value=[id1, id2])

### HTTP 요청 파라미터 - @ModelAttribute

롬복 `@Data`
- `@Getter` , `@Setter`, `@ToString`, `@EqualsAndHascode`, `@RequiredArgsConstructor` 를 자동으로 적용해준다.

스프링 MVC는 `ModelAttribute`가 있으면 다음을 실행한다.
- 바인딩을 받는 `HelloData` 객체를 생성
- 요청 파라미터의 이름으로 `Hellodata` 객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩)한다.
- 예) 파라미터 이름이 `username` 이면 `setUsername()` 메서드를 찾아서 호출하면서 값을 입력한다.

`@ModelAttribute` 는 생략 할 수 있다. 그런데 `@RequestParam`도 생략이 가능하다.   
스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
- `String`, `int`, `Integer` 같은 단순 타입 = `@RequestParam`
- 나머지 = `@ModelAttribute` (argument resolver 로 지정해둔 타입 외)

### HTTP 요청 메시지 - 단순 텍스트
요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 넘어오는 경우는 `@RequestParam` , `@ModelAttribute`를 사용할 수 없다.
(물론 HTML Form 형식으로 전달되는 경우는 요청 파라미터로 인정된다.)

스프링 MVC는 다음 파라미터를 지원한다.
- HttpEntity: HTTP header, body  정보를 편리하게 조회
    - 메시지 바디 정보를 직접 조회
    - 요청 파라미터를 조회하는 기능과 관계 없음 `@RequestParam` X, `ModelAttribute` X
- HttpEntity는 응답에도 사용 가능
  - 메시지 바디 정보 직접 반환
  - 헤더 정보 포함 가능
  - view 조회 X

__@RequestBody__   
`@RequestBody`를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가 필요하다면
`HttpEntity`를 사용하거나 `@RequestHeader`를 사용하면 된다.   
이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 `RequestParam`, `@ModelAttribute`와는 전혀 관계 없다.

요청 파라미터 vs HTTP 메시지 바디   
- 요청 파라미터를 조회하는 기능: `@RequestParam`, `@ModelAttribute`
- HTTP 메시지 바디를 직접 조회하는 기능: `@RequestBody`

__@ResponseBody__   
`@ResponseBody`를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다. 물론 이 경우에도 view를 사용하지 않는다.

### HTTP 요청 메시지 - JSON

@RequestBody 객체 파라미터
- `@RequestBody HelloData data`
- `@RequestBody`에 직접 만든 객체를 지정할 수 있다.

`HttpEntity`, `@RequestBody`를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.   
HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON도 객체로 변환해준다.

`@RequestBody`는 생략 불가능   
- 스프링은 `@ModelAttribute`, `@RequestParam` 해당 생략시 다음과 같은 규칙을 적용한다.
- `String`, `int` ,`Integer` 같은 단순 타입 = `@RequestParam`
- 나머지 = `ModelAttribute` (argument resolver로 지정해둔 타입 외)
- 생략을 하게 되면 HTTP 메시지 바디가 아닌 요청 파라미터를 처리하게 됨
- HttpEntity 를 사용해도 된다.
- JSON 요청 => HTTP 메시지 컨버터 => 객체

>[주의] HTTP 요청시에 content-type 이 application/json 인지 꼭 확인해야한다. 그래야 JSON을 처리할 수 있는 HTTP 메시지 컨버터가 실행됨.

`@ResponseBody`   
응답의 경우에도 `@ResponseBody`를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다. 물론 이 경우에도 HttpEntity 사용 가능
- 객체 => HTTP 메시지 컨버터 => JSON 응답

### HTTP 응답 - 정적 리소스, 뷰 템플릿
스프링(서버)에서 응답 데이터를 만드는 방법은 3가지이다.

1. 정적 리소스
   - 예) 웹 브라우저에 정적인 HTML, css, js을 제공할 때는, 정적 리소스를 사용한다.
2. 뷰 템플릿 사용
   - 예) 웹 브라우저에 동적인 HTML을 제공할 때는 뷰 템플릿을 사용한다.
3. HTTP 메시지 사용
   - HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야하므로, HTTP 메시지 바디에 JSON 같은 형식으로 데이터를 실어 보낸다.

__정적 리소스__   
- 스프링 부트는 클래스패스의 다음 디렉토리에 있는 정적 리소스를 제공한다.   
  - `/static`, `/public`, `/resources`, `/META_INF/resources`
- `src/main/resources`는 리소스를 보관하는 곳이고, 클래스패스의 시작 경로이다.   
- 따라서 다음 디렉토리에 리소스를 넣어두면 스프링 부트가 정적 리소스로 서비스를 제공한다.

<details><summary> 예시 </summary>

- 다음 경로에 파일이 들어있으면
  - `src/main/resources/static/basic/hello-form.html`
- 웹 브라우저에서 다음과 같이 실행하면 된다.
  - `http://localhost:8080/basic/hello-form.html`
- 정적 리소스는 해당 파일을 변경 없이 그대로 서비스하는 것이다.
</details>

__뷰 템플릿__   
- 뷰 템플릿을 거쳐서 HTML이 생성되고, 뷰가 응답을 만들어서 전달한다.   
- 일반적으로 HTML을 동적으로 생성하는 용도로 사용하지만, 다른 것들도 가능하다. 뷰 템플릿이 만들 수 있는 것이라면 뭐든지 가능.

String을 반환하는 경우 - View or HTTP 메시지
- `@ResponseBody`가 없으면 `response/hello`로 뷰 리졸버가 실행되어서 뷰를 찾고, 렌더링 한다.   
- `@ResponseBody`가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 `response/hello`라는 문자가 입력된다.

### HTTP 메시지 컨버터
뷰 템플릿으로 HTML을 생성해서 응답하는 것이 아니라, HTTP API 처럼 JSON 데이터를 HTTP메시지 바디에서 직접 읽거나 쓰는 경우
HTTP 메시지 컨버터를 사용하면 편리하다.

__스프링 MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.__
- HTTP 요청: `@RequestBody`, `HttpEntity(RequestEntitiy)`
- HTTP 응답: `@ResponseBody`, `HttpEntity(ResponseEntity)`

스프링 부트 기본 메시지 컨버터(일부 생략)
```
0 = ByteArrayHttpMessageConverter
1 = StringHttpMessageConverter
2 = MappingJackson2HttpMessageConverter
```
스프링 부트는 다양한 메시지 컨버터를 제공하는데, 대상 클래스 타입과 미디어 타입 둘을 체크해서 사용 여부 결정.
만약 만족하지 않으면 다음 메시지 컨버터로 우선순위가 넘어간다.

몇가지 주요한 메시지 컨터버를 알아보자.
1. `ByteArrayHttpMessageConverter` : `byte[]` 데이터를 처리한다.
    - 클래스 타입: `byte[]`, 미디어타입: `*/*`
    - 요청 예) `@RequestBody byte[] data`
    - 응답 예) `@ResponseBody return byte[]` 쓰기 미디어타입 `application/octet-stream`
2. `StringHttpMessageConverter` : `String` 문자로 데이터를 처리한다.
    - 클래스 타입 : `String` 미디어타입: `*/*`
    - 요청 예) `@RequestBody String data`
    - 응답 에) `@ResponseBody return "ok"`  쓰기 미디어타입 `text/plain`
3. `MappingJackson2HttpMessageConverter/json` : application/json
    - 클래스 타입 : 객체 또는 `HashMap`, 미디어타입 `application/json` 관련
    - 요청 예) `@RequestBody HelloData data`
    - 응답 예) `@ResponseBody return helloData` 쓰기 미디어타입 `application/json`관련

HTTP 요청 데이터 읽기
- HTTP 요청이 오고, 컨트롤러에서 `@RequestBody`, `HttpEntity` 파라미터를 사용한다.
- 메시지 컨버터가 메시지를 읽을 수 있는지 확인하기 위해 `canRead()`를 호출한다.
    - 대상 클래스 타입을 지원하는가.
        - 예) `@RequestBody`의 대상 클래스 (`byte[]`, `String`, `HelloData`)
    - HTTP 요청의 Content-Type 미디어 타입을 지원하는가.
        - 예) `text/plain`, `application/json`, `*/*`
    - `canRead()` 조건을 만족하면 `read()`를 호출해서 객체 생성하고, 반환한다.

HTTP 응답 데이터 생성
- 컨트롤러에서 `@ResponseBody`, `HttpEntity`로 값이 반환된다.
- 메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 `canWrite()`를 호출한다.
    - 대상 클래스 타입을 지원하는가.
        - 예) return의 대상 클래스 (`byte[]`, `String`, `HelloData`)
    - HTTP 요청의 Accept 미디어 타입을 지원하는가.(더 정확히는 `@RequestMapping`의 `produces`)
        - 예) `text/plain`, `application/json` , `*/*`
    - `canWrite()` 조건을 만족하면 `write()`를 호출해서 HTTP 응답 메시지 바디에 데이터를 생성한다.


### 요청 매핑 핸들러 어뎁터 구조
![image](https://user-images.githubusercontent.com/21376853/151933163-b9a9e87c-ac68-4c32-9284-da1a7667c607.png)


__ArgumentResolver__   
- 애노테이션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할 수 있다.  
- `HttpServletRequest`, `Model`,`@RequestParam`, `@ModelAttribute` 애노테이션 그리고 `@RequestBody`, `HttpEntity` 같은 HTTP메시지를
부분까지 매우 큰 유연함을 보여줌
- 이렇게 파라미터를 유연하게 처리할 수 있는 이유가 바로 `ArgumentResolver` 덕분이다.

애노테이션 기반 컨트롤러를 처리하는 `RequestMappingHandlerAdaptor`는 바로 이 `ArgumentResolver`를 호출해서 컨트롤러(핸들러)가 필요로 하는 다양한
파라미터 값(객체)을 생성한다. 그리고 이렇게 파라미터 값이 모두 준비되면 컨트롤러를 호출하면서 값을 넘겨줌.

__ReturnValueHandler__
- `ArgumentResolver`와 비슷한데, 이것은 응답 값을 변환하고 처리한다.
- 컨트롤러에서 String으로 뷰 이름을 반환해도, 동작하는 이유가 바로 ReturnValueHanlder 덕분이다.


__HTTP 메시지 컨버터__
![image](https://user-images.githubusercontent.com/21376853/151933729-34073cd4-316d-47bf-a162-278b140c04c2.png)
- HTTP 메시지 컨버터를 사용하는 `@RequestBody` 도 컨트롤러가 필요로 하는 파라미터의 값에 사용된다.
- `@ResponseBody`의 경우도 컨트롤러의 반환 값을 이용한다.

요청의 경우 
- `@RequestBody`를 처리하는 `ArgumentResolver`가 있고, `HttpEntity`를 처리하는 `ArguemntResolver`가 있다. 이 `ArguemntResolver`
들이 HTTP 메시지 컨버터를 사용해서 필요한 객체를 생성하는 것이다.

응답의 경우
- `ResponseBody`와 `HttpEntity`를 처리하는 `ReturnValueHandler`가 있다. 그리고 여기에서 HTTP 메시지 컨버터를
호출해서 응답 결과를 만든다.

## 스프링 MVC - 웹 페이지 만들기

요구사항 분석
- 상품을 관리할 수 있는 서비스를 만들어보자.

상품 도메인 도멜
- 상품 ID
- 상품명
- 가격
- 수량

상품 관리 기능
- 상품 목록
- 상품 상세
- 상품 등록
- 상품 수정

![image](https://user-images.githubusercontent.com/21376853/151969543-b50c1cea-4cd1-4376-849d-283e994cc880.png)
백엔드 개발자 : 디자이너 , 웹 퍼블리셔를 통해서 HTML 화면이 나오기 전까지 시스템을 설계하고, 핵심 비즈니스 모델을 개발한다.
이후 HTML이 나오면 이 HTML을 뷰 템플릿으로 변환해서 동적으로 화면을 만들고, 웹 화면의 흐름을 제어한다.

### 상품 목록 - 타임리프
 
타임리프 간단히 알아보기   
- 선언 : <`html xmlns:th="http://www.thymeleaf.org"`>

속성 변경 - th:href   
`th:href="@{/css/bootstrap.min.css}"`
- `href="value"1`을 `th:href="value2"`의 값으로 변경한다
- 타임리프 뷰 템플릿을 거치게 되면 원래 값을 `th:xxx` 값으로 변경한다. 만약 값이 없다면 새로 생성한다.
- HTML을 그대로 볼 때는 `href`속성이 사용되고, 뷰 템플릿을 거치면 `th:href`의 값이 `href`로 대체되면서 동적으로 변경할 수 있다.
- 대부분의 HTML 속성을 `th:xxx` 로 변경할 수 있다.

__타임리프 핵심__
- 핵심은 `th:xxx` 가 붙은 부분은 서버사이드에서 렌더링 되고, 기존 것을 대체한다. `th:xxx`이 없으면 기존 html의 `xxx`속성이 그대로 사용된다.
- HTML을 파일로 직접 열었을 때, `th:xxx`가 있어도 웹 브라우저는 `th:` 속성을 알지 못하므로 무시한다.
- 따라서 HTML을 파일 보기를 유지하면서 템플릿 기능도 할 수 있다.
- 순수 HTML을 그대로 유지하면서 뷰 탬플릿도 사용할 수 있는 타임리프의 특징을 내츄럴 템플릿이라 한다.


URL 링크 표현식 - @{...}   
`th:href="@{/css/bootstrap.min.css}"`
- `@{...}`: 타임리프는 URL 링크를 사용하는 경우 `@{...}`를 사용한다. 이것을 URL 링크 표현식이라 한다.
- URL 링크 표현식을 사용하면 서블릿 컨텍스트를 자동으로 포함한다.

속성 변경 - th:onclick
- `onclick="location.href='addForm.html'"
- `th:onclick="|location.href='@{/basic/items/add}'|"`
 
리터럴 대체 - |...|
- 타임리프에서 문자와 표현식 등은 분리되어 있기 때문에 더해서 사용해야 한다.
  - `span th:text="'Welcome to our application,' + ${user.name} + '!'"`
- 다음과 같이 리터럴 대체 문법을 사용하면, 더하기 없이 편하게 사용할 수 있다.
  - `span th:text="|Welcome to our application, ${user.name}!|"`
- 결과를 다음과 같이 만들어야 하는데
  - `location.href='basic/items/add'`
- 그냥 사용하면 문자와 표현식을 각각 따로 사용해야 하므로 다음과 같이 복잡해진다.
  - `th:onclick="'location.href=' + '\'' + @{/basic/items/add} + '\''"`
- 리터럴 대체 문법을 사용하면 다음과 같이 편리하게 사용할 수 있다.
  - `th:onclick="|location.href='@{/basic/items/add}'|"`

반복 출력 - th:each
- `tr th:each="item : ${items}"`
- 반복은 `th:each`를 사용한다. 이렇게 하면 모델에 포함된 `items` 컬렉션 데이터가 `item` 변수에 하나씩 포함되고, 반복문 앞에서
`item` 변수를 사용할 수 있다.
- 컬렉션의 수 만큼 `tr .. /tr` 이 하위 태그를 포함해서 생성된다.

변수 표현식 - ${...}
- td tr th:text="${item.price}">10000</td
- 모델에 포함된 값이나, 타임리프 변수로 선언한 값을 조회할 수 있다.
- 프로퍼티 접근법을 사용한다. (`item.getPrice())

내용 변경 - th:text
- td tr th:text="${item.price}">10000</td
- 내용의 값을 `th:text`의 값으로 변경한다.
- 여기서는 10000을 `${item.price}` 의 값으로 변경한다.

URL 링크 표현식 2  - @{...}
- `th:href="@{/basic/items/{itemId}(itemId=${item.id})}"`
- 경로 변수 `{itemId}` 뿐만 아니라 쿼리 파라미터도 생성한다.
- 예) `th:href="@{/basic/items/{itemId}(itemId=${item.id}, query='test')}"`
    - 생성 링크:  `http://localhost:8080/basic/items/1?query=test`

URL 링크 간단히
- `th:href="@{|/basic/items/${item.id}|}"`
- 리터럴 대체 문버을 활용해서 간단히 사용할 수 있다.

 속성 변경 - th:action
 - `th:action`
 - HTML form 에서 `action`에 값이 없으면 현재 URL 에 데이터를 전송한다.
 - 상품 등록 폼의 URL과 실제 상품 등록을 처리하는 URL을 똑같이 맞추고 HTTP 메서드로 두 기능을 구분
   - 상품 등록 폼: GET`/basic/items/add`
   - 상품 등록 처리: POST`/basic/items/add`
 - 이렇게 하면 하나의 URL로 등록 폼과, 등록 처리를 깔끔하게 처리할 수 있다.

### 상품 등록 처리 - @ModelAttribute
상품 등록 폼은 다음 방식으로 서버에 데이터를 전달한다.
- POST - HTML Form

요청 파라미터 형식으로 처리해야 하므로 `@RequestParam` 사용
- `@RequestParam String itemName`: itemName 요청 파라미터 데이터를 해당 변수에 받는다.
- `Item` 객체를 생성하고 `itemRepository`를 통해서 저장한다.
- 저장된 `item`을 모델에 담아서 뷰에 전달한다.

@ModelAttribute - 요청 파라미터 처리
- `@ModelAttribute`는 `Item`객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx) 으로 입력해준다.

@ModelAttribute - Model 추가
- `ModelAttribute`는 중요한 한가지 기능이 더 있다. 바로 모델(Model)에 `@ModelAttribute`로 지정한 객체를 자동으로 넣어준다.
- 모델에 데이터를 담을 때는 이름이 필요하다. 이름은 `ModelAttribute`에 지정한 `name(value)` 속성을 사용한다.
- `ModelAttribute` 의 이름을 생략할 수 있다.
  - 주의: 모델에 저장될 때 클래스명을 사용하는데 첫글자만 소문자로 변경해서 등록한다.

### 상품 수정

리다이렉트
- 스프링은 `redirect:/...` 으로 편리하게 리다이렉트를 지원한다.
- `"redirect:/basic/items/{itemId}"
  - 컨트롤러에 매핑된 `@PathVariable`의 값은 `redirect`에도 사용 할 수 있다.

### PRG Post/Redirect/Get
Post 요청으로 수정이 일어나면 새로고침을 할때마다 다시 Post 요청이 일어나서, PRG 방식을 사용해야 한다.  
웹  브라우저의 새로 고침은 마지막에 서버에 전송한 데이터를 다시 전송하기 때문이다. 그래서 리다이렉트를 호출하여 GET 으로 조회하도록 해야한다.

주의
- `"redirect:/basic/items/" + item.getId()` redirect 에서 `+item.getId()` 처럼 URL에 변수를 더해서 사용하는 것은
URL 인코딩이 안되기 때문에 위험하다! -> `RedirectAttributes`를 사용하자

### RedirectAttributes

`RedirectAttributes` 를 사용하면 URL 인코딩도 해주고, `pathVarible`, 쿼리 파라미터 까지 처리해준다.
- redirect:/basic/items/{itemId}
  - pathVariable 바인딩 : `{itemId}`
  - 나머지는 쿼리 파라미터로 처리 : `?status=true`
  
뷰 템플릿 메시지 추가
- `th:if`: 해당 조건이 참이면 실행
- `${param.status}`: 타임리프에서 쿼리 파라미터를 편리하게 조회하는 기능
  - 원래는 컨트롤러에서 모델에 직접 담고 값을 꺼내야 한다. 그런데 쿼리파라미터는 자주 사용해서 타임리프에서 직접 지원한다.
