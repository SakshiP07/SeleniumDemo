# Selenium Demo

A Java and Selenium WebDriver demonstration project that automates
[SauceDemo](https://www.saucedemo.com/) in Google Chrome. The project uses Maven
for dependency management and TestNG for test execution.

## Contents

- [Project Overview](#project-overview)
- [Test Coverage](#test-coverage)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Tests](#running-the-tests)
- [Test Reports](#test-reports)
- [Writing and Extending Tests](#writing-and-extending-tests)
- [Troubleshooting](#troubleshooting)
- [Notes and Limitations](#notes-and-limitations)

## Project Overview

This project demonstrates basic browser automation concepts, including:

- Starting Chrome through Selenium WebDriver
- Opening a website
- Locating elements with Selenium selectors
- Entering text and clicking controls
- Using explicit waits
- Asserting page state and navigation
- Handling browser cleanup

The automated test target is [https://www.saucedemo.com](https://www.saucedemo.com),
a public demo e-commerce application.

## Test Coverage

| Test | Location | Purpose |
| --- | --- | --- |
| Positive login | `LoginTest.testLogin` | Logs in with `standard_user` / `secret_sauce` and verifies that the Products inventory page is displayed |
| Negative login | `NegativeLoginTest.main` | Attempts to log in with invalid credentials and verifies that an error is shown |
| Checkout flow | `CheckoutTest` | Placeholder test class; no tests are currently implemented |

The main package also contains empty page-object placeholders for `LoginPage`,
`InventoryPage`, `CartPage`, and `CheckoutPage`. They can be expanded when the
project adopts the Page Object Model.

## Technology Stack

| Component | Version |
| --- | --- |
| Java | 23 |
| Maven | 3.x |
| Selenium Java | 4.38.0 |
| TestNG | 7.11.0 |
| WebDriverManager | 6.3.2 |
| Google Chrome | Required |
| Commons IO | 2.22.0 |

Selenium Manager or WebDriverManager may select or download an appropriate
ChromeDriver. An internet connection is required to open SauceDemo and may be
required to obtain browser driver dependencies.

## Project Structure

```text
seleniumdemo/
|-- pom.xml
|-- README.md
|-- src/
|   |-- main/
|   |   `-- java/com/itmskills/
|   |       |-- App.java
|   |       `-- pages/
|   |           |-- LoginPage.java
|   |           |-- InventoryPage.java
|   |           |-- CartPage.java
|   |           `-- CheckoutPage.java
|   `-- test/
|       `-- java/com/itmskills/tests/
|           |-- BaseTest.java
|           |-- LoginTest.java
|           |-- NegativeLoginTest.java
|           `-- CheckoutTest.java
`-- target/
    `-- surefire-reports/
```

`pom.xml` contains project dependencies and Java compiler settings.
`BaseTest` can prepare a Chrome browser before TestNG `@BeforeMethod` tests and
close it after `@AfterMethod`; however, the current `LoginTest` creates and
closes its own WebDriver instance.

## Prerequisites

Before running the project, install:

1. **JDK 23**
2. **Apache Maven 3.x**
3. **Google Chrome**
4. **A Java IDE**, such as IntelliJ IDEA or Eclipse (optional but recommended)

Verify the Java and Maven installations:

```bash
java -version
mvn -version
```

The reported Java version must be compatible with JDK 23 or a compatible JDK
that you configure in Maven.

## Getting Started

Clone or download the repository, then open a terminal in this folder:

```bash
cd /path/to/SoftwareTesting/seleniumdemo
```

Compile the project and download dependencies:

```bash
mvn clean compile
```

You can also run the initial Maven application entry point:

```bash
mvn exec:java -Dexec.mainClass=com.itmskills.App
```

> `mvn exec:java` may require the Maven Exec plugin to be configured or
> available separately. It does not run Selenium tests.

## Running the Tests

Run all discovered TestNG tests:

```bash
mvn clean test
```

Run only the positive login test:

```bash
mvn clean test -Dtest=LoginTest
```

Run a specific test method:

```bash
mvn clean test -Dtest=LoginTest#testLogin
```

The Chrome browser should open maximized or at the browser's default size,
navigate to SauceDemo, perform the login, and close after the test completes.

### Running `NegativeLoginTest`

`NegativeLoginTest` currently contains a `main` method rather than a TestNG
`@Test` method. Run it from a Java IDE as a Java application, or add a TestNG
`@Test` annotation to make it available to Maven test discovery.

## Test Reports

After `mvn test`, Maven Surefire reports are written to:

```text
target/surefire-reports/
```

Useful report files include:

- `index.html` - interactive TestNG HTML report
- `TEST-TestSuite.xml` - Surefire XML results
- `emailable-report.html` - summary suitable for sharing
- `junitreports/TEST-com.itmskills.tests.LoginTest.xml` - test-specific JUnit-style XML

Open the HTML report in a browser to review pass/fail status and test details.
Generated report and build files should not be committed unless your team
intentionally tracks them.

## Writing and Extending Tests

### Basic TestNG Structure

```java
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExampleTest {
    @Test
    public void example() {
        Assert.assertTrue(true);
    }
}
```

For browser tests, prefer one responsible WebDriver lifecycle:

1. Initialize Chrome in `@BeforeMethod`.
2. Navigate to the application.
3. Interact with elements.
4. Use explicit waits rather than fixed `Thread.sleep` calls.
5. Assert expected behavior.
6. Quit the browser in `@AfterMethod`, even when an assertion fails.

The existing `BaseTest` already provides protected `driver`, setup, and cleanup
hooks. A test can extend `BaseTest` instead of recreating the browser:

```java
public class InventoryTest extends BaseTest {
    // Tests use the inherited driver field.
}
```

### Page Object Model

The classes under `src/main/java/com/itmskills/pages` are ready for a Page Object
Model implementation. A page class should contain only locators and interactions
for that page. Assertions should generally remain in test classes.

## Troubleshooting

### Chrome does not open

- Confirm that Google Chrome is installed.
- Confirm that JDK 23 or a compatible JDK is active.
- Check that ChromeDriver is accessible through Selenium Manager or
  WebDriverManager.
- Run `mvn clean test` again to refresh dependencies.

### `NoClassDefFoundError` or dependency errors

Delete the generated build directory and rebuild:

```bash
mvn clean test
```

If Maven cannot download dependencies, verify network access and proxy settings.

### Element not found or page content is missing

- Verify that `https://www.saucedemo.com` is reachable.
- Confirm that the element IDs or classes have not changed.
- Use `WebDriverWait` and `ExpectedConditions` for dynamically loaded elements.
- Check the browser, test logs, and Maven Surefire report for the exact failure.

### Java version mismatch

The POM currently requests Java 23 source and target compatibility. Set
`JAVA_HOME` to JDK 23 and confirm Maven uses it:

```bash
java -version
mvn -version
```

### Test is not discovered

TestNG methods need `@Test`, and Maven needs to be able to compile and find the
test class. `NegativeLoginTest` is not discovered as a TestNG test until it is
converted from a `main` method to a `@Test` method.

## Notes and Limitations

- The current tests depend on public SauceDemo credentials and should have
  internet access.
- The demo uses live locators and may need updates if SauceDemo changes.
- `LoginTest` intentionally repeats several assertions and should be treated as
  a basic Selenium learning example.
- `CheckoutTest` and the page classes are placeholders.
- The TestNG dependency uses `compile` scope in the POM; TestNG is only needed
  for tests and would normally use `test` scope.
- Test credentials are demonstration values, not production secrets.
