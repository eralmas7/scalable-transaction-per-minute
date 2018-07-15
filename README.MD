Requirements
---------------
We would like​ ​to​ ​have​ ​a​ ​restful​ ​API​ ​for​ ​our​ ​statistics.​ ​The​ ​main​ ​use​ ​case​ ​for​ ​our​ ​API​ ​is​ ​to calculate​ ​realtime​ ​statistic​ ​from​ ​the​ ​last​ ​60​ ​seconds.​ ​There​ ​will​ ​be​ ​two​ ​APIs,​ ​one​ ​of​ ​them​ ​is called​ ​every​ ​time​ ​a​ ​transaction​ ​is​ ​made.​ ​It​ ​is​ ​also​ ​the​ ​sole​ ​input​ ​of​ ​this​ ​rest​ ​API.​ ​The​ ​other​ ​one returns​ ​the​ ​statistic​ ​based​ ​of​ ​the​ ​transactions​ ​of​ ​the​ ​last​ ​60​ ​seconds.

    POST /transactions 
    {
      ​​​​​​​​​​​​​"amount": ​​12.3,
      ​​​​​​"timestamp": ​​1478192204000
    } 

Every Time a new transaction happened, this endpoint will be called. 

`GET​ ​/statistics` 

Returns:  

    {
      ​​​​​​​​​​​​​​"sum": ​​1000,
      ​​​​​​​​​​​​​​"avg": ​​100,
      ​​​​​​​​​​​​​​"max": ​​200,
      ​​​​​​​​​​​​​​"min": ​​50,
      ​​​​​​​​​​​​​​"count": ​​10
    }

This​ ​is​ ​the​ ​main​ ​endpoint​ ​of​ ​this​ ​task,​ ​this​ ​endpoint​ ​have​ ​to​ ​execute​ ​in​ ​constant​ ​time​ ​and memory​ ​(O(1)).​ ​It​ ​returns​ ​the​ ​statistic​ ​based​ ​on​ ​the​ ​transactions​ ​which​ ​happened​ ​in​ ​the​ ​last​ ​60 seconds.