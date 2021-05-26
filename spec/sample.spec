# WireMockにリクエストを行い、いい感じにVerifyする

## WireMockにPOSTリクエストを行いリクエストを検証する
* WireMockの"/v1/systems/ping"にPOSTリクエストする
* ステータスコード"200"が返る
* WireMockの"/v1/systems/ping"に
*   Key: "foo" Value: "bar"
*   Key: "hoge.fuga" Value: "piyo"
* のJSONでPOSTリクエストが行われる
