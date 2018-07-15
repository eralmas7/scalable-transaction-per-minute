Scenario: When user tries to add a transaction into the system
 
Given a user tries to add a new transaction into the system
When client tries to add amount 120.32 with current time
Then user wouldn't get a response but the status would be 201