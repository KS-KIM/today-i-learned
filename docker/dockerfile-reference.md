# Dockerfile

Created: December 15, 2021 9:33 AM

도커는 Dockerfile에 있는 명령어를 읽어 자동으로 이미지를 빌드할 수 있다. Dockerfile은 이미지를 만들기 위해 사용자가 작성한 명령어를 모아놓은 텍스트 문서다. `docker build` 명령어를 통해 Dockerfile 내 작성된 명령어를 모두 수행하여 자동화된 빌드를 수행할 수 있다.

## 포맷

Dockerfile은 명령어 대소문자를 구분하지 않는다. 그러나, 컨벤션으로는 인자와 쉽게 구분하기 위해 UPPERCASE를 주로 사용한다.

Dockerfile은 반드시 상위 이미지를 지정하는 FROM 명령어로 시작해야 한다. FROM 명령어는 파서 지시문이나 주석 및 전역범위 ARG 뒤에 올 수 있다.

도커는 `#`으로 시작하는 라인이 파서 지시문이 아닌 경우 주석으로 처리한다. `#` 마커는 다른 라인에서 인자로 사용될 수 있다.

```dockerfile
# world
RUN echo 'hello #'
```

주석은 `#` 키워드가 나오기 전에 띄어쓰기를 하면 다른 명령어들과 마찬가지로 실행시 무시되지만 권장하지 않는다. 이와 다르게, 명령어에 전달되는 인자에 포함된 띄어쓰기는 무시하지 않는다.

```dockerfile
RUN echo "\
     hello\
     world"
```

## 환경 교체

환경변수는 특정 명령어에서 Dockerfile이 해석할 변수로 사용할 수 있다. 환경변수는 `$variable_name` 또는 `${variable_name}` 형태로 표현된다. `${variable_name}` 형태의 문법은 표준 bash 수정자를 지원한다.

- `${variable_name:-word}`: 변수가 설정되지 않은 경우 뒤에 지정된 값이 결과가 된다.
- `${variable_name:+word}`: 변수가 설정되면 뒤에 지정된 값이 결과가 되고, 없으면 빈 문자열이 결과가 된다.

위 예시에서 word는 문자열 뿐만 아니라 추가적인 환경변수가 될 수 있다.

환경변수는 아래 명령어에서 사용할 수 있다.

- `ADD`
- `COPY`
- `ENV`
- `EXPOSE`
- `FROM`
- `LABEL`
- `STOPSIGNAL`
- `USER`
- `VOLUME`
- `WORKDIR`
- `ONBUILD`

환경변수 대체는 전체 명령에서 각 변수에 대해 동일한 값을 사용한다.

```dockerfile
ENV abc=hello
ENV abc=bye def=$abc
ENV ghi=$abc
```

다음과 같이 주어진 경우 `def`는 `hello`, `ghi`는 `bye`를 가지게 된다.

## .dockerignore 파일

docker cli는 컨텍스트를 docker 데몬으로 보내기 전에 컨텍스트 루트 디렉터리에서 .dockerignore 파일을 찾아 일치하는 패턴의 디렉토리를 제외하도록 컨텍스트를 수정한다. 이는 불필요하거나 민감한 정보를 담고있는 파일을 제외할 떄 유용하다.

```dockerfile
# local packages
.adminbro
.github
.idea
node_modules
build
scripts

```

- `#`: 주석
- `/filename`: 모든 상위 디렉토리에 속하는 하위 디렉토리 내 filename을 무시한다. wilecard는 중첩 가능하다. (e.g. */*/)
- `filename*`: filename 디렉토리에 포함된 모든 디렉토리와 파일을 무시한다.
- `filename?`: 루트 디렉토리 내 filename 키워드를 포함하는 모든 파일과 디렉토리를 무시한다.

`!`키워드를 통해 exclude의 exclude를 만들 수 있다. 아래 예시에서 `README.md`를 제외한 모든 md 포맷 파일은 무시된다.

```dockerfile
*.md
!README.md
```

## FROM

`FROM`은 새로운 빌드 스테이지를 초기화하고 부수적인 명령어를 수행하기 위한 기반 이미지를 설정하는 명령어다. 유효한 Dockerfile은 반드시 FROM 명령어로 시작해야 한다.

- `ARG` 명령어는 `FROM` 명령어 앞에 호출될 수 있는 유일한 명령어다.
- `FROM`은 하나의 Dockerfile 내에서 여러번 사용되어 여러개의 이미지를 만들거나 하나의 빌드 단계를 다른 빌드 단계에 대한 종속성으로 사용할 수 있다. 각각의 FROM 명령어의 마지막에 커밋에 의해 출력된 마지막 이미지 ID를 기록해 둬야 한다. 각 FROM 명령어는 이전 명령어에 의해 생성된 모든 상태를 지운다.
- `FROM 이미지 AS 별칭`을 통해 새 빌드 단계에 이름을 지정할 수 있다. 이는 후속 FROM이나 `COPY —from=name`에서 사용할 수 있다.
- 태그 또는 다이제스트 값은 선택 사항이다. 둘 중 하나를 생략하면 빌더는 기본적으로 최신 태그를 사용하여 이미지를 참조한다. 태그를 찾을 수 없는 경우 오류가 발생한다.

## 명령어 형식

명령어를 입력하는 형식에는 shell form 과 exec form 두 가지가 있다.

### SHELL FORM

- `RUN <command>`
- shell에서 실행되는 명령어 형식이다.
- 리눅스에서는 `/bin/sh -c`를 기본으로 사용하고, 윈도우에서는 `cmd /S /C`를 기본으로 사용한다.
- 기본 쉘은 SHELL 명령어를 이용하여 변경할 수 있다.
- \(백슬래시)를 이용하여 명령어를 여러 줄로 개행할 수 있다.
    
    ```dockerfile
    RUN /bin/bash -c 'source $HOME/.bashrc; \
    echo $HOME'
    ```
    

### EXEC FORM

- `RUN ["executable", "param1", "param2"]`
- shell 문자열과 병합되는 것을 방지할 수 있다.
- 지정된 shell 실행파일이 포함되지 않은 기본 이미지를 사용하여 명령을 실행할 수 있다.
- 반드시 큰 따옴표를 사용해야 한다.
- 다른 쉘을 이용하기 위해 다음과 같이 명렁어를 사용할 수 있다.
    
    ```dockerfile
    RUN ["/bin/bash", "-c", "echo hello"]
    ```
    
- shell form과 달리 명령 쉘을 호출하지 않는다.
    - 정상적인 shell 처리가 일어나지 않는다. 환경변수를 입력하더라도 환경변수를 대체하지 않는다.
    - shell 처리를 원하는 경우 shell form을 사용하거나 shell을 직접 실행해야 한다. (e.g. `RUN ["sh", "-c", "echo $HOME"]`)

## RUN

현재 이미지 위에 있는 새 레이어의 모든 명령어를 실행하고 결과를 커밋한다. 커밋된 결과 이미지는 `Dockerfile`의 다음 단계에서 사용한다.

빌드 단계에서 호출되고 이후 빌드된 이미지로 새 컨테이너를 생성할 때는 호출되지 않는다. 그러므로 이미지를 구축하는 데 필요한 대부분의 명령어는 RUN을 통해 수행된다고 볼 수 있다.

shell form, exec form 두 가지 형식을 이용할 수 있다.

## CMD

```dockerfile
CMD command param1 param2
CMD ["executable", "param1, "param2"]
CMD ["param1", "param2"]
```

`CMD`의 주요 목적은 실행 컨테이너에 대한 기본값을 제공하는 것이다. 기본값은 실행 파일을 포함하거나 실행 파일을 생략할 수 있다. 생략하는 경우 반드시 `ENTRYPOINT` 명령어도 지정해야 한다. 

Dockerfile 내에서 오직 하나의 `CMD` 명령어만 존재할 수 있다. 둘 이상의 `CMD` 나열시 마지막 `CMD`만 적용된다.

`CMD`를 통해 `ENTRYPOINT` 명령어의 기본 인수를 제공하는 경우 `CMD` 및 `ENTRYPOINT` 명령어를 모두 JSON 배열 형식으로 지정해야 한다.

`CMD`는 세 가지 형식으로 작성될 수 있다.

- `CMD command param1 param2`: shell form
- `CMD ["executable", "param1, "param2"]`: exec form
- `CMD ["param1", "param2"]`: ENTRYPOINT의 기본 파라미터로 사용된다.

## ENTRYPOINT

```dockerfile
ENTRYPOINT ["executable", "param1", "param2"]

# or

ENTRYPOINT command param1 param2
```

`ENTRYPOINT`는 실행파일로 실행할 컨테이너를 구성할 수 있다. `docker run <image>`에 대한 명령줄 인수는 `ENTRYPOINT`의 모든 요소 뒤에 추가되고, CMD를 통해 지정된 모든 요소를 재정의 할 것이다. 이를 통해 인수를 `ENTRYPOINT`로 전달할 수 있다. 즉, `docker run --entrypoint`를 통해 `ENTRYPOINT` 명령어를 재정의 할 수 있다. 단, 재정의 하는 경우 바이너리를 `exec`로만 설정할 수 있으며, `sh -c`는 사용할 수 없다.

shell form을 이용하면 `CMD` 또는 실행 명령줄 인수가 사용되는 것을 방지하지만, `ENTRYPOINT`가 signal을 전달하지 않는 `/bin/sh -c`의 하위 명령으로 시작된다는 단점이 있다. 즉, 실행 파일은 컨테이너의 `PID 1`이 아니며, UNIX signal을 수신하지 않으므로 실행 파일은 `docker stop <container>` 수행시 `SIGTERM`을 수신하지 않는다. 그러므로 `docker stop` 명령어 입력시 타임아웃 후에 `SIGKILL`이 호출된다. 서버 이미지의 graceful shutdown을 위해서라면 shell form을 지양하자.

시작 스크립트 파일을 작성하여 사용하는 경우 해당 파일 내에서 exec 및 gosu 명령을 사용하여 최종 실행 파일이 UNIX signal을 수신하도록 할 수 있다.

```bash
#!/usr/bin/env bash
set -e

if [ "$1" = 'postgres' ]; then
    chown -R postgres "$PGDATA"

    if [ -z "$(ls -A "$PGDATA")" ]; then
        gosu postgres initdb
    fi

    exec gosu postgres "$@"
fi

exec "$@"
```

|  | No ENTRYPOINT | ENTRYPOINT exec_entry p1_entry | ENTRYPOINT [“exec_entry”, “p1_entry”] |
| --- | --- | --- | --- |
| No CMD | error, not allowed | /bin/sh -c exec_entry p1_entry | exec_entry p1_entry |
| CMD [“exec_cmd”, “p1_cmd”] | exec_cmd p1_cmd | /bin/sh -c exec_entry p1_entry | exec_entry p1_entry exec_cmd p1_cmd |
| CMD [“p1_cmd”, “p2_cmd”] | p1_cmd p2_cmd | /bin/sh -c exec_entry p1_entry | exec_entry p1_entry p1_cmd p2_cmd |
| CMD exec_cmd p1_cmd | /bin/sh -c exec_cmd p1_cmd | /bin/sh -c exec_entry p1_entry | exec_entry p1_entry /bin/sh -c exec_cmd p1_cmd |

## LABEL

```dockerfile
LABEL <key>=<value> <key>=<value> <key>=<value> ...

# e.g.
LABEL "com.example.vendor"="ACME Incorporated"
LABEL com.example.label-with-value="foo"
LABEL version="1.0"
LABEL description="This text illustrates \
that label-values can span multiple lines."
```

`LABEL` 명령어는 이미지에 메타데이터를 추가하는데 사용된다. 설정된 내용은 이미지 빌드 후 `docker image inspect`를 통해 확인할 수 있다. (e.g. `docker inage inspect <image>`)

## EXPOSE

```dockerfile
EXPOSE <port> [<port>/<protocol>]

# e.g.
EXPOSE 80/udp
EXPOSE 80/tcp
```

컨테이너가 특정한 네트워크 포트를 런타임에 listen중이라고 도커에 알린다. 포트번호와 프로토콜을 지정할 수 있다. 프로토콜을 별도로 지정하지 않은 경우 기본 값은 TCP다.

`EXPOSE` 명령어는 실제로 포트를 publish 하지 않는다. 이미지를 빌드하는 사람과 컨테이너를 실행하는 사람 사이에 어떤 포트를 publish할지에 대한 일종의 문서 역할을 수행한다. 컨테이너 실행시 실제로 포트를 publish하기 위해서는 `docker run`의 `-p` 플래그를 사용하여 하나 이상의 포트를 publish하거나 `-P` 플래그를 사용하여 노출된 모든 포트를 publish하고 상위 포트에 매핑해야 한다.

## ENV

```dockerfile
ENV <key>=<value>

# 또는

ENV KEY VALUE
```

`ENV`는 환경변수를 설정하기 위한 명령어다. key에 해당하는 value를 설정할 수 있다. 이 값이 선언된 후에는 빌드 단계에 있는 후속 명령어에 사용할 수 있다. 값은 다른 변수에 대해 interpreted되어 quote 문자열은 이스케이프 되지 않으면 제거된다.

명령줄 구문 분석(command line parsing)과 마찬가지로 따옴표와 백슬래시는 값 내에 공백을 포함하는 곳에서 사용할 수 있다.

```dockerfile
ENV MY_NAME="John Doe"
ENV MY_DOG=Rex\ The\ Dog
ENV MY_CAT=fluffy
```

여러개의 값을 하나의 명령으로 설정할 수도 있다.

```dockerfile
ENV MY_NAME="John Doe" MY_DOG=Rex\ The\ Dog \
    MY_CAT=fluffy
```

`ENV`를 통해 설정된 환경변수는 생성된 이미지를 실행하여 컨테이너가 작동할 때 까지 지속된다. `docker inspect`를 통해 확인하고 `docker run -—env <key>=<value>`로 변경할 수 있다.

환경변수 영속성은 예상치 못한 부수효과를 유발할 수 있다. 예를 들어, ENV `DEBIAN_FRONTEND=noninteractive`는 `apt-get`명령어의 동작이 변경되어 이미지를 사용하는 사용자에게 혼란을 줄 것이다.

환경변수가 빌드 중에만 필요하고 최종 이미지에는 없는 경우 아래와 같이 단일 명령에 대한 값을 대신 설정할 수 있다.

```dockerfile
RUN DEBIAN_FRONTEND=noninteractive apt-get update && apt-get install -y ...
```

또는 `ARG` 명령어를 사용하여 빌드 단계에서만 사용하며 영속성을 가지지 않는 환경변수를 선언할 수 있다.

## ADD

```dockerfile
ADD <src> <dest>

# hom으로 시작하는 빌드 디렉토리 내 모든 파일 또는 디렉토리를 컨테이너 내 절대경로 mydir 디렉토리에 복사
ADD hom* /mydir/

# hom?.txt 형태의 파일을 모두 컨테이너 내 절대경로 mydir 디렉토리에 복사
ADD hom?.txt /mydir/

# test.txt 파일을 컨테이너 내 상대경로에 복사. 현재 경로는 WORKDIR
ADD test.txt relativeDir/
```

`ADD` 명령어는 새로운 파일, 디렉토리 또는 원격 파일 URL을 `<src>`에서 복사하여 이미지 내 파일시스템 경로 `<dest>`에 추가하는 명령어다.

`<src>` 대상이 파일 또는 디렉토리인 경우 해당 경로는 빌드 위치를 기준으로 상대경로로 지정된다.

`<dest>`는 절대 경로 또는 `WORKDIR`에 대한 상대 경로이며, 컨테이너 내부에 소스가 복사된다.

각 `<src>`는 와일드카드를 포함할 수 있으며, 일치는 Go의 `filepath.Match` 규칙을 따른다. 

`.tar.xz`와 같이 압축된 파일인 경우 빌드시 자동으로 압축을 해제한다.

`<src>`경로는 빌드 컨텍스트 내에 있어야 한다. `../something`과 같은 경로는 추가할 수 없다. 도커 빌드의 첫 단계에서 컨텍스트 디렉토리 (및 하위 디렉토리)를 도커 데몬으로 보내기 때문이다.

## COPY

`COPY` 명령어는 로컬 내 파일, 디렉토리를 `<src>`에서 복사하여 컨테이너 내 파일시스템 경로 `<dest>`에 추가하는 명령어다.

`ADD`가 제공하는 원격 파일 복사, 압축 파일 해제 기능을 이용할 필요가 없다면 이 명령어를 사용하는 것을 권장한다.

## VOLUME

```dockerfile
VOLUME /myvolume
VOLUME ["/vol1", "/vol2", "/vol3"]
VOLUME /myvolume1 /myvolume2
```

`VOLUME`명령어는 지정된 이름으로 마운트 지점을 만들고 기본 호스트 또는 다른 컨테이너로부터 외부적으로 마운트된 볼륨을 보유하는 것으로 표시한다. 값은 JSON 배열, 일반 문자열이 될 수 있다.

docker run 명령은 기본 이미지 내 지정된 위치에 존재하는 모든 데이터로 새로 생성된 볼륨을 초기화한다.

생성된 볼륨은 컨테이너의 생명주기와 무관하므로 컨테이너가 삭제되더라도 사라지지 않는다. 데이터를 영속적으로 저장하거나 여러개의 도커 컨테이너가 같은 저장공간을 공유해야 하는 경우 유용하다.

생성된 볼륨을 컨테이너가 사용하기 위해서는 이미지 실행 시 `-v` 옵션을 통해 바인드 마운트를 수행하면 된다. (e.g. `docker run -v volume_name:/mount_path`)

생성된 볼륨은 `docker inspect`를 통해 위치를 확인할 수 있다.

```dockerfile
FROM ubuntu
RUN mkdir /myvol
RUN echo "hello, world" > /myvol/greeting
VOLUME /myvol
```

위 예제에서 `greeting` 파일을 새로 만든 볼륨 `/myvol`에 복사하도록 하는 이미지를 생성한다.

## WORKDIR

```dockerfile
WORKDIR /path/to/workdir
```

`WORKDIR` 명령어는 다른 명령어가 수행될 작업 디렉토리를 설정한다. 존재하지 않는 디렉토리인 경우 자동으로 생성된다.

여러번 호출되는 경우, 호출된 시점에 작업 디렉토리가 변경된다.

절대 경로(`WORKDIR /path`) 및 상대경로(`WORKDIR path`)를 모두 이용할 수 있다.

`WORKDIR` 명령어를 사용할 때, `ENV` 명령어를 통해 설정된 환경변수를 사용할 수 있다.

## ARG

```dockerfile
ARG <name>[=<default value>]

# e.g.
ARG user1
ARG buildno=1
```

`ARG`는 사용자가 buildtime에 사용자가 전달할 수 있는 변수를 정의하는 명령어다. `docker build —build-arg <varname≥<value>`형태로 지정할 수 있다. 선언시 기본값을 입력하지 않으면 빌드 경고가 발생한다.

`ARG` 명령어로 정의하기 전에 변수를 사용하면 빈 문자열이 생성된다.

`ARG` 명령어는 정의된 build step이 끝나면 범위를 벗어난다. 그러므로 여러 단계에서 ARG를 사용하기 위해서는 각 단계마다 ARG 명령어가 포함되어야 한다.

```dockerfile
FROM busybox
ARG SETTINGS
RUN ./run/setup $SETTINGS

FROM busybox
ARG SETTINGS
RUN ./run/other $SETTINGS
```

`ARG`와 `ENV`를 같이 사용하는 경우 `ENV`는 항상 같은 이름의 `ARG` 명령어를 재정의한다. 빌드 과정에서 `ARG`값을 설정하더라도 ENV를 통해 재정의했다면 재정의된 값이 사용된다.

```dockerfile
FROM ubuntu
ARG CONT_IMG_VER
ENV CONT_IMG_VER=${CONT_IMG_VER:-v1.0.0}
RUN echo $CONT_IMG_VER
```

위와 같이 활용하면 build 인자로 설정된 경우 설정된 값을, 설정되지 않은 경우 기본값을 영속적으로 사용하도록 지정할 수 있다.

## 참고

- [dockerfile reference](https://docs.docker.com/engine/reference/builder/)