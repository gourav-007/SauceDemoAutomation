# SauceDemo Automated Testing Project

![Tests](https://github.com/gourav-007/SauceDemoAutomation/workflows/Tests/badge.svg)

## Quick Start for Reviewers

**Want to see the tests run without installing anything?**

1. Visit: https://github.com/gourav-007/SauceDemoAutomation
2. Click the **Actions** tab to see automated test runs
3. Each push automatically triggers the test suite

**Want to run tests locally?**

```bash
mvn clean test
```

---

## Overview

This is a comprehensive automated testing framework for the [SauceDemo](https://www.saucedemo.com/) e-commerce demo application. Built with **Java**, **Selenium WebDriver**, **TestNG**, and follows the **Page Object Model (POM)** design pattern.

## Test Coverage

| Module | Tests | Description |
|--------|-------|-------------|
| Login | 6 | Valid/invalid login, locked user, empty credentials |
| Logout | 3 | Logout workflow, session management |
| Cart | 6 | Add/remove items, cart badge updates |
| Checkout | 8 | E2E flow, form validation, total calculation |
| **Total** | **23** | All workflows covered |

## Project Structure

```
SauceDemoAutomation/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
└── src/test/
    ├── java/com/saucedemo/
    │   ├── base/
    │   │   └── BaseTest.java        # WebDriver setup/teardown
    │   ├── config/
    │   │   └── ConfigReader.java    # Configuration management
    │   ├── data/
    │   │   └── TestData.java       # Test data constants
    │   ├── pages/
    │   │   ├── BasePage.java        # Common page methods
    │   │   ├── LoginPage.java       # Login page POM
    │   │   ├── ProductsPage.java    # Products/Inventory POM
    │   │   ├── CartPage.java        # Shopping cart POM
    │   │   ├── CheckoutInfoPage.java# Checkout info form POM
    │   │   ├── CheckoutOverviewPage.java# Checkout summary POM
    │   │   └── CheckoutCompletePage.java# Order confirmation POM
    │   └── tests/
    │       ├── LoginTests.java      # Login test cases
    │       ├── LogoutTests.java     # Logout test cases
    │       ├── CartTests.java       # Cart test cases
    │       └── CheckoutTests.java   # Checkout test cases
    └── resources/
        ├── config.properties        # Application configuration
        ├── log4j2.xml               # Logging configuration
        └── test-suite/
            └── testng.xml           # TestNG suite configuration
```

## Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| Language | Java | 17 |
| Test Framework | TestNG | 7.11.0 |
| Browser Automation | Selenium WebDriver | 4.27.0 |
| Driver Management | WebDriverManager | 5.9.2 |
| Build Tool | Maven | 3.x |
| Reporting | Allure | 2.30.0 |
| Logging | Log4j2 | 2.25.2 |

## Prerequisites

- **Java 17 or higher** - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.x** - [Download Maven](https://maven.apache.org/download.cgi)
- **Chrome browser** (latest version)

## Setup & Running

### Clone and Install
```bash
git clone https://github.com/YOUR_USERNAME/SauceDemoAutomation.git
cd SauceDemoAutomation
mvn clean install
```

### Run All Tests
```bash
mvn clean test
```

### Run Specific Tests
```bash
mvn test -Dtest=LoginTests        # Login tests
mvn test -Dtest=CartTests         # Cart tests
mvn test -Dtest=CheckoutTests     # Checkout tests
mvn test -Dtest=LogoutTests      # Logout tests
```

### View Reports
```bash
mvn allure:serve   # Generate and open Allure report
```

## CI/CD Pipeline

Tests automatically run on every push via **GitHub Actions**:

1. **Visit**: https://github.com/gourav-007/SauceDemoAutomation/actions
2. **Click** on a workflow run to see:
   - Test execution progress
   - Pass/fail status
   - Allure reports
   - Test artifacts

## Configuration

Edit `src/test/resources/config.properties`:

```properties
app.url=https://www.saucedemo.com/
browser=chrome
headless=true
implicit.wait=10
```

## Key Features

- **Page Object Model (POM)** - Maintainable, reusable code
- **Explicit Waits** - No hardcoded sleeps
- **Allure Reports** - Detailed HTML reports with screenshots
- **Cross-Browser Support** - Chrome, Firefox, Edge
- **CI/CD Ready** - GitHub Actions integration

## Support

- Check **Actions** tab for CI test runs
- Review logs in `target/logs/`
- Generate Allure report: `mvn allure:serve`
