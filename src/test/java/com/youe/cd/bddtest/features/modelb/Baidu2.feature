@testfeature
Feature: Go to baidu2

  @test2
Scenario: search oracle
  Given I wonna go to "http://www.baidu.com/"
     When I afill in field with id "kw" with "oracle"
     And  I aclick id "su" with baidu once
     Then I ashould see "oracle.com" within 2 second
     Then I aclose browser