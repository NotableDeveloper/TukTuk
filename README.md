# TukTuk
풋살 매칭 서비스 백엔드 레포지토리입니다.

## Spec
Language : Java 17

Freamework : SpringBoot 3.2.1

Build : Gradle 8.5

Database : MySQL

## Project Run
Docker compose가 작성되어 있고, 이를 실행하는 쉘 스크립트가 작성되어 있으므로 프로젝트의 루트에서 다음과 같이 실행할 수 있습니다.

$ start.sh

p.s 만약 CRLF로 인하여 start.sh 파일이 실행되지 않는다면 아래와 같이 sed 명령을 사용하여 개행을 제거해주시기 바랍니다. 

$ sed -i 's/\r//' start.sh