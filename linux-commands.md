# 자주 사용하는 

## 파일

### 파일 상단 일부 출력

```bash
head -출력할라인수 파일명
```

### 파일 하단 일부 출력

```bash
tail -출력할라인수 파일명
tail -F 파일명 # 파일 업데이트 사항 계속 출력. 로그 출력시 이용.
```

### 파일 검색

```bash
find 경로 -name 파일명
```

**옵션**

- `-name`: 검색파일명
- `-exec`: 해당하는 파일에 명령 적용
    - e.g. 현재 경로에서 모든 로그 파일 찾아 제거: `find ./ -name "*.log" -exec rm {} \;`

## 파일 조회

```bash
more 파일명 # 한 화면에서 스크롤 없이 조회 가능
less 파일명 # 한 화면에서 스크롤 없이 조회. 숫자 입력시 입력한 만큼 계속 이동 가능
```

### 파일 모드 변경

```bash
sudo chmod 권한 파일명
```

권한은 파일소유자(User)/그룹(Group)/그 외 사용자(Others) 순으로 지정된다.

각 권한별 값은 다음과 같다.

- 읽기: 4
- 쓰기: 2
- 실행: 1

e.g. `sudo chmod 777`: 모든 사용자에게 읽기, 쓰기, 실행 권한 허용

e.g. `sudo chmod 400`: 파일 소유자에게 읽기 허용

e.g. `sudo chmod 600`: 파일 소유자에게 읽기 + 쓰기 허용

## 환경변수

### 환경변수 적용

```bash
export 환경변수명=값
```

영구 적용시 아래 파일에 위 명령어를 입력한다.

- 전체 사용자의 환경변수 변경: `/etc/bash.bashrc` 수정
- 한 사용자의 환경변수 변경: `/home/사용자명/.bashrc` 수정

### 환경변수 조회

```bash
env # 전체 조회
env | grep 환경변수명 # 특정 환경변수 조회
```

### 환경변수 제거

```bash
unset 환경변수명
```

## 네트워크

### 네트워크 커넥션 조회

```bash
netstat -ant
netstat -tulpn
```

**옵션**

- `-a` : all type of port
- `-n` : numerical address로 변환
- `-t` : TCP only
- `-u` : UDP only
- `-x` : UDP domain only
- `-l` : listening socket only
- `-p` : 소켓을 열고 있는 프로그램과 pid
- `-s` : network statistics 조회

### 열려있는 파일 조회 (파일, 소켓, 디바이스)

조회가 되지 않는 경우 sudo 권한으로 조회를 시도해 볼 수 있다.

```bash
lsof
lsof -i :포트번호 # 특정 포트를 사용중인 프로세스 조회 
```

**옵션** (대소문자 유의)

- `-U`: unix domain socket
- `-i`: internet domain socket
- `-p`: 특정 pid가 열어놓은 파일
- `-u`: userid 지정
- `-c`: 특정 커맨드가 열고 있는 파일 조회

## 프로세스

### 서버 메트릭 확인

```bash
top
```

### 프로세스 목록 조회

```bash
ps -ef
ps -ef | grep 프로세스명 # 특정 프로세스 조회
```

### 프로세스 강제 종료

특정 포트번호를 프로세스가 점유중일 때, `lsof -i 포트번호` 명령어를 통해 프로세스를 찾아 제거할 수 있다.

```bash
kill -시그널번호(또는이름) 프로세스ID
kill -l # kill 시그널 목록 출력
kill -2 프로세스ID # SIGINT 시그널을 보내 정상 종료 시도 (우아한 종료 적용시 종료작업 수행)
kill -9 프로세스ID # SIGKILL 시그널을 보내 강제 종료
```

```bash
1) SIGHUP       2) SIGINT       3) SIGQUIT      4) SIGILL       5) SIGTRAP
6) SIGABRT      7) SIGBUS       8) SIGFPE       9) SIGKILL      10) SIGUSR1
11) SIGSEGV     12) SIGUSR2     13) SIGPIPE     14) SIGALRM     15) SIGTERM
16) SIGSTKFLT   17) SIGCHLD     18) SIGCONT     19) SIGSTOP     20) SIGTSTP
21) SIGTTIN     22) SIGTTOU     23) SIGURG      24) SIGXCPU     25) SIGXFSZ
26) SIGVTALRM   27) SIGPROF     28) SIGWINCH    29) SIGIO       30) SIGPWR
31) SIGSYS      34) SIGRTMIN    35) SIGRTMIN+1  36) SIGRTMIN+2  37) SIGRTMIN+3
38) SIGRTMIN+4  39) SIGRTMIN+5  40) SIGRTMIN+6  41) SIGRTMIN+7  42) SIGRTMIN+8
43) SIGRTMIN+9  44) SIGRTMIN+10 45) SIGRTMIN+11 46) SIGRTMIN+12 47) SIGRTMIN+13
48) SIGRTMIN+14 49) SIGRTMIN+15 50) SIGRTMAX-14 51) SIGRTMAX-13 52) SIGRTMAX-12
53) SIGRTMAX-11 54) SIGRTMAX-10 55) SIGRTMAX-9  56) SIGRTMAX-8  57) SIGRTMAX-7
58) SIGRTMAX-6  59) SIGRTMAX-5  60) SIGRTMAX-4  61) SIGRTMAX-3  62) SIGRTMAX-2
63) SIGRTMAX-1  64) SIGRTMAX
```

## 메모리

### 메모리 조회

```bash
free
```

## 디스크

### 디스크 사용량 조회

```bash
df
df -h # 보기 편하게 출력
```

### 디렉토리 용량 조회 (disk usage)

```bash
du
du -hs * # 현재 디렉토리 사용량 확인
du -hsx * | sort -rf  | head -n 10 # 용량을 많이 차지하는 순으로 10개 조회
```

## 서비스

환경에 따라 sudo 명령어가 필요할 수 있다.

### 서비스 생성 경로

```bash
/etc/systemd/system
vim /etc/systemd/system/서비스명.service # 새로운 서비스 작성시
```

- e.g. 시작시 특정한 도커 컴포즈 자동 실행

```bash
[Unit]
Description=Redis Docker Application Service
Requires=docker.service
After=docker.service # 도커 서비스가 먼저 실행된 후에 실행

[Service]
Type=oneshot
RemainAfterExit=yes
WorkingDirectory=/home/ec2-user/docker # 도커 컴포즈 파일 경로
ExecStart=/usr/local/bin/docker-compose -f ./docker-compose-prod.yml up -d
ExecStop=/usr/local/bin/docker-compose -f ./docker-compose-prod.yml down
TimeoutStartSec=0

[Install]
WantedBy=multi-user.target
```

- [서비스 작성하기](https://www.shubhamdipt.com/blog/how-to-create-a-systemd-service-in-linux/)

### 서비스 목록 조회

grep 명령어와 조합하여 목록을 필터링 할 수 있다.

```bash
systemctl list-unit-files
```

### 부팅시 실패한 서비스 목록

```bash
systemctl --failed
```

### 서비스 상태 조회

```bash
systemctl status 서비스명
```

### 서비스 실행

```bash
systemctl start 서비스명
```

### 서비스 재시작

```bash
systemctl restart 서비스명
```

### 서비스 중단

```bash
systemctl stop 서비스명
```

### 부팅시 자동 시작되도록 등록

```bash
systemctl enable 서비스명
```

## 기타

### 결과 필터링

```bash
명령어 | grep 키워드
```

**옵션**

- `-c 키워드`**: 키워드를 포함한 라인 수 카운트**
- `-i`: 대소문자 구분 하지 않음
- 

### 콘솔 출력 파일로 저장

```bash
명령어 > 파일명
```

### 명령어 위치 검색

```bash
which 명령어
which java # 자바 명령어 경로 조회
which docker # 도커 명령어 경로 조회
```

### 서버 시간 조회

```bash
date
```

### 사용한 명령어 목록 조회

```bash
history
history | grep docker # 도커 이미지 어떻게 실행했는지 기억 안날때 활용 가능
```
