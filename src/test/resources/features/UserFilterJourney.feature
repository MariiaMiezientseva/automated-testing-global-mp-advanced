Feature: User filter journey
  CRUD user filter via API

  @deleteFilters
  Scenario Outline: Create, update and delete filter
    Given User create new filter
    Then New filter appears with:
      | Api test filter 1            |
      | Filter, created via api test |
    When User update filter with values:
      | <filteringField> | <condition> | <value> | <description> | <name> | <isAsc> | <sortingColumn> | <type> |
    Then User check that filter is updated and has new name:
      | <name> |
    Examples:
      | filteringField | condition | value  | description           | name           | isAsc | sortingColumn | type   |
      | user           | in        | mariia | description_cucumber1 | name_cucumber1 | false | startTime     | launch |
      | user           | in        | mariia | description_cucumber2 | name_cucumber2 | false | startTime     | launch |
      | user           | in        | mariia | description_cucumber3 | name_cucumber3 | false | startTime     | launch |
      | user           | in        | mariia | description_cucumber4 | name_cucumber4 | false | startTime     | launch |
      | user           | in        | mariia | description_cucumber5 | name_cucumber5 | false | startTime     | launch |