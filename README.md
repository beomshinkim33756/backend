# 블로그 검색 서비스 API
## 목차
- [개발 환경](#개발-환경)
- [빌드 및 실행하기](#빌드-및-실행하기)
- [기능 요구사항](#기능-요구사항)

---

## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA Ultimate

- Server
    - Java11
    - Spring Boot 2.6.2
    - Spring Cache
    - Spring Boot Validation
    - JPA
    - H2
    - Gradle
    - Junit5
    - commons-lang3 (문자 검사 라이브러리)
    - caffeine cache (캐시 매니저)


## 빌드 및 실행하기
### 터미널 환경
- Git, Java 는 설치되어 있다고 가정한다.

```
$ git https://github.com/beomshinkim33756/backend.git
$ cd backend
$ ./gradlew clean build
$ java -jar api/build/libs/kakaobank.jar
```

- 접속 Base URI: `http://localhost:19001`

## 기능 요구사항
### 필수사항
- 키워드를 통해 블로그를 검색할 수 있어야 합니다.
- 검색 결과에서 Sorting(정확도순, 최신순) 기능을 지원해야 합니다.
- 검색 결과는 Pagination 형태로 제공해야 합니다.
- 검색 소스는 카카오 API의 키워드로 블로그 검색(https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog)을 활용합니다.
- 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려해야 합니다.


- Request

```
http://localhost:19001/api/v1/find/blog
```

```
GET /api/v1/find/blog HTTP/1.1
```

- Parameter
```
keyword : "키워드"
page : "페이지 번호"
size : "게시글 개수"
sort : "sorting(0: ACCURACY, 1: RECENCY)"
```

- Response

```json
{
  "totalCount": 45227,
  "totalPage": 50,
  "page": 1,
  "size": 3,
  "isEnd": false,
  "enterprise": "KAKAO",
  "documents": [
    {
      "title": "[항해<b>99</b>] 주특기 심화 주차 <b>키워드</b> 과제",
      "contents": "🔐 스프링 시큐리티를 사용해 회원관리하는 방법을 순서도를 통해 정리해 보세요. 회원가입, 로그인, 로그인 유지 방법으로 나눠서 정리 4개의 모듈 (Client, Server, Session, DB) 로 나누어 정리 🔐 단위 테스트 코드 작성 시 장/단점을 정리해 보세요. 단위 테스트(Unit Test) 하나의 모듈을 기준으로 독립적으로 진행...",
      "url": "http://kyuu-ng.tistory.com/75",
      "blogname": "Dev-Kyuu",
      "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/2DAkgnYJGUO",
      "datetime": "2022-12-15 15:55:37"
    },
    {
      "title": "2023 시나공_<b>키워드</b> 찾기 문제 <b>99</b>",
      "contents": "개체와 메소드로 옳은 것은? - Docmd.OpenReport ​ 98, 다음 중 Visual Basic에서 Microsoft Access 매크로 함수를 실행할 수 있는 액세스 개체는 무엇인가? - Docmd 개체 ​ <b>99</b>. RecordSet 개체 속성 중 현재 레코드 위치가 RecordSet 개체의 첫 번째 레코드 앞에 온다는 것을 나타내는 값을 반환하는 속성은 무엇인가? - BOF",
      "url": "https://blog.naver.com/zhalghkfxld/223025625282",
      "blogname": "♥️",
      "thumbnail": "",
      "datetime": "2023-02-23 11:42:00"
    },
    {
      "title": "[항해<b>99</b>]React 심화 주차 핵심 <b>키워드</b>",
      "contents": "리덕스에서 미들웨어 청크(thunk)의 역할은 뭘까? 먼저 미들웨어는 리덕스가 지니고 있는 핵심 기능이다. Context API 또는 MobX를 사용하는 것과 차별화가 되는 부분입니다. 출처 : [React] 미들웨어 이해하기(1) : redux-thunk (tistory.com) 미들웨어는 객체 대신 함수를 생성하는 액션 생성함수를 작성할 수 있도록...",
      "url": "http://growingman.tistory.com/31",
      "blogname": "그로윙맨",
      "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/3PVM9Wl5Teg",
      "datetime": "2022-08-11 10:41:06"
    }
  ],
  "resultCode": "00000",
  "msg": "성공"
}
```

- CSV 파일 요청을 받는 컨트롤러 구현
    - `/upload` URL 요청을 처리하는 `CsvFileUploadController` 클래스를 만듦


### 2. 인기 검색어 목록
- 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
- 검색어 별로 검색된 횟수도 함께 표기해 주세요.

- Request

```
http://localhost:19001/api/v1/find/rank
```

```
GET /api/v1/find/rank HTTP/1.1
```

- Response

```json
{
  "ranks": [
    {
      "keyword": "키워드99",
      "count": 7
    },
    {
      "keyword": "키워드66",
      "count": 4
    },
    {
      "keyword": "키워드",
      "count": 4
    },
    {
      "keyword": "키워드3",
      "count": 3
    },
    {
      "keyword": "키워드34",
      "count": 2
    }
  ],
  "resultCode": "00000",
  "msg": "성공"
}
```

## 우대사항
- 프로젝트 구성 추가 요건
  - 멀티 모듈 구성 및 모듈간 의존성 제약

- Back-end 추가 요건
  - 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 둔 구현
  - 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 구현 (예시. 키워드 별로 검색된 횟수의 정확도)
  - 카카오 블로그 검색 API에 장애가 발생한 경우, 네이버 블로그 검색 API를 통해 데이터 제공
        <br> * 네이버 블로그 검색 API: https://developers.naver.com/docs/serviceapi/search/blog/blog.md
