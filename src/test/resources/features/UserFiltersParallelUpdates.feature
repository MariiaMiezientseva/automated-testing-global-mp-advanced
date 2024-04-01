Feature: User filter parallel update
  Update user filter via API in parallel

  Background:
    Given User create new filter

  Scenario: Update filter 1
    When User update filter with values:
      | user | in | mariia | description_cucumber1 | name_cucumber1 | false | startTime | launch |
    Then User delete created filter

  Scenario: Update filter 2
    When User update filter with values:
      | user | in | mariia | description_cucumber2 | name_cucumber2 | false | startTime | launch |
    Then User delete created filter

  Scenario: Update filter 3
    When User update filter with values:
      | user | in | mariia | description_cucumber3 | name_cucumber3 | false | startTime | launch |
    Then User delete created filter

  Scenario: Update filter 4
    When User update filter with values:
      | user | in | mariia | description_cucumber4 | name_cucumber4 | false | startTime | launch |
    Then User delete created filter

  Scenario: Update filter 5
    When User update filter with values:
      | user | in | mariia | description_cucumber5 | name_cucumber5 | false | startTime | launch |
    Then User delete created filter