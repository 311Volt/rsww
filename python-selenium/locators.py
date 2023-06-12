from selenium.webdriver.common.by import By


class HomePageLocators(object):
    emailInput = (By.ID, "email")
    passwordInput = (By.ID, "password")
    pRegistered = (By.CSS_SELECTOR, ".text-muted")
