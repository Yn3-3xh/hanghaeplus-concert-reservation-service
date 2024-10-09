# 콘서트 예약 서비스
* 콘서트 예약 서비스 구현
* 사용자는 좌석예약 시 미리 충전한 잔액 사용
* 대기열 시스템 구축
* 좌석 예약 요청 시 결제가 이루어지지 않더라도 일정 시간동안 다른 유저가 해당 좌석에 접근할 수 없도록 제어

## API Specs
1. 유저 대기열 토큰 기능
   * 서비스를 이용할 토큰을 발급받는 API를 작성
   * 토큰은 유저의 UUID 와 해당 유저의 대기열을 관리할 수 있는 정보(대기 순서 or 잔여 시간 등) 를 포함
   * 이후 모든 API는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능
    > 기본적으로 폴링으로 본인의 대기열을 확인한다고 가정, 다른 방안 또한 고려해보고 구현 가능

2. 예약 가능 날짜 / 좌석 API
   * 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API를 각각 작성
   * 예약 가능한 날짜 목록을 조회 가능
   * 날짜 정보를 입력받아 예약가능한 좌석정보를 조회 가능
    > 좌석 정보는 1 ~ 50 까지의 좌석번호로 관리

3. 좌석 예약 요청 API
   * 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API를 작성
   * 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정(시간은 정책에 따라 자율적으로 정의)
   * 만약 배정 시간 내에 결제가 완료되지 않는다면 좌석에 대한 임시 배정은 해제되어야 하며, 다른 사용자는 예약할 수 없음

4. 잔액 충전 / 조회 API
   * 결제에 사용될 금액을 API를 통해 충전하는 API를 작성
   * 사용자 식별자 및 충전할 금액을 받아 잔액을 충전
   * 사용자 식별자를 통해 해당 사용자의 잔액을 조회

5. 결제 API
   * 결제 처리하고 결제 내역을 생성하는 API를 작성
   * 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고, 대기열 토큰을 만료시킴

## Milestone
[Project Milestone Link](https://github.com/users/Yn3-3xh/projects/1/views/1)

## Domain Modeling
<div style="background-color: white; display: inline-block;">
    <img src="docs/images/domain-modeling.png" alt="도메인 모델링" style="width: 800px;" />
</div>

## Sequence Diagram
### 1. 유저 대기열 토큰 기능
[유저 대기열 토큰 기능 Sequence Diagram](https://github.com/Yn3-3xh/hanghae-backend-plus/issues/4)

**토큰 발급 API**
``` mermaid
sequenceDiagram
autonumber

actor Client

Client->>Token: POST /tokens

Token->>Token: 토큰 조회

rect rgb(255, 230, 200)
    alt 토큰이 존재하는 경우
            Token->>Token: 토큰 초기화
    else 토큰이 존재하지 않는 경우
        Token->>Token: 토큰 발급 및 저장
    end
end

Token->>Client: 토큰 반환
Note right of Client: 토큰 수신
```

**대기열 확인 API**
``` mermaid
sequenceDiagram
autonumber

actor Client

loop Client에서 폴링으로 대기열을 확인한다고 가정
    Client->>Concert: GET /concerts/{concertId}/performance-date/{date}/queues
    
    Concert->>Token: 토큰 검증 요청
    Token->>Token: 토큰 검증
    Token->>Concert: 토큰 검증 완료
    
    rect rgb(212, 240, 240)
        opt 대기열에 등록되어 있지 않은 경우
            Concert->>Queue: 대기열 추가 요청
            Queue->>Queue: 대기열에 추가
            Concert->>Token: 토큰 상태 변경(대기)
        end
    end
    
    Concert->>Queue: 대기열 순서 조회
    Queue->>Concert: 대기열 순서 반환
    Concert->>Concert: 대기열 순서 계산
    Concert->>Client: 남은 순서 반환
    Note right of Client: 남은 순서 수신
end
```

### 2. 예약 가능 날짜 / 좌석 API
[예약 가능 날짜 / 좌석 API Sequence Diagram](https://github.com/Yn3-3xh/hanghae-backend-plus/issues/5)

**예약 가능 날짜 조회 API**
``` mermaid
sequenceDiagram
autonumber

actor Client

Client->>Concert: GET /concerts/{concertId}/reservations/available

Concert->>Concert: 예약 가능한 날짜 목록 조회

rect rgb(255, 222, 239)
    break 예약 가능한 날짜가 존재하지 않는 경우
        Concert->>Client: 204 No Content
        Note right of Client: 예약 가능한 날짜가 없습니다.
    end
end
Concert->>Client: 예약 가능한 날짜 목록 반환
Note right of Client: 예약 가능한 날짜 목록 수신
```

**좌석 조회 API**
``` mermaid
sequenceDiagram
autonumber

actor Client

Client->>Concert: GET /concerts/{concertId}/performance-date/{date}/seats/available

Concert->>Token: 토큰 검증 요청
Token->>Token: 토큰 검증
Token->>Concert: 토큰 검증 완료

rect rgb(255, 222, 239)
    break 토큰이 유효하지 않은 경우
        Concert->>Client: 401 Unauthorized
        Note right of Client: 유효하지 않은 토큰입니다.
    end
end

Concert->>Token: 토큰 상태값 변경(활성)
Concert->>Concert: 예약 가능한 좌석 목록 조회
rect rgb(255, 222, 239)
    break 예약 가능한 좌석이 존재하지 않는 경우
        Concert->>Client: 204 No Content
        Note right of Client: 예약 가능한 좌석이 없습니다.
    end
end

Concert->>Client: 예약 가능한 좌석 목록 반환
Note right of Client: 예약 가능한 좌석 목록 수신
```

### 3. 좌석 예약 요청 API
[좌석 예약 요청 API Sequence Diagram](https://github.com/Yn3-3xh/hanghae-backend-plus/issues/6)

**좌석 예약 요청 API**
``` mermaid
sequenceDiagram
autonumber

actor Client

Client->>Concert: POST /concerts/{concertId}/performance-date/{date}/seats/{seatId}

Concert->>Token: 토큰 검증 요청
Token->>Token: 토큰 검증
Token->>Concert: 토큰 검증 완료
rect rgb(255, 222, 239)
    break 토큰이 유효하지 않은 경우
        Concert->>Client: 401 Unauthorized
        Note right of Client: 유효하지 않은 토큰입니다.
    end
end

Concert->>Concert: 좌석 임시 배정

Concert->>Client: 좌석 임시 배정 완료 메시지 반환
Note right of Client: 좌석 임시 배정이 완료되었습니다.
```

### 4. 잔액 충전 / 조회 API
[잔액 충전 / 조회 API Sequence Diagram](https://github.com/Yn3-3xh/hanghae-backend-plus/issues/7)

**잔액 충전 API**
``` mermaid
sequenceDiagram
autonumber

actor Client

Client->>Point: POST /points

Point->>Token: 토큰 검증 요청
Token->>Token: 토큰 검증
Token->>Point: 토큰 검증 완료
rect rgb(255, 222, 239)
    break 토큰이 유효하지 않은 경우
        Point->>Client: 401 Unauthorized
        Note right of Client: 유효하지 않은 토큰입니다.
    end
end

Point->>Point: 포인트 조회
Point->>Point: 포인트 충전
Point->>Point: 포인트 충전 내역 저장
Point->>Client: 포인트 잔액 반환
Note right of Client: 포인트 잔액 수신
```

**잔액 조회 API**
``` mermaid
sequenceDiagram
autonumber

actor Client

Client->>Point: GET /points

Point->>Token: 토큰 검증 요청
Token->>Token: 토큰 검증
Token->>Point: 토큰 검증 완료
rect rgb(255, 222, 239)
    break 토큰이 유효하지 않은 경우
        Point->>Client: 401 Unauthorized
        Note right of Client: 유효하지 않은 토큰입니다.
    end
end

Point->>Point: 포인트 조회
Point->>Client: 포인트 잔액 반환
Note right of Client: 포인트 잔액 수신
```

### 5. 결제 API
[결제 API Sequence Diagram](https://github.com/Yn3-3xh/hanghae-backend-plus/issues/8)

**결제 API**
``` mermaid
sequenceDiagram
autonumber

actor Client

Client->>Payment: POST /payments

Payment->>Token: 토큰 검증 요청
Token->>Token: 토큰 검증
Token->>Payment: 토큰 검증 완료
rect rgb(255, 222, 239)
    break 토큰이 유효하지 않은 경우
        Payment->>Client: 401 Unauthorized
        Note right of Client: 유효하지 않은 토큰입니다.
    end
end

Payment->>Point: 포인트 차감 요청
Point->>Point: 포인트 차감
Point->>Payment: 포인트 차감 메세지 반환

Payment->>Payment: 결제 내역 저장

rect rgb(255, 222, 239)
    opt 포인트가 부족해서 결제가 안된 경우
        Payment->>Client: 400 Bad Request
        Note right of Client: 결제할 포인트가 부족합니다.
    end
end

Payment->>Concert: 임시 배정된 좌석을 해당 유저에게 배정
Payment->>Token: 토큰 상태 변경(완료)

Payment->>Client: 결제 완료 메시지 반환
Note right of Client: 결제가 완료되었습니다.
```