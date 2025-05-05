# Futsal Cloud Reservation Scheduler

기존 로컬 예약 시스템을 AWS 환경에 맞추어 개발하였습니다.  
[로컬 프로젝트 GitHub 링크](https://github.com/leedaham/futsal-reservation-view)


**Futsal Cloud Reservation Scheduler**는 AWS Lambda를 이용하여 풋살장 예약을 자동화하는 시스템입니다.  이 시스템은 **AWS Lambda**와 **AWS EventBridge**를 통해 예약 절차를 자동으로 처리하며, 예약 시도가 필요할 때마다 주기적으로 Lambda 함수가 실행됩니다. 또한 **AWS CloudWatch**를 통해 Lambda 함수 실행 로그를 확인할 수 있습니다.


---

## 주요 특징

- **AWS Lambda 활용한 서버리스 예약 자동화**
    - AWS Lambda에서 예약 작업을 실행하고, 예약 진행 상황을 관리
    - 예약 과정의 세션 ID 및 쿠키 기반 인증을 서버리스 환경에서 처리

- **자동화된 예약 작업**
    - 예약 시도를 위한 브라우저와 같은 세션 관리 자동화
    - AWS EventBridge를 사용하여 예약 시간에 맞춰 Lambda 함수 실행

- **클라우드 기반의 스케줄링**
    - AWS EventBridge와 Lambda를 활용한 클라우드 환경에서의 예약 자동화

- **CloudWatch Logs 활용**
    - AWS CloudWatch를 통해 Lambda 함수의 실행 로그를 실시간으로 모니터링
    - 예약 작업 성공 여부 및 오류를 CloudWatch에서 확인 가능

---

## 기술 스택

- **AWS Lambda**: 서버리스 컴퓨팅 서비스
- **AWS EventBridge**: 예약 작업의 스케줄링 및 트리거링
- **AWS CloudWatch Logs**: Lambda 함수 실행 로그 모니터링
- **Java 17+**: AWS Lambda에서 실행될 Java 기반 코드
- **Gradle**: 빌드 시스템
- **HTTP 요청 라이브러리**: 세션 ID 및 쿠키를 관리하는 로직 구현

---

### 확인 사항
- 예약 정보에 사용되는 **`enum` 파일** 중 일부는 보안상의 이유로 git 에서 삭제되었습니다.

---