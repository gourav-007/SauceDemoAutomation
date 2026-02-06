# SauceDemo Automated Testing Project

![Tests](https://img.shields.io/github/actions/workflow/status/gourav-007/SauceDemoAutomation/test.yml?label=Tests)

## Quick Start for Reviewers

**Want to see the tests run without installing anything?**

1. Visit: https://github.com/gourav-007/SauceDemoAutomation
2. Click the **Actions** tab to see automated test runs
3. Each push automatically triggers the test suite
4. You can also trigger tests manually: Go to Actions → "Tests" → Run workflow

**Want to run tests locally?**

```bash
mvn clean test
```

Open `target/surefire-reports/index.html` for a human-readable test report.

---

## Overview

This is a comprehensive automated testing framework for the [SauceDemo](https://www.saucedemo.com/) e-commerce demo application. Built with **Java**, **Selenium WebDriver**, and **TestNG**.

## Test Coverage

| Module | Tests | Description |
|--------|-------|-------------|
| Login | 6 | Valid/invalid credentials, locked user, empty fields |
| Logout | 3 | Logout workflow, session management |
| Cart | 6 | Add/remove items, cart badge updates |
| Checkout | 8 | E2E flow, form validation, price calculation |
| **Total** | **23** | All workflows covered |

## Project Structure

```
SauceDemoAutomation/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
└── src/test/
    ├── java/com/saucedemo/
    │   ├── base/BaseTest.java       # WebDriver setup/teardown
    │   ├── config/ConfigReader.java # Configuration management
    │   ├── data/TestData.java       # Test data constants
    │   ├── pages/                   # Page Object Models
    │   │   ├── BasePage.java
    │   │   ├── LoginPage.java
    │   │   ├── ProductsPage.java
    │   │   ├── CartPage.java
    │   │   ├── CheckoutInfoPage.java
    │   │   ├── CheckoutOverviewPage.java
    │   │   └── CheckoutCompletePage.java
    │   └── tests/                   # Test classes
    │       ├── LoginTests.java
    │       ├── LogoutTests.java
    │       ├── CartTests.java
    │       └── CheckoutTests.java
    └── resources/
        ├── config.properties        # Application configuration
        ├── log4j2.xml              # Logging configuration
        └── test-suite/testng.xml   # TestNG suite configuration
```

## Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Java 17 |
| Test Framework | TestNG 7.11 |
| Browser Automation | Selenium WebDriver 4.27 |
| Driver Management | WebDriverManager 5.9.2 |
| Build Tool | Maven 3.x |
| Reporting | TestNG HTML Reports |

## Prerequisites

- Java 17 or higher
- Maven 3.x
- Chrome browser

## Setup & Running

### Clone and Install
```bash
git clone https://github.com/gourav-007/SauceDemoAutomation.git
cd SauceDemoAutomation
mvn clean install
```

### Run All Tests
```bash
mvn clean test
```

### View Reports
```bash
# Open in browser
open target/surefire-reports/index.html
# or
start target/surefire-reports/index.html
```

## CI/CD Pipeline

Tests automatically run on every push via **GitHub Actions**:

1. Visit: https://github.com/gourav-007/SauceDemoAutomation/actions
2. Click on a workflow run to see:
   - Test execution progress
   - Pass/fail status
   - Test results

### Manual Trigger

You can also trigger tests manually without pushing code:

1. Go to: https://github.com/gourav-007/SauceDemoAutomation/actions/workflows/test.yml
2. Click **"Run workflow"** dropdown
3. Select branch (main) and click **"Run workflow"**

This is useful for reviewers who want to verify tests without requiring code changes.

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
- **HTML Reports** - Human-readable test reports
- **Cross-Browser Support** - Chrome, Firefox, Edge
- **CI/CD Ready** - GitHub Actions integration
- **Manual Trigger** - Run tests on-demand via Actions tab
