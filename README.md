# 도서 대여 및 관리 시스템 ![image](https://user-images.githubusercontent.com/48886490/104954335-64f74980-5a0b-11eb-8e13-3c0932e1031f.png)

@yeop96  @doputer  @metorg  @kwondongwook1

### 소개

![image](https://user-images.githubusercontent.com/48886490/104953645-22813d00-5a0a-11eb-864d-bba174904eac.png)

- C++ MFC의 대화 상자 기반으로 개발된 **도서 대여 및 관리 시스템**

### 사전설정

- mysql라이브러리.zip 에 라이브러리 본 폴더에 추가
- pch.h 헤더파일에 DB_USER , DB_PASS 부분 자신의 DB ID, PW를 입력해야 사용 가능, DB 테이블은 LibrarylDump.sql로 사용 가능
- BookManagement.rc 에 book.ico 경로가 달라서 안된다면 IDR_MAINFRAME 경로 맞게 설정

### 시스템 구조도

![image](https://user-images.githubusercontent.com/48886490/104951957-e7313f00-5a06-11eb-9315-e533073192ca.png)

### DB 테이블

![image](https://user-images.githubusercontent.com/48886490/104952877-b520dc80-5a08-11eb-9cd7-4339579175e5.png)

### 전체 UML

![image](https://user-images.githubusercontent.com/48886490/104952814-928ec380-5a08-11eb-8d45-8aab7b1b156a.png)

### 기능

![image](https://user-images.githubusercontent.com/48886490/104952757-73903180-5a08-11eb-8736-ff7ad0ddfbd5.png)

### 서비스 플로우

![image](https://user-images.githubusercontent.com/48886490/104952923-c9fd7000-5a08-11eb-93d9-f3abe694694d.png)

### 화면

- 로그인 및 회원가입

![image](https://user-images.githubusercontent.com/48886490/104953814-7ab83f00-5a0a-11eb-8a4b-f4837e147eda.png)
![image](https://user-images.githubusercontent.com/48886490/104953818-7db32f80-5a0a-11eb-8b50-6570c679bbba.png)

- 회원 도서 대여, 도서 반납

![image](https://user-images.githubusercontent.com/48886490/104954066-f31f0000-5a0a-11eb-9f25-9a2a15531d01.png)
![image](https://user-images.githubusercontent.com/48886490/104954071-f4e8c380-5a0a-11eb-9f4a-6a68e05d09dc.png)

- 검색

![image](https://user-images.githubusercontent.com/48886490/104955040-daafe500-5a0c-11eb-9c8c-8ca247521650.png)

- 대여 예외처리

![image](https://user-images.githubusercontent.com/48886490/104954589-d931ed00-5a0b-11eb-9917-c6299bed8e65.png)

- 관리자 도서 관리, 회원 관리

![image](https://user-images.githubusercontent.com/48886490/104954103-003bef00-5a0b-11eb-93cf-2f9423d31463.png)
![image](https://user-images.githubusercontent.com/48886490/104954107-029e4900-5a0b-11eb-9988-a65cec54c478.png)

- 회원 관리 CRUD 및 연체횟수 초기화

![image](https://user-images.githubusercontent.com/48886490/104954947-a9cfb000-5a0c-11eb-990d-b1eb74d2e169.png)
