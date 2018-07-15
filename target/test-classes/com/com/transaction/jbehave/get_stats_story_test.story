Scenario: When user tries to get statistics from the system.
 
Given a user adds the transaction statistics from the System with amount 102.9, 101.44 with current time.
When Client tries to get the statistics based on current time with a status of 200.
Then user expects a total amount as 204.34, min as 101.44, max as 102.9, average as 102.17 and count as 2