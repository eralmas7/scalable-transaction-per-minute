Scenario: When user tries to add an old transaction into the system
 
Given a user tries to add a new transaction into the system
When client tries to add amount 120.12 for time 100
Then user wouldn't get a response but the status would be 204 