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


- 필수 기능
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
    - 검색 소스는 카카오 API 사용 (KakaoBlogApiClient) / 캐시 처리(60초)
    - 카카오 API 실패 시 검색 소스 네이버 API 사용 (NaverBlogApiClient)
    - 이외 API 확장성 (ApiService.findBlogList) 교체 / (ApiServiceImpl.findBlogList) 변경
    - 키워드 입력 count 처리 (incrementCount) / 트랜잭션 lock 통해 race condition 예방 / async로 비동기 처리
    - KeywordTb entity 생성 / KeywordTbRepository jpa 생성
    - controller unit test (ApiControllerTest) / kakao, naver api service test(KaKaoBlogServiceTest/NaverBlogServiceTest)
    - 카카오, 네이버 API 키정보 yml 세팅

### 2. 인기 검색어 목록

- 필수 기능
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
  - 캐시 처리 (1초)

### 블로그 URL 검색


- 추가 기능
- URL

```
http://localhost:8080/api/v1/find/url/blog
```

- URI
```
GET /api/v1/find/url/blog HTTP/1.1
```

- Parameter

| 파라미터명   | 필수  | 타입     | 설명                            |
|---------|-----|--------|-------------------------------|
| keyword | ○   | string | 키워드                           |
| page    | ○   | string | 페이지 번호                        |
| size    | ○   | string | 게시글 개수                        |
| sort    | ○   | string | 정렬방식(0: ACCURACY, 1: RECENCY) |
| url     | ○   | string | 조회 블로그 URL                    |

```
?keyword=3&sort=1&page=1&size=3&url=https://brunch.co.kr
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
| documents.thumbnail | ○   | string  | 블로그 미리보기                   |
| documents.datetime  | ○   | string  | 블로그 작성시간                   |
| resultCode          | ○   | string  | 응답코드                       |
| msg                 | ○   | string  | 응답메세지                      |

```json
{
    "totalCount": 131,
    "totalPage": 44,
    "page": 1,
    "size": 3,
    "isEnd": false,
    "enterprise": "KAKAO",
    "documents": [
        {
            "title": "[Swift] Swift의 sort() 메서드에 대하여",
            "contents": "크기가 <b>3</b>일 경우 |Z| &gt; |X| + |Y| 스택의 크기가 2일 경우 |Y| &gt; |X| 만약 조건을 만족하지 않으면, Y를 X와 Z중 작은쪽과 합침 위 조건을 만족하거나, stack의 길이가 1이 되면 merge를 종료하고 다음 run을 구함 시간복잡도 timsort는 배열이 무작위가 아니라는 점에서 정렬을 최적화한 좋은 예로, 최선 O(N), 최악 O...",
            "url": "http://didu-story.tistory.com/430",
            "blogname": "potato's devlog",
            "thumbnail": "https://search4.kakaocdn.net/argon/130x130_85_c/BXhbpaeLuJW",
            "datetime": "2023-03-10 09:36:52"
        },
        {
            "title": "[매일학습일지_44] Chapter 23. 에어비엔비 서비스기획서 Case Study",
            "contents": "#8주<b>3</b>일차 ​ 1. 커머스 서비스에 대한 이해 ​ 🔸커머스 서비스의 종류 출처: https://blog.pumpkin-raccoon.com/35 - 크게 종합몰, 오픈마켓, 소셜커머스의 세 가지...257693 출처: <b>https://brunch.co.kr</b>/@thesmc/41 종합몰의 구조 / 오픈마켓의 구조 결제 관련 용어와 결제 상태 설명 ​ 2. 서비스 기획서 템플릿 🔸야놀자...",
            "url": "https://blog.naver.com/eunjincho_ca/223023978916",
            "blogname": "Study Ground",
            "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/LSrbXr8Vy6f",
            "datetime": "2023-02-22 03:16:00"
        },
        {
            "title": "[공지] 마음엮는 간호사 소개",
            "contents": "정리하고 후배 간호사들에게 공유하기도 합니다. 저는 지금. 출처: <b>https://brunch.co.kr</b> 운동을 합니다. 이브닝 전과 쉬는 날 플라잉 요가를 하는데 이것 때문...요가도 합니다. 요가는 마음수련이지요. 올해 마라톤 도전을 해보려고요. <b>3</b>월에 첫 마라톤 도전!!! 저 사진 정도는 뭐 ㅋㅋㅋㅋ기본입니다..​​ ​ 출처:Pexels...",
            "url": "https://blog.naver.com/zoo8349/223005839574",
            "blogname": "마음엮는 간호사",
            "thumbnail": "https://search4.kakaocdn.net/argon/130x130_85_c/5vUmRBdz7Ta",
            "datetime": "2023-02-05 06:13:00"
        }
    ],
    "resultCode": "00000",
    "msg": "성공"
}
```

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
  - 트랜잭션 lock 추가
  - 장애 대응 API 추가

## 코드정리

| 코드명                | 코드값   | 메세지          | 상태                    |
|--------------------|-------|--------------|-----------------------|
| SUCCESS            | 00000 | 성공           | OK                    |
| SYSTEM_ERROR       | 19999 | 시스템 에러       | INTERNAL_SERVER_ERROR |
| PARAM_MANIPULATION | 10001 | 파라미터 변조 에러   | BAD_REQUEST           |
| FAIL_BLOG_LOADING  | 10002 | 블로그 로딩 실패    | OK                    |
| NOT_EXIST_RANK     | 10003 | 인기 검색어 조회 실패 | OK                    |
| PARAMETER_NULL     | 10004 | 파라미터 NULL 에러 | INTERNAL_SERVER_ERROR |
