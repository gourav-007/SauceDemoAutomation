# SauceDemo Automation Testing - Submission Summary

## Quick Links

- **GitHub Repository**: https://github.com/gourav-007/SauceDemoAutomation
- **CI/CD Pipeline**: https://github.com/gourav-007/SauceDemoAutomation/actions
- **Test Reports**: Download from Actions tab after test run

---

## Project Overview

This is a comprehensive automated testing framework for the [SauceDemo](https://www.saucedemo.com/) e-commerce application, built with **Java 17**, **Selenium WebDriver**, and **TestNG**.

## Test Coverage

| Module | Tests | Description |
|--------|-------|-------------|
| Login | 6 | Valid/invalid credentials, locked user, empty fields |
| Logout | 3 | Logout workflow, session management |
| Cart | 6 | Add/remove items, cart badge updates |
| Checkout | 8 | E2E flow, form validation, price calculation |
| **Total** | **23** | All workflows covered |

## Architecture

- **Page Object Model (POM)** - Clean separation of concerns
- **Explicit Waits** - No hardcoded sleeps, reliable execution
- **TestNG HTML Reports** - Human-readable test reports
- **GitHub Actions** - Automated CI/CD pipeline with manual trigger

## Run Tests

**Option 1: GitHub Actions (No Setup Required)**
1. Visit: https://github.com/gourav-007/SauceDemoAutomation/actions
2. Click on a workflow run to see test execution results
3. Download HTML report from Artifacts
4. **Manual Trigger**: Go to Actions → "Tests" workflow → Click "Run workflow" to run tests on-demand

**Option 2: Local Execution**
```bash
mvn clean test
# Open target/surefire-reports/index.html for HTML report
```

## Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Java 17 |
| Test Framework | TestNG 7.11 |
| Browser Automation | Selenium WebDriver 4.27 |
| Build Tool | Maven 3.x |
| Reporting | TestNG HTML Reports |

---

## Summary

✅ **23 comprehensive tests** covering all required workflows
✅ **Industry-standard POM architecture**
✅ **Automated CI/CD via GitHub Actions** with manual trigger
✅ **Human-readable HTML test reports**
✅ **Cross-browser support** (Chrome, Firefox, Edge)

All tests pass reliably. The framework is production-ready and easy to extend.
