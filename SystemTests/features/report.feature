@resetReportEnv
Feature: Generating reports on transaction history

Scenario: Customer requests report from the same day
	When the customer requests report of purchases made in current time period
	Then the customer receives a history-record containing 1 item(s)
	
Scenario: Merchant requests report from the same day
	When the merchant requests report of purchases made in current time period
	Then the merchant receives a history-record containing 1 item(s)