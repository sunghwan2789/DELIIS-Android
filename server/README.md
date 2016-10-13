# 제품 관리자 API 문서

## Table Structure
* database: goods_manager
 * table: goods

Field      | Type             | Details
-----------|------------------|----------------
number     | int(10) unsigned | goods number, primary key
name       | varchar(150)     | goods name
division   | varchar(10)      | goods type, 교환, TMR, RLI, RAU, cable
broken     | tinyint(1)       | 1 if broken
codename   | varchar(150)     | goods code name
content    | text             | goods comment
takeType   | varchar(3)       | take type, 대여: "OUT", 빌림: "IN", 소유: "NO"
takePeriod | text             | TK 기간
image      | varchar(150)     | image name under /image

--------------------------------------------------

## GET: /list
returns goods rows in json format
### Query strings
Table Structure의 Field를 key, 찾고자하는 값을 value로 넣으면 Query string에 알맞은 데이터를 출력합니다.
#### examples

##### url
    /list?name=일본도&broken=0
##### body
    [
      {
        "number": 4,
        "name": "일본도3",
        "division": "TMR",
        "broken": 0,
        "codename": "",
        "content": "카타나라고 부른다.",
        "takeType": "OUT",
        "takePeriod": "영구",
        "image": ""
      }
    ]

#### details
number, broken은 equal check를, 그 외 필드는 contains check한다.

그 외 필드에 한하여 OR 검색이 가능하다. ex) division=TMR|RLI|교환

--------------------------------------------------

## POST: /upload
upload goods
### Query strings
모든 열의 값이 필수적으로 입력되어야 하며, 데이터 인코딩은 multipart/form-data로 한다.

#### examples

##### url
    /upload
##### body
    only response status used

#### details
image 필드에는 파일 데이터를 전송해야 한다.

--------------------------------------------------

## GET: /item/:number
view goods
### Query strings
no need

#### examples

##### url
    /item/4
##### body
    {
      "number": 4,
      "name": "일본도3",
      "division": "TMR",
      "broken": 0,
      "codename": "",
      "content": "카타나라고 부른다.",
      "takeType": "OUT",
      "takePeriod": "영구",
      "image": ""
    }

--------------------------------------------------

## PUT: /item/:number
modify goods
### Query strings
same as POST: /upload

#### examples

##### url
    /item/4
##### body
    only response status used

--------------------------------------------------

## DELETE: /item/:number
delete goods
### Query strings
no need

#### examples

##### url
    /item/4
##### body
    only response status used


--------------------------------------------------

## GET: /image/:filename
static 전송한다. 끝.
