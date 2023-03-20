# 블로그 검색 서비스 API
## 목차
- [개발 환경](#개발-환경)
- [빌드 및 실행하기](#빌드-및-실행하기)
- [기능 요구사항](#기능-요구사항)

---

- JAR 다운 링크 https://github.com/beomshinkim33756/backend/blob/main/kakaobank.jar

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

- 접속 Base URI: `http://localhost:8080`

## 기능 요구사항

### 블로그 검색

- URL

```
http://localhost:8080/api/v1/find/blog
```

- URI
```
GET /api/v1/find/blog HTTP/1.1
```

- Parameter

| 파라미터명   | 필수  | 타입     | 설명                            |
|---------|-----|--------|-------------------------------|
| keyword | ○   | string | 키워드                           |
| page    | ○   | string | 페이지 번호                        |
| size    | ○   | string | 게시글 개수                        |
| sort    | ○   | string | 정렬방식(0: ACCURACY, 1: RECENCY) |

```
?sort=0&page=1&size=3&keyword=키워드99
```

- Response

| 파라미터명               | 필수  | 타입      | 설명                         |
|---------------------|-----|---------|----------------------------|
| totalCount          | ○   | integer | 게시글총개수                     |
| totalPage           | ○   | integer | 총페이지개수(max 50)             |
| page                | ○   | integer | 현재 페이지                     |
| size                | ○   | integer | 현재 게시글 개수                  |
| isEnd               | ○   | boolean | 마지막 페이지 플래그 (true:마지막 페이지) |
| enterprise          | ○   | string  | 게시글 조회 타입 (KAKAO, NAVER)   |
| documents           | ○   | array   | 블로그 문서                     |
| documents.title     | ○   | string  | 블로그 타이틀                    |
| documents.contents  | ○   | string  | 블로그 내용                     |
| documents.url       | ○   | string  | 블로그 url                    |
| documents.blogname  | ○   | string  | 블로그 이름                     |
| documents.thumbnail | X   | string  | 블로그 미리보기                   |
| documents.datetime  | ○   | string  | 블로그 작성시간                   |
| resultCode          | ○   | string  | 응답코드                       |
| msg                 | ○   | string  | 응답메세지                      |

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
    }
  ],
  "resultCode": "00000",
  "msg": "성공"
}
```
- API 컨트롤러  GET `/api/v1/find/blog` 요청을 통해 이용 
    - 파라미터: 키워드, 페이지번호 (최대 50), 게시글 개수 (최대 50), 정렬 방식(0: ACCURACY, 1: RECENCY)
    - 파라미터 변조 체크
    - 응답 JSON: 게시글 총개수, 페이지 총개수, 현재페이지, 게시글 개수, 마지막 플래그, 게시글 조회 타입, 블로그 리스트, 응답코드/메세지
    - 게시글 총개수, 페이지 총개수, 현재페이지, 게시글 개수, 마지막 플래그 통한 Pagination 기능
    - 검색 소스는 카카오 API 사용 (KakaoBlogApiClient) / 캐시 적용 (트래픽 대응)
    - 카카오 API 실패 시 검색 소스 네이버 API 사용 (NaverBlogApiClient)
    - 이외 API 확장성 (ApiService.findBlogList) 교체 / (ApiServiceImpl.findBlogList) 변경
    - 키워드 입력 count 처리 (incrementCount) / synchronized 통해 race condition 예방
    - KeywordTb entity 생성 / KeywordTbRepository jpa 생성
    - controller unit test (ApiControllerTest) / kakao, naver api service test(KaKaoBlogServiceTest/NaverBlogServiceTest)
    - 카카오, 네이버 API 키정보 yml 세팅

### 2. 인기 검색어 목록

- URL
```
http://localhost:8080/api/v1/find/rank
```

- URI
```
GET /api/v1/find/rank HTTP/1.1
```

- Response

| 파라미터명         | 필수  | 타입     | 설명         |
|---------------|-----|--------|------------|
| ranks         | ○   | array  | 인기 검색어 리스트 |
| ranks.keyword | ○   | string | 인기 검색어 키워드 |
| ranks.count   | ○   | string | 인기 검색 횟수   |
| ranks.order   | ○   | string | 인기 검새어 순위  |
| resultCode    | ○   | string | 응답코드       |
| msg           | ○   | string | 응답메세지      |

```json
{
  "ranks": [
    {
      "keyword": "5f5935c61a",
      "count": 1249,
      "order": 1
    },
    {
      "keyword": "9dd013e77d",
      "count": 1237,
      "order": 2
    },
    {
      "keyword": "d441411949",
      "count": 1019,
      "order": 3
    },
    {
      "keyword": "3efb52213f",
      "count": 1004,
      "order": 4
    },
    {
      "keyword": "7b22aaf1b9",
      "count": 988,
      "order": 5
    },
    {
      "keyword": "16a0c60f61",
      "count": 983,
      "order": 6
    },
    {
      "keyword": "2dd46530dc",
      "count": 983,
      "order": 7
    },
    {
      "keyword": "e9c2d1a6fa",
      "count": 982,
      "order": 8
    },
    {
      "keyword": "ea7dbf02b9",
      "count": 969,
      "order": 9
    },
    {
      "keyword": "803ff424ab",
      "count": 586,
      "order": 10
    }
  ]
}
```

- API 컨트롤러  GET `/api/v1/find/rank` 요청을 통해 이용
  - jpa를 통한(findTop10ByOrderByCountDesc) 인기 검색 순위 10개 조회
  - 응답 JSON: 랭크 리스트, 응답 코드/메세지
  - 키워드, 키워드 검색 횟수, 키워드 순위 응답


## 코드레벨 평가항목
- spring boot 전반적인 기능 활용
- 유닛 테스트 작성
- 글로벌 exception, 비니지니스 exception 처리
- 불필요한(사용되지 않는) 코드 미존재


## 멀티 모듈 구성
  - api, core, external-api
  - api: REST API통신을 위한 모듈
  - core: 공통 모듈
  - external-api: 외부 API 호출 모듈

## Back-end 추가 요건
  - 캐시 기능 추가
  - synchronized 기능 추가
  - 장애 대응 API 추가

## 코드정리

| 코드명                | 코드값   | 메세지          | 상태                    |
|--------------------|-------|--------------|-----------------------|
| SUCCESS            | 00000 | 성공           | OK                    |
| SYSTEM_ERROR       | 19999 | 시스템 에러       | INTERNAL_SERVER_ERROR |
| PARAM_MANIPULATION | 10001 | 파라미터 변조 에러   | BAD_REQUEST           |
| FAIL_BLOG_LOADING  | 10002 | 블로그 로딩 실패    | OK                    |
| NOT_EXIST_RANK     | 10003 | 인기 검색어 조회 실패 | OK                    |