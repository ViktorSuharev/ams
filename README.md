# ams
Account Management System
    Simple REST API for managing banking account

Business Logic constraints:
    negative amounts are prohibited

REST API:
- top up money to account
    path: /service/account/put
    request params:
        - id
        - amount
    type: PATCH

- withdraw money from account
    path: /service/account/get
    request params:
            - id
            - amount
    type: PATCH
 
- transfer money from between accounts
    path: /service/account/transfer
    request params:
            - senderId
            - receiverId
            - amount
    type: PATCH

Database:
    H2
    Isolation level:
        - Serializable