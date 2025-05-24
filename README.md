# 일정 관리 시스템

### Lv 0 - API 명세 및 ERD 작성
- [x] **API 명세서**  
  Postman을 통해 작성된 API 명세서를 아래 링크에서 확인  
  👉 [API 문서](https://hyoeunchoi.postman.co/workspace/My-Workspace~35bd902f-2bad-4464-815d-e599dd0a7640/collection/45173639-f47b444a-c27a-419b-93dc-4e04b46fee69?action=share&creator=45173639)

- [x] **ERD(Entity Relationship Diagram)**  
  ![ERD](./images/img.png)

- [x] **SQL 테이블 생성**  
  `schedule.sql` 파일을 통해 데이터베이스 테이블을 생성

---
### ✅ Lv 1 - 일정 생성 및 조회
- [x] **일정 생성**  
  사용자가 일정, 작성자명, 비밀번호를 입력해서 일정을 생성
  작성/수정일은 서버 시간 기준으로 자동 생성

- [x] **전체 일정 조회**  
  작성자명(name) 또는 수정일(updateDate) 기준으로 전체 일정을 조회할 수 있다
  조건이 없을 경우 모든 일정이 수정일 기준 내림차순으로 조회된다

- [x] **선택 일정 조회**  
  특정 일정 ID를 기반으로 단일 일정의 상세 정보를 조회할 수 있다

---

### ✅ Lv 2 - 일정 수정 및 삭제
- [x] **선택 일정 수정**  
  비밀번호 인증을 통해 사용자가 제목과 작성자명을 수정할 수 있다
  수정일은 현재 시간으로 바뀌며 작성일은 변경되지 않는다

- [x] **선택 일정 삭제**  
  비밀번호가 일치할 경우 해당 ID의 일정을 삭제할 수 있다

---

                         

