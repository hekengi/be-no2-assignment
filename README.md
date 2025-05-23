# API 명세서 

- Base URL : `http://localhost:8080/api/schedules`
- Content-Type : `application/json`
- 모든 응답에서 `password`는 제외됨

---
# ERD 
![ERD](./images/img_1.png)
---
# 일정 생성 
- **POST** `/api/schedules`
### Request
```json
{
  "title": "일정 제목",
  "userName": "효은",
  "password": "2023-10-02T00:00:00",
  "description": "일정 설명"
}
```