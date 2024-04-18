# TukTuk
운동 경기를 하고 싶은 개인들을 매칭하여 경기장을 대여 해주는 플랫폼 서비스의 백엔드 레포지토리입니다.

## Spec
Language : Java 17 <br/>
Framework : SpringBoot 3.2.1 <br/>
Build : Gradle 8.5 <br/>
Database : MySQL <br/>
Environment : AWS EKS (Kubernetes)

## 제공하는 기능
TukTuk의 백엔드 애플리케이션에서는 다음과 같은 기능들을 제공합니다.

### 1. 로그인 및 회원가입 </br>
TukTuk은 kakao social login을 통해 인증 절차를 거쳐 로그인 및 회원가입이 가능합니다. <br/>
이와 관련한 인증 및 인가에 관한 상세한 내용은 [여기](#authorization-and-authentication)에 기술되어 있습니다.

또, 사용자를 경기장 소유주(FIELDOWNER), 일반 사용자(USER)라는 두 가지 Role로 구분하기 때문에 <br/>
회원가입은 아래와 같이 두 종류로 제공됩니다.

[일반 사용자 회원가입 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#241e9b24-f508-4387-b5ca-7642978f0666) <br/>
[경기장 소유주 회원가입 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#7eb8007e-f88f-4d92-8de6-9eb2463348d0) <br/>

로그인 기능은 Access Token을 기반으로 요청을 보낸 사용자의 Role을 구분하기 때문에 하나의 API만 존재합니다. <br/>

[로그인 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#771a76b3-5c51-4c4b-98aa-eba3768d66e3)<br/>

### 2. 경기장 및 코트에 관한 CRUD API
TukTuk의 도메인에는 경기장(Stadium)과 코트(Court)가 존재합니다. 경기장은 코트를 포함하는 개체이며, <br/>
하나의 경기장은 다수의 코트를 가질 수 있습니다. 반대로 하나의 코트는 반드시 하나의 경기장에 속해야합니다. <br>

경기장과 코트에 대해 예시를 들면 다음과 같습니다.
- "서울 체육관"이라는 체육관(Stadium)이 존재하고, 그 내부에 "A 경기장"이라는 이름의 축구 코트와 <br/>
"B 경기장" 이라는 이름의 농구 코트가 있다면 "서울 체육관"은 두 개의 코트(축구장과 농구장)을 가지는 것이 된다. <br/><br/>

- "A 경기장"은 "서울 체육관"에 속하는 고유한 코트이다. 다른 Stadium에서는 가질 수 없다.

다음은 경기장에 관한 CRUD API에 관한 설명입니다.

<details>
<summary> Stadium CRUD API </summary>
<div markdown="1">

#### 1. 경기장 등록
경기장을 등록하는 API입니다. 이 API는 경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다.

[경기장을 등록하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#17f13017-6a4f-4122-8f7f-d2557dcbd701)

#### 2. 경기장 ID로 조회
경기장이 가진 ID 값으로 조회하는 API이며, 누구나 호출이 가능합니다. 

[경기장 ID로 조회하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#653f9bc9-9479-4121-beeb-7cf2f1c16ec2)

#### 3. 경기장 정보 수정
등록한 경기장을 수정하는 API입니다. 이 API는 수정하려는 경기장을 소유하고, <br/> 
경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다. 

[경기장 정보를 수정하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#064bb6d7-33e2-497b-b518-f41f701460b9)

#### 4. 경기장 삭제
등록한 경기장을 삭제하는 API입니다. 이 API는 삭제하려는 경기장을 소유하고, <br/>
경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다.

[경기장을 삭제하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#ea190b1a-17c6-42ac-9614-3bfd7ffa5690)

#### 5. 경기장에 속한 코트 조회
경기장이 가진 ID 값으로 해당 경기장에 속한 코트들을 조회하는 API이며, 누구나 호출이 가능합니다. <br/>

[경기장에 속한 코트를 조회하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#1f33cb13-e2e1-4a9e-b0a5-432f81f997f9)

#### 6. 경기장 소유주가 등록한 경기장 조회
경기장 소유주가 자신이 등록한 경기장을 조회하는 API입니다. 이 API는 경기장 소유주 권한을 가진 사용자만
호출할 수 있습니다.

[경기장 소유주가 등록한 경기장을 조회하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#c5bf4e3f-9566-442a-aa9f-aaa961bdb1a7)

#### 7. 경기장 이름 검색
경기장 이름에 검색어가 포함되는 경기장을 검색하는 API입니다. 이 API는 누구나 호출할 수 있습니다.  

[경기장을 이름으로 검색하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#082d51a4-7db1-4942-903d-397ddd292f75)

</div>
</details>

다음은 코트에 관한 CRUD API에 관한 설명입니다.

<details>
<summary> Court CRUD API </summary>
<div markdown="1">

#### 1. 코트 등록
특정 경기장에 속할 코트를 등록하는 API입니다. 이 API는 등록하려는 코트가 속한 경기장을 소유하고 있고, <br>
경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다.

[코트를 등록하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#d1d5c8f5-d58f-46f6-89d2-5549a341b63a)

#### 2. 코트 ID로 조회
코트가 가진 ID로 조회하는 API입니다. 이 API는 누구나 호출할 수 있습니다.

[코트를 ID로 조회하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#2918699a-6c39-4327-8de5-73daae97d948)

#### 3. 코트 삭제
등록한 코트를 삭제하는 API입니다. 이 API는 삭제하려는 코트가 속한 경기장을 소유하고 있고, <br>
경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다.

[코트를 삭제하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#c6afb96e-cb2a-4ba7-b057-7ab4f99228fe)

#### 4. 코트 이미지 수정
코트를 등록할 때 사용한 이미지를 수정하는 API입니다. 이 API는 수정하려는 코트가 속한 경기장을 소유하고 있고, <br>
경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다.

[코트 이미지를 수정하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#8a4ae1d6-5505-45ba-9d99-0cb81a86f727)

#### 5. 코트 정보 수정
코트를 등록할 때 입력한 정보를 수정하는 API입니다. 이 API는 수정하려는 코트가 속한 경기장을 소유하고 있고, <br>
경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다.

[코트 정보를 수정하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#d82dc441-c4e2-4600-ba73-1677c5cc030e)

</div>
</details>

### 3. 경기 매칭 및 대여에 관한 API
TukTuk의 경기장 소유주는 자신이 등록한 경기장의 코트에 대해 매치 및 대여가 가능하도록 서비스에 등록할 수 있습니다. <br/>
또, 일반 사용자는 경기장 소유주가 등록한 매치 및 대여 내역에 대해 신청할 수도 있습니다.

다음은 경기 매칭 및 대여에 관한 API에 관한 설명입니다.

<details>
<summary> Schedule API </summary>
<div markdown="1">

#### 1. 일정 등록
경기장 소유주가 자신이 플랫폼에 등록한 경기장의 코트에 대해 매치 및 대여를 할 수 있도록 <br/>
일정을 등록하는 API입니다. 해당 API는 등록하려는 코트를 소유하고 있어야하며 <br/>
경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다. 

[일정 등록에 관한 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#9982652f-4d8a-4cc0-a835-a60c37edb80d)

#### 2. 일정 수정
경기장 소유주가 자신이 등록한 일정을 수정하는 API입니다. 해당 API는 수정하려는 코트를 소유하고 있어야하며 <br/> 
경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다.

[등록한 일정을 수정하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#dab7cbde-f9e8-42f7-91ad-777740313eaa)

#### 3. 일정 삭제
경기장 소유주가 자신이 등록한 일정을 삭제하는 API입니다. 해당 API는 삭제하려는 코트를 소유하고 있어야하며 <br/>
경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다.

[등록한 일정을 삭제하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#fe2a7754-73f7-4884-9e5d-f234e1b628e6)

#### 4. 일정 ID로 조회
등록된 일정을 ID로 조회하는 API입니다. 해당 API는 누구나 호출이 가능합니다.

[일정을 ID로 조회하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#4c5e62a9-fe8a-4590-86ff-9332f7b35cdb)

#### 5. 경기장 소유주가 등록한 일정 조회
경기장 소유주가 자신이 등록한 일정을 조회하는 API입니다. 경기장 소유주 권한을 가진 사용자만 호출할 수 있습니다.

[경기장 소유주가 등록한 일정을 조회하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#bb7f428f-2a86-4a6c-b432-4935dce5a4b9)

#### 5. 지역과 날짜로 일정 검색
등록된 일정을 지역과 날짜로 조회하는 API입니다. 해당 API는 누구나 호출이 가능합니다.

[지역과 날짜로 일정을 조회하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#1eaf14a8-883e-47e1-8b9e-c8a35c5d3fea)

#### 6. 매치 등록하기
등록된 일정에 참여하는 API입니다. 해당 API는 일반 사용자 및 경기장 소유주 권한을 가진 사용자라면 호출이 가능합니다.

[매치를 등록하는 API 명세](https://documenter.getpostman.com/view/32243568/2s9YysD2L4#92e701f2-188a-4983-acba-df98de9c25be)

</div>
</details>

## 프로젝트 시연
### 프로젝트 실행
docker 디렉터리 내에 Docker compose 파일이 작성되어있습니다. <br/> 
또, 프로젝트 루트에는 이를 실행하는 쉘 스크립트가 작성되어 있으므로, <br/>
프로젝트 루트에서 다음과 같이 실행할 수 있습니다.

$ start.sh

### 프로젝트 종료
띄워진 컨테이너를 모두 종료하는 스크립트도 프로젝트의 루트에 마련되어 있으니 <br/> 
프로젝트를 종료할 때에는 다음과 같이 실행하시면 됩니다.

$ stop.sh

### 쉘 스크립트 실행 시에 CRLF 문제 발생 시 
만약 CRLF로 인하여 start.sh 파일이 실행되지 않는다면 아래와 같이 sed 명령을 사용하여 <br/>
개행을 제거해주시기 바랍니다.

$ sed -i 's/\r//' start.sh

## 기타
[프로젝트 데모 동영상](readme/TukTuk%20Viedo.mp4)

[프로젝트 완료 보고서](readme/TukTuk%20Report.pdf)

# Authorization and Authentication
TukTuk은 사용자를 경기장 소유주, 일반 사용자로 구분합니다. 따라서 사용자 정보를 저장하고, <br/>
요청이 올 때마다 요청을 보낸 사용자가 해당 요청을 호출 할 수 있는 지를 확인할 필요가 있습니다. <br/>
이를 위해 해당 프로젝트에서는 AWS에서 제공하는 Congnito를 사용하였습니다.

## 로그인 및 회원가입 (인증)
사용자는 웹 브라우저를 통해 로그인 및 회원가입을 할 수 있습니다. 이 때의 동작은 아래 그림과 같습니다.

![auth2](readme/auth2.png)

## API 요청 (인가)
사용자는 백엔드 서버에 API 요청을 할 수 있습니다. 이 때, 사용자마다 호출이 가능한 API가 정해져있으므로 <br/>
아래와 같은 절차로 해당 사용자가 보낸 요청이 인가된 요청인 지를 확인합니다.

![auth1](readme/auth1.png)