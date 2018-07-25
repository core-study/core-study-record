**키워드 정리.**

# 스프링 5.0 마이크로서비스 2-e 
## 01. 쉽게 알아보는 마이크로 서비스
- Monolithic Architecture : 전통적인 일체형 아키텍처
- Service Oriented Architecture (SOA) : 서비스 지향 아키텍쳐
- 인프라스트럭처 : 도커 등의 최종 사용자에게 기술을 제공하는 데 필요한 모든 기본 요소를 포함한 전체 스택를 나타냅니다
- PaaS (Platform as a Service) : 개발에 필요한 인프라 뿐 아니라 운영체제 프레임워크 등을 서비스 형태로 제공 (AWS 등)
- iPaaS ( Integration Platform as a Service) : PaaS에 더해 시스템 통합에 필요한 환경과 기능을 제공한 것. (예시가 필요)
- 마이크로 서비스 : 분할 정복 기법을 이용해서 일체형 애플리케이션을 단일 기능을 수행하는 더 작은 크기의 원자화된 단위로 분할.
- 육각형 아키텍처 (포트와 어댑터 패턴) : 비즈니스 기능을 캡슐화 해서 세상과의 격리를 추구. (벌집처럼) 포트와 어댑터를 통해 외부와 연결
	- 마이크로서비스아키텍처에서는 포트 역할을 하는 부분이 REST API일테고, 어댑터가 Client
- 데브옵스(DevOps) : 더 나은 효율성을 추구하기 위해 전통적인 IT 개발과 운영 사이의 간격을 줄이는 일종의 IT역할 재배치
	- 즉 개발자가 운영까지 관여하는 것을 이야기하는 듯
- 벌집 구조 : 각각의 방 내부에 있는 내용물은 추상화 되어 외부에서 볼 수 없고, 전체 벌집 구조에 영향을 미치지 않으면서 확장 가능. 즉 마이크로서비스 아키텍쳐의 추상화 개념이라고 보면 된다.
- SOLID 디자인 패턴 : 객체 지향 프로그래밍 및 설계의 다섯 가지 기본 원칙
	- S {SRP 단일 책임 원칙 (Single responsibility principle)} : 한 클래스는 하나의 책임만 가져야 한다.
	- O {OCP 개방-폐쇄 원칙 (Open/closed principle)} : “소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.”
	- L {LSP 리스코프 치환 원칙 (Liskov substitution principle)} : “프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 한다.” 계약에 의한 설계를 참고하라.
	- I {ISP 인터페이스 분리 원칙 (Interface segregation principle)} : “특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.”
	- D {DIP 의존관계 역전 원칙 (Dependency inversion principle)} : 프로그래머는 “추상화에 의존해야지, 구체화에 의존하면 안된다.” 의존성 주입은 이 원칙을 따르는 방법 중 하나다.
- SOA 특징
	- 서비스 계약 : 일반적으로 JSON(혹은 WADL, Swagger, RAML)/REST 을 이용해서 커뮤니케이션
	- 느슨한 결합 : 서로 느슨하게 연결되어 있고, 이벤트로 입력을 받고, 이벤트로 응답한다.
	- 서비스 추상화 : 모든 라이브러리와 제반 환경 전체를 추상화한다.
	- 서비스 재사용 : 마이크로서비스는 덩어리째 재사용 가능 (모바일, 데스크톱, 또 다른 마이크로서비스 아예 다른 시스템에서도 접근 가능)
	- 무상태 : 말 그대로 무상태
	- 탐색 가능 : 자신의 존재를 스스로 드러내서 알리고, 탐색에 의해 찾아짐. (스웨거 등을 통해 api 리스트가 정리된 것 등)
	- 서비스 호환성 : 표준 프로토콜과 메시지 교환 표준 준수 (HTTP, REST/JSON, 메시징 (Message Queue 등))
	- 서비스 조립성 : 조립이 가능, 서비스 오케스트레이션이나 서비스 연출을 통해 확보 가능
		- Service Orchestration : 메시지 교환을 담당하는 어떤 주체에 의해 메시지를 주고받는 방식
		- Service Choreography : 서비스간의 메시지 교환에 있어서 각 서비스들의 전역적인 수준에서 스스로 메시지를 주고받음
- 붕괴 저항성 : 스트레스, 쇼크, 실수, 잘못, 공격 등에 대해서 허용성, 내구성, 회복력을 높이는것 (Antifragility is a property of systems that increase in capability, resilience, or robustness as a result of stressors, shocks, volatility, noise, mistakes, faults, attacks, or failures)
- 빨리 실패하기 : 장애를 견딜 수 있고, 회복력이 좋은 시스템 구축에 이용 (평균 무고장 시간을 높이는 것이 아닌 평균 복구 시간을 높이는 것에 중점을 둠)
- 자체 치유 : 시스템이 장애로부터 학습을 하고 스스로를 장애에 적응시킨다는 개념 (AWS auto scale out이 이에 해당되는 듯)
- 스케일 큐브 : 애플리케이션을 확장하는 세가지 주요 접근 방식 (아래 이미지의 y축과 z축이 내가 아는 방향과 다른데??)
	- x축 : 애플리케이션 복제
	- y축 : 기능 분리
	- z축 : 데이터 파티셔닝과 샤딩
![스케일 큐브](http://microservices.io/i/DecomposingApplications.021.jpg "스케일 큐브")
- 카나리아 출시 : 옛날 갱도에 가스로 사람이 죽나 안죽나 판별을 위해 탄광 인부들이 카나리아를 먼저 보냈다함 (카나리아가 죽으면 사람도 죽는 것) 이처럼 먼저 내보내서 문제가 있나 없나 눈치보는 배포를 의미함.


## 02. 마이크로서비스 관련 아키텍처 스타일 및 사례
- 서비스 지향 아키텍처(Service Oriented Architecture) : 서비스 지향성을 지원하는 아키텍쳐 스타일
- 서비스 지향성 : 서비스 그 자체, 서비스 기반의 개발, 서비스의 결과 관점에서 생각하는 방식을 의미
- ESB(Enterprise Service Bus) 정의
	- 서비스들간의 상호연계를 위한 논리적인 Layer로 다양한 인터페이스를 제공하며 이를 통해 서비스 조합, 연계, 상호운용(이벤트, 메세징)을 제공하는 인프라
	- Web Service, Intelligent Message Routing, Transformation 기술을 바탕으로 내/외부 정보시스템을 통합, 관리, 사용 지원하는 SOA지원 미들웨어 플랫폼
	- 비즈니스 단위들의 통합을 수월하게 하면서 이종의 플랫폼과 환경들을 잇는 가교 역할을 하는 인프라	
- 12요소 애플리케이션 (클라우드 환경에 적합한 애플리케이션의 특성을 12가지) 
	- 단일코드베이스 : 버전 관리되는 하나의 코드베이스와 다양한 배포
	- 종속성 : 명시적으로 선언되고 분리된 종속성
	- 설정 : 환경(environment)에 저장된 설정
	- 백엔드 서비스 : 백엔드 서비스를 연결된 리소스로 취급
	- 빌드, 릴리즈, 실행 : 철저하게 분리된 빌드와 실행 단계
	- 프로세스 : 애플리케이션을 하나 혹은 여러개의 무상태(stateless) 프로세스로 실행
	- 포트 바인딩 : 포트 바인딩을 사용해서 서비스를 공개함
	- 동시성(Concurrency) : 프로세스 모델을 사용한 확장	
	- 폐기 가능(Disposability) : 빠른 시작과 그레이스풀 셧다운(graceful shutdown)을 통한 안정성 극대화
	- dev/prod 일치 : development, staging, production 환경을 최대한 비슷하게 유지
	- 로그 : 로그를 이벤트 스트림으로 취급
	- Admin 프로세스 : admin/maintenance 작업을 일회성 프로세스로 실행
- 서버리스 컴퓨팅 : 단순히 서버가 없다는것 뿐만 아니라 서비스 단위의프로그램 코드만을 개발하고 배포에 집중한다는 의미도 포함되어 있다.
- 람다 아키텍처 : 실시간 분석을 지원하는 빅데이터 아키텍쳐
![람다 아키텍처](https://t1.daumcdn.net/cfile/tistory/247BBC4B58CFFEB216 "람다 아키텍처")
	- 배치 계층 : raw 데이터가 저장되어 있고, batch 처리하여 배치 뷰 생성 (HDFS, Amazon S3)
	- 서빙 계층 : batch로 분석된 데이터가 저장되어 있고 batch 외에는 쓰기가 안됨 (HBase, Amazon RedShift)
	- 스피드 계층 : 실시간 데이터를 집계 (Storm, Spark, Redis)
- 리액티브 시스템 : 오늘날의 애플리케이션에 대한 높은 요구사항을 충족시키는 현대적인 시스템을 만들기 위한 구조적인 설계 원칙의 집합이다. (메시지)
- 리액티브 프로그래밍 : 비동기 데이터 흐름에 기반을 둔 프로그래밍 패러다임이다. (이벤트)
- 리액티브 시스템 vs 리액티브 프로그래밍 (http://blog.lespinside.com/reactive-programming-versus-reactive-systems/)
- 폴리글랏(polyglot) : 여러가지 언어로 개발하는 것
- 모놀리틱 아키텍처 (Monolithic Architecture) : 마이크로서비스 아키텍처의 반대 개념 (일체형 아키텍처)


## 03. 스프링 부트로 만드는 마이크로서비스
- security + oauth관련 예제는 spring-boot-starter-parent를 최신(2.0.3.RELEASE) 으로 할 경우 oauth에 대한 dependency가 없어서 동작 안한다
	- 2.0.0.M1 에서는 동작 성공
- maven BOM(bil of materials 패턴)은 말 그대로 연관된 모듈들 버전을 맞춰서 pom.xml파일로 두는 것을 의미한다.
	- 자세한 설명은 (https://medium.com/java-user-group-malta/maven-s-bill-of-materials-bom-b430ede60599) 참고
- 레거시 방식과 스프링 부트와의 가장 큰 특징은 자기 완비적이라는 점. (sts 없이도 tomcat 없이도 실행 가능)
- 회귀 테스트 : 수정(Modification)이 기대하지 않은 결과를 발생시키지 않는다는 것을 증명하기 위한 시스템이나 컴포넌트에 대한 선택적 재테스트.
- HATEOAS : 네이게이션 링크가 응답에 포함돼 제공되는 REST 서비스 패턴
- HAL : JSON을 바탕으로 자원 사이의 하이퍼링크를 표현하는 관례
- 리액티브 스트림 : 논블로킹(non blocking), 역압(back pressure)으로 비동기 스트림 처리 표준을 제공하기 위한 계획
	- http://atin.tistory.com/574 참고
	- back pressure : 유동유체의 흐름이 방해를 받아 유체의 흐름방향과 반대방향으로 유체에 가해지는 압려을 말하는 것 (즉 뒷단에서 데이터를 푸쉬해주는 것을 의미)
- SSH를 활용한 모니터링 기능은 스프링 부트 2.0에서 제거됨 (https://github.com/spring-projects/spring-boot/issues/7044)
- docker를 통한 rabbit mq 설치
	- rabbit mq 실행
		- 초반 설치 이후 실행 : sudo docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 --restart=unless-stopped -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest rabbitmq:management
		- 2번째부터 : sudo docker start rabbitmq
	- docker process 보기 : docker ps (혹은 docker ps -a)
	- docker process 정지 : docker stop rabbitmq (혹은 컨테이너 name (ps 때려보고 나오는 names))
- JavaMailSender + fakeSMTP로 SMTP 테스트를 할 수 있으나 구현하지 않음 (귀찮아서)
