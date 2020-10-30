## java-backend-test

This project for test purpose.

## Installation

At project directory terminal run this for build.
```bash 
mvnw clean install
```
After build success then run this for start.
```bash
mvnw spring-boot:run
```
Or run this for test.
```bash
mvnw test
```

## Usage

Open swagger-ui
```
http://localhost:8080/swagger-ui/
```
Request for register.
```
{
    "firstName":"testFirstName",
    "lastName":"testLastName",
    "username":"testUsername",
    "password":"12345678",
    "email":"ttt@tt3.tt",
    "address":"testAddress",
    "telephoneNumber": "123457770",
    "salary": "15000"
}
```
Expect response.
```
{
    "data": {
        "id": 1,
        "firstName": "testFirstName",
        "lastName": "testLastName",
        "username": "testUsername",
        "email": "ttt@tt3.tt",
        "address": "testAddress",
        "telephoneNumber": "123457770",
        "salary": "15000",
        "referenceCode": "256310307770",
        "memberType": "Silver"
    },
    "accessToken": "xxxxxxxxxxxxxxxxxxxxxxxx.xxxxxxxxxxxxxxxxxxxxxxxx.xxxxxxxxxxxxxxxxxxxxxxxx"
}
```
## Database
```
http://localhost:8080/h2-console
```
No need password.

