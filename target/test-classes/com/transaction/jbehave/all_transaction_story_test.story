Scenario: When user tries to add an old transaction into the system
 
Given a user tries to add an old transaction into the system
When client tries to add amount 20.12 for time 100
Then user wouldn't get a response but the status would be 204

Scenario: When user tries to add a transaction into the system
 
Given a user tries to add a new transaction into the system
When client tries to add amount 120.13 with current time
Then user would get the status as 201

Scenario: When user tries to get the statistics from the system
 
Given a user adds the transaction statistics from the System with amount 10.90, 11.02 with current time.
When Client tries to get the statistics based on current time with a status of 200.
Then user expects a total amount as 142.05, min as 10.90, max as 120.13, average as 47.35 and count as 3