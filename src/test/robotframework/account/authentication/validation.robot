*** Settings ***
Documentation   Verify account authentication validation scenarios
Library         SeleniumLibrary
Suite Setup     Before Test Suite
Suite Teardown  Close Browser
Force Tags      account  authentication  validation

*** Test Cases ***
Authenticate with empty credentials
	Input Text              id=login        ${EMPTY}
	Input Text              id=password     ${EMPTY}
	Submit Form             id=auth-account-form
	Element Text Should Be  id=form.errors  Invalid login or password

Authenticate with invalid credentials
	Input Text              id=login        test
	Input Text              id=password     test
	Submit Form             id=auth-account-form
	Element Text Should Be  id=form.errors  Invalid login or password

*** Keywords ***
Before Test Suite
	Open Browser                        ${SITE_URL}/account/auth  ${BROWSER}
	Register Keyword To Run On Failure  Log Source

