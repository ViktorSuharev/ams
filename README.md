# ams
Account Management System
    Simple REST API for managing banking account

Business Logic constraints:
    negative amounts are prohibited

REST API:<br/>
- top up money to account:<br/>
    path: /service/account/put:<br/>
    request params:id, amount
    type: PATCH

- withdraw money from account<br/>
    path: /service/account/get<br/>
    request params: id, amount<br/>
    type: PATCH
 
- transfer money from between accounts<br/>
    path: /service/account/transfer<br/>
    request params: senderId, receiverId, amount<br/>
    type: PATCH

Database:
    H2
    Isolation level:
        - Serializable
