Feature: Go to baidu

@test
Scenario: search selenium
  Given I go to "http://www.baidu.com/"
     When I fill in field with id "kw" with "selenium"
     And  I click id "su" with baidu once
     Then I should see "seleniumhq.org" within 2 second
     Then I close browser
@test
  Scenario: search selenium1-2
    Given I go to "http://www.baidu.com/"
    When I fill in field with id "kw" with "http"
    And  I click id "su" with baidu once
    Then I should see "http" within 2 second
    Then I close browser