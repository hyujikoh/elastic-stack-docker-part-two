# ShortUrl 서비스

## 개요

ShortUrl 서비스는 긴 URL을 짧은 URL로 변환해주는 간단한 웹 서비스입니다. 사용자가 제공한 긴 URL에 대해 단축된 URL을 생성하고, 생성된 단축 URL을 통해 원본 URL로 리다이렉트할 수 있는 기능을 제공합니다. 또한, 원본 URL이 단축 요청 및 저장된 횟수를 조회하는 기능도 포함되어 있습니다.

## 기능

- **URL 단축**: 사용자가 입력한 긴 URL을 짧은 URL로 변환합니다.
- **URL 리다이렉션**: 단축된 URL을 통해 원본 긴 URL로 자동 리다이렉션합니다.
- **URL 요청 및 저장 횟수 조회**: 원본 URL이 단축 요청 및 저장된 횟수를 조회합니다.

## 사용 방법

### 1. URL 단축

- **Endpoint**: `/url/shortUrl`
- **Method**: `GET`
- **Parameter**: `url` (단축하고자 하는 원본 URL)
- **Response**: 단축된 URL 문자열

### 2. URL 리다이렉션

- **Endpoint**: `/url/keyUrl`
- **Method**: `GET`
- **Parameter**: `url` (단축된 URL의 key)
- **Action**: 입력된 key에 해당하는 원본 URL로 리다이렉션

### 3. URL 요청 및 저장 횟수 조회

- **Endpoint**: `/url/count`
- **Method**: `GET`
- **Parameter**: `url` (원본 URL)
- **Response**: 해당 URL이 단축 요청 및 저장된 횟수(Long)

## 개발 환경

- **Framework**: Spring Boot
- **Language**: Java
- **Database**: inMemory store

## 설치 및 실행 방법

1. 소스 코드를 로컬 시스템에 클론합니다.
    ```
    git clone [repository URL]
    ```
2. 프로젝트 디렉토리로 이동합니다.
    ```
    cd ShortUrl
    ```
3. 프로젝트를 빌드하고 실행합니다. (Maven을 예로 듭니다.)
    ```
    mvn clean install
    mvn spring-boot:run
    ```

## 사용 예시

1. **URL 단축하기**

   요청:
    ```
    GET /url/shortUrl?url=https://example.com/very/long/url
    ```
   응답:
    ```
    http://localhost:8080/shortUrl?key=abcd1234
    ```

2. **URL 리다이렉션**

   브라우저에서 다음 URL로 접근:
    ```
    http://localhost:8080/url/keyUrl?url=abcd1234
    ```

   이동될 페이지:
    ```
    https://example.com/very/long/url
    ```

3. **URL 요청 및 저장 횟수 조회**

   요청:
    ```
    GET /url/count?url=https://example.com/very/long/url
    ```
   응답:
    ```
    5
    ```



이런 자료를 참고했어요.
[1] 네이버 개발자 센터 - 단축 URL 호출 제한 수 문의 (https://developers.naver.com/forum/posts/30939)

[2] velog - 단축 Url 관리 애플리케이션 만들어보기 1 (https://velog.io/@tilsong/%EB%8B%A8%EC%B6%95-Url-%EC%83%9D%EC%84%B1-%EB%B0%8F-%EA%B4%80%EB%A6%AC-%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98-%EB%A7%8C%EB%93%A4%EC%96%B4%EB%B3%B4%EA%B8%B0-1)

[3] GitHub - Lob-dev/Short-Term: URL을 특정 문자열로 단축하여 제공 ... (https://github.com/Lob-dev/Short-Term)

[4] 티스토리 - Tiny URL 설계 레퍼런스 - swdream - 티스토리 (https://swengineer7.tistory.com/70)
